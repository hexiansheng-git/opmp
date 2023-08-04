package com.hhwy.pm.qqch.preparation.quality.qc.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcImplementPlan;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcTopicList;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.vo.QqchQcImplementPlanVo;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.vo.QqchQcTopicListVo;
import com.hhwy.pm.qqch.preparation.quality.qc.mapper.QqchQcTopicListMapper;
import com.hhwy.pm.qqch.preparation.quality.qc.service.IQqchQcImplementPlanService;
import com.hhwy.pm.qqch.preparation.quality.qc.service.IQqchQcTopicListService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-08-04 10:30:25
 * @remark 9.6.1 QC课题清单
 */
@Service
public class QqchQcTopicListServiceImpl implements IQqchQcTopicListService {

    @Autowired
    private QqchQcTopicListMapper qqchQcTopicListMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchQcImplementPlanService qqchQcImplementPlanService;

    public QqchQcTopicListVo getQqchQcTopicListList(BigDecimal version) {
        QqchQcTopicListVo vo = new QqchQcTopicListVo();
        version = VersionUtil.getVersion("qqch_qc_topic_list", version);

        QqchQcTopicList qryParam = new QqchQcTopicList();
        qryParam.setVersion(version);
        List<QqchQcTopicList> list = qqchQcTopicListMapper.getQqchQcTopicListList(qryParam);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void batchSave(QqchQcTopicListVo voParam) {
        for (QqchQcTopicList qqchQcTopicList : voParam.getList()) {
            qqchQcTopicList.setId(IdWorker.createId());
            qqchQcTopicList.setCreateUser(SecurityUtils.getUserName());
            qqchQcTopicList.setCreateTime(DateUtils.getNowDate());
        }

        // 清空数据库表中QC课题清单数据
        QqchQcTopicList deleteParam = new QqchQcTopicList();
        deleteParam.setVersion(voParam.getVersion());
        qqchQcTopicListMapper.deleteQqchQcTopicList(deleteParam);

        // QC实施计划集合
        List<QqchQcImplementPlan> planList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchQcTopicList qqchQcTopicList : voParam.getList()) {
                if (StringUtils.isBlank(qqchQcTopicList.getTopicCode())) {
                    qqchQcTopicList.setTopicCode(UUID.randomUUID().toString().replaceAll("-", ""));
                }

                qqchQcTopicList.setId(IdWorker.createId());
                qqchQcTopicList.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchQcTopicList.setValid(Valid.YES);
                }
                qqchQcTopicList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchQcTopicList.setCreateUserName(SecurityUtils.getUserName());
                qqchQcTopicList.setCreateTime(DateUtils.getNowDate());

                QqchQcImplementPlan qqchQcImplementPlan = new QqchQcImplementPlan();
                qqchQcImplementPlan.setTopicCode(qqchQcTopicList.getTopicCode());
                qqchQcImplementPlan.setTopicName(qqchQcTopicList.getTopicName());
                qqchQcImplementPlan.setTopicType(qqchQcTopicList.getTopicType());
                qqchQcImplementPlan.setResearchDirection(qqchQcTopicList.getResearchDirection());
                planList.add(qqchQcImplementPlan);

            }
            // 课题清单入库
            qqchQcTopicListMapper.insertQqchQcTopicListList(voParam.getList());

            QqchQcImplementPlanVo qqchQcImplementPlanVo = new QqchQcImplementPlanVo();
            qqchQcImplementPlanVo.setVersion(voParam.getVersion());
            qqchQcImplementPlanVo.setList(planList);
            // 同步到QC实施计划
            qqchQcImplementPlanService.syncData(qqchQcImplementPlanVo);
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
