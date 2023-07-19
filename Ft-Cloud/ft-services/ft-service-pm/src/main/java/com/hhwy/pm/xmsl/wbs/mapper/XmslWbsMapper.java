package com.hhwy.pm.xmsl.wbs.mapper;

import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * wbs-最新
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
public interface XmslWbsMapper {

    XmslWbs getXmslWbs(XmslWbs xmslWbs);

    List<XmslWbs> getXmslWbsList(XmslWbs xmslWbs);

    Long countByWbs(XmslWbs wbs);

    /**
     * 判断当前是否有生效的wbs
     * @return
     */
    int hasEffectWbs();

    int insertXmslWbs(XmslWbs xmslWbs);

    int insertXmslWbsList(@Param("xmslWbsList") List<XmslWbs> xmslWbsList);

    int updateXmslWbs(XmslWbs xmslWbs);

    int updateXmslWbsList(@Param("xmslWbsList") List<XmslWbs> xmslWbsList);

    int deleteXmslWbs(XmslWbs xmslWbs);

    int deleteXmslWbsByPks(@Param("xmslWbsPkList") List<Long> xmslWbsPkList);

    int deleteByParentIds(@Param("list") Collection<Long> list);
}
