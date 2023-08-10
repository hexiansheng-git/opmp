package com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.QqchProjectOperationObjective;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.vo.QqchProjectOperationObjectiveVo;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.mapper.QqchProjectOperationObjectiveMapper;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.service.IQqchProjectOperationObjectiveService;
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
 * @date 2023-08-03 13:39:48
 * @remark
 */
@Service
public class QqchProjectOperationObjectiveServiceImpl implements IQqchProjectOperationObjectiveService {

    @Autowired
    private QqchProjectOperationObjectiveMapper qqchProjectOperationObjectiveMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchProjectOperationObjective getQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective) {
        return qqchProjectOperationObjectiveMapper.getQqchProjectOperationObjective(qqchProjectOperationObjective);
    }

    public List<QqchProjectOperationObjective> getQqchProjectOperationObjectiveList(QqchProjectOperationObjective qqchProjectOperationObjective) {
        return qqchProjectOperationObjectiveMapper.getQqchProjectOperationObjectiveList(qqchProjectOperationObjective);
    }

    @Transactional
    public int insertQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective) {
        qqchProjectOperationObjective.setId(IdWorker.createId());
        qqchProjectOperationObjective.setCreateUser(SecurityUtils.getUserName());
        qqchProjectOperationObjective.setCreateTime(DateUtils.getNowDate());
        return qqchProjectOperationObjectiveMapper.insertQqchProjectOperationObjective(qqchProjectOperationObjective);
    }

    @Transactional
    public int updateQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective) {
        qqchProjectOperationObjective.setUpdateUser(SecurityUtils.getUserName());
        qqchProjectOperationObjective.setUpdateTime(DateUtils.getNowDate());
        return qqchProjectOperationObjectiveMapper.updateQqchProjectOperationObjective(qqchProjectOperationObjective);
    }

    @Transactional
    public int updateQqchProjectOperationObjectiveList(List<QqchProjectOperationObjective> qqchProjectOperationObjectiveList) {
        for (QqchProjectOperationObjective qqchProjectOperationObjective : qqchProjectOperationObjectiveList) {
            qqchProjectOperationObjective.setUpdateUser(SecurityUtils.getUserName());
            qqchProjectOperationObjective.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchProjectOperationObjectiveMapper.updateQqchProjectOperationObjectiveList(qqchProjectOperationObjectiveList);
    }

    @Transactional
    public int deleteQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective) {
        qqchProjectOperationObjective.setUpdateUser(SecurityUtils.getUserName());
        qqchProjectOperationObjective.setUpdateTime(DateUtils.getNowDate());
        return qqchProjectOperationObjectiveMapper.deleteQqchProjectOperationObjective(qqchProjectOperationObjective);
    }

    @Transactional
    public int deleteQqchProjectOperationObjectiveByPks(List<Long> qqchProjectOperationObjectivePkList) {
        return qqchProjectOperationObjectiveMapper.deleteQqchProjectOperationObjectiveByPks(qqchProjectOperationObjectivePkList);
    }

    /**
     * 获取项目整体经营目标Vo
     * @param qqchProjectOperationObjective
     * @return
     */
    @Override
    public QqchProjectOperationObjectiveVo getQqchProjectOperationObjectiveVo(QqchProjectOperationObjective qqchProjectOperationObjective) {
        QqchProjectOperationObjectiveVo qqchProjectOperationObjectiveVo = new QqchProjectOperationObjectiveVo();

        BigDecimal version = qqchProjectOperationObjective.getVersion();
        version = VersionUtil.getVersion("qqch_project_operation_objective",version);

        qqchProjectOperationObjective.setVersion(version);
        List<QqchProjectOperationObjective> qqchProjectOperationObjectiveList = qqchProjectOperationObjectiveMapper.getQqchProjectOperationObjectiveList(qqchProjectOperationObjective);

        //转树列表
        List<QqchProjectOperationObjective> treeList = ListTreeUtil.formatTree(
                qqchProjectOperationObjectiveList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchProjectOperationObjective::getChildren,
                QqchProjectOperationObjective::setChildren);

        qqchProjectOperationObjectiveVo.setVersion(version);
        qqchProjectOperationObjectiveVo.setStageIdentity(qqchReviewService.getStage());
        qqchProjectOperationObjectiveVo.setList(treeList);
        return qqchProjectOperationObjectiveVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchProjectOperationObjectiveVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchProjectOperationObjectiveVo qqchProjectOperationObjectiveVo) {
        String buttonMark = qqchProjectOperationObjectiveVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchProjectOperationObjectiveVo.getVersion();
        List<QqchProjectOperationObjective> qqchProjectOperationObjectiveList = qqchProjectOperationObjectiveVo.getList();

        List<QqchProjectOperationObjective> tileList = ListTreeUtil.formatList(
                qqchProjectOperationObjectiveList,
                QqchProjectOperationObjective::setId,
                QqchProjectOperationObjective::setPid,
                QqchProjectOperationObjective::setSort,
                QqchProjectOperationObjective::setLeaf,
                QqchProjectOperationObjective::getChildren,
                QqchProjectOperationObjective::setChildren);

        //处理数据
        this.disposeData(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchProjectOperationObjectiveVo.getMenuId();
            String stageIdentity = qqchProjectOperationObjectiveVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param tileList
     * @param version
     */
    @Transactional
    public void disposeData(List<QqchProjectOperationObjective> tileList, BigDecimal version) {
        //删除旧数据
        QqchProjectOperationObjective qqchProjectOperationObjective = new QqchProjectOperationObjective();
        qqchProjectOperationObjective.setVersion(version);
        qqchProjectOperationObjectiveMapper.deleteQqchProjectOperationObjective(qqchProjectOperationObjective);

        this.insertQqchProjectOperationObjectiveList(tileList,version);
    }

    /**
     * 批量插入
     * @param qqchProjectOperationObjectiveList
     * @param version
     */
    @Transactional
    public void insertQqchProjectOperationObjectiveList(List<QqchProjectOperationObjective> qqchProjectOperationObjectiveList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchProjectOperationObjectiveList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchProjectOperationObjective qqchProjectOperationObjective : qqchProjectOperationObjectiveList) {
            qqchProjectOperationObjective.setValid(valid);
            qqchProjectOperationObjective.setVersion(version);
            qqchProjectOperationObjective.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchProjectOperationObjective.setCreateUserName(SecurityUtils.getUserName());
            qqchProjectOperationObjective.setCreateTime(DateUtils.getNowDate());
        }
        qqchProjectOperationObjectiveMapper.insertQqchProjectOperationObjectiveList(qqchProjectOperationObjectiveList);
    }
}
