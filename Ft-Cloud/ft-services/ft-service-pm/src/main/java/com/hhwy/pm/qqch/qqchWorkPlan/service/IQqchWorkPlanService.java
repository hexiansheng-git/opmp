package com.hhwy.pm.qqch.qqchWorkPlan.service;

import java.util.List;
import java.util.Map;

import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;

/**
 * @author hwj
 * @date 2023-07-12 15:30:52
 * @remark
 */
public interface IQqchWorkPlanService {

    /**
     * 新增 编辑 详情数据回显
     *
     * @param map 参数
     * @return
     */
    BaseEntity baseInfo(Map<String, String> map);
    QqchWorkPlan getQqchWorkPlan(QqchWorkPlan qqchWorkPlan);

    List<QqchWorkPlan> getQqchWorkPlanList(QqchWorkPlan qqchWorkPlan);

    Long insertQqchWorkPlan(QqchWorkPlan qqchWorkPlan);
    Long submitQqchWorkPlan(QqchWorkPlan qqchWorkPlan);

    int insertQqchWorkPlanList(List<QqchWorkPlan> qqchWorkPlanList);

    int updateQqchWorkPlan(QqchWorkPlan qqchWorkPlan);
    Long adjustQqchWorkPlan(QqchWorkPlan qqchWorkPlan);

    int updateQqchWorkPlanList(List<QqchWorkPlan> qqchWorkPlanList);

    int deleteQqchWorkPlan(QqchWorkPlan qqchWorkPlan);

    int deleteQqchWorkPlanByPks(List<Long> qqchWorkPlanPkList);

    /**
     * 获取租户下的前期策划工作计划
     * @param plan {}
     * @return
     */
    List<QqchWorkPlan> planListByTenantKey(QqchWorkPlan plan);
}
