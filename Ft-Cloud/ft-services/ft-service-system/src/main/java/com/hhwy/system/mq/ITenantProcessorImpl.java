package com.hhwy.system.mq;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.flowable.api.RemoteBpmnSyncService;
import com.hhwy.system.api.domain.SysRole;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.core.domain.SysUserRole;
import com.hhwy.system.core.mapper.SysUserMapper;
import com.hhwy.system.core.mapper.SysUserRoleMapper;
import com.hhwy.system.core.processor.ITenantProcessor;
import com.hhwy.system.service.IRoleService;
import com.hhwy.system.service.IUserService;
import com.hhwy.utils.exception.CustomBusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class ITenantProcessorImpl implements ITenantProcessor {

    @Autowired
    private RocketMQTemplate   rocketMQTemplate;

    @Autowired
    PmServiceApi pmServiceApi;

    @Autowired
    RemoteBpmnSyncService remoteBpmnSyncService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private IUserService userService;

    @Override
    public void doPostForInsert(SysTenant sysTenant) {
//        System.out.println("租户创建成功回调方法开始********************************************************************************");
//        Map<String, Object> projectInfo = sysTenant.getParams();
//        rocketMQTemplate.convertAndSend("pm:tenantSuccess",projectInfo);
//        AjaxResult res = pmServiceApi.insertProjectTenant(projectInfo);
//        System.out.println("租户创建成功回调方法开始********************************************************************************");

//        if(!res.get("code").toString().equals("200")){
//            throw  new CustomBusinessException("同步项目信息到租户数据库失败！！");
//        };

        masterToTenant(sysTenant);
        addRoleToTenant(sysTenant);
    }

    //给租户下发流程信息
    public void masterToTenant(SysTenant sysTenant) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tenantKey",sysTenant.getTenantKey());
        remoteBpmnSyncService.masterToTenant(jsonObject);
    }
    //给租户用户分配默认角色
    public void addRoleToTenant(SysTenant sysTenant) {
        //默认普通角色信息
        SysRole sysRole = new SysRole();
        sysRole.setRoleKey("common");
        List<SysRole> sysRoles = roleService.list(sysRole);
        SysRole sysRoleCommon = sysRoles.get(0);
        String tenantKey = sysTenant.getTenantKey();
        //当前租户用户信息
        List<SysUser> userList=userService.selectUserIdByTenant(tenantKey);
        //插入的数据
        List<SysUserRole> sysUserRoleList=new ArrayList<>();

        // 角色用户关系维护
        for(SysUser item:userList){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(item.getUserId());
            sysUserRole.setRoleId(sysRoleCommon.getRoleId());
            sysUserRole.setTenantKey(tenantKey);
            sysUserRoleList.add(sysUserRole);
        }
        if(CollectionUtils.isNotEmpty(sysUserRoleList)) {
            sysUserRoleMapper.batchUserRole(sysUserRoleList);
        }

    }

    @Override
    public void doPostForUpdate(SysTenant sysTenant) {

    }

    @Override
    public void doPostForDelete(String s) {

    }
}
