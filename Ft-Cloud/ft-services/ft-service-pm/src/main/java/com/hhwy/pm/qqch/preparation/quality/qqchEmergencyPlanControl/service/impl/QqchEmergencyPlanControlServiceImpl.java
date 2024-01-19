package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.domain.QqchEmergencyPlanControl;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.domain.vo.QqchEmergencyPlanControlVo;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.mapper.QqchEmergencyPlanControlMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.service.IQqchEmergencyPlanControlService;
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
 * @date 2023-08-10 18:39:24
 * @remark  8.10.1 应急预案管控
 */
@Service
public class QqchEmergencyPlanControlServiceImpl implements IQqchEmergencyPlanControlService {

    @Autowired
    private QqchEmergencyPlanControlMapper qqchEmergencyPlanControlMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchEmergencyPlanControl getQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl) {
        return qqchEmergencyPlanControlMapper.getQqchEmergencyPlanControl(qqchEmergencyPlanControl);
    }


    @Transactional
    public int insertQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl) {
        qqchEmergencyPlanControl.setId(IdWorker.createId());
        qqchEmergencyPlanControl.setCreateUser(SecurityUtils.getUserName());
        qqchEmergencyPlanControl.setCreateTime(DateUtils.getNowDate());
        return qqchEmergencyPlanControlMapper.insertQqchEmergencyPlanControl(qqchEmergencyPlanControl);
    }


    @Transactional
    public int updateQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl) {
        qqchEmergencyPlanControl.setUpdateUser(SecurityUtils.getUserName());
        qqchEmergencyPlanControl.setUpdateTime(DateUtils.getNowDate());
        return qqchEmergencyPlanControlMapper.updateQqchEmergencyPlanControl(qqchEmergencyPlanControl);
    }

    @Transactional
    public int updateQqchEmergencyPlanControlList(List<QqchEmergencyPlanControl> qqchEmergencyPlanControlList) {
        for (QqchEmergencyPlanControl qqchEmergencyPlanControl : qqchEmergencyPlanControlList) {
            qqchEmergencyPlanControl.setUpdateUser(SecurityUtils.getUserName());
            qqchEmergencyPlanControl.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchEmergencyPlanControlMapper.updateQqchEmergencyPlanControlList(qqchEmergencyPlanControlList);
    }

    @Transactional
    public int deleteQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl) {
        qqchEmergencyPlanControl.setUpdateUser(SecurityUtils.getUserName());
        qqchEmergencyPlanControl.setUpdateTime(DateUtils.getNowDate());
        return qqchEmergencyPlanControlMapper.deleteQqchEmergencyPlanControl(qqchEmergencyPlanControl);
    }

    @Transactional
    public int deleteQqchEmergencyPlanControlByPks(List<Long> qqchEmergencyPlanControlPkList) {
        return qqchEmergencyPlanControlMapper.deleteQqchEmergencyPlanControlByPks(qqchEmergencyPlanControlPkList);
    }

    /**
     * 列表接口
     * @param qqchEmergencyPlanControl
     * @return
     */
    public QqchEmergencyPlanControlVo getQqchEmergencyPlanControlList(QqchEmergencyPlanControl qqchEmergencyPlanControl) {
        QqchEmergencyPlanControlVo vo = new QqchEmergencyPlanControlVo();

        BigDecimal version = qqchEmergencyPlanControl.getVersion();
        version = VersionUtil.getVersion("qqch_emergency_plan_control", version);

        qqchEmergencyPlanControl.setVersion(version);
        List<QqchEmergencyPlanControl> qqchEmergencyPlanControlList = qqchEmergencyPlanControlMapper.getQqchEmergencyPlanControlList(qqchEmergencyPlanControl);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchEmergencyPlanControlList(qqchEmergencyPlanControlList);
        return vo;
    }

    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchEmergencyPlanControlVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchEmergencyPlanControl> qqchEmergencyPlanControlList = vo.getQqchEmergencyPlanControlList();
        this.insertQqchEmergencyPlanControlList(qqchEmergencyPlanControlList, version);

        if(CollectionUtils.isEmpty(qqchEmergencyPlanControlList)){
            return;
        }
        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //校验数据必填
//            JyDetailsUtil.jyDetails(qqchEmergencyPlanControlList, ValidationGroups.Save.class);
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }


    public void insertQqchEmergencyPlanControlList(List<QqchEmergencyPlanControl> qqchEmergencyPlanControlList,BigDecimal version) {
        //删除旧数据
        QqchEmergencyPlanControl qqchEmergencyPlanControl1 = new QqchEmergencyPlanControl();
        qqchEmergencyPlanControl1.setVersion(version);
        qqchEmergencyPlanControlMapper.deleteQqchEmergencyPlanControl(qqchEmergencyPlanControl1);

        if(CollectionUtils.isEmpty(qqchEmergencyPlanControlList)){
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchEmergencyPlanControl qqchEmergencyPlanControl : qqchEmergencyPlanControlList) {
            qqchEmergencyPlanControl.setId(IdWorker.createId());
            qqchEmergencyPlanControl.setValid(valid);
            qqchEmergencyPlanControl.setVersion(version);
            qqchEmergencyPlanControl.setCreateUser(SecurityUtils.getUserName());
            qqchEmergencyPlanControl.setCreateTime(DateUtils.getNowDate());
        }
        qqchEmergencyPlanControlMapper.insertQqchEmergencyPlanControlList(qqchEmergencyPlanControlList);
    }
}
