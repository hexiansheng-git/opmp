package com.hhwy.job.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * sd服务
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-sd")
public interface SdServiceApi {

    @GetMapping("/kcsjPlanMonthlyReport/generateMonthlyReport")
    AjaxResult generateMonthlyReport();

    /**
     * 每周末生成计划进度管理-周报数据
     * @return
     */
    @GetMapping("/kcsjPlanWeekReport/produceData")
    AjaxResult produceData();

    /**
     * 勘察设计--技术文件报批
     *
     * @return
     */
    @GetMapping("/kcsjDesignDocumentApproval/designFileTask")
    AjaxResult designFile();

    /**
     * 勘察设计--同步物设系统设备进场记录
     *
     * @return
     */
    @GetMapping("/kcsjEquipEntryRecord/syncWusheJob")
    AjaxResult syncWusheJob();

    /**
     * 计划进度 job 预警
     *
     * @return
     */
    @GetMapping("/kcsjPlanProcess/jobPlanProcess")
    AjaxResult jobPlanProcess();
}
