package com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.service.ISgjsBuildSchemeExpertSuggestService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.sgjsBuildSchemeExpertSuggest.domain.SgjsBuildSchemeExpertSuggest;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author fsd
 * @date 2024-03-20 18:18:03
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsBuildSchemeExpertSuggest")
public class SgjsBuildSchemeExpertSuggestController extends BaseController {

    @Autowired
    private ISgjsBuildSchemeExpertSuggestService sgjsBuildSchemeExpertSuggestService;


    @PreAuthorize(hasPermi = "sgjsBuildSchemeExpertSuggest:list")
    @GetMapping
    public AjaxResult getSgjsBuildSchemeExpertSuggest(@Validated(ValidationGroups.Get.class) SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggestParam) {
        SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggest = sgjsBuildSchemeExpertSuggestService.getSgjsBuildSchemeExpertSuggest(sgjsBuildSchemeExpertSuggestParam);
        return AjaxResult.success(sgjsBuildSchemeExpertSuggest);
    }

    //查询
    @PreAuthorize(hasPermi = "sgjsBuildSchemeExpertSuggest:list")
    @GetMapping("/getGroupList")
    public AjaxResult getGroupList(@Validated(ValidationGroups.Select.class) SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggestParam) {
        List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestList = sgjsBuildSchemeExpertSuggestService.getGroupList(sgjsBuildSchemeExpertSuggestParam);
        return AjaxResult.success(sgjsBuildSchemeExpertSuggestList);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeExpertSuggest:list")
    @GetMapping("/list")
    public AjaxResult getSgjsBuildSchemeExpertSuggestList(@Validated(ValidationGroups.Select.class) SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggestParam) {
        List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestList = sgjsBuildSchemeExpertSuggestService.getSgjsBuildSchemeExpertSuggestList(sgjsBuildSchemeExpertSuggestParam);
        return AjaxResult.success(sgjsBuildSchemeExpertSuggestList);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeExpertSuggest:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsBuildSchemeExpertSuggest(@Validated(ValidationGroups.Save.class) @RequestBody SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggestParam) {
        sgjsBuildSchemeExpertSuggestService.insertSgjsBuildSchemeExpertSuggest(sgjsBuildSchemeExpertSuggestParam);
        return AjaxResult.success(sgjsBuildSchemeExpertSuggestParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeExpertSuggest:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsBuildSchemeExpertSuggestList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestListParam) {
        sgjsBuildSchemeExpertSuggestService.insertSgjsBuildSchemeExpertSuggestList(sgjsBuildSchemeExpertSuggestListParam);
        return AjaxResult.success(sgjsBuildSchemeExpertSuggestListParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeExpertSuggest:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsBuildSchemeExpertSuggest(@Validated(ValidationGroups.Update.class) @RequestBody SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggestParam) {
        return toAjax(sgjsBuildSchemeExpertSuggestService.updateSgjsBuildSchemeExpertSuggest(sgjsBuildSchemeExpertSuggestParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeExpertSuggest:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsBuildSchemeExpertSuggestList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestListParam) {
        return toAjax(sgjsBuildSchemeExpertSuggestService.updateSgjsBuildSchemeExpertSuggestList(sgjsBuildSchemeExpertSuggestListParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeExpertSuggest:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsBuildSchemeExpertSuggest(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggestParam) {
        return toAjax(sgjsBuildSchemeExpertSuggestService.deleteSgjsBuildSchemeExpertSuggest(sgjsBuildSchemeExpertSuggestParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeExpertSuggest:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsBuildSchemeExpertSuggestByPks(@PathVariable Long[] ids) {
        List<Long> sgjsBuildSchemeExpertSuggestPkList = Arrays.asList(ids);
        return toAjax(sgjsBuildSchemeExpertSuggestService.deleteSgjsBuildSchemeExpertSuggestByPks(sgjsBuildSchemeExpertSuggestPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsBuildSchemeExpertSuggest sgjsBuildSchemeExpertSuggestParam) throws IOException {
        List<SgjsBuildSchemeExpertSuggest> sgjsBuildSchemeExpertSuggestList = sgjsBuildSchemeExpertSuggestService.getSgjsBuildSchemeExpertSuggestList(sgjsBuildSchemeExpertSuggestParam);
        ExcelUtils<SgjsBuildSchemeExpertSuggest> util = new ExcelUtils<>(SgjsBuildSchemeExpertSuggest.class);
        util.exportExcel(response, sgjsBuildSchemeExpertSuggestList, DateUtils.getDate());
    }
}
