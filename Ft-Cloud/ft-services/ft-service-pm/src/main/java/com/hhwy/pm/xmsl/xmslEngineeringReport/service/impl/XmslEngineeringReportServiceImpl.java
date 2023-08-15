package com.hhwy.pm.xmsl.xmslEngineeringReport.service.impl;

import com.github.pagehelper.PageHelper;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewListService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewWbsService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.pm.xmsl.xmslEngineeringReport.domain.XmslEngineeringReport;
import com.hhwy.pm.xmsl.xmslEngineeringReport.mapper.XmslEngineeringReportMapper;
import com.hhwy.pm.xmsl.xmslEngineeringReport.service.IXmslEngineeringReportService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.lettuce.core.protocol.RedisProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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


    @Override
    @Transactional
    public void sync() {
        XmslDrawReview drawReview = drawReviewService.getLast();
        if(drawReview == null || drawReview.getId()==null)
            return;
        xmslEngineeringReportMapper.deleteAll();
        //1、获取图纸复核WBS。以及清单
        List<XmslEngineeringReport> addList = new ArrayList<>();
        List<XmslDrawReviewWbs> wbsList = drawReviewWbsService.getFullEffectList();
        List<XmslDrawReviewList> list = drawReviewListService.getFullEffectList();
        Map<Long,XmslDrawReviewWbs> wbsMap = new HashMap<>(wbsList.size());
        for (int i = 0; i < wbsList.size(); i++) {
            XmslDrawReviewWbs temp = wbsList.get(i);
            XmslEngineeringReport report = instanceWbs(temp);
            wbsMap.put(temp.getId(),temp);
            addList.add(report);
        }
        Map<String,XmslEngineeringReport> listReportMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            XmslDrawReviewList temp = list.get(i);
            XmslEngineeringReport report = null;
            if(listReportMap.containsKey(temp.getListCode())){
                report = listReportMap.get(temp.getListCode());
            }else{
                report = instanceList(temp);
                addList.add(report);
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
            addList.add(listReport);
            //WBS-清单
            XmslEngineeringReport wbsReport = instanceList(temp);
            wbsReport.setId(temp.getId());
            wbsReport.setParentId(temp.getWbsId());
            wbsReport.setReportType(1);
            addList.add(wbsReport);
        }
        //
        this.xmslEngineeringReportMapper.insertXmslEngineeringReportList(addList);
    }
    private XmslEngineeringReport instanceWbs(XmslDrawReviewWbs wbs){
        XmslEngineeringReport report = new XmslEngineeringReport();
        report.setParentId(wbs.getParentId());
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
        new AddBaseInfoUtil<>().addBaseEntity(report);
        report.setId(wbs.getId());
        report.setReportType(1);
        return report;
    }
    private XmslEngineeringReport instanceList(XmslDrawReviewList list){
        XmslEngineeringReport report = new XmslEngineeringReport();
        report.setParentId(list.getPid());
        report.setListCode(list.getListCode());
        report.setListName(list.getChineseName());
        report.setListId(list.getId());
        report.setUnit(list.getUnit());
        report.setUnitCode(list.getUnitCode());
        report.setCheckQuanlity(list.getCheckNum());
        report.setImageProgress(list.getImageProgress());
        new AddBaseInfoUtil<>().addBaseEntity(report);
        report.setId(list.getId());
        report.setReportType(2);
        return report;
    }

    public XmslEngineeringReport getXmslEngineeringReport(XmslEngineeringReport xmslEngineeringReport) {
        return xmslEngineeringReportMapper.getXmslEngineeringReport(xmslEngineeringReport);
    }

    public List<XmslEngineeringReport> getXmslEngineeringReportList(XmslEngineeringReport xmslEngineeringReport) {
        return xmslEngineeringReportMapper.getXmslEngineeringReportList(xmslEngineeringReport);
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
