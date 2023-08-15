package com.hhwy.pm.qqch.preparation.costControl.postDuty.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.QqchCostControlPostDuty;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.vo.QqchCostControlPostDutyVo;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.mapper.QqchCostControlPostDutyMapper;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.service.IQqchCostControlPostDutyService;
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
 * @date 2023-08-08 17:48:45
 * @remark
 */
@Service
public class QqchCostControlPostDutyServiceImpl implements IQqchCostControlPostDutyService {

    @Autowired
    private QqchCostControlPostDutyMapper qqchCostControlPostDutyMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchCostControlPostDuty getQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty) {
        return qqchCostControlPostDutyMapper.getQqchExtendApplyWorkGroup(qqchCostControlPostDuty);
    }

    public List<QqchCostControlPostDuty> getQqchExtendApplyWorkGroupList(QqchCostControlPostDuty qqchCostControlPostDuty) {
        return qqchCostControlPostDutyMapper.getQqchExtendApplyWorkGroupList(qqchCostControlPostDuty);
    }

    @Transactional
    public int insertQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty) {
        qqchCostControlPostDuty.setId(IdWorker.createId());
        qqchCostControlPostDuty.setCreateUser(SecurityUtils.getUserName());
        qqchCostControlPostDuty.setCreateTime(DateUtils.getNowDate());
        return qqchCostControlPostDutyMapper.insertQqchExtendApplyWorkGroup(qqchCostControlPostDuty);
    }

    @Transactional
    public int updateQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty) {
        qqchCostControlPostDuty.setUpdateUser(SecurityUtils.getUserName());
        qqchCostControlPostDuty.setUpdateTime(DateUtils.getNowDate());
        return qqchCostControlPostDutyMapper.updateQqchExtendApplyWorkGroup(qqchCostControlPostDuty);
    }

    @Transactional
    public int updateQqchExtendApplyWorkGroupList(List<QqchCostControlPostDuty> qqchCostControlPostDutyList) {
        for (QqchCostControlPostDuty qqchCostControlPostDuty : qqchCostControlPostDutyList) {
            qqchCostControlPostDuty.setUpdateUser(SecurityUtils.getUserName());
            qqchCostControlPostDuty.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchCostControlPostDutyMapper.updateQqchExtendApplyWorkGroupList(qqchCostControlPostDutyList);
    }

    @Transactional
    public int deleteQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty) {
        qqchCostControlPostDuty.setUpdateUser(SecurityUtils.getUserName());
        qqchCostControlPostDuty.setUpdateTime(DateUtils.getNowDate());
        return qqchCostControlPostDutyMapper.deleteQqchExtendApplyWorkGroup(qqchCostControlPostDuty);
    }

    @Transactional
    public int deleteQqchExtendApplyWorkGroupByPks(List<Long> qqchExtendApplyWorkGroupPkList) {
        return qqchCostControlPostDutyMapper.deleteQqchExtendApplyWorkGroupByPks(qqchExtendApplyWorkGroupPkList);
    }

    /**
     * 获取Vo
     * @param qqchCostControlPostDuty
     * @return
     */
    @Override
    public QqchCostControlPostDutyVo getQqchExtendApplyWorkGroupVo(QqchCostControlPostDuty qqchCostControlPostDuty) {
        QqchCostControlPostDutyVo qqchCostControlPostDutyVo = new QqchCostControlPostDutyVo();

        BigDecimal version = qqchCostControlPostDuty.getVersion();
        version = VersionUtil.getVersion("qqch_extend_apply_work_group",version);

        qqchCostControlPostDuty.setVersion(version);
        List<QqchCostControlPostDuty> qqchCostControlPostDutyList = qqchCostControlPostDutyMapper.getQqchExtendApplyWorkGroupList(qqchCostControlPostDuty);

        //转树列表
        List<QqchCostControlPostDuty> treeList = ListTreeUtil.formatTree(
                qqchCostControlPostDutyList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchCostControlPostDuty::getChildren,
                QqchCostControlPostDuty::setChildren);

        qqchCostControlPostDutyVo.setVersion(version);
        qqchCostControlPostDutyVo.setStageIdentity(qqchReviewService.getStage());
        qqchCostControlPostDutyVo.setList(treeList);
        return qqchCostControlPostDutyVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchCostControlPostDutyVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchCostControlPostDutyVo qqchCostControlPostDutyVo) {
        String buttonMark = qqchCostControlPostDutyVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchCostControlPostDutyVo.getVersion();
        List<QqchCostControlPostDuty> qqchCostControlPostDutyList = qqchCostControlPostDutyVo.getList();

        List<QqchCostControlPostDuty> tileList = ListTreeUtil.formatList(
                qqchCostControlPostDutyList,
                QqchCostControlPostDuty::setId,
                QqchCostControlPostDuty::setPid,
                QqchCostControlPostDuty::setSort,
                QqchCostControlPostDuty::setLeaf,
                QqchCostControlPostDuty::getChildren,
                QqchCostControlPostDuty::setChildren);

        //处理数据
        this.disposeData(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchCostControlPostDutyVo.getMenuId();
            String stageIdentity = qqchCostControlPostDutyVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param tileList
     * @param version
     */
    @Transactional
    public void disposeData(List<QqchCostControlPostDuty> tileList, BigDecimal version) {
        //删除旧数据
        QqchCostControlPostDuty qqchCostControlPostDuty = new QqchCostControlPostDuty();
        qqchCostControlPostDuty.setVersion(version);
        qqchCostControlPostDutyMapper.deleteQqchExtendApplyWorkGroup(qqchCostControlPostDuty);

        this.insertQqchExtendApplyWorkGroupList(tileList,version);
    }

    /**
     * 批量插入
     * @param qqchCostControlPostDutyList
     * @param version
     */
    @Transactional
    public void insertQqchExtendApplyWorkGroupList(List<QqchCostControlPostDuty> qqchCostControlPostDutyList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchCostControlPostDutyList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchCostControlPostDuty qqchCostControlPostDuty : qqchCostControlPostDutyList) {
            qqchCostControlPostDuty.setValid(valid);
            qqchCostControlPostDuty.setVersion(version);
            qqchCostControlPostDuty.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchCostControlPostDuty.setCreateUserName(SecurityUtils.getUserName());
            qqchCostControlPostDuty.setCreateTime(DateUtils.getNowDate());
        }
        qqchCostControlPostDutyMapper.insertQqchExtendApplyWorkGroupList(qqchCostControlPostDutyList);
    }
}
