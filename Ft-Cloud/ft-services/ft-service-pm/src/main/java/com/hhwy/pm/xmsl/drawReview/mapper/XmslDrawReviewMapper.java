package com.hhwy.pm.xmsl.drawReview.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;

/**
 * @author wk
 * @date 2023-08-07 11:23:32
 * @remark 
 */
public interface XmslDrawReviewMapper {
                                                                                                                                                                                                        
    XmslDrawReview getXmslDrawReview(XmslDrawReview xmslDrawReview);

    List<XmslDrawReview> getXmslDrawReviewList(XmslDrawReview xmslDrawReview);

    Integer getXmslDrawReviewCount(XmslDrawReview xmslDrawReview);

    XmslDrawReview getLast();

    int insertXmslDrawReview(XmslDrawReview xmslDrawReview);

    int insertXmslDrawReviewList(@Param("xmslDrawReviewList") List<XmslDrawReview> xmslDrawReviewList);

    int updateXmslDrawReview(XmslDrawReview xmslDrawReview);

    
    int deleteXmslDrawReview(XmslDrawReview xmslDrawReview);

}
