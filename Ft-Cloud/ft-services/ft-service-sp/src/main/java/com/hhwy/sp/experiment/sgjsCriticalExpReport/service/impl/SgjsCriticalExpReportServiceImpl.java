package com.hhwy.sp.experiment.sgjsCriticalExpReport.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.CriticalExpReportQueryVo;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.SgjsCriticalExpReport;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.mapper.SgjsCriticalExpReportMapper;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.service.ISgjsCriticalExpReportService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        sgjsCriticalExpReport.setCreateUser(SecurityUtils.getUserName());
        sgjsCriticalExpReport.setCreateTime(DateUtils.getNowDate());
        return sgjsCriticalExpReportMapper.insertSgjsCriticalExpReport(sgjsCriticalExpReport);
    }

    @Transactional
    public int insertSgjsCriticalExpReportList(List<SgjsCriticalExpReport> sgjsCriticalExpReportList) {
        for (SgjsCriticalExpReport sgjsCriticalExpReport : sgjsCriticalExpReportList) {
            sgjsCriticalExpReport.setId(IdWorker.createId());
            sgjsCriticalExpReport.setCreateUser(SecurityUtils.getUserName());
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
    public int deleteSgjsCriticalExpReportByPks(List<Long> sgjsCriticalExpReportPkList) {
        return sgjsCriticalExpReportMapper.deleteSgjsCriticalExpReportByPks(sgjsCriticalExpReportPkList);
    }

    @Override
    public List<SgjsCriticalExpReport> getListByIds(List<Long> ids) {
        return sgjsCriticalExpReportMapper.getListByIds(ids);
    }
}
