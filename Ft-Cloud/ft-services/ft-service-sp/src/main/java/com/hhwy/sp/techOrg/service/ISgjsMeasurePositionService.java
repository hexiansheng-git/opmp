package com.hhwy.sp.techOrg.service;

import com.hhwy.sp.techOrg.domain.SgjsMeasurePosition;

import java.util.List;


/**
 * @author lcf
 * @date 2023-11-20 10:41:18
 * @remark
 */
public interface ISgjsMeasurePositionService {

    SgjsMeasurePosition getSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition);

    List<SgjsMeasurePosition> getSgjsMeasurePositionList(SgjsMeasurePosition sgjsMeasurePosition);

    int insertSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition);

    int insertSgjsMeasurePositionList(List<SgjsMeasurePosition> sgjsMeasurePositionList);

    int updateSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition);

    int updateSgjsMeasurePositionList(List<SgjsMeasurePosition> sgjsMeasurePositionList);

    int deleteSgjsMeasurePosition(SgjsMeasurePosition sgjsMeasurePosition);

    int deleteSgjsMeasurePositionByPks(List<Long> sgjsMeasurePositionPkList);
}
