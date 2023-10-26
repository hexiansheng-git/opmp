package com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.impl;

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
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.mapper.JdglMonthPlanMapper;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.domain.JdglMonthValuePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.IJdglMonthValuePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.mapper.JdglQuarterPlanMapper;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
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
            if(jdglMonthPlan1.getThisPlanValueDl() != null) jdglMonthPlan1.setThisPlanValueDl(jdglMonthPlan1.getThisPlanValueDl().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
            List<JdglMonthValuePlan> jdglMonthValuePlanListByPlanId = iJdglMonthValuePlanService.getJdglMonthValuePlanListByPlanId(jdglMonthPlan1.getId());
            jdglMonthPlan1.setJdglMonthValuePlanList(jdglMonthValuePlanListByPlanId);
            List<JdglMonthImagePlan> jdglMonthImagePlanListByPlanId = iJdglMonthImagePlanService.getJdglMonthImagePlanListByPlanId(jdglMonthPlan1.getId());
            jdglMonthPlan1.setJdglMonthImagePlanList(jdglMonthImagePlanListByPlanId);
            FlowInfoSearchUtil.getFlowInfo(jdglMonthPlan1,FlowEnum.JDGL_MONTHPLAN);
        }
        return jdglMonthPlan1;
    }

    @Override
    public JdglMonthPlan getUsingMonthPlanByYearAndMonth(String year, String month) {
        JdglMonthPlan jdglMonthPlan = new JdglMonthPlan();
        jdglMonthPlan.setYear(year);
        jdglMonthPlan.setMonth(month);
        jdglMonthPlan.setTaskStatus("5");
        jdglMonthPlan.setIsUse("1");
        return jdglMonthPlanMapper.getJdglMonthPlan(jdglMonthPlan);
    }

    /**
     * 获取初始数据&未完&
     * @param jdglMonthPlanParam
     * @return
     */
    @Override
    public JdglMonthPlan getInitJdglMonthPlan(JdglMonthPlan jdglMonthPlanParam) {

        String year1 = jdglMonthPlanParam.getYear();
        String month = jdglMonthPlanParam.getMonth();
        if(StringUtils.isEmpty(year1) || StringUtils.isEmpty(month)) {
            throw new RuntimeException("参数传入异常!");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String nowStr =year1 + jdglMonthPlanParam.getMonth();
        jdglMonthPlanParam.setVersion("V1.0");

        // 获取项目及合同信息
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(new XmslContractInfo());

        ProjectBasicInfo projectBasicInfo = xmslProjectBasicInfoService.projectInfo();

        if(projectBasicInfo != null) {
//            returnVO.setCustUnit(projectBasicInfo.getContractCurrency());
//            returnVO.setCustUnitCode(projectBasicInfo.getContractCurrencyCode());
        }

        if(xmslContractInfo != null) {
            jdglMonthPlanParam.setCustUnit(xmslContractInfo.getListCurrencyName());
            jdglMonthPlanParam.setCustUnitCode(xmslContractInfo.getListCurrencyCode());
            jdglMonthPlanParam.setProjectName(xmslContractInfo.getProjectName());
            jdglMonthPlanParam.setContactAmtCu(xmslContractInfo.getEffectiveAmout());
            jdglMonthPlanParam.setRemainQuarterAmtDl(xmslContractInfo.getEffectiveAmout());

            // 获取财务管理-风险管理-汇率登记
            List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractInfo.getXmslContractPayinfoList();
            if(!CollectionUtils.isEmpty(xmslContractPayinfoList) && jdglMonthPlanParam.getCustUnitCode() != null) {
//                XmslContractPayinfo xmslContractPayinfo = xmslContractPayinfoList.stream().filter(vo -> jdglMonthPlanParam.getCustUnitCode().equals(vo.getCurrencyCode())).findFirst().orElse(null);
                XmslContractPayinfo xmslContractPayinfo = xmslContractPayinfoList.stream().filter(vo -> "USD".equals(vo.getCurrencyCode())).findFirst().orElse(null);
                if(xmslContractPayinfo != null && "1".equals(xmslContractPayinfo.getRateType())) {
                    jdglMonthPlanParam.setExchangeRate(new BigDecimal(xmslContractPayinfo.getObversionRate()));
                }
            }
        }

        if(jdglMonthPlanParam.getExchangeRate() == null) {
            PeriodInfo periodInfo = new PeriodInfo();
            periodInfo.setCurrencyCode(jdglMonthPlanParam.getCustUnitCode());
            periodInfo.setQueryDate(year1);
            AjaxResult ajaxResult = systemServiceApi.selectPeriodByYear(periodInfo);
            if(ajaxResult.get("data") != null) {
                List<Map> data = (List<Map>) ajaxResult.get("data");
                Map periodMap = data.stream().filter(map -> nowStr.equals(map.get("periodCode"))).findFirst().orElse(null);
                if(periodMap != null && periodMap.get("rate") != null) {
                    jdglMonthPlanParam.setExchangeRate(new BigDecimal((double)periodMap.get("rate")));
                }
            }
        }

        // 根据期次获取开累产值数据


        String quarter = StatisticsUtils.getQuarter(month);

        JdglQuarterPlan jdglQuarterPlan = new JdglQuarterPlan();

        jdglQuarterPlan.setQuarter(quarter);
        jdglQuarterPlan.setYear(year1);
        jdglQuarterPlan.setIsUse("1");
        jdglQuarterPlan.setTaskStatus("5");

        JdglQuarterPlan jdglQuarterPlan1 = jdglQuarterPlanMapper.getJdglQuarterPlan(jdglQuarterPlan);

        if(jdglQuarterPlan1 != null) {
            jdglMonthPlanParam.setQuarterPlanValueDl(jdglQuarterPlan1.getThisPlanValueDl()  == null ? BigDecimal.ZERO : jdglQuarterPlan1.getThisPlanValueDl().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        }

        // 计算合同、产值数据
        Map<String, Date> dateRange4YearMonth = StatisticsUtils.getDateRange4YearMonth(year1, month);
        Date startM = dateRange4YearMonth.get("start");
        Map<String, Date> dateRange4Quarter = StatisticsUtils.getDateRange4Quarter(year1, quarter);
        Date startQ = dateRange4Quarter.get("start");

        BigDecimal countValue = jdglDayScheduleService.getCountValue(startQ, startM);

        jdglMonthPlanParam.setQuarterCompValueDl(countValue  == null ? BigDecimal.ZERO : countValue.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
        if(jdglMonthPlanParam.getQuarterPlanValueDl() == null) jdglMonthPlanParam.setQuarterPlanValueDl(new BigDecimal(0));
        if(jdglMonthPlanParam.getQuarterCompValueDl()== null) jdglMonthPlanParam.setQuarterCompValueDl(new BigDecimal(0));
        jdglMonthPlanParam.setRemainQuarterAmtDl(jdglMonthPlanParam.getQuarterPlanValueDl().subtract(jdglMonthPlanParam.getQuarterCompValueDl()));

        return jdglMonthPlanParam;
    }

    /**
     * 调整版本&未完&
     * @param jdglMonthPlan
     * @return
     */
    @Override
    public JdglMonthPlan adjust(JdglMonthPlan jdglMonthPlan) {
//        int i = 0;

        JdglMonthPlan jdglMonthPlanParam = getJdglMonthPlan(jdglMonthPlan);
        if(jdglMonthPlanParam != null) {
            Long id = IdWorker.createId();
            jdglMonthPlanParam.setId(id);
            jdglMonthPlanParam.setCreateUser(SecurityUtils.getSysUser().getNickName());
            jdglMonthPlanParam.setCreateTime(DateUtils.getNowDate());
            jdglMonthPlanParam.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            jdglMonthPlanParam.setUpdateTime(DateUtils.getNowDate());
            String version = jdglMonthPlanParam.getVersion();
            if(StringUtils.isNotEmpty(version)) {
                String v = version.replace("V", "");
                String replace = v.replace(".0", "");
                jdglMonthPlanParam.setVersion("V" + (Integer.valueOf(replace)+1) + ".0");
            }
            jdglMonthPlanParam.setTaskStatus("0");
            jdglMonthPlanParam.setIsUse("0");

//            i = jdglMonthPlanMapper.insertJdglMonthPlan(jdglMonthPlanParam);
//
//            List<JdglMonthImagePlan> jdglMonthImagePlanList = jdglMonthPlanParam.getJdglMonthImagePlanList();
//            if(!CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
//                for (JdglMonthImagePlan jdglMonthImagePlan : jdglMonthImagePlanList) {
//                    jdglMonthImagePlan.setPlanId(id);
//                }
//                iJdglMonthImagePlanService.insertJdglMonthImagePlanList(jdglMonthImagePlanList);
//            }
        }

        return jdglMonthPlanParam;
    }

    @Override
    public void updateTaskStatus(Long id) {
        JdglMonthPlan queryThis = new JdglMonthPlan();
        queryThis.setId(id);
        JdglMonthPlan jdglMonthPlan = jdglMonthPlanMapper.getJdglMonthPlan(queryThis);
        if(jdglMonthPlan != null) {
            JdglMonthPlan usingMonthPlanByYearAndMonth = getUsingMonthPlanByYearAndMonth(jdglMonthPlan.getYear(), jdglMonthPlan.getMonth());
            if(usingMonthPlanByYearAndMonth != null) {
                usingMonthPlanByYearAndMonth.setIsUse("0");
                jdglMonthPlanMapper.updateJdglMonthPlan(usingMonthPlanByYearAndMonth);
            }
            jdglMonthPlan.setIsUse("1");
            jdglMonthPlan.setTaskStatus("5");
            jdglMonthPlanMapper.updateJdglMonthPlan(jdglMonthPlan);
        }
    }

    @Override
    public JdglMonthPlan getJdglMonthPlanListById(Long planId) {
        JdglMonthPlan jdglMonthPlan = new JdglMonthPlan();
        jdglMonthPlan.setId(planId);
        return getJdglMonthPlan(jdglMonthPlan);
    }

    public List<JdglMonthPlan> getJdglMonthPlanList(JdglMonthPlan jdglMonthPlan) {
        List<JdglMonthPlan> jdglMonthPlanList = jdglMonthPlanMapper.getJdglMonthPlanList(jdglMonthPlan);
        String tenantKey = SecurityUtils.getTenantKey();
        if(!CollectionUtils.isEmpty(jdglMonthPlanList)) {
            for (JdglMonthPlan jdglMonthPlan1 : jdglMonthPlanList) {
                if(jdglMonthPlan1.getThisPlanValueDl() != null) jdglMonthPlan1.setThisPlanValueDl(jdglMonthPlan1.getThisPlanValueDl().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));

//                List<JdglMonthImagePlan> jdglMonthImagePlanListByPlanId = iJdglMonthImagePlanService.getJdglMonthImagePlanListByPlanId(jdglMonthPlan1.getId());
//                jdglMonthPlan1.setJdglMonthImagePlanList(jdglMonthImagePlanListByPlanId);
//                List<JdglMonthValuePlan> jdglMonthValuePlanListByPlanId = iJdglMonthValuePlanService.getJdglMonthValuePlanListByPlanId(jdglMonthPlan1.getId());
//                jdglMonthPlan1.setJdglMonthValuePlanList(jdglMonthValuePlanListByPlanId);
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
        queryExist.setVersion(jdglMonthPlan.getVersion());
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
        jdglMonthPlan.setIsUse("0");

        jdglMonthPlanMapper.insertJdglMonthPlan(jdglMonthPlan);

        List<JdglMonthImagePlan> jdglMonthImagePlanList = jdglMonthPlan.getJdglMonthImagePlanList();
        if(!CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
            List<JdglMonthImagePlan> imagePlans = TreeUtil.treeToList(jdglMonthImagePlanList);
            imagePlans.forEach(vo -> {
                vo.setPlanId(id);
            });
            iJdglMonthImagePlanService.insertJdglMonthImagePlanList(imagePlans);
        }

        BigDecimal thisPlanAmt = iJdglMonthImagePlanService.getThisPlanAmt(id);
        BigDecimal exchangeRate = jdglMonthPlan.getExchangeRate();
        jdglMonthPlan.setThisPlanValueCu(thisPlanAmt);
        if(thisPlanAmt != null && exchangeRate != null && BigDecimal.ZERO.compareTo(exchangeRate) != 0) {
            jdglMonthPlan.setThisPlanValueDl(thisPlanAmt.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP));
        } else {
            jdglMonthPlan.setThisPlanValueDl(BigDecimal.ZERO);
        }
        return jdglMonthPlanMapper.updateJdglMonthPlan(jdglMonthPlan);
    }

    @Transactional
    public int insertJdglMonthPlanList(List<JdglMonthPlan> jdglMonthPlanList) {
        for (JdglMonthPlan jdglMonthPlan : jdglMonthPlanList) {
            jdglMonthPlan.setId(IdWorker.createId());
            jdglMonthPlan.setCreateUser(SecurityUtils.getUserName());
            jdglMonthPlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglMonthPlanMapper.insertJdglMonthPlanList(jdglMonthPlanList);
    }

    @Transactional
    public int updateJdglMonthPlan(JdglMonthPlan jdglMonthPlan) {

        Long id = jdglMonthPlan.getId();
        String year = jdglMonthPlan.getYear();
        String month = jdglMonthPlan.getMonth();
        JdglMonthPlan queryExist = new JdglMonthPlan();
        queryExist.setYear(year);
        queryExist.setMonth(month);
        List<JdglMonthPlan> jdglMonthPlanList = jdglMonthPlanMapper.getJdglMonthPlanList(queryExist);
        if(!CollectionUtils.isEmpty(jdglMonthPlanList)) {
            JdglMonthPlan jdglMonthPlan1 = jdglMonthPlanList.stream().filter(vo -> id.equals(vo.getId())).findFirst().orElse(null);
            if(jdglMonthPlan1 == null) {
                throw new RuntimeException("已存在"+year+"年"+month+"月数据!");
            }
        }

        jdglMonthPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglMonthPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglMonthValuePlanService.updateJdglMonthValuePlanList(jdglMonthPlan.getJdglMonthValuePlanList());
        List<JdglMonthImagePlan> jdglMonthImagePlanList = jdglMonthPlan.getJdglMonthImagePlanList();
        if(!CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
            List<JdglMonthImagePlan> jdglMonthImagePlans = TreeUtil.treeToListWithoutId(jdglMonthImagePlanList);
            for (JdglMonthImagePlan jdglMonthImagePlan: jdglMonthImagePlans) {
                jdglMonthImagePlan.setPlanId(jdglMonthPlan.getId());
            }
            iJdglMonthImagePlanService.updateJdglMonthImagePlanList(jdglMonthImagePlans);
        }
        BigDecimal thisPlanAmt = iJdglMonthImagePlanService.getThisPlanAmt(id);
        BigDecimal exchangeRate = jdglMonthPlan.getExchangeRate();
        jdglMonthPlan.setThisPlanValueCu(thisPlanAmt);
        if(thisPlanAmt != null && exchangeRate != null && BigDecimal.ZERO.compareTo(exchangeRate) != 0) {
            jdglMonthPlan.setThisPlanValueDl(thisPlanAmt.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_UP));
        } else {
            jdglMonthPlan.setThisPlanValueDl(BigDecimal.ZERO);
        }
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
