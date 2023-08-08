package com.hhwy.pm.qqch.preparation.safe.danger.mapper;

import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasures;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-07 14:23:10
 * @remark 8.3.2 危大工程安全技术措施
 */
public interface QqchDangerSafeMeasuresMapper {

    QqchDangerSafeMeasures getQqchDangerSafeMeasures(QqchDangerSafeMeasures qqchDangerSafeMeasures);

    List<QqchDangerSafeMeasures> getQqchDangerSafeMeasuresList(QqchDangerSafeMeasures qqchDangerSafeMeasures);

    int insertQqchDangerSafeMeasures(QqchDangerSafeMeasures qqchDangerSafeMeasures);

    int insertQqchDangerSafeMeasuresList(
        @Param("qqchDangerSafeMeasuresList") List<QqchDangerSafeMeasures> qqchDangerSafeMeasuresList);

    int updateQqchDangerSafeMeasures(QqchDangerSafeMeasures qqchDangerSafeMeasures);

    int updateQqchDangerSafeMeasuresList(@Param("list") List<QqchDangerSafeMeasures> qqchDangerSafeMeasuresList);

    int deleteQqchDangerSafeMeasures(QqchDangerSafeMeasures qqchDangerSafeMeasures);

    int deleteQqchDangerSafeMeasuresByPks(
        @Param("qqchDangerSafeMeasuresPkList") List<Long> qqchDangerSafeMeasuresPkList);
}
