package com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.domain.QqchTechRiskSolutions;
import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.mapper.QqchTechRiskSolutionsMapper;
import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.service.IQqchTechRiskSolutionsService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:55:16
 * @remark
 */
@Service
public class QqchTechRiskSolutionsServiceImpl implements IQqchTechRiskSolutionsService {

    @Autowired
    private QqchTechRiskSolutionsMapper qqchTechRiskSolutionsMapper;


    public QqchTechRiskSolutions getQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions) {
        return qqchTechRiskSolutionsMapper.getQqchTechRiskSolutions(qqchTechRiskSolutions);
    }

    public List<QqchTechRiskSolutions> getQqchTechRiskSolutionsList(QqchTechRiskSolutions qqchTechRiskSolutions) {
        return qqchTechRiskSolutionsMapper.getQqchTechRiskSolutionsList(qqchTechRiskSolutions);
    }

    @Transactional
    public int insertQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions) {
        qqchTechRiskSolutions.setId(IdWorker.createId());
        qqchTechRiskSolutions.setCreateUser(SecurityUtils.getUserName());
        qqchTechRiskSolutions.setCreateTime(DateUtils.getNowDate());
        return qqchTechRiskSolutionsMapper.insertQqchTechRiskSolutions(qqchTechRiskSolutions);
    }

    @Transactional
    public int insertQqchTechRiskSolutionsList(List<QqchTechRiskSolutions> qqchTechRiskSolutionsList) {
        for (QqchTechRiskSolutions qqchTechRiskSolutions : qqchTechRiskSolutionsList) {
            qqchTechRiskSolutions.setId(IdWorker.createId());
            qqchTechRiskSolutions.setCreateUser(SecurityUtils.getUserName());
            qqchTechRiskSolutions.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTechRiskSolutionsMapper.insertQqchTechRiskSolutionsList(qqchTechRiskSolutionsList);
    }

    @Transactional
    public int updateQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions) {
        qqchTechRiskSolutions.setUpdateUser(SecurityUtils.getUserName());
        qqchTechRiskSolutions.setUpdateTime(DateUtils.getNowDate());
        return qqchTechRiskSolutionsMapper.updateQqchTechRiskSolutions(qqchTechRiskSolutions);
    }

    @Transactional
    public int updateQqchTechRiskSolutionsList(List<QqchTechRiskSolutions> qqchTechRiskSolutionsList) {
        for (QqchTechRiskSolutions qqchTechRiskSolutions : qqchTechRiskSolutionsList) {
            qqchTechRiskSolutions.setUpdateUser(SecurityUtils.getUserName());
            qqchTechRiskSolutions.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTechRiskSolutionsMapper.updateQqchTechRiskSolutionsList(qqchTechRiskSolutionsList);
    }

    @Transactional
    public int deleteQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions) {
        qqchTechRiskSolutions.setUpdateUser(SecurityUtils.getUserName());
        qqchTechRiskSolutions.setUpdateTime(DateUtils.getNowDate());
        return qqchTechRiskSolutionsMapper.deleteQqchTechRiskSolutions(qqchTechRiskSolutions);
    }

    @Transactional
    public int deleteQqchTechRiskSolutionsByPks(List<Long> qqchTechRiskSolutionsPkList) {
        return qqchTechRiskSolutionsMapper.deleteQqchTechRiskSolutionsByPks(qqchTechRiskSolutionsPkList);
    }
}
