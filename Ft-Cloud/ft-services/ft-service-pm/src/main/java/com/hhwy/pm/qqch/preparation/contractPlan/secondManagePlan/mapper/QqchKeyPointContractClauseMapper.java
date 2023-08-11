package com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.mapper;

import com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.domain.QqchKeyPointContractClause;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:47:13
 * @remark
 */
@Repository
public interface QqchKeyPointContractClauseMapper {

    QqchKeyPointContractClause getQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause);

    List<QqchKeyPointContractClause> getQqchKeyPointContractClauseList(QqchKeyPointContractClause qqchKeyPointContractClause);

    int insertQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause);

    int insertQqchKeyPointContractClauseList(@Param("qqchKeyPointContractClauseList") List<QqchKeyPointContractClause> qqchKeyPointContractClauseList);

    int updateQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause);

    int updateQqchKeyPointContractClauseList(@Param("list") List<QqchKeyPointContractClause> qqchKeyPointContractClauseList);

    int deleteQqchKeyPointContractClause(QqchKeyPointContractClause qqchKeyPointContractClause);

    int deleteQqchKeyPointContractClauseByPks(@Param("qqchKeyPointContractClausePkList") List<Long> qqchKeyPointContractClausePkList);
}
