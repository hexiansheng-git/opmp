package com.hhwy.pm.core.sync.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrack;
import com.hhwy.pm.jdgl.diff.track.mapper.JdglProgressCorrectionTrackMapper;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import com.hhwy.pm.qqch.evaluation.service.IQqchSummaryEvaluationService;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.qqchPerformInspection.service.IQqchPerformInspectionService;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/syncInfo")
public class SyncController extends BaseController {

    @Autowired
    private IQqchWorkGroupService qqchWorkGroupService;
    @Autowired
    private IQqchWorkPlanService qqchWorkPlanService;
    @Autowired
    private IQqchReviewService reviewService;
    @Autowired
    private ISysSyncInfoService syncInfoService;
    @Autowired
    private IJdglDiffAnalysisService jdglDiffAnalysisService;
    @Autowired
    private IJdglProgressCorrectionTrackService jdglProgressCorrectionTrackService;
    @Autowired
    private IQqchPerformInspectionService performInspectionService;
    @Autowired
    private IQqchSummaryEvaluationService summaryEvaluationService;
    @Autowired
    private IJdglDayScheduleService jdglDayScheduleService;
    @Autowired
    private JdglProgressCorrectionTrackMapper jdglProgressCorrectionTrackMapper;

    /**
     * 前期策划测试用
     * @param 
     * @return
     */
    @PostMapping("/workGroup")
    public AjaxResult sync(@RequestBody QqchWorkGroup qqchWorkGroup) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        if(qqchWorkGroup.getId() != null){
            qqchWorkGroup = qqchWorkGroupService.getQqchWorkGroupById(qqchWorkGroup.getId());
            syncInfoService.pushQqchWorkGroup(qqchWorkGroup);
        }else{
            List<QqchWorkGroup> list = qqchWorkGroupService.getQqchWorkGroupList(new QqchWorkGroup());
            syncInfoService.pushQqchWorkGroup(list);
        }
        return AjaxResult.success();
    }

    /**
     * 前期策划工作计划测试用
     * @param
     * @return
     */
    @PostMapping("/workPlan")
    public AjaxResult workPlan(@RequestBody QqchWorkPlan qqchWorkPlan) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        if(qqchWorkPlan.getId() != null){
            QqchWorkPlan query = new QqchWorkPlan();
            query.setId(IdWorker.createId());
            qqchWorkPlan = qqchWorkPlanService.getQqchWorkPlan(query);
            syncInfoService.pushQqchWorkPlan(qqchWorkPlan);
        }else{
            List<QqchWorkPlan> list = qqchWorkPlanService.getQqchWorkPlanList(new QqchWorkPlan());
            syncInfoService.pushQqchWorkPlan(list);
        }
        return AjaxResult.success();
    }

    /**
     * 前期策划评审
     * @param 
     * @return
     */
    @PostMapping("/reviewPush")
    public AjaxResult reviewPush(@RequestBody Review review) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        if(review.getId() != null){
            Review query = new Review();
            query.setId(IdWorker.createId());
            review = reviewService.getQqchReview(query);
            syncInfoService.pushQqchReview(review);
        }else{
            List<Review> list = reviewService.getQqchReviewList(new Review());
            syncInfoService.pushQqchReview(list);
        }
        return AjaxResult.success();
    }

    /**
     * 前期策划评审
     * @param
     * @return
     */
    @PostMapping("/performInspectionPush")
    public AjaxResult performInspectionPush(@RequestBody QqchPerformInspection performInspection) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        if(performInspection.getId() != null){
            QqchPerformInspection query = new QqchPerformInspection();
            query.setId(IdWorker.createId());
            performInspection = performInspectionService.getQqchPerformInspection(query);
            syncInfoService.pushQqchPerformInspection(performInspection);
        }else{
            List<QqchPerformInspection> list = performInspectionService.getQqchPerformInspectionList(new QqchPerformInspection());
            syncInfoService.pushQqchPerformInspection(list);
        }
        return AjaxResult.success();
    }

    /**
     * 推送前期策划总结评价
     * @param summaryEvaluation
     * @return
     */
    @PostMapping("/qqchSummaryEvaluationPush")
    public AjaxResult qqchSummaryEvaluationPush(@RequestBody(required = false) QqchSummaryEvaluation summaryEvaluation) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        QqchSummaryEvaluation query = new QqchSummaryEvaluation();
        QqchSummaryEvaluation qqchSummaryEvaluation = summaryEvaluationService.getQqchSummaryEvaluation( new QqchSummaryEvaluation());
        syncInfoService.pushQqchSummaryEvaluation(qqchSummaryEvaluation);
        return AjaxResult.success();
    }

    /**
     * 差异化分析测试用
     * @param
     * @return
     */
    @PostMapping("/diffAnalysis")
    public AjaxResult diffAnalysis(@RequestBody JdglDiffAnalysis diffAnalysis) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        if(diffAnalysis.getId() != null){
            JdglDiffAnalysis query = new JdglDiffAnalysis();
            query.setId(IdWorker.createId());
            diffAnalysis = jdglDiffAnalysisService.getJdglDiffAnalysis(query);
            syncInfoService.pushJdglDiffAnalysis(diffAnalysis);
        }else{
            List<JdglDiffAnalysis> list = jdglDiffAnalysisService.getJdglDiffAnalysisList(new JdglDiffAnalysis());
            syncInfoService.pushJdglDiffAnalysis(list);
        }
        return AjaxResult.success();
    }

    /**
     * 进度纠偏跟踪测试用
     * @param
     * @return
     */
    @PostMapping("/progressCorrectionTrack")
    public AjaxResult progressCorrectionTrack(@RequestBody JdglProgressCorrectionTrack progressCorrectionTrack) {
        if(progressCorrectionTrack.getId() != null){
            JdglProgressCorrectionTrack query = new JdglProgressCorrectionTrack();
            query.setId(progressCorrectionTrack.getId());
            progressCorrectionTrack = jdglProgressCorrectionTrackMapper.getJdglProgressCorrectionTrack(query);
            syncInfoService.pushJdglProgressCorrectionTrack(progressCorrectionTrack);
        }else{
            List<JdglProgressCorrectionTrack> list = jdglProgressCorrectionTrackService.getJdglProgressCorrectionTrackList(new JdglProgressCorrectionTrack());
            syncInfoService.pushJdglProgressCorrectionTrack(list);
        }
        return AjaxResult.success();
    }

    /**
     * 进度填报测试用
     * @param
     * @return
     */
    @PostMapping("/jdglDaySchedule")
    public AjaxResult jdglDaySchedule(@RequestBody JdglDaySchedule jdglDaySchedule) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        List<JdglDaySchedule> list = jdglDayScheduleService.getListBy(new JdglDaySchedule());
        syncInfoService.pushJdglDaySchedule(list);
        return AjaxResult.success();
    }

    /**
     * 推送年度计划数据
     * @param yearPlan
     * @return
     */
    @PostMapping("pushJdglYearPlan")
    public AjaxResult pushJdglYearPlan(@RequestBody JdglYearPlan yearPlan){
        syncInfoService.pushJdglYearPlan(yearPlan);
        return AjaxResult.success();
    }

    @PostMapping("pushJdglMonthPlan")
    public AjaxResult pushJdglMonthPlan(@RequestBody JdglMonthPlan monthPlan){
        syncInfoService.pushJdglMonthPlan(monthPlan);
        return AjaxResult.success();
    }

    @PostMapping("pushJdglDaySchedule")
    public AjaxResult pushJdglDaySchedule(@RequestBody List<JdglDaySchedule> list){
        syncInfoService.pushJdglDaySchedule(list);
        return AjaxResult.success();
    }
}
