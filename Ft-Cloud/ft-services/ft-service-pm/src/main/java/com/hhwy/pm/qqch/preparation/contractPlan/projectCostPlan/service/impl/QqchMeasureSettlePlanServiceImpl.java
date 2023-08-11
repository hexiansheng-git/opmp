package com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.QqchMeasureSettlePlan;
import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.domain.vo.QqchMeasureSettlePlanVo;
import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.mapper.QqchMeasureSettlePlanMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.projectCostPlan.service.IQqchMeasureSettlePlanService;
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
 * @date 2023-08-04 10:45:52
 * @remark
 */
@Service
public class QqchMeasureSettlePlanServiceImpl implements IQqchMeasureSettlePlanService {

    @Autowired
    private QqchMeasureSettlePlanMapper qqchMeasureSettlePlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchMeasureSettlePlan getQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan) {
        return qqchMeasureSettlePlanMapper.getQqchMeasureSettlePlan(qqchMeasureSettlePlan);
    }

    public List<QqchMeasureSettlePlan> getQqchMeasureSettlePlanList(QqchMeasureSettlePlan qqchMeasureSettlePlan) {
        return qqchMeasureSettlePlanMapper.getQqchMeasureSettlePlanList(qqchMeasureSettlePlan);
    }

    @Transactional
    public int insertQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan) {
        qqchMeasureSettlePlan.setId(IdWorker.createId());
        qqchMeasureSettlePlan.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureSettlePlan.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureSettlePlanMapper.insertQqchMeasureSettlePlan(qqchMeasureSettlePlan);
    }

    @Transactional
    public int updateQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan) {
        qqchMeasureSettlePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureSettlePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureSettlePlanMapper.updateQqchMeasureSettlePlan(qqchMeasureSettlePlan);
    }

    @Transactional
    public int updateQqchMeasureSettlePlanList(List<QqchMeasureSettlePlan> qqchMeasureSettlePlanList) {
        for (QqchMeasureSettlePlan qqchMeasureSettlePlan : qqchMeasureSettlePlanList) {
            qqchMeasureSettlePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchMeasureSettlePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMeasureSettlePlanMapper.updateQqchMeasureSettlePlanList(qqchMeasureSettlePlanList);
    }

    @Transactional
    public int deleteQqchMeasureSettlePlan(QqchMeasureSettlePlan qqchMeasureSettlePlan) {
        qqchMeasureSettlePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureSettlePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureSettlePlanMapper.deleteQqchMeasureSettlePlan(qqchMeasureSettlePlan);
    }

    @Transactional
    public int deleteQqchMeasureSettlePlanByPks(List<Long> qqchMeasureSettlePlanPkList) {
        return qqchMeasureSettlePlanMapper.deleteQqchMeasureSettlePlanByPks(qqchMeasureSettlePlanPkList);
    }

    /**
     * 获取计量结算策划Vo
     * @param qqchMeasureSettlePlan
     * @return
     */
    @Override
    public QqchMeasureSettlePlanVo getQqchMeasureSettlePlanVo(QqchMeasureSettlePlan qqchMeasureSettlePlan) {
        QqchMeasureSettlePlanVo qqchMeasureSettlePlanVo = new QqchMeasureSettlePlanVo();

        BigDecimal version = qqchMeasureSettlePlan.getVersion();
        version = VersionUtil.getVersion("qqch_measure_settle_plan",version);

        qqchMeasureSettlePlan.setVersion(version);
        List<QqchMeasureSettlePlan> qqchMeasureSettlePlanList = qqchMeasureSettlePlanMapper.getQqchMeasureSettlePlanList(qqchMeasureSettlePlan);

        //转树列表
        List<QqchMeasureSettlePlan> treeList = ListTreeUtil.formatTree(
                qqchMeasureSettlePlanList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchMeasureSettlePlan::getChildren,
                QqchMeasureSettlePlan::setChildren);

        qqchMeasureSettlePlanVo.setVersion(version);
        qqchMeasureSettlePlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchMeasureSettlePlanVo.setList(treeList);
        return qqchMeasureSettlePlanVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchMeasureSettlePlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchMeasureSettlePlanVo qqchMeasureSettlePlanVo) {
        String buttonMark = qqchMeasureSettlePlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchMeasureSettlePlanVo.getVersion();
        List<QqchMeasureSettlePlan> qqchMeasureSettlePlanList = qqchMeasureSettlePlanVo.getList();

        List<QqchMeasureSettlePlan> tileList = ListTreeUtil.formatList(
                qqchMeasureSettlePlanList,
                QqchMeasureSettlePlan::setId,
                QqchMeasureSettlePlan::setPid,
                QqchMeasureSettlePlan::setSort,
                QqchMeasureSettlePlan::setLeaf,
                QqchMeasureSettlePlan::getChildren,
                QqchMeasureSettlePlan::setChildren);

        //处理数据
        this.disposeData(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchMeasureSettlePlanVo.getMenuId();
            String stageIdentity = qqchMeasureSettlePlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param tileList
     * @param version
     */
    @Transactional
    public void disposeData(List<QqchMeasureSettlePlan> tileList, BigDecimal version) {
        //删除旧数据
        QqchMeasureSettlePlan qqchMeasureSettlePlan = new QqchMeasureSettlePlan();
        qqchMeasureSettlePlan.setVersion(version);
        qqchMeasureSettlePlanMapper.deleteQqchMeasureSettlePlan(qqchMeasureSettlePlan);

        this.insertQqchMeasureSettlePlanList(tileList,version);
    }

    /**
     * 批量插入
     * @param qqchMeasureSettlePlanList
     * @param version
     */
    @Transactional
    public void insertQqchMeasureSettlePlanList(List<QqchMeasureSettlePlan> qqchMeasureSettlePlanList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchMeasureSettlePlanList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchMeasureSettlePlan qqchMeasureSettlePlan : qqchMeasureSettlePlanList) {
            qqchMeasureSettlePlan.setValid(valid);
            qqchMeasureSettlePlan.setVersion(version);
            qqchMeasureSettlePlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchMeasureSettlePlan.setCreateUserName(SecurityUtils.getUserName());
            qqchMeasureSettlePlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchMeasureSettlePlanMapper.insertQqchMeasureSettlePlanList(qqchMeasureSettlePlanList);
    }
}
