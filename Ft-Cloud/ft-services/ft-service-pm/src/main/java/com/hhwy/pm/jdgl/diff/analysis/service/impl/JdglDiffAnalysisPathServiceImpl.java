package com.hhwy.pm.jdgl.diff.analysis.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Value;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisPath;
import com.hhwy.pm.jdgl.diff.analysis.mapper.JdglDiffAnalysisPathMapper;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisPathService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.utils.tree.TreeUtil;
import io.swagger.models.auth.In;
import nonapi.io.github.classgraph.json.Id;
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

    @Autowired
    private IJdglMainPlanItemService jdglMainPlanItemService;

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

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
//        jdglDiffAnalysisPath.setId(IdWorker.createId());
//        jdglDiffAnalysisPath.setCreateUser(SecurityUtils.getUserName());
        jdglDiffAnalysisPath.setCreateTime(DateUtils.getNowDate());
        return jdglDiffAnalysisPathMapper.insertJdglDiffAnalysisPath(jdglDiffAnalysisPath);
    }

    @Transactional
    public int insertJdglDiffAnalysisPathList(List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList) {
        for (JdglDiffAnalysisPath jdglDiffAnalysisPath : jdglDiffAnalysisPathList) {
//            jdglDiffAnalysisPath.setId(IdWorker.createId());
//            jdglDiffAnalysisPath.setCreateUser(SecurityUtils.getUserName());
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

        BigDecimal returnBig = new BigDecimal(0);
        List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList = new ArrayList<>();

        // 获取总体计划关键线路数据
        List<JdglMainPlanItem> usingNoKeyRoad = jdglMainPlanItemService.getUsingKeyRoad();

        if(CollectionUtils.isEmpty(usingNoKeyRoad)) return returnBig;

        // 获取开累wbs填报
        List<JdglDayScheduleWbs4Value> totalWbsListByDateRange = iJdglDayScheduleWbsService.getTotalWbsListByDateRange(endDate);

        for (JdglMainPlanItem jdglMainPlanItem : usingNoKeyRoad) {
            JdglDiffAnalysisPath jdglDiffAnalysisPath = new JdglDiffAnalysisPath();
            jdglDiffAnalysisPath.setId(IdWorker.createId());
            jdglDiffAnalysisPath.setDiffAnalysisId(jdglDiffAnalysis.getId());
            jdglDiffAnalysisPath.setPtVar1(jdglMainPlanItem.getId()+"");
            jdglDiffAnalysisPath.setPtVar2(jdglMainPlanItem.getPid()+"");
            jdglDiffAnalysisPath.setPlanItemCode(jdglMainPlanItem.getItemCode());
            jdglDiffAnalysisPath.setPlanItemName(jdglMainPlanItem.getItemName());
            jdglDiffAnalysisPath.setUnit(jdglMainPlanItem.getUnit());
            jdglDiffAnalysisPath.setDesignNum(jdglMainPlanItem.getQuantity());
            jdglDiffAnalysisPath.setPlanStartDate(jdglMainPlanItem.getStartDate());
            jdglDiffAnalysisPath.setPlanEndDate(jdglMainPlanItem.getFinishDate());
            jdglDiffAnalysisPath.setPlanDays(jdglMainPlanItem.getPlannedDuration());
            jdglDiffAnalysisPath.setIsCriticalPath("1");
            jdglDiffAnalysisPath.setSort(jdglMainPlanItem.getSort());

            // 计算总体工期完成百分比
            int totalPlanDays = jdglMainPlanItem.getPlannedDuration();
            int totalCompDays = 0;
            Date actualStartDate = jdglMainPlanItem.getActualStartDate();
            Date actualFinishDate = jdglMainPlanItem.getActualFinishDate() == null ? DateUtils.getNowDate() : jdglMainPlanItem.getActualFinishDate();
            if(actualStartDate != null) {
                totalCompDays =  Math.round((actualFinishDate.getTime() - actualStartDate.getTime())/24/60/60/1000);
            }

            BigDecimal totalDayCompRate = new BigDecimal(totalPlanDays == 0 ? 0 : totalCompDays /totalPlanDays);
            jdglDiffAnalysisPath.setTotalDayCompRate(totalDayCompRate);

            // 计算总体进度完成百分比
            BigDecimal totalPlanQty = jdglMainPlanItem.getQuantity();
            BigDecimal totalActQty = new BigDecimal(0);
            if(!CollectionUtils.isEmpty(totalWbsListByDateRange)) {
                JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = totalWbsListByDateRange.stream().filter(vo -> vo.getWbsCode().equals(jdglMainPlanItem.getItemCode())).findFirst().orElse(null);
                if(jdglDayScheduleWbs4Value != null) totalActQty = jdglDayScheduleWbs4Value.getThisQuantity() == null ? totalActQty : jdglDayScheduleWbs4Value.getThisQuantity();
            }
            BigDecimal totalProgressCompRate = totalPlanQty == null || totalPlanQty.compareTo(new BigDecimal(0)) == 0
                    ? new BigDecimal(0) : totalActQty.divide(totalPlanQty,4,BigDecimal.ROUND_HALF_UP);
            jdglDiffAnalysisPath.setTotalProgressCompRate(totalProgressCompRate);

            // 计算偏差量
            BigDecimal thisDeviationNum = totalProgressCompRate.subtract(totalDayCompRate);
            jdglDiffAnalysisPath.setThisDeviationNum(thisDeviationNum);

            // 是否偏差
            jdglDiffAnalysisPath.setIsDeviation(thisDeviationNum.compareTo(new BigDecimal(0)) > 0 ? "1" : "0");

            jdglDiffAnalysisPathList.add(jdglDiffAnalysisPath);
        }

        if(!CollectionUtils.isEmpty(jdglDiffAnalysisPathList)) {
            for (JdglDiffAnalysisPath jdglDiffAnalysisPath : jdglDiffAnalysisPathList) {
                JdglDiffAnalysisPath jdglDiffAnalysisPath1 = jdglDiffAnalysisPathList.stream().filter(vo -> vo.getPtVar1().equals(jdglDiffAnalysisPath.getPtVar2())).findFirst().orElse(null);
                if(jdglDiffAnalysisPath1 != null) jdglDiffAnalysisPath.setPid(jdglDiffAnalysisPath1.getId());
            }
            insertJdglDiffAnalysisPathList(jdglDiffAnalysisPathList);
        }

        XmslContractInfo validMaxVersionContractInfo = xmslContractInfoService.getValidMaxVersionContractInfo();

        BigDecimal effectiveAmout = validMaxVersionContractInfo.getEffectiveAmout();
        BigDecimal totalCompAmt = new BigDecimal(0);

        if (!CollectionUtils.isEmpty(totalWbsListByDateRange)) {
            for (JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value : totalWbsListByDateRange) {
                BigDecimal thisValue = jdglDayScheduleWbs4Value.getThisValue() == null ? new BigDecimal(0) : jdglDayScheduleWbs4Value.getThisValue();
                totalCompAmt = totalCompAmt.add(thisValue);
            }
        }

        returnBig = effectiveAmout == null || effectiveAmout.compareTo(new BigDecimal(0)) == 0 ? new BigDecimal(0)
                : totalCompAmt.divide(effectiveAmout, 4, BigDecimal.ROUND_HALF_UP);

        return returnBig;
    }

    @Override
    public BigDecimal initNotKeyJdglDiffAnalysisPath(JdglDiffAnalysis jdglDiffAnalysis) {

        Date period = jdglDiffAnalysis.getPeriod();
        Date endDate = null;
        if(period != null) {
            Map<String, Date> dateRange4YearMonth = StatisticsUtils.getDateRange4YearMonth(period);
            endDate = dateRange4YearMonth.get("end");

        }

        BigDecimal returnBig = new BigDecimal(0);
        List<JdglDiffAnalysisPath> jdglDiffAnalysisPathList = new ArrayList<>();

        // 获取总体计划非关键线路数据
        List<JdglMainPlanItem> usingKeyRoad = jdglMainPlanItemService.getUsingNoKeyRoad();

        // 获取开累wbs填报
        List<JdglDayScheduleWbs4Value> totalWbsListByDateRange = iJdglDayScheduleWbsService.getTotalWbsListByDateRange(endDate);

        for (JdglMainPlanItem jdglMainPlanItem : usingKeyRoad) {
            JdglDiffAnalysisPath jdglDiffAnalysisPath = new JdglDiffAnalysisPath();
            jdglDiffAnalysisPath.setId(IdWorker.createId());
            jdglDiffAnalysisPath.setDiffAnalysisId(jdglDiffAnalysis.getId());
            jdglDiffAnalysisPath.setPtVar1(jdglMainPlanItem.getId()+"");
            jdglDiffAnalysisPath.setPtVar2(jdglMainPlanItem.getPid()+"");
            jdglDiffAnalysisPath.setPlanItemCode(jdglMainPlanItem.getItemCode());
            jdglDiffAnalysisPath.setPlanItemName(jdglMainPlanItem.getItemName());
            jdglDiffAnalysisPath.setUnit(jdglMainPlanItem.getUnit());
            jdglDiffAnalysisPath.setDesignNum(jdglMainPlanItem.getQuantity());
            jdglDiffAnalysisPath.setPlanStartDate(jdglMainPlanItem.getStartDate());
            jdglDiffAnalysisPath.setPlanEndDate(jdglMainPlanItem.getFinishDate());
            jdglDiffAnalysisPath.setPlanDays(jdglMainPlanItem.getPlannedDuration());
            jdglDiffAnalysisPath.setIsCriticalPath("0");
            jdglDiffAnalysisPath.setSort(jdglMainPlanItem.getSort());

            // 计算总体工期完成百分比
            int totalPlanDays = jdglMainPlanItem.getPlannedDuration();
            int totalCompDays = 0;
            Date actualStartDate = jdglMainPlanItem.getActualStartDate();
            Date actualFinishDate = jdglMainPlanItem.getActualFinishDate() == null ? DateUtils.getNowDate() : jdglMainPlanItem.getActualFinishDate();
            if(actualStartDate != null) {
                totalCompDays =  Math.round((actualFinishDate.getTime() - actualStartDate.getTime())/24/60/60/1000);
            }
            BigDecimal totalDayCompRate = new BigDecimal(totalPlanDays == 0 ? 0 : totalCompDays /totalPlanDays);
            jdglDiffAnalysisPath.setTotalDayCompRate(totalDayCompRate);

            // 计算总体进度完成百分比
            BigDecimal totalPlanQty = jdglMainPlanItem.getQuantity();
            BigDecimal totalActQty = new BigDecimal(0);
            if(!CollectionUtils.isEmpty(totalWbsListByDateRange)) {
                JdglDayScheduleWbs4Value jdglDayScheduleWbs4Value = totalWbsListByDateRange.stream().filter(vo -> vo.getWbsCode().equals(jdglMainPlanItem.getItemCode())).findFirst().orElse(null);
                if(jdglDayScheduleWbs4Value != null) totalActQty = jdglDayScheduleWbs4Value.getThisQuantity() == null ? totalActQty : jdglDayScheduleWbs4Value.getThisQuantity();
            }
            BigDecimal totalProgressCompRate = totalPlanQty == null || totalPlanQty.compareTo(new BigDecimal(0)) == 0
                    ? new BigDecimal(0) : totalActQty.divide(totalPlanQty,4,BigDecimal.ROUND_HALF_UP);
            jdglDiffAnalysisPath.setTotalProgressCompRate(totalProgressCompRate);

            // 计算偏差量
            BigDecimal thisDeviationNum = totalProgressCompRate.subtract(totalDayCompRate);
            jdglDiffAnalysisPath.setThisDeviationNum(thisDeviationNum);

            // 是否偏差
            jdglDiffAnalysisPath.setIsDeviation(thisDeviationNum.compareTo(new BigDecimal(0)) > 0 ? "1" : "0");

            jdglDiffAnalysisPathList.add(jdglDiffAnalysisPath);
        }

        if(!CollectionUtils.isEmpty(jdglDiffAnalysisPathList)) {
            for (JdglDiffAnalysisPath jdglDiffAnalysisPath : jdglDiffAnalysisPathList) {
                JdglDiffAnalysisPath jdglDiffAnalysisPath1 = jdglDiffAnalysisPathList.stream().filter(vo -> vo.getPtVar1().equals(jdglDiffAnalysisPath.getPtVar2())).findFirst().orElse(null);
                if(jdglDiffAnalysisPath1 != null) jdglDiffAnalysisPath.setPid(jdglDiffAnalysisPath1.getId());
            }
            insertJdglDiffAnalysisPathList(jdglDiffAnalysisPathList);
        }

        return returnBig;
    }
}
