package com.hhwy.pm.xmsl.drawReview.service.impl;

import cn.hutool.core.util.PageUtil;
import com.alibaba.cloud.nacos.discovery.NacosWatch;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.FtPmApplication;
import com.hhwy.pm.xmsl.drawReview.domain.*;
import com.hhwy.pm.xmsl.drawReview.mapper.XmslDrawReviewWbsMapper;
import com.hhwy.pm.xmsl.drawReview.service.*;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsListRelation;
import com.hhwy.utils.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.redisson.misc.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(XmslDrawReviewWbsServiceImpl.class);
    @Autowired
    private XmslDrawReviewWbsMapper xmslDrawReviewWbsMapper;
    @Autowired
    private IXmslDrawReviewService drawReviewService;
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
    public List<XmslDrawReviewWbs> getFullEffectList() {
        XmslDrawReview drawReview = drawReviewService.getLast();
        if(drawReview==null)
            return new ArrayList<>(2);
        List<XmslDrawReviewWbs> resuList = null;
        long beginMills = System.currentTimeMillis();
        long sum=0l;
        try{
            int version = drawReview.getVersion();
            int limitSize = 5000;
            //先查询第一页获取总条目数
            PageHelper.startPage(1,limitSize , true);
            List<XmslDrawReviewWbs> list = xmslDrawReviewWbsMapper.getFullList(version);
            sum = (new PageInfo<>(list)).getTotal();
            int pageNum = PageUtil.totalPage(sum, limitSize);
            resuList = new ArrayList<>((int)sum);
            resuList.addAll(list);
            for (int i = 2; i < pageNum; i++) {
                PageHelper.startPage(i,limitSize , false);
                List<XmslDrawReviewWbs> tempList = xmslDrawReviewWbsMapper.getFullList(version);
                resuList.addAll(tempList);
            }    
        }catch(Exception e){
            e.printStackTrace();
            logger.info("获取全量wbs失败,msg:{}",e.getMessage());
        }finally {
            logger.debug("获取全量wbs,共{}条,耗时:",sum,System.currentTimeMillis()-beginMills);
        }
        return resuList;
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
