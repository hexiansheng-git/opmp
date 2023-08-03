package com.hhwy.pm.xmsl.wbs.service;


import com.hhwy.pm.xmsl.wbs.domain.XmslWbsHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk
 * @date 2023-07-14 11:09:09
 * @remark
 */
public interface IXmslWbsHistoryService {

    XmslWbsHistory getXmslWbsHistory(XmslWbsHistory xmslWbsHistory);

    List<XmslWbsHistory> getXmslWbsHistoryList(XmslWbsHistory xmslWbsHistory);

    int insertXmslWbsHistory(XmslWbsHistory xmslWbsHistory);

    int insertXmslWbsHistoryList(List<XmslWbsHistory> xmslWbsHistoryList);

    int updateXmslWbsHistory(XmslWbsHistory xmslWbsHistory);

    int updateXmslWbsHistoryList(List<XmslWbsHistory> xmslWbsHistoryList);

    int deleteXmslWbsHistory(XmslWbsHistory xmslWbsHistory);

    int deleteXmslWbsHistoryByPks(List<Long> xmslWbsHistoryPkList);

    int deleteByParentIds(List<Long> list);
}
