package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAppInnovatePlan;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:47
 * @remark 四新应用及创新计划
 */
public interface IQqchAppInnovatePlanService {

    QqchAppInnovatePlan getQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    List<QqchAppInnovatePlan> getQqchAppInnovatePlanList(QqchAppInnovatePlan qqchAppInnovatePlan);

    int insertQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    int insertQqchAppInnovatePlanList(List<QqchAppInnovatePlan> qqchAppInnovatePlanList);

    int updateQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    int updateQqchAppInnovatePlanList(List<QqchAppInnovatePlan> qqchAppInnovatePlanList);

    int deleteQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    int deleteQqchAppInnovatePlanByPks(List<Long> qqchAppInnovatePlanPkList);
}
