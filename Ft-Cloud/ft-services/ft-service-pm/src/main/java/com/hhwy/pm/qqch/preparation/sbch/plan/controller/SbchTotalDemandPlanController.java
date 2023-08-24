package com.hhwy.pm.qqch.preparation.sbch.plan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.sbch.plan.service.SbchTotalDemandPlanService;
import com.hhwy.pm.qqch.preparation.sbch.plan.vo.SbchTotalDemandPlanDetailVo;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * 设备总部计划Controller
 * 
 * @author zq
 * @date 2022-11-23
 */
@Controller
@RequestMapping("/plan/info")
public class SbchTotalDemandPlanController extends BaseController {
    @Autowired
    private SbchTotalDemandPlanService sbchTotalDemandPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "sbchTotalDemandPlan:list")
    @GetMapping("/getList")
    @ResponseBody
    public AjaxResult getList(BigDecimal version) {
        SbchTotalDemandPlanDetailVo sbchTotalDemandPlanDetailVo = sbchTotalDemandPlanService.getList(version);
        return AjaxResult.success(sbchTotalDemandPlanDetailVo);
    }

    @PreAuthorize(hasPermi = "sbchTotalDemandPlan:add")
    @PostMapping("/batchAdd")
    @ResponseBody
    public AjaxResult add(@Validated(ValidationGroups.Save.class) @RequestBody SbchTotalDemandPlanDetailVo vo){
        try{
            sbchTotalDemandPlanService.batchSave(vo);
            return AjaxResult.success();
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }


}
