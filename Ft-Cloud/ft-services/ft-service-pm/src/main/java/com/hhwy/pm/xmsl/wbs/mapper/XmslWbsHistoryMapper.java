package com.hhwy.pm.xmsl.wbs.mapper;

import com.hhwy.pm.xmsl.wbs.domain.XmslWbsHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk
 * @date 2023-07-14 11:09:09
 * @remark
 */
public interface XmslWbsHistoryMapper {

    XmslWbsHistory getXmslWbsHistory(XmslWbsHistory xmslWbsHistory);

    List<XmslWbsHistory> getXmslWbsHistoryList(XmslWbsHistory xmslWbsHistory);

    int insertXmslWbsHistory(XmslWbsHistory xmslWbsHistory);

    int insertXmslWbsHistoryList(@Param("xmslWbsHistoryList") List<XmslWbsHistory> xmslWbsHistoryList);

    int updateXmslWbsHistory(XmslWbsHistory xmslWbsHistory);

    int updateXmslWbsHistoryList(@Param("list") List<XmslWbsHistory> xmslWbsHistoryList);

    int deleteXmslWbsHistory(XmslWbsHistory xmslWbsHistory);

    int deleteXmslWbsHistoryByPks(@Param("xmslWbsHistoryPkList") List<Long> xmslWbsHistoryPkList);

    int deleteByParentIds(@Param("list")List<Long> list);
}
