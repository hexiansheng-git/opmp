package com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
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
        }
        return jdglWeekPlanMapper.getJdglWeekPlan(jdglWeekPlan);
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

        JdglWeekPlan returnVO = new JdglWeekPlan();

        // 获取项目及合同信息
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(new XmslContractInfo());

        ProjectBasicInfo projectBasicInfo = xmslProjectBasicInfoService.projectInfo();

        String year1 = jdglWeekPlanParam.getYear();
        Date nowDate = DateUtils.getNowDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String nowStr = sdf.format(nowDate);

        if(projectBasicInfo != null) {
            returnVO.setCustUnit(projectBasicInfo.getContractCurrency());
            returnVO.setCustUnitCode(projectBasicInfo.getContractCurrencyCode());
        }

        if(xmslContractInfo != null) {
            returnVO.setProjectName(xmslContractInfo.getProjectName());
            returnVO.setContactAmtCu(xmslContractInfo.getEffectiveAmout());
            returnVO.setRemainMonthAmtDl(xmslContractInfo.getEffectiveAmout());

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
        String week = jdglWeekPlanParam.getWeek();

        Calendar cl = Calendar.getInstance();
        cl.setWeekDate(Integer.valueOf(year1), Integer.valueOf(week), 1);

        String month = cl.get(Calendar.MONTH + 1) + "";

        JdglMonthPlan usingMonthPlanByYearAndMonth = jdglMonthPlanService.getUsingMonthPlanByYearAndMonth(year1, month);

        if (usingMonthPlanByYearAndMonth != null) {
            returnVO.setMonthPlanValueDl(usingMonthPlanByYearAndMonth.getThisPlanValueDl());
        }

        // 计算合同、产值数据
        Map<String, Date> dateRange4YearMonth = StatisticsUtils.getDateRange4YearMonth(year1, month);

        Date startW = cl.getTime();
        Date startM = dateRange4YearMonth.get("start");

        BigDecimal countValue = jdglDayScheduleService.getCountValue(startM, startW);
        returnVO.setMonthCompValueDl(countValue);

        if(returnVO.getMonthPlanValueDl() == null) returnVO.setMonthPlanValueDl(new BigDecimal(0));
        if(returnVO.getMonthCompValueDl()== null) returnVO.setMonthCompValueDl(new BigDecimal(0));
        returnVO.setRemainMonthAmtDl(returnVO.getMonthPlanValueDl().subtract(returnVO.getMonthCompValueDl()));

        return returnVO;
    }

    /**
     * 调整版本&未完&
     * @param jdglWeekPlanParam
     * @return
     */
    @Override
    public int adjust(JdglWeekPlan jdglWeekPlanParam) {
        int i = 0;

        if(jdglWeekPlanParam != null) {
            Long id = IdWorker.createId();
            jdglWeekPlanParam.setId(id);
            jdglWeekPlanParam.setCreateUser(SecurityUtils.getUserName());
            jdglWeekPlanParam.setCreateTime(DateUtils.getNowDate());
            jdglWeekPlanParam.setVersion(Integer.parseInt(jdglWeekPlanParam.getVersion()) + 1 + "");
            jdglWeekPlanParam.setTaskStatus("0");
            jdglWeekPlanParam.setIsUse("0");

            i = jdglWeekPlanMapper.insertJdglWeekPlan(jdglWeekPlanParam);

            List<JdglWeekImagePlan> jdglWeekImagePlanList = jdglWeekPlanParam.getJdglWeekImagePlanList();
            if(!CollectionUtils.isEmpty(jdglWeekImagePlanList)) {
                for (JdglWeekImagePlan jdglWeekImagePlan : jdglWeekImagePlanList) {
                    jdglWeekImagePlan.setPlanId(id);
                }
                iJdglWeekImagePlanService.insertJdglWeekImagePlanList(jdglWeekImagePlanList);
            }
        }

        return i;
    }

    @Override
    public void updateTaskStatus(Long id) {
        JdglWeekPlan queryThis = new JdglWeekPlan();
        queryThis.setId(id);
        JdglWeekPlan jdglWeekPlan = jdglWeekPlanMapper.getJdglWeekPlan(queryThis);
        if(jdglWeekPlan != null) {
            JdglWeekPlan usingWeekPlanByYearAndWeek = getUsingWeekPlanByYearAndWeek(jdglWeekPlan.getYear(), jdglWeekPlan.getWeek());
            if(usingWeekPlanByYearAndWeek != null) {
                usingWeekPlanByYearAndWeek.setIsUse("-1");
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
        jdglWeekPlan.setVersion("1");
        jdglWeekPlan.setIsUse("0");
        return jdglWeekPlanMapper.insertJdglWeekPlan(jdglWeekPlan);
    }

    @Transactional
    public int insertJdglWeekPlanList(List<JdglWeekPlan> jdglWeekPlanList) {
        for (JdglWeekPlan jdglWeekPlan : jdglWeekPlanList) {
            jdglWeekPlan.setId(IdWorker.createId());
            jdglWeekPlan.setCreateUser(SecurityUtils.getUserName());
            jdglWeekPlan.setCreateTime(DateUtils.getNowDate());
            jdglWeekPlan.setVersion("1");
        }
        return jdglWeekPlanMapper.insertJdglWeekPlanList(jdglWeekPlanList);
    }

    @Transactional
    public int updateJdglWeekPlan(JdglWeekPlan jdglWeekPlan) {
        jdglWeekPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglWeekPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglWeekValuePlanService.updateJdglWeekValuePlanList(jdglWeekPlan.getJdglWeekValuePlanList());
        List<JdglWeekImagePlan> jdglWeekImagePlanList = jdglWeekPlan.getJdglWeekImagePlanList();
        if(!CollectionUtils.isEmpty(jdglWeekImagePlanList)) {
            for (JdglWeekImagePlan jdglWeekImagePlan: jdglWeekImagePlanList) {
                jdglWeekImagePlan.setPlanId(jdglWeekPlan.getId());
            }
        }
        iJdglWeekImagePlanService.updateJdglWeekImagePlanList(jdglWeekImagePlanList);

        // 根据计划完成产值汇总更新年计划产值&未完&

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
