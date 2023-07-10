package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonScheme;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchComparisonSchemeMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 18:35:38
 * @remark 重大设计方案比选-方案
 */
@Service
public class QqchComparisonSchemeServiceImpl implements IQqchComparisonSchemeService {

    @Autowired
    private QqchComparisonSchemeMapper qqchComparisonSchemeMapper;


    public QqchComparisonScheme getQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme) {
        return qqchComparisonSchemeMapper.getQqchComparisonScheme(qqchComparisonScheme);
    }

    public List<QqchComparisonScheme> getQqchComparisonSchemeList(QqchComparisonScheme qqchComparisonScheme) {
        return qqchComparisonSchemeMapper.getQqchComparisonSchemeList(qqchComparisonScheme);
    }

    @Transactional
    public int insertQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme) {
        qqchComparisonScheme.setId(IdWorker.createId());
        qqchComparisonScheme.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
        qqchComparisonScheme.setCreateUserName(SecurityUtils.getUserName());
        qqchComparisonScheme.setCreateTime(DateUtils.getNowDate());
        return qqchComparisonSchemeMapper.insertQqchComparisonScheme(qqchComparisonScheme);
    }

    @Transactional
    public int insertQqchComparisonSchemeList(List<QqchComparisonScheme> qqchComparisonSchemeList) {
        for (QqchComparisonScheme qqchComparisonScheme : qqchComparisonSchemeList) {
            qqchComparisonScheme.setId(IdWorker.createId());
            qqchComparisonScheme.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchComparisonScheme.setCreateUserName(SecurityUtils.getUserName());
            qqchComparisonScheme.setCreateTime(DateUtils.getNowDate());
        }
        return qqchComparisonSchemeMapper.insertQqchComparisonSchemeList(qqchComparisonSchemeList);
    }

    @Transactional
    public int updateQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme) {
        qqchComparisonScheme.setUpdateUser(SecurityUtils.getUserName());
        qqchComparisonScheme.setUpdateTime(DateUtils.getNowDate());
        return qqchComparisonSchemeMapper.updateQqchComparisonScheme(qqchComparisonScheme);
    }

    @Transactional
    public int updateQqchComparisonSchemeList(List<QqchComparisonScheme> qqchComparisonSchemeList) {
        for (QqchComparisonScheme qqchComparisonScheme : qqchComparisonSchemeList) {
            qqchComparisonScheme.setUpdateUser(SecurityUtils.getUserName());
            qqchComparisonScheme.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchComparisonSchemeMapper.updateQqchComparisonSchemeList(qqchComparisonSchemeList);
    }

    @Transactional
    public int deleteQqchComparisonScheme(QqchComparisonScheme qqchComparisonScheme) {
        qqchComparisonScheme.setUpdateUser(SecurityUtils.getUserName());
        qqchComparisonScheme.setUpdateTime(DateUtils.getNowDate());
        return qqchComparisonSchemeMapper.deleteQqchComparisonScheme(qqchComparisonScheme);
    }

    @Transactional
    public int deleteQqchComparisonSchemeByPks(List<Long> qqchComparisonSchemePkList) {
        return qqchComparisonSchemeMapper.deleteQqchComparisonSchemeByPks(qqchComparisonSchemePkList);
    }
}
