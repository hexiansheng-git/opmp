package com.hhwy.pm.gm.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.gm.qqch.service.IGmThirdService;
import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
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
