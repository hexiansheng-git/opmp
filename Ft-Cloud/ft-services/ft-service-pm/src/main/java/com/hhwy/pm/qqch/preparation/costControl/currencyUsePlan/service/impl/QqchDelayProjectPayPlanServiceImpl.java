package com.hhwy.pm.qqch.preparation.costControl.currencyUsePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.currencyUsePlan.domain.QqchDelayProjectPayPlan;
import com.hhwy.pm.qqch.preparation.costControl.currencyUsePlan.domain.vo.QqchDelayProjectPayPlanVo;
import com.hhwy.pm.qqch.preparation.costControl.currencyUsePlan.mapper.QqchDelayProjectPayPlanMapper;
import com.hhwy.pm.qqch.preparation.costControl.currencyUsePlan.service.IQqchDelayProjectPayPlanService;
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
 * @date 2023-08-08 17:09:45
 * @remark
 */
@Service
public class QqchDelayProjectPayPlanServiceImpl implements IQqchDelayProjectPayPlanService {

    @Autowired
    private QqchDelayProjectPayPlanMapper qqchDelayProjectPayPlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchDelayProjectPayPlan getQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan) {
        return qqchDelayProjectPayPlanMapper.getQqchDelayProjectPayPlan(qqchDelayProjectPayPlan);
    }

    public List<QqchDelayProjectPayPlan> getQqchDelayProjectPayPlanList(QqchDelayProjectPayPlan qqchDelayProjectPayPlan) {
        return qqchDelayProjectPayPlanMapper.getQqchDelayProjectPayPlanList(qqchDelayProjectPayPlan);
    }

    @Transactional
    public int insertQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan) {
        qqchDelayProjectPayPlan.setId(IdWorker.createId());
        qqchDelayProjectPayPlan.setCreateUser(SecurityUtils.getUserName());
        qqchDelayProjectPayPlan.setCreateTime(DateUtils.getNowDate());
        return qqchDelayProjectPayPlanMapper.insertQqchDelayProjectPayPlan(qqchDelayProjectPayPlan);
    }

    @Transactional
    public int updateQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan) {
        qqchDelayProjectPayPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDelayProjectPayPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDelayProjectPayPlanMapper.updateQqchDelayProjectPayPlan(qqchDelayProjectPayPlan);
    }

    @Transactional
    public int updateQqchDelayProjectPayPlanList(List<QqchDelayProjectPayPlan> qqchDelayProjectPayPlanList) {
        for (QqchDelayProjectPayPlan qqchDelayProjectPayPlan : qqchDelayProjectPayPlanList) {
            qqchDelayProjectPayPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchDelayProjectPayPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDelayProjectPayPlanMapper.updateQqchDelayProjectPayPlanList(qqchDelayProjectPayPlanList);
    }

    @Transactional
    public int deleteQqchDelayProjectPayPlan(QqchDelayProjectPayPlan qqchDelayProjectPayPlan) {
        qqchDelayProjectPayPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDelayProjectPayPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDelayProjectPayPlanMapper.deleteQqchDelayProjectPayPlan(qqchDelayProjectPayPlan);
    }

    @Transactional
    public int deleteQqchDelayProjectPayPlanByPks(List<Long> qqchDelayProjectPayPlanPkList) {
        return qqchDelayProjectPayPlanMapper.deleteQqchDelayProjectPayPlanByPks(qqchDelayProjectPayPlanPkList);
    }

    /**
     * 获取Vo
     * @param qqchDelayProjectPayPlan
     * @return
     */
    @Override
    public QqchDelayProjectPayPlanVo getQqchDelayProjectPayPlanVo(QqchDelayProjectPayPlan qqchDelayProjectPayPlan) {
        QqchDelayProjectPayPlanVo qqchDelayProjectPayPlanVo = new QqchDelayProjectPayPlanVo();

        BigDecimal version = qqchDelayProjectPayPlan.getVersion();
        version = VersionUtil.getVersion("qqch_delay_project_pay_plan",version);

        qqchDelayProjectPayPlan.setVersion(version);
        List<QqchDelayProjectPayPlan> qqchDelayProjectPayPlanList = qqchDelayProjectPayPlanMapper.getQqchDelayProjectPayPlanList(qqchDelayProjectPayPlan);

        //转树列表
        List<QqchDelayProjectPayPlan> treeList = ListTreeUtil.formatTree(
                qqchDelayProjectPayPlanList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchDelayProjectPayPlan::getChildren,
                QqchDelayProjectPayPlan::setChildren);

        qqchDelayProjectPayPlanVo.setVersion(version);
        qqchDelayProjectPayPlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchDelayProjectPayPlanVo.setQqchDelayProjectPayPlanList(treeList);
        return qqchDelayProjectPayPlanVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchDelayProjectPayPlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchDelayProjectPayPlanVo qqchDelayProjectPayPlanVo) {
        String buttonMark = qqchDelayProjectPayPlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchDelayProjectPayPlanVo.getVersion();
        List<QqchDelayProjectPayPlan> qqchDelayProjectPayPlanList = qqchDelayProjectPayPlanVo.getQqchDelayProjectPayPlanList();

        List<QqchDelayProjectPayPlan> tileList = ListTreeUtil.formatList(
                qqchDelayProjectPayPlanList,
                QqchDelayProjectPayPlan::setId,
                QqchDelayProjectPayPlan::setPid,
                QqchDelayProjectPayPlan::setSort,
                QqchDelayProjectPayPlan::setLeaf,
                QqchDelayProjectPayPlan::getChildren,
                QqchDelayProjectPayPlan::setChildren);

        //处理数据
        this.disposeData(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchDelayProjectPayPlanVo.getMenuId();
            String stageIdentity = qqchDelayProjectPayPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param tileList
     * @param version
     */
    @Transactional
    public void disposeData(List<QqchDelayProjectPayPlan> tileList, BigDecimal version) {
        //删除旧数据
        QqchDelayProjectPayPlan qqchDelayProjectPayPlan = new QqchDelayProjectPayPlan();
        qqchDelayProjectPayPlan.setVersion(version);
        qqchDelayProjectPayPlanMapper.deleteQqchDelayProjectPayPlan(qqchDelayProjectPayPlan);

        this.insertQqchDelayProjectPayPlanList(tileList,version);
    }

    /**
     * 批量插入
     *
     * @param qqchDelayProjectPayPlanList
     * @param version
     * @return
     */
    @Transactional
    public void insertQqchDelayProjectPayPlanList(List<QqchDelayProjectPayPlan> qqchDelayProjectPayPlanList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchDelayProjectPayPlanList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchDelayProjectPayPlan qqchDelayProjectPayPlan : qqchDelayProjectPayPlanList) {
            qqchDelayProjectPayPlan.setValid(valid);
            qqchDelayProjectPayPlan.setVersion(version);
            qqchDelayProjectPayPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchDelayProjectPayPlan.setCreateUserName(SecurityUtils.getUserName());
            qqchDelayProjectPayPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchDelayProjectPayPlanMapper.insertQqchDelayProjectPayPlanList(qqchDelayProjectPayPlanList);
    }
}
