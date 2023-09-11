package com.hhwy.pm.xmsl.xmslEngineeringReport.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.github.pagehelper.PageHelper;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewWbsService;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.pm.xmsl.xmslEngineeringReport.domain.XmslEngineeringReport;
import com.hhwy.pm.xmsl.xmslEngineeringReport.mapper.XmslEngineeringReportMapper;
import com.hhwy.pm.xmsl.xmslEngineeringReport.service.IXmslEngineeringReportService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import io.lettuce.core.protocol.RedisProtocolException;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 工程量报表
 * @author wk
 * @date 2023-08-14 13:48:27
 * @remark
 */
@Service
public class XmslEngineeringReportServiceImpl implements IXmslEngineeringReportService {
    @Autowired
    private XmslEngineeringReportMapper xmslEngineeringReportMapper;
    @Autowired
    private IXmslDrawReviewWbsService drawReviewWbsService;
    @Autowired
    private IXmslDrawReviewListService drawReviewListService;
    @Autowired
    private IXmslDrawReviewService drawReviewService;
    @Autowired
    private RedisUtils redisUtils;


    @Override
    @Transactional
    public void sync() {
        XmslDrawReview drawReview = drawReviewService.getLast();
        if(drawReview == null || drawReview.getId()==null)
            return;
        xmslEngineeringReportMapper.deleteAll();
        //1、获取图纸复核WBS。以及清单
        List<XmslEngineeringReport> addList = new ArrayList<>();
        Map<Long,XmslEngineeringReport> addMap = new HashMap<>();
        Function<XmslEngineeringReport,Boolean> addReportFunc = (r)->{
            addList.add(r);
            addMap.put(r.getId(),r);
            //修改父级的haveChild
            if(r.getParentId() != null && addMap.get(r.getParentId()) != null){
                XmslEngineeringReport parent = addMap.get(r.getParentId());
                parent.setHaveChildren(1);
            }
            return true;
        };
        List<XmslDrawReviewWbs> wbsList = drawReviewWbsService.getFullEffectList();
        List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();
        Map<Long,XmslDrawReviewWbs> wbsMap = new HashMap<>(wbsList.size());
        for (int i = 0; i < wbsList.size(); i++) {
            XmslDrawReviewWbs temp = wbsList.get(i);
            XmslEngineeringReport report = instanceWbs(temp);
            wbsMap.put(temp.getId(),temp);
            addReportFunc.apply(report);
        }
        Map<String,XmslEngineeringReport> listReportMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            XmslDrawReviewList temp = list.get(i);
            XmslEngineeringReport report = null;
            if(listReportMap.containsKey(temp.getListCode())){
                report = listReportMap.get(temp.getListCode());
            }else{
                report = instanceList(temp);
                addReportFunc.apply(report);
                listReportMap.put(temp.getListCode(),report);
            }
            if(temp.getWbsId() == null)
                continue;
            //清单-WBS
            XmslDrawReviewWbs wbs = wbsMap.get(temp.getWbsId());
            XmslEngineeringReport listReport = instanceWbs(wbs);
            listReport.setId(wbs.getId());
            listReport.setParentId(report.getId());
            listReport.setReportType(2);
            addReportFunc.apply(listReport);
            //WBS-清单
            XmslEngineeringReport wbsReport = instanceList(temp);
            wbsReport.setId(temp.getId());
            wbsReport.setParentId(temp.getWbsId());
            wbsReport.setReportType(1);
            addReportFunc.apply(wbsReport);
        }
        //
        this.xmslEngineeringReportMapper.insertXmslEngineeringReportList(addList);
    }
    private XmslEngineeringReport instanceWbs(XmslDrawReviewWbs wbs){
        XmslEngineeringReport report = new XmslEngineeringReport();
        report.setParentId(ObjectUtils.nvlLong(wbs.getParentId(),-1L));
        report.setWbsId(Long.valueOf(wbs.getId()));
        report.setWbsCode(wbs.getCode());
        report.setWbsName(wbs.getName());
        report.setNodeType(wbs.getNodeType());
        report.setHaveChildren(wbs.getHaveChildren());
        report.setAncestors(wbs.getAncestors());
        report.setAncestorsName(wbs.getAncestorsName());
        report.setPartCode(wbs.getPartCode());
        report.setWbsUnit(wbs.getUnit());
        report.setLevel(wbs.getLevel());
        report.setDesignQuanlity(wbs.getDesignQuanlity()); 
//        new AddBaseInfoUtil<>().addBaseEntity(report);
        report.setId(wbs.getId());
        report.setReportType(1);
        return report;
    }
    private XmslEngineeringReport instanceList(XmslDrawReviewList list){
        XmslEngineeringReport report = new XmslEngineeringReport();
        report.setParentId(ObjectUtils.nvlLong(list.getPid()));
        report.setListCode(list.getListCode());
        report.setListName(list.getChineseName());
        report.setListId(list.getId());
        report.setUnit(list.getUnit());
        report.setUnitCode(list.getUnitCode());
        report.setCheckQuanlity(list.getCheckNum());
        report.setImageProgress(list.getImageProgress());
//        new AddBaseInfoUtil<>().addBaseEntity(report);
        report.setId(list.getId());
        report.setReportType(2);
        return report;
    }

    public XmslEngineeringReport getXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport) {
        return xmslEngineeringReportMapper.getXmslEngineeringReport(xmslEngineeringReport);
    }

    @Override
    public List<XmslEngineeringReport> getList(XmslEngineeringReport report) {
        return xmslEngineeringReportMapper.getXmslEngineeringReportList(report);
    }

    public List<XmslEngineeringReport> getXmslEngineeringReportList(XmslEngineeringReport report) {
        report.setParentId(ObjectUtils.nvlLong(report.getParentId(),-1L));
        boolean hasCondition = StringUtils.isNotBlank(report.getWbsCode()) || StringUtils.isNotBlank(report.getWbsName())
                || StringUtils.isNotBlank(report.getListCode()) || StringUtils.isNotBlank(report.getListName()) ;
//        if(hasCondition && ( (StringUtils.trim(report.getWbsCode())+StringUtils.trim(report.getWbsName())).length() < 3
//                            && (StringUtils.trim(report.getListCode())+StringUtils.trim(report.getListName())).length() < 3) )
//            throw new RuntimeException("搜索参数过小");
        if(!hasCondition){
            return xmslEngineeringReportMapper.getXmslEngineeringReportList(report);
        }
        //如果是懒加载,找出满足条件的id，扔redis
        String key = "engineeringReport::lazySearch_"+SecurityUtils.getTenantKey()+StringUtils.join(new String[]{
                report.getWbsCode(),report.getWbsName(),report.getListCode(),report.getListName()  
        }, ",");
        //获取ids
        Set<String> idSet = null;
        if(!redisUtils.hasKey(key) ){
            List<XmslEngineeringReport> list = xmslEngineeringReportMapper.getId(report);
            final Set<String> resuIdSet = new ConcurrentHashSet<>();
            list.parallelStream().forEach(r->{
                resuIdSet.add(r.getId()+"");
                if(StringUtils.isBlank(r.getAncestors()))
                    return;
                resuIdSet.addAll(Arrays.asList(Convert.toStrArray(r.getAncestors())));
            });
            if(resuIdSet.size() < 1)
                resuIdSet.add("-1");
            redisUtils.sAdd(key,resuIdSet.toArray(new String[]{}));
            redisUtils.expire(key,10, TimeUnit.MINUTES);
            idSet = resuIdSet;
        }else{
            idSet = redisUtils.sMembers(key);
        }
        XmslEngineeringReport query = new XmslEngineeringReport();
        query.setParentId(report.getParentId());
        query.setParams(ObjectUtils.toMap("ids", idSet));
        return xmslEngineeringReportMapper.getXmslEngineeringReportList(query);
    }

    @Transactional
    public int insertXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport) {
        xmslEngineeringReport.setId(IdWorker.createId());
        xmslEngineeringReport.setCreateUser(SecurityUtils.getUserName());
        xmslEngineeringReport.setCreateTime(DateUtils.getNowDate());
        return xmslEngineeringReportMapper.insertXmslEngineeringReport(xmslEngineeringReport);
    }

    @Transactional
    public int insertXmslEngineeringReportList(List<XmslEngineeringReport> xmslEngineeringReportList) {
        for (XmslEngineeringReport xmslEngineeringReport : xmslEngineeringReportList) {
            xmslEngineeringReport.setId(IdWorker.createId());
            xmslEngineeringReport.setCreateUser(SecurityUtils.getUserName());
            xmslEngineeringReport.setCreateTime(DateUtils.getNowDate());
        }
        return xmslEngineeringReportMapper.insertXmslEngineeringReportList(xmslEngineeringReportList);
    }

    @Transactional
    public int updateXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport) {
        xmslEngineeringReport.setUpdateUser(SecurityUtils.getUserName());
        xmslEngineeringReport.setUpdateTime(DateUtils.getNowDate());
        return xmslEngineeringReportMapper.updateXmslEngineeringReport(xmslEngineeringReport);
    }

    @Transactional
    public int updateXmslEngineeringReportList(List<XmslEngineeringReport> xmslEngineeringReportList) {
        for (XmslEngineeringReport xmslEngineeringReport : xmslEngineeringReportList) {
            xmslEngineeringReport.setUpdateUser(SecurityUtils.getUserName());
            xmslEngineeringReport.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslEngineeringReportMapper.updateXmslEngineeringReportList(xmslEngineeringReportList);
    }

    @Transactional
    public int deleteXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport) {
        xmslEngineeringReport.setUpdateUser(SecurityUtils.getUserName());
        xmslEngineeringReport.setUpdateTime(DateUtils.getNowDate());
        return xmslEngineeringReportMapper.deleteXmslEngineeringReport(xmslEngineeringReport);
    }

    @Transactional
    public int deleteXmslEngineeringReportByPks(List<Long> xmslEngineeringReportPkList) {
        return xmslEngineeringReportMapper.deleteXmslEngineeringReportByPks(xmslEngineeringReportPkList);
    }
}
