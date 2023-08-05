package com.hhwy.pm.xmsl.wbs.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsListRelation;
import com.hhwy.pm.xmsl.wbs.mapper.XmslWbsListRelationMapper;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsListRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-03 18:39:00
 * @remark
 */
@Service
public class XmslWbsListRelationServiceImpl implements IXmslWbsListRelationService {

    @Autowired
    private XmslWbsListRelationMapper xmslWbsListRelationMapper;


    public XmslWbsListRelation getXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation) {
        return xmslWbsListRelationMapper.getXmslWbsListRelation(xmslWbsListRelation);
    }

    public List<XmslWbsListRelation> getXmslWbsListRelationList(XmslWbsListRelation xmslWbsListRelation) {
        return xmslWbsListRelationMapper.getXmslWbsListRelationList(xmslWbsListRelation);
    }

    @Transactional
    public int insertXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation) {
        xmslWbsListRelation.setCreateUser(SecurityUtils.getUserName());
        xmslWbsListRelation.setCreateTime(DateUtils.getNowDate());
        return xmslWbsListRelationMapper.insertXmslWbsListRelation(xmslWbsListRelation);
    }

    @Transactional
    public int insertXmslWbsListRelationList(List<XmslWbsListRelation> xmslWbsListRelationList) {
        if(CollectionUtils.isEmpty(xmslWbsListRelationList))
            return 0;
        return xmslWbsListRelationMapper.insertXmslWbsListRelationList(xmslWbsListRelationList);
    }

    @Transactional
    public int updateXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation) {
        xmslWbsListRelation.setUpdateUser(SecurityUtils.getUserName());
        xmslWbsListRelation.setUpdateTime(DateUtils.getNowDate());
        return xmslWbsListRelationMapper.updateXmslWbsListRelation(xmslWbsListRelation);
    }

    @Transactional
    public int updateXmslWbsListRelationList(List<XmslWbsListRelation> xmslWbsListRelationList) {
        for (XmslWbsListRelation xmslWbsListRelation : xmslWbsListRelationList) {
            xmslWbsListRelation.setUpdateUser(SecurityUtils.getUserName());
            xmslWbsListRelation.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslWbsListRelationMapper.updateXmslWbsListRelationList(xmslWbsListRelationList);
    }

    @Transactional
    public int deleteXmslWbsListRelation(XmslWbsListRelation xmslWbsListRelation) {
        xmslWbsListRelation.setUpdateUser(SecurityUtils.getUserName());
        xmslWbsListRelation.setUpdateTime(DateUtils.getNowDate());
        return xmslWbsListRelationMapper.deleteXmslWbsListRelation(xmslWbsListRelation);
    }

    @Transactional
    public int deleteXmslWbsListRelationByPks(List<Long> xmslWbsListRelationPkList) {
        return xmslWbsListRelationMapper.deleteXmslWbsListRelationByPks(xmslWbsListRelationPkList);
    }
}
