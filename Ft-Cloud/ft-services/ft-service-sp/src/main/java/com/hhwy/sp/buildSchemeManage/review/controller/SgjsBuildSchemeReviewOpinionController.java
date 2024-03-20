package com.hhwy.sp.buildSchemeManage.review.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewOpinion;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewOpinionService;
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
 * @date 2024-03-20 09:39:39
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsBuildSchemeReviewOpinion")
public class SgjsBuildSchemeReviewOpinionController extends BaseController {

    @Autowired
    private ISgjsBuildSchemeReviewOpinionService sgjsBuildSchemeReviewOpinionService;


    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinion:list")
    @GetMapping
    public AjaxResult getSgjsBuildSchemeReviewOpinion(@Validated(ValidationGroups.Get.class) SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinionParam) {
        SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinion = sgjsBuildSchemeReviewOpinionService.getSgjsBuildSchemeReviewOpinion(sgjsBuildSchemeReviewOpinionParam);
        return AjaxResult.success(sgjsBuildSchemeReviewOpinion);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinion:list")
    @GetMapping("/list")
    public AjaxResult getSgjsBuildSchemeReviewOpinionList(@Validated(ValidationGroups.Select.class) SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinionParam) {
        startPage();
        List<SgjsBuildSchemeReviewOpinion> sgjsBuildSchemeReviewOpinionList = sgjsBuildSchemeReviewOpinionService.getSgjsBuildSchemeReviewOpinionList(sgjsBuildSchemeReviewOpinionParam);
        return getDataTableAjaxResult(sgjsBuildSchemeReviewOpinionList);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinion:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsBuildSchemeReviewOpinion(@Validated(ValidationGroups.Save.class) @RequestBody SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinionParam) {
        sgjsBuildSchemeReviewOpinionService.insertSgjsBuildSchemeReviewOpinion(sgjsBuildSchemeReviewOpinionParam);
        return AjaxResult.success(sgjsBuildSchemeReviewOpinionParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinion:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsBuildSchemeReviewOpinionList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsBuildSchemeReviewOpinion> sgjsBuildSchemeReviewOpinionListParam) {
        sgjsBuildSchemeReviewOpinionService.insertSgjsBuildSchemeReviewOpinionList(sgjsBuildSchemeReviewOpinionListParam);
        return AjaxResult.success(sgjsBuildSchemeReviewOpinionListParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinion:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsBuildSchemeReviewOpinion(@Validated(ValidationGroups.Update.class) @RequestBody SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinionParam) {
        return toAjax(sgjsBuildSchemeReviewOpinionService.updateSgjsBuildSchemeReviewOpinion(sgjsBuildSchemeReviewOpinionParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinion:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsBuildSchemeReviewOpinionList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsBuildSchemeReviewOpinion> sgjsBuildSchemeReviewOpinionListParam) {
        return toAjax(sgjsBuildSchemeReviewOpinionService.updateSgjsBuildSchemeReviewOpinionList(sgjsBuildSchemeReviewOpinionListParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinion:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsBuildSchemeReviewOpinion(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinionParam) {
        return toAjax(sgjsBuildSchemeReviewOpinionService.deleteSgjsBuildSchemeReviewOpinion(sgjsBuildSchemeReviewOpinionParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewOpinion:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsBuildSchemeReviewOpinionByPks(@PathVariable Long[] ids) {
        List<Long> sgjsBuildSchemeReviewOpinionPkList = Arrays.asList(ids);
        return toAjax(sgjsBuildSchemeReviewOpinionService.deleteSgjsBuildSchemeReviewOpinionByPks(sgjsBuildSchemeReviewOpinionPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsBuildSchemeReviewOpinion sgjsBuildSchemeReviewOpinionParam) throws IOException {
        List<SgjsBuildSchemeReviewOpinion> sgjsBuildSchemeReviewOpinionList = sgjsBuildSchemeReviewOpinionService.getSgjsBuildSchemeReviewOpinionList(sgjsBuildSchemeReviewOpinionParam);
        ExcelUtils<SgjsBuildSchemeReviewOpinion> util = new ExcelUtils<>(SgjsBuildSchemeReviewOpinion.class);
        util.exportExcel(response, sgjsBuildSchemeReviewOpinionList, DateUtils.getDate());
    }
}
