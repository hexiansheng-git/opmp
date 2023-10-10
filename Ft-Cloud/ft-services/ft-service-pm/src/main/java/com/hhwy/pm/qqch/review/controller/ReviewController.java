package com.hhwy.pm.qqch.review.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.JsonUtils;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author mls
 * @date 2023-07-18 09:58:24
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchReview")
public class ReviewController extends BaseController {


    @Resource
    private IQqchWorkPlanService workPlanService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public static void main(String[] args) {
        JsonUtils.soutJsonStr(Review.class);
    }

    @PreAuthorize(hasPermi = "qqchReview:list")
    @GetMapping
    public AjaxResult getQqchReview(@Validated(ValidationGroups.Get.class) Review reviewParam) {
        Review review = qqchReviewService.getQqchReview(reviewParam);
        return AjaxResult.success(review);
    }

    @PreAuthorize(hasPermi = "qqchReview:list")
    @GetMapping("/list")
    public AjaxResult getQqchReviewList(@Validated(ValidationGroups.Select.class) Review reviewParam) {
        startPage();
        List<Review> reviewList = qqchReviewService.getQqchReviewList(reviewParam);
        return getDataTableAjaxResult(reviewList);
    }

    @PreAuthorize(hasPermi = "qqchReview:add")
    @PostMapping("/add")
    public AjaxResult insertQqchReview(@Validated(ValidationGroups.Save.class) @RequestBody Map<String, Object> params) {
        qqchReviewService.savePlan(Long.valueOf(params.get("id") + ""));
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchReview:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchReviewList(@Validated(ValidationGroups.Save.class) @RequestBody List<Review> reviewListParam) {
        qqchReviewService.insertQqchReviewList(reviewListParam);
        return AjaxResult.success(reviewListParam);
    }

    @PreAuthorize(hasPermi = "qqchReview:update")
    @PostMapping("/update")
    public AjaxResult updateQqchReview(@Validated(ValidationGroups.Update.class) @RequestBody Review reviewParam) {
        return toAjax(qqchReviewService.updateQqchReview(reviewParam));
    }

    @PreAuthorize(hasPermi = "qqchReview:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchReviewList(@Validated(ValidationGroups.Update.class) @RequestBody List<Review> reviewListParam) {
        return toAjax(qqchReviewService.updateQqchReviewList(reviewListParam));
    }

    @PreAuthorize(hasPermi = "qqchReview:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchReview(@Validated(ValidationGroups.Delete.class) @RequestBody Review reviewParam) {
        return toAjax(qqchReviewService.deleteQqchReview(reviewParam));
    }

    @PreAuthorize(hasPermi = "qqchReview:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchReviewByPks(@PathVariable Long[] ids) {
        List<Long> qqchReviewPkList = Arrays.asList(ids);
        return toAjax(qqchReviewService.deleteQqchReviewByPks(qqchReviewPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, Review reviewParam) throws IOException {
        List<Review> reviewList = qqchReviewService.getQqchReviewList(reviewParam);
        ExcelUtils<Review> util = new ExcelUtils<>(Review.class);
        util.exportExcel(response, reviewList, DateUtils.getDate());
    }


    /**
     * 获取详情数据
     *
     * @param map
     * @return
     */
    @PostMapping("/reviewInfo")
    @PreAuthorize(hasPermi = "qqchReview:reviewInfo")
    public AjaxResult reviewInfo(@RequestBody Map<String, String> map) {
        return AjaxResult.success(qqchReviewService.reviewInfo(map));
    }


    @PostMapping("/save")
    @PreAuthorize(hasPermi = "qqchReview:reviewInfo")
    public AjaxResult save(@RequestBody Review review) {
        qqchReviewService.updateQqchReview(review);
        return AjaxResult.success("操作成功", review.getId() + "");
    }


    @PostMapping("/submit")
    @PreAuthorize(hasPermi = "qqchReview:reviewInfo")
    public AjaxResult submit(@RequestBody Review review) {
        review.setTaskStatus("1");
        qqchReviewService.updateQqchReview(review);
        return AjaxResult.success("", review.getId() + "");
    }


    /**
     * 监听器
     */
    @PostMapping("/listener")
    @ResponseBody
    public AjaxResult listener(Long id) {
        qqchReviewService.listener(id);
        return AjaxResult.success("成功");
    }


    /**
     * 监听器
     */
    @PostMapping("/incr")
    @ResponseBody
    public AjaxResult incr(@RequestBody Map<String, Object> map) {
        String stage = this.qqchReviewService.getStage();
        return AjaxResult.success("成功");
    }

    /**
     * 前期策划编制第一阶段预警
     */
    @GetMapping("/preparationFirstStageWarn")
    AjaxResult preparationFirstStageWarn(){
        qqchReviewService.preparationFirstStageWarn();
        return AjaxResult.success();
    }

    /**
     * 前期策划编制第二阶段预警
     */
    @GetMapping("/preparationSecondStageWarn")
    AjaxResult preparationSecondStageWarn(){
        qqchReviewService.preparationSecondStageWarn();
        return AjaxResult.success();
    }

    /**
     * 前期策划编制第三阶段预警
     */
    @GetMapping("/preparationThirdStageWarn")
    AjaxResult preparationThirdStageWarn(){
        qqchReviewService.preparationThirdStageWarn();
        return AjaxResult.success();
    }

    /**
     * 前期策划评审预警
     */
    @GetMapping("/reviewWarn")
    AjaxResult reviewWarn(){
        qqchReviewService.reviewWarn();
        return AjaxResult.success();
    }
}
