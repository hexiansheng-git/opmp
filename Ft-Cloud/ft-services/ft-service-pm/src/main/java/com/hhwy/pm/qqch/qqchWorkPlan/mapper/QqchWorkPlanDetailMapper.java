package com.hhwy.pm.qqch.qqchWorkPlan.mapper;

import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlanDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<String> getEditorListByPlanStage(@Param("mainId") Long mainId, @Param("var1") String var1, @Param("var2") String var2);

}
