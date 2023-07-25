package com.hhwy.pm.qqch.preparation.survey.designCheckPlan.service;

import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.QqchDesignCheckPlan;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.vo.QqchDesignCheckPlanVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:48:41
 * @remark  2.3.3 设计成果验收计划
 */
public interface IQqchDesignCheckPlanService {
                                                                                                                                                                                                                                                                                                                                                                                                                                        

    List<QqchDesignCheckPlan> getQqchDesignCheckPlanList(QqchDesignCheckPlan qqchDesignCheckPlan);

    void save(QqchDesignCheckPlanVo qqchDesignCheckPlanVo);

    void confirm(QqchDesignCheckPlanVo qqchDesignCheckPlanVo);
}
