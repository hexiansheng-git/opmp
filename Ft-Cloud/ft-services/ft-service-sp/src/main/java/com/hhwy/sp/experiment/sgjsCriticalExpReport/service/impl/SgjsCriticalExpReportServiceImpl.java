package com.hhwy.sp.experiment.sgjsCriticalExpReport.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.SgjsCriticalExpReport;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.vo.CriticalExpReportQueryVo;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.vo.CriticalExpReportVo;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.mapper.SgjsCriticalExpReportMapper;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.service.ISgjsCriticalExpReportService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    }
}
