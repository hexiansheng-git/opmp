package com.hhwy.pm.qqch.preparation.costControl.masterContract.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchSpecialCondition;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.mapper.QqchSpecialConditionMapper;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.service.IQqchSpecialConditionService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:48
 * @remark 专用条件梳理
 */
@Service
public class QqchSpecialConditionServiceImpl implements IQqchSpecialConditionService {

    @Autowired
    private QqchSpecialConditionMapper qqchSpecialConditionMapper;


    public QqchSpecialCondition getQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition) {
        return qqchSpecialConditionMapper.getQqchSpecialCondition(qqchSpecialCondition);
    }

    public List<QqchSpecialCondition> getQqchSpecialConditionList(QqchSpecialCondition qqchSpecialCondition) {
        return qqchSpecialConditionMapper.getQqchSpecialConditionList(qqchSpecialCondition);
    }

    @Transactional
    public int insertQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition) {
        qqchSpecialCondition.setId(IdWorker.createId());
        qqchSpecialCondition.setCreateUser(SecurityUtils.getUserName());
        qqchSpecialCondition.setCreateTime(DateUtils.getNowDate());
        return qqchSpecialConditionMapper.insertQqchSpecialCondition(qqchSpecialCondition);
    }

    @Transactional
    public int insertQqchSpecialConditionList(List<QqchSpecialCondition> qqchSpecialConditionList) {
        for (QqchSpecialCondition qqchSpecialCondition : qqchSpecialConditionList) {
            qqchSpecialCondition.setId(IdWorker.createId());
            qqchSpecialCondition.setCreateUser(SecurityUtils.getUserName());
            qqchSpecialCondition.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSpecialConditionMapper.insertQqchSpecialConditionList(qqchSpecialConditionList);
    }

    @Transactional
    public int updateQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition) {
        qqchSpecialCondition.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialCondition.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialConditionMapper.updateQqchSpecialCondition(qqchSpecialCondition);
    }

    @Transactional
    public int updateQqchSpecialConditionList(List<QqchSpecialCondition> qqchSpecialConditionList) {
        for (QqchSpecialCondition qqchSpecialCondition : qqchSpecialConditionList) {
            qqchSpecialCondition.setUpdateUser(SecurityUtils.getUserName());
            qqchSpecialCondition.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSpecialConditionMapper.updateQqchSpecialConditionList(qqchSpecialConditionList);
    }

    @Transactional
    public int deleteQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition) {
        qqchSpecialCondition.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialCondition.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialConditionMapper.deleteQqchSpecialCondition(qqchSpecialCondition);
    }

    @Transactional
    public int deleteQqchSpecialConditionByPks(List<Long> qqchSpecialConditionPkList) {
        return qqchSpecialConditionMapper.deleteQqchSpecialConditionByPks(qqchSpecialConditionPkList);
    }
}
