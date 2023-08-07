package com.hhwy.pm.xmsl.drawReview.service;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-07 11:31:56
 * @remark
 */
public interface IXmslDrawReviewWbsService {

    XmslDrawReviewWbs getXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    List<XmslDrawReviewWbs> getXmslDrawReviewWbsList(XmslDrawReviewWbs xmslDrawReviewWbs);

    int insertXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    int insertXmslDrawReviewWbsList(List<XmslDrawReviewWbs> xmslDrawReviewWbsList);

    int updateXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    int updateXmslDrawReviewWbsList(List<XmslDrawReviewWbs> xmslDrawReviewWbsList);

    int deleteXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    int deleteXmslDrawReviewWbsByPks(List<Long> xmslDrawReviewWbsPkList);
}
