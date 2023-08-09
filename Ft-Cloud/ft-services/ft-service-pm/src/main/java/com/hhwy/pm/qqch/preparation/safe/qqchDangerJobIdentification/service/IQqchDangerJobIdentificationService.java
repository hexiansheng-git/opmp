package com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.service;

import com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.domain.QqchDangerJobIdentification;
import com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.domain.vo.QqchDangerJobIdentificationVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 11:55:28
 * @remark 
 */
public interface IQqchDangerJobIdentificationService {
                                                                                                                                                                                                                                                                                                                                                                            
    QqchDangerJobIdentification getQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification);

    QqchDangerJobIdentificationVo getQqchDangerJobIdentificationList(QqchDangerJobIdentification qqchDangerJobIdentification);

    int insertQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification);

    int updateQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification);

    int updateQqchDangerJobIdentificationList(List<QqchDangerJobIdentification> qqchDangerJobIdentificationList);
    
    int deleteQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification);

    int deleteQqchDangerJobIdentificationByPks(List<Long> qqchDangerJobIdentificationPkList);

    void save(QqchDangerJobIdentificationVo vo);
}
