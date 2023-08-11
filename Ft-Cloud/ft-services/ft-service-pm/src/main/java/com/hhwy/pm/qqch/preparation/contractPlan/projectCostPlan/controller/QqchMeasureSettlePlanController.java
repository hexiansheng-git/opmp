package com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.QqchMeasureSettlePlan;
import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.vo.QqchMeasureSettlePlanVo;
import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.service.IQqchMeasureSettlePlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:45:52
 * @remark 计量结算策划
 */
@Validated
@RestController
@RequestMapping("/qqchMeasureSettlePlan")
public class QqchMeasureSettlePlanController extends BaseController {

    @Autowired
    private IQqchMeasureSettlePlanService qqchMeasureSettlePlanService;


    @PreAuthorize(hasPermi = "qqchMeasureSettlePlan:list")
    @GetMapping
    public AjaxResult getQqchMeasureSettlePlan(@Validated(ValidationGroups.Get.class) QqchMeasureSettlePlan qqchMeasureSettlePlanParam) {
        QqchMeasureSettlePlan qqchMeasureSettlePlan = qqchMeasureSettlePlanService.getQqchMeasureSettlePlan(qqchMeasureSettlePlanParam);
        return AjaxResult.success(qqchMeasureSettlePlan);
    }

    @PreAuthorize(hasPermi = "qqchMeasureSettlePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchMeasureSettlePlanList(@Validated(ValidationGroups.Select.class) QqchMeasureSettlePlan qqchMeasureSettlePlanParam) {
        startPage();
        List<QqchMeasureSettlePlan> qqchMeasureSettlePlanList = qqchMeasureSettlePlanService.getQqchMeasureSettlePlanList(qqchMeasureSettlePlanParam);
        return getDataTableAjaxResult(qqchMeasureSettlePlanList);
    }

    @PreAuthorize(hasPermi = "qqchMeasureSettlePlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMeasureSettlePlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureSettlePlan qqchMeasureSettlePlanParam) {
        qqchMeasureSettlePlanService.insertQqchMeasureSettlePlan(qqchMeasureSettlePlanParam);
        return AjaxResult.success(qqchMeasureSettlePlanParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureSettlePlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMeasureSettlePlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchMeasureSettlePlan qqchMeasureSettlePlanParam) {
        return toAjax(qqchMeasureSettlePlanService.updateQqchMeasureSettlePlan(qqchMeasureSettlePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchMeasureSettlePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchMeasureSettlePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMeasureSettlePlan> qqchMeasureSettlePlanListParam) {
        return toAjax(qqchMeasureSettlePlanService.updateQqchMeasureSettlePlanList(qqchMeasureSettlePlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchMeasureSettlePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMeasureSettlePlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMeasureSettlePlan qqchMeasureSettlePlanParam) {
        return toAjax(qqchMeasureSettlePlanService.deleteQqchMeasureSettlePlan(qqchMeasureSettlePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchMeasureSettlePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchMeasureSettlePlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchMeasureSettlePlanPkList = Arrays.asList(ids);
        return toAjax(qqchMeasureSettlePlanService.deleteQqchMeasureSettlePlanByPks(qqchMeasureSettlePlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMeasureSettlePlan qqchMeasureSettlePlanParam) throws IOException {
        List<QqchMeasureSettlePlan> qqchMeasureSettlePlanList = qqchMeasureSettlePlanService.getQqchMeasureSettlePlanList(qqchMeasureSettlePlanParam);
        ExcelUtils<QqchMeasureSettlePlan> util = new ExcelUtils<>(QqchMeasureSettlePlan.class);
        util.exportExcel(response, qqchMeasureSettlePlanList, DateUtils.getDate());
    }

    /**
     * 获取计量结算策划Vo
     * @param qqchMeasureSettlePlan
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasureSettlePlan:list")
    @GetMapping("getQqchMeasureSettlePlanVo")
    public AjaxResult getQqchMeasureSettlePlanVo(@Validated(ValidationGroups.Get.class) QqchMeasureSettlePlan qqchMeasureSettlePlan) {
        QqchMeasureSettlePlanVo qqchMeasureSettlePlanVo = qqchMeasureSettlePlanService.getQqchMeasureSettlePlanVo(qqchMeasureSettlePlan);
        return AjaxResult.success(qqchMeasureSettlePlanVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchMeasureSettlePlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasureSettlePlan:add")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody QqchMeasureSettlePlanVo qqchMeasureSettlePlanVo) {
        qqchMeasureSettlePlanService.save(qqchMeasureSettlePlanVo);
        return AjaxResult.success();
    }
}
