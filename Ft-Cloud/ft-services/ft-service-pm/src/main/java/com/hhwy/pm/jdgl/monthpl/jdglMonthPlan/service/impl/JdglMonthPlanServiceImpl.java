package com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.domain.JdglMonthImagePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthImagePlan.service.IJdglMonthImagePlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.mapper.JdglMonthPlanMapper;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.service.IJdglMonthPlanService;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.domain.JdglMonthValuePlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthValuePlan.service.IJdglMonthValuePlanService;
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
public class JdglMonthPlanServiceImpl implements IJdglMonthPlanService {

    @Autowired
    private JdglMonthPlanMapper jdglMonthPlanMapper;

    @Autowired
    private IJdglMonthValuePlanService iJdglMonthValuePlanService;

    @Autowired
    private IJdglMonthImagePlanService iJdglMonthImagePlanService;

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;

//    @Autowired
//    private IPeriodCurrencyService periodCurrencyService;

    public JdglMonthPlan getJdglMonthPlan(JdglMonthPlan jdglMonthPlan) {
        JdglMonthPlan jdglMonthPlan1 = jdglMonthPlanMapper.getJdglMonthPlan(jdglMonthPlan);
        if(jdglMonthPlan1 != null) {
            List<JdglMonthValuePlan> jdglMonthValuePlanListByPlanId = iJdglMonthValuePlanService.getJdglMonthValuePlanListByPlanId(jdglMonthPlan1.getId());
            jdglMonthPlan1.setJdglMonthValuePlanList(jdglMonthValuePlanListByPlanId);
            List<JdglMonthImagePlan> jdglMonthImagePlanListByPlanId = iJdglMonthImagePlanService.getJdglMonthImagePlanListByPlanId(jdglMonthPlan1.getId());
            jdglMonthPlan1.setJdglMonthImagePlanList(jdglMonthImagePlanListByPlanId);
        }
        return jdglMonthPlanMapper.getJdglMonthPlan(jdglMonthPlan);
    }

    /**
     * 获取初始数据&未完&
     * @param jdglMonthPlanParam
     * @return
     */
    @Override
    public JdglMonthPlan getInitJdglMonthPlan(JdglMonthPlan jdglMonthPlanParam) {

        JdglMonthPlan returnVO = new JdglMonthPlan();

        // 获取项目及合同信息
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(new XmslContractInfo());

        if(xmslContractInfo != null) {
            returnVO.setProjectName(xmslContractInfo.getProjectName());
            returnVO.setContactAmtCu(xmslContractInfo.getEffectiveAmout());
            returnVO.setRemainQuarterAmtDl(xmslContractInfo.getEffectiveAmout());
            returnVO.setCustUnit(xmslContractInfo.getListCurrencyName());
        }

        // 获取财务管理-风险管理-汇率登记

        // 根据期次获取开累产值数据
        String year = jdglMonthPlanParam.getMonth();

        // 计算合同、产值数据

        return returnVO;
    }

    /**
     * 调整版本&未完&
     * @param jdglMonthPlanParam
     * @return
     */
    @Override
    public int adjust(JdglMonthPlan jdglMonthPlanParam) {
        int i = 0;

        if(jdglMonthPlanParam != null) {
            Long id = IdWorker.createId();
            jdglMonthPlanParam.setId(id);
            jdglMonthPlanParam.setCreateUser(SecurityUtils.getUserName());
            jdglMonthPlanParam.setCreateTime(DateUtils.getNowDate());
            jdglMonthPlanParam.setVersion(Integer.parseInt(jdglMonthPlanParam.getVersion()) + 1 + "");
            jdglMonthPlanParam.setTaskStatus("0");
            jdglMonthPlanParam.setIsUse("0");

            i = jdglMonthPlanMapper.insertJdglMonthPlan(jdglMonthPlanParam);

            List<JdglMonthImagePlan> jdglMonthImagePlanList = jdglMonthPlanParam.getJdglMonthImagePlanList();
            if(!CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
                for (JdglMonthImagePlan jdglMonthImagePlan : jdglMonthImagePlanList) {
                    jdglMonthImagePlan.setPlanId(id);
                }
                iJdglMonthImagePlanService.insertJdglMonthImagePlanList(jdglMonthImagePlanList);
            }
        }

        return i;
    }

    public List<JdglMonthPlan> getJdglMonthPlanList(JdglMonthPlan jdglMonthPlan) {
        List<JdglMonthPlan> jdglMonthPlanList = jdglMonthPlanMapper.getJdglMonthPlanList(jdglMonthPlan);
        if(!CollectionUtils.isEmpty(jdglMonthPlanList)) {
            for (JdglMonthPlan jdglMonthPlan1 : jdglMonthPlanList) {
                List<JdglMonthImagePlan> jdglMonthImagePlanListByPlanId = iJdglMonthImagePlanService.getJdglMonthImagePlanListByPlanId(jdglMonthPlan1.getId());
                jdglMonthPlan1.setJdglMonthImagePlanList(jdglMonthImagePlanListByPlanId);
                List<JdglMonthValuePlan> jdglMonthValuePlanListByPlanId = iJdglMonthValuePlanService.getJdglMonthValuePlanListByPlanId(jdglMonthPlan1.getId());
                jdglMonthPlan1.setJdglMonthValuePlanList(jdglMonthValuePlanListByPlanId);
            }
        }
        return jdglMonthPlanList;
    }

    @Transactional
    public int insertJdglMonthPlan(JdglMonthPlan jdglMonthPlan) {
        Long id = IdWorker.createId();
        jdglMonthPlan.setId(id);
        jdglMonthPlan.setCreateUser(SecurityUtils.getUserName());
        jdglMonthPlan.setCreateTime(DateUtils.getNowDate());
        jdglMonthPlan.setVersion("1");
        jdglMonthPlan.setIsUse("0");
        return jdglMonthPlanMapper.insertJdglMonthPlan(jdglMonthPlan);
    }

    @Transactional
    public int insertJdglMonthPlanList(List<JdglMonthPlan> jdglMonthPlanList) {
        for (JdglMonthPlan jdglMonthPlan : jdglMonthPlanList) {
            jdglMonthPlan.setId(IdWorker.createId());
            jdglMonthPlan.setCreateUser(SecurityUtils.getUserName());
            jdglMonthPlan.setCreateTime(DateUtils.getNowDate());
            jdglMonthPlan.setVersion("1");
        }
        return jdglMonthPlanMapper.insertJdglMonthPlanList(jdglMonthPlanList);
    }

    @Transactional
    public int updateJdglMonthPlan(JdglMonthPlan jdglMonthPlan) {
        jdglMonthPlan.setUpdateUser(SecurityUtils.getUserName());
        jdglMonthPlan.setUpdateTime(DateUtils.getNowDate());
//        iJdglMonthValuePlanService.updateJdglMonthValuePlanList(jdglMonthPlan.getJdglMonthValuePlanList());
        List<JdglMonthImagePlan> jdglMonthImagePlanList = jdglMonthPlan.getJdglMonthImagePlanList();
        if(!CollectionUtils.isEmpty(jdglMonthImagePlanList)) {
            for (JdglMonthImagePlan jdglMonthImagePlan: jdglMonthImagePlanList) {
                jdglMonthImagePlan.setPlanId(jdglMonthPlan.getId());
            }
        }
        iJdglMonthImagePlanService.updateJdglMonthImagePlanList(jdglMonthImagePlanList);

        // 根据计划完成产值汇总更新年计划产值&未完&

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
