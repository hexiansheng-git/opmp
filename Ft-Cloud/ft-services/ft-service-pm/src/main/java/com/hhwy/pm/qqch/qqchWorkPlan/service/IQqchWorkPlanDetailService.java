package com.hhwy.pm.qqch.qqchWorkPlan.service;

import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;

import java.util.List;

/**
 * @author hwj
 * @date 2023-07-14 17:15:57
 * @remark
 */
public interface IQqchWorkPlanDetailService {

    QqchWorkPlanDetail getQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail);

    List<QqchWorkPlanDetail> getQqchWorkPlanDetailList(QqchWorkPlanDetail qqchWorkPlanDetail);

    int insertQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail);

    int insertQqchWorkPlanDetailList(List<QqchWorkPlanDetail> qqchWorkPlanDetailList);

    int updateQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail);

    int updateQqchWorkPlanDetailList(List<QqchWorkPlanDetail> qqchWorkPlanDetailList);

    int deleteQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail);

    int deleteQqchWorkPlanDetailByPks(List<Long> qqchWorkPlanDetailPkList);

    /**
     * 新增和编辑 数据保存
     * @return 结果
     */
    int insertOrEditBatchByMainId(List<QqchWorkPlanDetail> list, Long mainId);

    /**
     * 根据阶段获取当前阶段所有的编制人（用户名）
     *
     * @param planStage
     * @return
     */
    List<String> getEditorListByPlanStage(String planStage);

}
