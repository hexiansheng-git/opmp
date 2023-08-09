package com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.mapper;

import com.hhwy.pm.qqch.preparation.safe.qqchDangerJobIdentification.domain.QqchDangerJobIdentification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 11:55:28
 * @remark 
 */
public interface QqchDangerJobIdentificationMapper {
                                                                                                                                                                                                                                                                                                                                                                            
    QqchDangerJobIdentification getQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification);

    List<QqchDangerJobIdentification> getQqchDangerJobIdentificationList(QqchDangerJobIdentification qqchDangerJobIdentification);

    int insertQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification);

    int insertQqchDangerJobIdentificationList(@Param("qqchDangerJobIdentificationList") List<QqchDangerJobIdentification> qqchDangerJobIdentificationList);

    int updateQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification);

            int updateQqchDangerJobIdentificationList(@Param("qqchDangerJobIdentificationList") List<QqchDangerJobIdentification> qqchDangerJobIdentificationList);
    
    int deleteQqchDangerJobIdentification(QqchDangerJobIdentification qqchDangerJobIdentification);

            int deleteQqchDangerJobIdentificationByPks(@Param("qqchDangerJobIdentificationPkList") List<Long> qqchDangerJobIdentificationPkList);
    }
