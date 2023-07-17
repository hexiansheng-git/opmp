package com.hhwy.pm.qqch.qqchWorkPlan.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanDetailService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author hwj
 * @date 2023-07-14 17:15:57
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchWorkPlanDetail")
public class QqchWorkPlanDetailController extends BaseController {

    @Autowired
    private IQqchWorkPlanDetailService qqchWorkPlanDetailService;


    @PreAuthorize(hasPermi = "qqchWorkPlanDetail:list")
    @GetMapping
    public AjaxResult getQqchWorkPlanDetail(@Validated(ValidationGroups.Get.class) QqchWorkPlanDetail qqchWorkPlanDetailParam) {
        QqchWorkPlanDetail qqchWorkPlanDetail = qqchWorkPlanDetailService.getQqchWorkPlanDetail(qqchWorkPlanDetailParam);
        return AjaxResult.success(qqchWorkPlanDetail);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanDetail:list")
    @GetMapping("/list")
    public AjaxResult getQqchWorkPlanDetailList(@Validated(ValidationGroups.Select.class) QqchWorkPlanDetail qqchWorkPlanDetailParam) {
        startPage();
        List<QqchWorkPlanDetail> qqchWorkPlanDetailList = qqchWorkPlanDetailService.getQqchWorkPlanDetailList(qqchWorkPlanDetailParam);
        return getDataTableAjaxResult(qqchWorkPlanDetailList);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanDetail:add")
    @PostMapping("/add")
    public AjaxResult insertQqchWorkPlanDetail(@Validated(ValidationGroups.Save.class) @RequestBody QqchWorkPlanDetail qqchWorkPlanDetailParam) {
        qqchWorkPlanDetailService.insertQqchWorkPlanDetail(qqchWorkPlanDetailParam);
        return AjaxResult.success(qqchWorkPlanDetailParam);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchWorkPlanDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchWorkPlanDetail> qqchWorkPlanDetailListParam) {
        qqchWorkPlanDetailService.insertQqchWorkPlanDetailList(qqchWorkPlanDetailListParam);
        return AjaxResult.success(qqchWorkPlanDetailListParam);
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanDetail:update")
    @PostMapping("/update")
    public AjaxResult updateQqchWorkPlanDetail(@Validated(ValidationGroups.Update.class) @RequestBody QqchWorkPlanDetail qqchWorkPlanDetailParam) {
        return toAjax(qqchWorkPlanDetailService.updateQqchWorkPlanDetail(qqchWorkPlanDetailParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchWorkPlanDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchWorkPlanDetail> qqchWorkPlanDetailListParam) {
        return toAjax(qqchWorkPlanDetailService.updateQqchWorkPlanDetailList(qqchWorkPlanDetailListParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchWorkPlanDetail(@Validated(ValidationGroups.Delete.class) @RequestBody QqchWorkPlanDetail qqchWorkPlanDetailParam) {
        return toAjax(qqchWorkPlanDetailService.deleteQqchWorkPlanDetail(qqchWorkPlanDetailParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlanDetail:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchWorkPlanDetailByPks(@PathVariable Long[] ids) {
        List<Long> qqchWorkPlanDetailPkList = Arrays.asList(ids);
        return toAjax(qqchWorkPlanDetailService.deleteQqchWorkPlanDetailByPks(qqchWorkPlanDetailPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchWorkPlanDetail qqchWorkPlanDetailParam) throws IOException {
        List<QqchWorkPlanDetail> qqchWorkPlanDetailList = qqchWorkPlanDetailService.getQqchWorkPlanDetailList(qqchWorkPlanDetailParam);
        ExcelUtils<QqchWorkPlanDetail> util = new ExcelUtils<>(QqchWorkPlanDetail.class);
        util.exportExcel(response, qqchWorkPlanDetailList, DateUtils.getDate());
    }
}
