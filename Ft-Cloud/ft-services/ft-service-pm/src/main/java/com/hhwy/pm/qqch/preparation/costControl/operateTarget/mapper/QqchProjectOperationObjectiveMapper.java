package com.hhwy.pm.qqch.preparation.costControl.operateTarget.mapper;

import com.hhwy.pm.qqch.preparation.costControl.operateTarget.domain.QqchProjectOperationObjective;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:39:48
 * @remark
 */
@Repository
public interface QqchProjectOperationObjectiveMapper {

    QqchProjectOperationObjective getQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective);

    List<QqchProjectOperationObjective> getQqchProjectOperationObjectiveList(QqchProjectOperationObjective qqchProjectOperationObjective);

    int insertQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective);

    int insertQqchProjectOperationObjectiveList(@Param("qqchProjectOperationObjectiveList") List<QqchProjectOperationObjective> qqchProjectOperationObjectiveList);

    int updateQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective);

    int updateQqchProjectOperationObjectiveList(@Param("list") List<QqchProjectOperationObjective> qqchProjectOperationObjectiveList);

    int deleteQqchProjectOperationObjective(QqchProjectOperationObjective qqchProjectOperationObjective);

    int deleteQqchProjectOperationObjectiveByPks(@Param("qqchProjectOperationObjectivePkList") List<Long> qqchProjectOperationObjectivePkList);
}
