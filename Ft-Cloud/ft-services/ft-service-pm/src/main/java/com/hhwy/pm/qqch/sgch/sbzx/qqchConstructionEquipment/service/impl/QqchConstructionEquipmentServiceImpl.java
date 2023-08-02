package com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.domain.QqchConstructionEquipment;
import com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.domain.vo.QqchConstructionEquipmentVo;
import com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.mapper.QqchConstructionEquipmentMapper;
import com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.service.IQqchConstructionEquipmentService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-01 16:22:12
 * @remark
 */
@Service
public class QqchConstructionEquipmentServiceImpl implements IQqchConstructionEquipmentService {

    @Autowired
    private QqchConstructionEquipmentMapper qqchConstructionEquipmentMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchConstructionEquipment getQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment) {
        return qqchConstructionEquipmentMapper.getQqchConstructionEquipment(qqchConstructionEquipment);
    }


    /**
     * 列表
     *
     * @param qqchConstructionEquipment
     * @return
     */
    public QqchConstructionEquipmentVo getQqchConstructionEquipmentList(QqchConstructionEquipment qqchConstructionEquipment) {
        QqchConstructionEquipmentVo vo = new QqchConstructionEquipmentVo();
        BigDecimal version = qqchConstructionEquipment.getVersion();
        version = VersionUtil.getVersion("qqch_construction_equipment", version);
        qqchConstructionEquipment.setVersion(version);
        List<QqchConstructionEquipment> qqchConstructionEquipmentList = qqchConstructionEquipmentMapper.getQqchConstructionEquipmentList(qqchConstructionEquipment);
        List<QqchConstructionEquipment> build = TreeUtil.build(qqchConstructionEquipmentList, 0l);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchConstructionEquipmentList(build);
        return vo;
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchConstructionEquipmentVo
     * @return
     */
    @Override
    public void save(QqchConstructionEquipmentVo qqchConstructionEquipmentVo) {
        String buttonMark = qqchConstructionEquipmentVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchConstructionEquipmentVo.getVersion();
        List<QqchConstructionEquipment> qqchConstructionEquipmentList = qqchConstructionEquipmentVo.getQqchConstructionEquipmentList();

        this.insertQqchConstructionEquipmentList(qqchConstructionEquipmentList, version);

        //判断是否是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = qqchConstructionEquipmentVo.getMenuId();
            String stageIdentity = qqchConstructionEquipmentVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Override
    public void insertQqchConstructionEquipmentList(List<QqchConstructionEquipment> qqchConstructionEquipmentList, BigDecimal version) {
        //删除旧数据
        QqchConstructionEquipment qqchConstructionEquipment = new QqchConstructionEquipment();
        qqchConstructionEquipment.setVersion(version);
        qqchConstructionEquipmentMapper.deleteQqchConstructionEquipment(qqchConstructionEquipment);

        if (CollectionUtils.isEmpty(qqchConstructionEquipmentList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        List<QqchConstructionEquipment> qqchConstructionEquipments = TreeUtil.treeToList(qqchConstructionEquipmentList);
        for (QqchConstructionEquipment equipment : qqchConstructionEquipments) {
            equipment.setValid(valid);
            equipment.setVersion(version);
            equipment.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            equipment.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            equipment.setCreateTime(DateUtils.getNowDate());
        }
        qqchConstructionEquipmentMapper.insertQqchConstructionEquipmentList(qqchConstructionEquipments);
    }


    @Transactional
    public int insertQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment) {
        qqchConstructionEquipment.setId(IdWorker.createId());
        qqchConstructionEquipment.setCreateUser(SecurityUtils.getUserName());
        qqchConstructionEquipment.setCreateTime(DateUtils.getNowDate());
        return qqchConstructionEquipmentMapper.insertQqchConstructionEquipment(qqchConstructionEquipment);
    }


    @Transactional
    public int updateQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment) {
        qqchConstructionEquipment.setUpdateUser(SecurityUtils.getUserName());
        qqchConstructionEquipment.setUpdateTime(DateUtils.getNowDate());
        return qqchConstructionEquipmentMapper.updateQqchConstructionEquipment(qqchConstructionEquipment);
    }

    @Transactional
    public int updateQqchConstructionEquipmentList(List<QqchConstructionEquipment> qqchConstructionEquipmentList) {
        for (QqchConstructionEquipment qqchConstructionEquipment : qqchConstructionEquipmentList) {
            qqchConstructionEquipment.setUpdateUser(SecurityUtils.getUserName());
            qqchConstructionEquipment.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchConstructionEquipmentMapper.updateQqchConstructionEquipmentList(qqchConstructionEquipmentList);
    }

    @Transactional
    public int deleteQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment) {
        qqchConstructionEquipment.setUpdateUser(SecurityUtils.getUserName());
        qqchConstructionEquipment.setUpdateTime(DateUtils.getNowDate());
        return qqchConstructionEquipmentMapper.deleteQqchConstructionEquipment(qqchConstructionEquipment);
    }

    @Transactional
    public int deleteQqchConstructionEquipmentByPks(List<Long> qqchConstructionEquipmentPkList) {
        return qqchConstructionEquipmentMapper.deleteQqchConstructionEquipmentByPks(qqchConstructionEquipmentPkList);
    }


}
