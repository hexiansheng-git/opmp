package com.hhwy.pm.qqch.sgch.qqchconst.mapper;

import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstFacilityPlan;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 16:08:12
 * @remark
 */
public interface QqchConstFacilityPlanMapper {

    QqchConstFacilityPlan getQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan);

    List<QqchConstFacilityPlan> getQqchConstFacilityPlanList(QqchConstFacilityPlan qqchConstFacilityPlan);

    int insertQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan);

    int insertQqchConstFacilityPlanList(@Param("qqchConstFacilityPlanList") List<QqchConstFacilityPlan> qqchConstFacilityPlanList);

    int updateQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan);

    int updateQqchConstFacilityPlanList(@Param("qqchConstFacilityPlanList") List<QqchConstFacilityPlan> qqchConstFacilityPlanList);

    int deleteQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan);

    int deleteQqchConstFacilityPlanByPks(@Param("qqchConstFacilityPlanPkList") List<Long> qqchConstFacilityPlanPkList);

    List<QqchConstFacilityPlan> getFacilityPlanByConstDesc(@Param("masterId13") Long masterId13, @Param("masterId213") Long masterId213, @Param("version") BigDecimal version);

}
