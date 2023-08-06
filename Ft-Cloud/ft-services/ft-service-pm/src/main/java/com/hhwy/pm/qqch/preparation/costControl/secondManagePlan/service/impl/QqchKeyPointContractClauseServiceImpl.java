package com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.QqchKeyPointContractClause;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.mapper.QqchKeyPointContractClauseMapper;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.service.IQqchKeyPointContractClauseService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:47:13
 * @remark 二次经营要点识别关联合同条款
 */
@Service
public class QqchKeyPointContractClauseServiceImpl implements IQqchKeyPointContractClauseService {

    @Autowired
    private QqchKeyPointContractClauseMapper qqchKeyPointContractClauseMapper;


    public QqchKeyPointContractClause getQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause) {
        return qqchKeyPointContractClauseMapper.getQqchKeyPointContractClause(qqchKeyPointContractClause);
    }

    public List<QqchKeyPointContractClause> getQqchKeyPointContractClauseList(QqchKeyPointContractClause qqchKeyPointContractClause) {
        return qqchKeyPointContractClauseMapper.getQqchKeyPointContractClauseList(qqchKeyPointContractClause);
    }

    @Transactional
    public int insertQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause) {
        qqchKeyPointContractClause.setId(IdWorker.createId());
        qqchKeyPointContractClause.setCreateUser(SecurityUtils.getUserName());
        qqchKeyPointContractClause.setCreateTime(DateUtils.getNowDate());
        return qqchKeyPointContractClauseMapper.insertQqchKeyPointContractClause(qqchKeyPointContractClause);
    }

    @Transactional
    public int insertQqchKeyPointContractClauseList(List<QqchKeyPointContractClause> qqchKeyPointContractClauseList) {
        for (QqchKeyPointContractClause qqchKeyPointContractClause : qqchKeyPointContractClauseList) {
            qqchKeyPointContractClause.setId(IdWorker.createId());
            qqchKeyPointContractClause.setCreateUser(SecurityUtils.getUserName());
            qqchKeyPointContractClause.setCreateTime(DateUtils.getNowDate());
        }
        return qqchKeyPointContractClauseMapper.insertQqchKeyPointContractClauseList(qqchKeyPointContractClauseList);
    }

    @Transactional
    public int updateQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause) {
        qqchKeyPointContractClause.setUpdateUser(SecurityUtils.getUserName());
        qqchKeyPointContractClause.setUpdateTime(DateUtils.getNowDate());
        return qqchKeyPointContractClauseMapper.updateQqchKeyPointContractClause(qqchKeyPointContractClause);
    }

    @Transactional
    public int updateQqchKeyPointContractClauseList(List<QqchKeyPointContractClause> qqchKeyPointContractClauseList) {
        for (QqchKeyPointContractClause qqchKeyPointContractClause : qqchKeyPointContractClauseList) {
            qqchKeyPointContractClause.setUpdateUser(SecurityUtils.getUserName());
            qqchKeyPointContractClause.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchKeyPointContractClauseMapper.updateQqchKeyPointContractClauseList(qqchKeyPointContractClauseList);
    }

    @Transactional
    public int deleteQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause) {
        qqchKeyPointContractClause.setUpdateUser(SecurityUtils.getUserName());
        qqchKeyPointContractClause.setUpdateTime(DateUtils.getNowDate());
        return qqchKeyPointContractClauseMapper.deleteQqchKeyPointContractClause(qqchKeyPointContractClause);
    }

    @Transactional
    public int deleteQqchKeyPointContractClauseByPks(List<Long> qqchKeyPointContractClausePkList) {
        return qqchKeyPointContractClauseMapper.deleteQqchKeyPointContractClauseByPks(qqchKeyPointContractClausePkList);
    }
}
