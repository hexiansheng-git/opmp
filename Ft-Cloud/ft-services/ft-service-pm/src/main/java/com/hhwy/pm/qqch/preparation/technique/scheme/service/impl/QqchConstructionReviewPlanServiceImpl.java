package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionReviewPlan;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionReviewPlanVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchConstructionReviewPlanMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionListService;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionReviewPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhenglili
 * @date 2023-07-17 15:32:17
 * @remark 施工方案编审计划
 */
@Service
public class QqchConstructionReviewPlanServiceImpl implements IQqchConstructionReviewPlanService {

    @Autowired
    private QqchConstructionReviewPlanMapper qqchConstructionReviewPlanMapper;
    @Autowired
    private IQqchConstructionListService qqchConstructionListService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;

    public QqchConstructionReviewPlanVo getQqchConstructionReviewPlanList(BigDecimal version) {
        QqchConstructionReviewPlanVo vo = new QqchConstructionReviewPlanVo();
        version = VersionUtil.getVersion("qqch_construction_review_plan", version);
        vo.setVersion(version);

        Map<String, List<QqchConstructionReviewPlan>> map = this.getPlanListMapGroupByLevel(version);

        List<QqchConstructionReviewPlan> newList = new ArrayList<>();

        QqchConstructionReviewPlan planOne = new QqchConstructionReviewPlan();

        planOne.setSchemeLevel("I级施工方案");
        planOne.setId(1L);
        planOne.setChildren(map.get("1"));
        newList.add(planOne);

        QqchConstructionReviewPlan planTwo = new QqchConstructionReviewPlan();
        planTwo.setId(2L);
        planTwo.setSchemeLevel("II级施工方案");
        planTwo.setChildren(map.get("2"));
        newList.add(planTwo);

        QqchConstructionReviewPlan planTree = new QqchConstructionReviewPlan();
        planTree.setId(3L);
        planTree.setSchemeLevel("III级施工方案");
        planTree.setChildren(map.get("3"));
        newList.add(planTree);

        QqchConstructionReviewPlan planFour = new QqchConstructionReviewPlan();
        planTree.setId(4L);
        planFour.setSchemeLevel("IV级施工方案");
        planFour.setChildren(map.get("4"));
        newList.add(planFour);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setList(newList);
        return vo;
    }

    @Transactional
    public void syncData(QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo) {
        /*该版本为后端返回或者为变更版本*/
        BigDecimal version = qqchConstructionReviewPlanVo.getVersion();
        //获取方案清单数据
        List<QqchConstructionList> latestList = qqchConstructionListService.getLatest(version);

        Map<String, QqchConstructionReviewPlan> map = new HashMap<>();
        List<QqchConstructionReviewPlan> list = qqchConstructionReviewPlanVo.getList();
        list.stream().forEach(plan -> {
            List<QqchConstructionReviewPlan> children = plan.getChildren();
            if(!CollectionUtils.isEmpty(children)){
                children.stream().forEach(o -> {
                    map.put(o.getSchemeCode(),o);
                });
            }
        });

        // 构造新的list
        List<QqchConstructionReviewPlan> insertList = new ArrayList<>();
        latestList.stream().forEach(construction -> {
            QqchConstructionReviewPlan insert = new QqchConstructionReviewPlan();
            BeanUtils.copyProperties(construction, insert);

            insert.setId(IdWorker.createId());
            insert.setCreateUser(SecurityUtils.getUserName());
            insert.setCreateTime(DateUtils.getNowDate());
            insert.setVersion(version);
            insert.setValid(Valid.YES);

            String schemeCode = construction.getSchemeCode();
            QqchConstructionReviewPlan plan = map.get(schemeCode);
            if(plan != null){
                insert.setSchemeLevelDescription(plan.getSchemeLevelDescription());
                insert.setPreparationMainBody(plan.getPreparationMainBody());
                insert.setContactInfo(plan.getContactInfo());
                insert.setReviewMainBody(plan.getReviewMainBody());
            }
            insertList.add(insert);
        });

        // 先清空表中旧数据
        this.deleteByVersion(version);

        if (insertList.size() > 0) {
            qqchConstructionReviewPlanMapper.insertQqchConstructionReviewPlanList(insertList);
        }
    }

    @Transactional
    public void batchSave(QqchConstructionReviewPlanVo qqchConstructionReviewPlanVo) {
        if (CollectionUtils.isEmpty(qqchConstructionReviewPlanVo.getList())) {
            return;
        }

        List<QqchConstructionReviewPlan> updateList = new ArrayList<>();
        List<QqchConstructionReviewPlan> list = qqchConstructionReviewPlanVo.getList();
        list.stream().forEach(plan -> {
            List<QqchConstructionReviewPlan> children = plan.getChildren();
            if(!CollectionUtils.isEmpty(children)){
                children.stream().forEach(child -> {
                    child.setUpdateUser(SecurityUtils.getUserName());
                    child.setUpdateTime(DateUtils.getNowDate());
                    updateList.add(child);
                });
            }
        });
        if (!CollectionUtils.isEmpty(updateList)) {
            // 数据更新
            qqchConstructionReviewPlanMapper.updateQqchConstructionReviewPlanList(updateList);
        }

        String buttonMark = qqchConstructionReviewPlanVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = qqchConstructionReviewPlanVo.getMenuId();
            String stageIdentity = qqchConstructionReviewPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Transactional
    public int deleteByVersion(BigDecimal version) {
        QqchConstructionReviewPlan deleteParam = new QqchConstructionReviewPlan();
        deleteParam.setVersion(version);
        return qqchConstructionReviewPlanMapper.deleteQqchConstructionReviewPlan(deleteParam);
    }

    public Map<String, List<QqchConstructionReviewPlan>> getPlanListMapGroupByLevel(BigDecimal version) {
        QqchConstructionReviewPlan qryParam = new QqchConstructionReviewPlan();
        qryParam.setVersion(version);
        List<QqchConstructionReviewPlan> planList = qqchConstructionReviewPlanMapper.getQqchConstructionReviewPlanList(qryParam);
        return planList.stream().filter(o -> StringUtils.isNotBlank(o.getSchemeLevel())).collect(Collectors.groupingBy(QqchConstructionReviewPlan::getSchemeLevel));
    }
}
