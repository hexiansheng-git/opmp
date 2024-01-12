package com.hhwy.pm.qqch.preparation.workPlanning.service;

import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningPrjImg;

import java.util.List;

/**
 * @author zq
 * @date 2023-07-17 14:18:26
 * @remark
 */
public interface IQqchWorkPlanningPrjImgService {

    QqchWorkPlanningPrjImg getQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg);

    QqchWorkPlanningPrjImg getLatestQqchWorkPlanningPrjImg();

    List<QqchWorkPlanningPrjImg> getQqchWorkPlanningPrjImgList(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg);

    int insertQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg);

    int insertQqchWorkPlanningPrjImgList(List<QqchWorkPlanningPrjImg> qqchWorkPlanningPrjImgList);

    int updateQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg);

    int updateQqchWorkPlanningPrjImgList(List<QqchWorkPlanningPrjImg> qqchWorkPlanningPrjImgList);

    int deleteQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg);

    int deleteQqchWorkPlanningPrjImgByPks(List<Long> qqchWorkPlanningPrjImgPkList);

    QqchWorkPlanningPrjImg getQqchWorkPlanningPrjIsValid(QqchWorkPlanningPrjImg img);

    QqchWorkPlanningPrjImg getQqchWorkPlanningPrjHistory(QqchWorkPlanningPrjImg img);

    void listener(Long businessId);
}
