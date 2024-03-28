package com.hhwy.sp.buildSchemeManage.review.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.enums.FlowEnum;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewOpinionRecord;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewDetailQueryVo;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewQueryVo;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewService;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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


    /**
     * 台账
     * @param queryVo
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:list")
    @GetMapping("/list")
    public AjaxResult getListByQueryVo(@Validated(ValidationGroups.Select.class) BuildSchemeReviewQueryVo queryVo) {
        startPage();
        List<SgjsBuildSchemeReview> reviewList = sgjsBuildSchemeReviewService.getListByQueryVo(queryVo);
        for (SgjsBuildSchemeReview review : reviewList) {
            String schemeLevel = review.getSchemeLevel();
            if("1".equals(schemeLevel)){
                continue;
            }
            if("2".equals(schemeLevel) || "3".equals(schemeLevel)){
                FlowInfoSearchUtil.getFlowInfo(review, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_2_3);
            }
            if("4".equals(schemeLevel)){
                FlowInfoSearchUtil.getFlowInfo(review, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_4);
            }
        }
        return getDataTableAjaxResult(reviewList);
    }

    /**
     * 详情
     * @param detailQueryVo
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:list")
    @GetMapping("/getDetail")
    public AjaxResult getDetail(BuildSchemeReviewDetailQueryVo detailQueryVo) {
        SgjsBuildSchemeReview review = sgjsBuildSchemeReviewService.getDetail(detailQueryVo);
        String schemeLevel = review.getSchemeLevel();
        if("2".equals(schemeLevel) || "3".equals(schemeLevel)){
            FlowInfoSearchUtil.getFlowInfo(review, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_2_3);
        }
        if("4".equals(schemeLevel)){
            FlowInfoSearchUtil.getFlowInfo(review, FlowEnum.SGJS_BUILD_SCHEME_REVIEW_4);
        }
        return AjaxResult.success(review);
    }

    /**
     * 保存
     * @param review
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsBuildSchemeReview:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody SgjsBuildSchemeReview review) {
        Long id = sgjsBuildSchemeReviewService.save(review);
        return AjaxResult.success(id);
    }

    /**
     * 导出
     * @param response
     * @param queryVo
     * @throws IOException
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody BuildSchemeReviewQueryVo queryVo) throws IOException {
        List<Long> ids = queryVo.getIds();
        List<SgjsBuildSchemeReview> reviewList;
        if(CollectionUtils.isEmpty(ids)){
            reviewList = sgjsBuildSchemeReviewService.getListByQueryVo(queryVo);
        }else {
            reviewList = sgjsBuildSchemeReviewService.getListByIds(ids);
        }
        FtExcelUtil<SgjsBuildSchemeReview> util = new FtExcelUtil<>(SgjsBuildSchemeReview.class);
        util.exportExcel(response, reviewList, DateUtils.getDate());
    }

    /**
     * 同步方案清单
     * @return
     */
    @GetMapping("/sync")
    public AjaxResult sync() {
        String remind = sgjsBuildSchemeReviewService.sync();
        return AjaxResult.success(remind);
    }

    /**
     * 区域总工不通过/海外事业部总工不通过/海外事业部总工修改后通过  记录历史意见并清除所有数据
     * @return
     */
    @GetMapping("/turnDown")
    public AjaxResult turnDown(Long reviewId) {
        sgjsBuildSchemeReviewService.turnDown(reviewId);
        return AjaxResult.success();
    }

    /**
     * 弹窗选择方案
     * @return
     */
    @GetMapping("/getSchemeList")
    public AjaxResult getSchemeList(SgjsBuildSchemeList schemeList) {
        List<SgjsBuildSchemeList> schemeListList = sgjsBuildSchemeReviewService.getSchemeList(schemeList);
        return AjaxResult.success(schemeListList);
    }

    /**
     * 获取方案评审记录
     * @param reviewId
     * @return
     */
    @GetMapping("/getSchemeReviewRecord")
    public AjaxResult getSchemeReviewRecord(Long reviewId){
        SgjsBuildSchemeReviewOpinionRecord reviewOpinionRecord = sgjsBuildSchemeReviewService.getSchemeReviewRecord(reviewId);
        return AjaxResult.success(reviewOpinionRecord);
    }

    @GetMapping("/submit")
    public AjaxResult submitBuildSchemeReviewProcess(@RequestParam("id") Long id){
        sgjsBuildSchemeReviewService.submitBuildSchemeReviewProcess(id);
        return AjaxResult.success();
    }

    @GetMapping("/listener")
    public AjaxResult updateBuildSchemeReviewProcess(@RequestParam("id") Long id){
        sgjsBuildSchemeReviewService.updateBuildSchemeReviewProcess(id);
        return AjaxResult.success();
    }

    /**
     * 流程分支-修改后通过
     * @param id
     * @return
     */
    @GetMapping("/approvedAfterModification")
    public AjaxResult approvedAfterModification(@RequestParam("id") Long id){
        sgjsBuildSchemeReviewService.approvedAfterModification(id);
        return AjaxResult.success();
    }
}
