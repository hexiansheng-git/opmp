package com.hhwy.pm.xmsl.wbs.service;


import com.hhwy.pm.xmsl.wbs.domain.XmslWbsListRelation;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-03 18:39:00
 * @remark
 */
public interface IXmslWbsListRelationService {

    XmslWbsListRelation getXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation);

    List<XmslWbsListRelation> getXmslWbsListRelationList(XmslWbsListRelation xmslWbsListRelation);

    int insertXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation);

    int insertXmslWbsListRelationList(List<XmslWbsListRelation> xmslWbsListRelationList);

    int updateXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation);

    int updateXmslWbsListRelationList(List<XmslWbsListRelation> xmslWbsListRelationList);

    int deleteXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation);

    int deleteXmslWbsListRelationByPks(List<Long> xmslWbsListRelationPkList);
}
