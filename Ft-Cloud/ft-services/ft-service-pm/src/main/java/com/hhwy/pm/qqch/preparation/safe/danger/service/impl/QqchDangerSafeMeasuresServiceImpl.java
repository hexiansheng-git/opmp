package com.hhwy.pm.qqch.preparation.safe.danger.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerList;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasures;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasuresDetail;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerSafeMeasuresVo;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerSafeMeasuresDetailMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.mapper.QqchDangerSafeMeasuresMapper;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerListService;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerSafeMeasuresService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private QqchDangerSafeMeasuresDetailMapper qqchDangerSafeMeasuresDetailMapper;
    @Autowired
    private IQqchDangerListService qqchDangerListService;

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

        // 组装新列表
        List<QqchDangerSafeMeasures> newList = new ArrayList<>();

        // 危大工程清单
        List<QqchDangerList> dangerList = qqchDangerListService.getQqchDangerListList(version).getList();
        for (QqchDangerList qqchDangerList : dangerList) {
            QqchDangerSafeMeasures qqchDangerSafeMeasures = new QqchDangerSafeMeasures();
            for (QqchDangerSafeMeasures measures : list) {
                if (measures.getSchemeCode().equals(qqchDangerList.getSchemeCode())) {
                    BeanUtils.copyProperties(measures, qqchDangerSafeMeasures);
                }
            }
            qqchDangerSafeMeasures.setSchemeCode(qqchDangerList.getSchemeCode());
            qqchDangerSafeMeasures.setSchemeName(qqchDangerList.getSchemeName());
            qqchDangerSafeMeasures.setDangerLevel(qqchDangerList.getDangerLevel());
            qqchDangerSafeMeasures.setWbsCode(qqchDangerList.getWbsCode());
            qqchDangerSafeMeasures.setWbsName(qqchDangerList.getWbsName());
            newList.add(qqchDangerSafeMeasures);
        }

        // 全部详情
        QqchDangerSafeMeasuresDetail qryParamDetail = new QqchDangerSafeMeasuresDetail();
        qryParamDetail.setVersion(version);
        List<QqchDangerSafeMeasuresDetail> deTailList = qqchDangerSafeMeasuresDetailMapper
            .getQqchDangerSafeMeasuresDetailList(qryParamDetail);

        if (!CollectionUtils.isEmpty(newList) && !CollectionUtils.isEmpty(deTailList)) {
            for (QqchDangerSafeMeasures qqchDangerSafeMeasures : newList) {
                List<QqchDangerSafeMeasuresDetail> detailListChild = new ArrayList<>();
                for (QqchDangerSafeMeasuresDetail deTail : deTailList) {
                    if (qqchDangerSafeMeasures.getId().equals(deTail.getMasterId())) {
                        detailListChild.add(deTail);
                    }
                }
                qqchDangerSafeMeasures.setDetailList(detailListChild);
            }
        }

        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(newList);
        return vo;
    }

    /**
     * 保存/确认/提交
     *
     * @param voParam
     * @return
     */
    @Transactional
    public void batchSave(QqchDangerSafeMeasuresVo voParam) {
        // 批量删除主表数据
        QqchDangerSafeMeasures deleteParam = new QqchDangerSafeMeasures();
        deleteParam.setVersion(voParam.getVersion());
        qqchDangerSafeMeasuresMapper.deleteQqchDangerSafeMeasures(deleteParam);

        if (!CollectionUtils.isEmpty(voParam.getList())) {
            List<Long> ids = voParam.getList().stream().map(QqchDangerSafeMeasures::getId).collect(Collectors.toList());
            // 批量删除子表数据
            qqchDangerSafeMeasuresDetailMapper.deleteQqchDangerSafeMeasuresDetailByPks(ids);

            List<QqchDangerSafeMeasures> newMainList = voParam.getList();
            // 新子列表集合
            List<QqchDangerSafeMeasuresDetail> newDetailList = new ArrayList<>();
            for (QqchDangerSafeMeasures newMain : newMainList) {
                newMain.setId(IdWorker.createId());
                newMain.setVersion(voParam.getVersion());
                if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    newMain.setValid(Valid.YES);
                }
                newMain.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                newMain.setCreateUserName(SecurityUtils.getUserName());
                newMain.setCreateTime(DateUtils.getNowDate());

                List<QqchDangerSafeMeasuresDetail> detailList = newMain.getDetailList();
                if (!CollectionUtils.isEmpty(detailList)) {
                    for (QqchDangerSafeMeasuresDetail detail : detailList) {
                        detail.setId(IdWorker.createId());
                        detail.setMasterId(newMain.getId());
                        detail.setVersion(newMain.getVersion());
                        if (voParam.getVersion().compareTo(BigDecimal.ONE) == 0) {
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
            qqchDangerSafeMeasuresMapper.insertQqchDangerSafeMeasuresList(newMainList);

            if (!CollectionUtils.isEmpty(newDetailList)) {
                // 子全量入库
                qqchDangerSafeMeasuresDetailMapper.insertQqchDangerSafeMeasuresDetailList(newDetailList);
            }
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
