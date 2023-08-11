package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.domain.QqchEmergencyImplementationPlan;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.domain.vo.QqchEmergencyImplementationPlanVo;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.mapper.QqchEmergencyImplementationPlanMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyImplementationPlan.service.IQqchEmergencyImplementationPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-10 18:39:15
 *
 * 8.10.3 应急演练实施策划
 * @remark
 */
@Service
public class QqchEmergencyImplementationPlanServiceImpl implements IQqchEmergencyImplementationPlanService {

    @Autowired
    private QqchEmergencyImplementationPlanMapper qqchEmergencyImplementationPlanMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;



    public QqchEmergencyImplementationPlan getQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan) {
        return qqchEmergencyImplementationPlanMapper.getQqchEmergencyImplementationPlan(qqchEmergencyImplementationPlan);
    }



    @Transactional
    public int insertQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan) {
        qqchEmergencyImplementationPlan.setId(IdWorker.createId());
        qqchEmergencyImplementationPlan.setCreateUser(SecurityUtils.getUserName());
        qqchEmergencyImplementationPlan.setCreateTime(DateUtils.getNowDate());
        return qqchEmergencyImplementationPlanMapper.insertQqchEmergencyImplementationPlan(qqchEmergencyImplementationPlan);
    }


    @Transactional
    public int updateQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan) {
        qqchEmergencyImplementationPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchEmergencyImplementationPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchEmergencyImplementationPlanMapper.updateQqchEmergencyImplementationPlan(qqchEmergencyImplementationPlan);
    }

    @Transactional
    public int updateQqchEmergencyImplementationPlanList(List<QqchEmergencyImplementationPlan> qqchEmergencyImplementationPlanList) {
        for (QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan : qqchEmergencyImplementationPlanList) {
            qqchEmergencyImplementationPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchEmergencyImplementationPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchEmergencyImplementationPlanMapper.updateQqchEmergencyImplementationPlanList(qqchEmergencyImplementationPlanList);
    }

    @Transactional
    public int deleteQqchEmergencyImplementationPlan(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan) {
        qqchEmergencyImplementationPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchEmergencyImplementationPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchEmergencyImplementationPlanMapper.deleteQqchEmergencyImplementationPlan(qqchEmergencyImplementationPlan);
    }

    @Transactional
    public int deleteQqchEmergencyImplementationPlanByPks(List<Long> qqchEmergencyImplementationPlanPkList) {
        return qqchEmergencyImplementationPlanMapper.deleteQqchEmergencyImplementationPlanByPks(qqchEmergencyImplementationPlanPkList);
    }

    /**
     * 列表接口
     * @param qqchEmergencyImplementationPlan
     * @return
     */
    public QqchEmergencyImplementationPlanVo getQqchEmergencyImplementationPlanList(QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan) {
        QqchEmergencyImplementationPlanVo vo = new QqchEmergencyImplementationPlanVo();

        BigDecimal version = qqchEmergencyImplementationPlan.getVersion();
        version = VersionUtil.getVersion("qqch_emergency_plan_control", version);

        qqchEmergencyImplementationPlan.setVersion(version);
        List<QqchEmergencyImplementationPlan> qqchEmergencyImplementationPlanList = qqchEmergencyImplementationPlanMapper.getQqchEmergencyImplementationPlanList(qqchEmergencyImplementationPlan);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchEmergencyImplementationPlanList(qqchEmergencyImplementationPlanList);
        return vo;
    }

    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchEmergencyImplementationPlanVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchEmergencyImplementationPlan> qqchEmergencyImplementationPlanList = vo.getQqchEmergencyImplementationPlanList();
        if(CollectionUtils.isEmpty(qqchEmergencyImplementationPlanList)){
            return;
        }else {
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(qqchEmergencyImplementationPlanList, ValidationGroups.Save.class);
            }
        }

        this.insertQqchEmergencyImplementationPlanList(qqchEmergencyImplementationPlanList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }


    public void insertQqchEmergencyImplementationPlanList(List<QqchEmergencyImplementationPlan> qqchEmergencyImplementationPlanList,BigDecimal version) {
        //删除旧数据
        QqchEmergencyImplementationPlan qqchEmergencyExerciseControl1 = new QqchEmergencyImplementationPlan();
        qqchEmergencyExerciseControl1.setVersion(version);
        qqchEmergencyImplementationPlanMapper.deleteQqchEmergencyImplementationPlan(qqchEmergencyExerciseControl1);

        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchEmergencyImplementationPlan qqchEmergencyImplementationPlan : qqchEmergencyImplementationPlanList) {
            qqchEmergencyImplementationPlan.setId(IdWorker.createId());
            qqchEmergencyImplementationPlan.setValid(valid);
            qqchEmergencyImplementationPlan.setVersion(version);
            qqchEmergencyImplementationPlan.setCreateUser(SecurityUtils.getSysUser().getNickName());
            qqchEmergencyImplementationPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchEmergencyImplementationPlanMapper.insertQqchEmergencyImplementationPlanList(qqchEmergencyImplementationPlanList);
    }
}
