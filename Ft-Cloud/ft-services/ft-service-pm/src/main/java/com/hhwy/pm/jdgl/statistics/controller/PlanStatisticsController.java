package com.hhwy.pm.jdgl.statistics.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.jdgl.statistics.domain.*;
import com.hhwy.pm.jdgl.statistics.service.IPlanStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/planStatistics")
public class PlanStatisticsController  {

    @Autowired
    private IPlanStatisticsService iPlanStatisticsService;

    @PostMapping("/getValueCompData")
    public AjaxResult getValueCompData(@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO) {
        return AjaxResult.success(iPlanStatisticsService.getValueCompData(iPlanStatisticsQueryVO));
    };

    @PostMapping("/getWbsValueList")
    public AjaxResult getWbsValueList(@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO) {
        return AjaxResult.success(iPlanStatisticsService.getWbsValueList(iPlanStatisticsQueryVO));
    };

    @PostMapping("/getBillValueList")
    public AjaxResult getBillValueList(@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO){
        return AjaxResult.success(iPlanStatisticsService.getBillValueList(iPlanStatisticsQueryVO));
    };

    @PostMapping("/getImageWbsList")
    public AjaxResult getImageWbsList(@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO){
        return AjaxResult.success(iPlanStatisticsService.getImageWbsList(iPlanStatisticsQueryVO));
    };

    @PostMapping("/getYearValueCompareList")
    public AjaxResult getYearValueCompareList(@RequestBody PlanStatisticsQueryVO iPlanStatisticsQueryVO){
        return AjaxResult.success(iPlanStatisticsService.getYearValueCompareList(iPlanStatisticsQueryVO));
    };

}
