package com.hhwy.pm.qqch.preparation.costControl.masterContract.service;

import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchGeneralCondition;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:46
 * @remark 通用条件梳理
 */
public interface IQqchGeneralConditionService {

    QqchGeneralCondition getQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition);

    List<QqchGeneralCondition> getQqchGeneralConditionList(QqchGeneralCondition qqchGeneralCondition);

    int insertQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition);

    int insertQqchGeneralConditionList(List<QqchGeneralCondition> qqchGeneralConditionList);

    int updateQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition);

    int updateQqchGeneralConditionList(List<QqchGeneralCondition> qqchGeneralConditionList);

    int deleteQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition);

    int deleteQqchGeneralConditionByPks(List<Long> qqchGeneralConditionPkList);
}
