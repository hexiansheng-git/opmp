package com.hhwy.pm.qqch.preparation.costControl.postDuty.service;

import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.QqchCostControlPostDuty;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.vo.QqchCostControlPostDutyVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:48:45
 * @remark
 */
public interface IQqchCostControlPostDutyService {

    QqchCostControlPostDuty getQqchCostControlPostDuty(QqchCostControlPostDuty qqchCostControlPostDuty);

    List<QqchCostControlPostDuty> getQqchCostControlPostDutyList(QqchCostControlPostDuty qqchCostControlPostDuty);

    int insertQqchCostControlPostDuty(QqchCostControlPostDuty qqchCostControlPostDuty);

    int updateQqchCostControlPostDuty(QqchCostControlPostDuty qqchCostControlPostDuty);

    int updateQqchCostControlPostDutyList(List<QqchCostControlPostDuty> qqchCostControlPostDutyList);

    int deleteQqchCostControlPostDuty(QqchCostControlPostDuty qqchCostControlPostDuty);

    int deleteQqchCostControlPostDutyByPks(List<Long> qqchCostControlPostDutyPkList);

    /**
     * 获取Vo
     * @param qqchCostControlPostDuty
     * @return
     */
    QqchCostControlPostDutyVo getQqchCostControlPostDutyVo(QqchCostControlPostDuty qqchCostControlPostDuty);

    /**
     * 保存/确认/提交
     * @param qqchCostControlPostDutyVo
     * @return
     */
    void save(QqchCostControlPostDutyVo qqchCostControlPostDutyVo);

    /**
     * 同步人员总需计划
     * @param qqchCostControlPostDutyVo
     * @return
     */
    void synchronization(QqchCostControlPostDutyVo qqchCostControlPostDutyVo);
}
