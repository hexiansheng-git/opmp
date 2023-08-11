package com.hhwy.pm.qqch.preparation.contractPlan.masterContract.service;

import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.QqchGeneralCondition;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.vo.QqchGeneralConditionVo;

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

    int updateQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition);

    int updateQqchGeneralConditionList(List<QqchGeneralCondition> qqchGeneralConditionList);

    int deleteQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition);

    int deleteQqchGeneralConditionByPks(List<Long> qqchGeneralConditionPkList);

    /**
     * 获取通用条件梳理Vo
     * @param qqchGeneralCondition
     * @return
     */
    QqchGeneralConditionVo getQqchGeneralConditionVo(QqchGeneralCondition qqchGeneralCondition);

    /**
     * 保存/确认/提交
     * @param qqchGeneralConditionVo
     * @return
     */
    void save(QqchGeneralConditionVo qqchGeneralConditionVo);
}
