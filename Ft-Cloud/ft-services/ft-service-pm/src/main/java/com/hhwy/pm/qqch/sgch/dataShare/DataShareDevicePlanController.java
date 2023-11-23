package com.hhwy.pm.qqch.sgch.dataShare;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：设备策划数据推送
 * 作者: fushudong
 * 时间: 2023/11/23
 */
@RestController
@RequestMapping("/sbch/datapush/")
public class DataShareDevicePlanController {

    @Autowired
    private DataShareDevicePlanService dataShareDevicePlanService;

    @GetMapping("/test")
    public AjaxResult test(){
        dataShareDevicePlanService.eachStagePush();
        return AjaxResult.success();
    }
}