package com.hhwy.pm.qqch.preparation.contractPlan.currencyUsePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.contractPlan.currencyUsePlan.domain.QqchDelayProjectPayPlan;
import com.hhwy.pm.qqch.preparation.contractPlan.currencyUsePlan.domain.vo.QqchDelayProjectPayPlanVo;
import com.hhwy.pm.qqch.preparation.contractPlan.currencyUsePlan.service.IQqchDelayProjectPayPlanService;
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
 * @date 2023-08-08 17:09:45
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchDelayProjectPayPlan")
public class QqchDelayProjectPayPlanController extends BaseController {

    @Autowired
    private IQqchDelayProjectPayPlanService qqchDelayProjectPayPlanService;


    @PreAuthorize(hasPermi = "qqchDelayProjectPayPlan:list")
    @GetMapping
    public AjaxResult getQqchDelayProjectPayPlan(@Validated(ValidationGroups.Get.class) QqchDelayProjectPayPlan qqchDelayProjectPayPlanParam) {
        QqchDelayProjectPayPlan qqchDelayProjectPayPlan = qqchDelayProjectPayPlanService.getQqchDelayProjectPayPlan(qqchDelayProjectPayPlanParam);
        return AjaxResult.success(qqchDelayProjectPayPlan);
    }

    @PreAuthorize(hasPermi = "qqchDelayProjectPayPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchDelayProjectPayPlanList(@Validated(ValidationGroups.Select.class) QqchDelayProjectPayPlan qqchDelayProjectPayPlanParam) {
        startPage();
        List<QqchDelayProjectPayPlan> qqchDelayProjectPayPlanList = qqchDelayProjectPayPlanService.getQqchDelayProjectPayPlanList(qqchDelayProjectPayPlanParam);
        return getDataTableAjaxResult(qqchDelayProjectPayPlanList);
    }

    @PreAuthorize(hasPermi = "qqchDelayProjectPayPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDelayProjectPayPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchDelayProjectPayPlan qqchDelayProjectPayPlanParam) {
        qqchDelayProjectPayPlanService.insertQqchDelayProjectPayPlan(qqchDelayProjectPayPlanParam);
        return AjaxResult.success(qqchDelayProjectPayPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchDelayProjectPayPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDelayProjectPayPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchDelayProjectPayPlan qqchDelayProjectPayPlanParam) {
        return toAjax(qqchDelayProjectPayPlanService.updateQqchDelayProjectPayPlan(qqchDelayProjectPayPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchDelayProjectPayPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchDelayProjectPayPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchDelayProjectPayPlan> qqchDelayProjectPayPlanListParam) {
        return toAjax(qqchDelayProjectPayPlanService.updateQqchDelayProjectPayPlanList(qqchDelayProjectPayPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchDelayProjectPayPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDelayProjectPayPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchDelayProjectPayPlan qqchDelayProjectPayPlanParam) {
        return toAjax(qqchDelayProjectPayPlanService.deleteQqchDelayProjectPayPlan(qqchDelayProjectPayPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchDelayProjectPayPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchDelayProjectPayPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchDelayProjectPayPlanPkList = Arrays.asList(ids);
        return toAjax(qqchDelayProjectPayPlanService.deleteQqchDelayProjectPayPlanByPks(qqchDelayProjectPayPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDelayProjectPayPlan qqchDelayProjectPayPlanParam) throws IOException {
        List<QqchDelayProjectPayPlan> qqchDelayProjectPayPlanList = qqchDelayProjectPayPlanService.getQqchDelayProjectPayPlanList(qqchDelayProjectPayPlanParam);
        ExcelUtils<QqchDelayProjectPayPlan> util = new ExcelUtils<>(QqchDelayProjectPayPlan.class);
        util.exportExcel(response, qqchDelayProjectPayPlanList, DateUtils.getDate());
    }

    /**
     * 获取Vo
     * @param qqchDelayProjectPayPlan
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDelayProjectPayPlan:list")
    @GetMapping("getQqchDelayProjectPayPlanVo")
    public AjaxResult getQqchDelayProjectPayPlanVo(@Validated(ValidationGroups.Get.class) QqchDelayProjectPayPlan qqchDelayProjectPayPlan) {
        QqchDelayProjectPayPlanVo qqchDelayProjectPayPlanVo = qqchDelayProjectPayPlanService.getQqchDelayProjectPayPlanVo(qqchDelayProjectPayPlan);
        return AjaxResult.success(qqchDelayProjectPayPlanVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchDelayProjectPayPlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDelayProjectPayPlan:add")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchDelayProjectPayPlanVo qqchDelayProjectPayPlanVo) {
        qqchDelayProjectPayPlanService.save(qqchDelayProjectPayPlanVo);
        return AjaxResult.success();
    }
}
