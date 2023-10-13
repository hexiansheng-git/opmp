package com.hhwy.system.warn.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.HtmlToText;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.core.config.SseEmitterServer;
import com.hhwy.system.core.mapper.SysUserMapper;
import com.hhwy.system.mapper.UserMapper;
import com.hhwy.system.warn.mapper.TWarnMapper;
import com.hhwy.system.warn.service.ITWarnService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private SysUserMapper userMapper;

    @Autowired
    private UserMapper myUserMapper;


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
        if (result > 0) {
            ThreadUtil.execAsync(() -> {
                this.notify(tWarn);
            });
        }
        return result;
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
            List<SysUser> userList = myUserMapper.selectByRoleKeyList(roleKeyList, "master");
            for (SysUser user : userList) {
                SseEmitterServer.sendMessage(user.getUserName(), "system", HtmlToText.filterHtmlStr(warnContent));
            }
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
