package com.hhwy.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.feign.factory.PmServiceFallbackFactory;
import com.hhwy.feign.service.domain.CommonQqchMeasureExpRange;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 文件服务
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-pm", fallbackFactory = PmServiceFallbackFactory.class)
public interface PmServiceApi {


    /**
     *  拆入对应租户的项目信息
     */
    @PostMapping("/projectBasicInfo/addTenant")
    AjaxResult insertProjectTenant(@RequestBody Map map);

    /**
     *  3.6.1测量工作概述
     */

    @PostMapping("/qqchMeasureExpRange/feignList")
    AjaxResult qqchMeasureExpRangeList(@RequestBody CommonQqchMeasureExpRange dto);


    /**
     * 获取项目技术管理部门及岗位设置表
     * @return
     */
    @GetMapping("/qqchPostSetting/getTechDeptList")
    List<QqchPostSetting> getTechDeptList();

    /**
     * 插入同步日志
     * @param log
     * @return
     */
    @PostMapping("/syncInfo/insert")
    AjaxResult insertSyncLog(@RequestBody SysSyncInfoLog log);
}
