package com.hhwy.pm.qqch.preparation.technique.bimTechPlan.service;

import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.QqchBimTechPlanExtend;

import java.util.List;

/**
 * @author han
 * @date 2023-07-27 15:04:01
 * @remark
 */
public interface IQqchBimTechPlanExtendService {

    QqchBimTechPlanExtend getQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend);

    List<QqchBimTechPlanExtend> getQqchBimTechPlanExtendList(QqchBimTechPlanExtend qqchBimTechPlanExtend);

    void insertQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend);

    int insertQqchBimTechPlanExtendList(List<QqchBimTechPlanExtend> qqchBimTechPlanExtendList);

    int updateQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend);

    int updateQqchBimTechPlanExtendList(List<QqchBimTechPlanExtend> qqchBimTechPlanExtendList);

    int deleteQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend);

    int deleteQqchBimTechPlanExtendByPks(List<Long> qqchBimTechPlanExtendPkList);
}
