package com.hhwy.pm.qqch.tax.qqchTaxCostDetail.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.tax.qqchTaxCostDetail.domain.QqchTaxCostDetail;
import com.hhwy.pm.qqch.tax.qqchTaxCostDetail.service.IQqchTaxCostDetailService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:26
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchTaxCostDetail")
public class QqchTaxCostDetailController extends BaseController {

    @Autowired
    private IQqchTaxCostDetailService qqchTaxCostDetailService;


    @PreAuthorize(hasPermi = "qqchTaxCostDetail:list")
    @GetMapping
    public AjaxResult getQqchTaxCostDetail(@Validated(ValidationGroups.Get.class) QqchTaxCostDetail qqchTaxCostDetailParam) {
        QqchTaxCostDetail qqchTaxCostDetail = qqchTaxCostDetailService.getQqchTaxCostDetail(qqchTaxCostDetailParam);
        return AjaxResult.success(qqchTaxCostDetail);
    }

    @PreAuthorize(hasPermi = "qqchTaxCostDetail:list")
    @GetMapping("/list")
    public AjaxResult getQqchTaxCostDetailList(@Validated(ValidationGroups.Select.class) QqchTaxCostDetail qqchTaxCostDetailParam) {
        startPage();
        List<QqchTaxCostDetail> qqchTaxCostDetailList = qqchTaxCostDetailService.getQqchTaxCostDetailList(qqchTaxCostDetailParam);
        return getDataTableAjaxResult(qqchTaxCostDetailList);
    }

    @PreAuthorize(hasPermi = "qqchTaxCostDetail:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTaxCostDetail(@Validated(ValidationGroups.Save.class) @RequestBody QqchTaxCostDetail qqchTaxCostDetailParam) {
        qqchTaxCostDetailService.insertQqchTaxCostDetail(qqchTaxCostDetailParam);
        return AjaxResult.success(qqchTaxCostDetailParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxCostDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxCostDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxCostDetail> qqchTaxCostDetailListParam) {
        qqchTaxCostDetailService.insertQqchTaxCostDetailList(qqchTaxCostDetailListParam);
        return AjaxResult.success(qqchTaxCostDetailListParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxCostDetail:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxCostDetail(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxCostDetail qqchTaxCostDetailParam) {
        return toAjax(qqchTaxCostDetailService.updateQqchTaxCostDetail(qqchTaxCostDetailParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxCostDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTaxCostDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxCostDetail> qqchTaxCostDetailListParam) {
        return toAjax(qqchTaxCostDetailService.updateQqchTaxCostDetailList(qqchTaxCostDetailListParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxCostDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxCostDetail(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxCostDetail qqchTaxCostDetailParam) {
        return toAjax(qqchTaxCostDetailService.deleteQqchTaxCostDetail(qqchTaxCostDetailParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxCostDetail:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTaxCostDetailByPks(@PathVariable Long[] ids) {
        List<Long> qqchTaxCostDetailPkList = Arrays.asList(ids);
        return toAjax(qqchTaxCostDetailService.deleteQqchTaxCostDetailByPks(qqchTaxCostDetailPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTaxCostDetail qqchTaxCostDetailParam) throws IOException {
        List<QqchTaxCostDetail> qqchTaxCostDetailList = qqchTaxCostDetailService.getQqchTaxCostDetailList(qqchTaxCostDetailParam);
        ExcelUtils<QqchTaxCostDetail> util = new ExcelUtils<>(QqchTaxCostDetail.class);
        util.exportExcel(response, qqchTaxCostDetailList, DateUtils.getDate());
    }
}
