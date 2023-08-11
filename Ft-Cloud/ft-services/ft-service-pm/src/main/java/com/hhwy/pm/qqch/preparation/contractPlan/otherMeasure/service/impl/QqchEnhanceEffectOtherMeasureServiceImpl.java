package com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.QqchEnhanceEffectOtherMeasure;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.vo.QqchEnhanceEffectOtherMeasureVo;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.mapper.QqchEnhanceEffectOtherMeasureMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.service.IQqchEnhanceEffectOtherMeasureService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:08:56
 * @remark
 */
@Service
public class QqchEnhanceEffectOtherMeasureServiceImpl implements IQqchEnhanceEffectOtherMeasureService {

    @Autowired
    private QqchEnhanceEffectOtherMeasureMapper qqchEnhanceEffectOtherMeasureMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchEnhanceEffectOtherMeasure getQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure) {
        return qqchEnhanceEffectOtherMeasureMapper.getQqchEnhanceEffectOtherMeasure(qqchEnhanceEffectOtherMeasure);
    }

    public List<QqchEnhanceEffectOtherMeasure> getQqchEnhanceEffectOtherMeasureList(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure) {
        return qqchEnhanceEffectOtherMeasureMapper.getQqchEnhanceEffectOtherMeasureList(qqchEnhanceEffectOtherMeasure);
    }

    @Transactional
    public int insertQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure) {
        qqchEnhanceEffectOtherMeasure.setId(IdWorker.createId());
        qqchEnhanceEffectOtherMeasure.setCreateUser(SecurityUtils.getUserName());
        qqchEnhanceEffectOtherMeasure.setCreateTime(DateUtils.getNowDate());
        return qqchEnhanceEffectOtherMeasureMapper.insertQqchEnhanceEffectOtherMeasure(qqchEnhanceEffectOtherMeasure);
    }

    @Transactional
    public int updateQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure) {
        qqchEnhanceEffectOtherMeasure.setUpdateUser(SecurityUtils.getUserName());
        qqchEnhanceEffectOtherMeasure.setUpdateTime(DateUtils.getNowDate());
        return qqchEnhanceEffectOtherMeasureMapper.updateQqchEnhanceEffectOtherMeasure(qqchEnhanceEffectOtherMeasure);
    }

    @Transactional
    public int updateQqchEnhanceEffectOtherMeasureList(List<QqchEnhanceEffectOtherMeasure> qqchEnhanceEffectOtherMeasureList) {
        for (QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure : qqchEnhanceEffectOtherMeasureList) {
            qqchEnhanceEffectOtherMeasure.setUpdateUser(SecurityUtils.getUserName());
            qqchEnhanceEffectOtherMeasure.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchEnhanceEffectOtherMeasureMapper.updateQqchEnhanceEffectOtherMeasureList(qqchEnhanceEffectOtherMeasureList);
    }

    @Transactional
    public int deleteQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure) {
        qqchEnhanceEffectOtherMeasure.setUpdateUser(SecurityUtils.getUserName());
        qqchEnhanceEffectOtherMeasure.setUpdateTime(DateUtils.getNowDate());
        return qqchEnhanceEffectOtherMeasureMapper.deleteQqchEnhanceEffectOtherMeasure(qqchEnhanceEffectOtherMeasure);
    }

    @Transactional
    public int deleteQqchEnhanceEffectOtherMeasureByPks(List<Long> qqchEnhanceEffectOtherMeasurePkList) {
        return qqchEnhanceEffectOtherMeasureMapper.deleteQqchEnhanceEffectOtherMeasureByPks(qqchEnhanceEffectOtherMeasurePkList);
    }

    /**
     * 获取Vo
     * @param qqchEnhanceEffectOtherMeasure
     * @return
     */
    @Override
    public QqchEnhanceEffectOtherMeasureVo getQqchEnhanceEffectOtherMeasureVo(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure) {
        QqchEnhanceEffectOtherMeasureVo qqchEnhanceEffectOtherMeasureVo = new QqchEnhanceEffectOtherMeasureVo();
        BigDecimal version = qqchEnhanceEffectOtherMeasure.getVersion();
        version = VersionUtil.getVersion("qqch_enhance_effect_other_measure",version);

        qqchEnhanceEffectOtherMeasure.setVersion(version);
        List<QqchEnhanceEffectOtherMeasure> qqchEnhanceEffectOtherMeasureList = qqchEnhanceEffectOtherMeasureMapper.getQqchEnhanceEffectOtherMeasureList(qqchEnhanceEffectOtherMeasure);

        //转树列表
        List<QqchEnhanceEffectOtherMeasure> treeList = ListTreeUtil.formatTree(
                qqchEnhanceEffectOtherMeasureList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchEnhanceEffectOtherMeasure::getChildren,
                QqchEnhanceEffectOtherMeasure::setChildren);

        qqchEnhanceEffectOtherMeasureVo.setVersion(version);
        qqchEnhanceEffectOtherMeasureVo.setStageIdentity(qqchReviewService.getStage());
        qqchEnhanceEffectOtherMeasureVo.setList(treeList);
        return qqchEnhanceEffectOtherMeasureVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchEnhanceEffectOtherMeasureVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchEnhanceEffectOtherMeasureVo qqchEnhanceEffectOtherMeasureVo) {
        String buttonMark = qqchEnhanceEffectOtherMeasureVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchEnhanceEffectOtherMeasureVo.getVersion();
        List<QqchEnhanceEffectOtherMeasure> qqchEnhanceEffectOtherMeasureList = qqchEnhanceEffectOtherMeasureVo.getList();

        List<QqchEnhanceEffectOtherMeasure> tileList = ListTreeUtil.formatList(
                qqchEnhanceEffectOtherMeasureList,
                QqchEnhanceEffectOtherMeasure::setId,
                QqchEnhanceEffectOtherMeasure::setPid,
                QqchEnhanceEffectOtherMeasure::setSort,
                QqchEnhanceEffectOtherMeasure::setLeaf,
                QqchEnhanceEffectOtherMeasure::getChildren,
                QqchEnhanceEffectOtherMeasure::setChildren);

        //处理数据
        this.disposeData(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchEnhanceEffectOtherMeasureVo.getMenuId();
            String stageIdentity = qqchEnhanceEffectOtherMeasureVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param tileList
     * @param version
     */
    @Transactional
    public void disposeData(List<QqchEnhanceEffectOtherMeasure> tileList, BigDecimal version) {
        //删除旧数据
        QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure = new QqchEnhanceEffectOtherMeasure();
        qqchEnhanceEffectOtherMeasure.setVersion(version);
        qqchEnhanceEffectOtherMeasureMapper.deleteQqchEnhanceEffectOtherMeasure(qqchEnhanceEffectOtherMeasure);

        this.insertQqchEnhanceEffectOtherMeasureList(tileList,version);
    }

    /**
     * 批量插入
     * @param qqchEnhanceEffectOtherMeasureList
     * @param version
     */
    @Transactional
    public void insertQqchEnhanceEffectOtherMeasureList(List<QqchEnhanceEffectOtherMeasure> qqchEnhanceEffectOtherMeasureList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchEnhanceEffectOtherMeasureList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure : qqchEnhanceEffectOtherMeasureList) {
            qqchEnhanceEffectOtherMeasure.setValid(valid);
            qqchEnhanceEffectOtherMeasure.setVersion(version);
            qqchEnhanceEffectOtherMeasure.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchEnhanceEffectOtherMeasure.setCreateUserName(SecurityUtils.getUserName());
            qqchEnhanceEffectOtherMeasure.setCreateTime(DateUtils.getNowDate());
        }
        qqchEnhanceEffectOtherMeasureMapper.insertQqchEnhanceEffectOtherMeasureList(qqchEnhanceEffectOtherMeasureList);
    }
}
