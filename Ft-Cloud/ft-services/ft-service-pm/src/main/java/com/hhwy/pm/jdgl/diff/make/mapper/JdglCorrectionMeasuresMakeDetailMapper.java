package com.hhwy.pm.jdgl.diff.make.mapper;

import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMakeDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-25 15:26:48
 * @remark 纠偏措施制定详情
 */
public interface JdglCorrectionMeasuresMakeDetailMapper {

    JdglCorrectionMeasuresMakeDetail getJdglCorrectionMeasuresMakeDetail(
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail);

    List<JdglCorrectionMeasuresMakeDetail> getJdglCorrectionMeasuresMakeDetailList(
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail);

    int insertJdglCorrectionMeasuresMakeDetail(JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail);

    int insertJdglCorrectionMeasuresMakeDetailList(
        @Param("jdglCorrectionMeasuresMakeDetailList") List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailList);

    int updateJdglCorrectionMeasuresMakeDetail(JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail);

    int updateJdglCorrectionMeasuresMakeDetailList(
        @Param("list") List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailList);

    int deleteJdglCorrectionMeasuresMakeDetail(JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail);

    int deleteJdglCorrectionMeasuresMakeDetailByPks(
        @Param("jdglCorrectionMeasuresMakeDetailPkList") List<Long> jdglCorrectionMeasuresMakeDetailPkList);
}
