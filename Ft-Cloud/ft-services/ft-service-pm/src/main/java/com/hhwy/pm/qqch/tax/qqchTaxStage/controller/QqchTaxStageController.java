package com.hhwy.pm.qqch.tax.qqchTaxStage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.tax.qqchTaxStage.domain.QqchTaxStage;
import com.hhwy.pm.qqch.tax.qqchTaxStage.dto.StageDTO;
import com.hhwy.pm.qqch.tax.qqchTaxStage.service.IQqchTaxStageService;
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
 * @date 2023-08-13 23:06:25
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchTaxStage")
public class QqchTaxStageController extends BaseController {

    @Autowired
    private IQqchTaxStageService qqchTaxStageService;


    @PreAuthorize(hasPermi = "qqchTaxStage:list")
    @GetMapping
    public AjaxResult getQqchTaxStage(@Validated(ValidationGroups.Get.class) QqchTaxStage qqchTaxStageParam) {
        QqchTaxStage qqchTaxStage = qqchTaxStageService.getQqchTaxStage(qqchTaxStageParam);
        return AjaxResult.success(qqchTaxStage);
    }

    @PreAuthorize(hasPermi = "qqchTaxStage:list")
    @GetMapping("/list")
    public AjaxResult getQqchTaxStageList(@Validated(ValidationGroups.Select.class) QqchTaxStage qqchTaxStageParam) {
        List<QqchTaxStage> qqchTaxStageList = qqchTaxStageService.getQqchTaxStageList(qqchTaxStageParam);
        return AjaxResult.success(qqchTaxStageList);
    }
    
    
    @PreAuthorize(hasPermi = "qqchTaxStage:add")
    @PostMapping("/refresh")
    public AjaxResult refresh(@Validated(ValidationGroups.Save.class) @RequestBody QqchTaxStage qqchTaxStageParam) {
        qqchTaxStageService.insertQqchTaxStage(qqchTaxStageParam);
        return AjaxResult.success(qqchTaxStageParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxStage:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxStageList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxStage> qqchTaxStageListParam) {
        qqchTaxStageService.insertQqchTaxStageList(qqchTaxStageListParam);
        return AjaxResult.success(qqchTaxStageListParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxStage:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxStage(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxStage qqchTaxStageParam) {
        return toAjax(qqchTaxStageService.updateQqchTaxStage(qqchTaxStageParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxStage:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTaxStageList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxStage> qqchTaxStageListParam) {
        return toAjax(qqchTaxStageService.updateQqchTaxStageList(qqchTaxStageListParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxStage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxStage(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxStage qqchTaxStageParam) {
        return toAjax(qqchTaxStageService.deleteQqchTaxStage(qqchTaxStageParam));
    }

    @PreAuthorize(hasPermi = "qqchTaxStage:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTaxStageByPks(@PathVariable Long[] ids) {
        List<Long> qqchTaxStagePkList = Arrays.asList(ids);
        return toAjax(qqchTaxStageService.deleteQqchTaxStageByPks(qqchTaxStagePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTaxStage qqchTaxStageParam) throws IOException {
        List<QqchTaxStage> qqchTaxStageList = qqchTaxStageService.getQqchTaxStageList(qqchTaxStageParam);
        ExcelUtils<QqchTaxStage> util = new ExcelUtils<>(QqchTaxStage.class);
        util.exportExcel(response, qqchTaxStageList, DateUtils.getDate());
    }
    
    
    
    
    
    
}
