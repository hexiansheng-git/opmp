package com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.domain.QqchPulicHealthRisk;
import com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.domain.vo.QqchPulicHealthRiskVo;
import com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.mapper.QqchPulicHealthRiskMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.service.IQqchPulicHealthRiskService;
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
 * @date 2023-08-09 15:01:10
 * @remark
 */
@Service
public class QqchPulicHealthRiskServiceImpl implements IQqchPulicHealthRiskService {

    @Autowired
    private QqchPulicHealthRiskMapper qqchPulicHealthRiskMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchPulicHealthRisk getQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk) {
        return qqchPulicHealthRiskMapper.getQqchPulicHealthRisk(qqchPulicHealthRisk);
    }

    @Transactional
    public int insertQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk) {
        qqchPulicHealthRisk.setId(IdWorker.createId());
        qqchPulicHealthRisk.setCreateUser(SecurityUtils.getUserName());
        qqchPulicHealthRisk.setCreateTime(DateUtils.getNowDate());
        return qqchPulicHealthRiskMapper.insertQqchPulicHealthRisk(qqchPulicHealthRisk);
    }


    @Transactional
    public int updateQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk) {
        qqchPulicHealthRisk.setUpdateUser(SecurityUtils.getUserName());
        qqchPulicHealthRisk.setUpdateTime(DateUtils.getNowDate());
        return qqchPulicHealthRiskMapper.updateQqchPulicHealthRisk(qqchPulicHealthRisk);
    }

    @Transactional
    public int updateQqchPulicHealthRiskList(List<QqchPulicHealthRisk> qqchPulicHealthRiskList) {
        for (QqchPulicHealthRisk qqchPulicHealthRisk : qqchPulicHealthRiskList) {
            qqchPulicHealthRisk.setUpdateUser(SecurityUtils.getUserName());
            qqchPulicHealthRisk.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchPulicHealthRiskMapper.updateQqchPulicHealthRiskList(qqchPulicHealthRiskList);
    }

    @Transactional
    public int deleteQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk) {
        qqchPulicHealthRisk.setUpdateUser(SecurityUtils.getUserName());
        qqchPulicHealthRisk.setUpdateTime(DateUtils.getNowDate());
        return qqchPulicHealthRiskMapper.deleteQqchPulicHealthRisk(qqchPulicHealthRisk);
    }

    @Transactional
    public int deleteQqchPulicHealthRiskByPks(List<Long> qqchPulicHealthRiskPkList) {
        return qqchPulicHealthRiskMapper.deleteQqchPulicHealthRiskByPks(qqchPulicHealthRiskPkList);
    }

    /**
     *  列表接口
     * @param qqchPulicHealthRisk
     * @return
     */
    public QqchPulicHealthRiskVo getQqchPulicHealthRiskList(QqchPulicHealthRisk qqchPulicHealthRisk) {
        QqchPulicHealthRiskVo vo = new QqchPulicHealthRiskVo();
        BigDecimal version = VersionUtil.getVersion("qqch_pulic_health_risk", qqchPulicHealthRisk.getVersion());
        qqchPulicHealthRisk.setVersion(version);
        List<QqchPulicHealthRisk> qqchPulicHealthRiskList = qqchPulicHealthRiskMapper.getQqchPulicHealthRiskList(qqchPulicHealthRisk);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchPulicHealthRiskList(qqchPulicHealthRiskList);
        return vo;
    }

    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    public void save(QqchPulicHealthRiskVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchPulicHealthRisk> qqchPulicHealthRiskList = vo.getQqchPulicHealthRiskList();
        this.insertQqchPulicHealthRiskList(qqchPulicHealthRiskList,version);

        if(CollectionUtils.isEmpty(qqchPulicHealthRiskList)){
            return;
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //校验数据必填
            JyDetailsUtil.jyDetails(qqchPulicHealthRiskList, ValidationGroups.Save.class);
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    @Transactional
    public int insertQqchPulicHealthRiskList(List<QqchPulicHealthRisk> qqchPulicHealthRiskList,BigDecimal version) {
        //删除旧数据
        QqchPulicHealthRisk qqchPulicHealthRisk1 = new QqchPulicHealthRisk();
        qqchPulicHealthRisk1.setVersion(version);
        qqchPulicHealthRiskMapper.deleteQqchPulicHealthRisk(qqchPulicHealthRisk1);
        if(CollectionUtils.isEmpty(qqchPulicHealthRiskList)){
            return 0;
        }
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchPulicHealthRisk qqchPulicHealthRisk : qqchPulicHealthRiskList) {
            qqchPulicHealthRisk.setValid(valid);
            qqchPulicHealthRisk.setVersion(version);
            qqchPulicHealthRisk.setId(IdWorker.createId());
            qqchPulicHealthRisk.setCreateUser(SecurityUtils.getUserName());
            qqchPulicHealthRisk.setCreateTime(DateUtils.getNowDate());
        }
        return qqchPulicHealthRiskMapper.insertQqchPulicHealthRiskList(qqchPulicHealthRiskList);
    }

}
