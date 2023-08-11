package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.domain.QqchEmergencyExerciseControl;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.domain.vo.QqchEmergencyExerciseControlVo;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.mapper.QqchEmergencyExerciseControlMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.service.IQqchEmergencyExerciseControlService;
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
 * @date 2023-08-10 18:39:07
 * @remark
 */
@Service
public class QqchEmergencyExerciseControlServiceImpl implements IQqchEmergencyExerciseControlService {

    @Autowired
    private QqchEmergencyExerciseControlMapper qqchEmergencyExerciseControlMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchEmergencyExerciseControl getQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl) {
        return qqchEmergencyExerciseControlMapper.getQqchEmergencyExerciseControl(qqchEmergencyExerciseControl);
    }


    @Transactional
    public int insertQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl) {
        qqchEmergencyExerciseControl.setId(IdWorker.createId());
        qqchEmergencyExerciseControl.setCreateUser(SecurityUtils.getUserName());
        qqchEmergencyExerciseControl.setCreateTime(DateUtils.getNowDate());
        return qqchEmergencyExerciseControlMapper.insertQqchEmergencyExerciseControl(qqchEmergencyExerciseControl);
    }


    @Transactional
    public int updateQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl) {
        qqchEmergencyExerciseControl.setUpdateUser(SecurityUtils.getUserName());
        qqchEmergencyExerciseControl.setUpdateTime(DateUtils.getNowDate());
        return qqchEmergencyExerciseControlMapper.updateQqchEmergencyExerciseControl(qqchEmergencyExerciseControl);
    }

    @Transactional
    public int updateQqchEmergencyExerciseControlList(List<QqchEmergencyExerciseControl> qqchEmergencyExerciseControlList) {
        for (QqchEmergencyExerciseControl qqchEmergencyExerciseControl : qqchEmergencyExerciseControlList) {
            qqchEmergencyExerciseControl.setUpdateUser(SecurityUtils.getUserName());
            qqchEmergencyExerciseControl.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchEmergencyExerciseControlMapper.updateQqchEmergencyExerciseControlList(qqchEmergencyExerciseControlList);
    }

    @Transactional
    public int deleteQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl) {
        qqchEmergencyExerciseControl.setUpdateUser(SecurityUtils.getUserName());
        qqchEmergencyExerciseControl.setUpdateTime(DateUtils.getNowDate());
        return qqchEmergencyExerciseControlMapper.deleteQqchEmergencyExerciseControl(qqchEmergencyExerciseControl);
    }

    @Transactional
    public int deleteQqchEmergencyExerciseControlByPks(List<Long> qqchEmergencyExerciseControlPkList) {
        return qqchEmergencyExerciseControlMapper.deleteQqchEmergencyExerciseControlByPks(qqchEmergencyExerciseControlPkList);
    }

    /**
     * 列表接口
     * @param qqchEmergencyExerciseControl
     * @return
     */
    public QqchEmergencyExerciseControlVo getQqchEmergencyExerciseControlList(QqchEmergencyExerciseControl qqchEmergencyExerciseControl) {
        QqchEmergencyExerciseControlVo vo = new QqchEmergencyExerciseControlVo();

        BigDecimal version = qqchEmergencyExerciseControl.getVersion();
        version = VersionUtil.getVersion("qqch_emergency_exercise_control", version);

        qqchEmergencyExerciseControl.setVersion(version);
        List<QqchEmergencyExerciseControl> qqchEmergencyExerciseControlList = qqchEmergencyExerciseControlMapper.getQqchEmergencyExerciseControlList(qqchEmergencyExerciseControl);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchEmergencyExerciseControlList(qqchEmergencyExerciseControlList);
        return vo;
    }

    /**
     *  保存/确认/提交
     *  @param vo
     */
    @Override
    public void save(QqchEmergencyExerciseControlVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchEmergencyExerciseControl> qqchEmergencyExerciseControlList = vo.getQqchEmergencyExerciseControlList();
        if(CollectionUtils.isEmpty(qqchEmergencyExerciseControlList)){
            return;
        }else {
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(qqchEmergencyExerciseControlList, ValidationGroups.Save.class);
            }
        }
        this.insertQqchEmergencyExerciseControlList(qqchEmergencyExerciseControlList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Transactional
    public void insertQqchEmergencyExerciseControlList(List<QqchEmergencyExerciseControl> qqchEmergencyExerciseControlList,BigDecimal version) {
        //删除旧数据
        QqchEmergencyExerciseControl qqchEmergencyExerciseControl1 = new QqchEmergencyExerciseControl();
        qqchEmergencyExerciseControl1.setVersion(version);
        qqchEmergencyExerciseControlMapper.deleteQqchEmergencyExerciseControl(qqchEmergencyExerciseControl1);


        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchEmergencyExerciseControl qqchEmergencyExerciseControl : qqchEmergencyExerciseControlList) {
            qqchEmergencyExerciseControl.setId(IdWorker.createId());
            qqchEmergencyExerciseControl.setValid(valid);
            qqchEmergencyExerciseControl.setVersion(version);
            qqchEmergencyExerciseControl.setCreateUser(SecurityUtils.getUserName());
            qqchEmergencyExerciseControl.setCreateTime(DateUtils.getNowDate());
        }
        qqchEmergencyExerciseControlMapper.insertQqchEmergencyExerciseControlList(qqchEmergencyExerciseControlList);
    }
}
