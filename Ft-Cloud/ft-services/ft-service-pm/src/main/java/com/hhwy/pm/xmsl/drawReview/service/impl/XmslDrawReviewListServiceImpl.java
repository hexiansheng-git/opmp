package com.hhwy.pm.xmsl.drawReview.service.impl;

import cn.hutool.core.util.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.mapper.XmslDrawReviewListMapper;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(XmslDrawReviewListServiceImpl.class);
    @Autowired
    private XmslDrawReviewListMapper xmslDrawReviewListMapper;
    @Autowired
    private IXmslDrawReviewService drawReviewService;


    public XmslDrawReviewList getXmslDrawReviewList(XmslDrawReviewList xmslDrawReviewList) {
        return xmslDrawReviewListMapper.getXmslDrawReviewList(xmslDrawReviewList);
    }

    public List<XmslDrawReviewList> getXmslDrawReviewListList(XmslDrawReviewList xmslDrawReviewList) {
        return xmslDrawReviewListMapper.getXmslDrawReviewListList(xmslDrawReviewList);
    }

    @Override
    public List<XmslDrawReviewList> getFullEffectList() {
        XmslDrawReview drawReview = drawReviewService.getLast();
        if(drawReview==null)
            return new ArrayList<>(2);
        List<XmslDrawReviewList> resuList = null;
        long beginMills = System.currentTimeMillis();
        long sum=0l;
        try{
            int version = drawReview.getVersion();
            int limitSize = 5000;
            //先查询第一页获取总条目数
            PageHelper.startPage(1,limitSize , true);
            List<XmslDrawReviewList> list = xmslDrawReviewListMapper.getFullList(version);
            sum = (new PageInfo<>(list)).getTotal();
            int pageNum = PageUtil.totalPage(sum, limitSize);
            resuList = new ArrayList<>((int)sum);
            resuList.addAll(list);
            for (int i = 2; i < pageNum; i++) {
                PageHelper.startPage(i,limitSize , false);
                List<XmslDrawReviewList> tempList = xmslDrawReviewListMapper.getFullList(version);
                resuList.addAll(tempList);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.info("获取全量图纸复核清单失败,msg:{}",e.getMessage());
        }finally {
            logger.debug("获取全量图纸复核清单,共{}条,耗时:",sum,System.currentTimeMillis()-beginMills);
        }
        return resuList;
    }

    /**
     * 获取全量图纸复核数据
     * @return
     */
    @Override
    public List<XmslDrawReviewList> getFullList() {
        List<XmslDrawReviewList> resuList = new ArrayList<>();

        XmslDrawReview drawReview = drawReviewService.getLast();
        if(drawReview != null){
            int version = drawReview.getVersion();
            resuList = xmslDrawReviewListMapper.getFullList(version);
        }
        return resuList;
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
        if(CollectionUtils.isEmpty(xmslDrawReviewListList))
            return 0;
        return xmslDrawReviewListMapper.updateXmslDrawReviewListList(xmslDrawReviewListList);
    }

    @Transactional
    public int updateParentId(List<XmslDrawReviewList> xmslDrawReviewListList) {
        if(CollectionUtils.isEmpty(xmslDrawReviewListList))
            return 0;
        return xmslDrawReviewListMapper.updateParentId(xmslDrawReviewListList);
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
