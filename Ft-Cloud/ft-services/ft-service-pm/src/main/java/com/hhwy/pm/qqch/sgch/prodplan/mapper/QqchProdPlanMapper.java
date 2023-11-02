package com.hhwy.pm.qqch.sgch.prodplan.mapper;

import com.hhwy.pm.qqch.sgch.prodplan.domain.QqchProdPlan;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-17 16:20:08
 * @remark
 */
public interface QqchProdPlanMapper {

    QqchProdPlan getQqchProdPlan(QqchProdPlan qqchProdPlan);

    List<QqchProdPlan> getQqchProdPlanList(QqchProdPlan qqchProdPlan);

    int insertQqchProdPlan(QqchProdPlan qqchProdPlan);

    int insertQqchProdPlanList(@Param("qqchProdPlanList") List<QqchProdPlan> qqchProdPlanList);

    int updateQqchProdPlan(QqchProdPlan qqchProdPlan);

    int updateQqchProdPlanList(@Param("qqchProdPlanList") List<QqchProdPlan> qqchProdPlanList);

    int deleteQqchProdPlan(QqchProdPlan qqchProdPlan);

    int deleteQqchProdPlanByPks(@Param("qqchProdPlanPkList") List<Long> qqchProdPlanPkList);

    int deleteQqchProdPlanByVersion(@Param("version") BigDecimal version);

    BigDecimal getQqchProdPlanAmt4DateRange(@Param("version") BigDecimal version, @Param("start") Date start,@Param("end") Date end);
}
