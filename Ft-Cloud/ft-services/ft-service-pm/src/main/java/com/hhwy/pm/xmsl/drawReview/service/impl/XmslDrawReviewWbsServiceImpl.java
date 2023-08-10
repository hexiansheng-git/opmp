package com.hhwy.pm.xmsl.drawReview.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.drawReview.domain.*;
import com.hhwy.pm.xmsl.drawReview.mapper.XmslDrawReviewWbsMapper;
import com.hhwy.pm.xmsl.drawReview.service.*;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsListRelation;
import com.hhwy.utils.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wk
 * @date 2023-08-07 11:31:56
 * @remark
 */
@Service
public class XmslDrawReviewWbsServiceImpl implements IXmslDrawReviewWbsService {

    @Autowired
    private XmslDrawReviewWbsMapper xmslDrawReviewWbsMapper;
    @Autowired
    private IXmslDrawReviewRelationService relationService;
    @Autowired
    private IXmslDrawReviewListService listService;
    @Autowired
    private IXmslDrawReviewMaterialService materialService;
    @Autowired
    private IXmslDrawReviewSourceMaterialService sourceMaterialService;


    public XmslDrawReviewWbs getXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs) {
        return xmslDrawReviewWbsMapper.getXmslDrawReviewWbs(xmslDrawReviewWbs);
    }

    public List<XmslDrawReviewWbs> getXmslDrawReviewWbsList(XmslDrawReviewWbs xmslDrawReviewWbs) {
        return xmslDrawReviewWbsMapper.getXmslDrawReviewWbsList(xmslDrawReviewWbs);
    }

    @Override
    public List<XmslDrawReviewWbs> getByIds(Set<Long> idSet) {
        if(CollectionUtils.isEmpty(idSet))
            return new ArrayList<>(2);
        return xmslDrawReviewWbsMapper.getByIds(idSet);
    }

    @Transactional
    public int insertXmslDrawReviewWbs(XmslDrawReviewWbs xmslDrawReviewWbs) {
        xmslDrawReviewWbs.setCreateUser(SecurityUtils.getUserName());
        xmslDrawReviewWbs.setCreateTime(DateUtils.getNowDate());
        return xmslDrawReviewWbsMapper.insertXmslDrawReviewWbs(xmslDrawReviewWbs);
    }

    @Transactional
    public int insertXmslDrawReviewWbsList(List<XmslDrawReviewWbs> xmslDrawReviewWbsList) {
        if(CollectionUtils.isEmpty(xmslDrawReviewWbsList))
            return 0;
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
