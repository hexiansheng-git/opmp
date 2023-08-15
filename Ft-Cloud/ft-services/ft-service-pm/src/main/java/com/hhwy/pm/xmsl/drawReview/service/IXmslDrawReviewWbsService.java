package com.hhwy.pm.xmsl.drawReview.service;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;

import java.util.List;
import java.util.Set;

/**
 * @author wk
 * @date 2023-08-07 11:31:56
 * @remark
 */
public interface IXmslDrawReviewWbsService {

    XmslDrawReviewWbs getXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    /**
     * 获取全部有效的wbs
     * @return
     */
    List<XmslDrawReviewWbs> getFullEffectList();

    List<XmslDrawReviewWbs> getXmslDrawReviewWbsList(XmslDrawReviewWbs xmslDrawReviewWbs);

    List<XmslDrawReviewWbs> getByIds(Set<Long> idSet);

    int insertXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    int insertXmslDrawReviewWbsList(List<XmslDrawReviewWbs> xmslDrawReviewWbsList);

    int updateXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    int updateXmslDrawReviewWbsList(List<XmslDrawReviewWbs> xmslDrawReviewWbsList);

    int deleteXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    int deleteXmslDrawReviewWbsByPks(List<Long> xmslDrawReviewWbsPkList);
}
