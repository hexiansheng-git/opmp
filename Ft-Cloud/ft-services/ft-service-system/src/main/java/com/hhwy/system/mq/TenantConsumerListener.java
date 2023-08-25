package com.hhwy.system.mq;/*
 * @Description: TODO
 * @Author: $
 * @Date: $
 **/

import com.alibaba.fastjson.JSON;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.core.service.ISysTenantDbService;
import com.hhwy.system.core.service.ISysTenantService;
import com.hhwy.system.core.service.ISysUserService;
import com.hhwy.system.core.service.impl.SysTenantResourceServiceImpl;
import com.hhwy.system.service.IDeptService;
import com.hhwy.system.service.IUserService;
import com.hhwy.utils.exception.CustomBusinessException;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 消费消息
 * 配置RocketMQ监听
 * @author qzz
 */
@Service
@RocketMQMessageListener(
        consumerGroup = "pm-project-system",
        topic = "gm",
        selectorExpression = "prj",
        // 消费模式: 顺序消费
        consumeMode = ConsumeMode.ORDERLY)
public class TenantConsumerListener implements RocketMQListener<String> , RocketMQPushConsumerLifecycleListener {


    @Autowired
    private ISysTenantService tenantService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;
    @Override
    public void onMessage(String s) {
//            try {
        System.out.println(s+"***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************");
                Map projectBasicInfo = JSON.parseObject(s, Map.class);
                String projectName = (String) projectBasicInfo.get("projectName");
                String projectCode = (String) projectBasicInfo.get("projectCode");
                Long projectId = (Long) projectBasicInfo.get("projectId");
                SysTenant sysTenant = new SysTenant();

                sysTenant.setParams(projectBasicInfo);
                sysTenant.setTenantName(projectName);
                sysTenant.setTenantKey(projectCode);
                sysTenant.setTenantStatus("0");
                sysTenant.setValidityStatus("1");
                sysTenant.setAdministratorNickName("系统管理员");
                sysTenant.setAdministratorUserName("admin");
                sysTenant.setAdministratorPassword(SecurityUtils.encryptRSAPassword("admin123"));
                sysTenant.setMenuStatus("1");
                sysTenant.setRoleStatus("1");
                sysTenant.setPostStatus("1");
                sysTenant.setDeptStatus("0");
                sysTenant.setUserStatus("0");

                SysDept dept=deptService.selectDeptIdByprojectId(projectId);
                Long deptId = dept.getDeptId();
                String ancestors = dept.getAncestors();
                List<SysDept> deptList = deptService.selectAllDept(deptId,ancestors);
                sysTenant.setDeptList(deptList);

                List<SysUser> userList=userService.selectAllUser(deptList);
                sysTenant.setUserList(userList);
                this.tenantService.insertSysTenant(sysTenant);
//            }catch (MyBatisSystemException e){
//                e.printStackTrace();
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
        defaultMQPushConsumer.setInstanceName("mqconsumer_system");
    }
}
