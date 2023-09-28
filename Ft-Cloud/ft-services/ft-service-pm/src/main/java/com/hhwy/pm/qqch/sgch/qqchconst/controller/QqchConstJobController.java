package com.hhwy.pm.qqch.sgch.qqchconst.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstFacilityPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstJob;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstFacilityPlanService;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstJobService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：施工部署 - 工作内容
 * 作者: fushudong
 * 时间: 2023/09/27
 */
@RestController
@RequestMapping("/qqchConstJob")
public class QqchConstJobController {

    @Autowired
    private IQqchConstJobService constJobService;

    /**
     * 获取班组，根据工作内容的wbs
     * @return
     */
    @GetMapping("/getWorkGroupByWBS")
    public AjaxResult getWorkGroupByWBS(QqchConstJob constJob) {
        List<QqchConst> list = constJobService.getWorkGroupByWBS(constJob);
        return AjaxResult.success(list);
    }
}