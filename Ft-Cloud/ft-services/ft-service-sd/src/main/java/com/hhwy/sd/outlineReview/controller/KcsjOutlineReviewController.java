package com.hhwy.sd.outlineReview.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.sd.outlineReview.domain.KcsjOutlineReview;
import com.hhwy.sd.outlineReview.service.IKcsjOutlineReviewService;
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
 * @author fushudong
 * @date 2024-02-04 15:29:15
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjOutlineReview")
public class KcsjOutlineReviewController extends BaseController {

    @Autowired
    private IKcsjOutlineReviewService kcsjOutlineReviewService;


    @PreAuthorize(hasPermi = "kcsjOutlineReview:list")
    @GetMapping
    public AjaxResult getKcsjOutlineReview(@Validated(ValidationGroups.Get.class) KcsjOutlineReview kcsjOutlineReviewParam) {
        KcsjOutlineReview kcsjOutlineReview = kcsjOutlineReviewService.getKcsjOutlineReview(kcsjOutlineReviewParam);
        return AjaxResult.success(kcsjOutlineReview);
    }

    //历史记录，台账
    @PreAuthorize(hasPermi = "kcsjOutlineReview:list")
    @GetMapping("/list")
    public AjaxResult getKcsjOutlineReviewList(@Validated(ValidationGroups.Select.class) KcsjOutlineReview kcsjOutlineReviewParam) {
        List<KcsjOutlineReview> kcsjOutlineReviewList = kcsjOutlineReviewService.getKcsjOutlineReviewList(kcsjOutlineReviewParam);
        return AjaxResult.success(kcsjOutlineReviewList);
    }

    //详情，编辑
    @PreAuthorize(hasPermi = "kcsjOutlineReview:list")
    @GetMapping("/detail")
    public AjaxResult detail(@Validated(ValidationGroups.Select.class) KcsjOutlineReview kcsjOutlineReviewParam) {
        KcsjOutlineReview kcsjOutlineReview = kcsjOutlineReviewService.getDetail(kcsjOutlineReviewParam);
        return AjaxResult.success(kcsjOutlineReview);
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjOutlineReview(@Validated(ValidationGroups.Save.class) @RequestBody KcsjOutlineReview kcsjOutlineReviewParam) {
        kcsjOutlineReviewService.insertKcsjOutlineReview(kcsjOutlineReviewParam);
        return AjaxResult.success(kcsjOutlineReviewParam);
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjOutlineReviewList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjOutlineReview> kcsjOutlineReviewListParam) {
        kcsjOutlineReviewService.insertKcsjOutlineReviewList(kcsjOutlineReviewListParam);
        return AjaxResult.success(kcsjOutlineReviewListParam);
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjOutlineReview(@Validated(ValidationGroups.Update.class) @RequestBody KcsjOutlineReview kcsjOutlineReviewParam) {
        return toAjax(kcsjOutlineReviewService.updateKcsjOutlineReview(kcsjOutlineReviewParam));
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjOutlineReviewList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjOutlineReview> kcsjOutlineReviewListParam) {
        return toAjax(kcsjOutlineReviewService.updateKcsjOutlineReviewList(kcsjOutlineReviewListParam));
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjOutlineReview(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjOutlineReview kcsjOutlineReviewParam) {
        return toAjax(kcsjOutlineReviewService.deleteKcsjOutlineReview(kcsjOutlineReviewParam));
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjOutlineReviewByPks(@PathVariable Integer[] ids) {
        List<Integer> kcsjOutlineReviewPkList = Arrays.asList(ids);
        return toAjax(kcsjOutlineReviewService.deleteKcsjOutlineReviewByPks(kcsjOutlineReviewPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjOutlineReview kcsjOutlineReviewParam) throws IOException {
        List<KcsjOutlineReview> kcsjOutlineReviewList = kcsjOutlineReviewService.getKcsjOutlineReviewList(kcsjOutlineReviewParam);
        ExcelUtils<KcsjOutlineReview> util = new ExcelUtils<>(KcsjOutlineReview.class);
        util.exportExcel(response, kcsjOutlineReviewList, DateUtils.getDate());
    }
}
