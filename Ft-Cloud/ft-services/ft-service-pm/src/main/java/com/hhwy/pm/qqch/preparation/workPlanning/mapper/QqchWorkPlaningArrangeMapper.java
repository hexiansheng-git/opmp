package com.hhwy.pm.qqch.preparation.workPlanning.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrange;
import org.apache.ibatis.annotations.Param;

/**
 * @author zq
 * @date 2023-07-19 11:49:17
 * @remark
 */
public interface QqchWorkPlaningArrangeMapper {

    QqchWorkPlaningArrange getQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange);

    List<QqchWorkPlaningArrange> getQqchWorkPlaningArrangeList(QqchWorkPlaningArrange qqchWorkPlaningArrange);

    int insertQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange);

    int insertQqchWorkPlaningArrangeList(@Param("qqchWorkPlaningArrangeList") List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList);

    int updateQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange);

    int updateQqchWorkPlaningArrangeList(@Param("qqchWorkPlaningArrangeList") List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList);

    int deleteQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange);

    int deleteQqchWorkPlaningArrangeByPks(@Param("qqchWorkPlaningArrangePkList") List<Long> qqchWorkPlaningArrangePkList);
}
