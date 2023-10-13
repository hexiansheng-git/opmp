package com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.service.impl;

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
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.domain.JdglWeekImagePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service.IJdglWeekImagePlanService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.mapper.JdglWeekPlanMapper;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.service.IJdglWeekPlanService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.domain.JdglWeekValuePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.service.IJdglWeekValuePlanService;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
@Service
public class JdglWeekPlanServiceImpl implements IJdglWeekPlanService {

    @Autowired
    private JdglWeekPlanMapper jdglWeekPlanMapper;

    @Autowired
    private IJdglMonthPlanService jdglMonthPlanService;

    @Autowired
    private IJdglWeekValuePlanService iJdglWeekValuePlanService;

    @Autowired
    private IJdglWeekImagePlanService iJdglWeekImagePlanService;

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

    public JdglWeekPlan getJdglWeekPlan(JdglWeekPlan jdglWeekPlan) {
        JdglWeekPlan jdglWeekPlan1 = jdglWeekPlanMapper.getJdglWeekPlan(jdglWeekPlan);
        if(jdglWeekPlan1 != null) {
            List<JdglWeekValuePlan> jdglWeekValuePlanListByPlanId = iJdglWeekValuePlanService.getJdglWeekValuePlanListByPlanId(jdglWeekPlan1.getId());
            jdglWeekPlan1.setJdglWeekValuePlanList(jdglWeekValuePlanListByPlanId);
            List<JdglWeekImagePlan> jdglWeekImagePlanListByPlanId = iJdglWeekImagePlanService.getJdglWeekImagePlanListByPlanId(jdglWeekPlan1.getId());
            jdglWeekPlan1.setJdglWeekImagePlanList(jdglWeekImagePlanListByPlanId);
            FlowInfoSearchUtil.getFlowInfo(jdglWeekPlan1,FlowEnum.JDGL_WEEKPLAN);
        }
        return jdglWeekPlan1;
    }

    @Override
    public JdglWeekPlan getUsingWeekPlanByYearAndWeek(String year, String week) {
        JdglWeekPlan jdglWeekPlan = new JdglWeekPlan();
        jdglWeekPlan.setYear(year);
        jdglWeekPlan.setWeek(week);
        jdglWeekPlan.setTaskStatus("5");
        jdglWeekPlan.setIsUse("1");
        return getJdglWeekPlan(jdglWeekPlan);
    }

    /**
     * 获取初始数据&未完&
     * @param jdglWeekPlanParam
     * @return
     */
    @Override
    public JdglWeekPlan getInitJdglWeekPlan(JdglWeekPlan jdglWeekPlanParam) {

        // 获取项目及合同信息
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(new XmslContractInfo());

        ProjectBasicInfo projectBasicInfo = xmslProjectBasicInfoService.projectInfo();

        String week = jdglWeekPlanParam.getWeek();
        String year1 = jdglWeekPlanParam.getYear();
        if(StringUtils.isEmpty(year1) || StringUtils.isEmpty(week)) {
            throw new RuntimeException("参数传入异常!");
        }
        Date nowDate = DateUtils.getNowDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String nowStr = sdf.format(nowDate);
        jdglWeekPlanParam.setVersion("V1.0");

        if(projectBasicInfo != null) {
//            returnVO.setCustUnit(projectBasicInfo.getContractCurrency());
//            returnVO.setCustUnitCode(projectBasicInfo.getContractCurrencyCode());
        }

        if(xmslContractInfo != null) {
            jdglWeekPlanParam.setCustUnit(xmslContractInfo.getListCurrencyName());
            jdglWeekPlanParam.setCustUnitCode(xmslContractInfo.getListCurrencyCode());
            jdglWeekPlanParam.setProjectName(xmslContractInfo.getProjectName());
            jdglWeekPlanParam.setContactAmtCu(xmslContractInfo.getEffectiveAmout());
            jdglWeekPlanParam.setRemainMonthAmtDl(xmslContractInfo.getEffectiveAmout());

            // 获取财务管理-风险管理-汇率登记
            List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractInfo.getXmslContractPayinfoList();
            if(!CollectionUtils.isEmpty(xmslContractPayinfoList) && jdglWeekPlanParam.getCustUnitCode() != null) {
                XmslContractPayinfo xmslContractPayinfo = xmslContractPayinfoList.stream().filter(vo -> jdglWeekPlanParam.getCustUnitCode().equals(vo.getCurrencyCode())).findFirst().orElse(null);
                if(xmslContractPayinfo != null && "1".equals(xmslContractPayinfo.getRateType())) {
                    jdglWeekPlanParam.setExchangeRate(new BigDecimal(xmslContractPayinfo.getObversionRate()));
                }
            }
        }

        if(jdglWeekPlanParam.getExchangeRate() == null) {
            PeriodInfo periodInfo = new PeriodInfo();
            periodInfo.setCurrencyCode(jdglWeekPlanParam.getCustUnitCode());
            periodInfo.setQueryDate(year1);
            AjaxResult ajaxResult = systemServiceApi.selectPeriodByYear(periodInfo);
            if(ajaxResult.get("data") != null) {
                List<Map> data = (List<Map>) ajaxResult.get("data");
                Map periodMap = data.stream().filter(map -> nowStr.equals(map.get("periodCode"))).findFirst().orElse(null);
                if(periodMap != null && periodMap.get("rate") != null) {
                    jdglWeekPlanParam.setExchangeRate(new BigDecimal((double)periodMap.get("rate")));
                }
            }
        }

        // 根据期次获取开累产值数据


        Calendar cl = Calendar.getInstance();
        cl.setWeekDate(Integer.valueOf(year1), Integer.valueOf(week), 1);

        String month = cl.get(Calendar.MONTH + 1) + "";

        JdglMonthPlan usingMonthPlanByYearAndMonth = jdglMonthPlanService.getUsingMonthPlanByYearAndMonth(year1, month);

        if (usingMonthPlanByYearAndMonth != null) {
            jdglWeekPlanParam.setMonthPlanValueDl(usingMonthPlanByYearAndMonth.getThisPlanValueDl());
        }

        // 计算合同、产值数据
        Map<String, Date> dateRange4YearMonth = StatisticsUtils.getDateRange4YearMonth(year1, month);

        Date startW = cl.getTime();
        Date startM = dateRange4YearMonth.get("start");

        BigDecimal countValue = jdglDayScheduleService.getCountValue(startM, startW);
        jdglWeekPlanParam.setMonthCompValueDl(countValue);

        if(jdglWeekPlanParam.getMonthPlanValueDl() == null) jdglWeekPlanParam.setMonthPlanValueDl(new BigDecimal(0));
        if(jdglWeekPlanParam.getMonthCompValueDl()== null) jdglWeekPlanParam.setMonthCompValueDl(new BigDecimal(0));
        jdglWeekPlanParam.setRemainMonthAmtDl(jdglWeekPlanParam.getMonthPlanValueDl().subtract(jdglWeekPlanParam.getMonthCompValueDl()));

        return jdglWeekPlanParam;
    }

    /**
     * 调整版本&未完&
     * @param jdglWeekPlan
     * @return
     */
    @Override
    public JdglWeekPlan adjust(JdglWeekPlan jdglWeekPlan) {
//        int i = 0;
        JdglWeekPlan jdglWeekPlanParam = getJdglWeekPlan(jdglWeekPlan);
        if(jdglWeekPlanParam != null) {
//            Long id = IdWorker.createId();
//            jdglWeekPlanParam.setId(id);
            jdglWeekPlanParam.setCreateUser(SecurityUtils.getSysUser().getNickName());
            jdglWeekPlanParam.setCreateTime(DateUtils.getNowDate());
            jdglWeekPlanParam.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            jdglWeekPlanParam.setUpdateTime(DateUtils.getNowDate());
            String version = jdglWeekPlanParam.getVersion();
            if(StringUtils.isNotEmpty(version)) {
                String v = version.replace("V", "");
                String replace = v.replace(".0", "");
                jdglWeekPlanParam.setVersion("V" + (Integer.valueOf(replace)+1) + ".0");
            }
            jdglWeekPlanParam.setTaskStatus("0");
            jdglWeekPlanParam.setIsUse("0");

//            i = jdglWeekPlanMapper.insertJdglWeekPlan(jdglWeekPlanParam);
//
//            List<JdglWeekImagePlan> jdglWeekImagePlanList = jdglWeekPlanParam.getJdglWeekImagePlanList();
//            if(!CollectionUtils.isEmpty(jdglWeekImagePlanList)) {
//                for (JdglWeekImagePlan jdglWeekImagePlan : jdglWeekImagePlanList) {
//                    jdglWeekImagePlan.setPlanId(id);
//                }
//                iJdglWeekImagePlanService.insertJdglWeekImagePlanList(jdglWeekImagePlanList);
//            }
        }

        return jdglWeekPlanParam;
    }

    @Override
    public void updateTaskStatus(Long id) {
        JdglWeekPlan queryThis = new JdglWeekPlan();
        queryThis.setId(id);
        JdglWeekPlan jdglWeekPlan = jdglWeekPlanMapper.getJdglWeekPlan(queryThis);
        if(jdglWeekPlan != null) {
            JdglWeekPlan usingWeekPlanByYearAndWeek = getUsingWeekPlanByYearAndWeek(jdglWeekPlan.getYear(), jdglWeekPlan.getWeek());
            if(usingWeekPlanByYearAndWeek != null) {
                usingWeekPlanByYearAndWeek.setIsUse("0");
                jdglWeekPlanMapper.updateJdglWeekPlan(usingWeekPlanByYearAndWeek);
            }
            jdglWeekPlan.setTaskStatus("5");
            jdglWeekPlan.setIsUse("1");
            jdglWeekPlanMapper.updateJdglWeekPlan(jdglWeekPlan);
        }
    }

    public List<JdglWeekPlan> getJdglWeekPlanList(JdglWeekPlan jdglWeekPlan) {
        List<JdglWeekPlan> jdglWeekPlanList = jdglWeekPlanMapper.getJdglWeekPlanList(jdglWeekPlan);
        if(!CollectionUtils.isEmpty(jdglWeekPlanList)) {
            for (JdglWeekPlan jdglWeekPlan1 : jdglWeekPlanList) {
                List<JdglWeekImagePlan> jdglWeekImagePlanListByPlanId = iJdglWeekImagePlanService.getJdglWeekImagePlanListByPlanId(jdglWeekPlan1.getId());
                jdglWeekPlan1.setJdglWeekImagePlanList(jdglWeekImagePlanListByPlanId);
                List<JdglWeekValuePlan> jdglWeekValuePlanListByPlanId = iJdglWeekValuePlanService.getJdglWeekValuePlanListByPlanId(jdglWeekPlan1.getId());
                jdglWeekPlan1.setJdglWeekValuePlanList(jdglWeekValuePlanListByPlanId);
            }
        }
        FlowInfoSearchUtil.getFlowInfo(jdglWeekPlanList,FlowEnum.JDGL_WEEKPLAN);
        return jdglWeekPlanList;
    }

    @Transactional
    public int insertJdglWeekPlan(JdglWeekPlan jdglWeekPlan) {
        String week = jdglWeekPlan.getWeek();
        String year = jdglWeekPlan.getYear();

        JdglWeekPlan queryExist = new JdglWeekPlan();
        queryExist.setYear(year);
        queryExist.setWeek(week);
        queryExist.setVersion(jdglWeekPlan.getVersion());
        List<JdglWeekPlan> jdglWeekPlanList = jdglWeekPlanMapper.getJdglWeekPlanList(queryExist);
        if(!CollectionUtils.isEmpty(jdglWeekPlanList)) {
            throw new RuntimeException("已存在"+year+"年第"+week+"周数据!");
        }

        Long id = IdWorker.createId();
        jdglWeekPlan.setId(id);
        jdglWeekPlan.setCreateUser(SecurityUtils.getSysUser().getNickName());
        jdglWeekPlan.setCreateTime(DateUtils.getNowDate());
        jdglWeekPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglWeekPlan.setUpdateTime(DateUtils.getNowDate());
        jdglWeekPlan.setIsUse("0");
        int i = jdglWeekPlanMapper.insertJdglWeekPlan(jdglWeekPlan);

        List<JdglWeekImagePlan> jdglWeekImagePlanList = jdglWeekPlan.getJdglWeekImagePlanList();
        if(!CollectionUtils.isEmpty(jdglWeekImagePlanList)) {
            List<JdglWeekImagePlan> imagePlans = TreeUtil.treeToList(jdglWeekImagePlanList);
            imagePlans.forEach(vo -> {
                vo.setPlanId(id);
            });
            iJdglWeekImagePlanService.insertJdglWeekImagePlanList(imagePlans);
        }

        return i;
    }

    @Transactional
    public int insertJdglWeekPlanList(List<JdglWeekPlan> jdglWeekPlanList) {
        for (JdglWeekPlan jdglWeekPlan : jdglWeekPlanList) {
            jdglWeekPlan.setId(IdWorker.createId());
            jdglWeekPlan.setCreateUser(SecurityUtils.getUserName());
            jdglWeekPlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglWeekPlanMapper.insertJdglWeekPlanList(jdglWeekPlanList);
    }

    @Transactional
    public int updateJdglWeekPlan(JdglWeekPlan jdglWeekPlan) {
        Long id = jdglWeekPlan.getId();
        String week = jdglWeekPlan.getWeek();
        String year = jdglWeekPlan.getYear();

        JdglWeekPlan queryExist = new JdglWeekPlan();
        queryExist.setYear(year);
        queryExist.setWeek(week);
        List<JdglWeekPlan> jdglWeekPlanList = jdglWeekPlanMapper.getJdglWeekPlanList(queryExist);
        if(!CollectionUtils.isEmpty(jdglWeekPlanList)) {
            JdglWeekPlan jdglWeekPlan1 = jdglWeekPlanList.stream().filter(vo -> id.equals(vo.getId())).findFirst().orElse(null);
            if(jdglWeekPlan1 == null) {
                throw new RuntimeException("已存在"+year+"年第"+week+"周数据!");
            }
        }
        jdglWeekPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglWeekPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglWeekValuePlanService.updateJdglWeekValuePlanList(jdglWeekPlan.getJdglWeekValuePlanList());
        List<JdglWeekImagePlan> jdglWeekImagePlanList = jdglWeekPlan.getJdglWeekImagePlanList();
        if(!CollectionUtils.isEmpty(jdglWeekImagePlanList)) {
            List<JdglWeekImagePlan> jdglWeekImagePlans = TreeUtil.treeToListWithoutId(jdglWeekImagePlanList);
            for (JdglWeekImagePlan jdglWeekImagePlan: jdglWeekImagePlans) {
                jdglWeekImagePlan.setPlanId(jdglWeekPlan.getId());
            }
            iJdglWeekImagePlanService.updateJdglWeekImagePlanList(jdglWeekImagePlans);
        }
        BigDecimal thisPlanAmt = iJdglWeekImagePlanService.getThisPlanAmt(id);
        BigDecimal exchangeRate = jdglWeekPlan.getExchangeRate();
        jdglWeekPlan.setThisPlanValueCu(thisPlanAmt);
        jdglWeekPlan.setThisPlanValueDl(thisPlanAmt == null || exchangeRate == null ? thisPlanAmt : thisPlanAmt.multiply(exchangeRate));

        return jdglWeekPlanMapper.updateJdglWeekPlan(jdglWeekPlan);
    }

    @Transactional
    public int updateJdglWeekPlanList(List<JdglWeekPlan> jdglWeekPlanList) {
        for (JdglWeekPlan jdglWeekPlan : jdglWeekPlanList) {
            jdglWeekPlan.setUpdateUser(SecurityUtils.getUserName());
            jdglWeekPlan.setUpdateTime(DateUtils.getNowDate());
//            iJdglWeekValuePlanService.updateJdglWeekValuePlanList(jdglWeekPlan.getJdglWeekValuePlanList());
            iJdglWeekImagePlanService.updateJdglWeekImagePlanList(jdglWeekPlan.getJdglWeekImagePlanList());
        }
        return jdglWeekPlanMapper.updateJdglWeekPlanList(jdglWeekPlanList);
    }

    @Transactional
    public int deleteJdglWeekPlan(JdglWeekPlan jdglWeekPlan) {
        jdglWeekPlan.setUpdateUser(SecurityUtils.getUserName());
        jdglWeekPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglWeekValuePlanService.deleteJdglWeekValuePlanByWeekPlanId(jdglWeekPlan.getId());
        iJdglWeekImagePlanService.deleteJdglWeekImagePlanByPlanId(jdglWeekPlan.getId());
        return jdglWeekPlanMapper.deleteJdglWeekPlan(jdglWeekPlan);
    }

    @Transactional
    public int deleteJdglWeekPlanByPks(List<Long> jdglWeekPlanPkList) {
        for (Long id : jdglWeekPlanPkList) {
//            iJdglWeekValuePlanService.deleteJdglWeekValuePlanByWeekPlanId(id);
            iJdglWeekImagePlanService.deleteJdglWeekImagePlanByPlanId(id);
        }
        return jdglWeekPlanMapper.deleteJdglWeekPlanByPks(jdglWeekPlanPkList);
    }


}
