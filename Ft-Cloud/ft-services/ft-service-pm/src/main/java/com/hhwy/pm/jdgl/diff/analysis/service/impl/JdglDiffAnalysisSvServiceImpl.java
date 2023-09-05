package com.hhwy.pm.jdgl.diff.analysis.service.impl;

import java.util.*;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisSv;
import com.hhwy.pm.jdgl.diff.analysis.mapper.JdglDiffAnalysisSvMapper;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisSvService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author 陈锦豪
 * @date 2023-08-28 16:24:27
 * @remark
 */
@Service
public class JdglDiffAnalysisSvServiceImpl implements IJdglDiffAnalysisSvService {

    @Autowired
    private JdglDiffAnalysisSvMapper jdglDiffAnalysisSvMapper;
    
    @Autowired
    private IJdglMonthPlanService jdglMonthPlanService;
    
    @Autowired
    private IJdglMonthImagePlanService jdglMonthImagePlanService;

    @Autowired
    private IJdglDayScheduleWbsService iJdglDayScheduleWbsService;

    public JdglDiffAnalysisSv getJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv) {
        return jdglDiffAnalysisSvMapper.getJdglDiffAnalysisSv(jdglDiffAnalysisSv);
    }

    public List<JdglDiffAnalysisSv> getJdglDiffAnalysisSvList(JdglDiffAnalysisSv jdglDiffAnalysisSv) {
        List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList = jdglDiffAnalysisSvMapper.getJdglDiffAnalysisSvList(jdglDiffAnalysisSv);
        if(CollectionUtils.isEmpty(jdglDiffAnalysisSvList)) {
            return jdglDiffAnalysisSvList;
        }
        List<JdglDiffAnalysisSv> build = TreeUtil.build(jdglDiffAnalysisSvList, jdglDiffAnalysisSv.getPid());
        return build;
    }

    @Transactional
    public int insertJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv) {
        jdglDiffAnalysisSv.setId(IdWorker.createId());
        jdglDiffAnalysisSv.setCreateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisSv.setCreateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisSvMapper.insertJdglDiffAnalysisSv(jdglDiffAnalysisSv);
    }

    @Transactional
    public int insertJdglDiffAnalysisSvList(List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList) {
        for (JdglDiffAnalysisSv jdglDiffAnalysisSv : jdglDiffAnalysisSvList) {
            jdglDiffAnalysisSv.setId(IdWorker.createId());
            jdglDiffAnalysisSv.setCreateUser(SecurityUtils.getUserName());
            jdglDiffAnalysisSv.setCreateTime(DateUtils.getNowDate());
        }
        return jdglDiffAnalysisSvMapper.insertJdglDiffAnalysisSvList(jdglDiffAnalysisSvList);
    }

    @Transactional
    public int updateJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv) {
        jdglDiffAnalysisSv.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisSv.setUpdateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisSvMapper.updateJdglDiffAnalysisSv(jdglDiffAnalysisSv);
    }

    @Transactional
    public int updateJdglDiffAnalysisSvList(List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList) {
        for (JdglDiffAnalysisSv jdglDiffAnalysisSv : jdglDiffAnalysisSvList) {
            jdglDiffAnalysisSv.setUpdateUser(SecurityUtils.getUserName());
            jdglDiffAnalysisSv.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglDiffAnalysisSvMapper.updateJdglDiffAnalysisSvList(jdglDiffAnalysisSvList);
    }

    @Transactional
    public int deleteJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv) {
        jdglDiffAnalysisSv.setUpdateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisSv.setUpdateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisSvMapper.deleteJdglDiffAnalysisSv(jdglDiffAnalysisSv);
    }

    @Transactional
    public int deleteJdglDiffAnalysisSvByPks(List<Long> jdglDiffAnalysisSvPkList) {
        return jdglDiffAnalysisSvMapper.deleteJdglDiffAnalysisSvByPks(jdglDiffAnalysisSvPkList);
    }

    @Override
    public int initJdglDiffAnalysisSv(JdglDiffAnalysis jdglDiffAnalysis) {

        List<JdglDiffAnalysisSv> insertList = new ArrayList<>();

        Date period = jdglDiffAnalysis.getPeriod();
        Long id = jdglDiffAnalysis.getId();

        Calendar cl = Calendar.getInstance();

        cl.setTime(period);

        String year = cl.get(Calendar.YEAR) + "";
        String month = (cl.get(Calendar.MONTH + 1))+"";

        // 月计划数据
        JdglMonthPlan usingMonthPlanByYearAndMonth = jdglMonthPlanService.getUsingMonthPlanByYearAndMonth(year, month);

        Map<String, Date> map = StatisticsUtils.getDateRange4YearMonth(year, month);
        Date startDate = map.get("start");
        Date endDate = map.get("end");

        // 获取月产值数据
        List<JdglDayScheduleWbs4Value> wbsListByDateRange = iJdglDayScheduleWbsService.getWbsListByDateRange(startDate, endDate);

        if(usingMonthPlanByYearAndMonth != null) {
            List<JdglMonthImagePlan> jdglMonthImagePlanListByPlanId = jdglMonthImagePlanService.getJdglMonthImagePlanListByPlanId(usingMonthPlanByYearAndMonth.getId());
            List<JdglMonthImagePlan> jdglMonthImagePlans = TreeUtil.treeToList(jdglMonthImagePlanListByPlanId);

            for (JdglMonthImagePlan jdglMonthImagePlan : jdglMonthImagePlans) {
                JdglDiffAnalysisSv jdglDiffAnalysisSv = new JdglDiffAnalysisSv();

                jdglDiffAnalysisSv.setId(jdglMonthImagePlan.getId());
                jdglDiffAnalysisSv.setPid(jdglMonthImagePlan.getPid());
                jdglDiffAnalysisSv.setDiffAnalysisId(id);
                jdglDiffAnalysisSv.setPlanItemCode(jdglMonthImagePlan.getWorkCode());
                jdglDiffAnalysisSv.setPlanItemName(jdglMonthImagePlan.getWorkName());
                jdglDiffAnalysisSv.setPlanStartDate(jdglMonthImagePlan.getPlanStartDate());
                jdglDiffAnalysisSv.setPlanEndDate(jdglMonthImagePlan.getPlanEndDate());
                jdglDiffAnalysisSv.setIsCriticalPath(jdglMonthImagePlan.getIsCriticalPath());
                jdglDiffAnalysisSv.setUnit(jdglMonthImagePlan.getUnit());
                jdglDiffAnalysisSv.setDesignNum(jdglMonthImagePlan.getDesignQuantity());
                jdglDiffAnalysisSv.setActStartDate(jdglMonthImagePlan.getCreateTime());
                jdglDiffAnalysisSv.setActEndDate(jdglMonthImagePlan.getPlanEndDate());

                if(!CollectionUtils.isEmpty(wbsListByDateRange)) {
                    JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = wbsListByDateRange.stream().filter(vo -> StringUtils.isNotEmpty(vo.getWbsCode()) && vo.getWbsCode().equals(jdglMonthImagePlan.getWbsCode())).findFirst().orElse(null);
                    if(jdglDayScheduleWbs4Value != null) {
                        if(jdglDayScheduleWbs4Value.getThisValue() != null && jdglMonthImagePlan.getPlanCompValue() != null) {
                            jdglDiffAnalysisSv.setThisDeviationNum(jdglDayScheduleWbs4Value.getThisValue().subtract(jdglMonthImagePlan.getPlanCompValue()));

                        }
                        if(jdglDayScheduleWbs4Value.getThisQuantity() != null &&  jdglMonthImagePlan.getPlanCompQuantity() != null){
                            jdglDiffAnalysisSv.setSvNum(jdglDayScheduleWbs4Value.getThisQuantity().subtract(jdglMonthImagePlan.getPlanCompQuantity()));
                        }
                    }
                }
            }
        }



        return 0;
    }
}
