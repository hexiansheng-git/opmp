package com.hhwy.pm.gm.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.pm.gm.service.IGmThirdService;
import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.review.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 为总部版提供的接口
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2023/8/24 15:59   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2023/8/24 15:59    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
@RestController
@RequestMapping("/gmThird")
public class GmThirdController {
    @Autowired
    private IGmThirdService gmThirdService;

    /**
     * 前期策划工作计划功能
     * @param map  {planApprovalUnit,valid,pageNum,pageSize}
     * @return
     */
    @PostMapping("/workPlanList")
    public AjaxResult workPlanList(@RequestBody Map map) {
        TableDataInfo tableDataInfo = gmThirdService.workPlanList(map);
        return AjaxResult.success(tableDataInfo);
    }
    
    /**
     * 前期策划评审功能
     * @param map {planStage,tenantKeys}
     * @return
     */
    @PostMapping("/reviewList")
    public AjaxResult reviewList(@RequestBody Map map) {
        Map<String,List<Review>> resuMap = gmThirdService.reviewList(map);
        return AjaxResult.success(resuMap);
    }

    /**
     * 所有租户下的前期策划执行检查统计信息
     * @return
     */
    @PostMapping("/inspectionSummaryList")
    public AjaxResult tenantSummaryList() {
        List<Map> list = gmThirdService.inspectionSummaryList();
        return AjaxResult.success(list);
    }

    /**
     * 前期策划执行检查信息
     * @param map
     * @return
     */
    @PostMapping("/inspectionList")
    public AjaxResult inspectionList(@RequestBody Map map) {
        List<QqchPerformInspection> list = gmThirdService.inspectionList(map);
        return AjaxResult.success(list);
    }
    

    /**
     * 前期策划执行检查
     * @param map {tenantKeys}
     * @return
     */
    @PostMapping("/evaluationList")
    public AjaxResult evaluationList(@RequestBody Map map) {
        List<QqchSummaryEvaluation> list = gmThirdService.evauluationList(map);
        return AjaxResult.success(list);
    }
}
