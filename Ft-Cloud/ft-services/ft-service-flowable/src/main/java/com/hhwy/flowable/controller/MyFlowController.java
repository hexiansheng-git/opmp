package com.hhwy.flowable.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.flowable.service.INodeTaskService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/myFlow")
public class MyFlowController {

    @Autowired
    private INodeTaskService nodeTaskService;

    @RequestMapping("/isNowfirstNode")
    public AjaxResult isNowfirstNode(@RequestParam("insId") String insId){
        String firstName = nodeTaskService.isNowfirstNode(insId);
        return AjaxResult.success(firstName);
    }
}
