package com.hhwy.pm.qqch.sgch.dataShare;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.system.api.domain.SysTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能：设备策划数据推送
 * 作者: fushudong
 * 时间: 2023/11/23
 */
@RestController
@RequestMapping("/sbch/datapush")
public class DataShareDevicePlanController {

    @Autowired
    private DataShareDevicePlanService dataShareDevicePlanService;

    @GetMapping("/test")
    public AjaxResult test(){
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("permission defined");
        String tenantKey = SecurityUtils.getTenantKey();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            //切换到master
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
            try {
                dataShareDevicePlanService.eachStagePush(tenantKey);
//                dataShareDevicePlanService.eachChangePush(tenantKey);
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomException(e.getMessage());
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        });
        return AjaxResult.success();
    }
    @GetMapping("/eachChangePush")
    public AjaxResult eachChangePush(){
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("permission defined");
        String tenantKey = SecurityUtils.getTenantKey();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            //切换到master
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
            try {
                dataShareDevicePlanService.eachChangePush(tenantKey);
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomException(e.getMessage());
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        });
        return AjaxResult.success();
    }
}