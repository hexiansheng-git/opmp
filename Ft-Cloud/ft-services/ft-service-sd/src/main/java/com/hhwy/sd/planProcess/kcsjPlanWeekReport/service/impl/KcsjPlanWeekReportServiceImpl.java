package com.hhwy.sd.planProcess.kcsjPlanWeekReport.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.organManage.util.StatisticsUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sd.planProcess.kcsjPlanWeekReport.mapper.KcsjPlanWeekReportMapper;
import com.hhwy.sd.planProcess.kcsjPlanWeekReport.service.IKcsjPlanWeekReportService;
import com.hhwy.sd.planProcess.kcsjPlanWeekReport.domain.KcsjPlanWeekReport;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2023-12-18 11:12:15
 * @remark
 */
@Service
public class KcsjPlanWeekReportServiceImpl implements IKcsjPlanWeekReportService {

    @Autowired
    private KcsjPlanWeekReportMapper kcsjPlanWeekReportMapper;


    public KcsjPlanWeekReport getKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport) {
        return kcsjPlanWeekReportMapper.getKcsjPlanWeekReport(kcsjPlanWeekReport);
    }

    public List<KcsjPlanWeekReport> getKcsjPlanWeekReportList(KcsjPlanWeekReport kcsjPlanWeekReport) {
        return kcsjPlanWeekReportMapper.getKcsjPlanWeekReportList(kcsjPlanWeekReport);
    }

    @Transactional
    public int insertKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport) {
        kcsjPlanWeekReport.setId(IdWorker.createId());
        kcsjPlanWeekReport.setCreateUser(SecurityUtils.getUserName());
        kcsjPlanWeekReport.setCreateTime(DateUtils.getNowDate());
        return kcsjPlanWeekReportMapper.insertKcsjPlanWeekReport(kcsjPlanWeekReport);
    }

    @Transactional
    public int insertKcsjPlanWeekReportList(List<KcsjPlanWeekReport> kcsjPlanWeekReportList) {
        for (KcsjPlanWeekReport kcsjPlanWeekReport : kcsjPlanWeekReportList) {
            kcsjPlanWeekReport.setId(IdWorker.createId());
            kcsjPlanWeekReport.setCreateUser(SecurityUtils.getUserName());
            kcsjPlanWeekReport.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjPlanWeekReportMapper.insertKcsjPlanWeekReportList(kcsjPlanWeekReportList);
    }

    @Transactional
    public int updateKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport) {
        kcsjPlanWeekReport.setUpdateUser(SecurityUtils.getUserName());
        kcsjPlanWeekReport.setUpdateTime(DateUtils.getNowDate());
        return kcsjPlanWeekReportMapper.updateKcsjPlanWeekReport(kcsjPlanWeekReport);
    }

    @Transactional
    public int updateKcsjPlanWeekReportList(List<KcsjPlanWeekReport> kcsjPlanWeekReportList) {
        if(CollectionUtils.isEmpty(kcsjPlanWeekReportList)) {
            return 0;
        }
        for (KcsjPlanWeekReport kcsjPlanWeekReport : kcsjPlanWeekReportList) {
            kcsjPlanWeekReport.setUpdateUser(SecurityUtils.getUserName());
            kcsjPlanWeekReport.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjPlanWeekReportMapper.updateKcsjPlanWeekReportList(kcsjPlanWeekReportList);
    }

    @Transactional
    public int deleteKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport) {
        return kcsjPlanWeekReportMapper.deleteKcsjPlanWeekReport(kcsjPlanWeekReport);
    }

    @Transactional
    public int deleteKcsjPlanWeekReportByPks(List<Long> kcsjPlanWeekReportPkList) {
        return kcsjPlanWeekReportMapper.deleteKcsjPlanWeekReportByPks(kcsjPlanWeekReportPkList);
    }

    @Override
    public int produceData() {
        return produceDataByPeriod(DateUtils.getNowDate());
    }

    @Override
    public int produceDataByPeriod(Date period) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(period);
        int year = cl.get(Calendar.YEAR);
        int week = cl.get(Calendar.WEEK_OF_YEAR);
        Map<String, Date> dateRange4Week = StatisticsUtils.getDateRange4Week(year + "", week + "");
        KcsjPlanWeekReport kcsjPlanWeekReport = new KcsjPlanWeekReport();
        kcsjPlanWeekReport.setWeekPeriod(year + "年第" + (week + 1) + "周");
        kcsjPlanWeekReport.setStartDate(dateRange4Week.get("start"));
        kcsjPlanWeekReport.setEndDate(dateRange4Week.get("end"));
        return insertKcsjPlanWeekReport(kcsjPlanWeekReport);
    }
}
