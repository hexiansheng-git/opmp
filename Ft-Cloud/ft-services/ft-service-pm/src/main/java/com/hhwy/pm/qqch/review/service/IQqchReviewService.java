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
     *
     * @param review
     * @return
     */
    void savePlan(Long workPlanId);

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

    /**
     * 确认更新阶段确认功能数量
     *
     * @param stageIdentity  阶段
     * @param moduleIdentity 模块唯一Id
     */
    void updateFinishNum(String stageIdentity, String moduleIdentity);

    public void canAdjust();

    void listener(Long id);


    /**
     * 获取到了那个阶段
     *
     * @return
     */
    String getStage();
}
