package com.hhwy.pm.qqch.preparation.safe.danger.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasures;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerSafeMeasuresVo;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerSafeMeasuresMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerSafeMeasuresService;
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
 * @date 2023-08-07 14:23:10
 * @remark 8.3.2 危大工程安全技术措施
 */
@Service
public class QqchDangerSafeMeasuresServiceImpl implements IQqchDangerSafeMeasuresService {

    @Autowired
    private QqchDangerSafeMeasuresMapper qqchDangerSafeMeasuresMapper;
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
    public QqchDangerSafeMeasuresVo getQqchDangerSafeMeasuresList(BigDecimal version) {
        QqchDangerSafeMeasuresVo vo = new QqchDangerSafeMeasuresVo();
        version = VersionUtil.getVersion("qqch_danger_safe_measures", version);

        QqchDangerSafeMeasures qryParam = new QqchDangerSafeMeasures();
        qryParam.setVersion(version);
        List<QqchDangerSafeMeasures> list = qqchDangerSafeMeasuresMapper.getQqchDangerSafeMeasuresList(qryParam);

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
    public void updateQqchDangerSafeMeasuresList(QqchDangerSafeMeasuresVo voParam) {
        for (QqchDangerSafeMeasures qqchDangerSafeMeasures : voParam.getList()) {
            qqchDangerSafeMeasures.setUpdateUser(SecurityUtils.getUserName());
            qqchDangerSafeMeasures.setUpdateTime(DateUtils.getNowDate());
        }
        qqchDangerSafeMeasuresMapper.updateQqchDangerSafeMeasuresList(voParam.getList());

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    /**
     * 同步数据
     *
     * @param voParam
     */
    @Transactional
    public void syncData(QqchDangerSafeMeasuresVo voParam) {
        // 从数据库查出素净
        List<QqchDangerSafeMeasures> dbList = this.getQqchDangerSafeMeasuresList(voParam.getVersion()).getList();

        // 清空数据库表中数据
        QqchDangerSafeMeasures deleteParam = new QqchDangerSafeMeasures();
        deleteParam.setVersion(voParam.getVersion());
        qqchDangerSafeMeasuresMapper.deleteQqchDangerSafeMeasures(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchDangerSafeMeasures qqchDangerSafeMeasures : voParam.getList()) {
                qqchDangerSafeMeasures.setId(IdWorker.createId());
                qqchDangerSafeMeasures.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchDangerSafeMeasures.setValid(Valid.YES);
                }
                qqchDangerSafeMeasures.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchDangerSafeMeasures.setCreateUserName(SecurityUtils.getUserName());
                qqchDangerSafeMeasures.setCreateTime(DateUtils.getNowDate());

                if (!CollectionUtils.isEmpty(dbList)) {
                    for (QqchDangerSafeMeasures db : dbList) {
                        if (db.getSchemeCode().equals(qqchDangerSafeMeasures.getSchemeCode())) {
                            qqchDangerSafeMeasures.setMeasures(db.getMeasures());
                        }
                    }
                }
            }
            qqchDangerSafeMeasuresMapper.insertQqchDangerSafeMeasuresList(voParam.getList());
        }
    }
}
