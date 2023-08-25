package com.hhwy.pm.jdgl.diff.make.mapper;

import com.hhwy.pm.jdgl.diff.make.domain.JdglCorrectionMeasuresMake;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-25 14:33:50
 * @remark 纠偏措施制定
 */
public interface JdglCorrectionMeasuresMakeMapper {

    JdglCorrectionMeasuresMake getJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    List<JdglCorrectionMeasuresMake> getJdglCorrectionMeasuresMakeList(
        JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    int insertJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    int insertJdglCorrectionMeasuresMakeList(
        @Param("jdglCorrectionMeasuresMakeList") List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList);

    int updateJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    int updateJdglCorrectionMeasuresMakeList(@Param("list") List<JdglCorrectionMeasuresMake> jdglCorrectionMeasuresMakeList);

    int deleteJdglCorrectionMeasuresMake(JdglCorrectionMeasuresMake jdglCorrectionMeasuresMake);

    int deleteJdglCorrectionMeasuresMakeByPks(
        @Param("jdglCorrectionMeasuresMakePkList") List<Long> jdglCorrectionMeasuresMakePkList);
}
