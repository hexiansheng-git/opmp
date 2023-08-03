package com.hhwy.pm.qqch.preparation.quality.problem.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemTrain;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemTrainVo;
import com.hhwy.pm.qqch.preparation.quality.problem.mapper.QqchQualityProblemTrainMapper;
import com.hhwy.pm.qqch.preparation.quality.problem.service.IQqchQualityProblemTrainService;
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
 * @date 2023-08-03 14:30:51
 * @remark 9.2.3 质量通病培训策划
 */
@Service
public class QqchQualityProblemTrainServiceImpl implements IQqchQualityProblemTrainService {

    @Autowired
    private QqchQualityProblemTrainMapper qqchQualityProblemTrainMapper;
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
    public QqchQualityProblemTrainVo getQqchQualityProblemTrainList(BigDecimal version) {
        QqchQualityProblemTrainVo vo = new QqchQualityProblemTrainVo();
        version = VersionUtil.getVersion("qqch_quality_problem_train", version);

        QqchQualityProblemTrain qryParam = new QqchQualityProblemTrain();
        qryParam.setVersion(version);
        List<QqchQualityProblemTrain> list = qqchQualityProblemTrainMapper.getQqchQualityProblemTrainList(qryParam);

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
    public void batchSave(QqchQualityProblemTrainVo voParam) {
        // 清空数据库表中数据
        QqchQualityProblemTrain deleteParam = new QqchQualityProblemTrain();
        deleteParam.setVersion(voParam.getVersion());
        qqchQualityProblemTrainMapper.deleteQqchQualityProblemTrain(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchQualityProblemTrain qqchQualityProblemTrain : voParam.getList()) {
                qqchQualityProblemTrain.setId(IdWorker.createId());
                qqchQualityProblemTrain.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchQualityProblemTrain.setValid(Valid.YES);
                }
                qqchQualityProblemTrain.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchQualityProblemTrain.setCreateUserName(SecurityUtils.getUserName());
                qqchQualityProblemTrain.setCreateTime(DateUtils.getNowDate());
            }
            qqchQualityProblemTrainMapper.insertQqchQualityProblemTrainList(voParam.getList());
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
