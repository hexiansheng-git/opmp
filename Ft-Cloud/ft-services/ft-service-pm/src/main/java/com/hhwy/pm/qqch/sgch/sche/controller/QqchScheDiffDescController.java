package com.hhwy.pm.qqch.sgch.sche.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiffDesc;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheDiffDescService;
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
 * @date 2023-07-31 11:22:50
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchScheDiffDesc")
public class QqchScheDiffDescController extends BaseController {

    @Autowired
    private IQqchScheDiffDescService qqchScheDiffDescService;


    @PreAuthorize(hasPermi = "qqchScheDiffDesc:list")
    @GetMapping
    public AjaxResult getQqchScheDiffDesc(@Validated(ValidationGroups.Get.class) QqchScheDiffDesc qqchScheDiffDescParam) {
        QqchScheDiffDesc qqchScheDiffDesc = qqchScheDiffDescService.getQqchScheDiffDesc(qqchScheDiffDescParam);
        return AjaxResult.success(qqchScheDiffDesc);
    }

    @PreAuthorize(hasPermi = "qqchScheDiffDesc:list")
    @GetMapping("/list")
    public AjaxResult getQqchScheDiffDescList(@Validated(ValidationGroups.Select.class) QqchScheDiffDesc qqchScheDiffDescParam) {
        startPage();
        List<QqchScheDiffDesc> qqchScheDiffDescList = qqchScheDiffDescService.getQqchScheDiffDescList(qqchScheDiffDescParam);
        return getDataTableAjaxResult(qqchScheDiffDescList);
    }

    @PreAuthorize(hasPermi = "qqchScheDiffDesc:add")
    @PostMapping("/add")
    public AjaxResult insertQqchScheDiffDesc(@Validated(ValidationGroups.Save.class) @RequestBody QqchScheDiffDesc qqchScheDiffDescParam) {
        qqchScheDiffDescService.insertQqchScheDiffDesc(qqchScheDiffDescParam);
        return AjaxResult.success(qqchScheDiffDescParam);
    }

    @PreAuthorize(hasPermi = "qqchScheDiffDesc:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchScheDiffDescList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchScheDiffDesc> qqchScheDiffDescListParam) {
        qqchScheDiffDescService.insertQqchScheDiffDescList(qqchScheDiffDescListParam);
        return AjaxResult.success(qqchScheDiffDescListParam);
    }

    @PreAuthorize(hasPermi = "qqchScheDiffDesc:update")
    @PostMapping("/update")
    public AjaxResult updateQqchScheDiffDesc(@Validated(ValidationGroups.Update.class) @RequestBody QqchScheDiffDesc qqchScheDiffDescParam) {
        return toAjax(qqchScheDiffDescService.updateQqchScheDiffDesc(qqchScheDiffDescParam));
    }

    @PreAuthorize(hasPermi = "qqchScheDiffDesc:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchScheDiffDescList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchScheDiffDesc> qqchScheDiffDescListParam) {
        return toAjax(qqchScheDiffDescService.updateQqchScheDiffDescList(qqchScheDiffDescListParam));
    }

    @PreAuthorize(hasPermi = "qqchScheDiffDesc:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchScheDiffDesc(@Validated(ValidationGroups.Delete.class) @RequestBody QqchScheDiffDesc qqchScheDiffDescParam) {
        return toAjax(qqchScheDiffDescService.deleteQqchScheDiffDesc(qqchScheDiffDescParam));
    }

    @PreAuthorize(hasPermi = "qqchScheDiffDesc:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchScheDiffDescByPks(@PathVariable Long[] ids) {
        List<Long> qqchScheDiffDescPkList = Arrays.asList(ids);
        return toAjax(qqchScheDiffDescService.deleteQqchScheDiffDescByPks(qqchScheDiffDescPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchScheDiffDesc qqchScheDiffDescParam) throws IOException {
        List<QqchScheDiffDesc> qqchScheDiffDescList = qqchScheDiffDescService.getQqchScheDiffDescList(qqchScheDiffDescParam);
        ExcelUtils<QqchScheDiffDesc> util = new ExcelUtils<>(QqchScheDiffDesc.class);
        util.exportExcel(response, qqchScheDiffDescList, DateUtils.getDate());
    }
}
