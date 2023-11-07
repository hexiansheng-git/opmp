package com.hhwy.pm.qqch.qqchChange.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;
import com.hhwy.pm.qqch.qqchChange.service.IQqchChangeDetailService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author wk
 * @date 2023-11-06 17:41:50
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchChangeDetail")
public class QqchChangeDetailController extends BaseController {

    @Autowired
    private IQqchChangeDetailService qqchChangeDetailService;


    @PreAuthorize(hasPermi = "qqchChangeDetail:list")
    @GetMapping
    public AjaxResult getQqchChangeDetail(@Validated(ValidationGroups.Get.class) QqchChangeDetail qqchChangeDetailParam) {
        QqchChangeDetail qqchChangeDetail = qqchChangeDetailService.getQqchChangeDetail(qqchChangeDetailParam);
        return AjaxResult.success(qqchChangeDetail);
    }

    @PreAuthorize(hasPermi = "qqchChangeDetail:list")
    @GetMapping("/list")
    public AjaxResult getQqchChangeDetailList(@Validated(ValidationGroups.Select.class) QqchChangeDetail qqchChangeDetailParam) {
        startPage();
        List<QqchChangeDetail> qqchChangeDetailList = qqchChangeDetailService.getQqchChangeDetailList(qqchChangeDetailParam);
        return getDataTableAjaxResult(qqchChangeDetailList);
    }

    @PreAuthorize(hasPermi = "qqchChangeDetail:add")
    @PostMapping("/add")
    public AjaxResult insertQqchChangeDetail(@Validated(ValidationGroups.Save.class) @RequestBody QqchChangeDetail qqchChangeDetailParam) {
        qqchChangeDetailService.insertQqchChangeDetail(qqchChangeDetailParam);
        return AjaxResult.success(qqchChangeDetailParam);
    }

    @PreAuthorize(hasPermi = "qqchChangeDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchChangeDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchChangeDetail> qqchChangeDetailListParam) {
        qqchChangeDetailService.insertQqchChangeDetailList(qqchChangeDetailListParam);
        return AjaxResult.success(qqchChangeDetailListParam);
    }

    @PreAuthorize(hasPermi = "qqchChangeDetail:update")
    @PostMapping("/update")
    public AjaxResult updateQqchChangeDetail(@Validated(ValidationGroups.Update.class) @RequestBody QqchChangeDetail qqchChangeDetailParam) {
        return toAjax(qqchChangeDetailService.updateQqchChangeDetail(qqchChangeDetailParam));
    }

    @PreAuthorize(hasPermi = "qqchChangeDetail:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchChangeDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchChangeDetail> qqchChangeDetailListParam) {
        return toAjax(qqchChangeDetailService.updateQqchChangeDetailList(qqchChangeDetailListParam));
    }

    @PreAuthorize(hasPermi = "qqchChangeDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchChangeDetail(@Validated(ValidationGroups.Delete.class) @RequestBody QqchChangeDetail qqchChangeDetailParam) {
        return toAjax(qqchChangeDetailService.deleteQqchChangeDetail(qqchChangeDetailParam));
    }

    @PreAuthorize(hasPermi = "qqchChangeDetail:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchChangeDetailByPks(@PathVariable Long[] ids) {
        List<Long> qqchChangeDetailPkList = Arrays.asList(ids);
        return toAjax(qqchChangeDetailService.deleteQqchChangeDetailByPks(qqchChangeDetailPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchChangeDetail qqchChangeDetailParam) throws IOException {
        List<QqchChangeDetail> qqchChangeDetailList = qqchChangeDetailService.getQqchChangeDetailList(qqchChangeDetailParam);
        ExcelUtils<QqchChangeDetail> util = new ExcelUtils<>(QqchChangeDetail.class);
        util.exportExcel(response, qqchChangeDetailList, DateUtils.getDate());
    }
}
