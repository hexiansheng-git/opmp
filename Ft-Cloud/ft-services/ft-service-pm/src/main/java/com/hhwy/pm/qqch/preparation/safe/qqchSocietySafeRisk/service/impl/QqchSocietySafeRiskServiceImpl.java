package com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRisk;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.vo.QqchSocietySafeRiskVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.mapper.QqchSocietySafeRiskMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.service.IQqchSocietySafeRiskService;
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
 * @date 2023-08-09 09:30:14
 * @remark
 */
@Service
public class QqchSocietySafeRiskServiceImpl implements IQqchSocietySafeRiskService {

    @Autowired
    private QqchSocietySafeRiskMapper qqchSocietySafeRiskMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchSocietySafeRisk getQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk) {
        return qqchSocietySafeRiskMapper.getQqchSocietySafeRisk(qqchSocietySafeRisk);
    }

    @Transactional
    public int insertQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk) {
        qqchSocietySafeRisk.setId(IdWorker.createId());
        qqchSocietySafeRisk.setCreateUser(SecurityUtils.getUserName());
        qqchSocietySafeRisk.setCreateTime(DateUtils.getNowDate());
        return qqchSocietySafeRiskMapper.insertQqchSocietySafeRisk(qqchSocietySafeRisk);
    }



    @Transactional
    public int updateQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk) {
        qqchSocietySafeRisk.setUpdateUser(SecurityUtils.getUserName());
        qqchSocietySafeRisk.setUpdateTime(DateUtils.getNowDate());
        return qqchSocietySafeRiskMapper.updateQqchSocietySafeRisk(qqchSocietySafeRisk);
    }

    @Transactional
    public int updateQqchSocietySafeRiskList(List<QqchSocietySafeRisk> qqchSocietySafeRiskList) {
        for (QqchSocietySafeRisk qqchSocietySafeRisk : qqchSocietySafeRiskList) {
            qqchSocietySafeRisk.setUpdateUser(SecurityUtils.getUserName());
            qqchSocietySafeRisk.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSocietySafeRiskMapper.updateQqchSocietySafeRiskList(qqchSocietySafeRiskList);
    }

    @Transactional
    public int deleteQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk) {
        qqchSocietySafeRisk.setUpdateUser(SecurityUtils.getUserName());
        qqchSocietySafeRisk.setUpdateTime(DateUtils.getNowDate());
        return qqchSocietySafeRiskMapper.deleteQqchSocietySafeRisk(qqchSocietySafeRisk);
    }

    @Transactional
    public int deleteQqchSocietySafeRiskByPks(List<Long> qqchSocietySafeRiskPkList) {
        return qqchSocietySafeRiskMapper.deleteQqchSocietySafeRiskByPks(qqchSocietySafeRiskPkList);
    }

    /**
     * 列表接口
     *
     * @param qqchSocietySafeRisk
     * @return
     */
    public QqchSocietySafeRiskVo getQqchSocietySafeRiskList(QqchSocietySafeRisk qqchSocietySafeRisk) {
        QqchSocietySafeRiskVo vo = new QqchSocietySafeRiskVo();
        BigDecimal version = VersionUtil.getVersion("qqch_society_safe_risk", qqchSocietySafeRisk.getVersion());
        qqchSocietySafeRisk.setVersion(version);
        List<QqchSocietySafeRisk> qqchSocietySafeRiskList = qqchSocietySafeRiskMapper.getQqchSocietySafeRiskList(qqchSocietySafeRisk);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSocietySafeRiskList(qqchSocietySafeRiskList);
        return vo;
    }

    /**
     * 保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchSocietySafeRiskVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchSocietySafeRisk> qqchSocietySafeRiskList = vo.getQqchSocietySafeRiskList();
        if(CollectionUtils.isEmpty(qqchSocietySafeRiskList)){
            return;
        }else {
            //校验数据必填
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


    public int insertQqchSocietySafeRiskList(List<QqchSocietySafeRisk> qqchSocietySafeRiskList,BigDecimal version) {
        //删除旧数据
        QqchSocietySafeRisk qqchSocietySafeRisk1 = new QqchSocietySafeRisk();
        qqchSocietySafeRisk1.setVersion(version);
        qqchSocietySafeRiskMapper.deleteQqchSocietySafeRisk(qqchSocietySafeRisk1);
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchSocietySafeRisk qqchSocietySafeRisk : qqchSocietySafeRiskList) {
            qqchSocietySafeRisk.setValid(valid);
            qqchSocietySafeRisk.setVersion(version);
            qqchSocietySafeRisk.setId(IdWorker.createId());
            qqchSocietySafeRisk.setCreateUser(SecurityUtils.getSysUser().getNickName());
            qqchSocietySafeRisk.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSocietySafeRiskMapper.insertQqchSocietySafeRiskList(qqchSocietySafeRiskList);
    }
}
