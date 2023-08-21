package com.hhwy.pm.qqch.tax.qqchTaxGlobal.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.IQqchTaxGlobalService;
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
 * @date 2023-08-17 16:19:06
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchTaxGlobal")
public class QqchTaxGlobalController extends BaseController {

    @Autowired
    private IQqchTaxGlobalService qqchTaxGlobalService;


    @PreAuthorize(hasPermi = "qqchTaxGlobal:list")
    @GetMapping
    public AjaxResult getQqchTaxGlobal(@Validated(ValidationGroups.Get.class) QqchTaxGlobal qqchTaxGlobalParam) {
        QqchTaxGlobal qqchTaxGlobal = qqchTaxGlobalService.getQqchTaxGlobal(qqchTaxGlobalParam);
        return AjaxResult.success(qqchTaxGlobal);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:list")
    @GetMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) CompileEntity<QqchTaxGlobal> dto) {
        List<QqchTaxGlobal> qqchTaxGlobalList = null;
        try {
            qqchTaxGlobalList = qqchTaxGlobalService.list(dto.dealListDto());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success(qqchTaxGlobalList);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTaxGlobal(@Validated(ValidationGroups.Save.class) @RequestBody QqchTaxGlobal qqchTaxGlobalParam) {
        qqchTaxGlobalService.insertQqchTaxGlobal(qqchTaxGlobalParam);
        return AjaxResult.success(qqchTaxGlobalParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxGlobalList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxGlobal> qqchTaxGlobalListParam) {
        qqchTaxGlobalService.insertQqchTaxGlobalList(qqchTaxGlobalListParam);
        return AjaxResult.success(qqchTaxGlobalListParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxGlobal(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxGlobal qqchTaxGlobalParam) {
        return toAjax(qqchTaxGlobalService.updateQqchTaxGlobal(qqchTaxGlobalParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTaxGlobalList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxGlobal> qqchTaxGlobalListParam) {
        return toAjax(qqchTaxGlobalService.updateQqchTaxGlobalList(qqchTaxGlobalListParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxGlobal(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxGlobal qqchTaxGlobalParam) {
        return toAjax(qqchTaxGlobalService.deleteQqchTaxGlobal(qqchTaxGlobalParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTaxGlobalByPks(@PathVariable Long[] ids) {
        List<Long> qqchTaxGlobalPkList = Arrays.asList(ids);
        return toAjax(qqchTaxGlobalService.deleteQqchTaxGlobalByPks(qqchTaxGlobalPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTaxGlobal qqchTaxGlobalParam) throws IOException {
        List<QqchTaxGlobal> qqchTaxGlobalList = qqchTaxGlobalService.getQqchTaxGlobalList(qqchTaxGlobalParam);
        ExcelUtils<QqchTaxGlobal> util = new ExcelUtils<>(QqchTaxGlobal.class);
        util.exportExcel(response, qqchTaxGlobalList, DateUtils.getDate());
    }
}
