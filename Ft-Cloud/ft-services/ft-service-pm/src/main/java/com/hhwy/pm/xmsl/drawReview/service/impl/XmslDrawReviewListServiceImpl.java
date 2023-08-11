package com.hhwy.pm.xmsl.drawReview.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.mapper.XmslDrawReviewListMapper;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wk
 * @date 2023-08-07 11:35:12
 * @remark
 */
@Service
public class XmslDrawReviewListServiceImpl implements IXmslDrawReviewListService {

    @Autowired
    private XmslDrawReviewListMapper xmslDrawReviewListMapper;


    public XmslDrawReviewList getXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList) {
        return xmslDrawReviewListMapper.getXmslDrawReviewList(xmslDrawReviewList);
    }

    public List<XmslDrawReviewList> getXmslDrawReviewListList(XmslDrawReviewList xmslDrawReviewList) {
        return xmslDrawReviewListMapper.getXmslDrawReviewListList(xmslDrawReviewList);
    }

    @Override
    public List<XmslDrawReviewList> getByIds(Collection collection) {
        if(CollectionUtils.isEmpty(collection))
            return new ArrayList<>(2);
        return xmslDrawReviewListMapper.getByIds(collection);
    }

    @Transactional
    public int insertXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList) {
        xmslDrawReviewList.setCreateUser(SecurityUtils.getUserName());
        xmslDrawReviewList.setCreateTime(DateUtils.getNowDate());
        return xmslDrawReviewListMapper.insertXmslDrawReviewList(xmslDrawReviewList);
    }

    @Transactional
    public int insertXmslDrawReviewListList(List<XmslDrawReviewList> xmslDrawReviewListList) {
        if(CollectionUtils.isEmpty(xmslDrawReviewListList))
            return 0;
        return xmslDrawReviewListMapper.insertXmslDrawReviewListList(xmslDrawReviewListList);
    }

    @Transactional
    public int updateXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList) {
        xmslDrawReviewList.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReviewList.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewListMapper.updateXmslDrawReviewList(xmslDrawReviewList);
    }

    @Transactional
    public int updateXmslDrawReviewListList(List<XmslDrawReviewList> xmslDrawReviewListList) {
        for (XmslDrawReviewList xmslDrawReviewList : xmslDrawReviewListList) {
            xmslDrawReviewList.setUpdateUser(SecurityUtils.getUserName());
            xmslDrawReviewList.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslDrawReviewListMapper.updateXmslDrawReviewListList(xmslDrawReviewListList);
    }

    @Transactional
    public int deleteXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList) {
        xmslDrawReviewList.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReviewList.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewListMapper.deleteXmslDrawReviewList(xmslDrawReviewList);
    }

    @Transactional
    public int deleteXmslDrawReviewListByPks(List<Long> xmslDrawReviewListPkList) {
        return xmslDrawReviewListMapper.deleteXmslDrawReviewListByPks(xmslDrawReviewListPkList);
    }
}
