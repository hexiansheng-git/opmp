package com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.mapper;

import com.hhwy.pm.qqch.preparation.contractPlan.secondManagePlan.domain.QqchSecondManageKeyPoint;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:47:11
 * @remark
 */
@Repository
public interface QqchSecondManageKeyPointMapper {

    QqchSecondManageKeyPoint getQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

    List<QqchSecondManageKeyPoint> getQqchSecondManageKeyPointList(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

    int insertQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

    int insertQqchSecondManageKeyPointList(@Param("qqchSecondManageKeyPointList") List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointList);

    int updateQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

    int updateQqchSecondManageKeyPointList(@Param("list") List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointList);

    int deleteQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint);

    int deleteQqchSecondManageKeyPointByPks(@Param("qqchSecondManageKeyPointPkList") List<Long> qqchSecondManageKeyPointPkList);
}
