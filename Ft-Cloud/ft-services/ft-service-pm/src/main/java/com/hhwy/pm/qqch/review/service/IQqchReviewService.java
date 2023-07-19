package com.hhwy.pm.qqch.review.service;

import java.util.List;
import java.util.Map;

import com.hhwy.pm.qqch.review.domain.Review;

/**
 * @author mls
 * @date 2023-07-18 09:58:24
 * @remark
 */
public interface IQqchReviewService {

    Review getQqchReview(Review review);

    List<Review> getQqchReviewList(Review review);

    int insertQqchReview(Review review);


    /**
     * 保存信息
     * @param review 
     * @return
     */
    int savePlan(Long workPlanId);
    int insertQqchReviewList(List<Review> reviewList);

    int updateQqchReview(Review review);

    int updateQqchReviewList(List<Review> reviewList);

    int deleteQqchReview(Review review);

    int deleteQqchReviewByPks(List<Long> qqchReviewPkList);

    /**
     * @param map 
     * @return
     */
    Review reviewInfo(Map<String, String> map);

    void incrFinishNum(String stage);

    public void canAdjust();

    void listener(Long id);
}
