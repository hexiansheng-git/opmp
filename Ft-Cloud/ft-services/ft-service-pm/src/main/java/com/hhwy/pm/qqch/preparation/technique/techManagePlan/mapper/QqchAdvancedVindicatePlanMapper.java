package com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author han
 * @date 2023-07-27 15:51:15
 * @remark 高新维护计划
 */
@Repository
public interface QqchAdvancedVindicatePlanMapper {

    QqchAdvancedVindicatePlan getQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    List<QqchAdvancedVindicatePlan> getQqchAdvancedVindicatePlanList(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    int insertQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    int insertQqchAdvancedVindicatePlanList(@Param("qqchAdvancedVindicatePlanList") List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList);

    int updateQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    int updateQqchAdvancedVindicatePlanList(@Param("list") List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList);

    int deleteQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan);

    int deleteQqchAdvancedVindicatePlanByPks(@Param("qqchAdvancedVindicatePlanPkList") List<Long> qqchAdvancedVindicatePlanPkList);

    Date getMinStartDateByVersion(@Param("version") BigDecimal version);

    Date getMaxEndDateByVersion(@Param("version") BigDecimal version);
}
