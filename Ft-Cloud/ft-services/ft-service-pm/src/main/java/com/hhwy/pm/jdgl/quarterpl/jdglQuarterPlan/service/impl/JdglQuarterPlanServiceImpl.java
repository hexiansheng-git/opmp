package com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
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
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.service.IJdglYearPlanService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
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
            if(jdglQuarterPlan1.getThisPlanValueDl() != null) jdglQuarterPlan1.setThisPlanValueDl(jdglQuarterPlan1.getThisPlanValueDl().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
//            if(jdglQuarterPlan1.getYearPlanValueDl() != null) jdglQuarterPlan1.setYearPlanValueDl(jdglQuarterPlan1.getYearPlanValueDl().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
            List<JdglQuarterValuePlan> jdglQuarterValuePlanListByPlanId = iJdglQuarterValuePlanService.getJdglQuarterValuePlanListByPlanId(jdglQuarterPlan1.getId());
            jdglQuarterPlan1.setJdglQuarterValuePlanList(jdglQuarterValuePlanListByPlanId);
            List<JdglQuarterImagePlan> jdglQuarterImagePlanListByPlanId = iJdglQuarterImagePlanService.getJdglQuarterImagePlanListByPlanId(jdglQuarterPlan1.getId());
            jdglQuarterPlan1.setJdglQuarterImagePlanList(jdglQuarterImagePlanListByPlanId);
            FlowInfoSearchUtil.getFlowInfo(jdglQuarterPlan1,FlowEnum.JDGL_QUARTERPLAN);
        }
        return jdglQuarterPlan1;
    }

    @Override
    public JdglQuarterPlan getUsingQuarterPlanByYearAndQuarter(String year, String quarter) {
        JdglQuarterPlan jdglQuarterPlan = new JdglQuarterPlan();
        jdglQuarterPlan.setYear(year);
        jdglQuarterPlan.setQuarter(quarter);
        jdglQuarterPlan.setTaskStatus("5");
        jdglQuarterPlan.setIsUse("1");
        return jdglQuarterPlanMapper.getJdglQuarterPlan(jdglQuarterPlan);
    }

    /**
     * 获取初始数据&未完&
     * @param jdglQuarterPlanParam
     * @return
     */
    @Override
    public JdglQuarterPlan getInitJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlanParam) {


        String year1 = jdglQuarterPlanParam.getYear();
        String quarter = jdglQuarterPlanParam.getQuarter();
        if(StringUtils.isEmpty(year1) || StringUtils.isEmpty(quarter)) {
            throw new RuntimeException("参数传入异常!");
        }

        Date nowDate = DateUtils.getNowDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String nowStr = sdf.format(nowDate);
        jdglQuarterPlanParam.setVersion("V1.0");

        // 获取项目及合同信息
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(new XmslContractInfo());

        ProjectBasicInfo projectBasicInfo = xmslProjectBasicInfoService.projectInfo();

        if(projectBasicInfo != null) {
//            returnVO.setCustUnit(projectBasicInfo.getContractCurrency());
//            returnVO.setCustUnitCode(projectBasicInfo.getContractCurrencyCode());
        }

        if(xmslContractInfo != null) {
            jdglQuarterPlanParam.setCustUnit(xmslContractInfo.getListCurrencyName());
            jdglQuarterPlanParam.setCustUnitCode(xmslContractInfo.getListCurrencyCode());
            jdglQuarterPlanParam.setProjectName(xmslContractInfo.getProjectName());
            jdglQuarterPlanParam.setContactAmtCu(xmslContractInfo.getEffectiveAmout());
            jdglQuarterPlanParam.setRemainYearAmtDl(xmslContractInfo.getEffectiveAmout());

            // 获取财务管理-风险管理-汇率登记
            List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractInfo.getXmslContractPayinfoList();
            if(!CollectionUtils.isEmpty(xmslContractPayinfoList) && jdglQuarterPlanParam.getCustUnitCode() != null) {
                XmslContractPayinfo xmslContractPayinfo = xmslContractPayinfoList.stream().filter(vo -> jdglQuarterPlanParam.getCustUnitCode().equals(vo.getCurrencyCode())).findFirst().orElse(null);
                if(xmslContractPayinfo != null && "1".equals(xmslContractPayinfo.getRateType())) {
                    jdglQuarterPlanParam.setExchangeRate(new BigDecimal(xmslContractPayinfo.getObversionRate()));
                }
            }
        }

        if(jdglQuarterPlanParam.getExchangeRate() == null) {
            PeriodInfo periodInfo = new PeriodInfo();
            periodInfo.setCurrencyCode(jdglQuarterPlanParam.getCustUnitCode());
            periodInfo.setQueryDate(year1);
            AjaxResult ajaxResult = systemServiceApi.selectPeriodByYear(periodInfo);
            if(ajaxResult.get("data") != null) {
                List<Map> data = (List<Map>) ajaxResult.get("data");
                Map periodMap = data.stream().filter(map -> nowStr.equals(map.get("periodCode"))).findFirst().orElse(null);
                if(periodMap != null && periodMap.get("rate") != null) {
                    jdglQuarterPlanParam.setExchangeRate(new BigDecimal((double)periodMap.get("rate")));
                }
            }
        }

        JdglYearPlan usingYearPlanByYear = jdglYearPlanService.getUsingYearPlanByYear(year1);
        if(usingYearPlanByYear != null) {
            jdglQuarterPlanParam.setYearPlanValueDl(usingYearPlanByYear.getYearPlanValueDl() == null ? BigDecimal.ZERO : usingYearPlanByYear.getYearPlanValueDl().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        }

        // 根据期次获取开累产值数据


        // 计算合同、产值数据
        Map<String, Date> dateRange4Quarter = StatisticsUtils.getDateRange4Quarter(year1, quarter);
        Date startQ = dateRange4Quarter.get("start");
        Map<String, Date> dateRange4Year = StatisticsUtils.getDateRange4Year(year1);
        Date startY = dateRange4Year.get("start");

        BigDecimal countValue = jdglDayScheduleService.getCountValue(startY, startQ);
        jdglQuarterPlanParam.setYearCompValueDl(countValue);
        if(jdglQuarterPlanParam.getYearCompValueDl() == null) jdglQuarterPlanParam.setYearCompValueDl(new BigDecimal(0));
        if(jdglQuarterPlanParam.getYearPlanValueDl()== null) jdglQuarterPlanParam.setYearPlanValueDl(new BigDecimal(0));
        jdglQuarterPlanParam.setRemainYearAmtDl(jdglQuarterPlanParam.getYearPlanValueDl().subtract(jdglQuarterPlanParam.getYearCompValueDl()));

        return jdglQuarterPlanParam;
    }

    /**
     * 调整版本&未完&
     * @param jdglQuarterPlan
     * @return
     */
    @Override
    public JdglQuarterPlan adjust(JdglQuarterPlan jdglQuarterPlan) {
//        int i = 0;

        JdglQuarterPlan jdglQuarterPlanParam = getJdglQuarterPlan(jdglQuarterPlan);
        if(jdglQuarterPlanParam != null) {
//            Long id = IdWorker.createId();
//            jdglQuarterPlanParam.setId(id);
            jdglQuarterPlanParam.setCreateUser(SecurityUtils.getSysUser().getNickName());
            jdglQuarterPlanParam.setCreateTime(DateUtils.getNowDate());
            jdglQuarterPlanParam.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            jdglQuarterPlanParam.setUpdateTime(DateUtils.getNowDate());
            String version = jdglQuarterPlanParam.getVersion();
            if(StringUtils.isNotEmpty(version)) {
                String v = version.replace("V", "");
                String replace = v.replace(".0", "");
                jdglQuarterPlanParam.setVersion("V" + (Integer.valueOf(replace)+1) + ".0");
            }
            jdglQuarterPlanParam.setTaskStatus("0");
            jdglQuarterPlanParam.setIsUse("0");

//            i = jdglQuarterPlanMapper.insertJdglQuarterPlan(jdglQuarterPlanParam);

//            List<JdglQuarterImagePlan> jdglQuarterImagePlanList = jdglQuarterPlanParam.getJdglQuarterImagePlanList();
//            if(!CollectionUtils.isEmpty(jdglQuarterImagePlanList)) {
//                for (JdglQuarterImagePlan jdglQuarterImagePlan : jdglQuarterImagePlanList) {
//                    jdglQuarterImagePlan.setPlanId(id);
//                }
//                iJdglQuarterImagePlanService.insertJdglQuarterImagePlanList(jdglQuarterImagePlanList);
//            }
        }

        return jdglQuarterPlanParam;
    }

    @Override
    public void updateTaskStatus(Long id) {
        JdglQuarterPlan queryThis = new JdglQuarterPlan();
        queryThis.setId(id);
        JdglQuarterPlan jdglQuarterPlan = jdglQuarterPlanMapper.getJdglQuarterPlan(queryThis);
        if (jdglQuarterPlan != null) {
            JdglQuarterPlan usingQuarterPlanByYearAndQuarter = getUsingQuarterPlanByYearAndQuarter(jdglQuarterPlan.getYear(), jdglQuarterPlan.getQuarter());
            if(usingQuarterPlanByYearAndQuarter != null) {
                usingQuarterPlanByYearAndQuarter.setIsUse("0");
                jdglQuarterPlanMapper.updateJdglQuarterPlan(usingQuarterPlanByYearAndQuarter);
            }
            jdglQuarterPlan.setTaskStatus("5");
            jdglQuarterPlan.setIsUse("1");
            jdglQuarterPlanMapper.updateJdglQuarterPlan(jdglQuarterPlan);
        }
    }

    @Override
    public JdglQuarterPlan getJdglQuarterPlanById(Long planId) {
        JdglQuarterPlan jdglQuarterPlan = new JdglQuarterPlan();
        jdglQuarterPlan.setId(planId);
        return getJdglQuarterPlan(jdglQuarterPlan);
    }

    public List<JdglQuarterPlan> getJdglQuarterPlanList(JdglQuarterPlan jdglQuarterPlan) {
        List<JdglQuarterPlan> jdglQuarterPlanList = jdglQuarterPlanMapper.getJdglQuarterPlanList(jdglQuarterPlan);
        String tenantKey = SecurityUtils.getTenantKey();
        if(!CollectionUtils.isEmpty(jdglQuarterPlanList)) {
            for (JdglQuarterPlan jdglQuarterPlan1 : jdglQuarterPlanList) {
                if(jdglQuarterPlan1.getThisPlanValueDl() != null) jdglQuarterPlan1.setThisPlanValueDl(jdglQuarterPlan1.getThisPlanValueDl().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));

//                List<JdglQuarterImagePlan> jdglQuarterImagePlanListByPlanId = iJdglQuarterImagePlanService.getJdglQuarterImagePlanListByPlanId(jdglQuarterPlan1.getId());
//                jdglQuarterPlan1.setJdglQuarterImagePlanList(jdglQuarterImagePlanListByPlanId);
//                List<JdglQuarterValuePlan> jdglQuarterValuePlanListByPlanId = iJdglQuarterValuePlanService.getJdglQuarterValuePlanListByPlanId(jdglQuarterPlan1.getId());
//                jdglQuarterPlan1.setJdglQuarterValuePlanList(jdglQuarterValuePlanListByPlanId);
            }
        }
        FlowInfoSearchUtil.getFlowInfo(jdglQuarterPlanList,FlowEnum.JDGL_QUARTERPLAN);
        return jdglQuarterPlanList;
    }

    @Transactional
    public int insertJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan) {

        String year = jdglQuarterPlan.getYear();
        String quarter = jdglQuarterPlan.getQuarter();
        JdglQuarterPlan queryExist = new JdglQuarterPlan();
        queryExist.setYear(year);
        queryExist.setQuarter(quarter);
        queryExist.setVersion(jdglQuarterPlan.getVersion());
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
        jdglQuarterPlan.setIsUse("0");

        jdglQuarterPlanMapper.insertJdglQuarterPlan(jdglQuarterPlan);

        List<JdglQuarterImagePlan> jdglQuarterImagePlanList = jdglQuarterPlan.getJdglQuarterImagePlanList();
        if(!CollectionUtils.isEmpty(jdglQuarterImagePlanList)) {
            List<JdglQuarterImagePlan> imagePlans = TreeUtil.treeToList(jdglQuarterImagePlanList);
            imagePlans.forEach(vo -> {
                vo.setPlanId(id);
            });
            iJdglQuarterImagePlanService.insertJdglQuarterImagePlanList(imagePlans);
        }
        BigDecimal thisPlanAmt = iJdglQuarterImagePlanService.getThisPlanAmt(id);
        BigDecimal exchangeRate = jdglQuarterPlan.getExchangeRate();
        jdglQuarterPlan.setThisPlanValueCu(thisPlanAmt);
        jdglQuarterPlan.setThisPlanValueDl(thisPlanAmt == null || exchangeRate == null ? thisPlanAmt : thisPlanAmt.multiply(exchangeRate));

        return jdglQuarterPlanMapper.updateJdglQuarterPlan(jdglQuarterPlan);
    }

    @Transactional
    public int insertJdglQuarterPlanList(List<JdglQuarterPlan> jdglQuarterPlanList) {
        for (JdglQuarterPlan jdglQuarterPlan : jdglQuarterPlanList) {
            jdglQuarterPlan.setId(IdWorker.createId());
            jdglQuarterPlan.setCreateUser(SecurityUtils.getUserName());
            jdglQuarterPlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglQuarterPlanMapper.insertJdglQuarterPlanList(jdglQuarterPlanList);
    }

    @Transactional
    public int updateJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlan) {
        Long id = jdglQuarterPlan.getId();
        String year = jdglQuarterPlan.getYear();
        String quarter = jdglQuarterPlan.getQuarter();
        JdglQuarterPlan queryExist = new JdglQuarterPlan();
        queryExist.setYear(year);
        queryExist.setQuarter(quarter);
        List<JdglQuarterPlan> jdglQuarterPlanList = jdglQuarterPlanMapper.getJdglQuarterPlanList(queryExist);
        if(!CollectionUtils.isEmpty(jdglQuarterPlanList)) {
            JdglQuarterPlan jdglQuarterPlan1 = jdglQuarterPlanList.stream().filter(vo -> id.equals(vo.getId())).findFirst().orElse(null);
            if(jdglQuarterPlan1 == null) {
                throw new RuntimeException("已存在"+year+"年第"+quarter+"季度数据!");
            }
        }

        jdglQuarterPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglQuarterPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglQuarterValuePlanService.updateJdglQuarterValuePlanList(jdglQuarterPlan.getJdglQuarterValuePlanList());
        List<JdglQuarterImagePlan> jdglQuarterImagePlanList = jdglQuarterPlan.getJdglQuarterImagePlanList();
        if(!CollectionUtils.isEmpty(jdglQuarterImagePlanList)) {
            List<JdglQuarterImagePlan> jdglQuarterImagePlans = TreeUtil.treeToListWithoutId(jdglQuarterImagePlanList);
            for (JdglQuarterImagePlan jdglQuarterImagePlan: jdglQuarterImagePlans) {
                jdglQuarterImagePlan.setPlanId(jdglQuarterPlan.getId());
            }
            iJdglQuarterImagePlanService.updateJdglQuarterImagePlanList(jdglQuarterImagePlans);
        }

        BigDecimal thisPlanAmt = iJdglQuarterImagePlanService.getThisPlanAmt(id);
        BigDecimal exchangeRate = jdglQuarterPlan.getExchangeRate();
        jdglQuarterPlan.setThisPlanValueCu(thisPlanAmt);
        jdglQuarterPlan.setThisPlanValueDl(thisPlanAmt == null || exchangeRate == null ? thisPlanAmt : thisPlanAmt.multiply(exchangeRate));


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
