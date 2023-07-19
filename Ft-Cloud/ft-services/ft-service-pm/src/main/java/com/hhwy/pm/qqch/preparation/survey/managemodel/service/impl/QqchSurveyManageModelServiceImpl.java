package com.hhwy.pm.qqch.preparation.survey.managemodel.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.SonEntity;
import com.hhwy.pm.qqch.preparation.survey.managemodel.service.IQqchSurveyManageModelService;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.MasterEntity;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;
import com.hhwy.pm.qqch.preparation.survey.managemodel.mapper.QqchSurveyManageModelMapper;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public QqchSurveyManageModel getQqchSurveyManageModel(QqchSurveyManageModel qqchSurveyManageModel) {
        return qqchSurveyManageModelMapper.getQqchSurveyManageModel(qqchSurveyManageModel);
    }

    /**
     *  总体勘察设计经营模式确定 列表查询
     * @param qqchSurveyManageModel
     * @return
     */
    public MasterEntity getQqchSurveyManageModelList(QqchSurveyManageModel qqchSurveyManageModel) {
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

    @Transactional
    public int insertQqchSurveyManageModel(MasterEntity masterEntity) {
        List<SonEntity> sonEntityList = masterEntity.getSonEntityList();
        ArrayList<QqchSurveyManageModel> list = new ArrayList<>();
        for (SonEntity sonEntity : sonEntityList) {
            QqchSurveyManageModel qqchSurveyManageModel = new QqchSurveyManageModel();
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
        return qqchSurveyManageModelMapper.insertQqchSurveyManageModelList(list);
    }

    @Transactional
    public int insertQqchSurveyManageModelList(List<QqchSurveyManageModel> qqchSurveyManageModelList) {
        for (QqchSurveyManageModel qqchSurveyManageModel : qqchSurveyManageModelList) {
            qqchSurveyManageModel.setId(IdWorker.createId());
            qqchSurveyManageModel.setCreateUser(SecurityUtils.getUserName());
            qqchSurveyManageModel.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSurveyManageModelMapper.insertQqchSurveyManageModelList(qqchSurveyManageModelList);
    }

    @Transactional
    public int updateQqchSurveyManageModel(QqchSurveyManageModel qqchSurveyManageModel) {
        qqchSurveyManageModel.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyManageModel.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyManageModelMapper.updateQqchSurveyManageModel(qqchSurveyManageModel);
    }


    @Transactional
    public int deleteQqchSurveyManageModel(QqchSurveyManageModel qqchSurveyManageModel) {
        qqchSurveyManageModel.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyManageModel.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyManageModelMapper.deleteQqchSurveyManageModel(qqchSurveyManageModel);
    }

    @Override
    public void confirm(MasterEntity masterEntity) {
        this.save(masterEntity);
        //TODO 修改确认状态
    }

    private void save(MasterEntity masterEntity) {
    }

}
