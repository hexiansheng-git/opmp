package com.hhwy.pm.jdgl.yearpl.jdglYearPlan.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.IJdglYearImagePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.domain.JdglYearValuePlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearValuePlan.service.IJdglYearValuePlanService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
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

//    @Autowired
//    private IPeriodCurrencyService periodCurrencyService;

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

    /**
     * 获取初始数据&未完&
     * @param jdglYearPlanParam
     * @return
     */
    @Override
    public JdglYearPlan getInitJdglYearPlan(JdglYearPlan jdglYearPlanParam) {

        JdglYearPlan returnVO = new JdglYearPlan();

        // 获取项目及合同信息
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(new XmslContractInfo());

        if(xmslContractInfo != null) {
            returnVO.setProjectName(xmslContractInfo.getProjectName());
            returnVO.setContactAmtCu(xmslContractInfo.getEffectiveAmout());
            returnVO.setRemainContactAmtCu(xmslContractInfo.getEffectiveAmout());
            returnVO.setCustUnit(xmslContractInfo.getListCurrencyName());
        }

        // 获取财务管理-风险管理-汇率登记

        // 根据期次获取开累产值数据
        String year = jdglYearPlanParam.getYear();

        // 计算合同、产值数据

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
        if(!CollectionUtils.isEmpty(jdglYearPlanList)) {
            for (JdglYearPlan jdglYearPlan1 : jdglYearPlanList) {
                List<JdglYearImagePlan> jdglYearImagePlanListByYearPlanId = iJdglYearImagePlanService.getJdglYearImagePlanListByYearPlanId(jdglYearPlan1.getId());
                jdglYearPlan1.setJdglYearImagePlanList(jdglYearImagePlanListByYearPlanId);
                List<JdglYearValuePlan> jdglYearValuePlanListByYearPlanId = iJdglYearValuePlanService.getJdglYearValuePlanListByYearPlanId(jdglYearPlan1.getId());
                jdglYearPlan1.setJdglYearValuePlanList(jdglYearValuePlanListByYearPlanId);
            }
        }
        return jdglYearPlanList;
    }

    @Transactional
    public int insertJdglYearPlan(JdglYearPlan jdglYearPlan) {
        Long id = IdWorker.createId();
        jdglYearPlan.setId(id);
        jdglYearPlan.setCreateUser(SecurityUtils.getUserName());
        jdglYearPlan.setCreateTime(DateUtils.getNowDate());
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
        jdglYearPlan.setUpdateUser(SecurityUtils.getUserName());
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
