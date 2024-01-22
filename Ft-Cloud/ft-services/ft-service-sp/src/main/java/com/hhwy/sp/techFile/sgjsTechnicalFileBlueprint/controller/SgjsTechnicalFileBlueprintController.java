package com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprint;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprintParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.service.ISgjsTechnicalFileBlueprintService;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author fsd
 * @date 2024-01-22 08:54:05
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsTechnicalFileBlueprint")
public class SgjsTechnicalFileBlueprintController extends BaseController {

    @Autowired
    private ISgjsTechnicalFileBlueprintService sgjsTechnicalFileBlueprintService;


    @PreAuthorize(hasPermi = "sgjsTechnicalFileBlueprint:list")
    @GetMapping
    public AjaxResult getSgjsTechnicalFileBlueprint(@Validated(ValidationGroups.Get.class) SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprintParam) {
        SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint = sgjsTechnicalFileBlueprintService.getSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprintParam);
        return AjaxResult.success(sgjsTechnicalFileBlueprint);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalFileBlueprint:list")
    @GetMapping("/listPage")
    public AjaxResult getSgjsTechnicalFileBlueprintList(@Validated(ValidationGroups.Select.class) SgjsTechnicalFileBlueprintParam sgjsTechnicalFileBlueprintParam) {
        startPage();
        List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList = sgjsTechnicalFileBlueprintService.getSgjsTechnicalFileBlueprintList(sgjsTechnicalFileBlueprintParam);
        return getDataTableAjaxResult(sgjsTechnicalFileBlueprintList);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalFileBlueprint:list")
    @GetMapping("/list")
    public AjaxResult getTreeList(@Validated(ValidationGroups.Select.class) SgjsTechnicalFileBlueprintParam sgjsTechnicalFileBlueprintParam) {
        List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList = sgjsTechnicalFileBlueprintService.getSgjsTechnicalFileBlueprintList(sgjsTechnicalFileBlueprintParam);
        return AjaxResult.success(sgjsTechnicalFileBlueprintList);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalFileBlueprint:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsTechnicalFileBlueprint(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprintParam) {
        sgjsTechnicalFileBlueprintService.insertSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprintParam);
        return AjaxResult.success(sgjsTechnicalFileBlueprintParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalFileBlueprint:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsTechnicalFileBlueprintList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintListParam) {
        sgjsTechnicalFileBlueprintService.insertSgjsTechnicalFileBlueprintList(sgjsTechnicalFileBlueprintListParam);
        return AjaxResult.success(sgjsTechnicalFileBlueprintListParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalFileBlueprint:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsTechnicalFileBlueprint(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprintParam) {
        return toAjax(sgjsTechnicalFileBlueprintService.updateSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprintParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalFileBlueprint:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsTechnicalFileBlueprintList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintListParam) {
        return toAjax(sgjsTechnicalFileBlueprintService.updateSgjsTechnicalFileBlueprintList(sgjsTechnicalFileBlueprintListParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalFileBlueprint:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsTechnicalFileBlueprint(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprintParam) {
        return toAjax(sgjsTechnicalFileBlueprintService.deleteSgjsTechnicalFileBlueprint(sgjsTechnicalFileBlueprintParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalFileBlueprint:remove")
    @PostMapping("/remove")
    public AjaxResult deleteSgjsTechnicalFileBlueprintByPks(Long[] ids) {
        List<Long> sgjsTechnicalFileBlueprintPkList = Arrays.asList(ids);
        return toAjax(sgjsTechnicalFileBlueprintService.deleteWithChildren(sgjsTechnicalFileBlueprintPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsTechnicalFileBlueprintParam sgjsTechnicalFileBlueprintParam) throws IOException {
        List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList = sgjsTechnicalFileBlueprintService.getSgjsTechnicalFileBlueprintList(sgjsTechnicalFileBlueprintParam);
        ExcelUtils<SgjsTechnicalFileBlueprint> util = new ExcelUtils<>(SgjsTechnicalFileBlueprint.class);
        util.exportExcel(response, sgjsTechnicalFileBlueprintList, DateUtils.getDate());
    }
}
