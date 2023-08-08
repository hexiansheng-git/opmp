package com.hhwy.pm.qqch.preparation.safe.danger.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerList;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerProcessControlPlan;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasures;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerListVo;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerProcessControlPlanVo;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerSafeMeasuresVo;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerListMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerListService;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerProcessControlPlanService;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerSafeMeasuresService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-08-07 14:22:57
 * @remark 8.3.1 危大工程清单
 */
@Service
public class QqchDangerListServiceImpl implements IQqchDangerListService {

    @Autowired
    private QqchDangerListMapper qqchDangerListMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchDangerSafeMeasuresService qqchDangerSafeMeasuresService;
    @Autowired
    private IQqchDangerProcessControlPlanService qqchDangerProcessControlPlanService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    public QqchDangerListVo getQqchDangerListList(BigDecimal version) {
        QqchDangerListVo vo = new QqchDangerListVo();
        version = VersionUtil.getVersion("qqch_danger_list", version);

        QqchDangerList qryParam = new QqchDangerList();
        qryParam.setVersion(version);
        List<QqchDangerList> list = qqchDangerListMapper.getQqchDangerListList(qryParam);

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
    public void updateQqchDangerListList(QqchDangerListVo voParam) {
        // 8.3.2 危大工程安全技术措施
        QqchDangerSafeMeasuresVo qqchDangerSafeMeasuresVo = new QqchDangerSafeMeasuresVo();
        List<QqchDangerSafeMeasures> safeList = new ArrayList<>();

        // 8.3.3 危大工程过程管控策划
        QqchDangerProcessControlPlanVo qqchDangerProcessControlPlanVo = new QqchDangerProcessControlPlanVo();
        List<QqchDangerProcessControlPlan> processList = new ArrayList<>();

        for (QqchDangerList qqchDangerList : voParam.getList()) {
            qqchDangerList.setUpdateUser(SecurityUtils.getUserName());
            qqchDangerList.setUpdateTime(DateUtils.getNowDate());

            QqchDangerSafeMeasures qqchDangerSafeMeasures = new QqchDangerSafeMeasures();
            qqchDangerSafeMeasures.setSchemeCode(qqchDangerList.getSchemeCode());
            qqchDangerSafeMeasures.setSchemeName(qqchDangerList.getSchemeName());
            qqchDangerSafeMeasures.setWbsCode(qqchDangerList.getWbsCode());
            qqchDangerSafeMeasures.setWbsName(qqchDangerList.getWbsName());
            safeList.add(qqchDangerSafeMeasures);
            qqchDangerSafeMeasuresVo.setList(safeList);
            qqchDangerSafeMeasuresVo.setVersion(voParam.getVersion());

            QqchDangerProcessControlPlan qqchDangerProcessControlPlan = new QqchDangerProcessControlPlan();
            qqchDangerProcessControlPlan.setSchemeCode(qqchDangerList.getSchemeCode());
            qqchDangerProcessControlPlan.setSchemeName(qqchDangerList.getSchemeName());
            processList.add(qqchDangerProcessControlPlan);
            qqchDangerProcessControlPlanVo.setList(processList);
            qqchDangerProcessControlPlanVo.setVersion(voParam.getVersion());
        }
        qqchDangerListMapper.updateQqchDangerListList(voParam.getList());

        // 同步到8.3.2 危大工程安全技术措施
        qqchDangerSafeMeasuresService.syncData(qqchDangerSafeMeasuresVo);

        // 同步到8.3.3 危大工程过程管控策划
        qqchDangerProcessControlPlanService.syncData(qqchDangerProcessControlPlanVo);

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
    public void syncData(QqchDangerListVo voParam) {
        // 从数据库查出数据
        List<QqchDangerList> dbList = this.getQqchDangerListList(voParam.getVersion()).getList();

        // 清空数据库表中数据
        QqchDangerList deleteParam = new QqchDangerList();
        deleteParam.setVersion(voParam.getVersion());
        qqchDangerListMapper.deleteQqchDangerList(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            for (QqchDangerList qqchDangerList : voParam.getList()) {
                qqchDangerList.setId(IdWorker.createId());
                qqchDangerList.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchDangerList.setValid(Valid.YES);
                }
                qqchDangerList.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchDangerList.setCreateUserName(SecurityUtils.getUserName());
                qqchDangerList.setCreateTime(DateUtils.getNowDate());

                if (!CollectionUtils.isEmpty(dbList)) {
                    for (QqchDangerList db : dbList) {
                        if (db.getSchemeCode().equals(qqchDangerList.getSchemeCode())) {
                            qqchDangerList.setDecisionCondition(db.getDecisionCondition());
                        }
                    }
                }
            }
            qqchDangerListMapper.insertQqchDangerListList(voParam.getList());
        }
    }
}
