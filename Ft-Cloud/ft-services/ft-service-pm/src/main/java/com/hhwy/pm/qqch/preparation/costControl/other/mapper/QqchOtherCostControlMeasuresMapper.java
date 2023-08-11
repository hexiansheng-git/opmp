package com.hhwy.pm.qqch.preparation.costControl.other.mapper;

import com.hhwy.pm.qqch.preparation.costControl.other.domain.QqchOtherCostControlMeasures;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-11 09:38:56
 * @remark 5.5 其他成本管控工作安排及措施
 */
public interface QqchOtherCostControlMeasuresMapper {

    QqchOtherCostControlMeasures getQqchOtherCostControlMeasures(
        QqchOtherCostControlMeasures qqchOtherCostControlMeasures);

    List<QqchOtherCostControlMeasures> getQqchOtherCostControlMeasuresList(
        QqchOtherCostControlMeasures qqchOtherCostControlMeasures);

    int insertQqchOtherCostControlMeasures(QqchOtherCostControlMeasures qqchOtherCostControlMeasures);

    int insertQqchOtherCostControlMeasuresList(
        @Param("qqchOtherCostControlMeasuresList") List<QqchOtherCostControlMeasures> qqchOtherCostControlMeasuresList);

    int updateQqchOtherCostControlMeasures(QqchOtherCostControlMeasures qqchOtherCostControlMeasures);

    int updateQqchOtherCostControlMeasuresList(
        @Param("list") List<QqchOtherCostControlMeasures> qqchOtherCostControlMeasuresList);

    int deleteQqchOtherCostControlMeasures(QqchOtherCostControlMeasures qqchOtherCostControlMeasures);

    int deleteQqchOtherCostControlMeasuresByPks(
        @Param("qqchOtherCostControlMeasuresPkList") List<Long> qqchOtherCostControlMeasuresPkList);
}
