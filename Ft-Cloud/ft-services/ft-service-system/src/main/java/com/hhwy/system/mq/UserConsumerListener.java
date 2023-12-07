package com.hhwy.system.mq;/*
 * @Description: TODO
 * @Author: $
 * @Date: $
 **/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.api.domain.SysRole;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.core.domain.SysUserRole;
import com.hhwy.system.core.mapper.SysUserRoleMapper;
import com.hhwy.system.core.service.ISysRoleService;
import com.hhwy.system.core.service.ISysTenantService;
import com.hhwy.system.core.service.ISysUserService;
import com.hhwy.system.service.IDeptService;
import com.hhwy.system.service.IRoleService;
import com.hhwy.system.service.IUserService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 消费消息(同步领导用户)
 * 配置RocketMQ监听
 * @author
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "pm-leader-user",
        topic = "gm-system",
        selectorExpression = "sysUser",
        // 消费模式: 顺序消费
        consumeMode = ConsumeMode.ORDERLY)
public class UserConsumerListener implements RocketMQListener<String> , RocketMQPushConsumerLifecycleListener {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public void onMessage(String s) {
        boolean addUser = false;
        Map userInfo = JSON.parseObject(s, Map.class);
        // 需要同步的账号
        SysUser sysUser = JSONObject.parseObject(userInfo.get("sysUser").toString(),SysUser.class);

        // 需要同步账号的租户的集合
        List<String> tenantKeys = JSONObject.parseObject(userInfo.get("tenantKeys").toString(), ArrayList.class);

        if(sysUser != null && !CollectionUtils.isEmpty(tenantKeys)) {

            // 需要新增的用户
            List<SysUser> sysUserList4Add = new ArrayList<>();
            // 需要新增的用户与角色关系
            List<SysUserRole> sysUserRoleList4Add = new ArrayList<>();
            // 查询已存在该用户的租户用户数据
            SysUser sysUser1 = new SysUser();
            sysUser1.setUserName(sysUser.getUserName());
            List<SysUser> sysUsers = userService.selectSysUserInfo(sysUser1);

            // 查询每个租户默认的领导角色
            SysRole sysRole = new SysRole();
            sysRole.setRoleKey("common");
            List<SysRole> sysRoles = roleService.list(sysRole);

            // 获取每个租户的项目机构数据
            List<SysDept> projectOrgInfo = deptService.getProjectOrgInfo();

            for(String tenantKey : tenantKeys) {
                // 过滤已存在的租户用户
                if(!CollectionUtils.isEmpty(sysUsers)) {
                    SysUser sysUserExist = sysUsers.stream().filter(vo -> tenantKey.equals(vo.getTenantKey())).findFirst().orElse(null);

                    if(sysUserExist != null) {
                        //检测租户内是否对用户授权过管理员角色信息
                        List<SysUserRole> roleUserList = roleService.selectRoleUserExit(sysUserExist.getUserId(), 1L, tenantKey);
                        if(CollectionUtils.isEmpty(roleUserList)){
                            //带有区域中心管理员标识 赋予超管权限
                            if(sysUser.getRemark().equals("region")){
                                SysUserRole sysUserRoleRegion = new SysUserRole();
                                sysUserRoleRegion.setUserId(sysUserExist.getUserId());
                                sysUserRoleRegion.setRoleId(1L);
                                sysUserRoleRegion.setTenantKey(tenantKey);
                                sysUserRoleList4Add.add(sysUserRoleRegion);
                            }
                        }
                    }else{
                        addUser=true;
                    }
                }
                // 复制领导用户数据
                SysUser sysUser4Add = JSONObject.parseObject(JSONObject.toJSONString(sysUser), SysUser.class);
                if(sysUser4Add != null && addUser) {
                    // ID
                    Long id = IdWorker.createId();
                    sysUser4Add.setUserId(id);

                    if(!CollectionUtils.isEmpty(sysRoles)) {
                        //逻辑有问题暂时修改2023年9月13日17:27:37 todo
                        //SysRole sysRole1 = sysRoles.stream().filter(vo -> tenantKey.equals(vo.getTenantKey())).findFirst().orElse(null);
                        SysRole sysRole1 = sysRoles.get(0);
                        // 默认角色
                        if(sysRole1 != null) {
                            // 权限id 维护
                            Long[] roleIds = new Long[1];
                            roleIds[0] = sysRole1.getRoleId();
                            sysUser4Add.setRoleIds(roleIds);
                            sysUser4Add.setTenantKey(tenantKey);
                            sysUser4Add.setStatus("0");

                            // 角色用户关系维护
                            SysUserRole sysUserRole = new SysUserRole();
                            sysUserRole.setUserId(id);
                            sysUserRole.setRoleId(sysRole1.getRoleId());
                            sysUserRole.setTenantKey(tenantKey);
                            sysUserRoleList4Add.add(sysUserRole);

                            //带有区域中心管理员标识 赋予超管权限
                            if(sysUser.getRemark()!=null  && sysUser.getRemark().equals("region")){
                                SysUserRole sysUserRoleRegion = new SysUserRole();
                                sysUserRoleRegion.setUserId(id);
                                sysUserRoleRegion.setRoleId(1L);
                                sysUserRoleRegion.setTenantKey(tenantKey);
                                sysUserRoleList4Add.add(sysUserRoleRegion);
                            }
                        }
                    }
                    if(!CollectionUtils.isEmpty(projectOrgInfo)) {
                        SysDept sysDept = projectOrgInfo.stream().filter(vo -> tenantKey.equals(vo.getTenantKey())).findFirst().orElse(null);
                        // 默认部门维护
                        if(sysDept != null) sysUser4Add.setDeptId(null);
                    }

                    sysUserList4Add.add(sysUser4Add);
                    addUser=false;
                }
            }
            // 插入用户数据
            if(!CollectionUtils.isEmpty(sysUserList4Add)) userService.insertSysUserList(sysUserList4Add);
            // 插入权限关系数据
            if(!CollectionUtils.isEmpty(sysUserRoleList4Add)) sysUserRoleMapper.batchUserRole(sysUserRoleList4Add);
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
        defaultMQPushConsumer.setInstanceName("");
    }
}
