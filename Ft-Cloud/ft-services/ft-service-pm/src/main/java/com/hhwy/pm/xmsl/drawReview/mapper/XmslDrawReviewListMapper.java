package com.hhwy.pm.xmsl.drawReview.mapper;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author wk
 * @date 2023-08-07 11:35:12
 * @remark
 */
public interface XmslDrawReviewListMapper {

    XmslDrawReviewList getXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList);

    List<XmslDrawReviewList> getXmslDrawReviewListList(XmslDrawReviewList xmslDrawReviewList);

    List<XmslDrawReviewList> getFullList(Integer version);

    List<XmslDrawReviewList> getByIds(@Param("ids") Collection collection);

    List<XmslDrawReviewList> getByCodes(@Param("mainId") Long mainId,@Param("listCodes") Collection collection);

    int insertXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList);

    int insertXmslDrawReviewListList(@Param("xmslDrawReviewListList") List<XmslDrawReviewList> xmslDrawReviewListList);

    int updateXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList);

    int updateXmslDrawReviewListList(@Param("list") List<XmslDrawReviewList> xmslDrawReviewListList);
    
    int updateParentId(@Param("list") List<XmslDrawReviewList> xmslDrawReviewListList);

    int deleteXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList);

    int deleteXmslDrawReviewListByPks(@Param("xmslDrawReviewListPkList") List<Long> xmslDrawReviewListPkList);
}
