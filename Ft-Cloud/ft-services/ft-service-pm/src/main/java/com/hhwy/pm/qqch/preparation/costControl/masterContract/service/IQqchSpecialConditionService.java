package com.hhwy.pm.qqch.preparation.costControl.masterContract.service;

import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchSpecialCondition;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:48
 * @remark 专用条件梳理
 */
public interface IQqchSpecialConditionService {

    QqchSpecialCondition getQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition);

    List<QqchSpecialCondition> getQqchSpecialConditionList(QqchSpecialCondition qqchSpecialCondition);

    int insertQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition);

    int insertQqchSpecialConditionList(List<QqchSpecialCondition> qqchSpecialConditionList);

    int updateQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition);

    int updateQqchSpecialConditionList(List<QqchSpecialCondition> qqchSpecialConditionList);

    int deleteQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition);

    int deleteQqchSpecialConditionByPks(List<Long> qqchSpecialConditionPkList);
}
