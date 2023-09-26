package com.hhwy.pm.jdgl.yearpl.jdglYearPlan.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
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
import com.hhwy.utils.tree.TreeUtil;
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
            FlowInfoSearchUtil.getFlowInfo(jdglYearPlan1,FlowEnum.JDGL_YEARPLAN);
        }
        return jdglYearPlan1;
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

//        JdglYearPlan returnVO = new JdglYearPlan();

        String year1 = jdglYearPlanParam.getYear();
        if(StringUtils.isEmpty(year1)) {
            throw new RuntimeException("参数传入异常!");
        }
        Date nowDate = DateUtils.getNowDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String nowStr = sdf.format(nowDate);
        jdglYearPlanParam.setVersion("V1.0");

        // 获取项目及合同信息
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(new XmslContractInfo());

        ProjectBasicInfo projectBasicInfo = xmslProjectBasicInfoService.projectInfo();

        if(projectBasicInfo != null) {

//            returnVO.setCustUnit(projectBasicInfo.getContractCurrency());
//            returnVO.setCustUnitCode(projectBasicInfo.getContractCurrencyCode());
        }

        if(xmslContractInfo != null) {
            jdglYearPlanParam.setCustUnit(xmslContractInfo.getListCurrencyName());
            jdglYearPlanParam.setCustUnitCode(xmslContractInfo.getListCurrencyCode());
            jdglYearPlanParam.setProjectName(xmslContractInfo.getProjectName());
            jdglYearPlanParam.setContactAmtCu(xmslContractInfo.getEffectiveAmout());

            // 获取财务管理-风险管理-汇率登记
            List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractInfo.getXmslContractPayinfoList();
            if(!CollectionUtils.isEmpty(xmslContractPayinfoList) && jdglYearPlanParam.getCustUnitCode() != null) {
                XmslContractPayinfo xmslContractPayinfo = xmslContractPayinfoList.stream().filter(vo -> jdglYearPlanParam.getCustUnitCode().equals(vo.getCurrencyCode())).findFirst().orElse(null);
                if(xmslContractPayinfo != null && "1".equals(xmslContractPayinfo.getRateType())) {
                    jdglYearPlanParam.setExchangeRate(new BigDecimal(xmslContractPayinfo.getObversionRate()));
                }
            }
        }

        if(jdglYearPlanParam.getExchangeRate() == null) {
            PeriodInfo periodInfo = new PeriodInfo();
            periodInfo.setCurrencyCode(jdglYearPlanParam.getCustUnitCode());
            periodInfo.setQueryDate(year1);
            AjaxResult ajaxResult = systemServiceApi.selectPeriodByYear(periodInfo);
            if(ajaxResult.get("data") != null) {
                List<Map> data = (List<Map>) ajaxResult.get("data");
                Map periodMap = data.stream().filter(map -> nowStr.equals(map.get("periodCode"))).findFirst().orElse(null);
                if(periodMap != null && periodMap.get("rate") != null) {
                    jdglYearPlanParam.setExchangeRate(new BigDecimal((double)periodMap.get("rate")));
                }
            }
        }

        // 根据期次获取开累产值数据
        Map<String, Date> dateRange4Year = StatisticsUtils.getDateRange4Year(year1);
        Date start = dateRange4Year.get("start");

        // 计算合同、产值数据
        BigDecimal countValue = jdglDayScheduleService.getCountValue(null, start);

        jdglYearPlanParam.setTotalCompValueCu(countValue);
        if(jdglYearPlanParam.getTotalCompValueCu() == null) jdglYearPlanParam.setTotalCompValueCu(new BigDecimal(0));
        if(jdglYearPlanParam.getContactAmtCu()== null) jdglYearPlanParam.setContactAmtCu(new BigDecimal(0));
        jdglYearPlanParam.setRemainContactAmtCu(jdglYearPlanParam.getContactAmtCu().subtract(jdglYearPlanParam.getTotalCompValueCu()));

        return jdglYearPlanParam;
    }

    /**
     * 调整版本&未完&
     * @param jdglYearPlan
     * @return
     */
    @Override
    public JdglYearPlan adjust(JdglYearPlan jdglYearPlan) {
//        int i = 0;

        JdglYearPlan jdglYearPlanParam = getJdglYearPlan(jdglYearPlan);
        if(jdglYearPlanParam != null) {
//            Long id = IdWorker.createId();
//            jdglYearPlanParam.setId(id);
            jdglYearPlanParam.setCreateUser(SecurityUtils.getSysUser().getNickName());
            jdglYearPlanParam.setCreateTime(DateUtils.getNowDate());
            jdglYearPlanParam.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            jdglYearPlanParam.setUpdateTime(DateUtils.getNowDate());
            String version = jdglYearPlanParam.getVersion();
            if(StringUtils.isNotEmpty(version)) {
                String v = version.replace("V", "");
                String replace = v.replace(".0", "");
                jdglYearPlanParam.setVersion("V" + (Integer.valueOf(replace)+1) + ".0");
            }
            jdglYearPlanParam.setTaskStatus("0");
            jdglYearPlanParam.setIsUse("0");

//            i = jdglYearPlanMapper.insertJdglYearPlan(jdglYearPlanParam);

//            List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearPlanParam.getJdglYearImagePlanList();
//            if(!CollectionUtils.isEmpty(jdglYearImagePlanList)) {
//                for (JdglYearImagePlan jdglYearImagePlan : jdglYearImagePlanList) {
//                    jdglYearImagePlan.setYearPlanId(id);
//                }
//                iJdglYearImagePlanService.insertJdglYearImagePlanList(jdglYearImagePlanList);
//            }
        }

        return jdglYearPlanParam;
    }

    @Override
    public void updateTaskStatus(Long id) {
        JdglYearPlan queryThis = new JdglYearPlan();
        queryThis.setId(id);
        JdglYearPlan jdglYearPlan = jdglYearPlanMapper.getJdglYearPlan(queryThis);
        if(jdglYearPlan != null) {
            JdglYearPlan usingYearPlanByYear = getUsingYearPlanByYear(jdglYearPlan.getYear());
            if(usingYearPlanByYear != null) {
                usingYearPlanByYear.setIsUse("-1");
                jdglYearPlanMapper.updateJdglYearPlan(usingYearPlanByYear);
            }
            jdglYearPlan.setTaskStatus("5");
            jdglYearPlan.setIsUse("1");
            jdglYearPlanMapper.updateJdglYearPlan(jdglYearPlan);
        }

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
        queryExist.setVersion(jdglYearPlan.getVersion());
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
        jdglYearPlan.setIsUse("0");

        int i = jdglYearPlanMapper.insertJdglYearPlan(jdglYearPlan);

        List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearPlan.getJdglYearImagePlanList();
        if(!CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            List<JdglYearImagePlan> jdglYearImagePlans = TreeUtil.treeToList(jdglYearImagePlanList);
            jdglYearImagePlans.forEach(vo -> {
                vo.setYearPlanId(id);
            });
            iJdglYearImagePlanService.insertJdglYearImagePlanList(jdglYearImagePlans);
        }

        return i;
    }

    @Transactional
    public int insertJdglYearPlanList(List<JdglYearPlan> jdglYearPlanList) {
        for (JdglYearPlan jdglYearPlan : jdglYearPlanList) {
            jdglYearPlan.setId(IdWorker.createId());
            jdglYearPlan.setCreateUser(SecurityUtils.getUserName());
            jdglYearPlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglYearPlanMapper.insertJdglYearPlanList(jdglYearPlanList);
    }

    @Transactional
    public int updateJdglYearPlan(JdglYearPlan jdglYearPlan) {
        Long id = jdglYearPlan.getId();
        String year = jdglYearPlan.getYear();
        JdglYearPlan queryExist = new JdglYearPlan();
        queryExist.setYear(year);
        List<JdglYearPlan> jdglYearPlanList = jdglYearPlanMapper.getJdglYearPlanList(queryExist);
        if(!CollectionUtils.isEmpty(jdglYearPlanList)) {
            JdglYearPlan jdglYearPlan1 = jdglYearPlanList.stream().filter(vo -> id.equals(vo.getId())).findFirst().orElse(null);
            if(jdglYearPlan1 == null) {
                throw new RuntimeException("已存在"+year+"年数据!");
            }
        }
        jdglYearPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        jdglYearPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglYearValuePlanService.updateJdglYearValuePlanList(jdglYearPlan.getJdglYearValuePlanList());
        List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearPlan.getJdglYearImagePlanList();
        if(!CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            List<JdglYearImagePlan> jdglYearImagePlans = TreeUtil.treeToListWithoutId(jdglYearImagePlanList);
            for (JdglYearImagePlan jdglYearImagePlan: jdglYearImagePlans) {
                jdglYearImagePlan.setYearPlanId(jdglYearPlan.getId());
            }
            iJdglYearImagePlanService.updateJdglYearImagePlanList(jdglYearImagePlans);
        }

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
