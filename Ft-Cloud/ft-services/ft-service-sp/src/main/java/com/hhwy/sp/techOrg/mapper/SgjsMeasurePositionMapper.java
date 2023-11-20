package com.hhwy.sp.techOrg.mapper;

import com.hhwy.sp.techOrg.domain.SgjsMeasurePosition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lcf
 * @date 2023-11-20 10:41:18
 * @remark
 */
public interface SgjsMeasurePositionMapper {

    SgjsMeasurePosition getSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition);

    List<SgjsMeasurePosition> getSgjsMeasurePositionList(SgjsMeasurePosition sgjsMeasurePosition);

    int insertSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition);

    int insertSgjsMeasurePositionList(@Param("sgjsMeasurePositionList") List<SgjsMeasurePosition> sgjsMeasurePositionList);

    int updateSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition);

    int updateSgjsMeasurePositionList(@Param("sgjsMeasurePositionList") List<SgjsMeasurePosition> sgjsMeasurePositionList);

    int deleteSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition);

    int deleteSgjsMeasurePositionByPks(@Param("sgjsMeasurePositionPkList") List<Long> sgjsMeasurePositionPkList);
}
