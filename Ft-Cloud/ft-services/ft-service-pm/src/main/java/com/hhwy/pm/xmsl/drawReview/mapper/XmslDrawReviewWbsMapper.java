package com.hhwy.pm.xmsl.drawReview.mapper;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-07 11:31:56
 * @remark
 */
public interface XmslDrawReviewWbsMapper {

    XmslDrawReviewWbs getXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    List<XmslDrawReviewWbs> getXmslDrawReviewWbsList(XmslDrawReviewWbs xmslDrawReviewWbs);

    int insertXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    int insertXmslDrawReviewWbsList(@Param("xmslDrawReviewWbsList") List<XmslDrawReviewWbs> xmslDrawReviewWbsList);

    int updateXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    int updateXmslDrawReviewWbsList(@Param("xmslDrawReviewWbsList") List<XmslDrawReviewWbs> xmslDrawReviewWbsList);

    int deleteXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs);

    int deleteXmslDrawReviewWbsByPks(@Param("xmslDrawReviewWbsPkList") List<Long> xmslDrawReviewWbsPkList);
}
