package com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.service;

import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.QqchKeyPointContractClause;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:47:13
 * @remark
 */
public interface IQqchKeyPointContractClauseService {

    QqchKeyPointContractClause getQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause);

    List<QqchKeyPointContractClause> getQqchKeyPointContractClauseList(QqchKeyPointContractClause qqchKeyPointContractClause);

    int insertQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause);

    int insertQqchKeyPointContractClauseList(List<QqchKeyPointContractClause> qqchKeyPointContractClauseList);

    int updateQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause);

    int updateQqchKeyPointContractClauseList(List<QqchKeyPointContractClause> qqchKeyPointContractClauseList);

    int deleteQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause);

    int deleteQqchKeyPointContractClauseByPks(List<Long> qqchKeyPointContractClausePkList);
}
