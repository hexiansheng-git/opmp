package com.hhwy.pm.jdgl.statistics.service.impl;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.IJdglQuarterImagePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.service.IJdglQuarterPlanService;
import com.hhwy.pm.jdgl.statistics.domain.PlanStatisticsQueryVO;
import com.hhwy.pm.jdgl.statistics.domain.PlanStatisticsWbsValueVO;
import com.hhwy.pm.jdgl.statistics.service.IPlanStatisticsService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.domain.JdglWeekImagePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service.IJdglWeekImagePlanService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.service.IJdglWeekPlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.IJdglYearImagePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.service.IJdglYearPlanService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Service
public class PlanStatisticsServiceImpl implements IPlanStatisticsService {

    @Autowired
    private IJdglDayScheduleService iJdglDayScheduleService;

    @Autowired
    private IJdglYearPlanService jdglYearPlanService;

    @Autowired
    private IJdglQuarterPlanService jdglQuarterPlanService;

    @Autowired
    private IJdglMonthPlanService jdglMonthPlanService;

    @Autowired
    private IJdglWeekPlanService jdglWeekPlanService;

    @Autowired
    private IJdglMainPlanService jdglMainPlanService;

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

    @Autowired
    private IJdglDayScheduleWbsService jdglDayScheduleWbsService;

    @Autowired
    private IJdglYearImagePlanService jdglYearImagePlanService;
    @Autowired
    private IJdglQuarterImagePlanService jdglQuarterImagePlanService;
    @Autowired
    private IJdglMonthImagePlanService jdglMonthImagePlanService;
    @Autowired
    private IJdglWeekImagePlanService jdglWeekImagePlanService;

    /**
     * 获取产值完成情况
     * @Param iPlanStatisticsQueryVO
     * @return
     */
    @Override
    public Map<String, Map<String, BigDecimal>> getValueCompData(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {

        // 自动化补充参数
        StatisticsUtils.initDateParams(iPlanStatisticsQueryVO);

        String queryDateType = iPlanStatisticsQueryVO.getQueryDateType();
        String year = iPlanStatisticsQueryVO.getYear();
        String quarter = iPlanStatisticsQueryVO.getQuarter();
        String month = iPlanStatisticsQueryVO.getMonth();
        String week = iPlanStatisticsQueryVO.getWeek();
        Date startDate = iPlanStatisticsQueryVO.getStartDate();
        Date endDate = iPlanStatisticsQueryVO.getEndDate();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = sdf.format(startDate);
        String endDateStr = sdf.format(endDate);

        Map<String, Map<String, BigDecimal>> return2Map = new HashMap<>();

        // 根据结束日期获取日填报数据
        List<JdglDaySchedule> listByDateRange = iJdglDayScheduleService.getListByDateRange(null, endDate);

        BigDecimal totalActAmt = new BigDecimal(0);
        BigDecimal yearActAmt = new BigDecimal(0);
        BigDecimal quarterActAmt = new BigDecimal(0);
        BigDecimal monthActAmt = new BigDecimal(0);
        BigDecimal weekActAmt = new BigDecimal(0);
        if(!CollectionUtils.isEmpty(listByDateRange)) {
            for (JdglDaySchedule jdglDaySchedule : listByDateRange) {
                BigDecimal dayValueDl = jdglDaySchedule.getDayValueDl();
                Date date = jdglDaySchedule.getDate();
                Calendar cl = Calendar.getInstance();
                cl.setTime(date);
                if(endDate.before(date)) {
                    continue;
                }
                totalActAmt.add(dayValueDl);
                if(!year.equals(cl.get(Calendar.YEAR))) {
                    continue;
                }
                yearActAmt.add(dayValueDl);
                int dateMonth = cl.get(Calendar.MONTH) + 1;

                if(Integer.parseInt(quarter)*3 > dateMonth || Integer.parseInt(quarter)*3-2 < dateMonth) {
                    continue;
                }
                quarterActAmt.add(dayValueDl);

                if(!month.equals(dateMonth+"")){
                    continue;
                }
                monthActAmt.add(dayValueDl);

                if(startDate.before(date) && endDate.after(date)) {
                    weekActAmt.add(dayValueDl);
                }
            }
        }

        BigDecimal contractAmt = new BigDecimal(0);

        //查询开累计划产值(有效合同额)
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(new XmslContractInfo());
        if(xmslContractInfo != null) {
            contractAmt = xmslContractInfo.getEffectiveAmout();
        }


        // 查询年计划产值
        BigDecimal yearPlanAmt = new BigDecimal(0);
        JdglYearPlan jdglYearPlan1 = jdglYearPlanService.getUsingYearPlanByYear(year);
        if(jdglYearPlan1 != null){
            yearPlanAmt = jdglYearPlan1.getYearPlanValueDl();
        }

        // 查询季计划产值
        BigDecimal quarterPlanAmt = new BigDecimal(0);
        JdglQuarterPlan jdglQuarterPlan1 = jdglQuarterPlanService.getUsingQuarterPlanByYearAndQuarter(year,quarter);
        if(jdglQuarterPlan1 != null) {
            quarterPlanAmt = jdglQuarterPlan1.getThisPlanValueDl();
        }

        // 查询月计划产值
        BigDecimal monthPlanAmt = new BigDecimal(0);
        JdglMonthPlan usingMonthPlanByYearAndMonth = jdglMonthPlanService.getUsingMonthPlanByYearAndMonth(year, month);
        if(usingMonthPlanByYearAndMonth != null) {
            monthPlanAmt = usingMonthPlanByYearAndMonth.getThisPlanValueDl();
        }

        // 查询周计划产值
        BigDecimal weekPlanAmt = new BigDecimal(0);
        JdglWeekPlan usingWeekPlanByYearAndWeek = jdglWeekPlanService.getUsingWeekPlanByYearAndWeek(year, week);
        if(usingWeekPlanByYearAndWeek != null) {
            weekPlanAmt = usingWeekPlanByYearAndWeek.getThisPlanValueDl();
        }

        Map<String, BigDecimal> totalMap = new HashMap<>();
        totalMap.put("planAmt", contractAmt);
        totalMap.put("actAmt", totalActAmt);
        return2Map.put("total", totalMap);

        Map<String, BigDecimal> yearMap = new HashMap<>();
        totalMap.put("planAmt", yearPlanAmt);
        totalMap.put("actAmt", yearActAmt);
        return2Map.put("year", yearMap);

        Map<String, BigDecimal> quarterMap = new HashMap<>();
        totalMap.put("planAmt", quarterPlanAmt);
        totalMap.put("actAmt", quarterActAmt);
        return2Map.put("quarter", quarterMap);

        Map<String, BigDecimal> monthMap = new HashMap<>();
        totalMap.put("planAmt", monthPlanAmt);
        totalMap.put("actAmt", monthActAmt);
        return2Map.put("month", monthMap);

        Map<String, BigDecimal> weekMap = new HashMap<>();
        totalMap.put("planAmt", weekPlanAmt);
        totalMap.put("actAmt", weekActAmt);
        return2Map.put("week", weekMap);

        return return2Map;
    }

    /**
     * 获取wbs汇总数据
     * @param iPlanStatisticsQueryVO
     * @return
     */
    @Override
    public List<PlanStatisticsWbsValueVO> getWbsValueList(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {

        StatisticsUtils.initDateParams(iPlanStatisticsQueryVO);

        String queryDateType = iPlanStatisticsQueryVO.getQueryDateType();
        String year = iPlanStatisticsQueryVO.getYear();
        String quarter = iPlanStatisticsQueryVO.getQuarter();
        String month = iPlanStatisticsQueryVO.getMonth();
        String week = iPlanStatisticsQueryVO.getWeek();
        Date startDate = iPlanStatisticsQueryVO.getStartDate();
        Date endDate = iPlanStatisticsQueryVO.getEndDate();

        // 本周/月/季/年实际产值数据
        List<JdglDayScheduleWbs4Value> wbsListByDateRange = jdglDayScheduleWbsService.getWbsListByDateRange(startDate, endDate);

        if(CollectionUtils.isEmpty(wbsListByDateRange)) {
            return null;
        }

        List<PlanStatisticsWbsValueVO> returnList = new ArrayList<>();

        for (JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value : wbsListByDateRange) {
            PlanStatisticsWbsValueVO planStatisticsWbsValueVO = new PlanStatisticsWbsValueVO();
            planStatisticsWbsValueVO.setWbsCode(jdglDayScheduleWbs4Value.getWbsCode());
            planStatisticsWbsValueVO.setWbsName(jdglDayScheduleWbs4Value.getWbsName());
            planStatisticsWbsValueVO.setThisActValue(jdglDayScheduleWbs4Value.getThisValue());
            planStatisticsWbsValueVO.setWbsUnit(jdglDayScheduleWbs4Value.getUnit());
            planStatisticsWbsValueVO.setSort(jdglDayScheduleWbs4Value.getSort());
            planStatisticsWbsValueVO.setId(jdglDayScheduleWbs4Value.getId());
            planStatisticsWbsValueVO.setPid(jdglDayScheduleWbs4Value.getPid());
            returnList.add(planStatisticsWbsValueVO);
        }

        // 开累实际产值数据
        List<JdglDayScheduleWbs4Value> totalWbsListByDateRange = jdglDayScheduleWbsService.getTotalWbsListByDateRange(endDate);

        if(!CollectionUtils.isEmpty(totalWbsListByDateRange)) {
            for (PlanStatisticsWbsValueVO planStatisticsWbsValueVO : returnList) {
                String wbsCode = planStatisticsWbsValueVO.getWbsCode();
                Stream<JdglDayScheduleWbs4Value> jdglDayScheduleWbs4ValueStream = totalWbsListByDateRange.stream().filter(vo -> !wbsCode.equals(vo.getWbsCode()));
                if(jdglDayScheduleWbs4ValueStream != null) {
                    JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = jdglDayScheduleWbs4ValueStream.findFirst().get();
                    if(jdglDayScheduleWbs4Value != null) {
                        planStatisticsWbsValueVO.setTotalActValue(jdglDayScheduleWbs4Value.getThisValue());
                    }
                }

                // 本周/月/季/年计划产值数据
                switch (queryDateType) {
                    case "z":
                        List<JdglWeekImagePlan> jdglWeekImagePlans = jdglWeekImagePlanService.getWbsListByYearAndWeek(year, week);
                        if(!CollectionUtils.isEmpty(jdglWeekImagePlans)) {
                            Stream<JdglWeekImagePlan> jdglWeekImagePlanStream = jdglWeekImagePlans.stream().filter(vo -> !wbsCode.equals(vo.getWbsCode()));
                            if(jdglWeekImagePlanStream != null) {
                                JdglWeekImagePlan jdglWeekImagePlan = jdglWeekImagePlanStream.findFirst().get();
                                planStatisticsWbsValueVO.setThisPlanValue(jdglWeekImagePlan.getPlanCompValue());
                            }
                        }
                        break;
                    case "y":
                        List<JdglMonthImagePlan> jdglMonthImagePlans = jdglMonthImagePlanService.getWbsListByYearAndMonth(year, month);
                        if(!CollectionUtils.isEmpty(jdglMonthImagePlans)) {
                            Stream<JdglMonthImagePlan> jdglWeekImagePlanStream = jdglMonthImagePlans.stream().filter(vo -> !wbsCode.equals(vo.getWbsCode()));
                            if(jdglWeekImagePlanStream != null) {
                                JdglMonthImagePlan jdglWeekImagePlan = jdglWeekImagePlanStream.findFirst().get();
                                planStatisticsWbsValueVO.setThisPlanValue(jdglWeekImagePlan.getPlanCompValue());
                            }
                        }
                        break;
                    case "j":
                        List<JdglQuarterImagePlan> jdglQuarterImagePlans = jdglQuarterImagePlanService.getWbsListByYearAndQuarter(year, quarter);
                        if(!CollectionUtils.isEmpty(jdglQuarterImagePlans)) {
                            Stream<JdglQuarterImagePlan> jdglWeekImagePlanStream = jdglQuarterImagePlans.stream().filter(vo -> !wbsCode.equals(vo.getWbsCode()));
                            if(jdglWeekImagePlanStream != null) {
                                JdglQuarterImagePlan jdglWeekImagePlan = jdglWeekImagePlanStream.findFirst().get();
                                planStatisticsWbsValueVO.setThisPlanValue(jdglWeekImagePlan.getPlanCompValue());
                            }
                        }
                        break;
                    case "n":
                        List<JdglYearImagePlan> jdglYearImagePlans = jdglYearImagePlanService.getWbsListByYear(year);
                        if(!CollectionUtils.isEmpty(jdglYearImagePlans)) {
                            Stream<JdglYearImagePlan> jdglWeekImagePlanStream = jdglYearImagePlans.stream().filter(vo -> !wbsCode.equals(vo.getWbsCode()));
                            if(jdglWeekImagePlanStream != null) {
                                JdglYearImagePlan jdglWeekImagePlan = jdglWeekImagePlanStream.findFirst().get();
                                planStatisticsWbsValueVO.setThisPlanValue(jdglWeekImagePlan.getPlanCompValue());
                            }
                        }
                        break;
                }
            }
        }
        return returnList;
    }




}
