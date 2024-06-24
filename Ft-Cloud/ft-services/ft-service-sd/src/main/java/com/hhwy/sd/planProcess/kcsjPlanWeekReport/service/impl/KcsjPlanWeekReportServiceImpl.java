package com.hhwy.sd.planProcess.kcsjPlanWeekReport.service.impl;

import java.util.*;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sd.organManage.util.StatisticsUtils;
import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjPlanProcess;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.exception.CustomBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
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
@Slf4j
public class KcsjPlanWeekReportServiceImpl implements IKcsjPlanWeekReportService {

    @Autowired
    private KcsjPlanWeekReportMapper kcsjPlanWeekReportMapper;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private PmServiceApi pmServiceApi;


    public KcsjPlanWeekReport getKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport) {
        return kcsjPlanWeekReportMapper.getKcsjPlanWeekReport(kcsjPlanWeekReport);
    }

    public List<KcsjPlanWeekReport> getKcsjPlanWeekReportList(KcsjPlanWeekReport kcsjPlanWeekReport) {
        return kcsjPlanWeekReportMapper.getKcsjPlanWeekReportList(kcsjPlanWeekReport);
    }

    @Transactional
    public int insertKcsjPlanWeekReport(KcsjPlanWeekReport kcsjPlanWeekReport) {
        kcsjPlanWeekReport.setId(IdWorker.createId());
//        kcsjPlanWeekReport.setCreateUser(SecurityUtils.getUserName());
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
        int i = kcsjPlanWeekReportMapper.updateKcsjPlanWeekReportList(kcsjPlanWeekReportList);
        try {
            doSendGm(SecurityUtils.getTenantKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return i;
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
        // 获取租户集合
        List<String> tenantKeyList = new ArrayList<>();
        List<SysTenant> sysTenants = systemServiceApi.tenantList();
        int i = 0;
        if(!CollectionUtils.isEmpty(sysTenants)) {
            sysTenants.forEach(vo -> tenantKeyList.add(vo.getTenantKey()));
            i = sysTenants.size();
        }
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            for (String tenantKey : tenantKeyList) {
                //切换租户
                DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
                try {
                    produceDataByPeriod(DateUtils.getNowDate());
                    //推送总部
                    doSendGm(tenantKey);
                }catch (Exception e){
                    log.error("周报计划生成错误，租户：{}", tenantKey);
                    throw new CustomBusinessException(e.getMessage());
                }
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
        return i;

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

    //数据推送总部版
    public void doSendGm(String tenantKey) throws Exception{
        KcsjPlanWeekReport planWeekReport = new KcsjPlanWeekReport();
        List<KcsjPlanWeekReport> kcsjPlanWeekReportList = kcsjPlanWeekReportMapper.getKcsjPlanWeekReportList(planWeekReport);
        if (CollUtil.isEmpty(kcsjPlanWeekReportList)) return;
        kcsjPlanWeekReportList.forEach(p ->{
            p.setPtVar5(tenantKey);
        });
        log.info("推送周报数据: {}", JSONObject.toJSONString(kcsjPlanWeekReportList));
        rocketMQTemplate.convertAndSend("kcsj_plan_week_report:tenantSuccess", kcsjPlanWeekReportList);
    }
}
