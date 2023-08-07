package com.hhwy.pm.xmsl.drawReview.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.xmsl.drawReview.mapper.XmslDrawReviewMapper;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.utils.idworker.IdWorker;

/**
 * 图纸复核service
 * @author wk
 * @date 2023-08-07 11:23:32
 * @remark 
 */
@Service
public class XmslDrawReviewServiceImpl implements IXmslDrawReviewService{
    @Autowired
    private XmslDrawReviewMapper xmslDrawReviewMapper;

                                                                                                                                                                                                        
    public XmslDrawReview getXmslDrawReview(XmslDrawReview xmslDrawReview) {
        return xmslDrawReviewMapper.getXmslDrawReview(xmslDrawReview);
    }

    public List<XmslDrawReview> getXmslDrawReviewList(XmslDrawReview xmslDrawReview) {
        return xmslDrawReviewMapper.getXmslDrawReviewList(xmslDrawReview);
    }

    @Override
    public XmslDrawReview getById(Long id) {
        XmslDrawReview query = new XmslDrawReview();
        query.setId(id);
        return xmslDrawReviewMapper.getXmslDrawReview(query);
    }

    @Override
    public XmslDrawReview getLast() {
        return this.xmslDrawReviewMapper.getLast();
    }

    @Override
    public Integer hasChange() {
//        this.xmslDrawReviewMapper.getXmslDrawReviewCount();
        return null;
    }

    @Transactional
    public int insertXmslDrawReview(XmslDrawReview xmslDrawReview) {
        xmslDrawReview.setId(IdWorker.createId());
        xmslDrawReview.setCreateUser(SecurityUtils.getUserName());
        xmslDrawReview.setCreateTime(DateUtils.getNowDate());
        return xmslDrawReviewMapper.insertXmslDrawReview(xmslDrawReview);
    }

    @Transactional
    public int insertXmslDrawReviewList(List<XmslDrawReview> xmslDrawReviewList) {
        for (XmslDrawReview xmslDrawReview : xmslDrawReviewList) {
            xmslDrawReview.setId(IdWorker.createId());
            xmslDrawReview.setCreateUser(SecurityUtils.getUserName());
            xmslDrawReview.setCreateTime(DateUtils.getNowDate());
        }
        return xmslDrawReviewMapper.insertXmslDrawReviewList(xmslDrawReviewList);
    }

    @Transactional
    public int updateXmslDrawReview(XmslDrawReview xmslDrawReview) {
        xmslDrawReview.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReview.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewMapper.updateXmslDrawReview(xmslDrawReview);
    }

    
    @Transactional
    public int deleteXmslDrawReview(XmslDrawReview xmslDrawReview) {
        xmslDrawReview.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReview.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewMapper.deleteXmslDrawReview(xmslDrawReview);
    }
}
