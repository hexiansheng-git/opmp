package com.hhwy.pm.jdgl.diff.make.service;

import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import java.util.Date;
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

    void insertJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    int insertJdglCorrectionMeasuresMakeList(List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList);

    void updateJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    int updateJdglCorrectionMeasuresMakeList(List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList);

    int deleteJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    int deleteJdglCorrectionMeasuresMakeByPks(List<Long> jdglCorrectionMeasuresMakePkList);

    /**
     * 更新流程数据
     * @param id
     */
    void updateTaskStatus(Long id);

    /**
     * 同步差异化分析数据
     * @param period
     */
    void syncData(Date period);
}
