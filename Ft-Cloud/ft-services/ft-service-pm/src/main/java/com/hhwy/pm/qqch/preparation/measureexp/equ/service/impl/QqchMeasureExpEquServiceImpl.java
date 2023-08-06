package com.hhwy.pm.qqch.preparation.measureexp.equ.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.measureexp.equ.domain.QqchMeasureExpEqu;
import com.hhwy.pm.qqch.preparation.measureexp.equ.domain.vo.QqchMeasureExpEquVo;
import com.hhwy.pm.qqch.preparation.measureexp.equ.mapper.QqchMeasureExpEquMapper;
import com.hhwy.pm.qqch.preparation.measureexp.equ.service.IQqchMeasureExpEquService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-08-04 16:12:29
 * @remark 3.6.4测量仪器设备配置计划、3.7.4试验仪器设备配置计划
 */
@Service
public class QqchMeasureExpEquServiceImpl implements IQqchMeasureExpEquService {

    @Autowired
    private QqchMeasureExpEquMapper qqchMeasureExpEquMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    /**
     * 列表
     *
     * @param version
     * @param type
     * @return
     */
    public QqchMeasureExpEquVo getQqchMeasureExpEquList(BigDecimal version, String type) {
        QqchMeasureExpEquVo vo = new QqchMeasureExpEquVo();
        version = VersionUtil.getVersionByType("qqch_measure_exp_equ", version, type);

        QqchMeasureExpEqu qqchMeasureExpEqu = new QqchMeasureExpEqu();
        qqchMeasureExpEqu.setVersion(version);
        // 测量仪器设备配置计划
        if ("1".equals(type)) {
            qqchMeasureExpEqu.setType("1");
            vo.setMeasureList(qqchMeasureExpEquMapper.getQqchMeasureExpEquList(qqchMeasureExpEqu));
        }
        // 试验仪器设备配置计划
        if ("2".equals(type)) {
            qqchMeasureExpEqu.setType("2");
            vo.setExperimentList(qqchMeasureExpEquMapper.getQqchMeasureExpEquList(qqchMeasureExpEqu));
        }

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        return vo;
    }

    /**
     * 保存/确认/提交
     *
     * @param voParam
     */
    @Transactional
    public void insertQqchMeasureExpEquList(QqchMeasureExpEquVo voParam) {
        // 清空数据库表中数据
        QqchMeasureExpEqu deleteParam = new QqchMeasureExpEqu();
        deleteParam.setVersion(voParam.getVersion());
        deleteParam.setType(voParam.getType());
        qqchMeasureExpEquMapper.deleteQqchMeasureExpEqu(deleteParam);

        // 测量仪器设备配置计划
        if ("1".equals(voParam.getType())) {
            if (!CollectionUtils.isEmpty(voParam.getMeasureList())) {
                for (QqchMeasureExpEqu measure : voParam.getMeasureList()) {
                    measure.setType("1");
                    measure.setId(IdWorker.createId());
                    measure.setVersion(voParam.getVersion());
                    if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                        measure.setValid(Valid.YES);
                    }
                    measure.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    measure.setCreateUserName(SecurityUtils.getUserName());
                    measure.setCreateTime(DateUtils.getNowDate());
                }
                qqchMeasureExpEquMapper.insertQqchMeasureExpEquList(voParam.getMeasureList());
            }
        }

        // 试验仪器设备配置计划
        if ("2".equals(voParam.getType())) {
            if (!CollectionUtils.isEmpty(voParam.getExperimentList())) {
                for (QqchMeasureExpEqu experiment : voParam.getExperimentList()) {
                    experiment.setType("2");
                    experiment.setId(IdWorker.createId());
                    experiment.setVersion(voParam.getVersion());
                    if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                        experiment.setValid(Valid.YES);
                    }
                    experiment.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    experiment.setCreateUserName(SecurityUtils.getUserName());
                    experiment.setCreateTime(DateUtils.getNowDate());
                }
                qqchMeasureExpEquMapper.insertQqchMeasureExpEquList(voParam.getExperimentList());
            }

            String buttonMark = voParam.getButtonMark();
            if (ButtonMark.CONFIRM.equals(buttonMark)) {
                // 插入确认状态
                String menuId = voParam.getMenuId();
                String stageIdentity = voParam.getStageIdentity();
                qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
            }
        }
    }
}
