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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<QqchSurveyWorkPlan> qqchSurveyWorkPlans = TreeUtil.build(qqchSurveyWorkPlanList, 0l);
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
            if(qqchSurveyWorkPlan.getPid()==null){
                qqchSurveyWorkPlan.setPid(0l);
            }
        }
        qqchSurveyWorkPlanMapper.insertQqchSurveyWorkPlanList(insertList);
    }

    @Override
    public List<QqchSurveyWorkPlan> handleActivityData(QqchSurveyWorkPlanVo qqchSurveyWorkPlanVo) {
        List<QqchSurveyWorkPlan> originList = qqchSurveyWorkPlanVo.getQqchSurveyWorkPlanList();
        List<QqchMainPlanItem> newList = qqchSurveyWorkPlanVo.getQqchMainPlanItemList();
        if (CollectionUtil.isEmpty(newList)) {
            return originList;
        }
//        List<QqchMainPlanItem> qqchMainPlanItems = TreeUtil.treeToList(newList);
        List<Long> ids = newList.stream().map(QqchMainPlanItem::getId).collect(Collectors.toList());
        //获取选中数据的所有上下级
        List<QqchMainPlanItem> allLinkList = qqchMainPlanItemService.getAllLinkList(ids);

        List<QqchSurveyWorkPlan> transBeanList = new ArrayList<>();
        allLinkList.forEach(p -> {
            QqchSurveyWorkPlan qqchSurveyWorkPlan = new QqchSurveyWorkPlan();
            qqchSurveyWorkPlan.setId(p.getId());
            qqchSurveyWorkPlan.setPlanWbsCode(p.getWbsCode());
            qqchSurveyWorkPlan.setPlanWbsName(p.getWbsName());
            qqchSurveyWorkPlan.setUnit(p.getUnit());
            qqchSurveyWorkPlan.setWorkNum(String.valueOf(p.getQuantity()));
            qqchSurveyWorkPlan.setStartTime(p.getStartDate());
            qqchSurveyWorkPlan.setEndTime(p.getFinishDate());
            qqchSurveyWorkPlan.setPid(p.getPid());
            transBeanList.add(qqchSurveyWorkPlan);
        });
        if (CollectionUtil.isEmpty(originList)) {
            return transBeanList;
        }
        Map<String, List<QqchSurveyWorkPlan>> collect = originList.stream()
                .filter(p -> StrUtil.isNotBlank(p.getWorkContent()) && StrUtil.isNotBlank(p.getRemark()))
                .collect(Collectors.groupingBy(QqchSurveyWorkPlan::getPlanWbsCode));
        Set<QqchSurveyWorkPlan> qqchSurveyWorkPlans = CollectionUtil.unionDistinct(originList, transBeanList);
        qqchSurveyWorkPlans.forEach(p -> {
            if (collect.containsKey(p.getPlanWbsCode())) {
                QqchSurveyWorkPlan qqchSurveyWorkPlan = collect.get(p.getPlanWbsCode()).get(0);
                p.setWorkContent(qqchSurveyWorkPlan.getWorkContent());
                p.setRemark(qqchSurveyWorkPlan.getRemark());
            }
        });
        ArrayList<QqchSurveyWorkPlan> qqchSurveyWorkPlans1 = new ArrayList<>(qqchSurveyWorkPlans);
        List<QqchSurveyWorkPlan> build = TreeUtil.build(qqchSurveyWorkPlans1, null);
        return build;
    }
}
