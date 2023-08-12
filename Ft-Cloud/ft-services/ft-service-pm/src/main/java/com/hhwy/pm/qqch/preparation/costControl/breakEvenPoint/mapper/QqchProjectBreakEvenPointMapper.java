package com.hhwy.pm.qqch.preparation.costControl.breakEvenPoint.mapper;

import com.hhwy.pm.qqch.preparation.costControl.breakEvenPoint.domain.QqchProjectBreakEvenPoint;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:39:59
 * @remark
 */
@Repository
public interface QqchProjectBreakEvenPointMapper {

    QqchProjectBreakEvenPoint getQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint);

    List<QqchProjectBreakEvenPoint> getQqchProjectBreakEvenPointList(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint);

    int insertQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint);

    int insertQqchProjectBreakEvenPointList(@Param("qqchProjectBreakEvenPointList") List<QqchProjectBreakEvenPoint> qqchProjectBreakEvenPointList);

    int updateQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint);

    int updateQqchProjectBreakEvenPointList(@Param("list") List<QqchProjectBreakEvenPoint> qqchProjectBreakEvenPointList);

    int deleteQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint);

    int deleteQqchProjectBreakEvenPointByPks(@Param("qqchProjectBreakEvenPointPkList") List<Long> qqchProjectBreakEvenPointPkList);
}
