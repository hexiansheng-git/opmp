package com.hhwy.pm.jdgl.diff.track.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMakeDetail;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeDetailService;
import com.hhwy.pm.jdgl.diff.make.service.IJdglCorrectionMeasuresMakeService;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrack;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrackDetail;
import com.hhwy.pm.jdgl.diff.track.domain.vo.ProgressCorrectionTrackQueryVo;
import com.hhwy.pm.jdgl.diff.track.mapper.JdglProgressCorrectionTrackMapper;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackDetailService;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.statistics.domain.PlanStatisticsQueryVO;
import com.hhwy.pm.jdgl.statistics.service.IPlanStatisticsService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.service.IJdglWeekPlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.service.IJdglYearPlanService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhenglili
 * @date 2023-08-24 16:51:08
 * @remark 进度纠偏跟踪
 */
@Service
@Slf4j
public class JdglProgressCorrectionTrackServiceImpl implements IJdglProgressCorrectionTrackService {

    @Autowired
    private JdglProgressCorrectionTrackMapper jdglProgressCorrectionTrackMapper;
    @Autowired
    private IJdglProgressCorrectionTrackDetailService jdglProgressCorrectionTrackDetailService;
    @Autowired
    private IJdglCorrectionMeasuresMakeService jdglCorrectionMeasuresMakeService;
    @Autowired
    private IJdglCorrectionMeasuresMakeDetailService jdglCorrectionMeasuresMakeDetailService;
    @Autowired
    private IJdglWeekPlanService jdglWeekPlanService;
    @Autowired
    private IJdglYearPlanService jdglYearPlanService;
    @Autowired
    private IJdglDayScheduleService jdglDayScheduleService;
    @Autowired
    private IJdglMonthPlanService jdglMonthPlanService;
    @Autowired
    private IJdglDiffAnalysisService jdglDiffAnalysisService;
    @Autowired
    private IXmslContractInfoService xmslContractInfoService;
    @Autowired
    private IJdglMainPlanItemService jdglMainPlanItemService;
    @Autowired
    private IPlanStatisticsService planStatisticsService;
    @Autowired
    private ISysSyncInfoService sysSyncInfoService;
    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;
    @Autowired
    private IJdglDayScheduleWbsService jdglDayScheduleWbsService;
    @Autowired
    private SystemServiceApi systemServiceApi;

    /**
     * 查询单条数据-详情
     *
     * @param jdglProgressCorrectionTrack
     * @return
     */
    public JdglProgressCorrectionTrack getJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        JdglProgressCorrectionTrack track = jdglProgressCorrectionTrackMapper.getJdglProgressCorrectionTrack(jdglProgressCorrectionTrack);
        if (track != null) {
            List<JdglProgressCorrectionTrackDetail> detailList = jdglProgressCorrectionTrackDetailService.getDetailListByTackId(track.getId());
            track.setDetailList(TreeUtil.build(detailList, null));
        }
        return track;
    }

    /**
     * 列表查询
     *
     * @param jdglProgressCorrectionTrack
     * @return
     */
    public List<JdglProgressCorrectionTrack> getJdglProgressCorrectionTrackList(JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        return jdglProgressCorrectionTrackMapper.getJdglProgressCorrectionTrackList(jdglProgressCorrectionTrack);
    }

    /**
     * 新增保存
     *
     * @param jdglProgressCorrectionTrack
     */
    @Transactional
    public void insertJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        jdglProgressCorrectionTrack.setId(IdWorker.createId());
        jdglProgressCorrectionTrack.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        jdglProgressCorrectionTrack.setCreateUserName(SecurityUtils.getUserName());
        jdglProgressCorrectionTrack.setCreateTime(DateUtils.getNowDate());
        jdglProgressCorrectionTrackMapper.insertJdglProgressCorrectionTrack(jdglProgressCorrectionTrack);

        List<JdglProgressCorrectionTrackDetail> detailList = jdglProgressCorrectionTrack.getDetailList();
        if (!CollectionUtils.isEmpty(detailList)) {
            for (JdglProgressCorrectionTrackDetail detail : detailList) {
                detail.setId(IdWorker.createId());
                detail.setTrackId(jdglProgressCorrectionTrack.getId());
                detail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                detail.setCreateUserName(SecurityUtils.getUserName());
                detail.setCreateTime(DateUtils.getNowDate());
            }
            jdglProgressCorrectionTrackDetailService.insertJdglProgressCorrectionTrackDetailList(detailList);
        }
    }

    @Transactional
    public int insertJdglProgressCorrectionTrackList(
        List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList) {
        for (JdglProgressCorrectionTrack jdglProgressCorrectionTrack : jdglProgressCorrectionTrackList) {
            jdglProgressCorrectionTrack.setId(IdWorker.createId());
            jdglProgressCorrectionTrack.setCreateUser(SecurityUtils.getUserName());
            jdglProgressCorrectionTrack.setCreateTime(DateUtils.getNowDate());
        }
        return jdglProgressCorrectionTrackMapper.insertJdglProgressCorrectionTrackList(jdglProgressCorrectionTrackList);
    }

    /**
     * 更新保存
     *
     * @param jdglProgressCorrectionTrack
     * @return
     */
    @Transactional
    public void updateJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        if (jdglProgressCorrectionTrack == null || jdglProgressCorrectionTrack.getId() == null) {
            return;
        }

        List<JdglProgressCorrectionTrackDetail> detailList = jdglProgressCorrectionTrack.getDetailList();
        if (!CollectionUtils.isEmpty(detailList)) {
            // 树转列表
            List<JdglProgressCorrectionTrackDetail> treeList = TreeUtil.treeToListWithoutId(detailList);
            for (JdglProgressCorrectionTrackDetail detail : treeList) {
                detail.setTrackId(jdglProgressCorrectionTrack.getId());
                detail.setUpdateUser(SecurityUtils.getUserName());
                detail.setUpdateTime(DateUtils.getNowDate());
            }
            // 执行更改下操作
            jdglProgressCorrectionTrackDetailService.updateJdglProgressCorrectionTrackDetailList(treeList);
        }
    }

    @Transactional
    public int updateJdglProgressCorrectionTrackList(
        List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList) {
        for (JdglProgressCorrectionTrack jdglProgressCorrectionTrack : jdglProgressCorrectionTrackList) {
            jdglProgressCorrectionTrack.setUpdateUser(SecurityUtils.getUserName());
            jdglProgressCorrectionTrack.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglProgressCorrectionTrackMapper.updateJdglProgressCorrectionTrackList(jdglProgressCorrectionTrackList);
    }

    @Transactional
    public int deleteJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        jdglProgressCorrectionTrack.setUpdateUser(SecurityUtils.getUserName());
        jdglProgressCorrectionTrack.setUpdateTime(DateUtils.getNowDate());
        return jdglProgressCorrectionTrackMapper.deleteJdglProgressCorrectionTrack(jdglProgressCorrectionTrack);
    }

    /**
     * 批量删除
     *
     * @param jdglProgressCorrectionTrackPkList
     * @return
     */
    @Transactional
    public int deleteJdglProgressCorrectionTrackByPks(List<Long> jdglProgressCorrectionTrackPkList) {
        return jdglProgressCorrectionTrackMapper
            .deleteJdglProgressCorrectionTrackByPks(jdglProgressCorrectionTrackPkList);
    }

    @Override
    public List<JdglProgressCorrectionTrack> gmList(ProgressCorrectionTrackQueryVo queryVo) {
        return jdglProgressCorrectionTrackMapper.gmList(queryVo);
    }

    //手动生成数据
    public void weekTimerTrackByDate(String date) {
        Assert.isTrue(StringUtils.isNotBlank(date), "时间不能为空");
        DateTime dateTime = DateUtil.parseDate(date);
        String tenantKey = SecurityUtils.getTenantKey();
        List<SysTenant> tenantList = new ArrayList<>();
        SysTenant sysTenant = new SysTenant();
        sysTenant.setTenantKey(tenantKey);
        tenantList.add(sysTenant);
        this.initData(tenantList, dateTime);
    }

    /**
     * 每周定时生成追踪数据, 所有租户
     *
     */
//    @Transactional
    public void weekTimerTrack(Date dateTime) {
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        try {
            //切换到master
            DynamicDataSourceContextHolder.push("master");
            //获取所有租户
            List<SysTenant> tenantList = systemServiceApi.tenantList();
            this.initData(tenantList, dateTime);
        }catch (Exception e){
            log.info("每周定时生成追踪数据，---------------19");
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }finally {
            log.info("每周定时生成追踪数据，---------------20");
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
            log.info("每周定时生成追踪数据，---------------21");
        }
        log.info("每周定时生成追踪数据，---------------22");
    }

    /*
    * 功能描述: 生成数据
    * @param: tenantList 租户列表
     * @param: dateTime  需要生成的数据时间
    * @return: void
    * 作者:
    * 时间: 2024/4/11
    */
    private void initData(List<SysTenant> tenantList, Date dateTime) {
        //时间参数处理
        if (null == dateTime) {
            dateTime = new Date();
        }
        //默认生成上一周的数据
        DateTime date = DateUtil.offsetWeek(dateTime, -1);
        Date beginOfMonth = DateUtil.beginOfMonth(date);
        Date endOfMonth = DateUtil.endOfMonth(date);
        Date beginOfWeek = DateUtil.beginOfWeek(date);
        Date endOfWeek = DateUtil.endOfWeek(date);
        //处理年初和年尾日期
        int thisYear = DateUtil.year(beginOfWeek);
        int thisMonth = DateUtil.month(beginOfWeek) + 1;
        for (SysTenant tenant : tenantList) {
            try {
                //切换租户
                String tenantKey = tenant.getTenantKey();
                log.info("每周定时生成追踪数据，租户：{}", tenantKey);
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);

                // 查询最新纠偏制定数据
                List<JdglCorrectionMeasuresMake> makeList = jdglCorrectionMeasuresMakeService.getJdglCorrectionMeasuresMakeList(new JdglCorrectionMeasuresMake());
                if (CollectionUtils.isEmpty(makeList)) {
                    log.info("每周定时生成追踪数据，跳过租户：{}, 原因：{}", tenantKey, "纠偏制定数据无数据");
                    continue;
                }
                JdglCorrectionMeasuresMake make = makeList.get(0);
                // 查询纠偏制定，方案详情
                List<JdglCorrectionMeasuresMakeDetail> detailList = this.getDetailList(make.getId());
                if (CollectionUtils.isEmpty(detailList)) {
                    log.info("每周定时生成追踪数据，跳过租户：{}, 原因：{}", tenantKey, "纠偏制定，方案详情无数据");
                    continue;
                }
                // 获取总体计划, 获取当月数据 todo 涉及版本
                List<JdglMainPlanItem> mainPlanItemList = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDateRange(DateUtil.beginOfMonth(date), DateUtil.endOfMonth(date));
                //
                JdglProgressCorrectionTrack jdglProgressCorrectionTrack = new JdglProgressCorrectionTrack();
                Long trackId = IdWorker.createId();
                jdglProgressCorrectionTrack.setId(trackId);
//                jdglProgressCorrectionTrack.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
//                jdglProgressCorrectionTrack.setCreateUserName(SecurityUtils.getUserName());
                jdglProgressCorrectionTrack.setCreateTime(DateUtils.getNowDate());
                jdglProgressCorrectionTrack.setPeriod(make.getWarnPeriod());
                jdglProgressCorrectionTrack.setRiskLevel(make.getRiskLevel());
                jdglProgressCorrectionTrack.setPeriodTotalScore(make.getPeriodTotalScore());
                jdglProgressCorrectionTrack.setCorrectionDate(make.getCorrectionDate());
                jdglProgressCorrectionTrack.setMonthTotalScore(make.getPeriodTotalScore());
                if (CollectionUtils.isNotEmpty(detailList)) {
                    log.info("每周定时生成追踪数据，---------------1");
                    // 获取纠偏完成日期最大值
                    List<Date> dateList = detailList.stream()
                            .filter(p -> p.getCorrectionCompleteDate() != null)
                            .map(JdglCorrectionMeasuresMakeDetail::getCorrectionCompleteDate).distinct()
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(dateList)) {
                        log.info("每周定时生成追踪数据，---------------2");
                        Date correctionCompleteDate = dateList.stream().max(Date::compareTo).get();
                        // 预计纠偏完成日期 预计纠偏方案最晚日期
                        jdglProgressCorrectionTrack.setPlanCorrectionCompleteDate(correctionCompleteDate);
                    }
                }
                // 周报期数
                jdglProgressCorrectionTrack.setWeekReportPeriod(thisYear + "年第" + DateUtil.weekOfYear(date) + "周");
                // 周报生成日期
                jdglProgressCorrectionTrack.setWeekReportBuildDate(FtDateUtils.getYearMonthDayDate());
                // 周报时间
                String weekReportDate = DateUtil.format(beginOfWeek, DatePattern.CHINESE_DATE_PATTERN) + "-" + DateUtil.format(endOfWeek, DatePattern.CHINESE_DATE_PATTERN);
                jdglProgressCorrectionTrack.setWeekReportTime(weekReportDate);
                log.info("每周定时生成追踪数据，---------------3");
                // 获取每周计划数据
                JdglWeekPlan jdglWeekPlan = jdglWeekPlanService.getUsingWeekPlanByYearAndWeek(String.valueOf(thisYear), String.valueOf(DateUtil.weekOfYear(date)));
                BigDecimal thisPlanValueDl = BigDecimal.ZERO;
                if (jdglWeekPlan != null && jdglWeekPlan.getThisPlanValueDl().compareTo(BigDecimal.ZERO) != 0) {
                    thisPlanValueDl = jdglWeekPlan.getThisPlanValueDl();
                }
                log.info("每周定时生成追踪数据，---------------4");
                // 本周计划产值
                jdglProgressCorrectionTrack.setWeekValuePlan(thisPlanValueDl);
                // 本周产值完成
                BigDecimal weekValueComplete = jdglDayScheduleService.getCountValueNotApprove(beginOfWeek, endOfWeek);
                jdglProgressCorrectionTrack.setWeekValueComplete(weekValueComplete==null?BigDecimal.ZERO:weekValueComplete);
                // 周完成比例 本周产值完成/本周产值计划*100%
                BigDecimal weekCompleteRatio = BigDecimalUtils.divide0(thisPlanValueDl, weekValueComplete, 4).multiply(new BigDecimal(100));
                jdglProgressCorrectionTrack.setWeekCompleteRatio(weekCompleteRatio);
                // 获取月度计划数据
                JdglMonthPlan jdglMonthPlan = jdglMonthPlanService.getUsingMonthPlanByYearAndMonth(String.valueOf(thisYear), String.valueOf(thisMonth));
                if (jdglMonthPlan != null) {
                    // 当月产值计划
                    jdglProgressCorrectionTrack.setMonthValuePlan(jdglMonthPlan.getThisPlanValueDl() == null ? BigDecimal.ZERO : jdglMonthPlan.getThisPlanValueDl());
                }
                // 当月产值完成
                BigDecimal monthValueComplete = jdglDayScheduleService.getCountValueNotApprove(beginOfMonth, endOfMonth);
                jdglProgressCorrectionTrack.setMonthValueComplete(monthValueComplete);
                // 开累产值完成
                BigDecimal sumValueComplete = jdglDayScheduleService.getCountValueNotApprove(null, endOfWeek);
                jdglProgressCorrectionTrack.setSumValueComplete(sumValueComplete);
                // 纠偏期次
                Date warnPeriod = FtDateUtils.parseDateYm(jdglProgressCorrectionTrack.getPeriod());
                // 纠偏期次开累产值完成
                BigDecimal sumValueCompleteWarnPeriod = jdglDayScheduleService.getCountValueNotApprove(null, endOfMonth);
                // 开累计量
                BigDecimal totalMeterValue = sumMeter(FtDateUtils.getYearMonthDate(endOfWeek));
                jdglProgressCorrectionTrack.setSumMeterage(totalMeterValue);
                //默认为0  工期完成百分比
                jdglProgressCorrectionTrack.setDurationCompletePercentage(BigDecimal.ZERO);
                //默认为0  本月差异值
                jdglProgressCorrectionTrack.setMonthDiffValue(BigDecimal.ZERO);
                // 合同信息
                log.info("每周定时生成追踪数据，---------------5");
                XmslContractInfo contractInfo = xmslContractInfoService.getValidMaxVersionContractInfo();
                if (contractInfo != null && StringUtils.isNotBlank(contractInfo.getDuration())) {
                    log.info("每周定时生成追踪数据，---------------6");
                    // 工期
                    BigDecimal duration = new BigDecimal(contractInfo.getDuration());
                    // 有效合同额
                    BigDecimal effectiveAmt = contractInfo.getEffectiveAmout();
                    // 纠偏期次开累产值完成/总产值(合同信息的有效合同额)(%)
                    BigDecimal sumValuePercentagePeriod = BigDecimalUtils.divide0(sumValueCompleteWarnPeriod, effectiveAmt, 4).multiply(new BigDecimal(100));
                    // 获取总体计划,获取实际开工日期最早的数据
                    JdglMainPlanItem maxActualStartDateMainPlanItem = jdglMainPlanItemService.getMaxActualStartDate();
                    // 实际开工日
                    Date actualStartDate = null;
                    if (maxActualStartDateMainPlanItem != null) {
                        actualStartDate = maxActualStartDateMainPlanItem.getActualStartDate();
                    }
                    BigDecimal durationCompletePercentagePeriod = BigDecimal.ZERO;
                    if (actualStartDate != null) {
                        log.info("每周定时生成追踪数据，---------------7");
                        // 周报结束日到实际开始日相差天数
                        BigDecimal days = new BigDecimal(FtDateUtils.getDays(actualStartDate, endOfWeek));
                        // 工期完成百分比 周报结束日期-实际开始日期/工期
                        BigDecimal durationCompletePercentage = BigDecimalUtils.divide0(days, duration, 4).multiply(new BigDecimal(100));
                        jdglProgressCorrectionTrack.setDurationCompletePercentage(durationCompletePercentage);

                        // 纠偏期次当月最后一天到实际开始日相差天数
                        BigDecimal daysPeriod = new BigDecimal(FtDateUtils.getDays(actualStartDate, endOfMonth));
                        // 截止纠偏期次工期完成百分比 纠偏期次当月最后一天-实际开始日期/工期
                        durationCompletePercentagePeriod = BigDecimalUtils.divide0(daysPeriod, duration, 4).multiply(new BigDecimal(100));
                    }
                    log.info("每周定时生成追踪数据，---------------8");
                    // 本月差异值 = 开累产值完成/总产值(合同信息的有效合同额)(%)-工期完成百分比(%)
                    BigDecimal diffValue = sumValuePercentagePeriod.subtract(durationCompletePercentagePeriod);
                    jdglProgressCorrectionTrack.setMonthDiffValue(diffValue);
                }
                // 项目计划当月总时差
                BigDecimal planMathTotalTimeDiff = BigDecimal.valueOf(sumTotalFloat(mainPlanItemList));
                jdglProgressCorrectionTrack.setPlanMathTotalTimeDiff(planMathTotalTimeDiff);
                // 进度统计,按季度查询
                PlanStatisticsQueryVO planStatisticsQueryVO = new PlanStatisticsQueryVO();
                planStatisticsQueryVO.setQueryDateType("j");
//                planStatisticsQueryVO.setEndDate(endOfWeek);
                planStatisticsQueryVO.setYear(String.valueOf(thisYear));
                planStatisticsQueryVO.setQuarter(DateUtil.quarter(endOfWeek)+"");
                Map<String, Map<String, BigDecimal>> statisticsMap = planStatisticsService.getValueCompData(planStatisticsQueryVO);
                log.info("每周定时生成追踪数据，---------------9");
                Map<String, BigDecimal> quarterMap = statisticsMap.get("quarter");
                // 计划产值
                BigDecimal planAmt = quarterMap.get("planAmt");
                // 实际产值
                BigDecimal actAmt = quarterMap.get("actAmt");
                // 季度产值计划完成百分比
                BigDecimal quarterValuePlanCompletePercentage = BigDecimalUtils.divide0(actAmt, planAmt, 4).multiply(new BigDecimal(100));
                jdglProgressCorrectionTrack.setQuarterValuePlanCompletePercentage(quarterValuePlanCompletePercentage);
                // 关键线路形象完成百分比: 关键线路完成产值/计划线路完成产值
                //关键线路
                List<JdglMainPlanItem> usingKeyRoad = jdglMainPlanItemService.getUsingKeyRoad();
                //wbs产值
                List<JdglDayScheduleWbs4Value> wbsListByDateRange = jdglDayScheduleWbsService.getTotalWbsListByDateRange(endOfWeek);
                log.info("每周定时生成追踪数据，---------------10");
                List<String> itemCode = usingKeyRoad.stream().map(JdglMainPlanItem::getItemCode).collect(Collectors.toList());
                List<String> wbsCode = wbsListByDateRange.stream().map(JdglDayScheduleWbs4Value::getWbsCode).collect(Collectors.toList());
                Set<String> interCode = wbsCode.stream().filter(itemCode::contains).collect(Collectors.toSet());
                List<JdglDayScheduleWbs4Value> interList = wbsListByDateRange.stream().filter(p -> interCode.contains(p.getWbsCode())).collect(Collectors.toList());
                //得到关键线路产值
                BigDecimal keyRoadValue = interList.stream().filter(p -> p.getThisValue()!=null).map(JdglDayScheduleWbs4Value::getThisValue).reduce(BigDecimal.ZERO, BigDecimal::add);
                //得到关键线路计划产值
                BigDecimal keyRoadValuePlan = BigDecimal.ZERO;
                JdglYearPlan jdglYearPlan = new JdglYearPlan();
                jdglYearPlan.setTaskStatus("5");
                jdglYearPlan.setIsUse("1");
                jdglYearPlan.setYear(String.valueOf(thisYear));
                JdglYearPlan jdglYearPlan1 = jdglYearPlanService.getJdglYearPlan(jdglYearPlan);
                if (ObjectUtil.isNotEmpty(jdglYearPlan1)) {
                    log.info("每周定时生成追踪数据，---------------11");
                    List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearPlan1.getJdglYearImagePlanList();
                    if (CollectionUtil.isNotEmpty(jdglYearImagePlanList)) {
                        List<String> wbsCodePlan = jdglYearImagePlanList.stream().map(JdglYearImagePlan::getWbsCode).collect(Collectors.toList());
                        Set<String> interCodePlan = wbsCodePlan.stream().filter(itemCode::contains).collect(Collectors.toSet());
                        List<JdglYearImagePlan> interListPlan = jdglYearImagePlanList.stream().filter(p -> interCodePlan.contains(p.getWbsCode())).collect(Collectors.toList());
                        keyRoadValuePlan = interListPlan.stream().map(JdglYearImagePlan::getPlanCompValue).reduce(BigDecimal.ZERO, BigDecimal::add);
                        log.info("每周定时生成追踪数据，---------------12");
                    }
                }
                BigDecimal imagePercent = BigDecimalUtils.divide0(keyRoadValue, keyRoadValuePlan, 4).multiply(new BigDecimal("100"));
                // 关键线路形象完成百分比
                jdglProgressCorrectionTrack.setKeyLineImageCompletePercentage(imagePercent);
                // 近三个月差异化值 存3个值，逗号隔开 不包含本月
                jdglProgressCorrectionTrack.setLastThreeMonthDiffValue("0");
                List<BigDecimal> valueList = jdglProgressCorrectionTrackMapper.getLastThreeMonthData(new JdglProgressCorrectionTrack());
                if (CollectionUtils.isNotEmpty(valueList)) {
                    log.info("每周定时生成追踪数据，---------------13");
                    String valueStr = valueList.stream().map(String::valueOf).collect(Collectors.joining(","));
                    jdglProgressCorrectionTrack.setLastThreeMonthDiffValue(valueStr);
                }
                // 周期信息入库
                JdglProgressCorrectionTrack param = new JdglProgressCorrectionTrack();
                param.setPeriod(jdglProgressCorrectionTrack.getPeriod());
                param.setWeekReportPeriod(jdglProgressCorrectionTrack.getWeekReportPeriod());
                List<JdglProgressCorrectionTrack> resultList = jdglProgressCorrectionTrackMapper.getJdglProgressCorrectionTrackList(param);
                if (CollectionUtil.isNotEmpty(resultList)) {
                    log.info("每周定时生成追踪数据，---------------14");
                    jdglProgressCorrectionTrackMapper.deleteJdglProgressCorrectionTrack(param);
                    JdglProgressCorrectionTrackDetail param1 = new JdglProgressCorrectionTrackDetail();
                    param1.setTrackId(trackId);
                    jdglProgressCorrectionTrackDetailService.deleteJdglProgressCorrectionTrackDetail(param1);
                }
                XmslProjectBasicInfo projectBasicInfo = projectBasicInfoService.getProjectBasicInfo(new XmslProjectBasicInfo());
                jdglProgressCorrectionTrack.setProjectId(projectBasicInfo.getProjectId());
                jdglProgressCorrectionTrack.setProjectName(projectBasicInfo.getProjectName());
                jdglProgressCorrectionTrackMapper.insertJdglProgressCorrectionTrack(jdglProgressCorrectionTrack);
                log.info("每周定时生成追踪数据，---------------15");
                //推送到总部版
                sysSyncInfoService.pushJdglProgressCorrectionTrack(jdglProgressCorrectionTrack);
                log.info("每周定时生成追踪数据，---------------16");
                List<JdglProgressCorrectionTrackDetail> trackDetailList = new ArrayList<>();
                for (JdglCorrectionMeasuresMakeDetail detail : detailList) {
                    JdglProgressCorrectionTrackDetail trackDetail = new JdglProgressCorrectionTrackDetail();
                    BeanUtils.copyProperties(detail, trackDetail);
                    trackDetail.setTrackId(trackId);
                    trackDetailList.add(trackDetail);
                }
                log.info("每周定时生成追踪数据，---------------17");
                // 纠偏方案详情入库
                jdglProgressCorrectionTrackDetailService.insertJdglProgressCorrectionTrackDetailList(trackDetailList);
                log.info("每周定时生成追踪数据，完成");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 合计开累计量 从差异化分析-计量填报获取
     *
     * @param currentDate
     * @return
     */
    public BigDecimal sumMeter(Date currentDate) {
        List<JdglDiffAnalysis> list = jdglDiffAnalysisService.getJdglDiffAnalysisList(new JdglDiffAnalysis());
        BigDecimal totalMeterValue = BigDecimal.ZERO;
        if (CollectionUtil.isEmpty(list))
            return totalMeterValue;
        List<JdglDiffAnalysis> collect = list.stream().filter(p -> p.getMeterValue() != null).collect(Collectors.toList());
        for (JdglDiffAnalysis jdglDiffAnalysis : collect) {
            // 当前日期在指定日期之后或与指定日期相等
            if (!currentDate.before(jdglDiffAnalysis.getPeriod())) {
                totalMeterValue = totalMeterValue.add(jdglDiffAnalysis.getMeterValue());
            }
        }
        return totalMeterValue;
    }

    /**
     * p6 合计总差时
     *
     * @param list
     * @return
     */
    public Integer sumTotalFloat(List<JdglMainPlanItem> list) {
        Integer totalFloat = 0;
        if (!CollectionUtils.isEmpty(list)) {
            for (JdglMainPlanItem jdglMainPlanItem : list) {
                totalFloat = totalFloat + jdglMainPlanItem.getTotalFloat();
            }
        }
        return totalFloat;
    }

    /**
     * 获取纠偏方案详情
     *
     * @param id
     * @return
     */
    public List<JdglCorrectionMeasuresMakeDetail> getDetailList(Long id) {
        List<JdglCorrectionMeasuresMakeDetail> newList = new ArrayList<>();

        JdglCorrectionMeasuresMakeDetail qryDetail = new JdglCorrectionMeasuresMakeDetail();
        qryDetail.setMakeId(id);
        // 查询全部纠偏方案详情
        List<JdglCorrectionMeasuresMakeDetail> detailList = jdglCorrectionMeasuresMakeDetailService.getJdglCorrectionMeasuresMakeDetailList(qryDetail);
        if (CollectionUtils.isEmpty(detailList)) {
            return newList;
        }
        // 查询偏差值为负的方案详情
        List<JdglCorrectionMeasuresMakeDetail> diffDetailList = new ArrayList<>();
        for (JdglCorrectionMeasuresMakeDetail detail : detailList) {
            if (detail.getDeviationQuantity() != null && detail.getDeviationQuantity().compareTo(BigDecimal.ZERO) < 0) {
                diffDetailList.add(detail);
            }
        }
        if (CollectionUtils.isEmpty(diffDetailList)) {
            return newList;
        }

        // 根据子集递归查询父级数据 ，封装成树结构返回
        newList = ListTreeUtil.getUpListBySublistToTree(
            diffDetailList,
            detailList,
            JdglCorrectionMeasuresMakeDetail::getId,
            JdglCorrectionMeasuresMakeDetail::getPid,
            o -> o.getPid() == null,
            (r, n) -> r.getId().equals(n.getPid()),
            JdglCorrectionMeasuresMakeDetail::getChildren,
            JdglCorrectionMeasuresMakeDetail::setChildren);

        // 再树转list返回，为了生成新的id和pid
        return TreeUtil.treeToList(newList);
    }
}
