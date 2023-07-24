package com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.domain.QqchSurveyResultAsk;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.mapper.QqchSurveyResultAskMapper;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.service.IQqchSurveyResultAskService;
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


    public QqchSurveyResultAsk getQqchSurveyResultAsk(QqchSurveyResultAsk qqchSurveyResultAsk) {
        return qqchSurveyResultAskMapper.getQqchSurveyResultAsk(qqchSurveyResultAsk);
    }

    /**
     *  列表查询
     *
     * @param qqchSurveyResultAsk
     * @return
     */
    public List<QqchSurveyResultAsk> getQqchSurveyResultAskList(QqchSurveyResultAsk qqchSurveyResultAsk) {
        BigDecimal version=new BigDecimal(1);
        if (qqchSurveyResultAsk.getVersion() == null) {
            // 获取最大版本号
            version = commonMapper.selectMaxVersion("qqch_survey_result_ask");
        }
        qqchSurveyResultAsk.setVersion(version);
        return qqchSurveyResultAskMapper.getQqchSurveyResultAskList(qqchSurveyResultAsk);
    }

    @Transactional
    public int insertQqchSurveyResultAsk(QqchSurveyResultAsk qqchSurveyResultAsk) {
        qqchSurveyResultAsk.setId(IdWorker.createId());
        qqchSurveyResultAsk.setCreateUser(SecurityUtils.getUserName());
        qqchSurveyResultAsk.setCreateTime(DateUtils.getNowDate());
        return qqchSurveyResultAskMapper.insertQqchSurveyResultAsk(qqchSurveyResultAsk);
    }

    /**
     *  批量新增、修改
     * @param qqchSurveyResultAskList
     * @return
     */
    @Transactional
    public int insertQqchSurveyResultAskList(List<QqchSurveyResultAsk> qqchSurveyResultAskList) {
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
         qqchSurveyResultAskMapper.insertQqchSurveyResultAskList(qqchSurveyResultAskList);
         qqchSurveyResultAskMapper.updateQqchSurveyResultAskList(qqchSurveyResultAskList);
        return 1;
    }

    @Transactional
    public int updateQqchSurveyResultAsk(QqchSurveyResultAsk qqchSurveyResultAsk) {
        qqchSurveyResultAsk.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyResultAsk.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyResultAskMapper.updateQqchSurveyResultAsk(qqchSurveyResultAsk);
    }

    @Transactional
    public int updateQqchSurveyResultAskList(List<QqchSurveyResultAsk> qqchSurveyResultAskList) {
        for (QqchSurveyResultAsk qqchSurveyResultAsk : qqchSurveyResultAskList) {
            qqchSurveyResultAsk.setUpdateUser(SecurityUtils.getUserName());
            qqchSurveyResultAsk.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSurveyResultAskMapper.updateQqchSurveyResultAskList(qqchSurveyResultAskList);
    }

    @Transactional
    public int deleteQqchSurveyResultAsk(QqchSurveyResultAsk qqchSurveyResultAsk) {
        qqchSurveyResultAsk.setUpdateUser(SecurityUtils.getUserName());
        qqchSurveyResultAsk.setUpdateTime(DateUtils.getNowDate());
        return qqchSurveyResultAskMapper.deleteQqchSurveyResultAsk(qqchSurveyResultAsk);
    }

    @Transactional
    public int deleteQqchSurveyResultAskByPks(List<Long> qqchSurveyResultAskPkList) {
        return qqchSurveyResultAskMapper.deleteQqchSurveyResultAskByPks(qqchSurveyResultAskPkList);
    }
}
