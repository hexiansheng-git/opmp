package com.hhwy.pm.qqch.preparation.survey.designCheckPlan.mapper;

import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.QqchDesignCheckPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:48:41
 * @remark  2.3.3 设计成果验收计划
 */
public interface QqchDesignCheckPlanMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                        
    QqchDesignCheckPlan getQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan);

    List<QqchDesignCheckPlan> getQqchDesignCheckPlanList(QqchDesignCheckPlan qqchDesignCheckPlan);

    int insertQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan);

    int insertQqchDesignCheckPlanList(@Param("qqchDesignCheckPlanList") List<QqchDesignCheckPlan> qqchDesignCheckPlanList);

    int updateQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan);

    int updateQqchDesignCheckPlanList(@Param("list") List<QqchDesignCheckPlan> qqchDesignCheckPlanList);
    
    int deleteQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan);

    int deleteQqchDesignCheckPlanByPks(@Param("qqchDesignCheckPlanPkList") List<Long> qqchDesignCheckPlanPkList);
    }
