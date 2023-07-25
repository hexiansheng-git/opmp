package com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAppInnovatePlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:47
 * @remark 四新应用及创新计划
 */
@Repository
public interface QqchAppInnovatePlanMapper {

    QqchAppInnovatePlan getQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    List<QqchAppInnovatePlan> getQqchAppInnovatePlanList(QqchAppInnovatePlan qqchAppInnovatePlan);

    int insertQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    int insertQqchAppInnovatePlanList(@Param("qqchAppInnovatePlanList") List<QqchAppInnovatePlan> qqchAppInnovatePlanList);

    int updateQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    int updateQqchAppInnovatePlanList(@Param("list") List<QqchAppInnovatePlan> qqchAppInnovatePlanList);

    int deleteQqchAppInnovatePlan(QqchAppInnovatePlan qqchAppInnovatePlan);

    int deleteQqchAppInnovatePlanByPks(@Param("qqchAppInnovatePlanPkList") List<Long> qqchAppInnovatePlanPkList);
}
