package com.hhwy.pm.xmsl.xmslEngineeringReport.mapper;

import com.hhwy.pm.xmsl.xmslEngineeringReport.domain.XmslEngineeringReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-14 13:48:27
 * @remark
 */
public interface XmslEngineeringReportMapper {

    XmslEngineeringReport getXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport);

    List<XmslEngineeringReport> getListByWbsCodes(@Param("reportType") Integer reportType,@Param("wbsCodes") String[] wbsCodes);
    List<XmslEngineeringReport> getListByParentIds(@Param("reportType") Integer reportType,@Param("parentIds") Long[] parentIds);
    
    List<XmslEngineeringReport> getXmslEngineeringReportList(XmslEngineeringReport xmslEngineeringReport);

    List<XmslEngineeringReport> getId(XmslEngineeringReport xmslEngineeringReport);

    int insertXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport);

    int insertXmslEngineeringReportList(@Param("xmslEngineeringReportList") List<XmslEngineeringReport> xmslEngineeringReportList);

    int updateXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport);

    int updateXmslEngineeringReportList(@Param("xmslEngineeringReportList") List<XmslEngineeringReport> xmslEngineeringReportList);

    int deleteXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport);

    int deleteXmslEngineeringReportByPks(@Param("xmslEngineeringReportPkList") List<Long> xmslEngineeringReportPkList);

    int deleteAll();
}
