package com.hhwy.pm.xmsl.xmslMaterialReport.mapper;

import com.hhwy.pm.xmsl.xmslMaterialReport.domain.XmslMaterialReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 主材报表
 * @author wk
 * @date 2023-08-15 17:39:46
 * @remark
 */
public interface XmslMaterialReportMapper {

    XmslMaterialReport getXmslMaterialReport(XmslMaterialReport xmslMaterialReport);

    List<XmslMaterialReport> getXmslMaterialReportList(XmslMaterialReport xmslMaterialReport);

    int insertXmslMaterialReport(XmslMaterialReport xmslMaterialReport);

    int insertXmslMaterialReportList(@Param("xmslMaterialReportList") List<XmslMaterialReport> xmslMaterialReportList);

    int updateXmslMaterialReport(XmslMaterialReport xmslMaterialReport);


    int deleteXmslMaterialReport(XmslMaterialReport xmslMaterialReport);

    int deleteAll();

}
