package com.hhwy.pm.qqch.preparation.costControl.gatherPlan.mapper;

import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.QqchGatherPlanTaskMain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-23 11:10:38
 * @remark
 */
@Repository
public interface QqchGatherPlanTaskMainMapper {

    QqchGatherPlanTaskMain getQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain);

    List<QqchGatherPlanTaskMain> getQqchGatherPlanTaskMainList(QqchGatherPlanTaskMain qqchGatherPlanTaskMain);

    int insertQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain);

    int insertQqchGatherPlanTaskMainList(@Param("qqchGatherPlanTaskMainList") List<QqchGatherPlanTaskMain> qqchGatherPlanTaskMainList);

    int updateQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain);

    int updateQqchGatherPlanTaskMainList(@Param("list") List<QqchGatherPlanTaskMain> qqchGatherPlanTaskMainList);

    int deleteQqchGatherPlanTaskMain(QqchGatherPlanTaskMain qqchGatherPlanTaskMain);

    int deleteQqchGatherPlanTaskMainByPks(@Param("qqchGatherPlanTaskMainPkList") List<Long> qqchGatherPlanTaskMainPkList);
}
