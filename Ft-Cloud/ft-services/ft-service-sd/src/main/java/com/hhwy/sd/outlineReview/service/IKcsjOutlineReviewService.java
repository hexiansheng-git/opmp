package com.hhwy.sd.outlineReview.service;

import com.hhwy.sd.outlineReview.domain.KcsjOutlineReview;

import java.util.List;
import java.util.Map;

/**
 * 功能描述: 勘察设计 - 勘察设计大纲评审
 * @author fushudong
 * @date 2024-02-04 15:29:15
 */
public interface IKcsjOutlineReviewService {

    KcsjOutlineReview getKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    List<KcsjOutlineReview> getKcsjOutlineReviewList(KcsjOutlineReview kcsjOutlineReview);

    Long insertKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    int insertKcsjOutlineReviewList(List<KcsjOutlineReview> kcsjOutlineReviewList);

    void updateKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    int updateKcsjOutlineReviewList(List<KcsjOutlineReview> kcsjOutlineReviewList);

    int deleteKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview);

    int deleteKcsjOutlineReviewByPks(List<Integer> kcsjOutlineReviewPkList);

    KcsjOutlineReview getDetail(KcsjOutlineReview kcsjOutlineReviewParam);

    KcsjOutlineReview adjust(KcsjOutlineReview kcsjOutlineReviewParam);

    Map<String, Object> getExpertSuggest(KcsjOutlineReview param);

    void update(KcsjOutlineReview param);

    void doSendGm(String tenantKey, String admin);
}
