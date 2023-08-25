package com.hhwy.pm.gm.service;

import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.review.domain.Review;

import java.util.List;
import java.util.Map;

/**
 * 为总部版前期策划提供的接口
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2023/8/24 15:55   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2023/8/24 15:55    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
public interface IGmThirdService {

    /**
     * 前期策划评审
     * @param map {planStage,tenantKeys}
     * @return {租户标志:评审对象集合}
     */
    public Map<String, List<Review>> reviewList(Map map);

    /**
     * 查询所有租户下的前期策划执行检查统计信息
     * @return {tenantKey,snum,centerNum,checkDate}
     */
    List<Map> inspectionSummaryList();

    /**
     * 查询 前期策划执行检查
     * @param map {checkUnit,checkPersonName,checkUnitFlag(1:海外事业部,2:区域中心),tenantKeys}
     * @return
     */
    List<QqchPerformInspection> inspectionList(Map map);

    /**
     * 前期策划总结评价
     * @param map
     * @return
     */
    public List<QqchSummaryEvaluation> evauluationList(Map map);
}
