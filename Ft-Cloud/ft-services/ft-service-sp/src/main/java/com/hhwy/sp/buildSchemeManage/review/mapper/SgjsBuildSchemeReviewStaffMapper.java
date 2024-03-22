package com.hhwy.sp.buildSchemeManage.review.mapper;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewStaff;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:55
 * @remark
 */
@Repository
public interface SgjsBuildSchemeReviewStaffMapper {

    SgjsBuildSchemeReviewStaff getSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    List<SgjsBuildSchemeReviewStaff> getSgjsBuildSchemeReviewStaffList(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    /**
     * 根据施工方案评审表主键id查询数据
     * @param reviewId
     * @return
     */
    List<SgjsBuildSchemeReviewStaff> getListByReviewId(@Param("reviewId") Long reviewId,@Param("flowNodeMark")String flowNodeMark);

    List<SgjsBuildSchemeReviewStaff> getOneByReviewStaffId(@Param("reviewId") Long reviewId,@Param("reviewStaffId") String reviewStaffId);

    List<SgjsBuildSchemeReviewStaff> getListByReviewIdGroupByUser(@Param("reviewId") Long reviewId, @Param("flowNodeMark") String flowNodeMark);

    int insertSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    int insertSgjsBuildSchemeReviewStaffList(@Param("sgjsBuildSchemeReviewStaffList") List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffList);

    int updateSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    int updateSgjsBuildSchemeReviewStaffList(@Param("list") List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffList);

    int deleteSgjsBuildSchemeReviewStaff(SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff);

    void deleteByReviewId(@Param("reviewId") Long reviewId);

    int deleteSgjsBuildSchemeReviewStaffByPks(@Param("sgjsBuildSchemeReviewStaffPkList") List<Long> sgjsBuildSchemeReviewStaffPkList);
}
