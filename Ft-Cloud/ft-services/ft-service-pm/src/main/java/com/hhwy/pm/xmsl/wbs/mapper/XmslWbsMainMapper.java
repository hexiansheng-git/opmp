package com.hhwy.pm.xmsl.wbs.mapper;

import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.push.bean.WbsInfoVoBean;
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

    Integer getMaxVersion();

    Long getXmslWbsMainCount(XmslWbsMain xmslWbsMain);

    int insertXmslWbsMain(XmslWbsMain xmslWbsMain);

    int insertXmslWbsMainList(@Param("xmslWbsMainList") List<XmslWbsMain> xmslWbsMainList);

    /**
     * 插入wbs导入历史
     * @param map {levels}
     * @return
     */
    int insertWbsToHistory(Map map);

    int insertHistoryToWbs(Long mainId);

    int updateXmslWbsMain(XmslWbsMain xmslWbsMain);

    int updateXmslWbsMainList(@Param("xmslWbsMainList") List<XmslWbsMain> xmslWbsMainList);

    /**
     * 更新valid
     * @param id
     * @return
     */
    int updateValid(Long id);

    /**
     * 更新xmsl_wbs的p6编号（ptVar4）
     * @param list
     * @return
     */
    int updateWbsP6Code(List<WbsInfoVoBean> list);
    //更新xmsl_wbs_history的p6编号（ptVar4）
    int updateWbsHisP6Code(List<WbsInfoVoBean> list);

    int deleteXmslWbsMain(XmslWbsMain xmslWbsMain);

    int deleteXmslWbsMainByPks(@Param("xmslWbsMainPkList") List<Long> xmslWbsMainPkList);

    int deleteWbs();
    int deleteWbsHitoryByMainId(Long mainId);

    /**
     * 逻辑删除wbsMain
     * @param id
     * @return
     */
    int deleteLogic(Long id);
    int deleteHistoryLogic(Long id);
    int deleteRelation(Long id);
}
