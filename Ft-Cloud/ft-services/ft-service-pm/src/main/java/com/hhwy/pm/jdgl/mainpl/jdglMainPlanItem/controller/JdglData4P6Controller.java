package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglData4P6Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jdglData4P6")
public class JdglData4P6Controller {

    @Autowired
    private IJdglData4P6Service jdglData4P6Service;

    @PostMapping("/initJdglData4P6")
    public AjaxResult initJdglData4P6() {
        return AjaxResult.success(jdglData4P6Service.initJdglData4P6());
    }

    @PostMapping("/initJdglData4P6ByOne")
    public AjaxResult initJdglData4P6ByOne(String tenantKey) {
        return AjaxResult.success(jdglData4P6Service.initJdglData4P6ByOne(tenantKey));
    }

    @PostMapping("/initJdglData4P6ByAll")
    public AjaxResult initAllJdglData4P6() {
        return AjaxResult.success(jdglData4P6Service.initAllJdglData4P6());
    }

}
