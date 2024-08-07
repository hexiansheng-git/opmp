package com.hhwy.pm.mq.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.tenant.processor.ITenantDataSourceProcessor;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.exception.CustomBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 租户数据库表创建、删除、修改回调
 */
@Service
@Slf4j
public class ITenantDataProcessorImpl implements ITenantDataSourceProcessor {

    @Autowired
    private RocketMQTemplate   rocketMQTemplate;

    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;

    @Override
    public void doPostForCreated(SysTenant sysTenant) {
        System.out.println("租户创建成功回调方法开始********************************************************************************");
        log.info("租户创建成功回调方法开始********************************************************************************租户代码："+sysTenant.getTenantKey());
        Map<String, Object> projectInfo = sysTenant.getParams();
        rocketMQTemplate.convertAndSend("pm:tenantSuccess",projectInfo);
        XmslProjectBasicInfo xmslProjectBasicInfoParam = JSON.parseObject(JSON.toJSONString(projectInfo), XmslProjectBasicInfo.class);
        String tenantKey = sysTenant.getTenantKey();
        String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
        System.out.println("新建租户数据源****************"+dataSource+"******************************");
        log.info("新建租户数据源****************"+dataSource+"******************************");
        String oldDataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey("master");
        if(StringUtils.isNotBlank(dataSource) && !dataSource.equals(oldDataSource)){
            DynamicDataSourceContextHolder.push(dataSource);
            try {
                projectBasicInfoService.insertProjectInvokeProject(xmslProjectBasicInfoParam);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("往表xmsl_project_basic_info初始化项目信息失败 租户代码："+sysTenant.getTenantKey());
            } finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        }else{
            throw new CustomBusinessException("数据源为空");
        }
        System.out.println("租户创建成功回调方法结束**********************************************");
        log.info("租户创建成功回调方法结束**********************************************租户代码："+sysTenant.getTenantKey());

    }

    @Override
    public void doPostForEdited(SysTenant sysTenant) {

    }

    @Override
    public void doPostForDeleted(SysTenant sysTenant) {

    }
}
