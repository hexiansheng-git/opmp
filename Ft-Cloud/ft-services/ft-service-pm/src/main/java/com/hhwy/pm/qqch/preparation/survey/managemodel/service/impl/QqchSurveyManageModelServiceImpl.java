package com.hhwy.pm.qqch.preparation.survey.managemodel.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.MasterEntity;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.MasterEntityVo;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.SonEntity;
import com.hhwy.pm.qqch.preparation.survey.managemodel.mapper.QqchSurveyManageModelMapper;
import com.hhwy.pm.qqch.preparation.survey.managemodel.service.IQqchSurveyManageModelService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-18 14:57:01
 * @remark  总体勘察设计经营模式确定
 */
@Service
public class QqchSurveyManageModelServiceImpl implements IQqchSurveyManageModelService {

    @Autowired
    private QqchSurveyManageModelMapper qqchSurveyManageModelMapper;
    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;


    /**
     *  总体勘察设计经营模式确定 列表查询
     * @param qqchSurveyManageModel
     * @return
     */
    public MasterEntity getQqchSurveyManageModelList(QqchSurveyManageModel qqchSurveyManageModel) {
        BigDecimal version = VersionUtil.getVersion("qqch_survey_manage_model",qqchSurveyManageModel.getVersion());
        qqchSurveyManageModel.setVersion(version);
        List<QqchSurveyManageModel> qqchSurveyManageModelList = qqchSurveyManageModelMapper.getQqchSurveyManageModelList(qqchSurveyManageModel);
        MasterEntity masterEntity = new MasterEntity();
        if(CollectionUtils.isNotEmpty(qqchSurveyManageModelList)){
            masterEntity.setResults(qqchSurveyManageModelList.get(0).getResults());
            List<SonEntity> list = new ArrayList<>();
            for (QqchSurveyManageModel surveyManageModel : qqchSurveyManageModelList) {
                SonEntity sonEntity = new SonEntity();
                sonEntity.setId(surveyManageModel.getId());
                sonEntity.setManageModel(surveyManageModel.getManageModel());
                sonEntity.setAdvantage(surveyManageModel.getAdvantage());
                sonEntity.setDisadvantage(surveyManageModel.getDisadvantage());
                sonEntity.setRemark(surveyManageModel.getRemark());
                list.add(sonEntity);
            }
            masterEntity.setSonEntityList(list);
        }
        return masterEntity;
    }


    /**
     *  确认状态
     *
     * @param masterEntityVo
     */
    @Override
    public void confirm(MasterEntityVo masterEntityVo) {
        this.save(masterEntityVo);
        String buttonMark = masterEntityVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = masterEntityVo.getMenuId();
            String stageIdentity = masterEntityVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    @Override
    public int save(MasterEntityVo masterEntityVo) {
        //删除旧数据
        QqchSurveyManageModel qqchSurveyManageModel = new QqchSurveyManageModel();
        qqchSurveyManageModel.setVersion(masterEntityVo.getVersion());
        qqchSurveyManageModelMapper.deleteQqchSurveyManageModel(qqchSurveyManageModel);
        //插入新数据
        return   this.insertQqchCompleteDesignHandoverList(masterEntityVo.getMasterEntityList(), masterEntityVo.getVersion());
    }


    private int insertQqchCompleteDesignHandoverList(List<MasterEntity> masterEntityList, BigDecimal version) {
        List<QqchSurveyManageModel> list = new ArrayList<>();
        for (MasterEntity masterEntity : masterEntityList) {
            List<SonEntity> sonEntityList = masterEntity.getSonEntityList();
            for (SonEntity sonEntity : sonEntityList) {
                QqchSurveyManageModel qqchSurveyManageModel = new QqchSurveyManageModel();
                qqchSurveyManageModel.setId(IdWorker.createId());
                qqchSurveyManageModel.setVersion(version);
                if(version.compareTo(BigDecimal.valueOf(1)) == 0){
                    qqchSurveyManageModel.setValid(Valid.YES);
                }
                qqchSurveyManageModel.setManageModel(sonEntity.getManageModel());
                qqchSurveyManageModel.setAdvantage(sonEntity.getAdvantage());
                qqchSurveyManageModel.setDisadvantage(sonEntity.getDisadvantage());
                qqchSurveyManageModel.setRemark(sonEntity.getRemark());
                qqchSurveyManageModel.setId(IdWorker.createId());
                qqchSurveyManageModel.setCreateUser(SecurityUtils.getUserName());
                qqchSurveyManageModel.setCreateTime(DateUtils.getNowDate());
                qqchSurveyManageModel.setResults(masterEntity.getResults());
                list.add(qqchSurveyManageModel);
            }
        }
        return qqchSurveyManageModelMapper.insertQqchSurveyManageModelList(list);
    }

}
