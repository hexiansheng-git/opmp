package com.hhwy.pm.qqch.preparation.measureexp.beton.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.QqchExpBeton;
import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.vo.QqchExpBetonVo;
import com.hhwy.pm.qqch.preparation.measureexp.beton.mapper.QqchExpBetonMapper;
import com.hhwy.pm.qqch.preparation.measureexp.beton.service.IQqchExpBetonService;
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
 * @date 2023-08-04 16:12:49
 * @remark 3.7.5混凝土配合比
 */
@Service
public class QqchExpBetonServiceImpl implements IQqchExpBetonService {

    @Autowired
    private QqchExpBetonMapper qqchExpBetonMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;

    /**
     * 树列表
     *
     * @param version
     * @return
     */
    public QqchExpBetonVo getTreeList(BigDecimal version) {
        QqchExpBetonVo vo = new QqchExpBetonVo();
        version = VersionUtil.getVersion("qqch_exp_beton", version);
        vo.setVersion(version);

        QqchExpBeton qryParam = new QqchExpBeton();
        qryParam.setVersion(version);
        List<QqchExpBeton> list = qqchExpBetonMapper.getQqchExpBetonList(qryParam);
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
    public void batchSave(QqchExpBetonVo voParam) {
        // 先批量删除当前版本所有数据
        QqchExpBeton deleteParam = new QqchExpBeton();
        deleteParam.setVersion(voParam.getVersion());
        qqchExpBetonMapper.deleteQqchExpBeton(deleteParam);

        if (CollectionUtils.isEmpty(voParam.getTreeList())) {
            return;
        }

        // 树转list
        List<QqchExpBeton> insertList = TreeUtil.treeToList(voParam.getTreeList());
        if (!CollectionUtils.isEmpty(insertList)) {
            for (QqchExpBeton insert : insertList) {
                insert.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    insert.setValid(Valid.YES);
                }
                insert.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                insert.setCreateUserName(SecurityUtils.getUserName());
                insert.setCreateTime(DateUtils.getNowDate());
            }
        }
        // 全量入库
        qqchExpBetonMapper.insertQqchExpBetonList(insertList);

        String buttonMark = voParam.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = voParam.getMenuId();
            String stageIdentity = voParam.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
}
