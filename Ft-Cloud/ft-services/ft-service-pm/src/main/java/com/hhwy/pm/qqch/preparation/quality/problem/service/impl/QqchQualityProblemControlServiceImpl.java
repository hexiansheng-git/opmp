package com.hhwy.pm.qqch.preparation.quality.problem.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemControl;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemControlVo;
import com.hhwy.pm.qqch.preparation.quality.problem.mapper.QqchQualityProblemControlMapper;
import com.hhwy.pm.qqch.preparation.quality.problem.service.IQqchQualityProblemControlService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:43
 * @remark 9.2.2 质量通病控制措施
 */
@Service
public class QqchQualityProblemControlServiceImpl implements IQqchQualityProblemControlService {

    @Autowired
    private QqchQualityProblemControlMapper qqchQualityProblemControlMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchQualityProblemControlVo getQqchQualityProblemControlList(BigDecimal version) {
        QqchQualityProblemControlVo vo = new QqchQualityProblemControlVo();
        version = VersionUtil.getVersion("qqch_quality_problem_list", version);

        QqchQualityProblemControl qryParam = new QqchQualityProblemControl();
        qryParam.setVersion(version);
        List<QqchQualityProblemControl> list =
            qqchQualityProblemControlMapper.getQqchQualityProblemControlList(qryParam);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    /**
     * 保存/确认/提交
     *
     * @param voParam
     * @return
     */
    @Transactional
    public void updateQqchQualityProblemControlList(QqchQualityProblemControlVo voParam) {
        for (QqchQualityProblemControl qqchQualityProblemControl : voParam.getList()) {
            qqchQualityProblemControl.setUpdateUser(SecurityUtils.getUserName());
            qqchQualityProblemControl.setUpdateTime(DateUtils.getNowDate());
        }
        qqchQualityProblemControlMapper.updateQqchQualityProblemControlList(voParam.getList());

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    /**
     * 质量通病清单数据保存时，将数据同步过来
     *
     * @param voParam
     */
    @Transactional
    public void syncData(QqchQualityProblemControlVo voParam) {
        // 查询数据库表中数据
        QqchQualityProblemControl control = new QqchQualityProblemControl();
        control.setVersion(voParam.getVersion());
        List<QqchQualityProblemControl> dbList = qqchQualityProblemControlMapper
            .getQqchQualityProblemControlList(control);

        // 清空数据库表中数据
        QqchQualityProblemControl deleteParam = new QqchQualityProblemControl();
        deleteParam.setVersion(voParam.getVersion());
        qqchQualityProblemControlMapper.deleteQqchQualityProblemControl(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchQualityProblemControl qqchQualityProblemControl : voParam.getList()) {
                qqchQualityProblemControl.setId(IdWorker.createId());
                qqchQualityProblemControl.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchQualityProblemControl.setValid(Valid.YES);
                }
                qqchQualityProblemControl.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchQualityProblemControl.setCreateUserName(SecurityUtils.getUserName());
                qqchQualityProblemControl.setCreateTime(DateUtils.getNowDate());

                if (!CollectionUtils.isEmpty(dbList)) {
                    for (QqchQualityProblemControl problemControl : dbList) {
                        if (problemControl.getProblemCode().equals(qqchQualityProblemControl.getProblemCode())) {
                            qqchQualityProblemControl.setCauseAnalysis(problemControl.getCauseAnalysis());
                            qqchQualityProblemControl.setControlMeasures(problemControl.getControlMeasures());
                            qqchQualityProblemControl.setSchemeFinalizeTime(problemControl.getSchemeFinalizeTime());
                            qqchQualityProblemControl
                                .setThirdDiscloseFinishTime(problemControl.getThirdDiscloseFinishTime());
                            qqchQualityProblemControl.setTechnicalTrainTime(problemControl.getTechnicalTrainTime());
                            qqchQualityProblemControl.setPlanImplementTime(problemControl.getPlanImplementTime());
                            qqchQualityProblemControl.setDirectorId(problemControl.getDirectorId());
                            qqchQualityProblemControl.setDirector(problemControl.getDirector());
                        }
                    }
                }
            }
            qqchQualityProblemControlMapper.insertQqchQualityProblemControlList(voParam.getList());
        }
    }
}
