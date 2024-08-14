package com.hhwy.system.controller;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.system.api.domain.SysRole;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.core.domain.SysUserRole;
import com.hhwy.system.core.mapper.SysUserRoleMapper;
import com.hhwy.system.mapper.RoleMapper;
import com.hhwy.system.mapper.UserMapper;
import com.hhwy.system.service.IRoleService;
import com.hhwy.system.service.IUserService;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/RoleAndUser")
public class RoleAndUserBindController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper myUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * master中对应角色的用户，推送到对应租户上
     * @param sysUserRole
     * @return
     */
    @PostMapping("/bind")
    @Transactional
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody SysUserRole sysUserRole) {
        String errorMsg = "";
        if (StringUtils.isBlank(sysUserRole.getTenantKey())||sysUserRole.getRoleId()==null||sysUserRole.getRoleId()==0L) {
            AjaxResult.error("请选中租户和角色");
        }
        log.info("给租户用户分配指定角色开始，租户key是"+sysUserRole.getTenantKey());
        log.info("给租户用户分配指定角色开始，角色id是"+sysUserRole.getRoleId());
        //获取角色信息
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(sysUserRole.getRoleId());
        List<SysRole> sysRoles = roleService.list(sysRole);
        SysRole sysRoleCommon = sysRoles.get(0);
        log.info("选中master租户中的角色是："+sysRoleCommon.getRoleName()+"key是:"+sysRoleCommon.getRoleKey());
        String tenantKeyStr = sysUserRole.getTenantKey();
        String[] tenantKeyArr = null;
        if (tenantKeyStr.contains(",")) {
            tenantKeyArr = tenantKeyStr.split(",");
        } else {
            tenantKeyArr = new String[]{sysUserRole.getTenantKey()};
        }
        //master租户对应角色中用户信息
        String[] roleKeyList = {sysRoleCommon.getRoleKey()};
        List<SysUser> userListMaster = myUserMapper.selectByRoleKeyList(roleKeyList, "master");
        if (userListMaster == null && userListMaster.size() == 0) {
            String msg = "master里面角色为："+sysRoleCommon.getRoleName()+"没有绑定用户";
            log.error(msg);
            AjaxResult.error(msg);
        } else {
            for (String tenantKey:tenantKeyArr) {
                //插入的数据
                List<SysUserRole> sysUserRoleList=new ArrayList<>();
                //删除选中租户对应角色中用户信息
                roleMapper.deleteRoleUserExit(sysUserRole.getRoleId(), tenantKey);
                //选中租户所有用户信息
                List<SysUser> userList=userMapper.selectUserIdByTenantNew(tenantKey);
                if (userList != null && userList.size() > 0) {
                    for (SysUser item : userListMaster) {
                        //循环master中用户，筛选选中租户中对应的用户信息
                        List<SysUser> sysUserList = userList.stream().filter(item1 -> StringUtils.isNotBlank(item1.getUserName()) &&item1.getUserName().equals(item.getUserName())).collect(Collectors.toList());
                        if ((sysUserList != null && sysUserList.size() > 0)) {
                            SysUserRole sysUserRoleNew = new SysUserRole();
                            sysUserRoleNew.setUserId(sysUserList.get(0).getUserId());
                            sysUserRoleNew.setRoleId(sysRoleCommon.getRoleId());
                            sysUserRoleNew.setTenantKey(tenantKey);
                            sysUserRoleList.add(sysUserRoleNew);
                        } else {
                            errorMsg += "租户key："+tenantKey+"中没有指定用户信息 用户账号："+item.getUserName()+" 用户名称："+item.getNickName()+" \n";
                        }
                    }
                } else if (CollectionUtils.isNotEmpty(sysUserRoleList)) {
                    errorMsg += "租户key："+tenantKey+"中用户为空 \n";
                }
                if (sysUserRoleList != null && sysUserRoleList.size() > 0) {
                    sysUserRoleMapper.batchUserRole(sysUserRoleList);
                }
                log.info("给租户用户分配选中角色结束，租户key是"+tenantKey+"，角色是"+sysRoleCommon.getRoleName());
            }
        }
        if (StringUtils.isBlank(errorMsg)) {
            log.info("下发成功");
            return AjaxResult.success("下发成功");
        } else {
            log.error(errorMsg);
            return AjaxResult.success("部分用户信息下发成功 \n失败用户信息：\n"+errorMsg);
        }
    }
}
