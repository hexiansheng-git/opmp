package com.hhwy.pm.qqch.review.mapper;

import com.hhwy.pm.qqch.review.domain.Review;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-18 09:58:24
 * @remark
 */
public interface ReviewMapper {

    Review getQqchReview(Review review);

    List<Review> getQqchReviewList(Review review);

    int insertQqchReview(Review review);

    int insertQqchReviewList(@Param("reviewList") List<Review> reviewList);

    int updateQqchReview(Review review);

    int updateQqchReviewList(@Param("list") List<Review> list);

    int deleteQqchReview(Review review);

    int deleteQqchReviewByPks(@Param("reviewPkList") List<Long> reviewPkList);

    /**
     * 通过阶段获取前期策划评审数据
     * @param planStage
     * @return
     */
    Review getReviewByPlanStage(@Param("planStage") String planStage);

    /**
     * 根据阶段获取该阶段是否已编制完成
     * @param planStage
     * @return
     */
    int getFinishedReviewCountByStage(@Param("planStage") String planStage);

    /**
     * 获取当前正在审批的数据
     * @return
     */
    Review getApprovedData();
}
