package com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.mapper;

import com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.domain.QqchMeasuringInstrument;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author ldd
 * @date 2023-08-02 10:50:39
 * @remark 
 */
public interface QqchMeasuringInstrumentMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchMeasuringInstrument getQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument);

    List<QqchMeasuringInstrument> getQqchMeasuringInstrumentList(QqchMeasuringInstrument qqchMeasuringInstrument);

    int insertQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument);

    int insertQqchMeasuringInstrumentList(@Param("qqchMeasuringInstrumentList") List<QqchMeasuringInstrument> qqchMeasuringInstrumentList);

    int updateQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument);

            int updateQqchMeasuringInstrumentList(@Param("qqchMeasuringInstrumentList") List<QqchMeasuringInstrument> qqchMeasuringInstrumentList);
    
    int deleteQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument);

            int deleteQqchMeasuringInstrumentByPks(@Param("qqchMeasuringInstrumentPkList") List<Long> qqchMeasuringInstrumentPkList);
    }
