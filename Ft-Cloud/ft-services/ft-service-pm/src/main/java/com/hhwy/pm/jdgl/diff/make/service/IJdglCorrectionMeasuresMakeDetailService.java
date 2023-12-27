package com.hhwy.pm.jdgl.diff.make.service;

import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMakeDetail;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-25 15:26:48
 * @remark 纠偏措施制定详情
 */
public interface IJdglCorrectionMeasuresMakeDetailService {

    JdglCorrectionMeasuresMakeDetail getJdglCorrectionMeasuresMakeDetail(
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail);

    List<JdglCorrectionMeasuresMakeDetail> getJdglCorrectionMeasuresMakeDetailList(
        JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail);

    int insertJdglCorrectionMeasuresMakeDetail(JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail);

    int insertJdglCorrectionMeasuresMakeDetailList(
        List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailList);

    int updateJdglCorrectionMeasuresMakeDetail(JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail);

    int updateJdglCorrectionMeasuresMakeDetailList(
        List<JdglCorrectionMeasuresMakeDetail> jdglCorrectionMeasuresMakeDetailList);

    int deleteJdglCorrectionMeasuresMakeDetail(JdglCorrectionMeasuresMakeDetail jdglCorrectionMeasuresMakeDetail);

    int deleteJdglCorrectionMeasuresMakeDetailByPks(List<Long> jdglCorrectionMeasuresMakeDetailPkList);

    List<JdglCorrectionMeasuresMakeDetail> getDetailListByMakeId(JdglCorrectionMeasuresMake makeId);

    int setEidtFlag();
}
