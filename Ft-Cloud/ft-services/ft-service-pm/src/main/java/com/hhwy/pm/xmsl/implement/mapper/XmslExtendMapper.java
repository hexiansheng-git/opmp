package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslExtend;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:35
 * @remark
 */
public interface XmslExtendMapper {

    XmslExtend getXmslExtend(XmslExtend xmslExtend);

    List<XmslExtend> getXmslExtendList(XmslExtend xmslExtend);

    int insertXmslExtend(XmslExtend xmslExtend);

    int insertXmslExtendList(@Param("xmslExtendList") List<XmslExtend> xmslExtendList);

    int updateXmslExtend(XmslExtend xmslExtend);

    int updateXmslExtendList(@Param("list") List<XmslExtend> xmslExtendList);

    int deleteXmslExtend(XmslExtend xmslExtend);

    int deleteXmslExtendByPks(@Param("xmslExtendPkList") List<Long> xmslExtendPkList);
}
