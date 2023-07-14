package com.hhwy.pm.qqch.preparation.survey.document.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchManageProcedure;
import com.hhwy.pm.qqch.preparation.survey.document.service.IQqchManageProcedureService;
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
 * @author han
 * @date 2023-07-13 11:40:23
 * @remark 管理程序
 */
@Validated
@RestController
@RequestMapping("/qqchManageProcedure")
public class QqchManageProcedureController extends BaseController {

    @Autowired
    private IQqchManageProcedureService qqchManageProcedureService;


    @PreAuthorize(hasPermi = "qqchManageProcedure:list")
    @GetMapping
    public AjaxResult getQqchManageProcedure(@Validated(ValidationGroups.Get.class) QqchManageProcedure qqchManageProcedureParam) {
        QqchManageProcedure qqchManageProcedure = qqchManageProcedureService.getQqchManageProcedure(qqchManageProcedureParam);
        return AjaxResult.success(qqchManageProcedure);
    }

    @PreAuthorize(hasPermi = "qqchManageProcedure:list")
    @GetMapping("/list")
    public AjaxResult getQqchManageProcedureList(@Validated(ValidationGroups.Select.class) QqchManageProcedure qqchManageProcedureParam) {
        startPage();
        List<QqchManageProcedure> qqchManageProcedureList = qqchManageProcedureService.getQqchManageProcedureList(qqchManageProcedureParam);
        return getDataTableAjaxResult(qqchManageProcedureList);
    }

    @PreAuthorize(hasPermi = "qqchManageProcedure:add")
    @PostMapping("/add")
    public AjaxResult insertQqchManageProcedure(@Validated(ValidationGroups.Save.class) @RequestBody QqchManageProcedure qqchManageProcedureParam) {
        qqchManageProcedureService.insertQqchManageProcedure(qqchManageProcedureParam);
        return AjaxResult.success(qqchManageProcedureParam);
    }

    @PreAuthorize(hasPermi = "qqchManageProcedure:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchManageProcedureList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchManageProcedure> qqchManageProcedureListParam) {
        qqchManageProcedureService.insertQqchManageProcedureList(qqchManageProcedureListParam);
        return AjaxResult.success(qqchManageProcedureListParam);
    }

    @PreAuthorize(hasPermi = "qqchManageProcedure:update")
    @PostMapping("/update")
    public AjaxResult updateQqchManageProcedure(@Validated(ValidationGroups.Update.class) @RequestBody QqchManageProcedure qqchManageProcedureParam) {
        return toAjax(qqchManageProcedureService.updateQqchManageProcedure(qqchManageProcedureParam));
    }

    @PreAuthorize(hasPermi = "qqchManageProcedure:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchManageProcedureList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchManageProcedure> qqchManageProcedureListParam) {
        return toAjax(qqchManageProcedureService.updateQqchManageProcedureList(qqchManageProcedureListParam));
    }

    @PreAuthorize(hasPermi = "qqchManageProcedure:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchManageProcedure(@Validated(ValidationGroups.Delete.class) @RequestBody QqchManageProcedure qqchManageProcedureParam) {
        return toAjax(qqchManageProcedureService.deleteQqchManageProcedure(qqchManageProcedureParam));
    }

    @PreAuthorize(hasPermi = "qqchManageProcedure:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchManageProcedureByPks(@PathVariable Long[] ids) {
        List<Long> qqchManageProcedurePkList = Arrays.asList(ids);
        return toAjax(qqchManageProcedureService.deleteQqchManageProcedureByPks(qqchManageProcedurePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchManageProcedure qqchManageProcedureParam) throws IOException {
        List<QqchManageProcedure> qqchManageProcedureList = qqchManageProcedureService.getQqchManageProcedureList(qqchManageProcedureParam);
        ExcelUtils<QqchManageProcedure> util = new ExcelUtils<>(QqchManageProcedure.class);
        util.exportExcel(response, qqchManageProcedureList, DateUtils.getDate());
    }
}
