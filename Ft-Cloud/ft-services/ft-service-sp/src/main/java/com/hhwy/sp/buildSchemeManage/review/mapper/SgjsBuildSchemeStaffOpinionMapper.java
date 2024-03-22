package com.hhwy.sp.buildSchemeManage.review.mapper;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeStaffOpinion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:59
 * @remark
 */
@Repository
public interface SgjsBuildSchemeStaffOpinionMapper {

    SgjsBuildSchemeStaffOpinion getSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    List<SgjsBuildSchemeStaffOpinion> getSgjsBuildSchemeStaffOpinionList(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    /**
     * 根据施工方案评审表主键id查询数据
     * @param reviewId
     * @return
     */
    List<SgjsBuildSchemeStaffOpinion> getListByReviewId(@Param("reviewId") Long reviewId);

    List<SgjsBuildSchemeStaffOpinion> getListByFlowNodeMarks(@Param("reviewId") Long reviewId,@Param("flowNodeMarks") String[] flowNodeMarks);

    List<SgjsBuildSchemeStaffOpinion> getListByReviewStaffIdList(@Param("reviewId") Long reviewId,@Param("reviewStaffIdList") List<String> reviewStaffIdList);

    int insertSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    int insertSgjsBuildSchemeStaffOpinionList(@Param("sgjsBuildSchemeStaffOpinionList") List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionList);

    int updateSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    int updateSgjsBuildSchemeStaffOpinionList(@Param("list") List<SgjsBuildSchemeStaffOpinion> sgjsBuildSchemeStaffOpinionList);

    int deleteSgjsBuildSchemeStaffOpinion(SgjsBuildSchemeStaffOpinion sgjsBuildSchemeStaffOpinion);

    void deleteByReviewId(@Param("reviewId") Long reviewId);

    int deleteSgjsBuildSchemeStaffOpinionByPks(@Param("sgjsBuildSchemeStaffOpinionPkList") List<Long> sgjsBuildSchemeStaffOpinionPkList);
}
