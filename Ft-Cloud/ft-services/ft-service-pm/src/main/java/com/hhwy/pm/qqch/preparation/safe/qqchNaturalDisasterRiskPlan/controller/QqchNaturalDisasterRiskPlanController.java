package com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.domain.QqchNaturalDisasterRiskPlan;
import com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.domain.vo.QqchNaturalDisasterRiskPlanVo;
import com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.service.IQqchNaturalDisasterRiskPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 09:30:37
 * @remark
 *
 * 8.6.2 自然灾害风险策划
 */
@Validated
@RestController
@RequestMapping("/qqchNaturalDisasterRiskPlan")
public class QqchNaturalDisasterRiskPlanController extends BaseController{

    @Autowired
    private IQqchNaturalDisasterRiskPlanService qqchNaturalDisasterRiskPlanService;

    /**
     * 列表接口
     * @param qqchNaturalDisasterRiskPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchNaturalDisasterRiskPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchNaturalDisasterRiskPlanList(@Validated(ValidationGroups.Select.class) QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlanParam){
         QqchNaturalDisasterRiskPlanVo vo = qqchNaturalDisasterRiskPlanService.getQqchNaturalDisasterRiskPlanList(qqchNaturalDisasterRiskPlanParam);
        return AjaxResult.success(vo);
    }

    /**
     * 保存/确认/提交
     * @param vo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchNaturalDisasterRiskPlan:save")
    @PostMapping("/save")
    public AjaxResult insertQqchNaturalDisasterRiskPlanList(@Validated(ValidationGroups.Save.class) @RequestBody QqchNaturalDisasterRiskPlanVo vo){
        qqchNaturalDisasterRiskPlanService.save(vo);
        return AjaxResult.success();
    }


    @PreAuthorize(hasPermi = "qqchNaturalDisasterRiskPlan:list")
    @GetMapping
    public AjaxResult getQqchNaturalDisasterRiskPlan(@Validated(ValidationGroups.Get.class)  QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlanParam){
        QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan =  qqchNaturalDisasterRiskPlanService.getQqchNaturalDisasterRiskPlan(qqchNaturalDisasterRiskPlanParam);
        return AjaxResult.success(qqchNaturalDisasterRiskPlan);
    }

    @PreAuthorize(hasPermi = "qqchNaturalDisasterRiskPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchNaturalDisasterRiskPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlanParam){
        qqchNaturalDisasterRiskPlanService.insertQqchNaturalDisasterRiskPlan(qqchNaturalDisasterRiskPlanParam);
        return AjaxResult.success(qqchNaturalDisasterRiskPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchNaturalDisasterRiskPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchNaturalDisasterRiskPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlanParam){
        return toAjax(qqchNaturalDisasterRiskPlanService.updateQqchNaturalDisasterRiskPlan(qqchNaturalDisasterRiskPlanParam));
    }

            @PreAuthorize(hasPermi = "qqchNaturalDisasterRiskPlan:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchNaturalDisasterRiskPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchNaturalDisasterRiskPlan> qqchNaturalDisasterRiskPlanListParam){
            return toAjax(qqchNaturalDisasterRiskPlanService.updateQqchNaturalDisasterRiskPlanList(qqchNaturalDisasterRiskPlanListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchNaturalDisasterRiskPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchNaturalDisasterRiskPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlanParam){
        return toAjax(qqchNaturalDisasterRiskPlanService.deleteQqchNaturalDisasterRiskPlan(qqchNaturalDisasterRiskPlanParam));
    }

            @PreAuthorize(hasPermi = "qqchNaturalDisasterRiskPlan:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchNaturalDisasterRiskPlanByPks(@PathVariable Long[] ids){
            List<Long> qqchNaturalDisasterRiskPlanPkList = Arrays.asList(ids);
            return toAjax(qqchNaturalDisasterRiskPlanService.deleteQqchNaturalDisasterRiskPlanByPks(qqchNaturalDisasterRiskPlanPkList));
        }
    

}
