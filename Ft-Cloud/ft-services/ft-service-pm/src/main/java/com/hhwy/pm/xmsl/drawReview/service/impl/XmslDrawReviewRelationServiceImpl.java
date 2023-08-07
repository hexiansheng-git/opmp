package com.hhwy.pm.xmsl.drawReview.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewRelation;
import com.hhwy.pm.xmsl.drawReview.mapper.XmslDrawReviewRelationMapper;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-07 11:35:16
 * @remark
 */
@Service
public class XmslDrawReviewRelationServiceImpl implements IXmslDrawReviewRelationService {

    @Autowired
    private XmslDrawReviewRelationMapper xmslDrawReviewRelationMapper;


    public XmslDrawReviewRelation getXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation) {
        return xmslDrawReviewRelationMapper.getXmslDrawReviewRelation(xmslDrawReviewRelation);
    }

    public List<XmslDrawReviewRelation> getXmslDrawReviewRelationList(XmslDrawReviewRelation xmslDrawReviewRelation) {
        return xmslDrawReviewRelationMapper.getXmslDrawReviewRelationList(xmslDrawReviewRelation);
    }

    @Transactional
    public int insertXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation) {
        xmslDrawReviewRelation.setCreateUser(SecurityUtils.getUserName());
        xmslDrawReviewRelation.setCreateTime(DateUtils.getNowDate());
        return xmslDrawReviewRelationMapper.insertXmslDrawReviewRelation(xmslDrawReviewRelation);
    }

    @Transactional
    public int insertXmslDrawReviewRelationList(List<XmslDrawReviewRelation> xmslDrawReviewRelationList) {
        for (XmslDrawReviewRelation xmslDrawReviewRelation : xmslDrawReviewRelationList) {
            xmslDrawReviewRelation.setCreateUser(SecurityUtils.getUserName());
            xmslDrawReviewRelation.setCreateTime(DateUtils.getNowDate());
        }
        return xmslDrawReviewRelationMapper.insertXmslDrawReviewRelationList(xmslDrawReviewRelationList);
    }

    @Transactional
    public int updateXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation) {
        xmslDrawReviewRelation.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReviewRelation.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewRelationMapper.updateXmslDrawReviewRelation(xmslDrawReviewRelation);
    }

    @Transactional
    public int updateXmslDrawReviewRelationList(List<XmslDrawReviewRelation> xmslDrawReviewRelationList) {
        for (XmslDrawReviewRelation xmslDrawReviewRelation : xmslDrawReviewRelationList) {
            xmslDrawReviewRelation.setUpdateUser(SecurityUtils.getUserName());
            xmslDrawReviewRelation.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslDrawReviewRelationMapper.updateXmslDrawReviewRelationList(xmslDrawReviewRelationList);
    }

    @Transactional
    public int deleteXmslDrawReviewRelation(XmslDrawReviewRelation xmslDrawReviewRelation) {
        xmslDrawReviewRelation.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReviewRelation.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewRelationMapper.deleteXmslDrawReviewRelation(xmslDrawReviewRelation);
    }

    @Transactional
    public int deleteXmslDrawReviewRelationByPks(List<Long> xmslDrawReviewRelationPkList) {
        return xmslDrawReviewRelationMapper.deleteXmslDrawReviewRelationByPks(xmslDrawReviewRelationPkList);
    }
}
