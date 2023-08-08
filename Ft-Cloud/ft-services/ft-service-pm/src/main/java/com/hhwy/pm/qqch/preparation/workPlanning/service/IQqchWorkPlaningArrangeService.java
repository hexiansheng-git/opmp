package com.hhwy.pm.qqch.preparation.workPlanning.service;

import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrange;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrangeVo;

import java.util.List;

/**
 * @author zq
 * @date 2023-07-19 11:49:17
 * @remark 
 */
public interface IQqchWorkPlaningArrangeService {
                                                                                                                                                                                                                                                                                                                
    QqchWorkPlaningArrange getQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange);

    List<QqchWorkPlaningArrange> getQqchWorkPlaningArrangeList(QqchWorkPlaningArrange qqchWorkPlaningArrange);

    int insertQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange);

    int insertQqchWorkPlaningArrangeList(QqchWorkPlaningArrangeVo qqchWorkPlaningArrangeVo);

    int updateQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange);

            int updateQqchWorkPlaningArrangeList(List<QqchWorkPlaningArrange> qqchWorkPlaningArrangeList);
    
    int deleteQqchWorkPlaningArrange(QqchWorkPlaningArrange qqchWorkPlaningArrange);

            int deleteQqchWorkPlaningArrangeByPks(List<Long> qqchWorkPlaningArrangePkList);

    List<QqchWorkPlaningArrange> getMaxVVData(QqchWorkPlaningArrange arrangeVo);

    List<QqchWorkPlaningArrange> getQqchWorkPlaningArrangeListHistory(QqchWorkPlaningArrange arrangeVo);

    QqchWorkPlaningArrangeVo detail(QqchWorkPlaningArrange arrange);
}
