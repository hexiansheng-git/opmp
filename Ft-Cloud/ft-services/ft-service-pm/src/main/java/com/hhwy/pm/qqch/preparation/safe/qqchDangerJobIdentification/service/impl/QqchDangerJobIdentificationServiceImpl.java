package com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.domain.QqchDangerJobIdentification;
import com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.domain.vo.QqchDangerJobIdentificationVo;
import com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.mapper.QqchDangerJobIdentificationMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.service.IQqchDangerJobIdentificationService;
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
 * @date 2023-08-09 11:55:28
 * @remark
 */
@Service
public class QqchDangerJobIdentificationServiceImpl implements IQqchDangerJobIdentificationService {

    @Autowired
    private QqchDangerJobIdentificationMapper qqchDangerJobIdentificationMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchDangerJobIdentification getQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification) {
        return qqchDangerJobIdentificationMapper.getQqchDangerJobIdentification(qqchDangerJobIdentification);
    }


    @Transactional
    public int insertQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification) {
        qqchDangerJobIdentification.setId(IdWorker.createId());
        qqchDangerJobIdentification.setCreateUser(SecurityUtils.getUserName());
        qqchDangerJobIdentification.setCreateTime(DateUtils.getNowDate());
        return qqchDangerJobIdentificationMapper.insertQqchDangerJobIdentification(qqchDangerJobIdentification);
    }



    @Transactional
    public int updateQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification) {
        qqchDangerJobIdentification.setUpdateUser(SecurityUtils.getUserName());
        qqchDangerJobIdentification.setUpdateTime(DateUtils.getNowDate());
        return qqchDangerJobIdentificationMapper.updateQqchDangerJobIdentification(qqchDangerJobIdentification);
    }

    @Transactional
    public int updateQqchDangerJobIdentificationList(List<QqchDangerJobIdentification> qqchDangerJobIdentificationList) {
        for (QqchDangerJobIdentification qqchDangerJobIdentification : qqchDangerJobIdentificationList) {
            qqchDangerJobIdentification.setUpdateUser(SecurityUtils.getUserName());
            qqchDangerJobIdentification.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDangerJobIdentificationMapper.updateQqchDangerJobIdentificationList(qqchDangerJobIdentificationList);
    }

    @Transactional
    public int deleteQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification) {
        qqchDangerJobIdentification.setUpdateUser(SecurityUtils.getUserName());
        qqchDangerJobIdentification.setUpdateTime(DateUtils.getNowDate());
        return qqchDangerJobIdentificationMapper.deleteQqchDangerJobIdentification(qqchDangerJobIdentification);
    }

    @Transactional
    public int deleteQqchDangerJobIdentificationByPks(List<Long> qqchDangerJobIdentificationPkList) {
        return qqchDangerJobIdentificationMapper.deleteQqchDangerJobIdentificationByPks(qqchDangerJobIdentificationPkList);
    }

    /**
     *  列表接口
     * @param qqchDangerJobIdentification
     * @return
     */
    public QqchDangerJobIdentificationVo getQqchDangerJobIdentificationList(QqchDangerJobIdentification qqchDangerJobIdentification) {
        QqchDangerJobIdentificationVo vo = new QqchDangerJobIdentificationVo();
        BigDecimal version = VersionUtil.getVersion("qqch_danger_job_identification", qqchDangerJobIdentification.getVersion());
        qqchDangerJobIdentification.setVersion(version);
        List<QqchDangerJobIdentification> qqchDangerJobIdentificationList = qqchDangerJobIdentificationMapper.getQqchDangerJobIdentificationList(qqchDangerJobIdentification);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchDangerJobIdentificationList(qqchDangerJobIdentificationList);
        return vo;
    }

    @Override
    @Transactional
    public void save(QqchDangerJobIdentificationVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchDangerJobIdentification> qqchDangerJobIdentificationList = vo.getQqchDangerJobIdentificationList();
        if(CollectionUtils.isEmpty(qqchDangerJobIdentificationList)){
            return;
        }else {
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(qqchDangerJobIdentificationList, ValidationGroups.Save.class);
            }
        }
        this.insertQqchDangerJobIdentificationList(qqchDangerJobIdentificationList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    public int insertQqchDangerJobIdentificationList(List<QqchDangerJobIdentification> qqchDangerJobIdentificationList,BigDecimal version) {
        //删除旧数据
        QqchDangerJobIdentification qqchDangerJobIdentification1 = new QqchDangerJobIdentification();
        qqchDangerJobIdentification1.setVersion(version);
        qqchDangerJobIdentificationMapper.deleteQqchDangerJobIdentification(qqchDangerJobIdentification1);
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchDangerJobIdentification qqchDangerJobIdentification : qqchDangerJobIdentificationList) {
            qqchDangerJobIdentification.setValid(valid);
            qqchDangerJobIdentification.setVersion(version);
            qqchDangerJobIdentification.setId(IdWorker.createId());
            qqchDangerJobIdentification.setCreateUser(SecurityUtils.getSysUser().getNickName());
            qqchDangerJobIdentification.setCreateTime(DateUtils.getNowDate());
        }
        return qqchDangerJobIdentificationMapper.insertQqchDangerJobIdentificationList(qqchDangerJobIdentificationList);
    }
}
