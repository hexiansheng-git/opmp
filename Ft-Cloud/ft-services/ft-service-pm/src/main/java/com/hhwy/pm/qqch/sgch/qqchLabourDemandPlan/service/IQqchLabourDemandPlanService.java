package com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.service;

import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanDto;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanVo;

import java.util.List;
import java.util.Map;

/**
 * @author ldd
 * @date 2023-07-31 16:38:26
 * @remark 
 */
public interface IQqchLabourDemandPlanService {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchLabourDemandPlan getQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan);

    QqchLabourDemandPlanVo getQqchLabourDemandPlanList(QqchLabourDemandPlan qqchLabourDemandPlan);

    List<QqchLabourDemandPlan> getList4Word();

    int insertQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan);



    int updateQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan);

    int updateQqchLabourDemandPlanList(List<QqchLabourDemandPlan> qqchLabourDemandPlanList);
    
    int deleteQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan);

    int deleteQqchLabourDemandPlanByPks(List<Long> qqchLabourDemandPlanPkList);

    void save(QqchLabourDemandPlanVo qqchLabourDemandPlanVo);

    List<QqchLabourDemandPlanDto> selectCount(QqchLabourDemandPlan qqchLabourDemandPlan);


    List<String> getAllWorkType(QqchLabourDemandPlanVo qqchLabourDemandPlanVo);


    QqchLabourDemandPlanVo sychData(QqchLabourDemandPlanVo vo);

    Map<String, Integer> personNumCalc(QqchLabourDemandPlan qqchLabourDemandPlanParam);

    /**
     * @param qqchLabourDemandPlanParam 
     * @return
     */
    QqchLabourDemandPlanVo getQqchLabourDemandPlanListWithSearch(QqchLabourDemandPlan qqchLabourDemandPlanParam);

    List<Map> getWorkTeamList(String workTeam);

}
