package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchKeyDifficultConstructionBrief;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchKeyDifficultConstructionBriefVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchKeyDifficultConstructionBriefMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchKeyDifficultConstructionBriefService;
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
 * @date 2023-07-17 15:29:49
 * @remark 3.4.4重难点分项施工方案简述
 */
@Service
public class QqchKeyDifficultConstructionBriefServiceImpl implements IQqchKeyDifficultConstructionBriefService {

    @Autowired
    private QqchKeyDifficultConstructionBriefMapper qqchKeyDifficultConstructionBriefMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;

    public QqchKeyDifficultConstructionBriefVo getQqchKeyDifficultConstructionBriefList(BigDecimal version) {
        QqchKeyDifficultConstructionBriefVo vo = new QqchKeyDifficultConstructionBriefVo();
        version = VersionUtil.getVersion("qqch_key_difficult_construction_brief", version);
        vo.setVersion(version);

        QqchKeyDifficultConstructionBrief qryParam = new QqchKeyDifficultConstructionBrief();
        qryParam.setVersion(version);
        List<QqchKeyDifficultConstructionBrief> list = qqchKeyDifficultConstructionBriefMapper
            .getQqchKeyDifficultConstructionBriefList(qryParam);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(list);
        return vo;
    }

    @Transactional
    public void batchSave(QqchKeyDifficultConstructionBriefVo qqchKeyDifficultConstructionBriefVo) {
        // 先批量删除当前版本所有数据
        QqchKeyDifficultConstructionBrief deleteParam = new QqchKeyDifficultConstructionBrief();
        deleteParam.setVersion(qqchKeyDifficultConstructionBriefVo.getVersion());
        deleteParam.setDelFlag("1");
        qqchKeyDifficultConstructionBriefMapper.updateQqchKeyDifficultConstructionBrief(deleteParam);

        if (CollectionUtils.isEmpty(qqchKeyDifficultConstructionBriefVo.getList())) {
            return;
        }

        for (QqchKeyDifficultConstructionBrief brief : qqchKeyDifficultConstructionBriefVo.getList()) {
            brief.setId(IdWorker.createId());
            brief.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            brief.setCreateUserName(SecurityUtils.getUserName());
            brief.setCreateTime(DateUtils.getNowDate());

            brief.setVersion(qqchKeyDifficultConstructionBriefVo.getVersion());
            if (qqchKeyDifficultConstructionBriefVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                brief.setValid(Valid.YES);
            }
        }

        qqchKeyDifficultConstructionBriefMapper
            .insertQqchKeyDifficultConstructionBriefList(qqchKeyDifficultConstructionBriefVo.getList());

        String buttonMark = qqchKeyDifficultConstructionBriefVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = qqchKeyDifficultConstructionBriefVo.getMenuId();
            String stageIdentity = qqchKeyDifficultConstructionBriefVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Override
    public List<QqchKeyDifficultConstructionBrief> getByWbsCodes(String[] wbsCodes) {
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_key_difficult_construction_brief");
        return qqchKeyDifficultConstructionBriefMapper.getByWbsCodes(wbsCodes, maxVersion);
    }
}
