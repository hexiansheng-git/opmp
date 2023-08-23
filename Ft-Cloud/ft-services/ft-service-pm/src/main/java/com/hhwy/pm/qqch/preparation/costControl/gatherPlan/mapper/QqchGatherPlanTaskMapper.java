package com.hhwy.pm.qqch.preparation.costControl.gatherPlan.mapper;

import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.QqchGatherPlanTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-23 11:10:35
 * @remark
 */
@Repository
public interface QqchGatherPlanTaskMapper {

    QqchGatherPlanTask getQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask);

    List<QqchGatherPlanTask> getQqchGatherPlanTaskList(QqchGatherPlanTask qqchGatherPlanTask);

    int insertQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask);

    int insertQqchGatherPlanTaskList(@Param("qqchGatherPlanTaskList") List<QqchGatherPlanTask> qqchGatherPlanTaskList);

    int updateQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask);

    int updateQqchGatherPlanTaskList(@Param("list") List<QqchGatherPlanTask> qqchGatherPlanTaskList);

    int deleteQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask);

    int deleteQqchGatherPlanTaskByPks(@Param("qqchGatherPlanTaskPkList") List<Long> qqchGatherPlanTaskPkList);
}
