package com.hhwy.pm.jdgl.diff.analysis.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisSv;
import com.hhwy.pm.jdgl.diff.analysis.mapper.JdglDiffAnalysisSvMapper;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisSvService;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMakeDetail;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.statistics.domain.PlanStatisticsPeriodValueVO;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.qqch.sgch.sche.dto.QqchScheDTO;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheService;
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
    private IJdglDayScheduleService jdglDayScheduleService;

    @Autowired
    private IJdglDayScheduleWbsService iJdglDayScheduleWbsService;

    @Autowired
    private IJdglDiffAnalysisService jdglDiffAnalysisService;

    @Autowired
    private IJdglCorrectionMeasuresMakeService jdglCorrectionMeasuresMakeService;

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

    /**
     * 懒加载
     * @param jdglDiffAnalysisSvParam
     * @return
     */
    @Override
    public List<JdglDiffAnalysisSv> getJdglDiffAnalysisSvLazyList(JdglDiffAnalysisSv jdglDiffAnalysisSvParam) {

        List<JdglDiffAnalysisSv> returnList = new ArrayList<>();

        Long pid = jdglDiffAnalysisSvParam.getPid();

        jdglDiffAnalysisSvParam.setPid(null);

        List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList = jdglDiffAnalysisSvMapper.getJdglDiffAnalysisSvList(jdglDiffAnalysisSvParam);

        if(CollectionUtils.isEmpty(jdglDiffAnalysisSvList)) {
            return returnList;
        }

        if(pid == null) {
            returnList = jdglDiffAnalysisSvList.stream().filter(vo -> vo.getPid() == null).collect(Collectors.toList());
        } else {
            returnList = jdglDiffAnalysisSvList.stream().filter(vo -> pid.equals(vo.getPid())).collect(Collectors.toList());
        }

        if(CollectionUtils.isEmpty(returnList)) {
            return returnList;
        }

        Long diffAnalysisId = jdglDiffAnalysisSvParam.getDiffAnalysisId();
        List<JdglCorrectionMeasuresMakeDetail> makeDetailList = new ArrayList<>();
        if(diffAnalysisId != null){
            JdglDiffAnalysis jdglDiffAnalysisById = jdglDiffAnalysisService.getJdglDiffAnalysisById(diffAnalysisId);
            if(jdglDiffAnalysisById != null) {
                Date period = jdglDiffAnalysisById.getPeriod();
                JdglCorrectionMeasuresMake jdglCorrectionMeasuresMakeByDate = jdglCorrectionMeasuresMakeService.getJdglCorrectionMeasuresMakeByDate(period);
                if(jdglCorrectionMeasuresMakeByDate != null) {
                    makeDetailList = jdglCorrectionMeasuresMakeByDate.getDetailList();
                }
            }
        }

        for (JdglDiffAnalysisSv jdglDiffAnalysisSv : returnList) {
            Long id = jdglDiffAnalysisSv.getId();
            if(CollectionUtils.isNotEmpty(makeDetailList)) {
                String planItemCode = jdglDiffAnalysisSv.getPlanItemCode();
                JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail = makeDetailList.stream().filter(vo -> planItemCode.equals(vo.getWorkCode())).findFirst().orElse(null);
                if(jdglCorrectionMeasuresMakeDetail != null)
                    jdglDiffAnalysisSv.setCauseAnalysis(jdglCorrectionMeasuresMakeDetail.getDeviationCausesAnalysis());
            }
            List<JdglDiffAnalysisSv> collect = jdglDiffAnalysisSvList.stream().filter(vo -> id.equals(vo.getPid())).collect(Collectors.toList());
            jdglDiffAnalysisSv.setHaveChildren(CollectionUtils.isEmpty(collect) ? 0 : 1);
        }

        return returnList;
    }

    @Override
    public void deleteJdglDiffAnalysisSvByDiffAnalysisId(Long diffAnalysisId) {
        JdglDiffAnalysisSv jdglDiffAnalysisSv = new JdglDiffAnalysisSv();
        jdglDiffAnalysisSv.setDiffAnalysisId(diffAnalysisId);
        deleteJdglDiffAnalysisSv(jdglDiffAnalysisSv);
    }

    @Transactional
    public int insertJdglDiffAnalysisSv(JdglDiffAnalysisSv jdglDiffAnalysisSv) {
        jdglDiffAnalysisSv.setId(IdWorker.createId());
//        jdglDiffAnalysisSv.setCreateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisSv.setCreateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisSvMapper.insertJdglDiffAnalysisSv(jdglDiffAnalysisSv);
    }

    @Transactional
    public int insertJdglDiffAnalysisSvList(List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList) {
        for (JdglDiffAnalysisSv jdglDiffAnalysisSv : jdglDiffAnalysisSvList) {
//            jdglDiffAnalysisSv.setId(IdWorker.createId());
//            jdglDiffAnalysisSv.setCreateUser(SecurityUtils.getUserName());
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
//        jdglDiffAnalysisSv.setUpdateUser(SecurityUtils.getUserName());
//        jdglDiffAnalysisSv.setUpdateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisSvMapper.deleteJdglDiffAnalysisSv(jdglDiffAnalysisSv);
    }

    @Transactional
    public int deleteJdglDiffAnalysisSvByPks(List<Long> jdglDiffAnalysisSvPkList) {
        return jdglDiffAnalysisSvMapper.deleteJdglDiffAnalysisSvByPks(jdglDiffAnalysisSvPkList);
    }

    @Override
    public BigDecimal initJdglDiffAnalysisSv(JdglDiffAnalysis jdglDiffAnalysis) {

        List<JdglDiffAnalysisSv> insertList = new ArrayList<>();

        Date period = jdglDiffAnalysis.getPeriod();
        Long id = jdglDiffAnalysis.getId();

        BigDecimal thisTotalPlanAmt = new BigDecimal(0);
        BigDecimal thisTotalActAmt = new BigDecimal(0);
        String users = "";

        Calendar cl = Calendar.getInstance();

        cl.setTime(period);

        String year = cl.get(Calendar.YEAR) + "";
        String month = (cl.get(Calendar.MONTH)  + 1) > 10 ? "" + (cl.get(Calendar.MONTH)  + 1) : "0" + (cl.get(Calendar.MONTH)  + 1);

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

                jdglDiffAnalysisSv.setId(IdWorker.createId());
                jdglDiffAnalysisSv.setOldId(jdglMonthImagePlan.getId());
                jdglDiffAnalysisSv.setOldPid(jdglMonthImagePlan.getPid());
                jdglDiffAnalysisSv.setDiffAnalysisId(id);
                jdglDiffAnalysisSv.setPlanItemCode(jdglMonthImagePlan.getWorkCode());
                jdglDiffAnalysisSv.setPlanItemName(jdglMonthImagePlan.getWorkName());
                jdglDiffAnalysisSv.setPlanStartDate(jdglMonthImagePlan.getPlanStartDate());
                jdglDiffAnalysisSv.setPlanEndDate(jdglMonthImagePlan.getPlanEndDate());
                jdglDiffAnalysisSv.setIsCriticalPath(jdglMonthImagePlan.getIsCriticalPath());
                jdglDiffAnalysisSv.setUnit(jdglMonthImagePlan.getUnit());
                jdglDiffAnalysisSv.setDesignNum(jdglMonthImagePlan.getPlanCompQuantity());
                jdglDiffAnalysisSv.setSort(jdglMonthImagePlan.getSort());
//                jdglDiffAnalysisSv.setActStartDate(jdglMonthImagePlan.getCreateTime());
                jdglDiffAnalysisSv.setActEndDate(jdglMonthImagePlan.getPlanEndDate());

                // 处理作业责任人给总部推送预警用
                String responsePersonId = jdglMonthImagePlan.getResponsePersonId();
                jdglDiffAnalysisSv.setPtVar2(responsePersonId);
                if(StringUtils.isNotEmpty(responsePersonId)) {
                    String[] split = users.split(",");
                    String userId = "";
                    if(split.length > 0) {
                        userId = Arrays.stream(split).filter(str -> str.equals(responsePersonId)).findFirst().orElse(null);
                    }
                    if(StringUtils.isEmpty(userId)) {
                        users = "".equals(users) ? responsePersonId : "," + responsePersonId;
                    }
                }

                if(!CollectionUtils.isEmpty(wbsListByDateRange)) {
                    JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = wbsListByDateRange.stream().filter(vo -> StringUtils.isNotEmpty(vo.getWbsCode()) && vo.getWbsCode().equals(jdglMonthImagePlan.getWorkCode())).findFirst().orElse(null);
                    if(jdglDayScheduleWbs4Value != null) {
                        jdglDiffAnalysisSv.setActStartDate(jdglDayScheduleWbs4Value.getEditerDate());
                        BigDecimal planCompValue = StatisticsUtils.getDivideTenThousand(jdglMonthImagePlan.getPlanCompValue());
                        BigDecimal thisValue = StatisticsUtils.getDivideTenThousand(jdglDayScheduleWbs4Value.getThisValue());
                        if(thisValue != null && planCompValue!= null) {
                            jdglDiffAnalysisSv.setSvNum(thisValue.subtract(planCompValue));
                        }
                        BigDecimal thisQuantity = jdglDayScheduleWbs4Value.getThisQuantity();
                        BigDecimal planCompQuantity = jdglMonthImagePlan.getPlanCompQuantity();
                        if(thisQuantity != null &&  planCompQuantity != null){
                            jdglDiffAnalysisSv.setThisDeviationNum(thisQuantity.subtract(planCompQuantity));
                        }
                    }
                }
                BigDecimal planCompValue = StatisticsUtils.getDivideTenThousand(jdglMonthImagePlan.getPlanCompValue());
                if(planCompValue != null && !jdglMonthImagePlan.getWbsCode().equals(jdglMonthImagePlan.getWorkCode())) {
                    thisTotalPlanAmt = thisTotalPlanAmt.add(planCompValue);
                }
                insertList.add(jdglDiffAnalysisSv);
            }

            for (JdglDiffAnalysisSv jdglDiffAnalysisSv : insertList) {
                JdglDiffAnalysisSv jdglDiffAnalysisSv1 = insertList.stream().filter(vo -> vo.getOldId().equals(jdglDiffAnalysisSv.getOldPid())).findFirst().orElse(null);
                if(jdglDiffAnalysisSv1 != null) {
                    jdglDiffAnalysisSv.setPid(jdglDiffAnalysisSv1.getId());
                }
            }
        }

        if(!CollectionUtils.isEmpty(insertList)) {
            jdglDiffAnalysisSvMapper.insertJdglDiffAnalysisSvList(insertList);
        }

        if(CollectionUtils.isNotEmpty(wbsListByDateRange)) {
            for (JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value : wbsListByDateRange){
                BigDecimal thisValue = StatisticsUtils.getDivideTenThousand(jdglDayScheduleWbs4Value.getThisValue());
                if(thisValue != null) {
                    thisTotalActAmt = thisTotalActAmt.add(thisValue);
                }
            };
        }
        jdglDiffAnalysis.setTotalCompValue(thisTotalActAmt);
        jdglDiffAnalysis.setPtVar2(users);

        if(new BigDecimal(0).compareTo(thisTotalPlanAmt) == 0) {
            return new BigDecimal(0);
        }

        return thisTotalActAmt.subtract(thisTotalPlanAmt).divide(thisTotalPlanAmt, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
    }

    @Override
    public Map<String, Object> getPlanAndComp(JdglDiffAnalysisSv jdglDiffAnalysisSvParam) {
        Map<String, Object> returnMap = new HashMap<>();
        Long diffAnalysisId = jdglDiffAnalysisSvParam.getDiffAnalysisId();
        JdglDiffAnalysis jdglDiffAnalysis = new JdglDiffAnalysis();
        jdglDiffAnalysis.setId(diffAnalysisId);
        JdglDiffAnalysis jdglDiffAnalysis1 = jdglDiffAnalysisService.getJdglDiffAnalysis(jdglDiffAnalysis);
        if(jdglDiffAnalysis1 == null) {
            return returnMap;
        }

        Date period = jdglDiffAnalysis1.getPeriod();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar cl = Calendar.getInstance();
        cl.setTime(period);

        String year = cl.get(Calendar.YEAR) + "";
        String nextMonth = (cl.get(Calendar.MONTH) + 2) < 10 ? "0" + (cl.get(Calendar.MONTH) + 2) : (cl.get(Calendar.MONTH) + 2)  + "";
        Date startPeriod = cl.getTime();
        try {
            startPeriod = sdf.parse(year + "-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JdglMonthPlan jdglMonthPlan = new JdglMonthPlan();
        jdglMonthPlan.setYear(year);
        jdglMonthPlan.setTaskStatus("5");
        jdglMonthPlan.setIsUse("1");
        // 获取月计划产值
        List<JdglMonthPlan> jdglMonthPlanList = jdglMonthPlanService.getJdglMonthPlanList(jdglMonthPlan);

        List<PlanStatisticsPeriodValueVO> planStatisticsPeriodValueVOS = new ArrayList<>();
        Map<String, BigDecimal> monthScheduleByMonthRange = jdglDayScheduleService.getMonthScheduleByMonthRange(startPeriod, period);
        if(!CollectionUtils.isEmpty(jdglMonthPlanList)) {
            for (JdglMonthPlan jdglMonthPlan1 : jdglMonthPlanList) {
                if(year.equals(jdglMonthPlan1.getYear()) && nextMonth.compareTo(jdglMonthPlan1.getMonth()) > 0) {
                    PlanStatisticsPeriodValueVO planStatisticsPeriodValueVO = new PlanStatisticsPeriodValueVO();
                    planStatisticsPeriodValueVO.setPeriod(jdglMonthPlan1.getMonth());
                    planStatisticsPeriodValueVO.setPlanValue(jdglMonthPlan1.getThisPlanValueDl());
                    if(monthScheduleByMonthRange != null) {
                        planStatisticsPeriodValueVO.setCompValue(StatisticsUtils.getDivideTenThousand(monthScheduleByMonthRange.get(jdglMonthPlan1.getYear() + "-" + jdglMonthPlan1.getMonth())));
                    }
                    if(planStatisticsPeriodValueVO.getPlanValue() != null && planStatisticsPeriodValueVO.getCompValue() != null) {
                        planStatisticsPeriodValueVO.setDiffValue(planStatisticsPeriodValueVO.getCompValue().subtract(planStatisticsPeriodValueVO.getPlanValue()));
                    }
                    planStatisticsPeriodValueVOS.add(planStatisticsPeriodValueVO);
                }
            }
        }

        List<String> periods = new ArrayList<>();
        List<BigDecimal> planValues = new ArrayList<>();
        List<BigDecimal> compValues = new ArrayList<>();
        List<BigDecimal> diffValues = new ArrayList<>();

        if(!CollectionUtils.isEmpty(planStatisticsPeriodValueVOS)) {
            List<PlanStatisticsPeriodValueVO> collect = planStatisticsPeriodValueVOS.stream().sorted(Comparator.comparing(PlanStatisticsPeriodValueVO::getPeriod)).collect(Collectors.toList());
            collect.forEach(vo-> {
                periods.add(vo.getPeriod());
                planValues.add(vo.getPlanValue());
                compValues.add(vo.getCompValue());
                diffValues.add(vo.getDiffValue());
            });
        }
        returnMap.put("periods",periods);
        returnMap.put("planValues",planValues);
        returnMap.put("compValues",compValues);
        returnMap.put("diffValues",diffValues);

        return returnMap;
    }

}
