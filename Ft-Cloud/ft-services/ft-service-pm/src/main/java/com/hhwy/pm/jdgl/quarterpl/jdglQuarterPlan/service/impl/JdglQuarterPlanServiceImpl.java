package com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.common.domain.FtActBusiness;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.IJdglQuarterImagePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.mapper.JdglQuarterPlanMapper;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.service.IJdglQuarterPlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.domain.JdglQuarterValuePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service.IJdglQuarterValuePlanService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.service.IJdglYearPlanService;
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
public class JdglQuarterPlanServiceImpl implements IJdglQuarterPlanService {

    @Autowired
    private JdglQuarterPlanMapper jdglQuarterPlanMapper;

    @Autowired
    private IJdglQuarterValuePlanService iJdglQuarterValuePlanService;

    @Autowired
    private IJdglQuarterImagePlanService iJdglQuarterImagePlanService;

    @Autowired
    private IJdglYearPlanService jdglYearPlanService;

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

    public JdglQuarterPlan getJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan) {
        JdglQuarterPlan jdglQuarterPlan1 = jdglQuarterPlanMapper.getJdglQuarterPlan(jdglQuarterPlan);
        if(jdglQuarterPlan1 != null) {
            List<JdglQuarterValuePlan> jdglQuarterValuePlanListByPlanId = iJdglQuarterValuePlanService.getJdglQuarterValuePlanListByPlanId(jdglQuarterPlan1.getId());
            jdglQuarterPlan1.setJdglQuarterValuePlanList(jdglQuarterValuePlanListByPlanId);
            List<JdglQuarterImagePlan> jdglQuarterImagePlanListByPlanId = iJdglQuarterImagePlanService.getJdglQuarterImagePlanListByPlanId(jdglQuarterPlan1.getId());
            jdglQuarterPlan1.setJdglQuarterImagePlanList(jdglQuarterImagePlanListByPlanId);
        }
        return jdglQuarterPlanMapper.getJdglQuarterPlan(jdglQuarterPlan);
    }

    @Override
    public JdglQuarterPlan getUsingQuarterPlanByYearAndQuarter(String year, String quarter) {
        JdglQuarterPlan jdglQuarterPlan = new JdglQuarterPlan();
        jdglQuarterPlan.setYear(year);
        jdglQuarterPlan.setQuarter(quarter);
        jdglQuarterPlan.setTaskStatus("5");
        jdglQuarterPlan.setIsUse("1");
        return getJdglQuarterPlan(jdglQuarterPlan);
    }

    /**
     * 获取初始数据&未完&
     * @param jdglQuarterPlanParam
     * @return
     */
    @Override
    public JdglQuarterPlan getInitJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlanParam) {

        JdglQuarterPlan returnVO = new JdglQuarterPlan();

        String year1 = jdglQuarterPlanParam.getYear();
        Date nowDate = DateUtils.getNowDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String nowStr = sdf.format(nowDate);

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
            returnVO.setRemainYearAmtDl(xmslContractInfo.getEffectiveAmout());

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

        JdglYearPlan usingYearPlanByYear = jdglYearPlanService.getUsingYearPlanByYear(year1);
        if(usingYearPlanByYear != null) {
            returnVO.setYearPlanValueDl(usingYearPlanByYear.getYearPlanValueDl());
        }

        // 根据期次获取开累产值数据
        String quarter = jdglQuarterPlanParam.getQuarter();

        // 计算合同、产值数据
        Map<String, Date> dateRange4Quarter = StatisticsUtils.getDateRange4Quarter(year1, quarter);
        Date startQ = dateRange4Quarter.get("start");
        Map<String, Date> dateRange4Year = StatisticsUtils.getDateRange4Year(year1);
        Date startY = dateRange4Year.get("start");

        BigDecimal countValue = jdglDayScheduleService.getCountValue(startY, startQ);
        returnVO.setYearCompValueDl(countValue);
        if(returnVO.getYearCompValueDl() == null) returnVO.setYearCompValueDl(new BigDecimal(0));
        if(returnVO.getYearPlanValueDl()== null) returnVO.setYearPlanValueDl(new BigDecimal(0));
        returnVO.setRemainYearAmtDl(returnVO.getYearPlanValueDl().subtract(returnVO.getYearCompValueDl()));

        return returnVO;
    }

    /**
     * 调整版本&未完&
     * @param jdglQuarterPlanParam
     * @return
     */
    @Override
    public int adjust(JdglQuarterPlan jdglQuarterPlanParam) {
        int i = 0;

        if(jdglQuarterPlanParam != null) {
            Long id = IdWorker.createId();
            jdglQuarterPlanParam.setId(id);
            jdglQuarterPlanParam.setCreateUser(SecurityUtils.getUserName());
            jdglQuarterPlanParam.setCreateTime(DateUtils.getNowDate());
            jdglQuarterPlanParam.setVersion(Integer.parseInt(jdglQuarterPlanParam.getVersion()) + 1 + "");
            jdglQuarterPlanParam.setTaskStatus("0");
            jdglQuarterPlanParam.setIsUse("0");

            i = jdglQuarterPlanMapper.insertJdglQuarterPlan(jdglQuarterPlanParam);

            List<JdglQuarterImagePlan> jdglQuarterImagePlanList = jdglQuarterPlanParam.getJdglQuarterImagePlanList();
            if(!CollectionUtils.isEmpty(jdglQuarterImagePlanList)) {
                for (JdglQuarterImagePlan jdglQuarterImagePlan : jdglQuarterImagePlanList) {
                    jdglQuarterImagePlan.setPlanId(id);
                }
                iJdglQuarterImagePlanService.insertJdglQuarterImagePlanList(jdglQuarterImagePlanList);
            }
        }

        return i;
    }

    public List<JdglQuarterPlan> getJdglQuarterPlanList(JdglQuarterPlan jdglQuarterPlan) {
        List<JdglQuarterPlan> jdglQuarterPlanList = jdglQuarterPlanMapper.getJdglQuarterPlanList(jdglQuarterPlan);
        String tenantKey = SecurityUtils.getTenantKey();
        if(!CollectionUtils.isEmpty(jdglQuarterPlanList)) {
            for (JdglQuarterPlan jdglQuarterPlan1 : jdglQuarterPlanList) {
                FtActBusiness flowInfo = FlowInfoSearchUtil
                        .getFlowInfo(FlowEnum.JDGL_QUARTERPLAN.getTableName(), String.valueOf(jdglQuarterPlan1.getId()), tenantKey);
                jdglQuarterPlan1.setTaskStatus(flowInfo.getName());
                jdglQuarterPlan1.setAssignee(flowInfo.getAssignee());
                List<JdglQuarterImagePlan> jdglQuarterImagePlanListByPlanId = iJdglQuarterImagePlanService.getJdglQuarterImagePlanListByPlanId(jdglQuarterPlan1.getId());
                jdglQuarterPlan1.setJdglQuarterImagePlanList(jdglQuarterImagePlanListByPlanId);
                List<JdglQuarterValuePlan> jdglQuarterValuePlanListByPlanId = iJdglQuarterValuePlanService.getJdglQuarterValuePlanListByPlanId(jdglQuarterPlan1.getId());
                jdglQuarterPlan1.setJdglQuarterValuePlanList(jdglQuarterValuePlanListByPlanId);
            }
        }
        return jdglQuarterPlanList;
    }

    @Transactional
    public int insertJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan) {

        String year = jdglQuarterPlan.getYear();
        String quarter = jdglQuarterPlan.getQuarter();
        JdglQuarterPlan queryExist = new JdglQuarterPlan();
        queryExist.setYear(year);
        queryExist.setQuarter(quarter);
        List<JdglQuarterPlan> jdglQuarterPlanList = jdglQuarterPlanMapper.getJdglQuarterPlanList(queryExist);
        if(!CollectionUtils.isEmpty(jdglQuarterPlanList)) {
            throw new RuntimeException("已存在"+year+"年第"+quarter+"季度数据!");
        }

        Long id = IdWorker.createId();
        jdglQuarterPlan.setId(id);
        jdglQuarterPlan.setCreateUser(SecurityUtils.getSysUser().getNickName());
        jdglQuarterPlan.setCreateTime(DateUtils.getNowDate());
        jdglQuarterPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglQuarterPlan.setUpdateTime(DateUtils.getNowDate());
        jdglQuarterPlan.setVersion("1");
        jdglQuarterPlan.setIsUse("0");
        return jdglQuarterPlanMapper.insertJdglQuarterPlan(jdglQuarterPlan);
    }

    @Transactional
    public int insertJdglQuarterPlanList(List<JdglQuarterPlan> jdglQuarterPlanList) {
        for (JdglQuarterPlan jdglQuarterPlan : jdglQuarterPlanList) {
            jdglQuarterPlan.setId(IdWorker.createId());
            jdglQuarterPlan.setCreateUser(SecurityUtils.getUserName());
            jdglQuarterPlan.setCreateTime(DateUtils.getNowDate());
            jdglQuarterPlan.setVersion("1");
        }
        return jdglQuarterPlanMapper.insertJdglQuarterPlanList(jdglQuarterPlanList);
    }

    @Transactional
    public int updateJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan) {
        jdglQuarterPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglQuarterPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglQuarterValuePlanService.updateJdglQuarterValuePlanList(jdglQuarterPlan.getJdglQuarterValuePlanList());
        List<JdglQuarterImagePlan> jdglQuarterImagePlanList = jdglQuarterPlan.getJdglQuarterImagePlanList();
        if(!CollectionUtils.isEmpty(jdglQuarterImagePlanList)) {
            for (JdglQuarterImagePlan jdglQuarterImagePlan: jdglQuarterImagePlanList) {
                jdglQuarterImagePlan.setPlanId(jdglQuarterPlan.getId());
            }
        }
        iJdglQuarterImagePlanService.updateJdglQuarterImagePlanList(jdglQuarterImagePlanList);

        // 根据计划完成产值汇总更新年计划产值&未完&

        return jdglQuarterPlanMapper.updateJdglQuarterPlan(jdglQuarterPlan);
    }

    @Transactional
    public int updateJdglQuarterPlanList(List<JdglQuarterPlan> jdglQuarterPlanList) {
        for (JdglQuarterPlan jdglQuarterPlan : jdglQuarterPlanList) {
            jdglQuarterPlan.setUpdateUser(SecurityUtils.getUserName());
            jdglQuarterPlan.setUpdateTime(DateUtils.getNowDate());
//            iJdglQuarterValuePlanService.updateJdglQuarterValuePlanList(jdglQuarterPlan.getJdglQuarterValuePlanList());
            iJdglQuarterImagePlanService.updateJdglQuarterImagePlanList(jdglQuarterPlan.getJdglQuarterImagePlanList());
        }
        return jdglQuarterPlanMapper.updateJdglQuarterPlanList(jdglQuarterPlanList);
    }

    @Transactional
    public int deleteJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan) {
        jdglQuarterPlan.setUpdateUser(SecurityUtils.getUserName());
        jdglQuarterPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglQuarterValuePlanService.deleteJdglQuarterValuePlanByQuarterPlanId(jdglQuarterPlan.getId());
        iJdglQuarterImagePlanService.deleteJdglQuarterImagePlanByPlanId(jdglQuarterPlan.getId());
        return jdglQuarterPlanMapper.deleteJdglQuarterPlan(jdglQuarterPlan);
    }

    @Transactional
    public int deleteJdglQuarterPlanByPks(List<Long> jdglQuarterPlanPkList) {
        for (Long id : jdglQuarterPlanPkList) {
//            iJdglQuarterValuePlanService.deleteJdglQuarterValuePlanByQuarterPlanId(id);
            iJdglQuarterImagePlanService.deleteJdglQuarterImagePlanByPlanId(id);
        }
        return jdglQuarterPlanMapper.deleteJdglQuarterPlanByPks(jdglQuarterPlanPkList);
    }


}
