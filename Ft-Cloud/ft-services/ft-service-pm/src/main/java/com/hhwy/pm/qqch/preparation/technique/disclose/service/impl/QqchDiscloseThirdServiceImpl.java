package com.hhwy.pm.qqch.preparation.technique.disclose.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThird;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThirdDetail;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.QqchDiscloseThirdVo;
import com.hhwy.pm.qqch.preparation.technique.disclose.mapper.QqchDiscloseThirdDetailMapper;
import com.hhwy.pm.qqch.preparation.technique.disclose.mapper.QqchDiscloseThirdMapper;
import com.hhwy.pm.qqch.preparation.technique.disclose.service.IQqchDiscloseThirdService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.tree.TreeUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-21 14:26:42
 * @remark 3.5.2三级交底
 */
@Service
public class QqchDiscloseThirdServiceImpl implements IQqchDiscloseThirdService {

    @Autowired
    private QqchDiscloseThirdMapper qqchDiscloseThirdMapper;
    @Autowired
    private QqchDiscloseThirdDetailMapper qqchDiscloseThirdDetailMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;

    public QqchDiscloseThirdVo getQqchDiscloseThirdList(BigDecimal version) {
        QqchDiscloseThirdVo vo = new QqchDiscloseThirdVo();
        version = VersionUtil.getVersion("qqch_disclose_third", version);
        vo.setVersion(version);

        QqchDiscloseThird qryParam = new QqchDiscloseThird();
        qryParam.setVersion(version);
        List<QqchDiscloseThird> list = qqchDiscloseThirdMapper.getQqchDiscloseThirdList(qryParam);

        // 全部详情
        QqchDiscloseThirdDetail qryParamDetail = new QqchDiscloseThirdDetail();
        qryParamDetail.setVersion(version);
        List<QqchDiscloseThirdDetail> deTailList =
            qqchDiscloseThirdDetailMapper.getQqchDiscloseThirdDetailList(qryParamDetail);

        if (!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(deTailList)) {
            for (QqchDiscloseThird qqchDiscloseThird : list) {
                List<QqchDiscloseThirdDetail> detailListChild = new ArrayList<>();
                for (QqchDiscloseThirdDetail deTail : deTailList) {
                    if (qqchDiscloseThird.getId().equals(deTail.getMasterId())) {
                        detailListChild.add(deTail);
                    }
                }
                qqchDiscloseThird.setDetailTreeList(TreeUtil.build(detailListChild, null));
            }
        }

        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setTreeList(TreeUtil.build(list, null));
        vo.setAllDetailTreeList(TreeUtil.build(deTailList, null));
        return vo;
    }

    @Transactional
    public void batchSave(QqchDiscloseThirdVo qqchDiscloseThirdVo) {
        // 三级交底主表数据
        List<QqchDiscloseThird> treeList = qqchDiscloseThirdVo.getTreeList();

        if (!CollectionUtils.isEmpty(treeList)) {
            List<Long> ids = treeList.stream().map(QqchDiscloseThird::getId).collect(Collectors.toList());
            // 批量删除子表数据
            QqchDiscloseThirdDetail deleteParamDetail = new QqchDiscloseThirdDetail();
            deleteParamDetail.setVersion(qqchDiscloseThirdVo.getVersion());
            qqchDiscloseThirdDetailMapper.deleteQqchDiscloseThirdDetailByPks(ids);
        }

        // 批量删除主表数据
        QqchDiscloseThird deleteParam = new QqchDiscloseThird();
        deleteParam.setVersion(qqchDiscloseThirdVo.getVersion());
        qqchDiscloseThirdMapper.deleteQqchDiscloseThird(deleteParam);

        if (!CollectionUtils.isEmpty(treeList)) {
            // 新主表集合
            List<QqchDiscloseThird> newMainList = TreeUtil.treeToList(treeList);
            for (QqchDiscloseThird newMain : newMainList) {
                newMain.setVersion(qqchDiscloseThirdVo.getVersion());
                if (qqchDiscloseThirdVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    newMain.setValid(Valid.YES);
                }
                newMain.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                newMain.setCreateUserName(SecurityUtils.getUserName());
                newMain.setCreateTime(DateUtils.getNowDate());
            }

            // 新子列表集合
            List<QqchDiscloseThirdDetail> newDetailList = new ArrayList<>();
            for (QqchDiscloseThird qqchDiscloseThird : newMainList) {
                List<QqchDiscloseThirdDetail> detailList = TreeUtil.treeToList(qqchDiscloseThird.getDetailTreeList());
                if (!CollectionUtils.isEmpty(detailList)) {
                    for (QqchDiscloseThirdDetail detail : detailList) {
                        detail.setMasterId(qqchDiscloseThird.getId());
                        detail.setVersion(qqchDiscloseThirdVo.getVersion());
                        if (qqchDiscloseThirdVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                            detail.setValid(Valid.YES);
                        }
                        detail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                        detail.setCreateUserName(SecurityUtils.getUserName());
                        detail.setCreateTime(DateUtils.getNowDate());
                        newDetailList.add(detail);
                    }
                }
            }

            // 主表全量入库
            qqchDiscloseThirdMapper.insertQqchDiscloseThirdList(newMainList);

            // 子全量入库
            qqchDiscloseThirdDetailMapper.insertQqchDiscloseThirdDetailList(newDetailList);
        }

        String buttonMark = qqchDiscloseThirdVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = qqchDiscloseThirdVo.getMenuId();
            String stageIdentity = qqchDiscloseThirdVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }
}
