package com.hhwy.system.warn.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.HtmlToText;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.socket.service.ISocketIOServerService;
import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.domain.base.system.warn.TWarnRecord;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.core.mapper.SysUserMapper;
import com.hhwy.system.mapper.UserMapper;
import com.hhwy.system.push.WarnPushMenHu;
import com.hhwy.system.warn.mapper.TWarnMapper;
import com.hhwy.system.warn.mapper.TWarnRecordMapper;
import com.hhwy.system.warn.push.Warn2Push;
import com.hhwy.system.warn.service.ITWarnService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2023-09-26 17:52:25
 * @remark
 */
@Service
public class TWarnServiceImpl implements ITWarnService {

    @Autowired
    private TWarnMapper tWarnMapper;

    @Autowired
    private TWarnRecordMapper tWarnRecordMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private UserMapper myUserMapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private ISocketIOServerService socketIOServerService;
    @Autowired
    private Warn2Push warn2Push;
    @Autowired
    private WarnPushMenHu warnPushMenHu;

    public TWarn getTWarn(TWarn tWarn) {
        return tWarnMapper.getTWarn(tWarn);
    }

    public List<TWarn> getTWarnList(TWarn tWarn) {
        return tWarnMapper.getTWarnList(tWarn);
    }

    @Override
    //@Transactional
    public int addWarnNonGm(TWarn tWarn) {
        String warnScope = tWarn.getWarnScope();
        if(StringUtils.isBlank(warnScope)){
            return 1;
        }
        this.setWarnScopeName(tWarn);
        tWarn.setWarnId(IdWorker.createId());
        tWarn.setCreateUser("admin");
        tWarn.setCreateTime(DateUtils.getNowDate());
        int result = tWarnMapper.insertTWarn(tWarn);
        if (result > 0) {
            ThreadUtil.execAsync(() -> {
                this.notify(tWarn);
            });
        }
        return result;
    }

    @Override
    @Transactional
    public int addWarn(TWarn tWarn) {
        String warnScope = tWarn.getWarnScope();
        if (StringUtils.isBlank(warnScope)) {
            return 1;
        }
        this.setWarnScopeName(tWarn);
        tWarn.setWarnId(IdWorker.createId());
        tWarn.setCreateUser("admin");
        tWarn.setCreateTime(DateUtils.getNowDate());
        int result = tWarnMapper.insertTWarn(tWarn);

        if (result > 0) {
            ThreadUtil.execAsync(() -> {
                this.notify(tWarn);
            });
        }
        //推送到总部版
        this.push2Head(tWarn);
        //推送到一公局门户
        warn2Push.push(tWarn);
        //推送到中交门户
        warnPushMenHu.push(tWarn);
        return result;
    }

    private void setWarnScopeName(TWarn tWarn){
        String warnScopeType = tWarn.getWarnScopeType();
        if(StringUtils.isBlank(warnScopeType) ){
            return;
        }
        String warnScope = tWarn.getWarnScope();
        String warnScopeName;
        if(WarnScopeType.ALL.getWarnScopeType().equals(warnScopeType)){
            warnScopeName = "全部";
        }else{
            List<SysUser> userList = new ArrayList<>();
            if(WarnScopeType.USER.getWarnScopeType().equals(warnScopeType)){
                List<String> userNameList = Arrays.stream(warnScope.split(",")).distinct().collect(Collectors.toList());
                userList = this.userMapper.selectUserListByUserNameList(userNameList, Collections.singletonList(tWarn.getTenantKey()));
            }else if(WarnScopeType.DEPT.getWarnScopeType().equals(warnScopeType)){
                userList = this.userMapper.selectUserListByDeptIds(warnScope);
            }else if(WarnScopeType.ROLE.getWarnScopeType().equals(warnScopeType)) {
                String[] roleKeyList = warnScope.split(",");
                userList = myUserMapper.selectByRoleKeyList(roleKeyList, tWarn.getTenantKey());
            }
            warnScopeName = userList.stream().map(SysUser::getNickName).collect(Collectors.joining(","));
        }
        tWarn.setWarnScopeName(warnScopeName);
    }

    private void push2Head(TWarn tWarn){
        this.setTWarn(tWarn);
        rocketMQTemplate.convertAndSend("pm_t_warn:tenantSuccess",tWarn);
    }

    private void setTWarn(TWarn tWarn){
        String warnScopeType = tWarn.getWarnScopeType();
        if(StringUtils.isBlank(warnScopeType)){
            return;
        }
        if(WarnScopeType.ALL.getWarnScopeType().equals(warnScopeType) || WarnScopeType.USER.getWarnScopeType().equals(warnScopeType)){
            return;
        }
        List<SysUser> userList = new ArrayList<>();
        if (WarnScopeType.DEPT.getWarnScopeType().equals(warnScopeType)) {
            userList = this.userMapper.selectUserListByDeptIds(tWarn.getWarnScope());
        }
        if(WarnScopeType.ROLE.getWarnScopeType().equals(warnScopeType)){
            String warnScope = tWarn.getWarnScope();
            String[] roleKeyList = warnScope.split(",");
            userList = myUserMapper.selectByRoleKeyList(roleKeyList, tWarn.getTenantKey());
        }
        String userNames = userList.stream().map(SysUser::getUserName).distinct().collect(Collectors.joining(","));
        tWarn.setWarnScopeType(WarnScopeType.USER.getWarnScopeType());
        tWarn.setWarnScope(userNames);
    }

    @Override
    public int addWarn(WarnItem warnItem, WarnScopeType warnScopeType, String warnScope, String warnUrl, String projectName, String tenantKey) {
        return 0;
    }

    private void notify(TWarn tWarn) {
        String warnScopeType = tWarn.getWarnScopeType();
        String warnContent = tWarn.getWarnContent();
        if (WarnScopeType.ALL.getWarnScopeType().equals(warnScopeType)) {
            socketIOServerService.pushMessageToClient("system", HtmlToText.filterHtmlStr(warnContent));
        }

        if (StringUtils.isNotBlank(warnScopeType) && WarnScopeType.DEPT.getWarnScopeType().equals(warnScopeType)) {
            List<SysUser> userList = this.userMapper.selectUserListByDeptIds(tWarn.getWarnScope());

            for (SysUser user : userList) {
                socketIOServerService.pushMessageToClient("system",user.getUserName(), HtmlToText.filterHtmlStr(warnContent));
            }
        }

        if (StringUtils.isNotBlank(warnScopeType) && WarnScopeType.USER.getWarnScopeType().equals(warnScopeType)) {
            String tWarnUsers = tWarn.getWarnScope();
            String[] userList = tWarnUsers.split(",");

            for (String tWarnUser : userList) {
                socketIOServerService.pushMessageToClient("system",tWarnUser, HtmlToText.filterHtmlStr(warnContent));
            }
        }

        if(StringUtils.isNotBlank(warnScopeType) && WarnScopeType.ROLE.getWarnScopeType().equals(warnScopeType)){
            String warnScope = tWarn.getWarnScope();
            String[] roleKeyList = warnScope.split(",");
            List<SysUser> userList = myUserMapper.selectByRoleKeyList(roleKeyList, tWarn.getTenantKey());
            for (SysUser user : userList) {
                socketIOServerService.pushMessageToClient("system",user.getUserName(), HtmlToText.filterHtmlStr(warnContent));
            }
        }
    }

    public List<TWarn> selectWarnListForSelf(TWarn warn) {
        return tWarnMapper.selectWarnListForSelf(warn);
    }

    @Override
    @Transactional
    public int changeHandleStatus(TWarnRecord record) {
        Long warnId = record.getWarnId();
        String userName = SecurityUtils.getUserName();
        record.setUpdateUser(userName);
        record.setUpdateTime(DateUtils.getNowDate());
        record.setWarnUserName(userName);
        TWarnRecord warnRecord = tWarnRecordMapper.getWarnRecordByWarnIdAndWarnUser(warnId, userName);
        if (warnRecord == null) {
            TWarn warn = tWarnMapper.selectWarnById(warnId);
            if (record.getWarnUserName() == null) {
                record.setWarnUserName(userName);
            }
            record.setId(IdWorker.createId());
            record.setCreateTime(warn.getCreateTime());
            record.setCreateUser(warn.getCreateUser());
            return tWarnRecordMapper.insertTWarnRecord(record);
        } else {
            return tWarnRecordMapper.changeStatus(record);
        }
    }

    @Override
    @Transactional
    public void batchChangeHandleStatus(Long[] warnIds, String status) {
        for (Long warnId : warnIds) {
            TWarnRecord record = new TWarnRecord();
            record.setWarnId(warnId);
            record.setStatus(status);
            this.changeHandleStatus(record);
        }
    }

    @Override
    public void pushTWarn(TWarn tWarn) {
        //总部版推送
        int result = tWarnMapper.insertTWarn(tWarn);
        if (result > 0) {
            tWarn.setPtVar1("1"); //标记为总部版
            warn2Push.push(tWarn);
            //推送到中交门户
            warnPushMenHu.push(tWarn);
            ThreadUtil.execAsync(() -> {
                this.notify(tWarn);
            });
        }
    }


    @Transactional
    public int insertTWarnList(List<TWarn> tWarnList) {
        for (TWarn tWarn : tWarnList) {
            tWarn.setWarnId(IdWorker.createId());
            tWarn.setCreateUser(SecurityUtils.getUserName());
            tWarn.setCreateTime(DateUtils.getNowDate());
        }
        int result = tWarnMapper.insertTWarnList(tWarnList);
        if (result > 0) {
            ThreadUtil.execAsync(() -> {
                for (TWarn tWarn : tWarnList) {
                    this.notify(tWarn);
                }
            });
        }
        return result;
    }

    @Transactional
    public int insertTWarnListToGm(List<TWarn> tWarnList) {
        for (TWarn tWarn : tWarnList) {
            tWarn.setWarnId(IdWorker.createId());
            tWarn.setCreateUser(SecurityUtils.getUserName());
            tWarn.setCreateTime(DateUtils.getNowDate());
        }
        int result = tWarnMapper.insertTWarnList(tWarnList);
        if (result > 0) {
            ThreadUtil.execAsync(() -> {
                for (TWarn tWarn : tWarnList) {
                    this.notify(tWarn);
                    //推送到总部版
                    this.push2Head(tWarn);
                    //推送到一公局门户
                    warn2Push.push(tWarn);
                    //推送到中交门户
                    warnPushMenHu.push(tWarn);
                }
            });
        }
        return result;
    }

    @Transactional
    public int updateTWarn(TWarn tWarn) {
        tWarn.setUpdateUser(SecurityUtils.getUserName());
        tWarn.setUpdateTime(DateUtils.getNowDate());
        return tWarnMapper.updateTWarn(tWarn);
    }

    @Transactional
    public int updateTWarnList(List<TWarn> tWarnList) {
        for (TWarn tWarn : tWarnList) {
            tWarn.setUpdateUser(SecurityUtils.getUserName());
            tWarn.setUpdateTime(DateUtils.getNowDate());
        }
        return tWarnMapper.updateTWarnList(tWarnList);
    }

    @Transactional
    public int deleteTWarn(TWarn tWarn) {
        tWarn.setUpdateUser(SecurityUtils.getUserName());
        tWarn.setUpdateTime(DateUtils.getNowDate());
        return tWarnMapper.deleteTWarn(tWarn);
    }

    @Transactional
    public int deleteTWarnByPks(List<Long> tWarnPkList) {
        return tWarnMapper.deleteTWarnByPks(tWarnPkList);
    }

    public List<SysUser> selectByRoleKeyList(String[] roleKeyList){
        return myUserMapper.selectByRoleKeyList(roleKeyList,"master");
    }

    @Override
    public List<SysUser> selectByRoleKeyList(String[] roleKeyList, String tenantKey) {
        if (StrUtil.isBlank(tenantKey))
            tenantKey = "master";
        return myUserMapper.selectByRoleKeyList(roleKeyList,tenantKey);
    }
}
