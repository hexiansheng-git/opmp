package com.hhwy.pm.qqch.preparation.contractPlan.costApplicationPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.contractPlan.costApplicationPlan.domain.QqchExtendApplyWorkGroup;
import com.hhwy.pm.qqch.preparation.contractPlan.costApplicationPlan.domain.vo.QqchExtendApplyWorkGroupVo;
import com.hhwy.pm.qqch.preparation.contractPlan.costApplicationPlan.mapper.QqchExtendApplyWorkGroupMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.costApplicationPlan.service.IQqchExtendApplyWorkGroupService;
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
public class QqchExtendApplyWorkGroupServiceImpl implements IQqchExtendApplyWorkGroupService {

    @Autowired
    private QqchExtendApplyWorkGroupMapper qqchExtendApplyWorkGroupMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchExtendApplyWorkGroup getQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup) {
        return qqchExtendApplyWorkGroupMapper.getQqchExtendApplyWorkGroup(qqchExtendApplyWorkGroup);
    }

    public List<QqchExtendApplyWorkGroup> getQqchExtendApplyWorkGroupList(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup) {
        return qqchExtendApplyWorkGroupMapper.getQqchExtendApplyWorkGroupList(qqchExtendApplyWorkGroup);
    }

    @Transactional
    public int insertQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup) {
        qqchExtendApplyWorkGroup.setId(IdWorker.createId());
        qqchExtendApplyWorkGroup.setCreateUser(SecurityUtils.getUserName());
        qqchExtendApplyWorkGroup.setCreateTime(DateUtils.getNowDate());
        return qqchExtendApplyWorkGroupMapper.insertQqchExtendApplyWorkGroup(qqchExtendApplyWorkGroup);
    }

    @Transactional
    public int updateQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup) {
        qqchExtendApplyWorkGroup.setUpdateUser(SecurityUtils.getUserName());
        qqchExtendApplyWorkGroup.setUpdateTime(DateUtils.getNowDate());
        return qqchExtendApplyWorkGroupMapper.updateQqchExtendApplyWorkGroup(qqchExtendApplyWorkGroup);
    }

    @Transactional
    public int updateQqchExtendApplyWorkGroupList(List<QqchExtendApplyWorkGroup> qqchExtendApplyWorkGroupList) {
        for (QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup : qqchExtendApplyWorkGroupList) {
            qqchExtendApplyWorkGroup.setUpdateUser(SecurityUtils.getUserName());
            qqchExtendApplyWorkGroup.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchExtendApplyWorkGroupMapper.updateQqchExtendApplyWorkGroupList(qqchExtendApplyWorkGroupList);
    }

    @Transactional
    public int deleteQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup) {
        qqchExtendApplyWorkGroup.setUpdateUser(SecurityUtils.getUserName());
        qqchExtendApplyWorkGroup.setUpdateTime(DateUtils.getNowDate());
        return qqchExtendApplyWorkGroupMapper.deleteQqchExtendApplyWorkGroup(qqchExtendApplyWorkGroup);
    }

    @Transactional
    public int deleteQqchExtendApplyWorkGroupByPks(List<Long> qqchExtendApplyWorkGroupPkList) {
        return qqchExtendApplyWorkGroupMapper.deleteQqchExtendApplyWorkGroupByPks(qqchExtendApplyWorkGroupPkList);
    }

    /**
     * 获取Vo
     * @param qqchExtendApplyWorkGroup
     * @return
     */
    @Override
    public QqchExtendApplyWorkGroupVo getQqchExtendApplyWorkGroupVo(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup) {
        QqchExtendApplyWorkGroupVo qqchExtendApplyWorkGroupVo = new QqchExtendApplyWorkGroupVo();

        BigDecimal version = qqchExtendApplyWorkGroup.getVersion();
        version = VersionUtil.getVersion("qqch_extend_apply_work_group",version);

        qqchExtendApplyWorkGroup.setVersion(version);
        List<QqchExtendApplyWorkGroup> qqchExtendApplyWorkGroupList = qqchExtendApplyWorkGroupMapper.getQqchExtendApplyWorkGroupList(qqchExtendApplyWorkGroup);

        //转树列表
        List<QqchExtendApplyWorkGroup> treeList = ListTreeUtil.formatTree(
                qqchExtendApplyWorkGroupList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchExtendApplyWorkGroup::getChildren,
                QqchExtendApplyWorkGroup::setChildren);

        qqchExtendApplyWorkGroupVo.setVersion(version);
        qqchExtendApplyWorkGroupVo.setStageIdentity(qqchReviewService.getStage());
        qqchExtendApplyWorkGroupVo.setList(treeList);
        return qqchExtendApplyWorkGroupVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchExtendApplyWorkGroupVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchExtendApplyWorkGroupVo qqchExtendApplyWorkGroupVo) {
        String buttonMark = qqchExtendApplyWorkGroupVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchExtendApplyWorkGroupVo.getVersion();
        List<QqchExtendApplyWorkGroup> qqchExtendApplyWorkGroupList = qqchExtendApplyWorkGroupVo.getList();

        List<QqchExtendApplyWorkGroup> tileList = ListTreeUtil.formatList(
                qqchExtendApplyWorkGroupList,
                QqchExtendApplyWorkGroup::setId,
                QqchExtendApplyWorkGroup::setPid,
                QqchExtendApplyWorkGroup::setSort,
                QqchExtendApplyWorkGroup::setLeaf,
                QqchExtendApplyWorkGroup::getChildren,
                QqchExtendApplyWorkGroup::setChildren);

        //处理数据
        this.disposeData(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchExtendApplyWorkGroupVo.getMenuId();
            String stageIdentity = qqchExtendApplyWorkGroupVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param tileList
     * @param version
     */
    @Transactional
    public void disposeData(List<QqchExtendApplyWorkGroup> tileList, BigDecimal version) {
        //删除旧数据
        QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup = new QqchExtendApplyWorkGroup();
        qqchExtendApplyWorkGroup.setVersion(version);
        qqchExtendApplyWorkGroupMapper.deleteQqchExtendApplyWorkGroup(qqchExtendApplyWorkGroup);

        this.insertQqchExtendApplyWorkGroupList(tileList,version);
    }

    /**
     * 批量插入
     * @param qqchExtendApplyWorkGroupList
     * @param version
     */
    @Transactional
    public void insertQqchExtendApplyWorkGroupList(List<QqchExtendApplyWorkGroup> qqchExtendApplyWorkGroupList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchExtendApplyWorkGroupList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup : qqchExtendApplyWorkGroupList) {
            qqchExtendApplyWorkGroup.setValid(valid);
            qqchExtendApplyWorkGroup.setVersion(version);
            qqchExtendApplyWorkGroup.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchExtendApplyWorkGroup.setCreateUserName(SecurityUtils.getUserName());
            qqchExtendApplyWorkGroup.setCreateTime(DateUtils.getNowDate());
        }
        qqchExtendApplyWorkGroupMapper.insertQqchExtendApplyWorkGroupList(qqchExtendApplyWorkGroupList);
    }
}
