package com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.service;

import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.QqchStandardExpenseAccount;

import java.util.List;

/**
 * @author han
 * @date 2023-08-16 16:21:18
 * @remark
 */
public interface IQqchStandardExpenseAccountService {

    QqchStandardExpenseAccount getQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount);

    List<QqchStandardExpenseAccount> getQqchStandardExpenseAccountList(QqchStandardExpenseAccount qqchStandardExpenseAccount);

    int insertQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount);

    int insertQqchStandardExpenseAccountList(List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList);

    int updateQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount);

    int updateQqchStandardExpenseAccountList(List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList);

    int deleteQqchStandardExpenseAccount(QqchStandardExpenseAccount qqchStandardExpenseAccount);

    int deleteQqchStandardExpenseAccountByPks(List<Long> qqchStandardExpenseAccountPkList);

    List<QqchStandardExpenseAccount> changeId(List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList);
}
