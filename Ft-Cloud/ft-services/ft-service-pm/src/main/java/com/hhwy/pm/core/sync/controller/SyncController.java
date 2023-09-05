package com.hhwy.pm.core.sync.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrack;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackService;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
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
    private ISysSyncInfoService syncInfoService;
    @Autowired
    private IJdglDiffAnalysisService jdglDiffAnalysisService;
    @Autowired
    private IJdglProgressCorrectionTrackService jdglProgressCorrectionTrackService;

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
     * 前期策划测试用
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
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        if(progressCorrectionTrack.getId() != null){
            JdglProgressCorrectionTrack query = new JdglProgressCorrectionTrack();
            query.setId(IdWorker.createId());
            progressCorrectionTrack = jdglProgressCorrectionTrackService.getJdglProgressCorrectionTrack(query);
            syncInfoService.pushJdglProgressCorrectionTrack(progressCorrectionTrack);
        }else{
            List<JdglProgressCorrectionTrack> list = jdglProgressCorrectionTrackService.getJdglProgressCorrectionTrackList(new JdglProgressCorrectionTrack());
            syncInfoService.pushJdglProgressCorrectionTrack(list);
        }
        return AjaxResult.success();
    }
}
