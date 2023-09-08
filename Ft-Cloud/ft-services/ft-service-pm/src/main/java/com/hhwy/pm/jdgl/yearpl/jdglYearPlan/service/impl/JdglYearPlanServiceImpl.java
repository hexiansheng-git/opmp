package com.hhwy.pm.jdgl.yearpl.jdglYearPlan.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.common.domain.FtActBusiness;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.IJdglYearImagePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.domain.JdglYearValuePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.service.IJdglYearValuePlanService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.mapper.JdglYearPlanMapper;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.service.IJdglYearPlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:04
 * @remark
 */
@Service
public class JdglYearPlanServiceImpl implements IJdglYearPlanService {

    @Autowired
    private JdglYearPlanMapper jdglYearPlanMapper;

    @Autowired
    private IJdglYearValuePlanService iJdglYearValuePlanService;

    @Autowired
    private IJdglYearImagePlanService iJdglYearImagePlanService;

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private IJdglDayScheduleService jdglDayScheduleService;

    public JdglYearPlan getJdglYearPlan(JdglYearPlan jdglYearPlan) {
        JdglYearPlan jdglYearPlan1 = jdglYearPlanMapper.getJdglYearPlan(jdglYearPlan);
        if(jdglYearPlan1 != null) {
            List<JdglYearValuePlan> jdglYearValuePlanListByYearPlanId = iJdglYearValuePlanService.getJdglYearValuePlanListByYearPlanId(jdglYearPlan1.getId());
            jdglYearPlan1.setJdglYearValuePlanList(jdglYearValuePlanListByYearPlanId);
            List<JdglYearImagePlan> jdglYearImagePlanListByYearPlanId = iJdglYearImagePlanService.getJdglYearImagePlanListByYearPlanId(jdglYearPlan1.getId());
            jdglYearPlan1.setJdglYearImagePlanList(jdglYearImagePlanListByYearPlanId);
        }
        return jdglYearPlanMapper.getJdglYearPlan(jdglYearPlan);
    }

    @Override
    public JdglYearPlan getUsingYearPlanByYear(String year) {
        JdglYearPlan jdglYearPlan = new JdglYearPlan();
        jdglYearPlan.setYear(year);
        jdglYearPlan.setTaskStatus("5");
        jdglYearPlan.setIsUse("1");
        return getJdglYearPlan(jdglYearPlan);
    }

    /**
     * 获取初始数据&未完&
     * @param jdglYearPlanParam
     * @return
     */
    @Override
    public JdglYearPlan getInitJdglYearPlan(JdglYearPlan jdglYearPlanParam) {

        JdglYearPlan returnVO = new JdglYearPlan();

        String year1 = jdglYearPlanParam.getYear();
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
        Map<String, Date> dateRange4Year = StatisticsUtils.getDateRange4Year(year1);
        Date start = dateRange4Year.get("start");

        // 计算合同、产值数据
        BigDecimal countValue = jdglDayScheduleService.getCountValue(null, start);

        returnVO.setTotalCompValueCu(countValue);
        if(returnVO.getTotalCompValueCu() == null) returnVO.setTotalCompValueCu(new BigDecimal(0));
        if(returnVO.getContactAmtCu()== null) returnVO.setContactAmtCu(new BigDecimal(0));
        returnVO.setRemainContactAmtCu(returnVO.getContactAmtCu().subtract(returnVO.getTotalCompValueCu()));

        return returnVO;
    }

    /**
     * 调整版本&未完&
     * @param jdglYearPlanParam
     * @return
     */
    @Override
    public int adjust(JdglYearPlan jdglYearPlanParam) {
        int i = 0;

        if(jdglYearPlanParam != null) {
            Long id = IdWorker.createId();
            jdglYearPlanParam.setId(id);
            jdglYearPlanParam.setCreateUser(SecurityUtils.getUserName());
            jdglYearPlanParam.setCreateTime(DateUtils.getNowDate());
            jdglYearPlanParam.setVersion(Integer.parseInt(jdglYearPlanParam.getVersion()) + 1 + "");
            jdglYearPlanParam.setTaskStatus("0");
            jdglYearPlanParam.setIsUse("0");

            i = jdglYearPlanMapper.insertJdglYearPlan(jdglYearPlanParam);

            List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearPlanParam.getJdglYearImagePlanList();
            if(!CollectionUtils.isEmpty(jdglYearImagePlanList)) {
                for (JdglYearImagePlan jdglYearImagePlan : jdglYearImagePlanList) {
                    jdglYearImagePlan.setYearPlanId(id);
                }
                iJdglYearImagePlanService.insertJdglYearImagePlanList(jdglYearImagePlanList);
            }
        }

        return i;
    }

    public List<JdglYearPlan> getJdglYearPlanList(JdglYearPlan jdglYearPlan) {
        List<JdglYearPlan> jdglYearPlanList = jdglYearPlanMapper.getJdglYearPlanList(jdglYearPlan);
        String tenantKey = SecurityUtils.getTenantKey();
        if(!CollectionUtils.isEmpty(jdglYearPlanList)) {
            for (JdglYearPlan jdglYearPlan1 : jdglYearPlanList) {
                List<JdglYearImagePlan> jdglYearImagePlanListByYearPlanId = iJdglYearImagePlanService.getJdglYearImagePlanListByYearPlanId(jdglYearPlan1.getId());
                jdglYearPlan1.setJdglYearImagePlanList(jdglYearImagePlanListByYearPlanId);
                List<JdglYearValuePlan> jdglYearValuePlanListByYearPlanId = iJdglYearValuePlanService.getJdglYearValuePlanListByYearPlanId(jdglYearPlan1.getId());
                jdglYearPlan1.setJdglYearValuePlanList(jdglYearValuePlanListByYearPlanId);
            }
        }
        FlowInfoSearchUtil.getFlowInfo(jdglYearPlanList,FlowEnum.JDGL_YEARPLAN);
        return jdglYearPlanList;
    }

    @Transactional
    public int insertJdglYearPlan(JdglYearPlan jdglYearPlan) {

        String year = jdglYearPlan.getYear();
        JdglYearPlan queryExist = new JdglYearPlan();
        queryExist.setYear(year);
        List<JdglYearPlan> jdglYearPlanList = jdglYearPlanMapper.getJdglYearPlanList(queryExist);
        if(!CollectionUtils.isEmpty(jdglYearPlanList)) {
            throw new RuntimeException("已存在"+year+"年数据!");
        }

        Long id = IdWorker.createId();
        jdglYearPlan.setId(id);
        jdglYearPlan.setCreateUser(SecurityUtils.getSysUser().getNickName());
        jdglYearPlan.setCreateTime(DateUtils.getNowDate());

        jdglYearPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglYearPlan.setUpdateTime(DateUtils.getNowDate());
        jdglYearPlan.setVersion("1");
        jdglYearPlan.setIsUse("0");
        return jdglYearPlanMapper.insertJdglYearPlan(jdglYearPlan);
    }

    @Transactional
    public int insertJdglYearPlanList(List<JdglYearPlan> jdglYearPlanList) {
        for (JdglYearPlan jdglYearPlan : jdglYearPlanList) {
            jdglYearPlan.setId(IdWorker.createId());
            jdglYearPlan.setCreateUser(SecurityUtils.getUserName());
            jdglYearPlan.setCreateTime(DateUtils.getNowDate());
            jdglYearPlan.setVersion("1");
        }
        return jdglYearPlanMapper.insertJdglYearPlanList(jdglYearPlanList);
    }

    @Transactional
    public int updateJdglYearPlan(JdglYearPlan jdglYearPlan) {
        jdglYearPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglYearPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglYearValuePlanService.updateJdglYearValuePlanList(jdglYearPlan.getJdglYearValuePlanList());
        List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearPlan.getJdglYearImagePlanList();
        if(!CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            for (JdglYearImagePlan jdglYearImagePlan: jdglYearImagePlanList) {
                jdglYearImagePlan.setYearPlanId(jdglYearPlan.getId());
            }
        }
        iJdglYearImagePlanService.updateJdglYearImagePlanList(jdglYearImagePlanList);

        // 根据计划完成产值汇总更新年计划产值&未完&

        return jdglYearPlanMapper.updateJdglYearPlan(jdglYearPlan);
    }

    @Transactional
    public int updateJdglYearPlanList(List<JdglYearPlan> jdglYearPlanList) {
        for (JdglYearPlan jdglYearPlan : jdglYearPlanList) {
            jdglYearPlan.setUpdateUser(SecurityUtils.getUserName());
            jdglYearPlan.setUpdateTime(DateUtils.getNowDate());
//            iJdglYearValuePlanService.updateJdglYearValuePlanList(jdglYearPlan.getJdglYearValuePlanList());
            iJdglYearImagePlanService.updateJdglYearImagePlanList(jdglYearPlan.getJdglYearImagePlanList());
        }
        return jdglYearPlanMapper.updateJdglYearPlanList(jdglYearPlanList);
    }

    @Transactional
    public int deleteJdglYearPlan(JdglYearPlan jdglYearPlan) {
        jdglYearPlan.setUpdateUser(SecurityUtils.getUserName());
        jdglYearPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglYearValuePlanService.deleteJdglYearValuePlanByYearPlanId(jdglYearPlan.getId());
        iJdglYearImagePlanService.deleteJdglYearImagePlanByYearPlanId(jdglYearPlan.getId());
        return jdglYearPlanMapper.deleteJdglYearPlan(jdglYearPlan);
    }

    @Transactional
    public int deleteJdglYearPlanByPks(List<Long> jdglYearPlanPkList) {
        for (Long id : jdglYearPlanPkList) {
//            iJdglYearValuePlanService.deleteJdglYearValuePlanByYearPlanId(id);
            iJdglYearImagePlanService.deleteJdglYearImagePlanByYearPlanId(id);
        }
        return jdglYearPlanMapper.deleteJdglYearPlanByPks(jdglYearPlanPkList);
    }


}
