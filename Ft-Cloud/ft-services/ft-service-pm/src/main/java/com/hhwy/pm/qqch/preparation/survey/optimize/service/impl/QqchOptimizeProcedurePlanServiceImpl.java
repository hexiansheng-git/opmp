package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.DictType;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.ModuleIdentity;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.extend.domain.QqchPreparationSurveyExtend;
import com.hhwy.pm.qqch.preparation.survey.extend.service.impl.QqchPreparationSurveyExtendServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeProcedurePlan;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchOptimizeProcedurePlanVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchOptimizeProcedurePlanMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeProcedurePlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
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
 * @date 2023-07-07 18:35:53
 * @remark 优化程序策划
 */
@Service
public class QqchOptimizeProcedurePlanServiceImpl implements IQqchOptimizeProcedurePlanService {

    @Autowired
    private QqchOptimizeProcedurePlanMapper qqchOptimizeProcedurePlanMapper;

    @Autowired
    private QqchPreparationSurveyExtendServiceImpl qqchPreparationSurveyExtendService;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private IQqchReviewService qqchReviewService;


    /**
     * 获取优化程序策划集合
     * @return
     * @param version
     */
    @Override
    public QqchOptimizeProcedurePlanVo getQqchOptimizeProcedurePlanVo(BigDecimal version) {
        QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo = new QqchOptimizeProcedurePlanVo();

        version = VersionUtil.getVersion("qqch_optimize_procedure_plan",version);
        List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList = qqchOptimizeProcedurePlanMapper.getQqchOptimizeProcedurePlanList(version);
        if(CollectionUtils.isEmpty(qqchOptimizeProcedurePlanList)){
            //数据库中没有数据，需要初始化
            qqchOptimizeProcedurePlanList = this.getInitializeData();
        }

        //获取附件组id（页面标识和版本号控制）
        QqchPreparationSurveyExtend qqchPreparationSurveyExtend = qqchPreparationSurveyExtendService.getQqchPreparationSurveyExtend(ModuleIdentity.OPTIMIZE_PROCEDURE_PLAN, version);
        if(qqchPreparationSurveyExtend != null){
            qqchOptimizeProcedurePlanVo.setFileGroupId(qqchPreparationSurveyExtend.getFileGroupId());
        }

        qqchOptimizeProcedurePlanVo.setVersion(version);
        qqchOptimizeProcedurePlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchOptimizeProcedurePlanVo.setQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanList);
        return qqchOptimizeProcedurePlanVo;
    }

    /**
     * 获取初始化数据
     * @return
     */
    public List<QqchOptimizeProcedurePlan> getInitializeData() {
        List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList = new ArrayList<>();

        AjaxResult result = systemServiceApi.dictType(DictType.OPTIMIZE_PROCEDURE_PLAN_INITIALIZE);
        List<Map<String,Object>> dictDataList = (List<Map<String, Object>>) result.get("data");

        for (Map<String, Object> map : dictDataList) {
            String dictValue = (String) map.get("dictValue");
            String dictLabel = (String) map.get("dictLabel");
            QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan = new QqchOptimizeProcedurePlan();
            qqchOptimizeProcedurePlan.setLinkName(dictLabel);
            qqchOptimizeProcedurePlan.setWorkContent(dictValue);
            qqchOptimizeProcedurePlanList.add(qqchOptimizeProcedurePlan);
        }

        return qqchOptimizeProcedurePlanList;
    }

    /**
     * 保存
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo) {
        //删除旧数据
        QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan = new QqchOptimizeProcedurePlan();
        qqchOptimizeProcedurePlan.setVersion(qqchOptimizeProcedurePlanVo.getVersion());
        qqchOptimizeProcedurePlanMapper.deleteQqchOptimizeProcedurePlan(qqchOptimizeProcedurePlan);

        //维护附件
        String fileGroupId = qqchOptimizeProcedurePlanVo.getFileGroupId();
        QqchPreparationSurveyExtend qqchPreparationSurveyExtend = qqchPreparationSurveyExtendService.getQqchPreparationSurveyExtend(ModuleIdentity.OPTIMIZE_PROCEDURE_PLAN, qqchOptimizeProcedurePlanVo.getVersion());
        if(qqchPreparationSurveyExtend == null){
            qqchPreparationSurveyExtend = new QqchPreparationSurveyExtend();
            qqchPreparationSurveyExtend.setFileGroupId(fileGroupId);
            qqchPreparationSurveyExtend.setModuleIdentity(ModuleIdentity.OPTIMIZE_PROCEDURE_PLAN);
            qqchPreparationSurveyExtend.setVersion(qqchOptimizeProcedurePlanVo.getVersion());
            qqchPreparationSurveyExtend.setValid(Valid.YES);
            qqchPreparationSurveyExtendService.insertQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
        }

        //插入新数据
        this.insertQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanVo.getQqchOptimizeProcedurePlanList(),qqchOptimizeProcedurePlanVo.getVersion());
    }

    /**
     * 确认
     * @param qqchOptimizeProcedurePlanVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchOptimizeProcedurePlanVo qqchOptimizeProcedurePlanVo) {
        this.save(qqchOptimizeProcedurePlanVo);

        String buttonMark = qqchOptimizeProcedurePlanVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchOptimizeProcedurePlanVo.getMenuId();
            String stageIdentity = qqchOptimizeProcedurePlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 批量插入
     * @param qqchOptimizeProcedurePlanList
     * @return
     */
    @Transactional
    public void insertQqchOptimizeProcedurePlanList(List<QqchOptimizeProcedurePlan> qqchOptimizeProcedurePlanList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchOptimizeProcedurePlanList)){
            return;
        }
        for (QqchOptimizeProcedurePlan qqchOptimizeProcedurePlan : qqchOptimizeProcedurePlanList) {
            qqchOptimizeProcedurePlan.setId(IdWorker.createId());
            qqchOptimizeProcedurePlan.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchOptimizeProcedurePlan.setValid(Valid.YES);
            }
            qqchOptimizeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchOptimizeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
            qqchOptimizeProcedurePlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchOptimizeProcedurePlanMapper.insertQqchOptimizeProcedurePlanList(qqchOptimizeProcedurePlanList);
    }
}
