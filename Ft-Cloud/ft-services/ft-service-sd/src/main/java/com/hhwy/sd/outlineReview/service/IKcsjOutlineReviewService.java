package com.hhwy.sd.outlineReview.service;

import com.hhwy.sd.outlineReview.domain.KcsjOutlineReview;

import java.util.List;

/**
 * @author fushudong
 * @date 2024-02-04 15:29:15
 * @remark
 */
public interface IKcsjOutlineReviewService {

    KcsjOutlineReview getKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    List<KcsjOutlineReview> getKcsjOutlineReviewList(KcsjOutlineReview kcsjOutlineReview);

    int insertKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    int insertKcsjOutlineReviewList(List<KcsjOutlineReview> kcsjOutlineReviewList);

    int updateKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    int updateKcsjOutlineReviewList(List<KcsjOutlineReview> kcsjOutlineReviewList);

    int deleteKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    int deleteKcsjOutlineReviewByPks(List<Integer> kcsjOutlineReviewPkList);
}
