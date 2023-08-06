package com.hhwy.pm.qqch.preparation.measureexp.equ.mapper;

import com.hhwy.pm.qqch.preparation.measureexp.equ.domain.QqchMeasureExpEqu;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-04 16:12:29
 * @remark 3.6.4测量仪器设备配置计划、3.7.4试验仪器设备配置计划
 */
public interface QqchMeasureExpEquMapper {

    QqchMeasureExpEqu getQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

    List<QqchMeasureExpEqu> getQqchMeasureExpEquList(QqchMeasureExpEqu qqchMeasureExpEqu);

    int insertQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

    int insertQqchMeasureExpEquList(@Param("qqchMeasureExpEquList") List<QqchMeasureExpEqu> qqchMeasureExpEquList);

    int updateQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

    int updateQqchMeasureExpEquList(@Param("list") List<QqchMeasureExpEqu> qqchMeasureExpEquList);

    int deleteQqchMeasureExpEqu(QqchMeasureExpEqu qqchMeasureExpEqu);

    int deleteQqchMeasureExpEquByPks(@Param("qqchMeasureExpEquPkList") List<Long> qqchMeasureExpEquPkList);
}
