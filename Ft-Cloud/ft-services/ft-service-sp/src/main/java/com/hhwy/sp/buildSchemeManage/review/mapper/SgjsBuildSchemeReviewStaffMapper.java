package com.hhwy.sp.buildSchemeManage.review.mapper;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewStaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:55
 * @remark
 */
public interface SgjsBuildSchemeReviewStaffMapper {

    SgjsBuildSchemeReviewStaff getSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    List<SgjsBuildSchemeReviewStaff> getSgjsBuildSchemeReviewStaffList(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    int insertSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    int insertSgjsBuildSchemeReviewStaffList(@Param("sgjsBuildSchemeReviewStaffList") List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffList);

    int updateSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    int updateSgjsBuildSchemeReviewStaffList(@Param("list") List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffList);

    int deleteSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    int deleteSgjsBuildSchemeReviewStaffByPks(@Param("sgjsBuildSchemeReviewStaffPkList") List<Long> sgjsBuildSchemeReviewStaffPkList);
}
