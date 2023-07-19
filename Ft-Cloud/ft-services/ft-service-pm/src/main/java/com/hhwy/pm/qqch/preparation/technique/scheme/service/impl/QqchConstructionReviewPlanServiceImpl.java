package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionReviewPlan;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionListVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionReviewPlanVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchConstructionReviewPlanMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionListService;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionReviewPlanService;
import com.hhwy.utils.idworker.IdWorker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenlili
 * @date 2023-07-17 15:32:17
 * @remark
 */
@Service
public class QqchConstructionReviewPlanServiceImpl implements IQqchConstructionReviewPlanService {

    @Autowired
    private QqchConstructionReviewPlanMapper qqchConstructionReviewPlanMapper;
    @Autowired
    private IQqchConstructionListService qqchConstructionListService;
    @Autowired
    private CommonMapper commonMapper;

    public QqchConstructionReviewPlanVo getQqchConstructionReviewPlanList() {
        QqchConstructionReviewPlanVo vo = new QqchConstructionReviewPlanVo();

        List<QqchConstructionReviewPlan> onePlanList = this.getPlanListBySchemeLevel("1");
        List<QqchConstructionReviewPlan> twoPlanList = this.getPlanListBySchemeLevel("2");
        List<QqchConstructionReviewPlan> threePlanList = this.getPlanListBySchemeLevel("3");
        List<QqchConstructionReviewPlan> fourPlanList = this.getPlanListBySchemeLevel("4");

        List<QqchConstructionReviewPlan> newList = new ArrayList<>();

        QqchConstructionReviewPlan planOne = new QqchConstructionReviewPlan();
        planOne.setSchemeLevel("I级施工方案");
        planOne.setChildren(onePlanList);
        newList.add(planOne);

        QqchConstructionReviewPlan planTwo = new QqchConstructionReviewPlan();
        planTwo.setSchemeLevel("II级施工方案");
        planTwo.setChildren(twoPlanList);
        newList.add(planTwo);

        QqchConstructionReviewPlan planTree = new QqchConstructionReviewPlan();
        planTree.setSchemeLevel("III级施工方案");
        planTree.setChildren(threePlanList);
        newList.add(planTree);

        QqchConstructionReviewPlan planFour = new QqchConstructionReviewPlan();
        planFour.setSchemeLevel("IV级施工方案");
        planFour.setChildren(fourPlanList);
        newList.add(planFour);
        vo.setList(newList);
        return vo;
    }

    @Transactional
    public void syncData() {
        // 获取当前数据库表数据
        List<QqchConstructionReviewPlan> dbList = this.getQqchConstructionReviewPlanList().getList();

        // 获取方案清单数据
        QqchConstructionListVo listVo = qqchConstructionListService.getQqchConstructionListList();

        // 构造新的list
        List<QqchConstructionReviewPlan> insertList = new ArrayList<>();
        for (QqchConstructionList construction : listVo.getList()) {
            QqchConstructionReviewPlan insert = new QqchConstructionReviewPlan();
            BeanUtils.copyProperties(construction, insert);

            insert.setId(IdWorker.createId());
            insert.setCreateUser(SecurityUtils.getUserName());
            insert.setCreateTime(DateUtils.getNowDate());
            insert.setVersion(listVo.getVersion());
            insert.setValid(Valid.YES);

            for (QqchConstructionReviewPlan db : dbList) {
                if (insert.getSchemeCode().equals(db.getSchemeCode())) {
                    insert.setSchemeLevelDescription(db.getSchemeLevelDescription());
                    insert.setPreparationMainBody(db.getPreparationMainBody());
                    insert.setContactInfo(db.getContactInfo());
                    insert.setReviewMainBody(db.getReviewMainBody());
                }
            }
            insertList.add(insert);
        }

        // 先清空表中旧数据
        this.deleteByVersion();

        if (insertList.size() > 0) {
            qqchConstructionReviewPlanMapper.insertQqchConstructionReviewPlanList(insertList);
        }
    }

    @Transactional
    public void batchSave(QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo) {
        if (qqchConstructionReviewPlanVo.getVersion() == null) {
            // 获取最大版本号
            BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_construction_review_plan");
            qqchConstructionReviewPlanVo.setVersion(maxVersion);
        }

        if (CollectionUtils.isEmpty(qqchConstructionReviewPlanVo.getList())) {
            return;
        }

        List<QqchConstructionReviewPlan> updateList = new ArrayList<>();

        for (QqchConstructionReviewPlan plan : qqchConstructionReviewPlanVo.getList()) {
            List<QqchConstructionReviewPlan> planList = plan.getChildren();
            for (QqchConstructionReviewPlan update : planList) {
                update.setUpdateUser(SecurityUtils.getUserName());
                update.setUpdateTime(DateUtils.getNowDate());
                updateList.add(update);
            }
        }

        // 数据更新
        qqchConstructionReviewPlanMapper.updateQqchConstructionReviewPlanList(updateList);
    }

    @Transactional
    public int deleteByVersion() {
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_construction_review_plan");

        // 先批量删除当前版本所有数据
        QqchConstructionReviewPlan deleteParam = new QqchConstructionReviewPlan();
        deleteParam.setVersion(maxVersion);
        deleteParam.setDelFlag("1");
        return qqchConstructionReviewPlanMapper.updateQqchConstructionReviewPlan(deleteParam);
    }

    public List<QqchConstructionReviewPlan> getPlanListBySchemeLevel(String schemeLevel) {
        // 获取最大版本号
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_construction_review_plan");
        QqchConstructionReviewPlan qryParam = new QqchConstructionReviewPlan();
        qryParam.setVersion(maxVersion);
        qryParam.setSchemeLevel(schemeLevel);
        List<QqchConstructionReviewPlan> planList = qqchConstructionReviewPlanMapper
            .getQqchConstructionReviewPlanList(qryParam);
        return planList;
    }

    /**
     * 确认
     * @param qqchConstructionReviewPlanVo
     * @return
     */
    @Override
    public void confirm(QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo) {
        this.batchSave(qqchConstructionReviewPlanVo);
        // TODO 修改确认状态
    }
}
