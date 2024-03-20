package com.hhwy.sp.buildSchemeManage.review.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeStaffOpinion;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeStaffOpinionService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:59
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsBuildSchemeStaffOpinion")
public class SgjsBuildSchemeStaffOpinionController extends BaseController {

    @Autowired
    private ISgjsBuildSchemeStaffOpinionService sgjsBuildSchemeStaffOpinionService;


    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinion:list")
    @GetMapping
    public AjaxResult getSgjsBuildSchemeStaffOpinion(@Validated(ValidationGroups.Get.class) SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinionParam) {
        SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion = sgjsBuildSchemeStaffOpinionService.getSgjsBuildSchemeStaffOpinion(sgjsBuildSchemeStaffOpinionParam);
        return AjaxResult.success(sgjsBuildSchemeStaffOpinion);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinion:list")
    @GetMapping("/list")
    public AjaxResult getSgjsBuildSchemeStaffOpinionList(@Validated(ValidationGroups.Select.class) SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinionParam) {
        startPage();
        List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionList = sgjsBuildSchemeStaffOpinionService.getSgjsBuildSchemeStaffOpinionList(sgjsBuildSchemeStaffOpinionParam);
        return getDataTableAjaxResult(sgjsBuildSchemeStaffOpinionList);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinion:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsBuildSchemeStaffOpinion(@Validated(ValidationGroups.Save.class) @RequestBody SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinionParam) {
        sgjsBuildSchemeStaffOpinionService.insertSgjsBuildSchemeStaffOpinion(sgjsBuildSchemeStaffOpinionParam);
        return AjaxResult.success(sgjsBuildSchemeStaffOpinionParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinion:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsBuildSchemeStaffOpinionList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionListParam) {
        sgjsBuildSchemeStaffOpinionService.insertSgjsBuildSchemeStaffOpinionList(sgjsBuildSchemeStaffOpinionListParam);
        return AjaxResult.success(sgjsBuildSchemeStaffOpinionListParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinion:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsBuildSchemeStaffOpinion(@Validated(ValidationGroups.Update.class) @RequestBody SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinionParam) {
        return toAjax(sgjsBuildSchemeStaffOpinionService.updateSgjsBuildSchemeStaffOpinion(sgjsBuildSchemeStaffOpinionParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinion:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsBuildSchemeStaffOpinionList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionListParam) {
        return toAjax(sgjsBuildSchemeStaffOpinionService.updateSgjsBuildSchemeStaffOpinionList(sgjsBuildSchemeStaffOpinionListParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinion:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsBuildSchemeStaffOpinion(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinionParam) {
        return toAjax(sgjsBuildSchemeStaffOpinionService.deleteSgjsBuildSchemeStaffOpinion(sgjsBuildSchemeStaffOpinionParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeStaffOpinion:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsBuildSchemeStaffOpinionByPks(@PathVariable Long[] ids) {
        List<Long> sgjsBuildSchemeStaffOpinionPkList = Arrays.asList(ids);
        return toAjax(sgjsBuildSchemeStaffOpinionService.deleteSgjsBuildSchemeStaffOpinionByPks(sgjsBuildSchemeStaffOpinionPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinionParam) throws IOException {
        List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionList = sgjsBuildSchemeStaffOpinionService.getSgjsBuildSchemeStaffOpinionList(sgjsBuildSchemeStaffOpinionParam);
        ExcelUtils<SgjsBuildSchemeStaffOpinion> util = new ExcelUtils<>(SgjsBuildSchemeStaffOpinion.class);
        util.exportExcel(response, sgjsBuildSchemeStaffOpinionList, DateUtils.getDate());
    }
}
