package com.hhwy.pm.qqch.sgch.qqchconst.service;

import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyParam;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.vo.QqchSurveyDesignTeamsVo;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstFacilityPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstJob;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author mls
 * @date 2023-08-03 16:08:12
 * @remark
 */
public interface IQqchConstFacilityPlanService {

    QqchConstFacilityPlan getQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan);

    List<QqchConstFacilityPlan> getQqchConstFacilityPlanList(QqchConstFacilityPlan qqchConstFacilityPlan);

    int insertQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan);

    int insertQqchConstFacilityPlanList(List<QqchConstFacilityPlan> qqchConstFacilityPlanList);

    int updateQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan);

    int updateQqchConstFacilityPlanList(List<QqchConstFacilityPlan> qqchConstFacilityPlanList);

    int deleteQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan);

    int deleteQqchConstFacilityPlanByPks(List<Long> qqchConstFacilityPlanPkList);

    void saveList(List<QqchConstFacilityPlan> iFacList);

    /**
     * @param dto 
     */
    List<QqchConstFacilityPlan> list(QqchConstFacilityPlan dto);

    Map queryDevicePlanListByConstDesc(List<QqchSurveyParam> param, BigDecimal version);

    QqchSurveyDesignTeamsVo dataSync(QqchSurveyDesignTeamsVo qqchSurveyDesignTeamsVo);
}
