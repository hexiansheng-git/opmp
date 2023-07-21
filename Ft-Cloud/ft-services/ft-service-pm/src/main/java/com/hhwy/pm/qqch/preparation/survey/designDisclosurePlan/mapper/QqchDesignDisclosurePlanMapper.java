package com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.mapper;

import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.domain.QqchDesignDisclosurePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:47:23
 * @remark 2.4 设计交底策划
 */
public interface QqchDesignDisclosurePlanMapper {

    QqchDesignDisclosurePlan getQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan);

    List<QqchDesignDisclosurePlan> getQqchDesignDisclosurePlanList(QqchDesignDisclosurePlan qqchDesignDisclosurePlan);

    int insertQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan);

    int insertQqchDesignDisclosurePlanList(@Param("qqchDesignDisclosurePlanList") List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList);

    int updateQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan);

    int updateQqchDesignDisclosurePlanList(@Param("qqchDesignDisclosurePlanList") List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList);

    int deleteQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan);

    int deleteQqchDesignDisclosurePlanByPks(@Param("qqchDesignDisclosurePlanPkList") List<Long> qqchDesignDisclosurePlanPkList);
}
