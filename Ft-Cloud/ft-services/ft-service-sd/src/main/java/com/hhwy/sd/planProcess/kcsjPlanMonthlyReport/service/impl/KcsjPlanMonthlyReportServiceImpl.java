package com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.KcsjPlanMonthlyReport;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.mapper.KcsjPlanMonthlyReportMapper;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.service.IKcsjPlanMonthlyReportService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-12-18 11:21:39
 * @remark
 */
@Service
public class KcsjPlanMonthlyReportServiceImpl implements IKcsjPlanMonthlyReportService {

    @Autowired
    private KcsjPlanMonthlyReportMapper kcsjPlanMonthlyReportMapper;


    public KcsjPlanMonthlyReport getKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport) {
        return kcsjPlanMonthlyReportMapper.getKcsjPlanMonthlyReport(kcsjPlanMonthlyReport);
    }

    public List<KcsjPlanMonthlyReport> getKcsjPlanMonthlyReportList(KcsjPlanMonthlyReport kcsjPlanMonthlyReport) {
        return kcsjPlanMonthlyReportMapper.getKcsjPlanMonthlyReportList(kcsjPlanMonthlyReport);
    }

    @Transactional
    public int insertKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport) {
        kcsjPlanMonthlyReport.setId(IdWorker.createId());
        kcsjPlanMonthlyReport.setCreateUser(SecurityUtils.getUserName());
        kcsjPlanMonthlyReport.setCreateTime(DateUtils.getNowDate());
        return kcsjPlanMonthlyReportMapper.insertKcsjPlanMonthlyReport(kcsjPlanMonthlyReport);
    }

    @Transactional
    public int insertKcsjPlanMonthlyReportList(List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportList) {
        for (KcsjPlanMonthlyReport kcsjPlanMonthlyReport : kcsjPlanMonthlyReportList) {
            kcsjPlanMonthlyReport.setId(IdWorker.createId());
            kcsjPlanMonthlyReport.setCreateUser(SecurityUtils.getUserName());
            kcsjPlanMonthlyReport.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjPlanMonthlyReportMapper.insertKcsjPlanMonthlyReportList(kcsjPlanMonthlyReportList);
    }

    @Transactional
    public int updateKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport) {
        kcsjPlanMonthlyReport.setUpdateUser(SecurityUtils.getUserName());
        kcsjPlanMonthlyReport.setUpdateTime(DateUtils.getNowDate());
        return kcsjPlanMonthlyReportMapper.updateKcsjPlanMonthlyReport(kcsjPlanMonthlyReport);
    }

    @Transactional
    public int updateKcsjPlanMonthlyReportList(List<KcsjPlanMonthlyReport> kcsjPlanMonthlyReportList) {
        for (KcsjPlanMonthlyReport kcsjPlanMonthlyReport : kcsjPlanMonthlyReportList) {
            kcsjPlanMonthlyReport.setUpdateUser(SecurityUtils.getUserName());
            kcsjPlanMonthlyReport.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjPlanMonthlyReportMapper.updateKcsjPlanMonthlyReportList(kcsjPlanMonthlyReportList);
    }

    @Transactional
    public int deleteKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport) {
        kcsjPlanMonthlyReport.setUpdateUser(SecurityUtils.getUserName());
        kcsjPlanMonthlyReport.setUpdateTime(DateUtils.getNowDate());
        return kcsjPlanMonthlyReportMapper.deleteKcsjPlanMonthlyReport(kcsjPlanMonthlyReport);
    }

    @Transactional
    public int deleteKcsjPlanMonthlyReportByPks(List<Long> kcsjPlanMonthlyReportPkList) {
        return kcsjPlanMonthlyReportMapper.deleteKcsjPlanMonthlyReportByPks(kcsjPlanMonthlyReportPkList);
    }
}
