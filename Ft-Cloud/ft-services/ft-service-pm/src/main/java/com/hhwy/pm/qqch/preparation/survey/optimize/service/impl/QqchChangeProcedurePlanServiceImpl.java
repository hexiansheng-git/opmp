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
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchChangeProcedurePlan;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchChangeProcedurePlanVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchChangeProcedurePlanMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchChangeProcedurePlanService;
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
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
@Service
public class QqchChangeProcedurePlanServiceImpl implements IQqchChangeProcedurePlanService {

    @Autowired
    private QqchChangeProcedurePlanMapper qqchChangeProcedurePlanMapper;

    @Autowired
    private QqchPreparationSurveyExtendServiceImpl qqchPreparationSurveyExtendService;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private IQqchReviewService qqchReviewService;


    /**
     * 获取变更程序策划
     * @return
     * @param version
     */
    public QqchChangeProcedurePlanVo getQqchChangeProcedurePlanVo(BigDecimal version) {
        QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo = new QqchChangeProcedurePlanVo();

        version = VersionUtil.getVersion("qqch_change_procedure_plan",version);
        List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList = qqchChangeProcedurePlanMapper.getQqchChangeProcedurePlanList(version);
        if(CollectionUtils.isEmpty(qqchChangeProcedurePlanList)){
            qqchChangeProcedurePlanList = this.getInitializeData();
        }

        //获取附件组id（页面标识和版本号控制）
        QqchPreparationSurveyExtend qqchPreparationSurveyExtend = qqchPreparationSurveyExtendService.getQqchPreparationSurveyExtend(ModuleIdentity.CHANGE_PROCEDURE_PLAN, version);
        if(qqchPreparationSurveyExtend != null){
            qqchChangeProcedurePlanVo.setFileGroupId(qqchPreparationSurveyExtend.getFileGroupId());
        }

        qqchChangeProcedurePlanVo.setVersion(version);
        qqchChangeProcedurePlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchChangeProcedurePlanVo.setQqchChangeProcedurePlanList(qqchChangeProcedurePlanList);
        return qqchChangeProcedurePlanVo;
    }

    /**
     * 获取初始化数据
     * @return
     */
    public List<QqchChangeProcedurePlan> getInitializeData(){
        List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList = new ArrayList<>();
        AjaxResult result = systemServiceApi.dictType(DictType.CHANGE_PROCEDURE_PLAN_INITIALIZE);
        List<Map<String,Object>> dictDataList = (List<Map<String, Object>>) result.get("data");
        for (Map<String, Object> map : dictDataList) {
            String dictValue = (String) map.get("dictValue");
            String dictLabel = (String) map.get("dictLabel");
            QqchChangeProcedurePlan qqchChangeProcedurePlan = new QqchChangeProcedurePlan();
            qqchChangeProcedurePlan.setLinkName(dictLabel);
            qqchChangeProcedurePlan.setWorkContent(dictValue);
            qqchChangeProcedurePlanList.add(qqchChangeProcedurePlan);
        }
        return qqchChangeProcedurePlanList;
    }

    /**
     * 保存
     * @param qqchChangeProcedurePlanVo
     */
    @Override
    @Transactional
    public void save(QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo) {
        //删除旧数据
        QqchChangeProcedurePlan qqchChangeProcedurePlan = new QqchChangeProcedurePlan();
        qqchChangeProcedurePlan.setVersion(qqchChangeProcedurePlanVo.getVersion());
        qqchChangeProcedurePlanMapper.deleteQqchChangeProcedurePlan(qqchChangeProcedurePlan);

        //维护附件
        String fileGroupId = qqchChangeProcedurePlanVo.getFileGroupId();
        QqchPreparationSurveyExtend qqchPreparationSurveyExtend = qqchPreparationSurveyExtendService.getQqchPreparationSurveyExtend(ModuleIdentity.OPTIMIZE_PROCEDURE_PLAN, qqchChangeProcedurePlanVo.getVersion());
        if(qqchPreparationSurveyExtend == null){
            qqchPreparationSurveyExtend = new QqchPreparationSurveyExtend();
            qqchPreparationSurveyExtend.setVersion(qqchChangeProcedurePlanVo.getVersion());
            qqchPreparationSurveyExtend.setValid(Valid.YES);
            qqchChangeProcedurePlanVo.setFileGroupId(fileGroupId);
            qqchPreparationSurveyExtendService.insertQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
        }else {
            qqchPreparationSurveyExtendService.updateQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
        }

        //插入新数据
        List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList = qqchChangeProcedurePlanVo.getQqchChangeProcedurePlanList();
        this.insertQqchChangeProcedurePlanList(qqchChangeProcedurePlanList, qqchChangeProcedurePlanVo.getVersion());
    }

    /**
     * 确认
     * @param qqchChangeProcedurePlanVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo) {
        this.save(qqchChangeProcedurePlanVo);

        String buttonMark = qqchChangeProcedurePlanVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchChangeProcedurePlanVo.getMenuId();
            String stageIdentity = qqchChangeProcedurePlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 批量插入
     * @param qqchChangeProcedurePlanList
     * @param version
     */
    @Transactional
    public void insertQqchChangeProcedurePlanList(List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList, BigDecimal version){
        for (QqchChangeProcedurePlan qqchChangeProcedurePlan : qqchChangeProcedurePlanList) {
            qqchChangeProcedurePlan.setId(IdWorker.createId());
            qqchChangeProcedurePlan.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchChangeProcedurePlan.setValid(Valid.YES);
            }
            qqchChangeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchChangeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
            qqchChangeProcedurePlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchChangeProcedurePlanMapper.insertQqchChangeProcedurePlanList(qqchChangeProcedurePlanList);
    }
}
