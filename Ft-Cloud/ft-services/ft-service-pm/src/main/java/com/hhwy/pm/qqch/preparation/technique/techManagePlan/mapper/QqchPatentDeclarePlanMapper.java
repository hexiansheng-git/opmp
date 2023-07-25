package com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper;

import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchPatentDeclarePlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:40:39
 * @remark
 */
@Repository
public interface QqchPatentDeclarePlanMapper {

    QqchPatentDeclarePlan getQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    List<QqchPatentDeclarePlan> getQqchPatentDeclarePlanList(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int insertQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int insertQqchPatentDeclarePlanList(@Param("qqchPatentDeclarePlanList") List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList);

    int updateQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int updateQqchPatentDeclarePlanList(@Param("list") List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList);

    int deleteQqchPatentDeclarePlan(QqchPatentDeclarePlan qqchPatentDeclarePlan);

    int deleteQqchPatentDeclarePlanByPks(@Param("qqchPatentDeclarePlanPkList") List<Long> qqchPatentDeclarePlanPkList);
}
