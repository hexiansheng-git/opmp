package com.hhwy.pm.qqch.preparation.safe.risk.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.risk.service.IQqchSafeRiskListDetailService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
//import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author zq
 * @date 2023-08-11 13:41:38
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchSafeRiskListDetail")
public class QqchSafeRiskListDetailController extends BaseController {

    @Autowired
    private IQqchSafeRiskListDetailService qqchSafeRiskListDetailService;


//    @PreAuthorize(hasPermi = "qqchSafeRiskListDetail:list")
    @GetMapping
    public AjaxResult getQqchSafeRiskListDetail(@Validated(ValidationGroups.Get.class) QqchSafeRiskListDetail qqchSafeRiskListDetailParam) {
        QqchSafeRiskListDetail qqchSafeRiskListDetail = qqchSafeRiskListDetailService.getQqchSafeRiskListDetail(qqchSafeRiskListDetailParam);
        return AjaxResult.success(qqchSafeRiskListDetail);
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskListDetail:list")
    @GetMapping("/list")
    public AjaxResult getQqchSafeRiskListDetailList(@Validated(ValidationGroups.Select.class) QqchSafeRiskListDetail qqchSafeRiskListDetailParam) {
        startPage();
        List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList = qqchSafeRiskListDetailService.getQqchSafeRiskListDetailList(qqchSafeRiskListDetailParam);
        return getDataTableAjaxResult(qqchSafeRiskListDetailList);
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskListDetail:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSafeRiskListDetail(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeRiskListDetail qqchSafeRiskListDetailParam) {
        qqchSafeRiskListDetailService.insertQqchSafeRiskListDetail(qqchSafeRiskListDetailParam);
        return AjaxResult.success(qqchSafeRiskListDetailParam);
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskListDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSafeRiskListDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSafeRiskListDetail> qqchSafeRiskListDetailListParam) {
        qqchSafeRiskListDetailService.insertQqchSafeRiskListDetailList(qqchSafeRiskListDetailListParam);
        return AjaxResult.success(qqchSafeRiskListDetailListParam);
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskListDetail:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSafeRiskListDetail(@Validated(ValidationGroups.Update.class) @RequestBody QqchSafeRiskListDetail qqchSafeRiskListDetailParam) {
        return toAjax(qqchSafeRiskListDetailService.updateQqchSafeRiskListDetail(qqchSafeRiskListDetailParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskListDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSafeRiskListDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSafeRiskListDetail> qqchSafeRiskListDetailListParam) {
        return toAjax(qqchSafeRiskListDetailService.updateQqchSafeRiskListDetailList(qqchSafeRiskListDetailListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskListDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSafeRiskListDetail(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSafeRiskListDetail qqchSafeRiskListDetailParam) {
        return toAjax(qqchSafeRiskListDetailService.deleteQqchSafeRiskListDetail(qqchSafeRiskListDetailParam));
    }

//    @PreAuthorize(hasPermi = "qqchSafeRiskListDetail:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSafeRiskListDetailByPks(@PathVariable Long[] ids) {
        List<Long> qqchSafeRiskListDetailPkList = Arrays.asList(ids);
        return toAjax(qqchSafeRiskListDetailService.deleteQqchSafeRiskListDetailByPks(qqchSafeRiskListDetailPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSafeRiskListDetail qqchSafeRiskListDetailParam) throws IOException {
        List<QqchSafeRiskListDetail> qqchSafeRiskListDetailList = qqchSafeRiskListDetailService.getQqchSafeRiskListDetailList(qqchSafeRiskListDetailParam);
        ExcelUtils<QqchSafeRiskListDetail> util = new ExcelUtils<>(QqchSafeRiskListDetail.class);
        util.exportExcel(response, qqchSafeRiskListDetailList, DateUtils.getDate());
    }
}
