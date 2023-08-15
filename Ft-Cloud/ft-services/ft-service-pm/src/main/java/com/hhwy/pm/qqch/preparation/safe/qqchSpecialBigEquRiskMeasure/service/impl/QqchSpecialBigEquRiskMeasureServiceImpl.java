package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.domain.QqchSpecialBigEquRiskMeasure;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.domain.vo.QqchSpecialBigEquRiskMeasureVo;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.mapper.QqchSpecialBigEquRiskMeasureMapper;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquRiskMeasure.service.IQqchSpecialBigEquRiskMeasureService;
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
 * @date 2023-08-08 15:22:12
 * @remark
 */
@Service
public class QqchSpecialBigEquRiskMeasureServiceImpl implements IQqchSpecialBigEquRiskMeasureService {

    @Autowired
    private QqchSpecialBigEquRiskMeasureMapper qqchSpecialBigEquRiskMeasureMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchSpecialBigEquRiskMeasure getQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure) {
        return qqchSpecialBigEquRiskMeasureMapper.getQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasure);
    }

    @Transactional
    public int insertQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure) {
        qqchSpecialBigEquRiskMeasure.setId(IdWorker.createId());
        qqchSpecialBigEquRiskMeasure.setCreateUser(SecurityUtils.getUserName());
        qqchSpecialBigEquRiskMeasure.setCreateTime(DateUtils.getNowDate());
        return qqchSpecialBigEquRiskMeasureMapper.insertQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasure);
    }



    @Transactional
    public int updateQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure) {
        qqchSpecialBigEquRiskMeasure.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialBigEquRiskMeasure.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialBigEquRiskMeasureMapper.updateQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasure);
    }

    @Transactional
    public int updateQqchSpecialBigEquRiskMeasureList(List<QqchSpecialBigEquRiskMeasure> qqchSpecialBigEquRiskMeasureList) {
        for (QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure : qqchSpecialBigEquRiskMeasureList) {
            qqchSpecialBigEquRiskMeasure.setUpdateUser(SecurityUtils.getUserName());
            qqchSpecialBigEquRiskMeasure.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSpecialBigEquRiskMeasureMapper.updateQqchSpecialBigEquRiskMeasureList(qqchSpecialBigEquRiskMeasureList);
    }

    @Transactional
    public int deleteQqchSpecialBigEquRiskMeasure(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure) {
        qqchSpecialBigEquRiskMeasure.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialBigEquRiskMeasure.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialBigEquRiskMeasureMapper.deleteQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasure);
    }

    @Transactional
    public int deleteQqchSpecialBigEquRiskMeasureByPks(List<Long> qqchSpecialBigEquRiskMeasurePkList) {
        return qqchSpecialBigEquRiskMeasureMapper.deleteQqchSpecialBigEquRiskMeasureByPks(qqchSpecialBigEquRiskMeasurePkList);
    }

    /**
     * 列表接口
     * @param qqchSpecialBigEquRiskMeasure
     * @return
     */
    public QqchSpecialBigEquRiskMeasureVo getQqchSpecialBigEquRiskMeasureList(QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure) {
        QqchSpecialBigEquRiskMeasureVo vo = new QqchSpecialBigEquRiskMeasureVo();
        BigDecimal version = VersionUtil.getVersion("qqch_special_big_equ_risk_measure", qqchSpecialBigEquRiskMeasure.getVersion());
        qqchSpecialBigEquRiskMeasure.setVersion(version);
        List<QqchSpecialBigEquRiskMeasure> qqchSpecialBigEquRiskMeasureList = qqchSpecialBigEquRiskMeasureMapper.getQqchSpecialBigEquRiskMeasureList(qqchSpecialBigEquRiskMeasure);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(qqchSpecialBigEquRiskMeasureList);
        return vo;
    }

    /**
     * 保存/确认/提交
     * @param vo
     */
    @Override
    public void save(QqchSpecialBigEquRiskMeasureVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchSpecialBigEquRiskMeasure> riskMeasureList = vo.getList();
        if(CollectionUtils.isEmpty(riskMeasureList)){
            return;
        }else {
            //校验数据必填
            if("1".equals(vo.getButtonMark())||"2".equals(vo.getButtonMark())){//确认
                JyDetailsUtil.jyDetails(riskMeasureList, ValidationGroups.Save.class);
            }
        }
        this.insertQqchSpecialBigEquRiskMeasureList(riskMeasureList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    public int insertQqchSpecialBigEquRiskMeasureList(List<QqchSpecialBigEquRiskMeasure> qqchSpecialBigEquRiskMeasureList,BigDecimal version) {
        //删除旧数据
        QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure1 = new QqchSpecialBigEquRiskMeasure();
        qqchSpecialBigEquRiskMeasure1.setVersion(version);
        qqchSpecialBigEquRiskMeasureMapper.deleteQqchSpecialBigEquRiskMeasure(qqchSpecialBigEquRiskMeasure1);
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchSpecialBigEquRiskMeasure qqchSpecialBigEquRiskMeasure : qqchSpecialBigEquRiskMeasureList) {
            qqchSpecialBigEquRiskMeasure.setValid(valid);
            qqchSpecialBigEquRiskMeasure.setVersion(version);
            qqchSpecialBigEquRiskMeasure.setId(IdWorker.createId());
            qqchSpecialBigEquRiskMeasure.setCreateUser(SecurityUtils.getSysUser().getNickName());
            qqchSpecialBigEquRiskMeasure.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSpecialBigEquRiskMeasureMapper.insertQqchSpecialBigEquRiskMeasureList(qqchSpecialBigEquRiskMeasureList);
    }
}
