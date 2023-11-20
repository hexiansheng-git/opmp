package com.hhwy.system.warn.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.HtmlToText;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.domain.base.system.warn.TWarnRecord;
import com.hhwy.system.api.domain.SysRole;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.core.config.SseEmitterServer;
import com.hhwy.system.core.mapper.SysRoleMapper;
import com.hhwy.system.core.mapper.SysUserMapper;
import com.hhwy.system.mapper.UserMapper;
import com.hhwy.system.warn.mapper.TWarnMapper;
import com.hhwy.system.warn.mapper.TWarnRecordMapper;
import com.hhwy.system.warn.service.ITWarnService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    private SysRoleMapper roleMapper;

    @Autowired
    private UserMapper myUserMapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;



    public TWarn getTWarn(TWarn tWarn) {
        return tWarnMapper.getTWarn(tWarn);
    }

    public List<TWarn> getTWarnList(TWarn tWarn) {
        return tWarnMapper.getTWarnList(tWarn);
    }


    @Override
    @Transactional
    public int addWarn(TWarn tWarn) {
        tWarn.setWarnId(IdWorker.createId());
        tWarn.setCreateUser("admin");
        tWarn.setCreateTime(DateUtils.getNowDate());
        int result = tWarnMapper.insertTWarn(tWarn);

        //推送到总部版
        this.push2Head(tWarn);
        if (result > 0) {
            ThreadUtil.execAsync(() -> {
                this.notify(tWarn);
            });
        }
        return result;
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
            SseEmitterServer.batchSendMessage("system", HtmlToText.filterHtmlStr(warnContent));
        }

        if (StringUtils.isNotBlank(warnScopeType) && WarnScopeType.DEPT.getWarnScopeType().equals(warnScopeType)) {
            List<SysUser> userList = this.userMapper.selectUserListByDeptIds(tWarn.getWarnScope());

            for (SysUser user : userList) {
                SseEmitterServer.sendMessage(user.getUserName(), "system", HtmlToText.filterHtmlStr(warnContent));
            }
        }

        if (StringUtils.isNotBlank(warnScopeType) && WarnScopeType.USER.getWarnScopeType().equals(warnScopeType)) {
            String tWarnUsers = tWarn.getWarnScope();
            String[] userList = tWarnUsers.split(",");

            for (String tWarnUser : userList) {
                SseEmitterServer.sendMessage(tWarnUser, "system", HtmlToText.filterHtmlStr(warnContent));
            }
        }

        if(StringUtils.isNotBlank(warnScopeType) && WarnScopeType.ROLE.getWarnScopeType().equals(warnScopeType)){
            String warnScope = tWarn.getWarnScope();
            String[] roleKeyList = warnScope.split(",");
            List<SysUser> userList = myUserMapper.selectByRoleKeyList(roleKeyList, tWarn.getTenantKey());
            for (SysUser user : userList) {
                SseEmitterServer.sendMessage(user.getUserName(), "system", HtmlToText.filterHtmlStr(warnContent));
            }
        }
    }

    public List<TWarn> selectWarnListForSelf(TWarn warn) {
        Map<String, Object> params = new HashMap<>();
        params.put("deptId", SecurityUtils.getSysUser().getDeptId());
        params.put("userName", SecurityUtils.getUserName());
        Long userId = SecurityUtils.getUserId();
        String tenantKey = SecurityUtils.getTenantKey();
        List<SysRole> sysRoles = roleMapper.selectRoleListByUserId(userId, tenantKey, Collections.singletonList(tenantKey));
        String roleKeys = sysRoles.stream().map(SysRole::getRoleKey).collect(Collectors.joining());
        params.put("roleKeys",roleKeys);
        warn.setParams(params);
        warn.setTenantKey(tenantKey);
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
        int result = tWarnMapper.insertTWarn(tWarn);
        if (result > 0) {
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
        return tWarnMapper.insertTWarnList(tWarnList);
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
}
