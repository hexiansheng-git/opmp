package com.hhwy.pm.qqch.tax.qqchTaxGoal.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxGoal.domain.QqchTaxGoal;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:29
 * @remark
 */
public interface IQqchTaxGoalService {

    QqchTaxGoal getQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

    List<QqchTaxGoal> getQqchTaxGoalList(QqchTaxGoal qqchTaxGoal);

    int insertQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

    int insertQqchTaxGoalList(List<QqchTaxGoal> qqchTaxGoalList);

    int updateQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

    int updateQqchTaxGoalList(List<QqchTaxGoal> qqchTaxGoalList);

    int deleteQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

    int deleteQqchTaxGoalByPks(List<Long> qqchTaxGoalPkList);

    /**
     * 保存
     *
     * @param list
     */
    void save(List<QqchTaxGoal> list);

    /**
     * 列表
     *
     * @param qqchTaxGoalParam
     * @return
     */
    CompileEntity<List<QqchTaxGoal>> list(QqchTaxGoal qqchTaxGoalParam);

}
