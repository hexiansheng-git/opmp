package com.hhwy.sp.experiment.sgjsCriticalExpReport.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.SgjsCriticalExpReport;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.vo.CriticalExpReportQueryVo;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.vo.CriticalExpReportVo;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.mapper.SgjsCriticalExpReportMapper;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.service.ISgjsCriticalExpReportService;
import com.hhwy.sp.sync.mq.service.ISysSyncInfoService4Sp;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2023-12-11 16:41:12
 * @remark
 */
@Service
public class SgjsCriticalExpReportServiceImpl implements ISgjsCriticalExpReportService {

    @Autowired
    private SgjsCriticalExpReportMapper sgjsCriticalExpReportMapper;

    @Autowired
    private ISysSyncInfoService4Sp sysSyncInfoService4Sp;

    public SgjsCriticalExpReport getSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport) {
        return sgjsCriticalExpReportMapper.getSgjsCriticalExpReport(sgjsCriticalExpReport);
    }

    public List<SgjsCriticalExpReport> getSgjsCriticalExpReportList(CriticalExpReportQueryVo queryVo) {
        return sgjsCriticalExpReportMapper.getSgjsCriticalExpReportList(queryVo);
    }

    @Transactional
    public int insertSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport) {
        sgjsCriticalExpReport.setId(IdWorker.createId());
        sgjsCriticalExpReport.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        sgjsCriticalExpReport.setCreateUserName(SecurityUtils.getUserName());
        sgjsCriticalExpReport.setCreateTime(DateUtils.getNowDate());
        return sgjsCriticalExpReportMapper.insertSgjsCriticalExpReport(sgjsCriticalExpReport);
    }

    @Transactional
    public int insertSgjsCriticalExpReportList(List<SgjsCriticalExpReport> sgjsCriticalExpReportList) {
        for (SgjsCriticalExpReport sgjsCriticalExpReport : sgjsCriticalExpReportList) {
            sgjsCriticalExpReport.setId(IdWorker.createId());
            sgjsCriticalExpReport.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            sgjsCriticalExpReport.setCreateUserName(SecurityUtils.getUserName());
            sgjsCriticalExpReport.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsCriticalExpReportMapper.insertSgjsCriticalExpReportList(sgjsCriticalExpReportList);
    }

    @Transactional
    public int updateSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport) {
        sgjsCriticalExpReport.setUpdateUser(SecurityUtils.getUserName());
        sgjsCriticalExpReport.setUpdateTime(DateUtils.getNowDate());
        return sgjsCriticalExpReportMapper.updateSgjsCriticalExpReport(sgjsCriticalExpReport);
    }

    @Transactional
    public int updateSgjsCriticalExpReportList(List<SgjsCriticalExpReport> sgjsCriticalExpReportList) {
        for (SgjsCriticalExpReport sgjsCriticalExpReport : sgjsCriticalExpReportList) {
            sgjsCriticalExpReport.setUpdateUser(SecurityUtils.getUserName());
            sgjsCriticalExpReport.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsCriticalExpReportMapper.updateSgjsCriticalExpReportList(sgjsCriticalExpReportList);
    }

    @Transactional
    public int deleteSgjsCriticalExpReport(SgjsCriticalExpReport sgjsCriticalExpReport) {
        sgjsCriticalExpReport.setUpdateUser(SecurityUtils.getUserName());
        sgjsCriticalExpReport.setUpdateTime(DateUtils.getNowDate());
        return sgjsCriticalExpReportMapper.deleteSgjsCriticalExpReport(sgjsCriticalExpReport);
    }

    @Transactional
    public int deleteSgjsCriticalExpReportByPks(List<Long> idList) {
        return sgjsCriticalExpReportMapper.deleteSgjsCriticalExpReportByPks(idList);
    }

    @Override
    public List<SgjsCriticalExpReport> getListByIds(List<Long> ids) {
        return sgjsCriticalExpReportMapper.getListByIds(ids);
    }

    @Override
    @Transactional
    public void save(CriticalExpReportVo criticalExpReportVo) {
        List<SgjsCriticalExpReport> reportList = criticalExpReportVo.getReportList();
        List<Long> delIdList = criticalExpReportVo.getDelIdList();
        Set<Long> delIdSet = new HashSet<>(delIdList);
        this.checkData(reportList,delIdSet);
        reportList = reportList.stream().filter(o -> !delIdSet.contains(o.getId())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(reportList) && CollectionUtils.isEmpty(delIdList)){
            return;
        }
        List<SgjsCriticalExpReport> addList = new ArrayList<>();
        List<SgjsCriticalExpReport> updateList = new ArrayList<>();

        for (SgjsCriticalExpReport report : reportList) {
            if("1".equals(report.getIsAdd())) {
                addList.add(report);
            } else {
                report.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                report.setUpdateTime(DateUtils.getNowDate());
                updateList.add(report);
            }
        }
        if(CollectionUtils.isNotEmpty(addList)) {
            this.insertSgjsCriticalExpReportList(addList);
        }
        if(CollectionUtils.isNotEmpty(updateList)) {
            sgjsCriticalExpReportMapper.updateSgjsCriticalExpReportList(updateList);
        }

        if(CollectionUtils.isNotEmpty(delIdList)){
            sgjsCriticalExpReportMapper.deleteSgjsCriticalExpReportByPks(delIdList);
        }
        //推送最新数据
        pushData();
    }

    public void checkData(List<SgjsCriticalExpReport> reportList,Set<Long> delIdSet){
        reportList = reportList.stream().filter(o -> StringUtils.isNotBlank(o.getExpReportCode())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(reportList)){
            return;
        }
        Set<String> codeSet1 = new HashSet<>();
        for (SgjsCriticalExpReport report : reportList) {
            String code = report.getExpReportCode();
            if(codeSet1.contains(code)){
                throw new RuntimeException(String.format("报告编码[%s]重复", report.getExpReportCode()));
            }else {
                codeSet1.add(code);
            }
        }

        List<SgjsCriticalExpReport> allList = sgjsCriticalExpReportMapper.getSgjsCriticalExpReportList(new CriticalExpReportQueryVo());
        allList = allList.stream().filter(o -> !delIdSet.contains(o.getId())).filter(o -> StringUtils.isNotBlank(o.getExpReportCode())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(allList)){
            return;
        }
        Map<Long, String> idCodeMap = allList.stream().collect(Collectors.toMap(SgjsCriticalExpReport::getId, SgjsCriticalExpReport::getExpReportCode));
        Set<String> codeSet2 = allList.stream().map(SgjsCriticalExpReport::getExpReportCode).collect(Collectors.toSet());

        for(SgjsCriticalExpReport report : reportList) {
            String isAdd = report.getIsAdd();
            if ("1".equals(isAdd)) {
                if (codeSet2.contains(report.getExpReportCode())) {
                    throw new RuntimeException(String.format("报告编码[%s]已存在", report.getExpReportCode()));
                }
            } else {
                String oldCode = idCodeMap.get(report.getId());
                if (codeSet2.contains(report.getExpReportCode()) && !oldCode.equals(report.getExpReportCode())) {
                    throw new RuntimeException(String.format("报告编码[%s]已存在", report.getExpReportCode()));
                }
            }
        }
    }


    //推送数据到总部版
    private void pushData() {
        CriticalExpReportVo vo = new CriticalExpReportVo();
        List<SgjsCriticalExpReport> list = sgjsCriticalExpReportMapper.getAll();
        vo.setReportList(list);
        if (list != null && !list.isEmpty()) {
            sysSyncInfoService4Sp.pushSgjsCriticalExpReport(vo);
        }
    }
}
