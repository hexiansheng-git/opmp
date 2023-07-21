package com.hhwy.pm.qqch.preparation.technique.disclose.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThirdDetail;
import com.hhwy.pm.qqch.preparation.technique.disclose.service.IQqchDiscloseThirdDetailService;
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
 * @date 2023-07-21 14:26:47
 * @remark 3.5.2三级交底详情
 */
@Validated
@RestController
@RequestMapping("/qqchDiscloseThirdDetail")
public class QqchDiscloseThirdDetailController extends BaseController {

    @Autowired
    private IQqchDiscloseThirdDetailService qqchDiscloseThirdDetailService;

    @PreAuthorize(hasPermi = "qqchDiscloseThirdDetail:list")
    @GetMapping
    public AjaxResult getQqchDiscloseThirdDetail(
        @Validated(ValidationGroups.Get.class) QqchDiscloseThirdDetail qqchDiscloseThirdDetailParam) {
        QqchDiscloseThirdDetail qqchDiscloseThirdDetail = qqchDiscloseThirdDetailService
            .getQqchDiscloseThirdDetail(qqchDiscloseThirdDetailParam);
        return AjaxResult.success(qqchDiscloseThirdDetail);
    }

    @PreAuthorize(hasPermi = "qqchDiscloseThirdDetail:list")
    @GetMapping("/list")
    public AjaxResult getQqchDiscloseThirdDetailList(
        @Validated(ValidationGroups.Select.class) QqchDiscloseThirdDetail qqchDiscloseThirdDetailParam) {
        startPage();
        List<QqchDiscloseThirdDetail> qqchDiscloseThirdDetailList = qqchDiscloseThirdDetailService
            .getQqchDiscloseThirdDetailList(qqchDiscloseThirdDetailParam);
        return getDataTableAjaxResult(qqchDiscloseThirdDetailList);
    }

    @PreAuthorize(hasPermi = "qqchDiscloseThirdDetail:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDiscloseThirdDetail(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchDiscloseThirdDetail qqchDiscloseThirdDetailParam) {
        qqchDiscloseThirdDetailService.insertQqchDiscloseThirdDetail(qqchDiscloseThirdDetailParam);
        return AjaxResult.success(qqchDiscloseThirdDetailParam);
    }

    @PreAuthorize(hasPermi = "qqchDiscloseThirdDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDiscloseThirdDetailList(
        @Validated(ValidationGroups.Save.class) @RequestBody List<QqchDiscloseThirdDetail> qqchDiscloseThirdDetailListParam) {
        qqchDiscloseThirdDetailService.insertQqchDiscloseThirdDetailList(qqchDiscloseThirdDetailListParam);
        return AjaxResult.success(qqchDiscloseThirdDetailListParam);
    }

    @PreAuthorize(hasPermi = "qqchDiscloseThirdDetail:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDiscloseThirdDetail(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchDiscloseThirdDetail qqchDiscloseThirdDetailParam) {
        return toAjax(qqchDiscloseThirdDetailService.updateQqchDiscloseThirdDetail(qqchDiscloseThirdDetailParam));
    }

    @PreAuthorize(hasPermi = "qqchDiscloseThirdDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchDiscloseThirdDetailList(
        @Validated(ValidationGroups.Update.class) @RequestBody List<QqchDiscloseThirdDetail> qqchDiscloseThirdDetailListParam) {
        return toAjax(
            qqchDiscloseThirdDetailService.updateQqchDiscloseThirdDetailList(qqchDiscloseThirdDetailListParam));
    }

    @PreAuthorize(hasPermi = "qqchDiscloseThirdDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDiscloseThirdDetail(
        @Validated(ValidationGroups.Delete.class) @RequestBody QqchDiscloseThirdDetail qqchDiscloseThirdDetailParam) {
        return toAjax(qqchDiscloseThirdDetailService.deleteQqchDiscloseThirdDetail(qqchDiscloseThirdDetailParam));
    }

    @PreAuthorize(hasPermi = "qqchDiscloseThirdDetail:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchDiscloseThirdDetailByPks(@PathVariable Long[] ids) {
        List<Long> qqchDiscloseThirdDetailPkList = Arrays.asList(ids);
        return toAjax(qqchDiscloseThirdDetailService.deleteQqchDiscloseThirdDetailByPks(qqchDiscloseThirdDetailPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDiscloseThirdDetail qqchDiscloseThirdDetailParam)
        throws IOException {
        List<QqchDiscloseThirdDetail> qqchDiscloseThirdDetailList = qqchDiscloseThirdDetailService
            .getQqchDiscloseThirdDetailList(qqchDiscloseThirdDetailParam);
        ExcelUtils<QqchDiscloseThirdDetail> util = new ExcelUtils<>(QqchDiscloseThirdDetail.class);
        util.exportExcel(response, qqchDiscloseThirdDetailList, DateUtils.getDate());
    }
}
