package com.hhwy.sp.job.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.job.service.ExperimentJobServiceImpl;
import com.hhwy.sp.job.service.MeasureJobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobController")
public class JobServiceController {
    @Autowired
    private ExperimentJobServiceImpl jobService;

    @Autowired
    private MeasureJobServiceImpl measureJobService;

    /**
     * 试验进场设备
     *
     * @return
     */
    @GetMapping("/getWuSheMaterialRecord")
    public AjaxResult getWuSheMaterialRecord(){
        jobService.getWuSheMaterialRecord();
        return AjaxResult.success();
    }

    /**
     * 测量进场设备
     *
     * @return
     */
    @GetMapping("/getWuSheMeasureMaterialInfo")
    public AjaxResult getWuSheMeasureMaterialInfo(){
        measureJobService.getWuSheMeasureMaterialInfo();
        return AjaxResult.success();
    }
}
