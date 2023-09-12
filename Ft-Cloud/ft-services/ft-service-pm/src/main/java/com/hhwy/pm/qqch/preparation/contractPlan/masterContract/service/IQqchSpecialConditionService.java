package com.hhwy.pm.qqch.preparation.contractPlan.masterContract.service;

import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.QqchSpecialCondition;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.vo.QqchSpecialConditionVo;

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

    int updateQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition);

    int updateQqchSpecialConditionList(List<QqchSpecialCondition> qqchSpecialConditionList);

    int deleteQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition);

    int deleteQqchSpecialConditionByPks(List<Long> qqchSpecialConditionPkList);

    /**
     * 获取专用条件梳理Vo
     * @param qqchSpecialCondition
     * @return
     */
    QqchSpecialConditionVo getQqchSpecialConditionVo(QqchSpecialCondition qqchSpecialCondition);

    /**
     * 保存/确认/提交
     * @param qqchSpecialConditionVo
     * @return
     */
    void save(QqchSpecialConditionVo qqchSpecialConditionVo);

    /**
     * 10.1财务相关主合同条款 弹窗
     * @param qqchSpecialCondition
     * @return
     */
    List<QqchSpecialCondition> popUpWindows(QqchSpecialCondition qqchSpecialCondition);
}
