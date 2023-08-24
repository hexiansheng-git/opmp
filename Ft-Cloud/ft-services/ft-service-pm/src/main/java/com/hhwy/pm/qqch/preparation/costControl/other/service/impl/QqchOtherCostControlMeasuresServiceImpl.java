package com.hhwy.pm.qqch.preparation.costControl.other.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.costControl.other.domain.QqchOtherCostControlMeasures;
import com.hhwy.pm.qqch.preparation.costControl.other.domain.vo.QqchOtherCostControlMeasuresVo;
import com.hhwy.pm.qqch.preparation.costControl.other.mapper.QqchOtherCostControlMeasuresMapper;
import com.hhwy.pm.qqch.preparation.costControl.other.service.IQqchOtherCostControlMeasuresService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.tree.TreeUtil;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-08-11 09:38:56
 * @remark 5.5 其他成本管控工作安排及措施
 */
@Service
public class QqchOtherCostControlMeasuresServiceImpl implements IQqchOtherCostControlMeasuresService {

    @Autowired
    private QqchOtherCostControlMeasuresMapper qqchOtherCostControlMeasuresMapper;
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
    public QqchOtherCostControlMeasuresVo getQqchOtherCostControlMeasuresList(BigDecimal version) {
        QqchOtherCostControlMeasuresVo vo = new QqchOtherCostControlMeasuresVo();
        version = VersionUtil.getVersion("qqch_other_cost_control_measures", version);

        QqchOtherCostControlMeasures qryParam = new QqchOtherCostControlMeasures();
        qryParam.setVersion(version);
        List<QqchOtherCostControlMeasures> list = qqchOtherCostControlMeasuresMapper
            .getQqchOtherCostControlMeasuresList(qryParam);

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
    public void batchSave(QqchOtherCostControlMeasuresVo voParam) {
        // 清空数据库表中数据
        QqchOtherCostControlMeasures deleteParam = new QqchOtherCostControlMeasures();
        deleteParam.setVersion(voParam.getVersion());
        qqchOtherCostControlMeasuresMapper.deleteQqchOtherCostControlMeasures(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getTreeList())) {
            // 树转list
            List<QqchOtherCostControlMeasures> list = TreeUtil.treeToList(voParam.getTreeList());

            for (QqchOtherCostControlMeasures qqchOtherCostControlMeasures : list) {
                qqchOtherCostControlMeasures.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    qqchOtherCostControlMeasures.setValid(Valid.YES);
                }
                qqchOtherCostControlMeasures.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchOtherCostControlMeasures.setCreateUserName(SecurityUtils.getUserName());
                qqchOtherCostControlMeasures.setCreateTime(DateUtils.getNowDate());
            }
            qqchOtherCostControlMeasuresMapper.insertQqchOtherCostControlMeasuresList(list);
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
