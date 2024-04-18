package com.hhwy.pm.xmsl.xmslEngineeringReport.service;


import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.xmslEngineeringReport.domain.XmslEngineeringReport;

import java.util.List;
import java.util.Map;

/**
 * 工程量报表
 * @author wk
 * @date 2023-08-14 13:48:27
 * @remark
 */
public interface IXmslEngineeringReportService {

    /**
     * 同步图纸复核数据到工程量报表
     */
    void sync(String tenantKey);
    
    XmslEngineeringReport getXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport);

    List<XmslEngineeringReport> getXmslEngineeringReportList(XmslEngineeringReport xmslEngineeringReport);
    
    List<XmslEngineeringReport> getList(XmslEngineeringReport report);

    int insertXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport);

    int insertXmslEngineeringReportList(List<XmslEngineeringReport> xmslEngineeringReportList);

    int updateXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport);

    int updateXmslEngineeringReportList(List<XmslEngineeringReport> xmslEngineeringReportList);

    int deleteXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport);

    int deleteXmslEngineeringReportByPks(List<Long> xmslEngineeringReportPkList);


    public List<XmslEngineeringReport> getTreeListByPid(XmslEngineeringReport report);

    /**
     * 根据wbs编号获取挂在的清单
     * @param wbsCode wbs编号 多个以逗号隔开
     * @return
     */
    public Map<String,List<XmslContractList>> relateListByWbsCode(String wbsCode);
}
