package com.hhwy.pm.qqch.sgch.qqchconst.mapper;

import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstStaffPlan;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 16:08:24
 * @remark
 */
public interface QqchConstStaffPlanMapper {

    QqchConstStaffPlan getQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan);

    List<QqchConstStaffPlan> getQqchConstStaffPlanList(QqchConstStaffPlan qqchConstStaffPlan);

    int insertQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan);

    int insertQqchConstStaffPlanList(@Param("qqchConstStaffPlanList") List<QqchConstStaffPlan> qqchConstStaffPlanList);

    int updateQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan);

    int updateQqchConstStaffPlanList(@Param("list") List<QqchConstStaffPlan> list);

    int deleteQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan);

    int deleteQqchConstStaffPlanByPks(@Param("qqchConstStaffPlanPkList") List<Long> qqchConstStaffPlanPkList);

    void deleteByVersion(@Param("version") BigDecimal version);
}
