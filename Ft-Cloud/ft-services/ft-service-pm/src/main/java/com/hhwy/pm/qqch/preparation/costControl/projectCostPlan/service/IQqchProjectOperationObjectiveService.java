package com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.service;

import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.QqchProjectOperationObjective;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.vo.QqchProjectOperationObjectiveVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:39:48
 * @remark
 */
public interface IQqchProjectOperationObjectiveService {

    QqchProjectOperationObjective getQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective);

    List<QqchProjectOperationObjective> getQqchProjectOperationObjectiveList(QqchProjectOperationObjective qqchProjectOperationObjective);

    int insertQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective);

    int updateQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective);

    int updateQqchProjectOperationObjectiveList(List<QqchProjectOperationObjective> qqchProjectOperationObjectiveList);

    int deleteQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective);

    int deleteQqchProjectOperationObjectiveByPks(List<Long> qqchProjectOperationObjectivePkList);

    /**
     * 获取项目整体经营目标Vo
     * @param qqchProjectOperationObjective
     * @return
     */
    QqchProjectOperationObjectiveVo getQqchProjectOperationObjectiveVo(QqchProjectOperationObjective qqchProjectOperationObjective);

    /**
     * 保存/确认/提交
     * @param qqchProjectOperationObjectiveVo
     * @return
     */
    void save(QqchProjectOperationObjectiveVo qqchProjectOperationObjectiveVo);
}
