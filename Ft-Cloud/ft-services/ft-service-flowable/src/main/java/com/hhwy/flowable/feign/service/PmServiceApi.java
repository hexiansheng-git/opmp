package com.hhwy.flowable.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文件服务
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-pm")
public interface PmServiceApi {


    /**
     *  修改合同流程状态
     */
    @RequestMapping(value = "/xmslContractInfo/listener/", method = RequestMethod.POST)
    AjaxResult updateContract(@RequestParam("id") Long id);

    /**
     *  修改年计划流程状态
     */
    @RequestMapping(value = "/jdglYearPlan/listener/", method = RequestMethod.POST)
    AjaxResult updateJdglYearPlan(@RequestParam("id") Long id);
    /**
     *  修改季计划流程状态
     */
    @RequestMapping(value = "/jdglQuarterPlan/listener/", method = RequestMethod.POST)
    AjaxResult updateJdglQuarterPlan(@RequestParam("id") Long id);
    /**
     *  修改月计划流程状态
     */
    @RequestMapping(value = "/jdglMonthPlan/listener/", method = RequestMethod.POST)
    AjaxResult updateJdglMonthPlan(@RequestParam("id") Long id);
    /**
     *  修改周计划流程状态
     */
    @RequestMapping(value = "/jdglWeekPlan/listener/", method = RequestMethod.POST)
    AjaxResult updateJdglWeekPlan(@RequestParam("id") Long id);
    /**
     *  修改进度填报流程状态
     */
    @RequestMapping(value = "/jdglDaySchedule/listener/", method = RequestMethod.POST)
    AjaxResult updateJdglDaySchedule(@RequestParam("id") Long id);

    /**
     * 修改前期策划工作小组流程状态
     * @param id
     * @return
     */
    @PostMapping("/qqchWorkGroup/listener")
    AjaxResult updateWorkGroupProcess(@RequestParam("id") Long id);
}
