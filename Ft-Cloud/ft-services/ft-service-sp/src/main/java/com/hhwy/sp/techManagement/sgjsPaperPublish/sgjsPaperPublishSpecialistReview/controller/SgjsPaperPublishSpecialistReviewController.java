package com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.controller;

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
import com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.service.ISgjsPaperPublishSpecialistReviewService;
import com.hhwy.sp.techManagement.sgjsPaperPublish.sgjsPaperPublishSpecialistReview.domain.SgjsPaperPublishSpecialistReview;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * 论文申请专家评审记录
 * @author fsd
 * @date 2024-07-24 14:15:53
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsPaperPublishSpecialistReview")
public class SgjsPaperPublishSpecialistReviewController extends BaseController {

    @Autowired
    private ISgjsPaperPublishSpecialistReviewService sgjsPaperPublishSpecialistReviewService;


    @PreAuthorize(hasPermi = "sgjsPaperPublishSpecialistReview:list")
    @GetMapping
    public AjaxResult getSgjsPaperPublishSpecialistReview(@Validated(ValidationGroups.Get.class) SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReviewParam) {
        SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReview = sgjsPaperPublishSpecialistReviewService.getSgjsPaperPublishSpecialistReview(sgjsPaperPublishSpecialistReviewParam);
        return AjaxResult.success(sgjsPaperPublishSpecialistReview);
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublishSpecialistReview:list")
    @GetMapping("/list")
    public AjaxResult getSgjsPaperPublishSpecialistReviewList(@Validated(ValidationGroups.Select.class) SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReviewParam) {
        startPage();
        List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewList = sgjsPaperPublishSpecialistReviewService.getSgjsPaperPublishSpecialistReviewList(sgjsPaperPublishSpecialistReviewParam);
        return getDataTableAjaxResult(sgjsPaperPublishSpecialistReviewList);
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublishSpecialistReview:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsPaperPublishSpecialistReview(@Validated(ValidationGroups.Save.class) @RequestBody SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReviewParam) {
        sgjsPaperPublishSpecialistReviewService.insertSgjsPaperPublishSpecialistReview(sgjsPaperPublishSpecialistReviewParam);
        return AjaxResult.success(sgjsPaperPublishSpecialistReviewParam);
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublishSpecialistReview:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsPaperPublishSpecialistReviewList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewListParam) {
        sgjsPaperPublishSpecialistReviewService.insertSgjsPaperPublishSpecialistReviewList(sgjsPaperPublishSpecialistReviewListParam);
        return AjaxResult.success(sgjsPaperPublishSpecialistReviewListParam);
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublishSpecialistReview:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsPaperPublishSpecialistReview(@Validated(ValidationGroups.Update.class) @RequestBody SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReviewParam) {
        return toAjax(sgjsPaperPublishSpecialistReviewService.updateSgjsPaperPublishSpecialistReview(sgjsPaperPublishSpecialistReviewParam));
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublishSpecialistReview:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsPaperPublishSpecialistReviewList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewListParam) {
        return toAjax(sgjsPaperPublishSpecialistReviewService.updateSgjsPaperPublishSpecialistReviewList(sgjsPaperPublishSpecialistReviewListParam));
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublishSpecialistReview:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsPaperPublishSpecialistReview(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReviewParam) {
        return toAjax(sgjsPaperPublishSpecialistReviewService.deleteSgjsPaperPublishSpecialistReview(sgjsPaperPublishSpecialistReviewParam));
    }

    @PreAuthorize(hasPermi = "sgjsPaperPublishSpecialistReview:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsPaperPublishSpecialistReviewByPks(@PathVariable Long[] ids) {
        List<Long> sgjsPaperPublishSpecialistReviewPkList = Arrays.asList(ids);
        return toAjax(sgjsPaperPublishSpecialistReviewService.deleteSgjsPaperPublishSpecialistReviewByPks(sgjsPaperPublishSpecialistReviewPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsPaperPublishSpecialistReview sgjsPaperPublishSpecialistReviewParam) throws IOException {
        List<SgjsPaperPublishSpecialistReview> sgjsPaperPublishSpecialistReviewList = sgjsPaperPublishSpecialistReviewService.getSgjsPaperPublishSpecialistReviewList(sgjsPaperPublishSpecialistReviewParam);
        ExcelUtils<SgjsPaperPublishSpecialistReview> util = new ExcelUtils<>(SgjsPaperPublishSpecialistReview.class);
        util.exportExcel(response, sgjsPaperPublishSpecialistReviewList, DateUtils.getDate());
    }
}
