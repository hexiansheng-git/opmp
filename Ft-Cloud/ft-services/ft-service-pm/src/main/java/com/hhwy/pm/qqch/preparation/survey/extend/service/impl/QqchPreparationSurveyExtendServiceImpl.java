package com.hhwy.pm.qqch.preparation.survey.extend.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.extend.domain.QqchPreparationSurveyExtend;
import com.hhwy.pm.qqch.preparation.survey.extend.mapper.QqchPreparationSurveyExtendMapper;
import com.hhwy.pm.qqch.preparation.survey.extend.service.IQqchPreparationSurveyExtendService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 18:36:03
 * @remark 前期策划-前期策划编制-勘察设计策划-扩展
 */
@Service
public class QqchPreparationSurveyExtendServiceImpl implements IQqchPreparationSurveyExtendService {

    @Autowired
    private QqchPreparationSurveyExtendMapper qqchPreparationSurveyExtendMapper;


    public QqchPreparationSurveyExtend getQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend) {
        return qqchPreparationSurveyExtendMapper.getQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
    }

    public List<QqchPreparationSurveyExtend> getQqchPreparationSurveyExtendList(QqchPreparationSurveyExtend qqchPreparationSurveyExtend) {
        return qqchPreparationSurveyExtendMapper.getQqchPreparationSurveyExtendList(qqchPreparationSurveyExtend);
    }

    @Transactional
    public int insertQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend) {
        qqchPreparationSurveyExtend.setId(IdWorker.createId());
        qqchPreparationSurveyExtend.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
        qqchPreparationSurveyExtend.setCreateUserName(SecurityUtils.getUserName());
        qqchPreparationSurveyExtend.setCreateTime(DateUtils.getNowDate());
        return qqchPreparationSurveyExtendMapper.insertQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
    }

    @Transactional
    public int insertQqchPreparationSurveyExtendList(List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendList) {
        for (QqchPreparationSurveyExtend qqchPreparationSurveyExtend : qqchPreparationSurveyExtendList) {
            qqchPreparationSurveyExtend.setId(IdWorker.createId());
            qqchPreparationSurveyExtend.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchPreparationSurveyExtend.setCreateUserName(SecurityUtils.getUserName());
            qqchPreparationSurveyExtend.setCreateTime(DateUtils.getNowDate());
        }
        return qqchPreparationSurveyExtendMapper.insertQqchPreparationSurveyExtendList(qqchPreparationSurveyExtendList);
    }

    @Transactional
    public int updateQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend) {
        qqchPreparationSurveyExtend.setUpdateUser(SecurityUtils.getUserName());
        qqchPreparationSurveyExtend.setUpdateTime(DateUtils.getNowDate());
        return qqchPreparationSurveyExtendMapper.updateQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
    }

    @Transactional
    public int updateQqchPreparationSurveyExtendList(List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendList) {
        for (QqchPreparationSurveyExtend qqchPreparationSurveyExtend : qqchPreparationSurveyExtendList) {
            qqchPreparationSurveyExtend.setUpdateUser(SecurityUtils.getUserName());
            qqchPreparationSurveyExtend.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchPreparationSurveyExtendMapper.updateQqchPreparationSurveyExtendList(qqchPreparationSurveyExtendList);
    }

    @Transactional
    public int deleteQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend) {
        qqchPreparationSurveyExtend.setUpdateUser(SecurityUtils.getUserName());
        qqchPreparationSurveyExtend.setUpdateTime(DateUtils.getNowDate());
        return qqchPreparationSurveyExtendMapper.deleteQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
    }

    @Transactional
    public int deleteQqchPreparationSurveyExtendByPks(List<Long> qqchPreparationSurveyExtendPkList) {
        return qqchPreparationSurveyExtendMapper.deleteQqchPreparationSurveyExtendByPks(qqchPreparationSurveyExtendPkList);
    }
}
