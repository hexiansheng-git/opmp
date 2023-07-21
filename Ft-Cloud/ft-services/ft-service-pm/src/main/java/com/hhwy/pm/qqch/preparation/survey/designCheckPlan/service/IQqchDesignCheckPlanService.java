package com.hhwy.pm.qqch.preparation.survey.designCheckPlan.service;

import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.QqchDesignCheckPlan;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:48:41
 * @remark  2.3.3 设计成果验收计划
 */
public interface IQqchDesignCheckPlanService {
                                                                                                                                                                                                                                                                                                                                                                                                                                        
    QqchDesignCheckPlan getQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan);

    List<QqchDesignCheckPlan> getQqchDesignCheckPlanList(QqchDesignCheckPlan qqchDesignCheckPlan);

    int insertQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan);

    int insertQqchDesignCheckPlanList(List<QqchDesignCheckPlan> qqchDesignCheckPlanList);

    int updateQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan);

            int updateQqchDesignCheckPlanList(List<QqchDesignCheckPlan> qqchDesignCheckPlanList);
    
    int deleteQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan);

            int deleteQqchDesignCheckPlanByPks(List<Long> qqchDesignCheckPlanPkList);
    }
