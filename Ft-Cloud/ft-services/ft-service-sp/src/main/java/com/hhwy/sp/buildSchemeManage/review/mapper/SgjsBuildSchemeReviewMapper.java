package com.hhwy.sp.buildSchemeManage.review.mapper;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeReviewQueryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2024-03-20 09:39:35
 * @remark
 */
@Repository
public interface SgjsBuildSchemeReviewMapper {

    SgjsBuildSchemeReview getSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    List<SgjsBuildSchemeReview> getSgjsBuildSchemeReviewList(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int insertSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int insertSgjsBuildSchemeReviewList(@Param("sgjsBuildSchemeReviewList") List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList);

    int updateSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int updateSgjsBuildSchemeReviewList(@Param("list") List<SgjsBuildSchemeReview> sgjsBuildSchemeReviewList);

    int deleteSgjsBuildSchemeReview(SgjsBuildSchemeReview sgjsBuildSchemeReview);

    int deleteSgjsBuildSchemeReviewByPks(@Param("sgjsBuildSchemeReviewPkList") List<Long> sgjsBuildSchemeReviewPkList);

    List<SgjsBuildSchemeReview> getListByQueryVo(BuildSchemeReviewQueryVo queryVo);

    SgjsBuildSchemeReview getById(@Param("id") Long id);

    List<SgjsBuildSchemeReview> getListByIds(@Param("ids") List<Long> ids);

    void updateTaskStatus(@Param("id") Long id,@Param("taskStatus") String taskStatus);

    void updateApprovalTime(@Param("id") Long id);

    void dismissedSchemeReview(@Param("id") Long id);

    void deleteById(@Param("id") Long id);
    
    @Update("update sgjs_build_scheme_review_staff set review_staff_name = #{nickName},review_staff_id=#{userName} where review_id=#{reviewId} and flow_node_mark=#{type}")
    int updateStaffUser(@Param("userName") String userName,@Param("nickName") String nickName,
                        @Param("reviewId") Long reviewId,@Param("type") String type);
}
