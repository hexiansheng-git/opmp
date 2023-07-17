package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionReviewPlan;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionReviewPlanVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchConstructionListMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchConstructionReviewPlanMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionReviewPlanService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtils;
import java.math.BigDecimal;
import java.util.List;
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
    private QqchConstructionListMapper qqchConstructionListMapper;
    @Autowired
    private CommonMapper commonMapper;


    public QqchConstructionReviewPlan getQqchConstructionReviewPlan(
        QqchConstructionReviewPlan qqchConstructionReviewPlan) {
        return qqchConstructionReviewPlanMapper.getQqchConstructionReviewPlan(qqchConstructionReviewPlan);
    }

    public QqchConstructionReviewPlanVo getQqchConstructionReviewPlanList() {
        QqchConstructionReviewPlanVo vo = new QqchConstructionReviewPlanVo();

        // 获取最大版本号
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_construction_review_plan");
        vo.setVersion(maxVersion);

        QqchConstructionReviewPlan qryParam = new QqchConstructionReviewPlan();
        qryParam.setVersion(maxVersion);
        List<QqchConstructionReviewPlan> list = qqchConstructionReviewPlanMapper
            .getQqchConstructionReviewPlanList(qryParam);
        vo.setTreeList(TreeUtils.listToTree(list));
        return vo;
    }

    @Transactional
    public void syncData() {
        BigDecimal maxVersion = commonMapper.selectMaxVersion("qqch_construction_list");
        QqchConstructionList qryParam = new QqchConstructionList();
        qryParam.setVersion(maxVersion);
        // 获取方案清单数据
        List<QqchConstructionList> constructionList = qqchConstructionListMapper
            .getBigDangerLevelConstructionList(qryParam);
        // todo

    }

    @Transactional
    public int insertQqchConstructionReviewPlan(QqchConstructionReviewPlan qqchConstructionReviewPlan) {
        qqchConstructionReviewPlan.setId(IdWorker.createId());
        qqchConstructionReviewPlan.setCreateUser(SecurityUtils.getUserName());
        qqchConstructionReviewPlan.setCreateTime(DateUtils.getNowDate());
        return qqchConstructionReviewPlanMapper.insertQqchConstructionReviewPlan(qqchConstructionReviewPlan);
    }

    @Transactional
    public void batchSave(QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo) {
        if (qqchConstructionReviewPlanVo.getVersion() == null) {
            throw new RuntimeException("版本号不能为空！");
        }

        // 先批量删除当前版本所有数据
        QqchConstructionReviewPlan deleteParam = new QqchConstructionReviewPlan();
        deleteParam.setVersion(qqchConstructionReviewPlanVo.getVersion());
        deleteParam.setDelFlag("1");
        qqchConstructionReviewPlanMapper.updateQqchConstructionReviewPlan(deleteParam);

        if (CollectionUtils.isEmpty(qqchConstructionReviewPlanVo.getTreeList())) {
            return;
        }

        // 树转list
        List<QqchConstructionReviewPlan> insertList = TreeUtils
            .splitTreeList(qqchConstructionReviewPlanVo.getTreeList());

        if (!CollectionUtils.isEmpty(insertList)) {
            for (QqchConstructionReviewPlan insert : insertList) {
                insert.setVersion(qqchConstructionReviewPlanVo.getVersion());
                insert.setValid("1");
                insert.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                insert.setCreateUserName(SecurityUtils.getUserName());
                insert.setCreateTime(DateUtils.getNowDate());
            }
        }

        // 全量入库
        qqchConstructionReviewPlanMapper.insertQqchConstructionReviewPlanList(insertList);
    }

    @Transactional
    public int updateQqchConstructionReviewPlan(QqchConstructionReviewPlan qqchConstructionReviewPlan) {
        qqchConstructionReviewPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchConstructionReviewPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchConstructionReviewPlanMapper.updateQqchConstructionReviewPlan(qqchConstructionReviewPlan);
    }

    @Transactional
    public int updateQqchConstructionReviewPlanList(List<QqchConstructionReviewPlan> qqchConstructionReviewPlanList) {
        for (QqchConstructionReviewPlan qqchConstructionReviewPlan : qqchConstructionReviewPlanList) {
            qqchConstructionReviewPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchConstructionReviewPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchConstructionReviewPlanMapper.updateQqchConstructionReviewPlanList(qqchConstructionReviewPlanList);
    }

    @Transactional
    public int deleteQqchConstructionReviewPlan(QqchConstructionReviewPlan qqchConstructionReviewPlan) {
        qqchConstructionReviewPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchConstructionReviewPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchConstructionReviewPlanMapper.deleteQqchConstructionReviewPlan(qqchConstructionReviewPlan);
    }

    @Transactional
    public int deleteQqchConstructionReviewPlanByPks(List<Long> qqchConstructionReviewPlanPkList) {
        return qqchConstructionReviewPlanMapper.deleteQqchConstructionReviewPlanByPks(qqchConstructionReviewPlanPkList);
    }
}
