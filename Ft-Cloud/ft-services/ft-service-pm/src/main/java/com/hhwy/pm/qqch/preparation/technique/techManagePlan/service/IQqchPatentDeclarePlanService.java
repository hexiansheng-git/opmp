package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchPatentDeclarePlan;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:40:39
 * @remark
 */
public interface IQqchPatentDeclarePlanService {

    QqchPatentDeclarePlan getQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    List<QqchPatentDeclarePlan> getQqchPatentDeclarePlanList(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int insertQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int insertQqchPatentDeclarePlanList(List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList);

    int updateQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int updateQqchPatentDeclarePlanList(List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList);

    int deleteQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int deleteQqchPatentDeclarePlanByPks(List<Long> qqchPatentDeclarePlanPkList);
}
