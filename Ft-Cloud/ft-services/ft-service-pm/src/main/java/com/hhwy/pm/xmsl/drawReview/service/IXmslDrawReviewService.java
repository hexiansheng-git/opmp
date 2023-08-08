package com.hhwy.pm.xmsl.drawReview.service;

import java.util.List;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;

/**
 * @author wk
 * @date 2023-08-07 11:23:32
 * @remark 
 */
public interface IXmslDrawReviewService {
                                                                                                                                                                                                        
    XmslDrawReview getXmslDrawReview(XmslDrawReview xmslDrawReview);

    List<XmslDrawReview> getXmslDrawReviewList(XmslDrawReview xmslDrawReview);

    XmslDrawReview getById(Long id);

    XmslDrawReview getLast();

    Integer hasChange();

    int insertXmslDrawReview(XmslDrawReview xmslDrawReview);

    int insertXmslDrawReviewList(List<XmslDrawReview> xmslDrawReviewList);

    int updateXmslDrawReview(XmslDrawReview xmslDrawReview);

    
    int deleteXmslDrawReview(XmslDrawReview xmslDrawReview);

}
