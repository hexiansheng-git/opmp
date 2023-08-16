package com.hhwy.pm.qqch.preparation.safe.monitor.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.monitor.domain.QqchMonitorDataParam;
import com.hhwy.pm.qqch.preparation.safe.monitor.domain.vo.QqchMonitorDataParamVo;
import com.hhwy.pm.qqch.preparation.safe.monitor.mapper.QqchMonitorDataParamMapper;
import com.hhwy.pm.qqch.preparation.safe.monitor.service.IQqchMonitorDataParamService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.tree.TreeUtil;
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
 * @date 2023-08-10 16:50:46
 * @remark 8.12.2 监控数据参数
 */
@Service
public class QqchMonitorDataParamServiceImpl implements IQqchMonitorDataParamService {

    @Autowired
    private QqchMonitorDataParamMapper qqchMonitorDataParamMapper;
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
    public QqchMonitorDataParamVo getQqchMonitorDataParamList(BigDecimal version) {
        QqchMonitorDataParamVo vo = new QqchMonitorDataParamVo();
        version = VersionUtil.getVersion("qqch_monitor_data_param", version);

        QqchMonitorDataParam qryParam = new QqchMonitorDataParam();
        qryParam.setVersion(version);
        List<QqchMonitorDataParam> list = qqchMonitorDataParamMapper.getQqchMonitorDataParamList(qryParam);

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setTreeList(TreeUtil.build(list, null));
        return vo;
    }


    /**
     * 保存/确认/提交
     *
     * @param voParam
     */
    @Transactional
    public void batchSave(QqchMonitorDataParamVo voParam) {
        // 清空数据库表中数据
        QqchMonitorDataParam deleteParam = new QqchMonitorDataParam();
        deleteParam.setVersion(voParam.getVersion());
        qqchMonitorDataParamMapper.deleteQqchMonitorDataParam(deleteParam);

        String buttonMark = voParam.getButtonMark();
        if (!CollectionUtils.isEmpty(voParam.getTreeList())) {
            // 校验非空
            if (!ButtonMark.SAVE.equals(buttonMark)) {
                JyDetailsUtil.jyDetails(voParam.getTreeList(), ValidationGroups.Save.class);
            }

            // 树转list
            List<QqchMonitorDataParam> list = TreeUtil.treeToList(voParam.getTreeList());

            for (QqchMonitorDataParam qqchMonitorDataParam : list) {
                qqchMonitorDataParam.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchMonitorDataParam.setValid(Valid.YES);
                }
                qqchMonitorDataParam.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchMonitorDataParam.setCreateUserName(SecurityUtils.getUserName());
                qqchMonitorDataParam.setCreateTime(DateUtils.getNowDate());
            }
            qqchMonitorDataParamMapper.insertQqchMonitorDataParamList(list);
        }

        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
}
