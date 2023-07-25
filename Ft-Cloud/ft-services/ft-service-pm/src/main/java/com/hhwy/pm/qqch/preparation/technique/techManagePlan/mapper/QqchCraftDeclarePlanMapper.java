package com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchCraftDeclarePlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:40:02
 * @remark
 */
@Repository
public interface QqchCraftDeclarePlanMapper {

    QqchCraftDeclarePlan getQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    List<QqchCraftDeclarePlan> getQqchCraftDeclarePlanList(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int insertQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int insertQqchCraftDeclarePlanList(@Param("qqchCraftDeclarePlanList") List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList);

    int updateQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int updateQqchCraftDeclarePlanList(@Param("qqchCraftDeclarePlanList") List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList);

    int deleteQqchCraftDeclarePlan(QqchCraftDeclarePlan qqchCraftDeclarePlan);

    int deleteQqchCraftDeclarePlanByPks(@Param("qqchCraftDeclarePlanPkList") List<Long> qqchCraftDeclarePlanPkList);
}
