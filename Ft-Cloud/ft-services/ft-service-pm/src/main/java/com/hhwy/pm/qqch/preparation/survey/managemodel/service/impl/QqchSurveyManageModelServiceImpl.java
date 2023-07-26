package com.hhwy.pm.qqch.preparation.survey.managemodel.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModelVo;
import com.hhwy.pm.qqch.preparation.survey.managemodel.mapper.QqchSurveyManageModelMapper;
import com.hhwy.pm.qqch.preparation.survey.managemodel.service.IQqchSurveyManageModelService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
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
    public QqchSurveyManageModelVo getQqchSurveyManageModelList(QqchSurveyManageModel qqchSurveyManageModel) {
        QqchSurveyManageModelVo vo = new QqchSurveyManageModelVo();
        BigDecimal version = VersionUtil.getVersion("qqch_survey_manage_model",qqchSurveyManageModel.getVersion());
        qqchSurveyManageModel.setVersion(version);
        vo.setVersion(version);
        List<QqchSurveyManageModel> qqchSurveyManageModelList = qqchSurveyManageModelMapper.getQqchSurveyManageModelList(qqchSurveyManageModel);
        vo.setQqchSurveyManageModelList(qqchSurveyManageModelList);
        return vo;
    }


    /**
     *  确认状态
     *
     * @param qqchSurveyManageModelVo
     */
    @Override
    public void confirm(QqchSurveyManageModelVo qqchSurveyManageModelVo) {
        this.save(qqchSurveyManageModelVo);
        String buttonMark = qqchSurveyManageModelVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchSurveyManageModelVo.getMenuId();
            String stageIdentity = qqchSurveyManageModelVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    @Override
    public int save(QqchSurveyManageModelVo qqchSurveyManageModelVo) {
        //删除旧数据
        QqchSurveyManageModel qqchSurveyManageModel = new QqchSurveyManageModel();
        qqchSurveyManageModel.setVersion(qqchSurveyManageModelVo.getVersion());
        qqchSurveyManageModelMapper.deleteQqchSurveyManageModel(qqchSurveyManageModel);
        //插入新数据
        return  this.insertQqchSurveyManageModelList(qqchSurveyManageModelVo.getQqchSurveyManageModelList(), qqchSurveyManageModelVo.getVersion());
    }


    private int insertQqchSurveyManageModelList(List<QqchSurveyManageModel> qqchSurveyManageModelList, BigDecimal version) {
        for (QqchSurveyManageModel qqchSurveyManageModel : qqchSurveyManageModelList) {
            qqchSurveyManageModel.setId(IdWorker.createId());
            qqchSurveyManageModel.setVersion(version);
            if(version.compareTo(BigDecimal.valueOf(1)) == 0){
                qqchSurveyManageModel.setValid(Valid.YES);
            }
            qqchSurveyManageModel.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSurveyManageModel.setCreateUserName(SecurityUtils.getUserName());
            qqchSurveyManageModel.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSurveyManageModelMapper.insertQqchSurveyManageModelList(qqchSurveyManageModelList);
    }

}
