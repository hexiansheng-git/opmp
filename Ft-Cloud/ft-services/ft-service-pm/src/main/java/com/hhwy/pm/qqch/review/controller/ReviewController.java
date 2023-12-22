package com.hhwy.pm.qqch.review.controller;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.dataShare.DataShareDevicePlanService;
import com.hhwy.utils.JsonUtils;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
    @Autowired
    private DataShareDevicePlanService dataShareDevicePlanService;


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
    @CustomLogger(title = "前期策划-前期策划评审", name = "前期策划评审" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchReviewList(@Validated(ValidationGroups.Select.class) Review reviewParam) {
        startPage();
        List<Review> reviewList = qqchReviewService.getQqchReviewList(reviewParam);
        handlerReviewList(reviewList);
        setIsCanApprove(reviewList);
        setParticularsMark(reviewList);
        setDisposeMark(reviewList);
        return getDataTableAjaxResult(reviewList);
    }

    /**
     *  处理前期策划流程状态等字段
     * @return
     */
    private List<Review> handlerReviewList(List<Review> list){
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
            review.setPlanNum(ObjectUtils.nvl(review.getPlanNum()));
            review.setFinishNum(ObjectUtils.nvl(review.getFinishNum()));
            BigDecimal ratio = BigDecimalUtils.divideMay0(review.getFinishNum(),review.getPlanNum() , 4, BigDecimal.ROUND_HALF_UP);
            review.setFinishRatio(ObjectUtils.nvlBigDecimal(ratio).multiply(new BigDecimal(100)));
            //处理状态字段 0-未发起; 1审核中; 4-流程已结束,业务未结束; 5-流程和业务都已结束'
            String taskStatusDesc = "";
            String reviewStatus = "";
            if(review.getTaskStatus().equals("0")){
                if(review.getFinishNum() <= 0){
                    taskStatusDesc ="未编制";
                    reviewStatus = "0";
                }else if(review.getFinishNum() >= review.getPlanNum()){
                    taskStatusDesc ="编制完成";
                    reviewStatus = "2";
                }else{
                    taskStatusDesc ="正在编制";
                    reviewStatus = "1";
                }                         
            }else if(review.getTaskStatus().equals("4")){ //审批结束
                taskStatusDesc ="审批完成";
                reviewStatus = "4";
            }else{
                taskStatusDesc ="正在审批";
                reviewStatus = "3";
            }
            review.setTaskStatusDesc(taskStatusDesc);
            review.setReviewStatus(reviewStatus);
        }
        return list;
    }

    private void setParticularsMark(List<Review> qqchReviewList){
        if(CollectionUtils.isEmpty(qqchReviewList)){
            return;
        }

        qqchReviewList.stream().forEach(review -> {
            String reviewStatus = review.getReviewStatus();
            String taskStatus = review.getTaskStatus();
            if(!"0".equals(taskStatus) && ("2".equals(reviewStatus) || "3".equals(reviewStatus) || "4".equals(reviewStatus))){
                review.setParticularsMark("1");
            }
        });
    }

    /**
     * 设置评审阶段数据是否存在发起审批按钮
     * @param qqchReviewList
     */
    private void setIsCanApprove(List<Review> qqchReviewList){
        if(CollectionUtils.isEmpty(qqchReviewList)){
            return;
        }
        Map<String, Review> reviewMap = qqchReviewList.stream().collect(Collectors.toMap(Review::getPlanStage, o -> o));

        Review review1 = reviewMap.get("1");
        if (isCanApprove(review1)) return;

        Review review2 = reviewMap.get("2");
        if (isCanApprove(review2)) return;

        Review review3 = reviewMap.get("3");
        isCanApprove(review3);
    }

    private boolean isCanApprove(Review review) {
        if(review == null){
            return true;
        }
        String reviewStatus = review.getReviewStatus();
        String taskStatus = review.getTaskStatus();
        //编制完成并且未发起流程
        if("2".equals(reviewStatus) && "0".equals(taskStatus)){
            review.setIsCanApprove("1");
            return true;
        }
        return !"4".equals(reviewStatus);
    }

    private void setDisposeMark(List<Review> qqchReviewList){
        if(CollectionUtils.isEmpty(qqchReviewList)){
            return;
        }
        Long userId = SecurityUtils.getSysUser().getUserId();
        qqchReviewList.stream().forEach(review -> {
            String taskStatus = review.getTaskStatus();
            String processTaskManId = review.getProcessTaskManId();
            if("1".equals(taskStatus) && userId.toString().equals(processTaskManId)){
                review.setDisposeMark("1");
            }
        });
    }

    //    @PreAuthorize(hasPermi = "qqchReview:add")
    @PostMapping("/add")
    @CustomLogger(title = "前期策划-前期策划评审", name = "前期策划评审" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchReview(@Validated(ValidationGroups.Save.class) @RequestBody Map<String, Object> params) {
        qqchReviewService.savePlan(Long.valueOf(params.get("id") + ""));
        return AjaxResult.success();
    }

//    @PreAuthorize(hasPermi = "qqchReview:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划评审", name = "前期策划评审" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchReviewList(@Validated(ValidationGroups.Save.class) @RequestBody List<Review> reviewListParam) {
        qqchReviewService.insertQqchReviewList(reviewListParam);
        return AjaxResult.success(reviewListParam);
    }

//    @PreAuthorize(hasPermi = "qqchReview:update")
    @PostMapping("/update")
    @CustomLogger(title = "前期策划-前期策划评审", name = "前期策划评审" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateQqchReview(@Validated(ValidationGroups.Update.class) @RequestBody Review reviewParam) {
        return toAjax(qqchReviewService.updateQqchReview(reviewParam));
    }

//    @PreAuthorize(hasPermi = "qqchReview:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "前期策划-前期策划评审", name = "前期策划评审" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateQqchReviewList(@Validated(ValidationGroups.Update.class) @RequestBody List<Review> reviewListParam) {
        return toAjax(qqchReviewService.updateQqchReviewList(reviewListParam));
    }

//    @PreAuthorize(hasPermi = "qqchReview:remove")
    @PostMapping("/delete")
    @CustomLogger(title = "前期策划-前期策划评审", name = "前期策划评审" ,businessType = CustomBusinessType.DELETE)
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
    @CustomLogger(title = "前期策划-前期策划评审", name = "前期策划评审" ,businessType = CustomBusinessType.EXPORT)
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
    @CustomLogger(title = "前期策划-前期策划评审", name = "前期策划评审" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@RequestBody Review review) {
        qqchReviewService.updateQqchReview(review);
        return AjaxResult.success("操作成功", review.getId() + "");
    }

    @PostMapping("/submit")
    @CustomLogger(title = "前期策划-前期策划评审", name = "前期策划评审" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult submit(@RequestBody Review review) {
        review.setTaskStatus("1");
        review.setReviewStatus("3");
        review.setInitDate(new Date());
        review.setInitUserId(SecurityUtils.getUserId());
        review.setInitUserName(SecurityUtils.getSysUser().getNickName());
        qqchReviewService.updateQqchReview(review);
        return AjaxResult.success("", review.getId() + "");
    }

    /**
     * 监听器
     */
    @PostMapping("/listener")
    public AjaxResult reviewListener(Long id) {
        qqchReviewService.listener(id);
        //推送设备策划数据到物设中间库
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        String tenantKey = SecurityUtils.getTenantKey();
        executorService.submit(() -> {
            //切换
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
            try {
                dataShareDevicePlanService.eachStagePush(tenantKey);
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomException(e.getMessage());
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        });
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
