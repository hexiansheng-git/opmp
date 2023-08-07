package com.hhwy.pm.xmsl.drawReview.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;
import com.hhwy.pm.xmsl.drawReview.mapper.XmslDrawReviewWbsMapper;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewWbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-07 11:31:56
 * @remark
 */
@Service
public class XmslDrawReviewWbsServiceImpl implements IXmslDrawReviewWbsService {

    @Autowired
    private XmslDrawReviewWbsMapper xmslDrawReviewWbsMapper;


    public XmslDrawReviewWbs getXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs) {
        return xmslDrawReviewWbsMapper.getXmslDrawReviewWbs(xmslDrawReviewWbs);
    }

    public List<XmslDrawReviewWbs> getXmslDrawReviewWbsList(XmslDrawReviewWbs xmslDrawReviewWbs) {
        return xmslDrawReviewWbsMapper.getXmslDrawReviewWbsList(xmslDrawReviewWbs);
    }

    @Transactional
    public int insertXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs) {
        xmslDrawReviewWbs.setCreateUser(SecurityUtils.getUserName());
        xmslDrawReviewWbs.setCreateTime(DateUtils.getNowDate());
        return xmslDrawReviewWbsMapper.insertXmslDrawReviewWbs(xmslDrawReviewWbs);
    }

    @Transactional
    public int insertXmslDrawReviewWbsList(List<XmslDrawReviewWbs> xmslDrawReviewWbsList) {
        for (XmslDrawReviewWbs xmslDrawReviewWbs : xmslDrawReviewWbsList) {
            xmslDrawReviewWbs.setCreateUser(SecurityUtils.getUserName());
            xmslDrawReviewWbs.setCreateTime(DateUtils.getNowDate());
        }
        return xmslDrawReviewWbsMapper.insertXmslDrawReviewWbsList(xmslDrawReviewWbsList);
    }

    @Transactional
    public int updateXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs) {
        xmslDrawReviewWbs.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReviewWbs.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewWbsMapper.updateXmslDrawReviewWbs(xmslDrawReviewWbs);
    }

    @Transactional
    public int updateXmslDrawReviewWbsList(List<XmslDrawReviewWbs> xmslDrawReviewWbsList) {
        for (XmslDrawReviewWbs xmslDrawReviewWbs : xmslDrawReviewWbsList) {
            xmslDrawReviewWbs.setUpdateUser(SecurityUtils.getUserName());
            xmslDrawReviewWbs.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslDrawReviewWbsMapper.updateXmslDrawReviewWbsList(xmslDrawReviewWbsList);
    }

    @Transactional
    public int deleteXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs) {
        xmslDrawReviewWbs.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReviewWbs.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewWbsMapper.deleteXmslDrawReviewWbs(xmslDrawReviewWbs);
    }

    @Transactional
    public int deleteXmslDrawReviewWbsByPks(List<Long> xmslDrawReviewWbsPkList) {
        return xmslDrawReviewWbsMapper.deleteXmslDrawReviewWbsByPks(xmslDrawReviewWbsPkList);
    }
}
