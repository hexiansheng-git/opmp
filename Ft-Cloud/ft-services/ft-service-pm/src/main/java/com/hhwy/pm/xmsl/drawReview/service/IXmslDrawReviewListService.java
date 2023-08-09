package com.hhwy.pm.xmsl.drawReview.service;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;

import java.util.Collection;
import java.util.List;

/**
 * @author wk
 * @date 2023-08-07 11:35:12
 * @remark
 */
public interface IXmslDrawReviewListService {

    XmslDrawReviewList getXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList);

    List<XmslDrawReviewList> getXmslDrawReviewListList(XmslDrawReviewList xmslDrawReviewList);

    List<XmslDrawReviewList> getByIds(Collection collection);

    int insertXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList);

    int insertXmslDrawReviewListList(List<XmslDrawReviewList> xmslDrawReviewListList);

    int updateXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList);

    int updateXmslDrawReviewListList(List<XmslDrawReviewList> xmslDrawReviewListList);

    int deleteXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList);

    int deleteXmslDrawReviewListByPks(List<Long> xmslDrawReviewListPkList);
}
