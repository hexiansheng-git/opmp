package com.hhwy.pm.xmsl.drawReview.mapper;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewRelation;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-07 11:35:16
 * @remark
 */
public interface XmslDrawReviewRelationMapper {

    XmslDrawReviewRelation getXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation);

    List<XmslDrawReviewRelation> getXmslDrawReviewRelationList(XmslDrawReviewRelation xmslDrawReviewRelation);

    int insertXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation);

    int insertXmslDrawReviewRelationList(@Param("xmslDrawReviewRelationList") List<XmslDrawReviewRelation> xmslDrawReviewRelationList);

    int updateXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation);

    int updateXmslDrawReviewRelationList(@Param("xmslDrawReviewRelationList") List<XmslDrawReviewRelation> xmslDrawReviewRelationList);

    int deleteXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation);

    int deleteXmslDrawReviewRelationByPks(@Param("xmslDrawReviewRelationPkList") List<Long> xmslDrawReviewRelationPkList);
}
