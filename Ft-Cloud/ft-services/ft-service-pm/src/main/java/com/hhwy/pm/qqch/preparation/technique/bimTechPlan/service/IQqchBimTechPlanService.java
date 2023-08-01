package com.hhwy.pm.qqch.preparation.technique.bimTechPlan.service;

import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.QqchBimTechPlan;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.vo.QqchBimTechPlanVo;

import java.util.List;


/**
 * @author han
 * @date 2023-07-25 10:45:57
 * @remark BIM技术策划
 */
public interface IQqchBimTechPlanService {

    QqchBimTechPlan getQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    List<QqchBimTechPlan> getQqchBimTechPlanList(QqchBimTechPlan qqchBimTechPlan);

    int insertQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    int updateQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    int updateQqchBimTechPlanList(List<QqchBimTechPlan> qqchBimTechPlanList);

    int deleteQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan);

    int deleteQqchBimTechPlanByPks(List<Long> qqchBimTechPlanPkList);

    /**
     * 获取BIM技术策划Vo
     * @param qqchBimTechPlan
     * @return
     */
    QqchBimTechPlanVo getQqchBimTechPlanVo(QqchBimTechPlan qqchBimTechPlan);

    /**
     * 保存/确认/提交
     * @param qqchBimTechPlanVo
     * @return
     */
    void save(QqchBimTechPlanVo qqchBimTechPlanVo);
}
