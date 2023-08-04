package com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.mapper;

import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.QqchAdjustAnalyse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:45:37
 * @remark
 */
@Repository
public interface QqchAdjustAnalyseMapper {

    QqchAdjustAnalyse getQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse);

    List<QqchAdjustAnalyse> getQqchAdjustAnalyseList(QqchAdjustAnalyse qqchAdjustAnalyse);

    int insertQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse);

    int insertQqchAdjustAnalyseList(@Param("qqchAdjustAnalyseList") List<QqchAdjustAnalyse> qqchAdjustAnalyseList);

    int updateQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse);

    int updateQqchAdjustAnalyseList(@Param("list") List<QqchAdjustAnalyse> qqchAdjustAnalyseList);

    int deleteQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse);

    int deleteQqchAdjustAnalyseByPks(@Param("qqchAdjustAnalysePkList") List<Long> qqchAdjustAnalysePkList);
}
