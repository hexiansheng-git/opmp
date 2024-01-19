package com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.domain.QqchNaturalDisasterRiskPlan;
import com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.domain.vo.QqchNaturalDisasterRiskPlanVo;
import com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.mapper.QqchNaturalDisasterRiskPlanMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchNaturalDisasterRiskPlan.service.IQqchNaturalDisasterRiskPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 09:30:37
 * @remark
 */
@Service
public class QqchNaturalDisasterRiskPlanServiceImpl implements IQqchNaturalDisasterRiskPlanService {

    @Autowired
    private QqchNaturalDisasterRiskPlanMapper qqchNaturalDisasterRiskPlanMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchNaturalDisasterRiskPlan getQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan) {
        return qqchNaturalDisasterRiskPlanMapper.getQqchNaturalDisasterRiskPlan(qqchNaturalDisasterRiskPlan);
    }


    @Transactional
    public int insertQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan) {
        qqchNaturalDisasterRiskPlan.setId(IdWorker.createId());
        qqchNaturalDisasterRiskPlan.setCreateUser(SecurityUtils.getUserName());
        qqchNaturalDisasterRiskPlan.setCreateTime(DateUtils.getNowDate());
        return qqchNaturalDisasterRiskPlanMapper.insertQqchNaturalDisasterRiskPlan(qqchNaturalDisasterRiskPlan);
    }

    @Transactional
    public int updateQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan) {
        qqchNaturalDisasterRiskPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchNaturalDisasterRiskPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchNaturalDisasterRiskPlanMapper.updateQqchNaturalDisasterRiskPlan(qqchNaturalDisasterRiskPlan);
    }

    @Transactional
    public int updateQqchNaturalDisasterRiskPlanList(List<QqchNaturalDisasterRiskPlan> qqchNaturalDisasterRiskPlanList) {
        for (QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan : qqchNaturalDisasterRiskPlanList) {
            qqchNaturalDisasterRiskPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchNaturalDisasterRiskPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchNaturalDisasterRiskPlanMapper.updateQqchNaturalDisasterRiskPlanList(qqchNaturalDisasterRiskPlanList);
    }

    @Transactional
    public int deleteQqchNaturalDisasterRiskPlan(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan) {
        qqchNaturalDisasterRiskPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchNaturalDisasterRiskPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchNaturalDisasterRiskPlanMapper.deleteQqchNaturalDisasterRiskPlan(qqchNaturalDisasterRiskPlan);
    }

    @Transactional
    public int deleteQqchNaturalDisasterRiskPlanByPks(List<Long> qqchNaturalDisasterRiskPlanPkList) {
        return qqchNaturalDisasterRiskPlanMapper.deleteQqchNaturalDisasterRiskPlanByPks(qqchNaturalDisasterRiskPlanPkList);
    }

    /**
     * 列表接口
     * @param qqchNaturalDisasterRiskPlan
     * @return
     */
    public QqchNaturalDisasterRiskPlanVo getQqchNaturalDisasterRiskPlanList(QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan) {
        QqchNaturalDisasterRiskPlanVo vo = new QqchNaturalDisasterRiskPlanVo();
        BigDecimal version = VersionUtil.getVersion("qqch_natural_disaster_risk_plan", qqchNaturalDisasterRiskPlan.getVersion());
        qqchNaturalDisasterRiskPlan.setVersion(version);
        List<QqchNaturalDisasterRiskPlan> qqchNaturalDisasterRiskPlanList = qqchNaturalDisasterRiskPlanMapper.getQqchNaturalDisasterRiskPlanList(qqchNaturalDisasterRiskPlan);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchNaturalDisasterRiskPlanList(qqchNaturalDisasterRiskPlanList);
        return vo;
    }


    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchNaturalDisasterRiskPlanVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchNaturalDisasterRiskPlan> qqchNaturalDisasterRiskPlanList = vo.getQqchNaturalDisasterRiskPlanList();
        this.insertQqchNaturalDisasterRiskPlanList(qqchNaturalDisasterRiskPlanList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //校验数据必填
            JyDetailsUtil.jyDetails(qqchNaturalDisasterRiskPlanList, ValidationGroups.Save.class);
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    public int insertQqchNaturalDisasterRiskPlanList(List<QqchNaturalDisasterRiskPlan> qqchNaturalDisasterRiskPlanList,BigDecimal version) {
        //删除旧数据
        QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan1 = new QqchNaturalDisasterRiskPlan();
        qqchNaturalDisasterRiskPlan1.setVersion(version);
        qqchNaturalDisasterRiskPlanMapper.deleteQqchNaturalDisasterRiskPlan(qqchNaturalDisasterRiskPlan1);
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        if (CollectionUtils.isEmpty(qqchNaturalDisasterRiskPlanList))
            return 0;
        for (QqchNaturalDisasterRiskPlan qqchNaturalDisasterRiskPlan : qqchNaturalDisasterRiskPlanList) {
            qqchNaturalDisasterRiskPlan.setValid(valid);
            qqchNaturalDisasterRiskPlan.setVersion(version);
            qqchNaturalDisasterRiskPlan.setId(IdWorker.createId());
            qqchNaturalDisasterRiskPlan.setCreateUser(SecurityUtils.getUserName());
            qqchNaturalDisasterRiskPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchNaturalDisasterRiskPlanMapper.insertQqchNaturalDisasterRiskPlanList(qqchNaturalDisasterRiskPlanList);
    }
}
