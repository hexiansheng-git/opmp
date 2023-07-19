package com.hhwy.pm.xmsl.wbs.mapper;

import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wk
 * @date 2023-07-13 18:02:49
 * @remark
 */
public interface XmslWbsMainMapper {

    XmslWbsMain getXmslWbsMain(XmslWbsMain xmslWbsMain);

    List<XmslWbsMain> getXmslWbsMainList(XmslWbsMain xmslWbsMain);

    /**
     * 获取最新数据
     * @param xmslWbsMain
     * @return
     */
    XmslWbsMain getLast(XmslWbsMain xmslWbsMain);

    Long getXmslWbsMainCount(XmslWbsMain xmslWbsMain);

    int insertXmslWbsMain(XmslWbsMain xmslWbsMain);

    int insertXmslWbsMainList(@Param("xmslWbsMainList") List<XmslWbsMain> xmslWbsMainList);

    int insertWbsToHistory();

    int insertHistoryToWbs(Long mainId);

    int updateXmslWbsMain(XmslWbsMain xmslWbsMain);

    int updateXmslWbsMainList(@Param("xmslWbsMainList") List<XmslWbsMain> xmslWbsMainList);

    /**
     * 更新valid
     * @param id
     * @return
     */
    int updateValid(Long id);

    int deleteXmslWbsMain(XmslWbsMain xmslWbsMain);

    int deleteXmslWbsMainByPks(@Param("xmslWbsMainPkList") List<Long> xmslWbsMainPkList);

    int deleteWbs();
    int deleteWbsHitoryByMainId(Long mainId);
}
