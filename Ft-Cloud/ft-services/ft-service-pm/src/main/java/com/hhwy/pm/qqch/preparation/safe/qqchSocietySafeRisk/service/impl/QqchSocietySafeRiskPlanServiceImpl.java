package com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;

import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.domain.QqchSafeEnvriRiskManage;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.vo.QqchSafeEnvriRiskManageVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRisk;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRiskPlan;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.vo.QqchSocietySafeRiskPlanVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.vo.QqchSocietySafeRiskVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.mapper.QqchSocietySafeRiskPlanMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.service.IQqchSocietySafeRiskPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author hwj
 * @date 2024-05-23 17:36:41
 * @remark
 */
@Service
public class QqchSocietySafeRiskPlanServiceImpl implements IQqchSocietySafeRiskPlanService {

    @Autowired
    private QqchSocietySafeRiskPlanMapper qqchSocietySafeRiskPlanMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchSocietySafeRiskPlan getQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan) {
        return qqchSocietySafeRiskPlanMapper.getQqchSocietySafeRiskPlan(qqchSocietySafeRiskPlan);
    }

    public List<QqchSocietySafeRiskPlan> getQqchSocietySafeRiskPlanList(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan) {
        return qqchSocietySafeRiskPlanMapper.getQqchSocietySafeRiskPlanList(qqchSocietySafeRiskPlan);
    }

    @Transactional
    public int insertQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan) {
        qqchSocietySafeRiskPlan.setId(IdWorker.createId());
        qqchSocietySafeRiskPlan.setCreateUser(SecurityUtils.getUserName());
        qqchSocietySafeRiskPlan.setCreateTime(DateUtils.getNowDate());
        return qqchSocietySafeRiskPlanMapper.insertQqchSocietySafeRiskPlan(qqchSocietySafeRiskPlan);
    }

    @Transactional
    public int insertQqchSocietySafeRiskPlanList(List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanList) {
        for (QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan : qqchSocietySafeRiskPlanList) {
            qqchSocietySafeRiskPlan.setId(IdWorker.createId());
            qqchSocietySafeRiskPlan.setCreateUser(SecurityUtils.getUserName());
            qqchSocietySafeRiskPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSocietySafeRiskPlanMapper.insertQqchSocietySafeRiskPlanList(qqchSocietySafeRiskPlanList);
    }

    @Transactional
    public int updateQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan) {
        qqchSocietySafeRiskPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSocietySafeRiskPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSocietySafeRiskPlanMapper.updateQqchSocietySafeRiskPlan(qqchSocietySafeRiskPlan);
    }

    @Transactional
    public int updateQqchSocietySafeRiskPlanList(List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskPlanList) {
        for (QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan : qqchSocietySafeRiskPlanList) {
            qqchSocietySafeRiskPlan.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            qqchSocietySafeRiskPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSocietySafeRiskPlanMapper.updateQqchSocietySafeRiskPlanList(qqchSocietySafeRiskPlanList);
    }

    @Transactional
    public int deleteQqchSocietySafeRiskPlan(QqchSocietySafeRiskPlan qqchSocietySafeRiskPlan) {
        qqchSocietySafeRiskPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchSocietySafeRiskPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchSocietySafeRiskPlanMapper.deleteQqchSocietySafeRiskPlan(qqchSocietySafeRiskPlan);
    }

    @Transactional
    public int deleteQqchSocietySafeRiskPlanByPks(List<Long> qqchSocietySafeRiskPlanPkList) {
        return qqchSocietySafeRiskPlanMapper.deleteQqchSocietySafeRiskPlanByPks(qqchSocietySafeRiskPlanPkList);
    }
    /**
     * 保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchSocietySafeRiskPlanVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);
        BigDecimal version = vo.getVersion();
        List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskList = vo.getQqchSocietySafeRiskList();
        //校验数据必填
        if(io.seata.common.util.CollectionUtils.isNotEmpty(qqchSocietySafeRiskList)){
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(qqchSocietySafeRiskList, ValidationGroups.Save.class);
            }
        }
        this.insertQqchSocietySafeRiskList(qqchSocietySafeRiskList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    public int insertQqchSocietySafeRiskList(List<QqchSocietySafeRiskPlan> qqchSocietySafeRiskList,BigDecimal version) {
        //删除旧数据
        QqchSocietySafeRiskPlan qqchSocietySafeRisk1 = new QqchSocietySafeRiskPlan();
        qqchSocietySafeRisk1.setVersion(version);
        qqchSocietySafeRiskPlanMapper.deleteQqchSocietySafeRiskPlan(qqchSocietySafeRisk1);
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        if (CollectionUtils.isEmpty(qqchSocietySafeRiskList))
            return 0;
        for (QqchSocietySafeRiskPlan qqchSocietySafeRisk : qqchSocietySafeRiskList) {
            qqchSocietySafeRisk.setValid(valid);
            qqchSocietySafeRisk.setVersion(version);
            qqchSocietySafeRisk.setId(IdWorker.createId());
            qqchSocietySafeRisk.setCreateUser(SecurityUtils.getSysUser().getNickName());
            qqchSocietySafeRisk.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSocietySafeRiskPlanMapper.insertQqchSocietySafeRiskPlanList(qqchSocietySafeRiskList);
    }

    @Override
    public QqchSocietySafeRiskPlanVo getList(BigDecimal version) {
        QqchSocietySafeRiskPlanVo planVo = new QqchSocietySafeRiskPlanVo();
        version = VersionUtil.getVersion("qqch_society_safe_risk_plan", version);

        QqchSocietySafeRiskPlan riskPlan = new QqchSocietySafeRiskPlan();
        riskPlan.setVersion(version);
        List<QqchSocietySafeRiskPlan> list = qqchSocietySafeRiskPlanMapper.getQqchSocietySafeRiskPlanList(riskPlan);

        planVo.setVersion(version);
        planVo.setStageIdentity(qqchReviewService.getStage());
        planVo.setQqchSocietySafeRiskList(list);
        return planVo;
    }
}
