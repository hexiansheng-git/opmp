package com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.KcsjPlanMonthlyReport;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.vo.PlanMonthlyReportQueryVo;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.mapper.KcsjPlanMonthlyReportMapper;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.service.IKcsjPlanMonthlyReportService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public List<KcsjPlanMonthlyReport> getKcsjPlanMonthlyReportList(PlanMonthlyReportQueryVo queryVo) {
        return kcsjPlanMonthlyReportMapper.getKcsjPlanMonthlyReportList(queryVo);
    }

    @Transactional
    public int insertKcsjPlanMonthlyReport(KcsjPlanMonthlyReport kcsjPlanMonthlyReport) {
        kcsjPlanMonthlyReport.setId(IdWorker.createId());
        kcsjPlanMonthlyReport.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        kcsjPlanMonthlyReport.setCreateUserName(SecurityUtils.getUserName());
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

    @Override
    @Transactional
    public void generateMonthlyReport() {
        Date nowDate = DateUtils.getNowDate();
        //删除当月月报
        this.deleteMonthlyReportByDate(nowDate);

        //插入当月月报
        KcsjPlanMonthlyReport report = new KcsjPlanMonthlyReport();
        report.setPeriod(nowDate);
        report.setId(IdWorker.createId());
        report.setCreateUserName("定时生成");
        report.setCreateTime(nowDate);
        kcsjPlanMonthlyReportMapper.insertKcsjPlanMonthlyReport(report);
    }

    public void deleteMonthlyReportByDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String yearMonth = sdf.format(date);
        kcsjPlanMonthlyReportMapper.deleteMonthlyReportByYearMonth(yearMonth);
    }

    @Override
    public void generateMonthlyReportByDate(Date date) {
        //删除当月月报
        this.deleteMonthlyReportByDate(date);

        //插入当月月报
        KcsjPlanMonthlyReport report = new KcsjPlanMonthlyReport();
        report.setPeriod(date);
        report.setId(IdWorker.createId());
        report.setCreateUserName("定时生成");
        report.setCreateTime(date);
        kcsjPlanMonthlyReportMapper.insertKcsjPlanMonthlyReport(report);
    }
}
