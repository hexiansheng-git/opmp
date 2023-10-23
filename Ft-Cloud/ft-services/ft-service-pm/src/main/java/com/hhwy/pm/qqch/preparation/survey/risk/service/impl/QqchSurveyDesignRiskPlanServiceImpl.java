package com.hhwy.pm.qqch.preparation.survey.risk.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.DictType;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.common.defaultData.service.IQqchDefaultDataInitializeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.ModuleIdentity;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.QqchSurveyDesignRiskPlan;
import com.hhwy.pm.qqch.preparation.survey.risk.domain.vo.QqchSurveyDesignRiskPlanVo;
import com.hhwy.pm.qqch.preparation.survey.risk.mapper.QqchSurveyDesignRiskPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.risk.service.IQqchSurveyDesignRiskPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author han
 * @date 2023-07-13 11:39:34
 * @remark 勘察设计风险策划
 */
@Service
public class QqchSurveyDesignRiskPlanServiceImpl implements IQqchSurveyDesignRiskPlanService {

    @Autowired
    private QqchSurveyDesignRiskPlanMapper qqchSurveyDesignRiskPlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private IQqchReviewService qqchReviewService;

    @Autowired
    private IQqchDefaultDataInitializeService qqchDefaultDataInitializeService;


    /**
     * 勘察设计风险策划Vo
     * @return
     * @param version
     */
    @Override
    @Transactional
    public QqchSurveyDesignRiskPlanVo getQqchSurveyDesignRiskPlanVo(BigDecimal version) {
        QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo = new QqchSurveyDesignRiskPlanVo();

        version = VersionUtil.getVersion("qqch_survey_design_risk_plan",version);
        QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan = new QqchSurveyDesignRiskPlan();
        qqchSurveyDesignRiskPlan.setVersion(version);
        List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList = qqchSurveyDesignRiskPlanMapper.getQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlan);

        qqchSurveyDesignRiskPlanVo.setVersion(version);
        qqchSurveyDesignRiskPlanVo.setStageIdentity(qqchReviewService.getStage());
        if(CollectionUtils.isEmpty(qqchSurveyDesignRiskPlanList)){
            //判断是否已经初始化过
            boolean initialize = qqchDefaultDataInitializeService.interpretInitializeStatus(ModuleIdentity.QQCH_SURVEY_DESIGN_RISK_PLAN, version);
            if(!initialize){
                qqchSurveyDesignRiskPlanList = this.getInitializeData();
                //入库
                qqchSurveyDesignRiskPlanMapper.insertQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanList);
            }
            qqchSurveyDesignRiskPlanVo.setQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanList);
            return qqchSurveyDesignRiskPlanVo;
        }
        //转换树列表
        List<QqchSurveyDesignRiskPlan> treeList = ListTreeUtil.formatTree(
                qqchSurveyDesignRiskPlanList,
                o -> o.getPid() == null,
                (r,n) -> r.getId().equals(n.getPid()),
                QqchSurveyDesignRiskPlan::getChildren,
                QqchSurveyDesignRiskPlan::setChildren);

        qqchSurveyDesignRiskPlanVo.setQqchSurveyDesignRiskPlanList(treeList);
        return qqchSurveyDesignRiskPlanVo;
    }

    /**
     * 获取初始化数据
     * @return
     */
    public List<QqchSurveyDesignRiskPlan> getInitializeData(){
        List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList = new ArrayList<>();

        AjaxResult result = systemServiceApi.dictType(DictType.SURVEY_DESIGN_RISK_PLAN_INITIALIZE);
        List<Map<String,Object>> dictDataList = (List<Map<String, Object>>) result.get("data");

        for (Map<String, Object> map : dictDataList) {
            String dictLabel = (String) map.get("dictLabel");
            QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan = new QqchSurveyDesignRiskPlan();
            qqchSurveyDesignRiskPlan.setId(IdWorker.createId());
            qqchSurveyDesignRiskPlan.setRiskIdentificationItem(dictLabel);
            qqchSurveyDesignRiskPlanList.add(qqchSurveyDesignRiskPlan);
        }

        return qqchSurveyDesignRiskPlanList;
    }

    /**
     * 保存
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo) {
        //删除旧数据
        QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan = new QqchSurveyDesignRiskPlan();
        qqchSurveyDesignRiskPlan.setVersion(qqchSurveyDesignRiskPlan.getVersion());
        qqchSurveyDesignRiskPlanMapper.deleteQqchSurveyDesignRiskPlan(qqchSurveyDesignRiskPlan);

        //插入新数据
        this.insertQqchSurveyDesignRiskPlanList(qqchSurveyDesignRiskPlanVo.getQqchSurveyDesignRiskPlanList(), qqchSurveyDesignRiskPlanVo.getVersion());
    }

    /**
     * 确认
     * @param qqchSurveyDesignRiskPlanVo
     * @return
     */
    @Override
    public void confirm(QqchSurveyDesignRiskPlanVo qqchSurveyDesignRiskPlanVo) {
        this.save(qqchSurveyDesignRiskPlanVo);

        String buttonMark = qqchSurveyDesignRiskPlanVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchSurveyDesignRiskPlanVo.getMenuId();
            String stageIdentity = qqchSurveyDesignRiskPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 批量插入
     * @param qqchSurveyDesignRiskPlanList
     * @param version
     */
    @Transactional
    public void insertQqchSurveyDesignRiskPlanList(List<QqchSurveyDesignRiskPlan> qqchSurveyDesignRiskPlanList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchSurveyDesignRiskPlanList)){
            return;
        }
        List<QqchSurveyDesignRiskPlan> insertList = ListTreeUtil.formatList(
                qqchSurveyDesignRiskPlanList,
                QqchSurveyDesignRiskPlan::setId,
                QqchSurveyDesignRiskPlan::setPid,
                QqchSurveyDesignRiskPlan::setSort,
                QqchSurveyDesignRiskPlan::getChildren,
                QqchSurveyDesignRiskPlan::setChildren);
        for (QqchSurveyDesignRiskPlan qqchSurveyDesignRiskPlan : insertList) {
            qqchSurveyDesignRiskPlan.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchSurveyDesignRiskPlan.setValid(Valid.YES);
            }
            qqchSurveyDesignRiskPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSurveyDesignRiskPlan.setCreateUserName(SecurityUtils.getUserName());
            qqchSurveyDesignRiskPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchSurveyDesignRiskPlanMapper.insertQqchSurveyDesignRiskPlanList(insertList);
    }
}
