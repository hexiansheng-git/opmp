package com.hhwy.pm.xmsl.wbs.mapper;

import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk
 * @date 2023-07-13 18:02:49
 * @remark
 */
public interface XmslWbsMainMapper {

    XmslWbsMain getXmslWbsMain(XmslWbsMain xmslWbsMain);

    List<XmslWbsMain> getXmslWbsMainList(XmslWbsMain xmslWbsMain);

    int insertXmslWbsMain(XmslWbsMain xmslWbsMain);

    int insertXmslWbsMainList(@Param("xmslWbsMainList") List<XmslWbsMain> xmslWbsMainList);

    int updateXmslWbsMain(XmslWbsMain xmslWbsMain);

    int updateXmslWbsMainList(@Param("xmslWbsMainList") List<XmslWbsMain> xmslWbsMainList);

    int deleteXmslWbsMain(XmslWbsMain xmslWbsMain);

    int deleteXmslWbsMainByPks(@Param("xmslWbsMainPkList") List<Long> xmslWbsMainPkList);
}
