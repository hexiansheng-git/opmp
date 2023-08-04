package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.QqchWeightEngineeringControl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.vo.QqchWeightEngineeringControlVo;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.mapper.QqchWeightEngineeringControlMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.service.IQqchWeightEngineeringControlService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 15:10:09
 * @remark
 */
@Service
public class QqchWeightEngineeringControlServiceImpl implements IQqchWeightEngineeringControlService {

    @Autowired
    private QqchWeightEngineeringControlMapper qqchWeightEngineeringControlMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchWeightEngineeringControl getQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl) {
        return qqchWeightEngineeringControlMapper.getQqchWeightEngineeringControl(qqchWeightEngineeringControl);
    }


    @Transactional
    public int insertQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl) {
        qqchWeightEngineeringControl.setId(IdWorker.createId());
        qqchWeightEngineeringControl.setCreateUser(SecurityUtils.getUserName());
        qqchWeightEngineeringControl.setCreateTime(DateUtils.getNowDate());
        return qqchWeightEngineeringControlMapper.insertQqchWeightEngineeringControl(qqchWeightEngineeringControl);
    }



    @Transactional
    public int updateQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl) {
        qqchWeightEngineeringControl.setUpdateUser(SecurityUtils.getUserName());
        qqchWeightEngineeringControl.setUpdateTime(DateUtils.getNowDate());
        return qqchWeightEngineeringControlMapper.updateQqchWeightEngineeringControl(qqchWeightEngineeringControl);
    }

    @Transactional
    public int updateQqchWeightEngineeringControlList(List<QqchWeightEngineeringControl> qqchWeightEngineeringControlList) {
        for (QqchWeightEngineeringControl qqchWeightEngineeringControl : qqchWeightEngineeringControlList) {
            qqchWeightEngineeringControl.setUpdateUser(SecurityUtils.getUserName());
            qqchWeightEngineeringControl.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchWeightEngineeringControlMapper.updateQqchWeightEngineeringControlList(qqchWeightEngineeringControlList);
    }

    @Transactional
    public int deleteQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl) {
        qqchWeightEngineeringControl.setUpdateUser(SecurityUtils.getUserName());
        qqchWeightEngineeringControl.setUpdateTime(DateUtils.getNowDate());
        return qqchWeightEngineeringControlMapper.deleteQqchWeightEngineeringControl(qqchWeightEngineeringControl);
    }

    @Transactional
    public int deleteQqchWeightEngineeringControlByPks(List<Long> qqchWeightEngineeringControlPkList) {
        return qqchWeightEngineeringControlMapper.deleteQqchWeightEngineeringControlByPks(qqchWeightEngineeringControlPkList);
    }


    /**
     * 列表接口
     * @param qqchWeightEngineeringControl
     * @return
     */
    public QqchWeightEngineeringControlVo getQqchWeightEngineeringControlList(QqchWeightEngineeringControl qqchWeightEngineeringControl) {
        QqchWeightEngineeringControlVo vo = new QqchWeightEngineeringControlVo();

        BigDecimal version = qqchWeightEngineeringControl.getVersion();
        version = VersionUtil.getVersion("qqch_weight_engineering_control", version);

        qqchWeightEngineeringControl.setVersion(version);
        List<QqchWeightEngineeringControl> qqchWeightEngineeringControlList = qqchWeightEngineeringControlMapper.getQqchWeightEngineeringControlList(qqchWeightEngineeringControl);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchWeightEngineeringControlList(qqchWeightEngineeringControlList);
        return vo;
    }


    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchWeightEngineeringControlVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchWeightEngineeringControl> qqchWeightEngineeringControlList = vo.getQqchWeightEngineeringControlList();

        this.insertQqchWeightEngineeringControlList(qqchWeightEngineeringControlList, version);

        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }


    public void insertQqchWeightEngineeringControlList(List<QqchWeightEngineeringControl> qqchWeightEngineeringControlList,BigDecimal version) {
        //删除旧数据
        QqchWeightEngineeringControl qqchWeightEngineeringControl = new QqchWeightEngineeringControl();
        qqchWeightEngineeringControl.setVersion(version);
        qqchWeightEngineeringControlMapper.deleteQqchWeightEngineeringControl(qqchWeightEngineeringControl);
        if (CollectionUtils.isEmpty(qqchWeightEngineeringControlList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchWeightEngineeringControl engineeringControl : qqchWeightEngineeringControlList) {
            engineeringControl.setId(IdWorker.createId());
            engineeringControl.setValid(valid);
            engineeringControl.setVersion(version);
            engineeringControl.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            engineeringControl.setCreateUserName(SecurityUtils.getUserName());
            engineeringControl.setCreateTime(DateUtils.getNowDate());
        }
        qqchWeightEngineeringControlMapper.insertQqchWeightEngineeringControlList(qqchWeightEngineeringControlList);
    }


}
