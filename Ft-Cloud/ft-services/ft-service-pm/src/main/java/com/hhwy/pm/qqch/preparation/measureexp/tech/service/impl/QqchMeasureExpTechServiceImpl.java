package com.hhwy.pm.qqch.preparation.measureexp.tech.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.measureexp.tech.domain.QqchMeasureExpTech;
import com.hhwy.pm.qqch.preparation.measureexp.tech.domain.vo.QqchMeasureExpTechVo;
import com.hhwy.pm.qqch.preparation.measureexp.tech.mapper.QqchMeasureExpTechMapper;
import com.hhwy.pm.qqch.preparation.measureexp.tech.service.IQqchMeasureExpTechService;
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
 * @date 2023-08-04 16:09:57
 * @remark 3.6.3测量技术方案计划、3.7.3试验方案计划
 */
@Service
public class QqchMeasureExpTechServiceImpl implements IQqchMeasureExpTechService {

    @Autowired
    private QqchMeasureExpTechMapper qqchMeasureExpTechMapper;
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
    public QqchMeasureExpTechVo getQqchMeasureExpTechList(BigDecimal version, String type) {
        QqchMeasureExpTechVo vo = new QqchMeasureExpTechVo();
        version = VersionUtil.getVersionByType("qqch_measure_exp_tech", version, type);

        QqchMeasureExpTech qqchMeasureExpTech = new QqchMeasureExpTech();
        qqchMeasureExpTech.setVersion(version);
        // 测量技术方案计划
        if ("1".equals(type)) {
            vo.setType("1");
            vo.setMeasureList(qqchMeasureExpTechMapper.getQqchMeasureExpTechList(qqchMeasureExpTech));
        }
        // 试验方案计划
        if ("2".equals(type)) {
            vo.setType("2");
            vo.setExperimentList(qqchMeasureExpTechMapper.getQqchMeasureExpTechList(qqchMeasureExpTech));
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
    public void insertQqchMeasureExpTechList(QqchMeasureExpTechVo voParam) {
        // 清空数据库表中数据
        QqchMeasureExpTech deleteParam = new QqchMeasureExpTech();
        deleteParam.setVersion(voParam.getVersion());
        deleteParam.setType(voParam.getType());
        qqchMeasureExpTechMapper.deleteQqchMeasureExpTech(deleteParam);

        int sort = 1;
        // 测量技术方案计划
        if ("1".equals(voParam.getType())) {
            if (!CollectionUtils.isEmpty(voParam.getMeasureList())) {
                for (QqchMeasureExpTech measure : voParam.getMeasureList()) {
                    measure.setType("1");
                    measure.setId(IdWorker.createId());
                    measure.setVersion(voParam.getVersion());
                    if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                        measure.setValid(Valid.YES);
                    }
                    measure.setSort(sort++);
                    measure.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    measure.setCreateUserName(SecurityUtils.getUserName());
                    measure.setCreateTime(DateUtils.getNowDate());
                }
                qqchMeasureExpTechMapper.insertQqchMeasureExpTechList(voParam.getMeasureList());
            }
        }

        // 试验方案计划
        if ("2".equals(voParam.getType())) {
            if (!CollectionUtils.isEmpty(voParam.getExperimentList())) {
                for (QqchMeasureExpTech experiment : voParam.getExperimentList()) {
                    experiment.setType("2");
                    experiment.setId(IdWorker.createId());
                    experiment.setVersion(voParam.getVersion());
                    if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                        experiment.setValid(Valid.YES);
                    }
                    experiment.setSort(sort++);
                    experiment.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    experiment.setCreateUserName(SecurityUtils.getUserName());
                    experiment.setCreateTime(DateUtils.getNowDate());
                }
                qqchMeasureExpTechMapper.insertQqchMeasureExpTechList(voParam.getExperimentList());
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
