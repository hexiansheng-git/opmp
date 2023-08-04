package com.hhwy.pm.qqch.preparation.quality.problem.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemControl;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemList;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemControlVo;
import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemListVo;
import com.hhwy.pm.qqch.preparation.quality.problem.mapper.QqchQualityProblemListMapper;
import com.hhwy.pm.qqch.preparation.quality.problem.service.IQqchQualityProblemControlService;
import com.hhwy.pm.qqch.preparation.quality.problem.service.IQqchQualityProblemListService;
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
 * @date 2023-08-03 14:30:34
 * @remark 9.2.1 质量通病清单
 */
@Service
public class QqchQualityProblemListServiceImpl implements IQqchQualityProblemListService {

    @Autowired
    private QqchQualityProblemListMapper qqchQualityProblemListMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchQualityProblemControlService qqchQualityProblemControlService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchQualityProblemListVo getQqchQualityProblemListList(BigDecimal version) {
        QqchQualityProblemListVo vo = new QqchQualityProblemListVo();
        version = VersionUtil.getVersion("qqch_quality_problem_list", version);

        QqchQualityProblemList qryParam = new QqchQualityProblemList();
        qryParam.setVersion(version);
        List<QqchQualityProblemList> list = qqchQualityProblemListMapper.getQqchQualityProblemListList(qryParam);

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
    public void batchSave(QqchQualityProblemListVo voParam) {
        // 清空数据库表中数据
        QqchQualityProblemList deleteParam = new QqchQualityProblemList();
        deleteParam.setVersion(voParam.getVersion());
        qqchQualityProblemListMapper.deleteQqchQualityProblemList(deleteParam);

        // 质量通病控制措施集合
        List<QqchQualityProblemControl> controlList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchQualityProblemList qqchQualityProblemList : voParam.getList()) {
                if (StringUtils.isBlank(qqchQualityProblemList.getProblemCode())) {
                    qqchQualityProblemList.setProblemCode(UUID.randomUUID().toString().replaceAll("-", ""));
                }
                qqchQualityProblemList.setId(IdWorker.createId());
                qqchQualityProblemList.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchQualityProblemList.setValid(Valid.YES);
                }
                qqchQualityProblemList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchQualityProblemList.setCreateUserName(SecurityUtils.getUserName());
                qqchQualityProblemList.setCreateTime(DateUtils.getNowDate());

                QqchQualityProblemControl qqchQualityProblemControl = new QqchQualityProblemControl();
                qqchQualityProblemControl.setProblemCode(qqchQualityProblemList.getProblemCode());
                qqchQualityProblemControl.setProblemName(qqchQualityProblemList.getProblemName());
                qqchQualityProblemControl.setControlMeasures(qqchQualityProblemList.getControlMeasures());
                controlList.add(qqchQualityProblemControl);

            }
            // 质量通病清单入库
            qqchQualityProblemListMapper.insertQqchQualityProblemListList(voParam.getList());

            QqchQualityProblemControlVo qqchQualityProblemControlVo = new QqchQualityProblemControlVo();
            qqchQualityProblemControlVo.setVersion(voParam.getVersion());
            qqchQualityProblemControlVo.setList(controlList);
            // 同步到质量通病控制措施
            qqchQualityProblemControlService.syncData(qqchQualityProblemControlVo);

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
