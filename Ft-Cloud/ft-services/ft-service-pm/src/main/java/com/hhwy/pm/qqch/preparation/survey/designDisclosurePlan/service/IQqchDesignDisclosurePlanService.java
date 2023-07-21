package com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.service;

import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.domain.QqchDesignDisclosurePlan;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:47:23
 * @remark 2.4 设计交底策划
 */
public interface IQqchDesignDisclosurePlanService {

    QqchDesignDisclosurePlan getQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan);

    List<QqchDesignDisclosurePlan> getQqchDesignDisclosurePlanList(QqchDesignDisclosurePlan qqchDesignDisclosurePlan);

    int insertQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan);

    int insertQqchDesignDisclosurePlanList(List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList);

    int updateQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan);

    int updateQqchDesignDisclosurePlanList(List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList);

    int deleteQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan);

    int deleteQqchDesignDisclosurePlanByPks(List<Long> qqchDesignDisclosurePlanPkList);
}
