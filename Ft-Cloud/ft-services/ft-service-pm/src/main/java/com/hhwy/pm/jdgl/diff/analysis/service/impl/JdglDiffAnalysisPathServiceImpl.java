package com.hhwy.pm.jdgl.diff.analysis.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisPath;
import com.hhwy.pm.jdgl.diff.analysis.mapper.JdglDiffAnalysisPathMapper;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisPathService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author 陈锦豪
 * @date 2023-08-28 16:24:21
 * @remark
 */
@Service
public class JdglDiffAnalysisPathServiceImpl implements IJdglDiffAnalysisPathService {

    @Autowired
    private JdglDiffAnalysisPathMapper jdglDiffAnalysisPathMapper;

    @Autowired
    private IJdglDayScheduleWbsService iJdglDayScheduleWbsService;


    public JdglDiffAnalysisPath getJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath) {
        return jdglDiffAnalysisPathMapper.getJdglDiffAnalysisPath(jdglDiffAnalysisPath);
    }

    public List<JdglDiffAnalysisPath> getJdglDiffAnalysisPathList(JdglDiffAnalysisPath jdglDiffAnalysisPath) {
        List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList = jdglDiffAnalysisPathMapper.getJdglDiffAnalysisPathList(jdglDiffAnalysisPath);
        if(CollectionUtils.isEmpty(jdglDiffAnalysisPathList)) {
            return jdglDiffAnalysisPathList;
        }
        List<JdglDiffAnalysisPath> build = TreeUtil.build(jdglDiffAnalysisPathList, jdglDiffAnalysisPath.getPid());
        return build;
    }

    @Transactional
    public int insertJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath) {
        jdglDiffAnalysisPath.setId(IdWorker.createId());
        jdglDiffAnalysisPath.setCreateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisPath.setCreateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisPathMapper.insertJdglDiffAnalysisPath(jdglDiffAnalysisPath);
    }

    @Transactional
    public int insertJdglDiffAnalysisPathList(List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList) {
        for (JdglDiffAnalysisPath jdglDiffAnalysisPath : jdglDiffAnalysisPathList) {
            jdglDiffAnalysisPath.setId(IdWorker.createId());
            jdglDiffAnalysisPath.setCreateUser(SecurityUtils.getUserName());
            jdglDiffAnalysisPath.setCreateTime(DateUtils.getNowDate());
        }
        return jdglDiffAnalysisPathMapper.insertJdglDiffAnalysisPathList(jdglDiffAnalysisPathList);
    }

    @Transactional
    public int updateJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath) {
        jdglDiffAnalysisPath.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisPath.setUpdateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisPathMapper.updateJdglDiffAnalysisPath(jdglDiffAnalysisPath);
    }

    @Transactional
    public int updateJdglDiffAnalysisPathList(List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList) {
        for (JdglDiffAnalysisPath jdglDiffAnalysisPath : jdglDiffAnalysisPathList) {
            jdglDiffAnalysisPath.setUpdateUser(SecurityUtils.getUserName());
            jdglDiffAnalysisPath.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglDiffAnalysisPathMapper.updateJdglDiffAnalysisPathList(jdglDiffAnalysisPathList);
    }

    @Transactional
    public int deleteJdglDiffAnalysisPath(JdglDiffAnalysisPath jdglDiffAnalysisPath) {
        jdglDiffAnalysisPath.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisPath.setUpdateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisPathMapper.deleteJdglDiffAnalysisPath(jdglDiffAnalysisPath);
    }

    @Transactional
    public int deleteJdglDiffAnalysisPathByPks(List<Long> jdglDiffAnalysisPathPkList) {
        return jdglDiffAnalysisPathMapper.deleteJdglDiffAnalysisPathByPks(jdglDiffAnalysisPathPkList);
    }

    @Override
    public BigDecimal initKeyJdglDiffAnalysisPath(JdglDiffAnalysis jdglDiffAnalysis) {

        Date period = jdglDiffAnalysis.getPeriod();
        Date endDate = null;
        if(period != null) {
            Map<String, Date> dateRange4YearMonth = StatisticsUtils.getDateRange4YearMonth(period);
            endDate = dateRange4YearMonth.get("end");

        }

        // 获取总体计划非关键线路数据

        // 获取开累wbs填报
        List<JdglDayScheduleWbs4Value> totalWbsListByDateRange = iJdglDayScheduleWbsService.getTotalWbsListByDateRange(endDate);

        //

        return new BigDecimal(0);
    }

    @Override
    public BigDecimal initNotKeyJdglDiffAnalysisPath(JdglDiffAnalysis jdglDiffAnalysis) {

        Date period = jdglDiffAnalysis.getPeriod();
        Date endDate = null;
        if(period != null) {
            Map<String, Date> dateRange4YearMonth = StatisticsUtils.getDateRange4YearMonth(period);
            endDate = dateRange4YearMonth.get("end");

        }

        // 获取总体计划非关键线路数据

        // 获取开累wbs填报
        List<JdglDayScheduleWbs4Value> totalWbsListByDateRange = iJdglDayScheduleWbsService.getTotalWbsListByDateRange(endDate);

        //

        return new BigDecimal(0);
    }
}
