package com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.domain.QqchQualityRiskControlMeasures;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.domain.vo.QqchQualityRiskControlMeasuresVo;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.mapper.QqchQualityRiskControlMeasuresMapper;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskControlMeasures.service.IQqchQualityRiskControlMeasuresService;
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
 * @date 2023-08-04 11:20:54
 * @remark
 */
@Service
public class QqchQualityRiskControlMeasuresServiceImpl implements IQqchQualityRiskControlMeasuresService {

    @Autowired
    private QqchQualityRiskControlMeasuresMapper qqchQualityRiskControlMeasuresMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchQualityRiskControlMeasures getQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures) {
        return qqchQualityRiskControlMeasuresMapper.getQqchQualityRiskControlMeasures(qqchQualityRiskControlMeasures);
    }


    @Transactional
    public int insertQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures) {
        qqchQualityRiskControlMeasures.setId(IdWorker.createId());
        qqchQualityRiskControlMeasures.setCreateUser(SecurityUtils.getUserName());
        qqchQualityRiskControlMeasures.setCreateTime(DateUtils.getNowDate());
        return qqchQualityRiskControlMeasuresMapper.insertQqchQualityRiskControlMeasures(qqchQualityRiskControlMeasures);
    }



    @Transactional
    public int updateQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures) {
        qqchQualityRiskControlMeasures.setUpdateUser(SecurityUtils.getUserName());
        qqchQualityRiskControlMeasures.setUpdateTime(DateUtils.getNowDate());
        return qqchQualityRiskControlMeasuresMapper.updateQqchQualityRiskControlMeasures(qqchQualityRiskControlMeasures);
    }

    @Transactional
    public int updateQqchQualityRiskControlMeasuresList(List<QqchQualityRiskControlMeasures> qqchQualityRiskControlMeasuresList) {
        for (QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures : qqchQualityRiskControlMeasuresList) {
            qqchQualityRiskControlMeasures.setUpdateUser(SecurityUtils.getUserName());
            qqchQualityRiskControlMeasures.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchQualityRiskControlMeasuresMapper.updateQqchQualityRiskControlMeasuresList(qqchQualityRiskControlMeasuresList);
    }

    @Transactional
    public int deleteQqchQualityRiskControlMeasures(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures) {
//        qqchQualityRiskControlMeasures.setUpdateUser(SecurityUtils.getUserName());
//        qqchQualityRiskControlMeasures.setUpdateTime(DateUtils.getNowDate());
        return qqchQualityRiskControlMeasuresMapper.deleteQqchQualityRiskControlMeasures(qqchQualityRiskControlMeasures);
    }

    @Transactional
    public int deleteQqchQualityRiskControlMeasuresByPks(List<Long> qqchQualityRiskControlMeasuresPkList) {
        return qqchQualityRiskControlMeasuresMapper.deleteQqchQualityRiskControlMeasuresByPks(qqchQualityRiskControlMeasuresPkList);
    }

    /**
     *  列表接口
     * @param qqchQualityRiskControlMeasures
     * @return
     */
    @Override
    public QqchQualityRiskControlMeasuresVo getQqchQualityRiskControlMeasuresList(QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures) {
        QqchQualityRiskControlMeasuresVo vo = new QqchQualityRiskControlMeasuresVo();

        BigDecimal version = qqchQualityRiskControlMeasures.getVersion();
        version = VersionUtil.getVersion("qqch_quality_risk_control_measures", version);

        qqchQualityRiskControlMeasures.setVersion(version);
        List<QqchQualityRiskControlMeasures> qqchQualityRiskControlMeasuresList = qqchQualityRiskControlMeasuresMapper.getQqchQualityRiskControlMeasuresList(qqchQualityRiskControlMeasures);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchQualityRiskControlMeasuresList(qqchQualityRiskControlMeasuresList);
        return vo;
    }

    /**
     *  保存/确认/提交
     * @param vo
     */
    @Override
    @Transactional
    public void save(QqchQualityRiskControlMeasuresVo vo) {
        String buttonMark = vo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = vo.getVersion();
        List<QqchQualityRiskControlMeasures> qqchQualityRiskControlMeasuresList = vo.getQqchQualityRiskControlMeasuresList();
        this.insertQqchQualityRiskControlMeasuresList(qqchQualityRiskControlMeasuresList, version);

        if (CollectionUtils.isEmpty(qqchQualityRiskControlMeasuresList)) {
            return;
        }
        //处理确认状态是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }


    public void insertQqchQualityRiskControlMeasuresList(List<QqchQualityRiskControlMeasures> qqchQualityRiskControlMeasuresList,BigDecimal version) {
        //删除旧数据
        QqchQualityRiskControlMeasures qqchQualityRiskControlMeasures = new QqchQualityRiskControlMeasures();
        qqchQualityRiskControlMeasures.setVersion(version);
        qqchQualityRiskControlMeasuresMapper.deleteQqchQualityRiskControlMeasures(qqchQualityRiskControlMeasures);

        if (CollectionUtils.isEmpty(qqchQualityRiskControlMeasuresList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchQualityRiskControlMeasures riskControlMeasures : qqchQualityRiskControlMeasuresList) {
            riskControlMeasures.setId(IdWorker.createId());
            riskControlMeasures.setValid(valid);
            riskControlMeasures.setVersion(version);
            riskControlMeasures.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            riskControlMeasures.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            riskControlMeasures.setCreateTime(DateUtils.getNowDate());
        }
        qqchQualityRiskControlMeasuresMapper.insertQqchQualityRiskControlMeasuresList(qqchQualityRiskControlMeasuresList);
    }

    public void insetrList(List<QqchQualityRiskControlMeasures> qqchQualityRiskControlMeasuresList,BigDecimal version) {
        if (CollectionUtils.isEmpty(qqchQualityRiskControlMeasuresList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchQualityRiskControlMeasures riskControlMeasures : qqchQualityRiskControlMeasuresList) {
            riskControlMeasures.setId(IdWorker.createId());
            riskControlMeasures.setValid(valid);
            riskControlMeasures.setVersion(version);
            riskControlMeasures.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            riskControlMeasures.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            riskControlMeasures.setCreateTime(DateUtils.getNowDate());
        }
        qqchQualityRiskControlMeasuresMapper.insertQqchQualityRiskControlMeasuresList(qqchQualityRiskControlMeasuresList);
    }
}
