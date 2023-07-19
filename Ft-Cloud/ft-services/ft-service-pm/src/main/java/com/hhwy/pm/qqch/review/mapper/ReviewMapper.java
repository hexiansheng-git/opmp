package com.hhwy.pm.qqch.review.mapper;

import java.util.List;

import com.hhwy.pm.qqch.review.domain.Review;
import org.apache.ibatis.annotations.Param;

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
}
