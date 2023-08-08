package com.hhwy.pm.xmsl.drawReview.service;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewRelation;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-07 11:35:16
 * @remark
 */
public interface IXmslDrawReviewRelationService {

    XmslDrawReviewRelation getXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation);

    List<XmslDrawReviewRelation> getXmslDrawReviewRelationList(XmslDrawReviewRelation xmslDrawReviewRelation);

    int insertXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation);

    int insertXmslDrawReviewRelationList(List<XmslDrawReviewRelation> xmslDrawReviewRelationList);

    int updateXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation);

    int updateXmslDrawReviewRelationList(List<XmslDrawReviewRelation> xmslDrawReviewRelationList);

    int deleteXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation);

    int deleteXmslDrawReviewRelationByPks(List<Long> xmslDrawReviewRelationPkList);
}
