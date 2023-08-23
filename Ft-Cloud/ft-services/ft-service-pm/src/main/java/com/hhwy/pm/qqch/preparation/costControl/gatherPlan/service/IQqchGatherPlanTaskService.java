package com.hhwy.pm.qqch.preparation.costControl.gatherPlan.service;

import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.QqchGatherPlanTask;

import java.util.List;

/**
 * @author han
 * @date 2023-08-23 11:10:35
 * @remark
 */
public interface IQqchGatherPlanTaskService {

    QqchGatherPlanTask getQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask);

    List<QqchGatherPlanTask> getQqchGatherPlanTaskList(QqchGatherPlanTask qqchGatherPlanTask);

    int insertQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask);

    int insertQqchGatherPlanTaskList(List<QqchGatherPlanTask> qqchGatherPlanTaskList);

    int updateQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask);

    int updateQqchGatherPlanTaskList(List<QqchGatherPlanTask> qqchGatherPlanTaskList);

    int deleteQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask);

    int deleteQqchGatherPlanTaskByPks(List<Long> qqchGatherPlanTaskPkList);
}
