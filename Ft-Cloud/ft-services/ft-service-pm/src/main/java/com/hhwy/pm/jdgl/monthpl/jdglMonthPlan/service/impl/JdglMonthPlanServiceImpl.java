package com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.common.domain.FtActBusiness;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.mapper.JdglMonthPlanMapper;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.domain.JdglMonthValuePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.IJdglMonthValuePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.mapper.JdglQuarterPlanMapper;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
@Service
public class JdglMonthPlanServiceImpl implements IJdglMonthPlanService {

    @Autowired
    private JdglMonthPlanMapper jdglMonthPlanMapper;

    @Autowired
    private JdglQuarterPlanMapper jdglQuarterPlanMapper;

    @Autowired
    private IJdglMonthValuePlanService iJdglMonthValuePlanService;

    @Autowired
    private IJdglMonthImagePlanService iJdglMonthImagePlanService;

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private IJdglDayScheduleService jdglDayScheduleService;

//    @Autowired
//    private IPeriodCurrencyService periodCurrencyService;

    public JdglMonthPlan getJdglMonthPlan(JdglMonthPlan jdglMonthPlan) {
        JdglMonthPlan jdglMonthPlan1 = jdglMonthPlanMapper.getJdglMonthPlan(jdglMonthPlan);
        if(jdglMonthPlan1 != null) {
            List<JdglMonthValuePlan> jdglMonthValuePlanListByPlanId = iJdglMonthValuePlanService.getJdglMonthValuePlanListByPlanId(jdglMonthPlan1.getId());
            jdglMonthPlan1.setJdglMonthValuePlanList(jdglMonthValuePlanListByPlanId);
            List<JdglMonthImagePlan> jdglMonthImagePlanListByPlanId = iJdglMonthImagePlanService.getJdglMonthImagePlanListByPlanId(jdglMonthPlan1.getId());
            jdglMonthPlan1.setJdglMonthImagePlanList(jdglMonthImagePlanListByPlanId);
        }
        return jdglMonthPlanMapper.getJdglMonthPlan(jdglMonthPlan);
    }

    @Override
    public JdglMonthPlan getUsingMonthPlanByYearAndMonth(String year, String month) {
        JdglMonthPlan jdglMonthPlan = new JdglMonthPlan();
        jdglMonthPlan.setYear("year");
        jdglMonthPlan.setMonth("month");
        jdglMonthPlan.setTaskStatus("5");
        jdglMonthPlan.setIsUse("1");
        return getJdglMonthPlan(jdglMonthPlan);
    }

    /**
     * 获取初始数据&未完&
     * @param jdglMonthPlanParam
     * @return
     */
    @Override
    public JdglMonthPlan getInitJdglMonthPlan(JdglMonthPlan jdglMonthPlanParam) {

        JdglMonthPlan returnVO = new JdglMonthPlan();

        String year1 = jdglMonthPlanParam.getYear();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String nowStr =year1 + jdglMonthPlanParam.getMonth();

        // 获取项目及合同信息
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(new XmslContractInfo());

        ProjectBasicInfo projectBasicInfo = xmslProjectBasicInfoService.projectInfo();

        if(projectBasicInfo != null) {
            returnVO.setCustUnit(projectBasicInfo.getContractCurrency());
            returnVO.setCustUnitCode(projectBasicInfo.getContractCurrencyCode());
        }

        if(xmslContractInfo != null) {
            returnVO.setProjectName(xmslContractInfo.getProjectName());
            returnVO.setContactAmtCu(xmslContractInfo.getEffectiveAmout());
            returnVO.setRemainQuarterAmtDl(xmslContractInfo.getEffectiveAmout());

            // 获取财务管理-风险管理-汇率登记
            List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractInfo.getXmslContractPayinfoList();
            if(!CollectionUtils.isEmpty(xmslContractPayinfoList)) {
                XmslContractPayinfo xmslContractPayinfo = xmslContractPayinfoList.stream().filter(vo -> returnVO.getCustUnitCode().equals(vo.getCurrencyCode())).findFirst().orElse(null);
                if(xmslContractPayinfo != null && "1".equals(xmslContractPayinfo.getRateType())) {
                    returnVO.setExchangeRate(new BigDecimal(xmslContractPayinfo.getObversionRate()));
                }
            }
        }

        if(returnVO.getExchangeRate() == null) {
            PeriodInfo periodInfo = new PeriodInfo();
            periodInfo.setCurrencyCode(returnVO.getCustUnitCode());
            periodInfo.setQueryDate(year1);
            AjaxResult ajaxResult = systemServiceApi.selectPeriodByYear(periodInfo);
            if(ajaxResult.get("data") != null) {
                List<Map> data = (List<Map>) ajaxResult.get("data");
                Map periodMap = data.stream().filter(map -> nowStr.equals(map.get("periodCode"))).findFirst().orElse(null);
                if(periodMap != null && periodMap.get("rate") != null) {
                    returnVO.setExchangeRate((BigDecimal)periodMap.get("rate"));
                }
            }
        }

        // 根据期次获取开累产值数据
        String month = jdglMonthPlanParam.getMonth();

        String quarter = StatisticsUtils.getQuarter(month);

        JdglQuarterPlan jdglQuarterPlan = new JdglQuarterPlan();

        jdglQuarterPlan.setQuarter(quarter);
        jdglQuarterPlan.setYear(year1);
        jdglQuarterPlan.setIsUse("1");
        jdglQuarterPlan.setTaskStatus("5");

        JdglQuarterPlan jdglQuarterPlan1 = jdglQuarterPlanMapper.getJdglQuarterPlan(jdglQuarterPlan);

        if(jdglQuarterPlan1 != null) {
            returnVO.setQuarterPlanValueDl(jdglQuarterPlan1.getThisPlanValueDl());
        }

        // 计算合同、产值数据
        Map<String, Date> dateRange4YearMonth = StatisticsUtils.getDateRange4YearMonth(year1, month);
        Date startM = dateRange4YearMonth.get("start");
        Map<String, Date> dateRange4Quarter = StatisticsUtils.getDateRange4Quarter(year1, quarter);
        Date startQ = dateRange4Quarter.get("start");

        BigDecimal countValue = jdglDayScheduleService.getCountValue(startQ, startM);

        returnVO.setQuarterCompValueDl(countValue);
        if(returnVO.getQuarterPlanValueDl() == null) returnVO.setQuarterPlanValueDl(new BigDecimal(0));
        if(returnVO.getQuarterCompValueDl()== null) returnVO.setQuarterCompValueDl(new BigDecimal(0));
        returnVO.setRemainQuarterAmtDl(returnVO.getQuarterPlanValueDl().subtract(returnVO.getQuarterCompValueDl()));

        return returnVO;
    }

    /**
     * 调整版本&未完&
     * @param jdglMonthPlanParam
     * @return
     */
    @Override
    public int adjust(JdglMonthPlan jdglMonthPlanParam) {
        int i = 0;

        if(jdglMonthPlanParam != null) {
            Long id = IdWorker.createId();
            jdglMonthPlanParam.setId(id);
            jdglMonthPlanParam.setCreateUser(SecurityUtils.getUserName());
            jdglMonthPlanParam.setCreateTime(DateUtils.getNowDate());
            jdglMonthPlanParam.setVersion(Integer.parseInt(jdglMonthPlanParam.getVersion()) + 1 + "");
            jdglMonthPlanParam.setTaskStatus("0");
            jdglMonthPlanParam.setIsUse("0");

            i = jdglMonthPlanMapper.insertJdglMonthPlan(jdglMonthPlanParam);

            List<JdglMonthImagePlan> jdglMonthImagePlanList = jdglMonthPlanParam.getJdglMonthImagePlanList();
            if(!CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
                for (JdglMonthImagePlan jdglMonthImagePlan : jdglMonthImagePlanList) {
                    jdglMonthImagePlan.setPlanId(id);
                }
                iJdglMonthImagePlanService.insertJdglMonthImagePlanList(jdglMonthImagePlanList);
            }
        }

        return i;
    }

    public List<JdglMonthPlan> getJdglMonthPlanList(JdglMonthPlan jdglMonthPlan) {
        List<JdglMonthPlan> jdglMonthPlanList = jdglMonthPlanMapper.getJdglMonthPlanList(jdglMonthPlan);
        String tenantKey = SecurityUtils.getTenantKey();
        if(!CollectionUtils.isEmpty(jdglMonthPlanList)) {
            for (JdglMonthPlan jdglMonthPlan1 : jdglMonthPlanList) {
                List<JdglMonthImagePlan> jdglMonthImagePlanListByPlanId = iJdglMonthImagePlanService.getJdglMonthImagePlanListByPlanId(jdglMonthPlan1.getId());
                jdglMonthPlan1.setJdglMonthImagePlanList(jdglMonthImagePlanListByPlanId);
                List<JdglMonthValuePlan> jdglMonthValuePlanListByPlanId = iJdglMonthValuePlanService.getJdglMonthValuePlanListByPlanId(jdglMonthPlan1.getId());
                jdglMonthPlan1.setJdglMonthValuePlanList(jdglMonthValuePlanListByPlanId);
            }
        }
        FlowInfoSearchUtil.getFlowInfo(jdglMonthPlanList,FlowEnum.JDGL_MONTHPLAN);
        return jdglMonthPlanList;
    }

    @Transactional
    public int insertJdglMonthPlan(JdglMonthPlan jdglMonthPlan) {

        String year = jdglMonthPlan.getYear();
        String month = jdglMonthPlan.getMonth();
        JdglMonthPlan queryExist = new JdglMonthPlan();
        queryExist.setYear(year);
        queryExist.setMonth(month);
        List<JdglMonthPlan> jdglMonthPlanList = jdglMonthPlanMapper.getJdglMonthPlanList(queryExist);
        if(!CollectionUtils.isEmpty(jdglMonthPlanList)) {
            throw new RuntimeException("已存在"+year+"年"+month+"月数据!");
        }

        Long id = IdWorker.createId();
        jdglMonthPlan.setId(id);
        jdglMonthPlan.setCreateUser(SecurityUtils.getSysUser().getNickName());
        jdglMonthPlan.setCreateTime(DateUtils.getNowDate());
        jdglMonthPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglMonthPlan.setUpdateTime(DateUtils.getNowDate());
        jdglMonthPlan.setVersion("1");
        jdglMonthPlan.setIsUse("0");
        return jdglMonthPlanMapper.insertJdglMonthPlan(jdglMonthPlan);
    }

    @Transactional
    public int insertJdglMonthPlanList(List<JdglMonthPlan> jdglMonthPlanList) {
        for (JdglMonthPlan jdglMonthPlan : jdglMonthPlanList) {
            jdglMonthPlan.setId(IdWorker.createId());
            jdglMonthPlan.setCreateUser(SecurityUtils.getUserName());
            jdglMonthPlan.setCreateTime(DateUtils.getNowDate());
            jdglMonthPlan.setVersion("1");
        }
        return jdglMonthPlanMapper.insertJdglMonthPlanList(jdglMonthPlanList);
    }

    @Transactional
    public int updateJdglMonthPlan(JdglMonthPlan jdglMonthPlan) {
        jdglMonthPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglMonthPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglMonthValuePlanService.updateJdglMonthValuePlanList(jdglMonthPlan.getJdglMonthValuePlanList());
        List<JdglMonthImagePlan> jdglMonthImagePlanList = jdglMonthPlan.getJdglMonthImagePlanList();
        if(!CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
            for (JdglMonthImagePlan jdglMonthImagePlan: jdglMonthImagePlanList) {
                jdglMonthImagePlan.setPlanId(jdglMonthPlan.getId());
            }
        }
        iJdglMonthImagePlanService.updateJdglMonthImagePlanList(jdglMonthImagePlanList);

        // 根据计划完成产值汇总更新年计划产值&未完&

        return jdglMonthPlanMapper.updateJdglMonthPlan(jdglMonthPlan);
    }

    @Transactional
    public int updateJdglMonthPlanList(List<JdglMonthPlan> jdglMonthPlanList) {
        for (JdglMonthPlan jdglMonthPlan : jdglMonthPlanList) {
            jdglMonthPlan.setUpdateUser(SecurityUtils.getUserName());
            jdglMonthPlan.setUpdateTime(DateUtils.getNowDate());
//            iJdglMonthValuePlanService.updateJdglMonthValuePlanList(jdglMonthPlan.getJdglMonthValuePlanList());
            iJdglMonthImagePlanService.updateJdglMonthImagePlanList(jdglMonthPlan.getJdglMonthImagePlanList());
        }
        return jdglMonthPlanMapper.updateJdglMonthPlanList(jdglMonthPlanList);
    }

    @Transactional
    public int deleteJdglMonthPlan(JdglMonthPlan jdglMonthPlan) {
        jdglMonthPlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMonthPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglMonthValuePlanService.deleteJdglMonthValuePlanByMonthPlanId(jdglMonthPlan.getId());
        iJdglMonthImagePlanService.deleteJdglMonthImagePlanByPlanId(jdglMonthPlan.getId());
        return jdglMonthPlanMapper.deleteJdglMonthPlan(jdglMonthPlan);
    }

    @Transactional
    public int deleteJdglMonthPlanByPks(List<Long> jdglMonthPlanPkList) {
        for (Long id : jdglMonthPlanPkList) {
//            iJdglMonthValuePlanService.deleteJdglMonthValuePlanByMonthPlanId(id);
            iJdglMonthImagePlanService.deleteJdglMonthImagePlanByPlanId(id);
        }
        return jdglMonthPlanMapper.deleteJdglMonthPlanByPks(jdglMonthPlanPkList);
    }


}
