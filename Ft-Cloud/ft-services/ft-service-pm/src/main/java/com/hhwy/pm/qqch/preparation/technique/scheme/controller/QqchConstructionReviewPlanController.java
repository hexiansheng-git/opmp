package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionReviewPlan;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionReviewPlanVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionReviewPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenlili
 * @date 2023-07-17 15:32:17
 * @remark 3.4.5施工方案编审计划
 */
@Validated
@RestController
@RequestMapping("/qqchConstructionReviewPlan")
public class QqchConstructionReviewPlanController extends BaseController {

    @Autowired
    private IQqchConstructionReviewPlanService qqchConstructionReviewPlanService;

    @PreAuthorize(hasPermi = "qqchConstructionReviewPlan:list")
    @GetMapping
    public AjaxResult getQqchConstructionReviewPlan(
        @Validated(ValidationGroups.Get.class) QqchConstructionReviewPlan qqchConstructionReviewPlanParam) {
        QqchConstructionReviewPlan qqchConstructionReviewPlan = qqchConstructionReviewPlanService
            .getQqchConstructionReviewPlan(qqchConstructionReviewPlanParam);
        return AjaxResult.success(qqchConstructionReviewPlan);
    }

    @PreAuthorize(hasPermi = "qqchConstructionReviewPlan:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList() {
        QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo = qqchConstructionReviewPlanService
            .getQqchConstructionReviewPlanList();
        return AjaxResult.success(qqchConstructionReviewPlanVo);
    }

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:add")
    @PostMapping("/syncData")
    public AjaxResult syncData() {
        qqchConstructionReviewPlanService.syncData();
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchConstructionReviewPlan:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo) {
        qqchConstructionReviewPlanService.batchSave(qqchConstructionReviewPlanVo);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchConstructionReviewPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchConstructionReviewPlan(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchConstructionReviewPlan qqchConstructionReviewPlanParam) {
        return toAjax(
            qqchConstructionReviewPlanService.updateQqchConstructionReviewPlan(qqchConstructionReviewPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchConstructionReviewPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchConstructionReviewPlanList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<QqchConstructionReviewPlan> qqchConstructionReviewPlanListParam) {
        return toAjax(qqchConstructionReviewPlanService
            .updateQqchConstructionReviewPlanList(qqchConstructionReviewPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchConstructionReviewPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchConstructionReviewPlan(
        @Validated(ValidationGroups.Delete.class) @RequestBody QqchConstructionReviewPlan qqchConstructionReviewPlanParam) {
        return toAjax(
            qqchConstructionReviewPlanService.deleteQqchConstructionReviewPlan(qqchConstructionReviewPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchConstructionReviewPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchConstructionReviewPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchConstructionReviewPlanPkList = Arrays.asList(ids);
        return toAjax(
            qqchConstructionReviewPlanService.deleteQqchConstructionReviewPlanByPks(qqchConstructionReviewPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response)
        throws IOException {
        QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo = qqchConstructionReviewPlanService
            .getQqchConstructionReviewPlanList();
        ExcelUtils<QqchConstructionReviewPlan> util = new ExcelUtils<>(QqchConstructionReviewPlan.class);
        util.exportExcel(response, qqchConstructionReviewPlanVo.getTreeList(), DateUtils.getDate());
    }
}
