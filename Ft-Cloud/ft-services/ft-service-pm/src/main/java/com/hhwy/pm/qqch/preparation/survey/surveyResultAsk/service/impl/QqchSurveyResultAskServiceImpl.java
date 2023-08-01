package com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.domain.QqchSurveyResultAsk;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.domain.QqchSurveyResultAskVo;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.mapper.QqchSurveyResultAskMapper;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.service.IQqchSurveyResultAskService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:45:52
 * @remark  2.3.2 勘察成果验收内容形式审查要求
 */
@Service
public class QqchSurveyResultAskServiceImpl implements IQqchSurveyResultAskService {

    @Autowired
    private QqchSurveyResultAskMapper qqchSurveyResultAskMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;

    /**
     *  列表查询
     *
     * @param qqchSurveyResultAsk
     * @return
     */
    public QqchSurveyResultAskVo getQqchSurveyResultAskList(QqchSurveyResultAsk qqchSurveyResultAsk) {
        BigDecimal version=new BigDecimal(1);
        if (qqchSurveyResultAsk.getVersion() == null) {
            // 获取最大版本号
            version = commonMapper.selectMaxVersion("qqch_survey_result_ask");
        }
        qqchSurveyResultAsk.setVersion(version);
        List<QqchSurveyResultAsk> qqchSurveyResultAskList = qqchSurveyResultAskMapper.getQqchSurveyResultAskList(qqchSurveyResultAsk);
        QqchSurveyResultAskVo vo = new QqchSurveyResultAskVo();
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSurveyResultAskList(qqchSurveyResultAskList);
        return vo;
    }

    /*
     * 新增
     */
    @Override
    public void save(QqchSurveyResultAskVo qqchSurveyResultAskVo) {
        //删除旧数据
        QqchSurveyResultAsk qqchSurveyResultAsk = new QqchSurveyResultAsk();
        qqchSurveyResultAsk.setVersion(qqchSurveyResultAskVo.getVersion());
        qqchSurveyResultAskMapper.deleteQqchSurveyResultAsk(qqchSurveyResultAsk);
        //插入新数据
        this.insertQqchSurveyResultAskList(qqchSurveyResultAskVo.getQqchSurveyResultAskList(), qqchSurveyResultAskVo.getVersion());
    }




    /**
     *  确认
     * @param qqchSurveyResultAskVo
     * @return
     */
    @Override
    public void confirm(QqchSurveyResultAskVo qqchSurveyResultAskVo) {
        this.save(qqchSurveyResultAskVo);
        String buttonMark = qqchSurveyResultAskVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchSurveyResultAskVo.getMenuId();
            String stageIdentity = qqchSurveyResultAskVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }



    private int insertQqchSurveyResultAskList(List<QqchSurveyResultAsk> qqchSurveyResultAskList, BigDecimal version) {
        for (QqchSurveyResultAsk qqchSurveyResultAsk : qqchSurveyResultAskList) {
            qqchSurveyResultAsk.setId(IdWorker.createId());
            qqchSurveyResultAsk.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchSurveyResultAsk.setValid(Valid.YES);
            }
            qqchSurveyResultAsk.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchSurveyResultAsk.setCreateUserName(SecurityUtils.getUserName());
            qqchSurveyResultAsk.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSurveyResultAskMapper.insertQqchSurveyResultAskList(qqchSurveyResultAskList);
    }



    /**
     *  批量新增、修改
     * @param qqchSurveyResultAskList
     * @return
     */
    @Transactional
    public int batchAdd(List<QqchSurveyResultAsk> qqchSurveyResultAskList) {
        List<QqchSurveyResultAsk> insertList = new ArrayList<>();
        List<QqchSurveyResultAsk> updateList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(qqchSurveyResultAskList)){
            for (QqchSurveyResultAsk qqchSurveyResultAsk : qqchSurveyResultAskList) {
                if(qqchSurveyResultAsk.getId()==null){
                    qqchSurveyResultAsk.setId(IdWorker.createId());
                    EntityUtils.setCreateUpdateInfo(qqchSurveyResultAsk);
                    insertList.add(qqchSurveyResultAsk);
                }else {
                    EntityUtils.setUpdateInfo(qqchSurveyResultAsk);
                    updateList.add(qqchSurveyResultAsk);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(insertList)) {
            qqchSurveyResultAskMapper.insertQqchSurveyResultAskList(insertList);
        }
        if(CollectionUtils.isNotEmpty(updateList)){
            qqchSurveyResultAskMapper.updateQqchSurveyResultAskList(updateList);
        }
        return 1;
    }


}
