package com.hhwy.pm.qqch.qqchWorkPlan.mapper;

import java.util.List;

import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import org.apache.ibatis.annotations.Param;

/**
 * @author hwj
 * @date 2023-07-14 17:15:57
 * @remark
 */
public interface QqchWorkPlanDetailMapper {

    QqchWorkPlanDetail getQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail);

    List<QqchWorkPlanDetail> getQqchWorkPlanDetailList(QqchWorkPlanDetail qqchWorkPlanDetail);

    int insertQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail);

    int insertQqchWorkPlanDetailList(@Param("qqchWorkPlanDetailList") List<QqchWorkPlanDetail> qqchWorkPlanDetailList);

    int updateQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail);

    int updateQqchWorkPlanDetailList(@Param("qqchWorkPlanDetailList") List<QqchWorkPlanDetail> qqchWorkPlanDetailList);

    int deleteQqchWorkPlanDetail(QqchWorkPlanDetail qqchWorkPlanDetail);

    int deleteQqchWorkPlanDetailByPks(@Param("qqchWorkPlanDetailPkList") List<Long> qqchWorkPlanDetailPkList);
}
