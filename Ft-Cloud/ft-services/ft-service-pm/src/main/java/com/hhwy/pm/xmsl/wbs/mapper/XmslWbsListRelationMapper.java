package com.hhwy.pm.xmsl.wbs.mapper;


import com.hhwy.pm.xmsl.wbs.domain.XmslWbsListRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-03 18:39:00
 * @remark
 */
public interface XmslWbsListRelationMapper {

    XmslWbsListRelation getXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation);

    List<XmslWbsListRelation> getXmslWbsListRelationList(XmslWbsListRelation xmslWbsListRelation);

    int insertXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation);

    int insertXmslWbsListRelationList(@Param("xmslWbsListRelationList") List<XmslWbsListRelation> xmslWbsListRelationList);

    int updateXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation);

    int updateXmslWbsListRelationList(@Param("xmslWbsListRelationList") List<XmslWbsListRelation> xmslWbsListRelationList);

    int deleteXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation);

    int deleteXmslWbsListRelationByPks(@Param("xmslWbsListRelationPkList") List<Long> xmslWbsListRelationPkList);
}
