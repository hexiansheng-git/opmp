package com.hhwy.pm.jdgl.statistics.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.domain.JdglDayScheduleBill;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service.IJdglDayScheduleBillService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.domain.JdglMonthValuePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.IJdglMonthValuePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.IJdglQuarterImagePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.service.IJdglQuarterPlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.domain.JdglQuarterValuePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service.IJdglQuarterValuePlanService;
import com.hhwy.pm.jdgl.statistics.domain.*;
import com.hhwy.pm.jdgl.statistics.domain.export.PlanStatisticsBillValueVO4Export;
import com.hhwy.pm.jdgl.statistics.domain.export.PlanStatisticsWbsImageVO4Export;
import com.hhwy.pm.jdgl.statistics.domain.export.PlanStatisticsWbsValueVO4Export;
import com.hhwy.pm.jdgl.statistics.service.IPlanStatisticsService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.domain.JdglWeekImagePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service.IJdglWeekImagePlanService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.service.IJdglWeekPlanService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.domain.JdglWeekValuePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.service.IJdglWeekValuePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.IJdglYearImagePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.service.IJdglYearPlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.domain.JdglYearValuePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.service.IJdglYearValuePlanService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PlanStatisticsServiceImpl implements IPlanStatisticsService {

    @Autowired
    private IJdglDayScheduleService iJdglDayScheduleService;
    @Autowired
    private IJdglDayScheduleWbsService jdglDayScheduleWbsService;
    @Autowired
    private IJdglDayScheduleBillService jdglDayScheduleBillService;
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
    private IXmslContractListService xmslContractListService;
    @Autowired
    private IJdglYearImagePlanService jdglYearImagePlanService;
    @Autowired
    private IJdglQuarterImagePlanService jdglQuarterImagePlanService;
    @Autowired
    private IJdglMonthImagePlanService jdglMonthImagePlanService;
    @Autowired
    private IJdglWeekImagePlanService jdglWeekImagePlanService;
    @Autowired
    private IJdglYearValuePlanService jdglYearValuePlanService;
    @Autowired
    private IJdglQuarterValuePlanService jdglQuarterValuePlanService;
    @Autowired
    private IJdglMonthValuePlanService jdglMonthValuePlanService;
    @Autowired
    private IJdglWeekValuePlanService jdglWeekValuePlanService;

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

        Map<String, Map<String, BigDecimal>> return2Map = new HashMap<>();

        // 根据结束日期获取日填报数据
        List<JdglDaySchedule> listByDateRange = iJdglDayScheduleService.getListByDateRange(null, endDate);

        BigDecimal totalActAmt = BigDecimal.ZERO;
        BigDecimal yearActAmt = BigDecimal.ZERO;
        BigDecimal quarterActAmt = BigDecimal.ZERO;
        BigDecimal monthActAmt = BigDecimal.ZERO;
        BigDecimal weekActAmt = BigDecimal.ZERO;
        if(!CollectionUtils.isEmpty(listByDateRange)) {
            for (JdglDaySchedule jdglDaySchedule : listByDateRange) {
                BigDecimal dayValueDl = jdglDaySchedule.getDayValueDl();
                Date date = jdglDaySchedule.getDate();
                Calendar cl = Calendar.getInstance();
                cl.setTime(date);
                if(endDate.before(date)) {
                    continue;
                }
                totalActAmt = totalActAmt.add(dayValueDl == null ? BigDecimal.ZERO : dayValueDl);
                int yearI = cl.get(Calendar.YEAR);
                if(!year.equals(yearI + "")) {
                    continue;
                }
                yearActAmt = yearActAmt.add(dayValueDl == null ? BigDecimal.ZERO : dayValueDl);
                int dateMonth = cl.get(Calendar.MONTH) + 1;

                if(quarter == null) {
                    continue;
                }
                int i = Integer.parseInt(quarter);

                if(i*3 < dateMonth || i*3-2 > dateMonth) {
                    continue;
                }
                quarterActAmt = quarterActAmt.add(dayValueDl == null ? BigDecimal.ZERO : dayValueDl);

                if(month == null || Integer.parseInt(month) != dateMonth){
                    continue;
                }
                monthActAmt = monthActAmt.add(dayValueDl == null ? BigDecimal.ZERO : dayValueDl);

                if((startDate.before(date) || startDate.equals(date)) && (endDate.after(date)||endDate.equals(date))) {
                    weekActAmt = weekActAmt.add(dayValueDl == null ? BigDecimal.ZERO : dayValueDl);
                }
            }
        }

        BigDecimal contractAmt = BigDecimal.ZERO;

        //查询开累计划产值(有效合同额)
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(new XmslContractInfo());
        if(xmslContractInfo != null) {
            contractAmt = xmslContractInfo.getEffectiveAmout() == null ? BigDecimal.ZERO : xmslContractInfo.getEffectiveAmout();
        }

        // 查询年计划产值
        BigDecimal yearPlanAmt = BigDecimal.ZERO;
        JdglYearPlan jdglYearPlan1 = jdglYearPlanService.getUsingYearPlanByYear(year);
        if(jdglYearPlan1 != null){
            yearPlanAmt = jdglYearPlan1.getYearPlanValueDl();
            BigDecimal exchange  = jdglYearPlan1.getExchangeRate();
            if(exchange != null && BigDecimal.ZERO.compareTo(exchange) != 0) {
                contractAmt = contractAmt.divide(exchange, 2, BigDecimal.ROUND_HALF_UP);
            } else {
                contractAmt = BigDecimal.ZERO;
            }
        } else {
            contractAmt = BigDecimal.ZERO;
        }

        // 查询季计划产值
        BigDecimal quarterPlanAmt = BigDecimal.ZERO;
        JdglQuarterPlan jdglQuarterPlan1 = jdglQuarterPlanService.getUsingQuarterPlanByYearAndQuarter(year,quarter);
        if(jdglQuarterPlan1 != null) {
            quarterPlanAmt = jdglQuarterPlan1.getThisPlanValueDl();
        }

        // 查询月计划产值
        BigDecimal monthPlanAmt = BigDecimal.ZERO;
        JdglMonthPlan usingMonthPlanByYearAndMonth = jdglMonthPlanService.getUsingMonthPlanByYearAndMonth(year, month);
        if(usingMonthPlanByYearAndMonth != null) {
            monthPlanAmt = usingMonthPlanByYearAndMonth.getThisPlanValueDl();
        }

        // 查询周计划产值
        BigDecimal weekPlanAmt = BigDecimal.ZERO;
        JdglWeekPlan usingWeekPlanByYearAndWeek = jdglWeekPlanService.getUsingWeekPlanByYearAndWeek(year, week);
        if(usingWeekPlanByYearAndWeek != null) {
            weekPlanAmt = usingWeekPlanByYearAndWeek.getThisPlanValueDl();
        }

        Map<String, BigDecimal> totalMap = new HashMap<>();
        totalMap.put("planAmt", contractAmt == null ? BigDecimal.ZERO : contractAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        totalMap.put("actAmt", totalActAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        if(contractAmt != null && BigDecimal.ZERO.compareTo(contractAmt) != 0) {
            totalMap.put("ratio", totalActAmt.divide(contractAmt, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        } else {
            totalMap.put("ratio", BigDecimal.ZERO);
        }
        return2Map.put("total", totalMap);

        Map<String, BigDecimal> yearMap = new HashMap<>();
        yearMap.put("planAmt", yearPlanAmt == null ? BigDecimal.ZERO : yearPlanAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        yearMap.put("actAmt", yearActAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        if(yearPlanAmt != null && BigDecimal.ZERO.compareTo(yearPlanAmt) != 0) {
            yearMap.put("ratio", yearActAmt.divide(yearPlanAmt, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        } else {
            yearMap.put("ratio", BigDecimal.ZERO);
        }
        return2Map.put("year", yearMap);

        Map<String, BigDecimal> quarterMap = new HashMap<>();
        quarterMap.put("planAmt", quarterPlanAmt == null ? BigDecimal.ZERO : quarterPlanAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        quarterMap.put("actAmt", quarterActAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        if(quarterPlanAmt != null && BigDecimal.ZERO.compareTo(quarterPlanAmt) != 0) {
            quarterMap.put("ratio", quarterActAmt.divide(quarterPlanAmt, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        } else {
            quarterMap.put("ratio", BigDecimal.ZERO);
        }
        return2Map.put("quarter", quarterMap);

        Map<String, BigDecimal> monthMap = new HashMap<>();
        monthMap.put("planAmt", monthPlanAmt == null ? BigDecimal.ZERO : monthPlanAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        monthMap.put("actAmt", monthActAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        if(monthPlanAmt != null && BigDecimal.ZERO.compareTo(monthPlanAmt) != 0) {
            monthMap.put("ratio", monthActAmt.divide(monthPlanAmt, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        } else {
            monthMap.put("ratio", BigDecimal.ZERO);
        }
        return2Map.put("month", monthMap);

        Map<String, BigDecimal> weekMap = new HashMap<>();
        weekMap.put("planAmt", weekPlanAmt == null ? BigDecimal.ZERO : weekPlanAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        weekMap.put("actAmt", weekActAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        if(weekPlanAmt != null && BigDecimal.ZERO.compareTo(weekPlanAmt) != 0) {
            weekMap.put("ratio", weekActAmt.divide(weekPlanAmt, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        } else {
            weekMap.put("ratio", BigDecimal.ZERO);
        }
        return2Map.put("week", weekMap);

        return return2Map;
    }

    @Override
    public List<PlanStatisticsValueCompVO> getValueCompData4VO(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {

        List<PlanStatisticsValueCompVO> returnList = new ArrayList<>();

        Map<String, Map<String, BigDecimal>> valueCompData = getValueCompData(iPlanStatisticsQueryVO);

        Set<String> keys = valueCompData.keySet();

        PlanStatisticsValueCompVO vo1 = new PlanStatisticsValueCompVO();
        PlanStatisticsValueCompVO vo2 = new PlanStatisticsValueCompVO();
        PlanStatisticsValueCompVO vo3 = new PlanStatisticsValueCompVO();
        vo1.setType("计划完成产值(万美元)");
        vo2.setType("实际完成产值(万美元)");
        vo3.setType("完成比例(%)");
        for (String key : keys) {
            switch (key) {
                case "week":
                    Map<String, BigDecimal> week = valueCompData.get("week");
                    vo1.setWeekValue(week.get("planAmt"));
                    vo2.setWeekValue(week.get("actAmt"));
                    vo3.setWeekValue(week.get("ratio"));
                    break;
                case "month":
                    Map<String, BigDecimal> month = valueCompData.get("month");
                    vo1.setMonthValue(month.get("planAmt"));
                    vo2.setMonthValue(month.get("actAmt"));
                    vo3.setMonthValue(month.get("ratio"));
                    break;
                case "quarter":
                    Map<String, BigDecimal> quarter = valueCompData.get("quarter");
                    vo1.setQuarterValue(quarter.get("planAmt"));
                    vo2.setQuarterValue(quarter.get("actAmt"));
                    vo3.setQuarterValue(quarter.get("ratio"));
                    break;
                case "year":
                    Map<String, BigDecimal> year = valueCompData.get("year");
                    vo1.setYearValue(year.get("planAmt"));
                    vo2.setYearValue(year.get("actAmt"));
                    vo3.setYearValue(year.get("ratio"));
                    break;
                case "total":
                    Map<String, BigDecimal> total = valueCompData.get("total");
                    vo1.setTotalValue(total.get("planAmt"));
                    vo2.setTotalValue(total.get("actAmt"));
                    vo3.setTotalValue(total.get("ratio"));
                    break;
            }
        }
        returnList.add(vo1);
        returnList.add(vo2);
        returnList.add(vo3);
        return returnList;
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
            planStatisticsWbsValueVO.setThisActValue(jdglDayScheduleWbs4Value.getThisValue() == null ? BigDecimal.ZERO : jdglDayScheduleWbs4Value.getThisValue().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
            planStatisticsWbsValueVO.setWbsUnit(jdglDayScheduleWbs4Value.getUnit());
            planStatisticsWbsValueVO.setSort(jdglDayScheduleWbs4Value.getSort());
            planStatisticsWbsValueVO.setId(jdglDayScheduleWbs4Value.getId());
            planStatisticsWbsValueVO.setPid(jdglDayScheduleWbs4Value.getPid());
            returnList.add(planStatisticsWbsValueVO);
        }

        // 开累实际产值数据
        List<JdglDayScheduleWbs4Value> totalWbsListByDateRange = jdglDayScheduleWbsService.getTotalWbsListByDateRange(endDate);

        if(!CollectionUtils.isEmpty(totalWbsListByDateRange)) {
            List<JdglWeekImagePlan> jdglWeekImagePlans = "z".equals(queryDateType) ? jdglWeekImagePlanService.getWbsListByYearAndWeek(year, week) : null;
            List<JdglMonthImagePlan> jdglMonthImagePlans = "y".equals(queryDateType) ? jdglMonthImagePlanService.getWbsListByYearAndMonth(year, month) : null;
            List<JdglQuarterImagePlan> jdglQuarterImagePlans = "j".equals(queryDateType) ? jdglQuarterImagePlanService.getWbsListByYearAndQuarter(year, quarter) : null;
            List<JdglYearImagePlan> jdglYearImagePlans = "n".equals(queryDateType) ? jdglYearImagePlanService.getWbsListByYear(year) : null;
            for (PlanStatisticsWbsValueVO planStatisticsWbsValueVO : returnList) {
                String wbsCode = planStatisticsWbsValueVO.getWbsCode();
                JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = totalWbsListByDateRange.stream().filter(vo -> wbsCode.equals(vo.getWbsCode())).findFirst().orElse(null);
                if(jdglDayScheduleWbs4Value != null) {
                    planStatisticsWbsValueVO.setTotalActValue(jdglDayScheduleWbs4Value.getThisValue() == null ? BigDecimal.ZERO : jdglDayScheduleWbs4Value.getThisValue().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
                }


                // 本周/月/季/年计划产值数据
                switch (queryDateType) {
                    case "z":
                        if(!CollectionUtils.isEmpty(jdglWeekImagePlans)) {
                            JdglWeekImagePlan imagePlan = jdglWeekImagePlans.stream().filter(vo -> wbsCode.equals(vo.getWorkCode())).findFirst().orElse(null);
                            if(imagePlan != null) planStatisticsWbsValueVO.setThisPlanValue(imagePlan.getPlanCompValue() == null ? BigDecimal.ZERO : imagePlan.getPlanCompValue().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
                        }
                        break;
                    case "y":
                        if(!CollectionUtils.isEmpty(jdglMonthImagePlans)) {
                            JdglMonthImagePlan jdglMonthImagePlan = jdglMonthImagePlans.stream().filter(vo -> wbsCode.equals(vo.getWorkCode())).findFirst().orElse(null);
                            if(jdglMonthImagePlan != null) planStatisticsWbsValueVO.setThisPlanValue(jdglMonthImagePlan.getPlanCompValue()== null ? BigDecimal.ZERO : jdglMonthImagePlan.getPlanCompValue().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));

                        }
                        break;
                    case "j":
                        if(!CollectionUtils.isEmpty(jdglQuarterImagePlans)) {
                            JdglQuarterImagePlan jdglQuarterImagePlan = jdglQuarterImagePlans.stream().filter(vo -> wbsCode.equals(vo.getWorkCode())).findFirst().orElse(null);
                            if(jdglQuarterImagePlan != null) planStatisticsWbsValueVO.setThisPlanValue(jdglQuarterImagePlan.getPlanCompValue()== null ? BigDecimal.ZERO : jdglQuarterImagePlan.getPlanCompValue().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
                        }
                        break;
                    case "n":
                        if(!CollectionUtils.isEmpty(jdglYearImagePlans)) {
                            JdglYearImagePlan jdglYearImagePlan = jdglYearImagePlans.stream().filter(vo -> wbsCode.equals(vo.getWorkCode())).findFirst().orElse(null);
                            if(jdglYearImagePlan != null)  planStatisticsWbsValueVO.setThisPlanValue(jdglYearImagePlan.getPlanCompValue()== null ? BigDecimal.ZERO : jdglYearImagePlan.getPlanCompValue().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
                        }
                        break;
                }
            }
        }

        List<PlanStatisticsWbsValueVO> build = TreeUtil.build(returnList, null);

        return CollectionUtils.isEmpty(build)?new ArrayList<>():build;
    }


    public List<PlanStatisticsWbsValueVO4Export> getWbsValueList4Export(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {
        List<PlanStatisticsWbsValueVO> wbsValueList = getWbsValueList(iPlanStatisticsQueryVO);
        if (CollectionUtils.isEmpty(wbsValueList)) return null;
        List<PlanStatisticsWbsValueVO> planStatisticsWbsValueVOS = TreeUtil.treeToList(wbsValueList);
        List<PlanStatisticsWbsValueVO4Export> returnList = new ArrayList<>();
        for (PlanStatisticsWbsValueVO planStatisticsWbsValueVO : planStatisticsWbsValueVOS) {
            String s = JSONObject.toJSONString(planStatisticsWbsValueVO);
            PlanStatisticsWbsValueVO4Export planStatisticsWbsValueVO4Export = JSONObject.parseObject(s, PlanStatisticsWbsValueVO4Export.class);
            returnList.add(planStatisticsWbsValueVO4Export);
        }
        return returnList;
    }

    /**
     * 获取清单汇总数据
     * @param iPlanStatisticsQueryVO
     * @return
     */
    public List<PlanStatisticsBillValueVO> getBillValueList(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {

        StatisticsUtils.initDateParams(iPlanStatisticsQueryVO);

        String queryDateType = iPlanStatisticsQueryVO.getQueryDateType();
        String year = iPlanStatisticsQueryVO.getYear();
        String quarter = iPlanStatisticsQueryVO.getQuarter();
        String month = iPlanStatisticsQueryVO.getMonth();
        String week = iPlanStatisticsQueryVO.getWeek();
        Date startDate = iPlanStatisticsQueryVO.getStartDate();
        Date endDate = iPlanStatisticsQueryVO.getEndDate();

        List<PlanStatisticsBillValueVO> returnList = new ArrayList<>();

        // 获取主合同清单数据
        List<XmslContractList> xmslContractListVos = xmslContractListService.getValidMaxVersionContractInventoryList();

        if(CollectionUtils.isEmpty(xmslContractListVos)) {
            return returnList;
        }

        //组装树
        //List<XmslContractList> billTreeList = ListTreeUtil.formatTree(xmslContractListVos, o -> o.getPid() == 0, (r, n) -> r.getId().equals(n.getPid()), XmslContractList::getChildren, XmslContractList::setChildren);

        // 获取区间清单填报数据
        List<JdglDayScheduleBill> billValueListByRangeDate = jdglDayScheduleBillService.getBillValueListByRangeDate(startDate, endDate);

        // 获取开累填报清单数据
        List<JdglDayScheduleBill> billValueListByEndDate = jdglDayScheduleBillService.getBillValueListByEndDate(startDate);

        if(CollectionUtils.isEmpty(billValueListByRangeDate)) {
            return returnList;
        }

        // 填充实际产值数据
        for (JdglDayScheduleBill jdglDayScheduleBill : billValueListByRangeDate) {
            String billCode = jdglDayScheduleBill.getBillCode();
            XmslContractList xmslContractList1 = xmslContractListVos.stream().filter(vo -> billCode.equals(vo.getCode())).findFirst().orElse(null);
            for (XmslContractList xmslContractList : xmslContractListVos) {
                if(xmslContractList1 != null && xmslContractList1.getAncestors() !=
                        null && (xmslContractList1.getAncestors() + "," + xmslContractList1.getId()).contains(xmslContractList.getId()+"")) {
                    String listCode = xmslContractList.getCode();
                    PlanStatisticsBillValueVO planStatisticsBillValueVO1 = returnList.stream().filter(vo -> listCode.equals(vo.getBillCode())).findFirst().orElse(null);
                    if(planStatisticsBillValueVO1 != null) continue;
                    PlanStatisticsBillValueVO planStatisticsBillValueVO = new PlanStatisticsBillValueVO();
                    planStatisticsBillValueVO.setId(xmslContractList.getId());
                    planStatisticsBillValueVO.setPid(xmslContractList.getPid());
                    planStatisticsBillValueVO.setBillCode(listCode);
                    planStatisticsBillValueVO.setBillName(xmslContractList.getChineseName());
                    planStatisticsBillValueVO.setBillUnit(xmslContractList.getUnit());
                    BigDecimal price = xmslContractList.getChangeUnitPrice() == null ? xmslContractList.getWinUnitPrice() : xmslContractList.getChangeUnitPrice();
                    planStatisticsBillValueVO.setBillPrice(price);

                    if(xmslContractList.getChangeNum() != null) {
                        planStatisticsBillValueVO.setRemainDesignNum(xmslContractList.getChangeNum().subtract(planStatisticsBillValueVO.getLastTotalDesignNum()));
                    }
                    if(billCode.equals(listCode)) {
                        BigDecimal thisCompDesignNum = planStatisticsBillValueVO.getThisCompDesignNum() == null ? BigDecimal.ZERO : planStatisticsBillValueVO.getThisCompDesignNum();
                        BigDecimal lastTotalDesignNum = planStatisticsBillValueVO.getLastTotalDesignNum() == null ? BigDecimal.ZERO : planStatisticsBillValueVO.getLastTotalDesignNum();
                        if(jdglDayScheduleBill.getThisQuantity() != null) thisCompDesignNum = thisCompDesignNum.add(jdglDayScheduleBill.getThisQuantity());
                        planStatisticsBillValueVO.setThisCompDesignNum(thisCompDesignNum);
                        BigDecimal thisCompValue = BigDecimal.ZERO;
                        if(price != null) thisCompValue = price.multiply(thisCompDesignNum);
                        planStatisticsBillValueVO.setThisCompValue(thisCompValue.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
                        planStatisticsBillValueVO.setThisTotalDesignNum(thisCompDesignNum.add(lastTotalDesignNum));

                    }
                    returnList.add(planStatisticsBillValueVO);
                }
            }
        }

        if(CollectionUtils.isEmpty(returnList)) {
            return returnList;
        }

        List<JdglWeekValuePlan> jdglWeekValuePlans = "z".equals(queryDateType) ? jdglWeekValuePlanService.getBillListByYearAndWeek(year, week) : null;
        List<JdglMonthValuePlan> jdglMonthValuePlans = "y".equals(queryDateType) ? jdglMonthValuePlanService.getBillListByYearAndMonth(year, month) : null;
        List<JdglQuarterValuePlan> jdglQuarterValuePlans = "j".equals(queryDateType) ? jdglQuarterValuePlanService.getBillListByYearAndQuarter(year, quarter) : null;
        List<JdglYearValuePlan> jdglYearValuePlans = "n".equals(queryDateType) ? jdglYearValuePlanService.getBillListByYear(year) : null;

        for (PlanStatisticsBillValueVO planStatisticsBillValueVO : returnList) {
            String billCode = planStatisticsBillValueVO.getBillCode();
            if(billCode == null) {
                continue;
            }
            // 填充计划产值数据
            switch (queryDateType) {
                case "z":
                    if(!CollectionUtils.isEmpty(jdglWeekValuePlans)){
                        JdglWeekValuePlan jdglWeekValuePlan = jdglWeekValuePlans.stream().filter(vo -> billCode.equals(vo.getInventoryCode())).findFirst().orElse(null);
                        if(jdglWeekValuePlan != null) {
                            planStatisticsBillValueVO.setThisPlanDesignNum(jdglWeekValuePlan.getWeekPlanCompDesignQuantity());;
                            planStatisticsBillValueVO.setThisPlanValue(jdglWeekValuePlan.getWeekPlanValueCu() == null ? BigDecimal.ZERO : jdglWeekValuePlan.getWeekPlanValueCu().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
                            planStatisticsBillValueVO.setLastTotalDesignNum(jdglWeekValuePlan.getTotalCompDesignQuantity());
                            planStatisticsBillValueVO.setRemainDesignNum(jdglWeekValuePlan.getRemainDesignQuantity());
                        }
                    }
                    break;
                case "y":
                    if(!CollectionUtils.isEmpty(jdglMonthValuePlans)){
                        JdglMonthValuePlan jdglMonthValuePlan = jdglMonthValuePlans.stream().filter(vo -> billCode.equals(vo.getInventoryCode())).findFirst().orElse(null);
                        if(jdglMonthValuePlan != null) {
                            planStatisticsBillValueVO.setThisPlanDesignNum(jdglMonthValuePlan.getMonthPlanCompDesignQuantity());;
                            planStatisticsBillValueVO.setThisPlanValue(jdglMonthValuePlan.getMonthPlanValueCu()== null ? BigDecimal.ZERO : jdglMonthValuePlan.getMonthPlanValueCu().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
                            planStatisticsBillValueVO.setLastTotalDesignNum(jdglMonthValuePlan.getTotalCompDesignQuantity());
                            planStatisticsBillValueVO.setRemainDesignNum(jdglMonthValuePlan.getRemainDesignQuantity());
                        }
                    }
                    break;
                case "j":
                    if(!CollectionUtils.isEmpty(jdglQuarterValuePlans)){
                        JdglQuarterValuePlan jdglQuarterValuePlan = jdglQuarterValuePlans.stream().filter(vo -> billCode.equals(vo.getInventoryCode())).findFirst().orElse(null);
                        if(jdglQuarterValuePlan != null) {
                            planStatisticsBillValueVO.setThisPlanDesignNum(jdglQuarterValuePlan.getQuarterPlanCompDesignQuantity());;
                            planStatisticsBillValueVO.setThisPlanValue(jdglQuarterValuePlan.getQuarterPlanValueCu()== null ? BigDecimal.ZERO : jdglQuarterValuePlan.getQuarterPlanValueCu().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
                            planStatisticsBillValueVO.setLastTotalDesignNum(jdglQuarterValuePlan.getTotalCompDesignQuantity());
                            planStatisticsBillValueVO.setRemainDesignNum(jdglQuarterValuePlan.getRemainDesignQuantity());
                        }
                    }
                    break;
                case "n":
                    if(!CollectionUtils.isEmpty(jdglYearValuePlans)){
                        JdglYearValuePlan jdglYearValuePlan = jdglYearValuePlans.stream().filter(vo -> billCode.equals(vo.getInventoryCode())).findFirst().orElse(null);
                        if(jdglYearValuePlan != null) {
                            planStatisticsBillValueVO.setThisPlanDesignNum(jdglYearValuePlan.getYearPlanCompDesignQuantity());;
                            planStatisticsBillValueVO.setThisPlanValue(jdglYearValuePlan.getYearPlanValueCu()== null ? BigDecimal.ZERO : jdglYearValuePlan.getYearPlanValueCu().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
                            planStatisticsBillValueVO.setLastTotalDesignNum(jdglYearValuePlan.getTotalCompDesignQuantity());
                            planStatisticsBillValueVO.setRemainDesignNum(jdglYearValuePlan.getRemainDesignQuantity());
                        }
                    }
                    break;
            }
        }

        List<PlanStatisticsBillValueVO> build = TreeUtil.build(returnList, null);

        return build;

    }

    @Override
    public List<PlanStatisticsBillValueVO4Export> getBillValueList4Export(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {
        List<PlanStatisticsBillValueVO> billValueList = getBillValueList(iPlanStatisticsQueryVO);
        if(CollectionUtils.isEmpty(billValueList)) return null;
        List<PlanStatisticsBillValueVO> planStatisticsBillValueVOList = TreeUtil.treeToList(billValueList);
        List<PlanStatisticsBillValueVO4Export> returnList = new ArrayList<>();
        for (PlanStatisticsBillValueVO planStatisticsBillValueVO : planStatisticsBillValueVOList) {
            String s = JSONObject.toJSONString(planStatisticsBillValueVO);
            PlanStatisticsBillValueVO4Export planStatisticsBillValueVO4Export = JSONObject.parseObject(s, PlanStatisticsBillValueVO4Export.class);
            returnList.add(planStatisticsBillValueVO4Export);
        }
        return returnList;
    }

    /**
     * 从形象汇总
     * @param iPlanStatisticsQueryVO
     * @return
     */
    public List<PlanStatisticsWbsImageVO> getImageWbsList(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {

        StatisticsUtils.initDateParams(iPlanStatisticsQueryVO);

        String queryDateType = iPlanStatisticsQueryVO.getQueryDateType();
        String year = iPlanStatisticsQueryVO.getYear();
        String quarter = iPlanStatisticsQueryVO.getQuarter();
        String month = iPlanStatisticsQueryVO.getMonth();
        String week = iPlanStatisticsQueryVO.getWeek();
        Date startDate = iPlanStatisticsQueryVO.getStartDate();
        Date endDate = iPlanStatisticsQueryVO.getEndDate();

        List<PlanStatisticsWbsImageVO> returnList = new ArrayList<PlanStatisticsWbsImageVO>();

        // 本周/月/季/年实际产值数据
        List<JdglDayScheduleWbs4Value> wbsListByDateRange = jdglDayScheduleWbsService.getWbsListByDateRange(startDate, endDate);

        if(CollectionUtils.isEmpty(wbsListByDateRange)) {
            return returnList;
        }

        for (JdglDayScheduleWbs4Value dayScheduleWbs4Value : wbsListByDateRange) {
            PlanStatisticsWbsImageVO planStatisticsWbsImageVO = new PlanStatisticsWbsImageVO();

            planStatisticsWbsImageVO.setId(dayScheduleWbs4Value.getId());
            planStatisticsWbsImageVO.setPid(dayScheduleWbs4Value.getPid());
            planStatisticsWbsImageVO.setWbsCode(dayScheduleWbs4Value.getWbsCode());
            planStatisticsWbsImageVO.setWbsName(dayScheduleWbs4Value.getWbsName());
            planStatisticsWbsImageVO.setWbsUnit(dayScheduleWbs4Value.getUnit());
            planStatisticsWbsImageVO.setDesignNum(dayScheduleWbs4Value.getDesignQuantity());
            planStatisticsWbsImageVO.setThisCompNum(dayScheduleWbs4Value.getThisQuantity());
            returnList.add(planStatisticsWbsImageVO);
        }

        List<PlanStatisticsWbsImageVO> build = null;
        if(!CollectionUtils.isEmpty(returnList)) {
            build = TreeUtil.build(returnList, null);
        }


        return CollectionUtils.isEmpty(build) ? new ArrayList<>() : build;

    }

    @Override
    public List<PlanStatisticsWbsImageVO4Export> getImageWbsList4Export(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {
        List<PlanStatisticsWbsImageVO> imageWbsList = getImageWbsList(iPlanStatisticsQueryVO);
        if(CollectionUtils.isEmpty(imageWbsList)) return null;
        List<PlanStatisticsWbsImageVO> planStatisticsWbsImageVOS = TreeUtil.treeToList(imageWbsList);
        List<PlanStatisticsWbsImageVO4Export> returnList = new ArrayList<>();
        for(PlanStatisticsWbsImageVO planStatisticsWbsImageVO:planStatisticsWbsImageVOS) {
            String s = JSONObject.toJSONString(planStatisticsWbsImageVO);
            PlanStatisticsWbsImageVO4Export planStatisticsWbsImageVO4Export = JSONObject.parseObject(s, PlanStatisticsWbsImageVO4Export.class);
            returnList.add(planStatisticsWbsImageVO4Export);
        }
        return returnList;
    }

    /**
     * 获取年季产值对比
     * @param iPlanStatisticsQueryVO
     * @return
     */
    public Map<String, PlanStatisticsPeriodValueVO> getYearValueCompareList(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {

        Map<String, PlanStatisticsPeriodValueVO> returnMapList = new HashMap<>();

        StatisticsUtils.initDateParams(iPlanStatisticsQueryVO);

        String queryDateType = iPlanStatisticsQueryVO.getQueryDateType();
        String year = iPlanStatisticsQueryVO.getYear();
        String quarter = iPlanStatisticsQueryVO.getQuarter();
        String month = iPlanStatisticsQueryVO.getMonth();
        String week = iPlanStatisticsQueryVO.getWeek();
        Date startDate = iPlanStatisticsQueryVO.getStartDate();
        Date endDate = iPlanStatisticsQueryVO.getEndDate();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse((Integer.valueOf(year)-1)+"-12"+"-21");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<JdglDaySchedule> listByDateRange = iJdglDayScheduleService.getListByDateRange(date, endDate);
        List<JdglDaySchedule> listByDateRangeByEnd = iJdglDayScheduleService.getListByDateRange(null, endDate);

        PlanStatisticsPeriodValueVO planStatisticsPeriodValueVOQuarter = new PlanStatisticsPeriodValueVO();
        PlanStatisticsPeriodValueVO planStatisticsPeriodValueVOYear = new PlanStatisticsPeriodValueVO();

        switch (queryDateType) {
            case "j":
                List<String> periodListQ = new ArrayList<>();
                List<BigDecimal> planValueListQ = new ArrayList<>();
                List<BigDecimal> compValueListQ = new ArrayList<>();
                // 获取季计划产值数据
                JdglQuarterPlan jdglQuarterPlan = new JdglQuarterPlan();
                jdglQuarterPlan.setYear(year);
                jdglQuarterPlan.setIsUse("1");
                jdglQuarterPlan.setTaskStatus("5");
                List<JdglQuarterPlan> jdglQuarterPlanList = jdglQuarterPlanService.getJdglQuarterPlanList(jdglQuarterPlan);

                for(int i = 1; i <= 4; i++) {
                    PlanStatisticsPeriodValueVO planStatisticsPeriodValueVO = new PlanStatisticsPeriodValueVO();
                    String period = i+"";
                    planStatisticsPeriodValueVO.setPeriod(period);
                    periodListQ.add(period);
                    if(!CollectionUtils.isEmpty(jdglQuarterPlanList)) {
                        List<JdglQuarterPlan> collect = jdglQuarterPlanList.stream().filter(vo -> period.equals(vo.getQuarter()) && year.equals(vo.getYear())).collect(Collectors.toList());
                        if(!CollectionUtils.isEmpty(collect)) {
                            for (JdglQuarterPlan jdglQuarterPlan1 : collect) {
                                if(planStatisticsPeriodValueVO.getPlanValue() == null) planStatisticsPeriodValueVO.setPlanValue(BigDecimal.ZERO);
                                if(jdglQuarterPlan1.getThisPlanValueDl() != null) {
                                    planStatisticsPeriodValueVO.setPlanValue(planStatisticsPeriodValueVO.getPlanValue().add(jdglQuarterPlan1.getThisPlanValueDl()));
                                }
                            }
                        }
                    }
                    // 获取季实际产值数据
                    Map<String, Date> dateRange4Quarter = StatisticsUtils.getDateRange4Quarter(year, period);
                    Date start = dateRange4Quarter.get("start");
                    Date end = dateRange4Quarter.get("end");
                    if(!CollectionUtils.isEmpty(listByDateRangeByEnd)) {
                        List<JdglDaySchedule> collect = listByDateRangeByEnd.stream().filter(vo ->
                                start.before(vo.getDate()) && end.after(vo.getDate())
                        ).collect(Collectors.toList());
                        if(!CollectionUtils.isEmpty(collect)) {
                            for (JdglDaySchedule jdglDaySchedule : collect) {
                                if (planStatisticsPeriodValueVO.getCompValue() == null) planStatisticsPeriodValueVO.setCompValue(BigDecimal.ZERO);
                                if(jdglDaySchedule.getDayValueDl() != null) {
                                    planStatisticsPeriodValueVO.setCompValue(planStatisticsPeriodValueVO.getCompValue().add(jdglDaySchedule.getDayValueDl()));
                                }

                            }
                        }
                    }

                    planValueListQ.add(planStatisticsPeriodValueVO.getPlanValue() == null ? BigDecimal.ZERO : planStatisticsPeriodValueVO.getPlanValue());
                    compValueListQ.add(planStatisticsPeriodValueVO.getCompValue() == null ? BigDecimal.ZERO : StatisticsUtils.getDivideTenThousand(planStatisticsPeriodValueVO.getCompValue()));
//                    quarterList.add(planStatisticsPeriodValueVO);
                }
                planStatisticsPeriodValueVOQuarter.setPeriodList(periodListQ);
                planStatisticsPeriodValueVOQuarter.setPlanValueList(planValueListQ);
                planStatisticsPeriodValueVOQuarter.setCompValueList(compValueListQ);

                returnMapList.put("quarter", planStatisticsPeriodValueVOQuarter);

            case "n":
                List<String> periodListY = new ArrayList<>();
                List<BigDecimal> planValueListY = new ArrayList<>();
                List<BigDecimal> compValueListY = new ArrayList<>();
                List<PlanStatisticsPeriodValueVO> yearList = new ArrayList<>();
                JdglYearPlan jdglYearPlan1 = new JdglYearPlan();
                jdglYearPlan1.setIsUse("1");
                // 获取年计划产值数据
                List<JdglYearPlan> jdglYearPlanList = jdglYearPlanService.getJdglYearPlanList(jdglYearPlan1);

                if(!CollectionUtils.isEmpty(jdglYearPlanList)) {
                    List<JdglYearPlan> collect1 = jdglYearPlanList.stream().sorted(Comparator.comparing(JdglYearPlan::getYear)).collect(Collectors.toList());
                    for (JdglYearPlan jdglYearPlan : collect1) {
                        if(year.compareTo(jdglYearPlan.getYear()) >= 0) {
                            PlanStatisticsPeriodValueVO planStatisticsPeriodValueVO = new PlanStatisticsPeriodValueVO();
                            planStatisticsPeriodValueVO.setPeriod(jdglYearPlan.getYear());
                            periodListY.add(jdglYearPlan.getYear());
                            planStatisticsPeriodValueVO.setPlanValue(jdglYearPlan.getYearPlanValueDl());
                            planValueListY.add(jdglYearPlan.getYearPlanValueDl() == null ? BigDecimal.ZERO : jdglYearPlan.getYearPlanValueDl());
                            yearList.add(planStatisticsPeriodValueVO);
                        }
                    }
                }


                // 获取年实际产值数据
                for (PlanStatisticsPeriodValueVO planStatisticsPeriodValueVO: yearList) {

                    Map<String, Date> dateRange4Year = StatisticsUtils.getDateRange4Year(planStatisticsPeriodValueVO.getPeriod());
                    Date start = dateRange4Year.get("start");
                    Date end = dateRange4Year.get("end");

                    if(!CollectionUtils.isEmpty(listByDateRangeByEnd)) {
                        Date finalStart = start;
                        Date finalEnd = end;
                        List<JdglDaySchedule> collect = listByDateRangeByEnd.stream().filter(vo ->
                                finalStart.before(vo.getDate()) && finalEnd.after(vo.getDate())
                        ).collect(Collectors.toList());
                        if(!CollectionUtils.isEmpty(collect)) {
                            for (JdglDaySchedule jdglDaySchedule : collect) {
                                if(planStatisticsPeriodValueVO.getCompValue() == null)planStatisticsPeriodValueVO.setCompValue(BigDecimal.ZERO);
                                if(jdglDaySchedule.getDayValueDl() != null) {
                                    planStatisticsPeriodValueVO.setCompValue(planStatisticsPeriodValueVO.getCompValue().add(jdglDaySchedule.getDayValueDl()));
                                }
                            }
                        }
                    }
                    compValueListY.add(planStatisticsPeriodValueVO.getCompValue() == null ? BigDecimal.ZERO : StatisticsUtils.getDivideTenThousand(planStatisticsPeriodValueVO.getCompValue()));
                }

                planStatisticsPeriodValueVOYear.setPeriodList(periodListY);
                planStatisticsPeriodValueVOYear.setPlanValueList(planValueListY);
                planStatisticsPeriodValueVOYear.setCompValueList(compValueListY);
                returnMapList.put("year", planStatisticsPeriodValueVOYear);
                break;
        }

        return returnMapList;

    }

    @Override
    public List<JdglDaySchedule> getDayScheduleCalendarList(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {

        Map<String, PlanStatisticsPeriodValueVO> returnMapList = new HashMap<>();

        StatisticsUtils.initDateParams(iPlanStatisticsQueryVO);

        String queryDateType = iPlanStatisticsQueryVO.getQueryDateType();
        String year = iPlanStatisticsQueryVO.getYear();
        String quarter = iPlanStatisticsQueryVO.getQuarter();
        String month = iPlanStatisticsQueryVO.getMonth();
        String week = iPlanStatisticsQueryVO.getWeek();
        Date startDate = iPlanStatisticsQueryVO.getStartDate();
        Date endDate = iPlanStatisticsQueryVO.getEndDate();

        if(year == null || month == null) {
            throw new RuntimeException("参数异常");
        }

        String period = year + "-" + month + "-01";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            startDate = sdf.parse(period);
            Calendar cl = Calendar.getInstance();
            cl.setTime(startDate);
            cl.set(Calendar.DAY_OF_MONTH, cl.getActualMaximum(Calendar.DAY_OF_MONTH));
            endDate = cl.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<JdglDaySchedule> listByDateRange = iJdglDayScheduleService.getAllListByDateRange(startDate, endDate);

        List<Date> dateList = new ArrayList<>();
        StatisticsUtils.getDayList(dateList, startDate, endDate);

        for (Date date : dateList) {
            JdglDaySchedule jdglDaySchedule = listByDateRange.stream().filter(vo -> date.compareTo(vo.getDate()) == 0).findFirst().orElse(null);
            if(jdglDaySchedule == null) {
                jdglDaySchedule = new JdglDaySchedule();
                jdglDaySchedule.setDate(date);
                jdglDaySchedule.setPtVar5("0");
                listByDateRange.add(jdglDaySchedule);
            } else {
                jdglDaySchedule.setDayValueDl(jdglDaySchedule.getDayValueDl() == null ? BigDecimal.ZERO : jdglDaySchedule.getDayValueDl().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
                jdglDaySchedule.setPtVar5("1");
            }
        }

        return listByDateRange;
    }


}
