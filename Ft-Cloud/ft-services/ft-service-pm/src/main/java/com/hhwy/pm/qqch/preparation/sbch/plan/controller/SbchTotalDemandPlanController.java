package com.hhwy.pm.qqch.preparation.sbch.plan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlan;
import com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail;
import com.hhwy.pm.qqch.preparation.sbch.plan.service.SbchTotalDemandPlanService;
import com.hhwy.pm.qqch.preparation.sbch.plan.vo.SbchTotalDemandPlanDetailVo;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 设备总部计划Controller
 * 
 * @author zq
 * @date 2022-11-23
 *
 * 7.1
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
//    @PreAuthorize(hasPermi = "sbchTotalDemandPlan:list")
    @GetMapping("/getList")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备总需计划", name = "7.1设备总需计划", businessType = CustomBusinessType.SELECT)
    public AjaxResult getList(BigDecimal version) {
        SbchTotalDemandPlan sbchTotalDemandPlan = sbchTotalDemandPlanService.getList(version);
        return AjaxResult.success(sbchTotalDemandPlan);
    }

//    @PreAuthorize(hasPermi = "sbchTotalDemandPlan:add")
    @PostMapping("/batchAdd")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备总需计划", name = "7.1设备总需计划", businessType = CustomBusinessType.SAVE)
    public AjaxResult add(@Validated(ValidationGroups.Save.class) @RequestBody SbchTotalDemandPlan vo){
        try{
            sbchTotalDemandPlanService.batchSave(vo);
            return AjaxResult.success();
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询设备总部计划总需用详情列表--领导视角 其他功能调用
     *
     * 7.4.3 选择7.1的设备，默认条件：当地采购数量 大于0
     * 7.3.2 选择7.1的设备，默认条件：是特种设备
     * 7.2.4 选择7.1的设备，默认条件：协作单位自带数量 大于0
     * 7.2.5 选择7.1的设备，默认条件：当地采购数量 大于0
     * 7.2.5 选择7.1的设备，默认条件：是特种设备
     */
    @PostMapping("/leaderList")
    @ResponseBody
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备总需计划", name = "7.1设备总需计划", businessType = CustomBusinessType.SELECT)
    public AjaxResult leaderList(@RequestBody SbchTotalDemandPlanDetail sbchTotalDemandPlanDetail) {
//        startPage(sbchTotalDemandPlanDetail.getPageNum(),sbchTotalDemandPlanDetail.getPageSize());
        SbchTotalDemandPlan sbchTotalDemandPlan = sbchTotalDemandPlanService.getLeaderList(sbchTotalDemandPlanDetail);
        return AjaxResult.success(getDataTable(sbchTotalDemandPlan.getPlanDetailList()));
    }




}
