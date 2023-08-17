package com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.service;

import com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.domain.QqchMeasuringInstrument;
import com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.domain.vo.QqchMeasuringInstrumentVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-02 10:50:39
 * @remark 
 */
public interface IQqchMeasuringInstrumentService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchMeasuringInstrument getQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument);

    QqchMeasuringInstrumentVo  getQqchMeasuringInstrumentList(QqchMeasuringInstrument qqchMeasuringInstrument);

    int insertQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument);


    int updateQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument);

            int updateQqchMeasuringInstrumentList(List<QqchMeasuringInstrument> qqchMeasuringInstrumentList);
    
    int deleteQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument);

            int deleteQqchMeasuringInstrumentByPks(List<Long> qqchMeasuringInstrumentPkList);

    void save(QqchMeasuringInstrumentVo vo);

    QqchMeasuringInstrumentVo syncData(QqchMeasuringInstrumentVo qqchMeasuringInstrumentParam);
}
