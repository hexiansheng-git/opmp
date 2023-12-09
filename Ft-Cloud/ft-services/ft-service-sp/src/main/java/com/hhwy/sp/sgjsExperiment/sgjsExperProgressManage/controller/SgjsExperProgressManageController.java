package com.hhwy.sp.sgjsExperiment.sgjsExperProgressManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.sgjsExperiment.sgjsExperProgressManage.domain.SgjsExperProgressManage;
import com.hhwy.sp.sgjsExperiment.sgjsExperProgressManage.service.ISgjsExperProgressManageService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author wll
 * @date 2023-12-09 11:06:27
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsExperProgressManage")
public class SgjsExperProgressManageController extends BaseController {

    @Autowired
    private ISgjsExperProgressManageService sgjsExperProgressManageService;


    @PreAuthorize(hasPermi = "sgjsExperProgressManage:list")
    @GetMapping
    public AjaxResult getSgjsExperProgressManage(@Validated(ValidationGroups.Get.class) SgjsExperProgressManage sgjsExperProgressManageParam) {
        SgjsExperProgressManage sgjsExperProgressManage = sgjsExperProgressManageService.getSgjsExperProgressManage(sgjsExperProgressManageParam);
        return AjaxResult.success(sgjsExperProgressManage);
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:list")
    @GetMapping("/list")
    public AjaxResult getSgjsExperProgressManageList(@Validated(ValidationGroups.Select.class) SgjsExperProgressManage sgjsExperProgressManageParam) {
        startPage();
        List<SgjsExperProgressManage> sgjsExperProgressManageList = sgjsExperProgressManageService.getSgjsExperProgressManageList(sgjsExperProgressManageParam);
        return getDataTableAjaxResult(sgjsExperProgressManageList);
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsExperProgressManage(@Validated(ValidationGroups.Save.class) @RequestBody SgjsExperProgressManage sgjsExperProgressManageParam) {
        sgjsExperProgressManageService.insertSgjsExperProgressManage(sgjsExperProgressManageParam);
        return AjaxResult.success(sgjsExperProgressManageParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsExperProgressManageList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsExperProgressManage> sgjsExperProgressManageListParam) {
        sgjsExperProgressManageService.insertSgjsExperProgressManageList(sgjsExperProgressManageListParam);
        return AjaxResult.success(sgjsExperProgressManageListParam);
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsExperProgressManage(@Validated(ValidationGroups.Update.class) @RequestBody SgjsExperProgressManage sgjsExperProgressManageParam) {
        return toAjax(sgjsExperProgressManageService.updateSgjsExperProgressManage(sgjsExperProgressManageParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsExperProgressManageList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsExperProgressManage> sgjsExperProgressManageListParam) {
        return toAjax(sgjsExperProgressManageService.updateSgjsExperProgressManageList(sgjsExperProgressManageListParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsExperProgressManage(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsExperProgressManage sgjsExperProgressManageParam) {
        return toAjax(sgjsExperProgressManageService.deleteSgjsExperProgressManage(sgjsExperProgressManageParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperProgressManage:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsExperProgressManageByPks(@PathVariable Long[] ids) {
        List<Long> sgjsExperProgressManagePkList = Arrays.asList(ids);
        return toAjax(sgjsExperProgressManageService.deleteSgjsExperProgressManageByPks(sgjsExperProgressManagePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsExperProgressManage sgjsExperProgressManageParam) throws IOException {
        List<SgjsExperProgressManage> sgjsExperProgressManageList = sgjsExperProgressManageService.getSgjsExperProgressManageList(sgjsExperProgressManageParam);
        ExcelUtils<SgjsExperProgressManage> util = new ExcelUtils<>(SgjsExperProgressManage.class);
        util.exportExcel(response, sgjsExperProgressManageList, DateUtils.getDate());
    }
}
