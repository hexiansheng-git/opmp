package com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlan;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.domain.QqchSurveyWorkPlanVo;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.mapper.QqchSurveyWorkPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyWorkPlan.service.IQqchSurveyWorkPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-07-20 11:49:55
 * @remark  2.2 勘察设计工作计划
 */
@Service
public class QqchSurveyWorkPlanServiceImpl implements IQqchSurveyWorkPlanService{

    @Autowired
    private QqchSurveyWorkPlanMapper qqchSurveyWorkPlanMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchMainPlanItemService qqchMainPlanItemService;




    public QqchSurveyWorkPlanVo getQqchSurveyWorkPlanList(QqchSurveyWorkPlan qqchSurveyWorkPlan) {
        BigDecimal version = VersionUtil.getVersion("qqch_survey_work_plan",qqchSurveyWorkPlan.getVersion());
        qqchSurveyWorkPlan.setVersion(version);
        List<QqchSurveyWorkPlan> qqchSurveyWorkPlanList = qqchSurveyWorkPlanMapper.getQqchSurveyWorkPlanList(qqchSurveyWorkPlan);
        List<QqchSurveyWorkPlan> qqchSurveyWorkPlans = TreeUtil.build(qqchSurveyWorkPlanList, null);
        QqchSurveyWorkPlanVo vo = new QqchSurveyWorkPlanVo();
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSurveyWorkPlanList(qqchSurveyWorkPlans);
        return  vo;
    }

    @Override
    public void save(QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo) {
        //删除旧数据
        QqchSurveyWorkPlan qqchSurveyWorkPlan = new QqchSurveyWorkPlan();
        qqchSurveyWorkPlan.setVersion(qqchSurveyWorkPlanVo.getVersion());
        qqchSurveyWorkPlanMapper.deleteQqchSurveyWorkPlan(qqchSurveyWorkPlan);
        List<QqchSurveyWorkPlan> paramList = qqchSurveyWorkPlanVo.getQqchSurveyWorkPlanList();
        if (CollectionUtils.isEmpty(paramList)){
            return;
        }
        //插入新数据
        this.insertQqchSurveyWorkPlanList(paramList, qqchSurveyWorkPlanVo.getVersion());
    }

    @Override
    public void confirm(QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo) {
        this.save(qqchSurveyWorkPlanVo);
        String buttonMark = qqchSurveyWorkPlanVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchSurveyWorkPlanVo.getMenuId();
            String stageIdentity = qqchSurveyWorkPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    private void insertQqchSurveyWorkPlanList(List<QqchSurveyWorkPlan> qqchSurveyWorkPlanList, BigDecimal version) {
        List<QqchSurveyWorkPlan> insertList = TreeUtil.treeToList(qqchSurveyWorkPlanList);
        for (QqchSurveyWorkPlan    qqchSurveyWorkPlan : insertList) {
            qqchSurveyWorkPlan.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchSurveyWorkPlan.setValid(Valid.YES);
            }
            qqchSurveyWorkPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSurveyWorkPlan.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            qqchSurveyWorkPlan.setCreateTime(DateUtils.getNowDate());

        }
        qqchSurveyWorkPlanMapper.insertQqchSurveyWorkPlanList(insertList);
    }

    @Override
    public List<QqchSurveyWorkPlan> handleActivityData(QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo) {
        List<QqchSurveyWorkPlan> originTreeList = qqchSurveyWorkPlanVo.getQqchSurveyWorkPlanList();
        List<QqchMainPlanItem> newList = qqchSurveyWorkPlanVo.getQqchMainPlanItemList();
        if (CollectionUtil.isEmpty(newList)) {
            return originTreeList;
        }
        List<Long> ids = newList.stream().map(QqchMainPlanItem::getId).collect(Collectors.toList());
        //获取选中数据的所有上下级
        List<QqchMainPlanItem> allLinkList = qqchMainPlanItemService.getAllLinkList(ids);

        List<QqchSurveyWorkPlan> transBeanList = new ArrayList<>();
        allLinkList.forEach(p -> {
            QqchSurveyWorkPlan qqchSurveyWorkPlan = new QqchSurveyWorkPlan();
            qqchSurveyWorkPlan.setId(p.getId());
            qqchSurveyWorkPlan.setPlanWbsId(StrUtil.isBlank(p.getWbsObjectId())?1L:Long.valueOf(p.getWbsObjectId()));
            qqchSurveyWorkPlan.setPlanWbsPid(StrUtil.isBlank(p.getWbsParentObjectId())?null:Long.valueOf(p.getWbsParentObjectId()));
            qqchSurveyWorkPlan.setPlanWbsCode(p.getItemCode());
            qqchSurveyWorkPlan.setPlanWbsName(p.getItemName());
            qqchSurveyWorkPlan.setUnit(p.getUnit());
            qqchSurveyWorkPlan.setWorkNum(p.getQuantity() == null ? "0" : String.valueOf(p.getQuantity()));
            qqchSurveyWorkPlan.setStartTime(p.getStartDate());
            qqchSurveyWorkPlan.setEndTime(p.getFinishDate());
            Long pid = p.getPid();
            qqchSurveyWorkPlan.setPid(pid);
            if ( null == pid ) qqchSurveyWorkPlan.setPlanWbsPid(null);
            transBeanList.add(qqchSurveyWorkPlan);
        });
        if (CollectionUtil.isEmpty(originTreeList)) {
            return build(transBeanList, null);
        }
        List<QqchSurveyWorkPlan> originList = TreeUtil.treeToListWithoutId(originTreeList);
        Map<String, List<QqchSurveyWorkPlan>> collect = originList.stream()
                .filter(p -> StrUtil.isNotBlank(p.getWorkContent()) || StrUtil.isNotBlank(p.getRemark()))
                .collect(Collectors.groupingBy(QqchSurveyWorkPlan::getPlanWbsCode));
        HashMap<String, QqchSurveyWorkPlan> objects = new HashMap<>();
        for (int i = 0; i < originList.size(); i++) {
            QqchSurveyWorkPlan qqchSurveyWorkPlan = originList.get(i);
            objects.put(qqchSurveyWorkPlan.getPlanWbsCode(), qqchSurveyWorkPlan);
        }
        for (int i = 0; i < transBeanList.size(); i++) {
            QqchSurveyWorkPlan qqchSurveyWorkPlan = transBeanList.get(i);
            objects.put(qqchSurveyWorkPlan.getPlanWbsCode(), qqchSurveyWorkPlan);
        }
        List<QqchSurveyWorkPlan> qqchSurveyWorkPlans = objects.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        qqchSurveyWorkPlans.forEach(p -> {
            if (collect.containsKey(p.getPlanWbsCode())) {
                QqchSurveyWorkPlan qqchSurveyWorkPlan = collect.get(p.getPlanWbsCode()).get(0);
                p.setWorkContent(qqchSurveyWorkPlan.getWorkContent());
                p.setRemark(qqchSurveyWorkPlan.getRemark());
            }
        });
        List<QqchSurveyWorkPlan> build = build(qqchSurveyWorkPlans, null);
        return build;
    }

    /**
     * 根据pid，构建树节点
     */
    public static List<QqchSurveyWorkPlan> build(List<QqchSurveyWorkPlan> treeNodes, Long pid) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return new ArrayList<>();
        }
        treeNodes.forEach(treeVO -> {

            List<QqchSurveyWorkPlan> nChildren = treeNodes.stream().filter((item) -> treeVO.getPlanWbsId().equals(item.getPlanWbsPid()))
                    .collect(Collectors.toList());

            List<QqchSurveyWorkPlan> oChildren = treeVO.getChildren();
            if (CollectionUtils.isNotEmpty(oChildren)) {
                nChildren = CollectionUtils.isEmpty(nChildren) ? new ArrayList<>() : nChildren;
                nChildren.addAll(oChildren);
            }
            treeVO.setChildren(nChildren);
        });
        List<QqchSurveyWorkPlan> collect;
        if (pid == null) {
            collect = treeNodes.stream().filter((item) -> item.getPlanWbsPid() == null)
                    .collect(Collectors.toList());
        } else {
            collect = treeNodes.stream().filter((item) -> pid.equals(item.getPlanWbsPid()))
                    .collect(Collectors.toList());
        }
        return collect;
    }
}
