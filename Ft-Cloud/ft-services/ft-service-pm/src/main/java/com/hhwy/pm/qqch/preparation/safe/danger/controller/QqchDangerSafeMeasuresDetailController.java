package com.hhwy.pm.qqch.preparation.safe.danger.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasuresDetail;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerSafeMeasuresDetailService;
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
 * @author zhenglili
 * @date 2023-08-17 13:32:20
 * @remark 8.3.2 危大工程安全技术措施明细
 */
@Validated
@RestController
@RequestMapping("/qqchDangerSafeMeasuresDetail")
public class QqchDangerSafeMeasuresDetailController extends BaseController {

    @Autowired
    private IQqchDangerSafeMeasuresDetailService qqchDangerSafeMeasuresDetailService;

    @PreAuthorize(hasPermi = "qqchDangerSafeMeasuresDetail:list")
    @GetMapping
    public AjaxResult getQqchDangerSafeMeasuresDetail(
        @Validated(ValidationGroups.Get.class) QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetailParam) {
        QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail = qqchDangerSafeMeasuresDetailService
            .getQqchDangerSafeMeasuresDetail(qqchDangerSafeMeasuresDetailParam);
        return AjaxResult.success(qqchDangerSafeMeasuresDetail);
    }

    @PreAuthorize(hasPermi = "qqchDangerSafeMeasuresDetail:list")
    @GetMapping("/list")
    public AjaxResult getQqchDangerSafeMeasuresDetailList(
        @Validated(ValidationGroups.Select.class) QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetailParam) {
        startPage();
        List<QqchDangerSafeMeasuresDetail> qqchDangerSafeMeasuresDetailList = qqchDangerSafeMeasuresDetailService
            .getQqchDangerSafeMeasuresDetailList(qqchDangerSafeMeasuresDetailParam);
        return getDataTableAjaxResult(qqchDangerSafeMeasuresDetailList);
    }

    @PreAuthorize(hasPermi = "qqchDangerSafeMeasuresDetail:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDangerSafeMeasuresDetail(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetailParam) {
        qqchDangerSafeMeasuresDetailService.insertQqchDangerSafeMeasuresDetail(qqchDangerSafeMeasuresDetailParam);
        return AjaxResult.success(qqchDangerSafeMeasuresDetailParam);
    }

    @PreAuthorize(hasPermi = "qqchDangerSafeMeasuresDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDangerSafeMeasuresDetailList(
        @Validated(ValidationGroups.Save.class) @RequestBody List<QqchDangerSafeMeasuresDetail> qqchDangerSafeMeasuresDetailListParam) {
        qqchDangerSafeMeasuresDetailService
            .insertQqchDangerSafeMeasuresDetailList(qqchDangerSafeMeasuresDetailListParam);
        return AjaxResult.success(qqchDangerSafeMeasuresDetailListParam);
    }

    @PreAuthorize(hasPermi = "qqchDangerSafeMeasuresDetail:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDangerSafeMeasuresDetail(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetailParam) {
        return toAjax(
            qqchDangerSafeMeasuresDetailService.updateQqchDangerSafeMeasuresDetail(qqchDangerSafeMeasuresDetailParam));
    }

    @PreAuthorize(hasPermi = "qqchDangerSafeMeasuresDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchDangerSafeMeasuresDetailList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<QqchDangerSafeMeasuresDetail> qqchDangerSafeMeasuresDetailListParam) {
        return toAjax(qqchDangerSafeMeasuresDetailService
            .updateQqchDangerSafeMeasuresDetailList(qqchDangerSafeMeasuresDetailListParam));
    }

    @PreAuthorize(hasPermi = "qqchDangerSafeMeasuresDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDangerSafeMeasuresDetail(
        @Validated(ValidationGroups.Delete.class) @RequestBody QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetailParam) {
        return toAjax(
            qqchDangerSafeMeasuresDetailService.deleteQqchDangerSafeMeasuresDetail(qqchDangerSafeMeasuresDetailParam));
    }

    @PreAuthorize(hasPermi = "qqchDangerSafeMeasuresDetail:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchDangerSafeMeasuresDetailByPks(@PathVariable Long[] ids) {
        List<Long> qqchDangerSafeMeasuresDetailPkList = Arrays.asList(ids);
        return toAjax(qqchDangerSafeMeasuresDetailService
            .deleteQqchDangerSafeMeasuresDetailByPks(qqchDangerSafeMeasuresDetailPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetailParam)
        throws IOException {
        List<QqchDangerSafeMeasuresDetail> qqchDangerSafeMeasuresDetailList = qqchDangerSafeMeasuresDetailService
            .getQqchDangerSafeMeasuresDetailList(qqchDangerSafeMeasuresDetailParam);
        ExcelUtils<QqchDangerSafeMeasuresDetail> util = new ExcelUtils<>(QqchDangerSafeMeasuresDetail.class);
        util.exportExcel(response, qqchDangerSafeMeasuresDetailList, DateUtils.getDate());
    }
}
