package com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.mapper;

import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ldd
 * @date 2023-07-31 16:38:26
 * @remark 
 */
public interface QqchLabourDemandPlanMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchLabourDemandPlan getQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan);

    List<QqchLabourDemandPlan> getQqchLabourDemandPlanList(QqchLabourDemandPlan qqchLabourDemandPlan);

    int insertQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan);

    int insertQqchLabourDemandPlanList(@Param("qqchLabourDemandPlanList") List<QqchLabourDemandPlan> qqchLabourDemandPlanList);

    int updateQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan);

    int updateQqchLabourDemandPlanList(@Param("qqchLabourDemandPlanList") List<QqchLabourDemandPlan> qqchLabourDemandPlanList);
    
    int deleteQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan);

     int deleteQqchLabourDemandPlanByPks(@Param("qqchLabourDemandPlanPkList") List<Long> qqchLabourDemandPlanPkList);

    List<QqchLabourDemandPlanDto> selectCount(QqchLabourDemandPlan qqchLabourDemandPlan);

    QqchLabourDemandPlan getQqchLabourDemandPlan1(QqchLabourDemandPlan qqchLabourDemandPlan);

    QqchLabourDemandPlan getQqchLabourDemandPlan2(QqchLabourDemandPlan qqchLabourDemandPlan);

    Map<String, Integer> personNumCalc(QqchLabourDemandPlan qqchLabourDemandPlanParam);

    List<Map> getWorkTeamList();

}
