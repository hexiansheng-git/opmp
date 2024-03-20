package com.hhwy.sp.buildSchemeManage.review.mapper;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeStaffOpinion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:59
 * @remark
 */
public interface SgjsBuildSchemeStaffOpinionMapper {

    SgjsBuildSchemeStaffOpinion getSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    List<SgjsBuildSchemeStaffOpinion> getSgjsBuildSchemeStaffOpinionList(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    int insertSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    int insertSgjsBuildSchemeStaffOpinionList(@Param("sgjsBuildSchemeStaffOpinionList") List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionList);

    int updateSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    int updateSgjsBuildSchemeStaffOpinionList(@Param("list") List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionList);

    int deleteSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    int deleteSgjsBuildSchemeStaffOpinionByPks(@Param("sgjsBuildSchemeStaffOpinionPkList") List<Long> sgjsBuildSchemeStaffOpinionPkList);
}
