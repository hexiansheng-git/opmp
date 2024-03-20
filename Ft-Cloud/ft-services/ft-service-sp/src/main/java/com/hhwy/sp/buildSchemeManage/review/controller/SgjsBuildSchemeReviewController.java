package com.hhwy.sp.buildSchemeManage.review.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewService;
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
 * @date 2024-03-20 09:39:35
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsBuildSchemeReview")
public class SgjsBuildSchemeReviewController extends BaseController {

    @Autowired
    private ISgjsBuildSchemeReviewService sgjsBuildSchemeReviewService;


    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:list")
    @GetMapping
    public AjaxResult getSgjsBuildSchemeReview(@Validated(ValidationGroups.Get.class) SgjsBuildSchemeReview sgjsBuildSchemeReviewParam) {
        SgjsBuildSchemeReview sgjsBuildSchemeReview = sgjsBuildSchemeReviewService.getSgjsBuildSchemeReview(sgjsBuildSchemeReviewParam);
        return AjaxResult.success(sgjsBuildSchemeReview);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:list")
    @GetMapping("/list")
    public AjaxResult getSgjsBuildSchemeReviewList(@Validated(ValidationGroups.Select.class) SgjsBuildSchemeReview sgjsBuildSchemeReviewParam) {
        startPage();
        List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList = sgjsBuildSchemeReviewService.getSgjsBuildSchemeReviewList(sgjsBuildSchemeReviewParam);
        return getDataTableAjaxResult(sgjsBuildSchemeReviewList);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsBuildSchemeReview(@Validated(ValidationGroups.Save.class) @RequestBody SgjsBuildSchemeReview sgjsBuildSchemeReviewParam) {
        sgjsBuildSchemeReviewService.insertSgjsBuildSchemeReview(sgjsBuildSchemeReviewParam);
        return AjaxResult.success(sgjsBuildSchemeReviewParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsBuildSchemeReviewList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewListParam) {
        sgjsBuildSchemeReviewService.insertSgjsBuildSchemeReviewList(sgjsBuildSchemeReviewListParam);
        return AjaxResult.success(sgjsBuildSchemeReviewListParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsBuildSchemeReview(@Validated(ValidationGroups.Update.class) @RequestBody SgjsBuildSchemeReview sgjsBuildSchemeReviewParam) {
        return toAjax(sgjsBuildSchemeReviewService.updateSgjsBuildSchemeReview(sgjsBuildSchemeReviewParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsBuildSchemeReviewList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewListParam) {
        return toAjax(sgjsBuildSchemeReviewService.updateSgjsBuildSchemeReviewList(sgjsBuildSchemeReviewListParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsBuildSchemeReview(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsBuildSchemeReview sgjsBuildSchemeReviewParam) {
        return toAjax(sgjsBuildSchemeReviewService.deleteSgjsBuildSchemeReview(sgjsBuildSchemeReviewParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsBuildSchemeReviewByPks(@PathVariable Long[] ids) {
        List<Long> sgjsBuildSchemeReviewPkList = Arrays.asList(ids);
        return toAjax(sgjsBuildSchemeReviewService.deleteSgjsBuildSchemeReviewByPks(sgjsBuildSchemeReviewPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsBuildSchemeReview sgjsBuildSchemeReviewParam) throws IOException {
        List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList = sgjsBuildSchemeReviewService.getSgjsBuildSchemeReviewList(sgjsBuildSchemeReviewParam);
        ExcelUtils<SgjsBuildSchemeReview> util = new ExcelUtils<>(SgjsBuildSchemeReview.class);
        util.exportExcel(response, sgjsBuildSchemeReviewList, DateUtils.getDate());
    }
}
