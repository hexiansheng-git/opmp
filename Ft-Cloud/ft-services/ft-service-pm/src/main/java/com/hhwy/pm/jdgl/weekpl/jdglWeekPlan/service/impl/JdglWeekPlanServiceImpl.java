package com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.domain.JdglWeekImagePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekImagePlan.service.IJdglWeekImagePlanService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.mapper.JdglWeekPlanMapper;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.service.IJdglWeekPlanService;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.domain.JdglWeekValuePlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekValuePlan.service.IJdglWeekValuePlanService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private IJdglWeekValuePlanService iJdglWeekValuePlanService;

    @Autowired
    private IJdglWeekImagePlanService iJdglWeekImagePlanService;

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

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

        if(xmslContractInfo != null) {
            returnVO.setProjectName(xmslContractInfo.getProjectName());
            returnVO.setContactAmtCu(xmslContractInfo.getEffectiveAmout());
            returnVO.setRemainMonthAmtDl(xmslContractInfo.getEffectiveAmout());
            returnVO.setCustUnit(xmslContractInfo.getListCurrencyName());
        }

        // 获取财务管理-风险管理-汇率登记

        // 根据期次获取开累产值数据
        String year = jdglWeekPlanParam.getWeek();

        // 计算合同、产值数据

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
        jdglWeekPlan.setCreateUser(SecurityUtils.getUserName());
        jdglWeekPlan.setCreateTime(DateUtils.getNowDate());
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
        jdglWeekPlan.setUpdateUser(SecurityUtils.getUserName());
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
