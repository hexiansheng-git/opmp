package com.hhwy.pm.xmsl.xmslMaterialReport.service;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.xmslMaterialReport.domain.XmslMaterialReport;

import java.util.List;

/**
 * 主材报表
 * @author wk
 * @date 2023-08-15 17:39:46
 * @remark
 */
public interface IXmslMaterialReportService {

    XmslMaterialReport getXmslMaterialReport(XmslMaterialReport xmslMaterialReport);

    List<XmslMaterialReport> getXmslMaterialReportList(XmslMaterialReport xmslMaterialReport);

    int insertXmslMaterialReport(XmslMaterialReport xmslMaterialReport);

    int insertXmslMaterialReportList(List<XmslMaterialReport> xmslMaterialReportList);

    int updateXmslMaterialReport(XmslMaterialReport xmslMaterialReport);


    int deleteXmslMaterialReport(XmslMaterialReport xmslMaterialReport);
    
    void sync(Long drawReviewId,String tenangKey);

}
