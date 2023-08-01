package com.hhwy.pm.qqch.preparation.technique.techTrainPlan.service;

import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.domain.QqchTechTrainPlan;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.domain.vo.QqchTechTrainPlanVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:57:39
 * @remark 技术培训策划
 */
public interface IQqchTechTrainPlanService {

    QqchTechTrainPlan getQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    List<QqchTechTrainPlan> getQqchTechTrainPlanList(QqchTechTrainPlan qqchTechTrainPlan);

    int insertQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    int updateQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    int updateQqchTechTrainPlanList(List<QqchTechTrainPlan> qqchTechTrainPlanList);

    int deleteQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan);

    int deleteQqchTechTrainPlanByPks(List<Long> qqchTechTrainPlanPkList);

    /**
     * 获取技术培训策划Vo
     * @param qqchTechTrainPlan
     * @return
     */
    QqchTechTrainPlanVo getQqchTechTrainPlanVo(QqchTechTrainPlan qqchTechTrainPlan);

    /**
     * 保存/确认/提交
     * @param qqchTechTrainPlanVo
     * @return
     */
    void save(QqchTechTrainPlanVo qqchTechTrainPlanVo);
}
