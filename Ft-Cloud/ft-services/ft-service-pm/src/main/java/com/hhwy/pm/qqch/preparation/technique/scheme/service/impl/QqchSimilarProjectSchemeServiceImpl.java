package com.hhwy.pm.qqch.preparation.technique.scheme.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchSimilarProjectScheme;
import com.hhwy.pm.qqch.preparation.technique.scheme.mapper.QqchSimilarProjectSchemeMapper;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchSimilarProjectSchemeService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2024-01-05 11:49:06
 * @remark
 */
@Service
public class QqchSimilarProjectSchemeServiceImpl implements IQqchSimilarProjectSchemeService {

    @Autowired
    private QqchSimilarProjectSchemeMapper qqchSimilarProjectSchemeMapper;


    public QqchSimilarProjectScheme getQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme) {
        return qqchSimilarProjectSchemeMapper.getQqchSimilarProjectScheme(qqchSimilarProjectScheme);
    }

    public List<QqchSimilarProjectScheme> getQqchSimilarProjectSchemeList(QqchSimilarProjectScheme qqchSimilarProjectScheme) {
        return qqchSimilarProjectSchemeMapper.getQqchSimilarProjectSchemeList(qqchSimilarProjectScheme);
    }

    @Transactional
    public int insertQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme) {
        qqchSimilarProjectScheme.setId(IdWorker.createId());
        qqchSimilarProjectScheme.setCreateUser(SecurityUtils.getUserName());
        qqchSimilarProjectScheme.setCreateTime(DateUtils.getNowDate());
        return qqchSimilarProjectSchemeMapper.insertQqchSimilarProjectScheme(qqchSimilarProjectScheme);
    }

    @Transactional
    public int insertQqchSimilarProjectSchemeList(List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeList) {
        for (QqchSimilarProjectScheme qqchSimilarProjectScheme : qqchSimilarProjectSchemeList) {
            qqchSimilarProjectScheme.setId(IdWorker.createId());
            qqchSimilarProjectScheme.setCreateUser(SecurityUtils.getUserName());
            qqchSimilarProjectScheme.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSimilarProjectSchemeMapper.insertQqchSimilarProjectSchemeList(qqchSimilarProjectSchemeList);
    }

    @Transactional
    public int updateQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme) {
        qqchSimilarProjectScheme.setUpdateUser(SecurityUtils.getUserName());
        qqchSimilarProjectScheme.setUpdateTime(DateUtils.getNowDate());
        return qqchSimilarProjectSchemeMapper.updateQqchSimilarProjectScheme(qqchSimilarProjectScheme);
    }

    @Transactional
    public int updateQqchSimilarProjectSchemeList(List<QqchSimilarProjectScheme> qqchSimilarProjectSchemeList) {
        for (QqchSimilarProjectScheme qqchSimilarProjectScheme : qqchSimilarProjectSchemeList) {
            qqchSimilarProjectScheme.setUpdateUser(SecurityUtils.getUserName());
            qqchSimilarProjectScheme.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSimilarProjectSchemeMapper.updateQqchSimilarProjectSchemeList(qqchSimilarProjectSchemeList);
    }

    @Transactional
    public int deleteQqchSimilarProjectScheme(QqchSimilarProjectScheme qqchSimilarProjectScheme) {
        qqchSimilarProjectScheme.setUpdateUser(SecurityUtils.getUserName());
        qqchSimilarProjectScheme.setUpdateTime(DateUtils.getNowDate());
        return qqchSimilarProjectSchemeMapper.deleteQqchSimilarProjectScheme(qqchSimilarProjectScheme);
    }

    @Transactional
    public int deleteQqchSimilarProjectSchemeByPks(List<Long> qqchSimilarProjectSchemePkList) {
        return qqchSimilarProjectSchemeMapper.deleteQqchSimilarProjectSchemeByPks(qqchSimilarProjectSchemePkList);
    }
}
