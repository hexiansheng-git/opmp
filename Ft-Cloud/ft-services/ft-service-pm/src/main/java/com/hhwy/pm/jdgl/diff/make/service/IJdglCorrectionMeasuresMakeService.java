package com.hhwy.pm.jdgl.diff.make.service;

import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-25 14:33:50
 * @remark 纠偏措施制定
 */
public interface IJdglCorrectionMeasuresMakeService {

    JdglCorrectionMeasuresMake getJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    List<JdglCorrectionMeasuresMake> getJdglCorrectionMeasuresMakeList(
        JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    int insertJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    int insertJdglCorrectionMeasuresMakeList(List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList);

    void updateJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    int updateJdglCorrectionMeasuresMakeList(List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList);

    int deleteJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    int deleteJdglCorrectionMeasuresMakeByPks(List<Long> jdglCorrectionMeasuresMakePkList);
}
