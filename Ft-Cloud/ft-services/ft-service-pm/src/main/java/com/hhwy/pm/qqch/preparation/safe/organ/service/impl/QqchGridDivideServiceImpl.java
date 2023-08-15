package com.hhwy.pm.qqch.preparation.safe.organ.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.QqchGridDivide;
import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchGridDivideVo;
import com.hhwy.pm.qqch.preparation.safe.organ.mapper.QqchGridDivideMapper;
import com.hhwy.pm.qqch.preparation.safe.organ.service.IQqchGridDivideService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-08-07 13:52:31
 * @remark 8.1.4 格子划分
 */
@Service
public class QqchGridDivideServiceImpl implements IQqchGridDivideService {

    @Autowired
    private QqchGridDivideMapper qqchGridDivideMapper;
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
    public QqchGridDivideVo getQqchGridDivideList(BigDecimal version) {
        QqchGridDivideVo vo = new QqchGridDivideVo();
        version = VersionUtil.getVersion("qqch_grid_divide", version);

        QqchGridDivide qryParam = new QqchGridDivide();
        qryParam.setVersion(version);
        List<QqchGridDivide> list = qqchGridDivideMapper.getQqchGridDivideList(qryParam);

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
    public void batchSave(QqchGridDivideVo voParam) {
        // 清空数据库表中数据
        QqchGridDivide deleteParam = new QqchGridDivide();
        deleteParam.setVersion(voParam.getVersion());
        qqchGridDivideMapper.deleteQqchGridDivide(deleteParam);

        String buttonMark = voParam.getButtonMark();
        if (!CollectionUtils.isEmpty(voParam.getList())) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getList(), ValidationGroups.Save.class);
            }

            for (QqchGridDivide qqchGridDivide : voParam.getList()) {
                qqchGridDivide.setId(IdWorker.createId());
                qqchGridDivide.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchGridDivide.setValid(Valid.YES);
                }
                qqchGridDivide.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchGridDivide.setCreateUserName(SecurityUtils.getUserName());
                qqchGridDivide.setCreateTime(DateUtils.getNowDate());
            }
            qqchGridDivideMapper.insertQqchGridDivideList(voParam.getList());
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
}
