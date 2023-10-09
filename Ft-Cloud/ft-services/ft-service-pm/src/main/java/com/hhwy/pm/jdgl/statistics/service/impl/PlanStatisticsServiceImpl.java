package com.hhwy.pm.jdgl.statistics.service.impl;

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
                totalActAmt = totalActAmt.add(dayValueDl);
                int yearI = cl.get(Calendar.YEAR);
                if(!year.equals(yearI + "")) {
                    continue;
                }
                yearActAmt = yearActAmt.add(dayValueDl);
                int dateMonth = cl.get(Calendar.MONTH) + 1;

                if(quarter == null) {
                    continue;
                }
                int i = Integer.parseInt(quarter);

                if(i*3 < dateMonth || i*3-2 > dateMonth) {
                    continue;
                }
                quarterActAmt = quarterActAmt.add(dayValueDl);

                if(month == null || Integer.parseInt(month) != dateMonth){
                    continue;
                }
                monthActAmt = monthActAmt.add(dayValueDl);

                if((startDate.before(date) || startDate.equals(date)) && (endDate.after(date)||endDate.equals(date))) {
                    weekActAmt = weekActAmt.add(dayValueDl);
                }
            }
        }

        BigDecimal contractAmt = new BigDecimal(0);

        //查询开累计划产值(有效合同额)
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getValidMaxVersionContractInfo();
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
        totalMap.put("planAmt", contractAmt == null ? new BigDecimal(0) : contractAmt);
        totalMap.put("actAmt", totalActAmt);
        return2Map.put("total", totalMap);

        Map<String, BigDecimal> yearMap = new HashMap<>();
        yearMap.put("planAmt", yearPlanAmt);
        yearMap.put("actAmt", yearActAmt);
        return2Map.put("year", yearMap);

        Map<String, BigDecimal> quarterMap = new HashMap<>();
        quarterMap.put("planAmt", quarterPlanAmt);
        quarterMap.put("actAmt", quarterActAmt);
        return2Map.put("quarter", quarterMap);

        Map<String, BigDecimal> monthMap = new HashMap<>();
        monthMap.put("planAmt", monthPlanAmt);
        monthMap.put("actAmt", monthActAmt);
        return2Map.put("month", monthMap);

        Map<String, BigDecimal> weekMap = new HashMap<>();
        weekMap.put("planAmt", weekPlanAmt);
        weekMap.put("actAmt", weekActAmt);
        return2Map.put("week", weekMap);

        return return2Map;
    }

    @Override
    public List<PlanStatisticsValueCompVO> getValueCompData4VO(PlanStatisticsQueryVO iPlanStatisticsQueryVO) {

        List<PlanStatisticsValueCompVO> returnList = new ArrayList<>();

        Map<String, Map<String, BigDecimal>> valueCompData = getValueCompData(iPlanStatisticsQueryVO);

        Set<String> keys = valueCompData.keySet();

        for (String key : keys) {
            PlanStatisticsValueCompVO vo1 = new PlanStatisticsValueCompVO();
            PlanStatisticsValueCompVO vo2 = new PlanStatisticsValueCompVO();
            PlanStatisticsValueCompVO vo3 = new PlanStatisticsValueCompVO();
            switch (key) {
                case "week":
                    vo1.setType("");
                    break;
                case "month":
                    break;
                case "quarter":
                    break;
                case "year":
                    break;
                case "total":
                    break;
            }
        }


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
            List<JdglWeekImagePlan> jdglWeekImagePlans = "z".equals(queryDateType) ? jdglWeekImagePlanService.getWbsListByYearAndWeek(year, week) : null;
            List<JdglMonthImagePlan> jdglMonthImagePlans = "y".equals(queryDateType) ? jdglMonthImagePlanService.getWbsListByYearAndMonth(year, month) : null;
            List<JdglQuarterImagePlan> jdglQuarterImagePlans = "j".equals(queryDateType) ? jdglQuarterImagePlanService.getWbsListByYearAndQuarter(year, quarter) : null;
            List<JdglYearImagePlan> jdglYearImagePlans = "n".equals(queryDateType) ? jdglYearImagePlanService.getWbsListByYear(year) : null;
            for (PlanStatisticsWbsValueVO planStatisticsWbsValueVO : returnList) {
                String wbsCode = planStatisticsWbsValueVO.getWbsCode();
                Stream<JdglDayScheduleWbs4Value> jdglDayScheduleWbs4ValueStream = totalWbsListByDateRange.stream().filter(vo -> wbsCode.equals(vo.getWbsCode()));
                if(jdglDayScheduleWbs4ValueStream != null) {
                    JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = jdglDayScheduleWbs4ValueStream.findFirst().get();
                    if(jdglDayScheduleWbs4Value != null) {
                        planStatisticsWbsValueVO.setTotalActValue(jdglDayScheduleWbs4Value.getThisValue());
                    }
                }

                // 本周/月/季/年计划产值数据
                switch (queryDateType) {
                    case "z":
                        if(!CollectionUtils.isEmpty(jdglWeekImagePlans)) {
                            JdglWeekImagePlan imagePlan = jdglWeekImagePlans.stream().filter(vo -> wbsCode.equals(vo.getWbsCode())).findFirst().orElse(null);
                            if(imagePlan != null) planStatisticsWbsValueVO.setThisPlanValue(imagePlan.getPlanCompValue());
                        }
                        break;
                    case "y":
                        if(!CollectionUtils.isEmpty(jdglMonthImagePlans)) {
                            JdglMonthImagePlan jdglMonthImagePlan = jdglMonthImagePlans.stream().filter(vo -> wbsCode.equals(vo.getWbsCode())).findFirst().orElse(null);
                            if(jdglMonthImagePlan != null) planStatisticsWbsValueVO.setThisPlanValue(jdglMonthImagePlan.getPlanCompValue());

                        }
                        break;
                    case "j":
                        if(!CollectionUtils.isEmpty(jdglQuarterImagePlans)) {
                            JdglQuarterImagePlan jdglQuarterImagePlan = jdglQuarterImagePlans.stream().filter(vo -> wbsCode.equals(vo.getWbsCode())).findFirst().orElse(null);
                            if(jdglQuarterImagePlan != null) planStatisticsWbsValueVO.setThisPlanValue(jdglQuarterImagePlan.getPlanCompValue());
                        }
                        break;
                    case "n":
                        if(!CollectionUtils.isEmpty(jdglYearImagePlans)) {
                            JdglYearImagePlan jdglYearImagePlan = jdglYearImagePlans.stream().filter(vo -> wbsCode.equals(vo.getWbsCode())).findFirst().orElse(null);
                            if(jdglYearImagePlan != null)  planStatisticsWbsValueVO.setThisPlanValue(jdglYearImagePlan.getPlanCompValue());
                        }
                        break;
                }
            }
        }

        List<PlanStatisticsWbsValueVO> build = TreeUtil.build(returnList, null);

        return CollectionUtils.isEmpty(build)?new ArrayList<>():build;
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
            Long billId = jdglDayScheduleBill.getBillId();
            for (XmslContractList xmslContractList : xmslContractListVos) {
                if(xmslContractList.getAncestors() != null && xmslContractList.getAncestors().contains(billId+"")) {
                    Long listId = xmslContractList.getId();
                    PlanStatisticsBillValueVO planStatisticsBillValueVO = new PlanStatisticsBillValueVO();
                    planStatisticsBillValueVO.setId(listId);
                    planStatisticsBillValueVO.setPid(xmslContractList.getPid());
                    planStatisticsBillValueVO.setBillId(listId);
                    planStatisticsBillValueVO.setBillCode(xmslContractList.getCode());
                    planStatisticsBillValueVO.setBillName(xmslContractList.getChineseName());
                    planStatisticsBillValueVO.setBillUnit(xmslContractList.getUnit());
                    BigDecimal price = xmslContractList.getChangeUnitPrice() == null ? xmslContractList.getWinUnitPrice() : xmslContractList.getChangeUnitPrice();
                    planStatisticsBillValueVO.setBillPrice(price);

                    if(xmslContractList.getChangeNum() != null) {
                        planStatisticsBillValueVO.setRemainDesignNum(xmslContractList.getChangeNum().subtract(planStatisticsBillValueVO.getLastTotalDesignNum()));
                    }
                    if(billId.equals(listId)) {
                        planStatisticsBillValueVO.setThisCompDesignNum(jdglDayScheduleBill.getThisQuantity());
                        planStatisticsBillValueVO.setThisCompValue(price.multiply(planStatisticsBillValueVO.getThisCompDesignNum()));
                        planStatisticsBillValueVO.setThisTotalDesignNum(planStatisticsBillValueVO.getThisCompDesignNum().add(planStatisticsBillValueVO.getLastTotalDesignNum()));
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
            Long billId = planStatisticsBillValueVO.getBillId();
            if(billId == null) {
                continue;
            }
            // 填充计划产值数据
            switch (queryDateType) {
                case "z":
                    if(!CollectionUtils.isEmpty(jdglWeekValuePlans)){
                        Stream<JdglWeekValuePlan> jdglWeekValuePlanStream = jdglWeekValuePlans.stream().filter(vo -> billId.equals(vo.getInventoryId()));
                        if(jdglWeekValuePlanStream == null) {
                           continue;
                        }
                        JdglWeekValuePlan jdglWeekValuePlan = jdglWeekValuePlanStream.findFirst().get();
                        planStatisticsBillValueVO.setThisPlanDesignNum(jdglWeekValuePlan.getWeekPlanCompDesignQuantity());;
                        planStatisticsBillValueVO.setThisPlanValue(jdglWeekValuePlan.getWeekPlanValueCu());
                        planStatisticsBillValueVO.setLastTotalDesignNum(jdglWeekValuePlan.getTotalCompDesignQuantity());
                        planStatisticsBillValueVO.setRemainDesignNum(jdglWeekValuePlan.getRemainDesignQuantity());
                    }
                    break;
                case "y":
                    if(!CollectionUtils.isEmpty(jdglMonthValuePlans)){
                        Stream<JdglMonthValuePlan> jdglMonthValuePlanStream = jdglMonthValuePlans.stream().filter(vo -> billId.equals(vo.getInventoryId()));
                        if(jdglMonthValuePlanStream == null) {
                            continue;
                        }
                        JdglMonthValuePlan jdglMonthValuePlan = jdglMonthValuePlanStream.findFirst().get();
                        planStatisticsBillValueVO.setThisPlanDesignNum(jdglMonthValuePlan.getMonthPlanCompDesignQuantity());;
                        planStatisticsBillValueVO.setThisPlanValue(jdglMonthValuePlan.getMonthPlanValueCu());
                        planStatisticsBillValueVO.setLastTotalDesignNum(jdglMonthValuePlan.getTotalCompDesignQuantity());
                        planStatisticsBillValueVO.setRemainDesignNum(jdglMonthValuePlan.getRemainDesignQuantity());
                    }
                    break;
                case "j":
                    if(!CollectionUtils.isEmpty(jdglQuarterValuePlans)){
                        Stream<JdglQuarterValuePlan> jdglQuarterValuePlanStream = jdglQuarterValuePlans.stream().filter(vo -> billId.equals(vo.getInventoryId()));
                        if(jdglQuarterValuePlanStream == null) {
                            continue;
                        }
                        JdglQuarterValuePlan jdglQuarterValuePlan = jdglQuarterValuePlanStream.findFirst().get();
                        planStatisticsBillValueVO.setThisPlanDesignNum(jdglQuarterValuePlan.getQuarterPlanCompDesignQuantity());;
                        planStatisticsBillValueVO.setThisPlanValue(jdglQuarterValuePlan.getQuarterPlanValueCu());
                        planStatisticsBillValueVO.setLastTotalDesignNum(jdglQuarterValuePlan.getTotalCompDesignQuantity());
                        planStatisticsBillValueVO.setRemainDesignNum(jdglQuarterValuePlan.getRemainDesignQuantity());
                    }
                    break;
                case "n":
                    if(!CollectionUtils.isEmpty(jdglYearValuePlans)){
                        Stream<JdglYearValuePlan> jdglYearValuePlanStream = jdglYearValuePlans.stream().filter(vo -> billId.equals(vo.getInventoryId()));
                        if(jdglYearValuePlanStream == null) {
                            continue;
                        }
                        JdglYearValuePlan jdglYearValuePlan = jdglYearValuePlanStream.findFirst().get();
                        planStatisticsBillValueVO.setThisPlanDesignNum(jdglYearValuePlan.getYearPlanCompDesignQuantity());;
                        planStatisticsBillValueVO.setThisPlanValue(jdglYearValuePlan.getYearPlanValueCu());
                        planStatisticsBillValueVO.setLastTotalDesignNum(jdglYearValuePlan.getTotalCompDesignQuantity());
                        planStatisticsBillValueVO.setRemainDesignNum(jdglYearValuePlan.getRemainDesignQuantity());
                    }
                    break;
            }
        }

        List<PlanStatisticsBillValueVO> build = TreeUtil.build(returnList, null);

        return build;

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
                                if(planStatisticsPeriodValueVO.getPlanValue() == null) planStatisticsPeriodValueVO.setPlanValue(new BigDecimal(0));
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
                                if (planStatisticsPeriodValueVO.getCompValue() == null) planStatisticsPeriodValueVO.setCompValue(new BigDecimal(0));
                                if(jdglDaySchedule.getDayValueDl() != null) {
                                    planStatisticsPeriodValueVO.setCompValue(planStatisticsPeriodValueVO.getCompValue().add(jdglDaySchedule.getDayValueDl()));
                                }

                            }
                        }
                    }

                    planValueListQ.add(planStatisticsPeriodValueVO.getPlanValue() == null ? new BigDecimal(0) : planStatisticsPeriodValueVO.getPlanValue());
                    compValueListQ.add(planStatisticsPeriodValueVO.getCompValue() == null ? new BigDecimal(0) : planStatisticsPeriodValueVO.getCompValue());
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
                // 获取年计划产值数据
                List<JdglYearPlan> jdglYearPlanList = jdglYearPlanService.getJdglYearPlanList(new JdglYearPlan());

                if(!CollectionUtils.isEmpty(jdglYearPlanList)) {
                    List<JdglYearPlan> collect1 = jdglYearPlanList.stream().sorted(Comparator.comparing(JdglYearPlan::getYear)).collect(Collectors.toList());
                    for (JdglYearPlan jdglYearPlan : collect1) {
                        if(year.compareTo(jdglYearPlan.getYear()) >= 0) {
                            PlanStatisticsPeriodValueVO planStatisticsPeriodValueVO = new PlanStatisticsPeriodValueVO();
                            planStatisticsPeriodValueVO.setPeriod(jdglYearPlan.getYear());
                            periodListY.add(jdglYearPlan.getYear());
                            planStatisticsPeriodValueVO.setPlanValue(jdglYearPlan.getYearPlanValueDl());
                            planValueListY.add(jdglYearPlan.getYearPlanValueDl() == null ? new BigDecimal(0) : jdglYearPlan.getYearPlanValueDl());
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
                                if(planStatisticsPeriodValueVO.getCompValue() == null)planStatisticsPeriodValueVO.setCompValue(new BigDecimal(0));
                                if(jdglDaySchedule.getDayValueDl() != null) {
                                    planStatisticsPeriodValueVO.setCompValue(planStatisticsPeriodValueVO.getCompValue().add(jdglDaySchedule.getDayValueDl()));
                                }
                            }
                        }
                    }
                    compValueListY.add(planStatisticsPeriodValueVO.getCompValue() == null ? new BigDecimal(0) : planStatisticsPeriodValueVO.getCompValue());
                }

                planStatisticsPeriodValueVOYear.setPeriodList(periodListY);
                planStatisticsPeriodValueVOYear.setPlanValueList(planValueListY);
                planStatisticsPeriodValueVOYear.setCompValueList(compValueListY);
                returnMapList.put("year", planStatisticsPeriodValueVOYear);
                break;
        }

        return returnMapList;

    }


}
