package com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.mapper;

import com.hhwy.pm.qqch.preparation.safe.qqchPulicHealthRisk.domain.QqchPulicHealthRisk;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 15:01:10
 * @remark 
 */
public interface QqchPulicHealthRiskMapper {
                                                                                                                                                                                                                                                                                                                                                                            
    QqchPulicHealthRisk getQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk);

    List<QqchPulicHealthRisk> getQqchPulicHealthRiskList(QqchPulicHealthRisk qqchPulicHealthRisk);

    int insertQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk);

    int insertQqchPulicHealthRiskList(@Param("qqchPulicHealthRiskList") List<QqchPulicHealthRisk> qqchPulicHealthRiskList);

    int updateQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk);

            int updateQqchPulicHealthRiskList(@Param("qqchPulicHealthRiskList") List<QqchPulicHealthRisk> qqchPulicHealthRiskList);
    
    int deleteQqchPulicHealthRisk(QqchPulicHealthRisk qqchPulicHealthRisk);

            int deleteQqchPulicHealthRiskByPks(@Param("qqchPulicHealthRiskPkList") List<Long> qqchPulicHealthRiskPkList);
    }
