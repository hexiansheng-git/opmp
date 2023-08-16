package com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.mapper;

import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.QqchStandardExpenseAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-16 16:21:18
 * @remark
 */
@Repository
public interface QqchStandardExpenseAccountMapper {

    QqchStandardExpenseAccount getQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount);

    List<QqchStandardExpenseAccount> getQqchStandardExpenseAccountList(QqchStandardExpenseAccount qqchStandardExpenseAccount);

    int insertQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount);

    int insertQqchStandardExpenseAccountList(@Param("qqchStandardExpenseAccountList") List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList);

    int updateQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount);

    int updateQqchStandardExpenseAccountList(@Param("list") List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList);

    int deleteQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount);

    int deleteQqchStandardExpenseAccountByPks(@Param("qqchStandardExpenseAccountPkList") List<Long> qqchStandardExpenseAccountPkList);
}
