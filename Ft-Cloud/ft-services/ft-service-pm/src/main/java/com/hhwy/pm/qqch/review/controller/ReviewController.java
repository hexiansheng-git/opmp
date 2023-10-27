package com.hhwy.pm.qqch.review.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.JsonUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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


    @Autowired
    private IQqchReviewService qqchReviewService;


    public static void main(String[] args) {
        JsonUtils.soutJsonStr(Review.class);
    }

//    @PreAuthorize(hasPermi = "qqchReview:list")
    @GetMapping
    public AjaxResult getQqchReview(@Validated(ValidationGroups.Get.class) Review reviewParam) {
        Review review = qqchReviewService.getQqchReview(reviewParam);
        return AjaxResult.success(review);
    }

//    @PreAuthorize(hasPermi = "qqchReview:list")
    @GetMapping("/list")
    public AjaxResult getQqchReviewList(@Validated(ValidationGroups.Select.class) Review reviewParam) {
        startPage();
        List<Review> reviewList = qqchReviewService.getQqchReviewList(reviewParam);
        handlerReviewList(reviewList);
        return getDataTableAjaxResult(reviewList);
    }

    /**
     *  处理前期策划流程状态等字段
     * @return
     */
    public List<Review> handlerReviewList(List<Review> list){
        //根据阶段不同，会走不同的流程
        List<Review> review1List = new ArrayList<>();   //1,2阶段流程
        List<Review> review3List = new ArrayList<>();   //3阶段流程
        list.stream().forEach(r->{
            List temp = r.getPlanStage().equals("3")?review3List:review1List;
            temp.add(r);
        });
        FlowInfoSearchUtil.getFlowInfo(review1List, FlowEnum.QQCH_REVIEW1);
        FlowInfoSearchUtil.getFlowInfo(review3List, FlowEnum.QQCH_REVIEW2);
        //处理完成百分比、创建日期>发起日期、
        for (int i = 0; i < list.size(); i++) {
            Review review = list.get(i);
            review.setInitDate(review.getCreateTime());
            BigDecimal ratio = BigDecimalUtils.divideMay0(review.getFinishNum(),review.getPlanNum() , 4, BigDecimal.ROUND_HALF_UP);
            review.setFinishRatio(ratio.multiply(new BigDecimal(100)));
            //处理状态字段 0-未发起; 1审核中; 4-流程已结束,业务未结束; 5-流程和业务都已结束'
            String taskStatusDesc = "";
            if(review.getTaskStatus().equals("0")){
                if(review.getFinishNum() <= 0){
                    taskStatusDesc ="未编制"; 
                }else if(review.getFinishNum() >= review.getPlanNum()){
                    taskStatusDesc ="编制完成";
                }else{
                    taskStatusDesc ="正在编制";
                }                         
            }else if(review.getTaskStatus().equals("4")){ //审批结束
                taskStatusDesc ="审批完成";
            }else{
                taskStatusDesc ="正在审批";
            }
            review.setTaskStatusDesc(taskStatusDesc);
        }
        return list;
    }

//    @PreAuthorize(hasPermi = "qqchReview:add")
    @PostMapping("/add")
    public AjaxResult insertQqchReview(@Validated(ValidationGroups.Save.class) @RequestBody Map<String, Object> params) {
        qqchReviewService.savePlan(Long.valueOf(params.get("id") + ""));
        return AjaxResult.success();
    }

//    @PreAuthorize(hasPermi = "qqchReview:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchReviewList(@Validated(ValidationGroups.Save.class) @RequestBody List<Review> reviewListParam) {
        qqchReviewService.insertQqchReviewList(reviewListParam);
        return AjaxResult.success(reviewListParam);
    }

//    @PreAuthorize(hasPermi = "qqchReview:update")
    @PostMapping("/update")
    public AjaxResult updateQqchReview(@Validated(ValidationGroups.Update.class) @RequestBody Review reviewParam) {
        return toAjax(qqchReviewService.updateQqchReview(reviewParam));
    }

//    @PreAuthorize(hasPermi = "qqchReview:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchReviewList(@Validated(ValidationGroups.Update.class) @RequestBody List<Review> reviewListParam) {
        return toAjax(qqchReviewService.updateQqchReviewList(reviewListParam));
    }

//    @PreAuthorize(hasPermi = "qqchReview:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchReview(@Validated(ValidationGroups.Delete.class) @RequestBody Review reviewParam) {
        return toAjax(qqchReviewService.deleteQqchReview(reviewParam));
    }

//    @PreAuthorize(hasPermi = "qqchReview:remove")
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
//    @PreAuthorize(hasPermi = "qqchReview:reviewInfo")
    public AjaxResult reviewInfo(@RequestBody Map<String, String> map) {
        return AjaxResult.success(qqchReviewService.reviewInfo(map));
    }

    @PostMapping("/save")
//    @PreAuthorize(hasPermi = "qqchReview:reviewInfo")
    public AjaxResult save(@RequestBody Review review) {
        qqchReviewService.updateQqchReview(review);
        return AjaxResult.success("操作成功", review.getId() + "");
    }

    @PostMapping("/submit")
//    @PreAuthorize(hasPermi = "qqchReview:reviewInfo")
    public AjaxResult submit(@RequestBody Review review) {
        review.setTaskStatus("1");
        review.setReviewStatus("3");
        qqchReviewService.updateQqchReview(review);
        return AjaxResult.success("", review.getId() + "");
    }

    /**
     * 监听器
     */
    @PostMapping("/listener")
    public AjaxResult reviewListener(Long id) {
        qqchReviewService.listener(id);
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
