package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.domain.QqchSpecialBigProcessControl;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.domain.vo.QqchSpecialBigProcessControlVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.mapper.QqchSpecialBigProcessControlMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.service.IQqchSpecialBigProcessControlService;
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
 * @date 2023-08-08 16:17:26
 * @remark
 */
@Service
public class QqchSpecialBigProcessControlServiceImpl implements IQqchSpecialBigProcessControlService {

    @Autowired
    private QqchSpecialBigProcessControlMapper qqchSpecialBigProcessControlMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchSpecialBigProcessControl getQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl) {
        return qqchSpecialBigProcessControlMapper.getQqchSpecialBigProcessControl(qqchSpecialBigProcessControl);
    }


    @Transactional
    public int insertQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl) {
        qqchSpecialBigProcessControl.setId(IdWorker.createId());
        qqchSpecialBigProcessControl.setCreateUser(SecurityUtils.getUserName());
        qqchSpecialBigProcessControl.setCreateTime(DateUtils.getNowDate());
        return qqchSpecialBigProcessControlMapper.insertQqchSpecialBigProcessControl(qqchSpecialBigProcessControl);
    }


    @Transactional
    public int updateQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl) {
        qqchSpecialBigProcessControl.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialBigProcessControl.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialBigProcessControlMapper.updateQqchSpecialBigProcessControl(qqchSpecialBigProcessControl);
    }

    @Transactional
    public int updateQqchSpecialBigProcessControlList(List<QqchSpecialBigProcessControl> qqchSpecialBigProcessControlList) {
        for (QqchSpecialBigProcessControl qqchSpecialBigProcessControl : qqchSpecialBigProcessControlList) {
            qqchSpecialBigProcessControl.setUpdateUser(SecurityUtils.getUserName());
            qqchSpecialBigProcessControl.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSpecialBigProcessControlMapper.updateQqchSpecialBigProcessControlList(qqchSpecialBigProcessControlList);
    }

    @Transactional
    public int deleteQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl) {
        qqchSpecialBigProcessControl.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialBigProcessControl.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialBigProcessControlMapper.deleteQqchSpecialBigProcessControl(qqchSpecialBigProcessControl);
    }

    @Transactional
    public int deleteQqchSpecialBigProcessControlByPks(List<Long> qqchSpecialBigProcessControlPkList) {
        return qqchSpecialBigProcessControlMapper.deleteQqchSpecialBigProcessControlByPks(qqchSpecialBigProcessControlPkList);
    }

    /**
     * 列表接口
     *
     * @param qqchSpecialBigProcessControl
     * @return
     */
    public QqchSpecialBigProcessControlVo getQqchSpecialBigProcessControlList(QqchSpecialBigProcessControl qqchSpecialBigProcessControl) {
        QqchSpecialBigProcessControlVo vo = new QqchSpecialBigProcessControlVo();
        BigDecimal version = VersionUtil.getVersion("qqch_special_big_equ_risk_measure", qqchSpecialBigProcessControl.getVersion());
        qqchSpecialBigProcessControl.setVersion(version);
        List<QqchSpecialBigProcessControl> qqchSpecialBigProcessControlList = qqchSpecialBigProcessControlMapper.getQqchSpecialBigProcessControlList(qqchSpecialBigProcessControl);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(qqchSpecialBigProcessControlList);
        return vo;
    }

    /**
     * 保存/确认/提交
     * @param vo
     */
    @Override
    public void save(QqchSpecialBigProcessControlVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchSpecialBigProcessControl> qqchSpecialBigProcessControlList = vo.getList();
        if(CollectionUtils.isEmpty(qqchSpecialBigProcessControlList)){
            return;
        }else {
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(qqchSpecialBigProcessControlList, ValidationGroups.Save.class);
            }
        }
        this.insertQqchSpecialBigProcessControlList(qqchSpecialBigProcessControlList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    @Transactional
    public int insertQqchSpecialBigProcessControlList(List<QqchSpecialBigProcessControl> qqchSpecialBigProcessControlList,BigDecimal version) {
        //删除旧数据
        QqchSpecialBigProcessControl qqchSpecialBigEquRiskMeasure1 = new QqchSpecialBigProcessControl();
        qqchSpecialBigEquRiskMeasure1.setVersion(version);
        qqchSpecialBigProcessControlMapper.deleteQqchSpecialBigProcessControl(qqchSpecialBigEquRiskMeasure1);
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchSpecialBigProcessControl qqchSpecialBigProcessControl : qqchSpecialBigProcessControlList) {
            qqchSpecialBigProcessControl.setValid(valid);
            qqchSpecialBigProcessControl.setVersion(version);
            qqchSpecialBigProcessControl.setId(IdWorker.createId());
            qqchSpecialBigProcessControl.setCreateUser(SecurityUtils.getSysUser().getNickName());
            qqchSpecialBigProcessControl.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSpecialBigProcessControlMapper.insertQqchSpecialBigProcessControlList(qqchSpecialBigProcessControlList);
    }
}
