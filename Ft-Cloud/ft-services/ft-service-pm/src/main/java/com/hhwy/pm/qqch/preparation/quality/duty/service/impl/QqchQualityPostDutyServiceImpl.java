package com.hhwy.pm.qqch.preparation.quality.duty.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.QqchQualityPostDuty;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.vo.QqchQualityPostDutyVo;
import com.hhwy.pm.qqch.preparation.quality.duty.mapper.QqchQualityPostDutyMapper;
import com.hhwy.pm.qqch.preparation.quality.duty.service.IQqchQualityPostDutyService;
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
 * @date 2023-08-03 14:28:57
 * @remark 9.1.1 质量岗位职责
 */
@Service
public class QqchQualityPostDutyServiceImpl implements IQqchQualityPostDutyService {

    @Autowired
    private QqchQualityPostDutyMapper qqchQualityPostDutyMapper;
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
    public QqchQualityPostDutyVo getQqchQualityPostDutyList(BigDecimal version) {
        QqchQualityPostDutyVo vo = new QqchQualityPostDutyVo();
        version = VersionUtil.getVersion("qqch_quality_post_duty", version);

        QqchQualityPostDuty qryParam = new QqchQualityPostDuty();
        qryParam.setVersion(version);
        List<QqchQualityPostDuty> list = qqchQualityPostDutyMapper.getQqchQualityPostDutyList(qryParam);

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
    public void batchSave(QqchQualityPostDutyVo voParam) {
        // 清空数据库表中数据
        QqchQualityPostDuty deleteParam = new QqchQualityPostDuty();
        deleteParam.setVersion(voParam.getVersion());
        qqchQualityPostDutyMapper.deleteQqchQualityPostDuty(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchQualityPostDuty qqchQualityPostDuty : voParam.getList()) {
                qqchQualityPostDuty.setId(IdWorker.createId());
                qqchQualityPostDuty.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchQualityPostDuty.setValid(Valid.YES);
                }
                qqchQualityPostDuty.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchQualityPostDuty.setCreateUserName(SecurityUtils.getUserName());
                qqchQualityPostDuty.setCreateTime(DateUtils.getNowDate());
            }
            qqchQualityPostDutyMapper.insertQqchQualityPostDutyList(voParam.getList());
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
