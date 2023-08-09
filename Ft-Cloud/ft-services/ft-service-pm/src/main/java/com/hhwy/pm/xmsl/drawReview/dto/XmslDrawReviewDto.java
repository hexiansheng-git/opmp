package com.hhwy.pm.xmsl.drawReview.dto;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;
import lombok.Data;

import java.util.List;

@Data
public class XmslDrawReviewDto extends XmslDrawReview {
    private Integer version;
    private Long mainId;
    private String listCode;
    private Long listId;
    private String wbsCode;
    private Long wbsId;

    private List<XmslDrawReviewWbs> wbsList;
    private List<XmslDrawReviewList> list;
}
