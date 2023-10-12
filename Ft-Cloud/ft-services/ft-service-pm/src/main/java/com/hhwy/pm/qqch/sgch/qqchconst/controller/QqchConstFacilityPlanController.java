package com.hhwy.pm.qqch.sgch.qqchconst.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstFacilityPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstFacilityPlanService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 功能：施工部署 - 设备策划
 * 作者: fushudong
 * 时间: 2023/09/27
 */
@RestController
@RequestMapping("/qqchConstFacilityPlan")
public class QqchConstFacilityPlanController {

    @Autowired
    private IQqchConstFacilityPlanService constFacilityPlanService;

    /**
     * 获取设备策划
     * @return
     */
    @GetMapping("/getDevicePlanList")
    public AjaxResult getDevicePlanList(QqchConst qqchConst) {
        if (qqchConst.getVersion() == null){
            BigDecimal version = VersionUtil.getVersion("qqch_const",null);
            qqchConst.setVersion(version);
        }
        List<QqchConstFacilityPlan> list = constFacilityPlanService.list(CompileEntity.dealListDto(qqchConst.getVersion(), new QqchConstFacilityPlan()));
        return AjaxResult.success(list);
    }

    /**
     * 获取设备策划和人员策划 2.1.3使用 根据主表明细字段获取id，根据id到设备策划表获取列表
     * @return
     */
    @GetMapping("/queryDevicePlanListByConstDesc")
    public AjaxResult queryDevicePlanListByConstDesc(List<String> constDescs) {
        BigDecimal version = VersionUtil.getVersion("qqch_const",null);
        Map list = constFacilityPlanService.queryDevicePlanListByConstDesc(constDescs, version);
        return AjaxResult.success(list);
    }

}