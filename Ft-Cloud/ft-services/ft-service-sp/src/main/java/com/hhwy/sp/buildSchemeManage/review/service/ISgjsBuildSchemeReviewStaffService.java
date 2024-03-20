package com.hhwy.sp.buildSchemeManage.review.service;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewStaff;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:55
 * @remark
 */
public interface ISgjsBuildSchemeReviewStaffService {

    SgjsBuildSchemeReviewStaff getSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    List<SgjsBuildSchemeReviewStaff> getSgjsBuildSchemeReviewStaffList(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    int insertSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    int insertSgjsBuildSchemeReviewStaffList(List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffList);

    int updateSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    int updateSgjsBuildSchemeReviewStaffList(List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffList);

    int deleteSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    int deleteSgjsBuildSchemeReviewStaffByPks(List<Long> sgjsBuildSchemeReviewStaffPkList);
}
