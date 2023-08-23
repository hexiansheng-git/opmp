package com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.domain.JdglQuarterImagePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterImagePlan.service.IJdglQuarterImagePlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.mapper.JdglQuarterPlanMapper;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.service.IJdglQuarterPlanService;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.domain.JdglQuarterValuePlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterValuePlan.service.IJdglQuarterValuePlanService;
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
public class JdglQuarterPlanServiceImpl implements IJdglQuarterPlanService {

    @Autowired
    private JdglQuarterPlanMapper jdglQuarterPlanMapper;

    @Autowired
    private IJdglQuarterValuePlanService iJdglQuarterValuePlanService;

    @Autowired
    private IJdglQuarterImagePlanService iJdglQuarterImagePlanService;

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

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

    /**
     * 获取初始数据&未完&
     * @param jdglQuarterPlanParam
     * @return
     */
    @Override
    public JdglQuarterPlan getInitJdglQuarterPlan(JdglQuarterPlan jdglQuarterPlanParam) {

        JdglQuarterPlan returnVO = new JdglQuarterPlan();

        // 获取项目及合同信息
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(new XmslContractInfo());

        if(xmslContractInfo != null) {
            returnVO.setProjectName(xmslContractInfo.getProjectName());
            returnVO.setContactAmtCu(xmslContractInfo.getEffectiveAmout());
            returnVO.setRemainYearAmtDl(xmslContractInfo.getEffectiveAmout());
            returnVO.setCustUnit(xmslContractInfo.getListCurrencyName());
        }

        // 获取财务管理-风险管理-汇率登记

        // 根据期次获取开累产值数据
        String quarter = jdglQuarterPlanParam.getQuarter();

        // 计算合同、产值数据

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
        if(!CollectionUtils.isEmpty(jdglQuarterPlanList)) {
            for (JdglQuarterPlan jdglQuarterPlan1 : jdglQuarterPlanList) {
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
        Long id = IdWorker.createId();
        jdglQuarterPlan.setId(id);
        jdglQuarterPlan.setCreateUser(SecurityUtils.getUserName());
        jdglQuarterPlan.setCreateTime(DateUtils.getNowDate());
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
        jdglQuarterPlan.setUpdateUser(SecurityUtils.getUserName());
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
