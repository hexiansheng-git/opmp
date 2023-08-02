package com.hhwy.pm.qqch.preparation.costControl.masterContract.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchGeneralCondition;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.mapper.QqchGeneralConditionMapper;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.service.IQqchGeneralConditionService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:46
 * @remark 通用条件梳理
 */
@Service
public class QqchGeneralConditionServiceImpl implements IQqchGeneralConditionService {

    @Autowired
    private QqchGeneralConditionMapper qqchGeneralConditionMapper;


    public QqchGeneralCondition getQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition) {
        return qqchGeneralConditionMapper.getQqchGeneralCondition(qqchGeneralCondition);
    }

    public List<QqchGeneralCondition> getQqchGeneralConditionList(QqchGeneralCondition qqchGeneralCondition) {
        return qqchGeneralConditionMapper.getQqchGeneralConditionList(qqchGeneralCondition);
    }

    @Transactional
    public int insertQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition) {
        qqchGeneralCondition.setId(IdWorker.createId());
        qqchGeneralCondition.setCreateUser(SecurityUtils.getUserName());
        qqchGeneralCondition.setCreateTime(DateUtils.getNowDate());
        return qqchGeneralConditionMapper.insertQqchGeneralCondition(qqchGeneralCondition);
    }

    @Transactional
    public int insertQqchGeneralConditionList(List<QqchGeneralCondition> qqchGeneralConditionList) {
        for (QqchGeneralCondition qqchGeneralCondition : qqchGeneralConditionList) {
            qqchGeneralCondition.setId(IdWorker.createId());
            qqchGeneralCondition.setCreateUser(SecurityUtils.getUserName());
            qqchGeneralCondition.setCreateTime(DateUtils.getNowDate());
        }
        return qqchGeneralConditionMapper.insertQqchGeneralConditionList(qqchGeneralConditionList);
    }

    @Transactional
    public int updateQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition) {
        qqchGeneralCondition.setUpdateUser(SecurityUtils.getUserName());
        qqchGeneralCondition.setUpdateTime(DateUtils.getNowDate());
        return qqchGeneralConditionMapper.updateQqchGeneralCondition(qqchGeneralCondition);
    }

    @Transactional
    public int updateQqchGeneralConditionList(List<QqchGeneralCondition> qqchGeneralConditionList) {
        for (QqchGeneralCondition qqchGeneralCondition : qqchGeneralConditionList) {
            qqchGeneralCondition.setUpdateUser(SecurityUtils.getUserName());
            qqchGeneralCondition.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchGeneralConditionMapper.updateQqchGeneralConditionList(qqchGeneralConditionList);
    }

    @Transactional
    public int deleteQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition) {
        qqchGeneralCondition.setUpdateUser(SecurityUtils.getUserName());
        qqchGeneralCondition.setUpdateTime(DateUtils.getNowDate());
        return qqchGeneralConditionMapper.deleteQqchGeneralCondition(qqchGeneralCondition);
    }

    @Transactional
    public int deleteQqchGeneralConditionByPks(List<Long> qqchGeneralConditionPkList) {
        return qqchGeneralConditionMapper.deleteQqchGeneralConditionByPks(qqchGeneralConditionPkList);
    }
}
