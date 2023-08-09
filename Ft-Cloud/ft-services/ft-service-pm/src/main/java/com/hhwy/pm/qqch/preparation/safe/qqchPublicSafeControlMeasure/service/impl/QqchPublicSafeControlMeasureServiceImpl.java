package com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.domain.QqchPublicSafeControlMeasure;
import com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.domain.vo.QqchPublicSafeControlMeasureVo;
import com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.mapper.QqchPublicSafeControlMeasureMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchPublicSafeControlMeasure.service.IQqchPublicSafeControlMeasureService;
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
 * @date 2023-08-09 09:30:52
 * @remark
 */
@Service
public class QqchPublicSafeControlMeasureServiceImpl implements IQqchPublicSafeControlMeasureService {

    @Autowired
    private QqchPublicSafeControlMeasureMapper qqchPublicSafeControlMeasureMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchPublicSafeControlMeasure getQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure) {
        return qqchPublicSafeControlMeasureMapper.getQqchPublicSafeControlMeasure(qqchPublicSafeControlMeasure);
    }

    @Transactional
    public int insertQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure) {
        qqchPublicSafeControlMeasure.setId(IdWorker.createId());
        qqchPublicSafeControlMeasure.setCreateUser(SecurityUtils.getUserName());
        qqchPublicSafeControlMeasure.setCreateTime(DateUtils.getNowDate());
        return qqchPublicSafeControlMeasureMapper.insertQqchPublicSafeControlMeasure(qqchPublicSafeControlMeasure);
    }



    @Transactional
    public int updateQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure) {
        qqchPublicSafeControlMeasure.setUpdateUser(SecurityUtils.getUserName());
        qqchPublicSafeControlMeasure.setUpdateTime(DateUtils.getNowDate());
        return qqchPublicSafeControlMeasureMapper.updateQqchPublicSafeControlMeasure(qqchPublicSafeControlMeasure);
    }

    @Transactional
    public int updateQqchPublicSafeControlMeasureList(List<QqchPublicSafeControlMeasure> qqchPublicSafeControlMeasureList) {
        for (QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure : qqchPublicSafeControlMeasureList) {
            qqchPublicSafeControlMeasure.setUpdateUser(SecurityUtils.getUserName());
            qqchPublicSafeControlMeasure.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchPublicSafeControlMeasureMapper.updateQqchPublicSafeControlMeasureList(qqchPublicSafeControlMeasureList);
    }

    @Transactional
    public int deleteQqchPublicSafeControlMeasure(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure) {
        qqchPublicSafeControlMeasure.setUpdateUser(SecurityUtils.getUserName());
        qqchPublicSafeControlMeasure.setUpdateTime(DateUtils.getNowDate());
        return qqchPublicSafeControlMeasureMapper.deleteQqchPublicSafeControlMeasure(qqchPublicSafeControlMeasure);
    }

    @Transactional
    public int deleteQqchPublicSafeControlMeasureByPks(List<Long> qqchPublicSafeControlMeasurePkList) {
        return qqchPublicSafeControlMeasureMapper.deleteQqchPublicSafeControlMeasureByPks(qqchPublicSafeControlMeasurePkList);
    }

    public QqchPublicSafeControlMeasureVo getQqchPublicSafeControlMeasureList(QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure) {
        QqchPublicSafeControlMeasureVo vo = new QqchPublicSafeControlMeasureVo();
        BigDecimal version = VersionUtil.getVersion("qqch_public_safe_control_measure", qqchPublicSafeControlMeasure.getVersion());
        qqchPublicSafeControlMeasure.setVersion(version);
        List<QqchPublicSafeControlMeasure> qqchPublicSafeControlMeasureList = qqchPublicSafeControlMeasureMapper.getQqchPublicSafeControlMeasureList(qqchPublicSafeControlMeasure);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchPublicSafeControlMeasureList(qqchPublicSafeControlMeasureList);
        return vo;
    }

    @Override
    @Transactional
    public void save(QqchPublicSafeControlMeasureVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchPublicSafeControlMeasure> qqchPublicSafeControlMeasureList = vo.getQqchPublicSafeControlMeasureList();
        if(CollectionUtils.isEmpty(qqchPublicSafeControlMeasureList)){
            return;
        }else {
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(qqchPublicSafeControlMeasureList, ValidationGroups.Save.class);
            }
        }
        this.insertQqchPublicSafeControlMeasureList(qqchPublicSafeControlMeasureList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    public int insertQqchPublicSafeControlMeasureList(List<QqchPublicSafeControlMeasure> qqchPublicSafeControlMeasureList,BigDecimal version) {
        //删除旧数据
        QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure1 = new QqchPublicSafeControlMeasure();
        qqchPublicSafeControlMeasure1.setVersion(version);
        qqchPublicSafeControlMeasureMapper.deleteQqchPublicSafeControlMeasure(qqchPublicSafeControlMeasure1);
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchPublicSafeControlMeasure qqchPublicSafeControlMeasure : qqchPublicSafeControlMeasureList) {
            qqchPublicSafeControlMeasure.setValid(valid);
            qqchPublicSafeControlMeasure.setVersion(version);
            qqchPublicSafeControlMeasure.setId(IdWorker.createId());
            qqchPublicSafeControlMeasure.setCreateUser(SecurityUtils.getSysUser().getNickName());
            qqchPublicSafeControlMeasure.setCreateTime(DateUtils.getNowDate());
        }
        return qqchPublicSafeControlMeasureMapper.insertQqchPublicSafeControlMeasureList(qqchPublicSafeControlMeasureList);
    }
}
