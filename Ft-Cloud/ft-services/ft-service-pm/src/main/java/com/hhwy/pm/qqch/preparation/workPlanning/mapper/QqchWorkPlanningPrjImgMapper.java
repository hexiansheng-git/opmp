package com.hhwy.pm.qqch.preparation.workPlanning.mapper;

import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningPrjImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zq
 * @date 2023-07-17 14:18:26
 * @remark 
 */
public interface QqchWorkPlanningPrjImgMapper {
                                                                                                                                                                
    QqchWorkPlanningPrjImg getQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg);

    List<QqchWorkPlanningPrjImg> getQqchWorkPlanningPrjImgList(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg);

    int insertQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg);

    int insertQqchWorkPlanningPrjImgList(@Param("qqchWorkPlanningPrjImgList") List<QqchWorkPlanningPrjImg> qqchWorkPlanningPrjImgList);

    int updateQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg);

            int updateQqchWorkPlanningPrjImgList(@Param("qqchWorkPlanningPrjImgList") List<QqchWorkPlanningPrjImg> qqchWorkPlanningPrjImgList);
    
    int deleteQqchWorkPlanningPrjImg(QqchWorkPlanningPrjImg qqchWorkPlanningPrjImg);

            int deleteQqchWorkPlanningPrjImgByPks(@Param("qqchWorkPlanningPrjImgPkList") List<Long> qqchWorkPlanningPrjImgPkList);
    }
