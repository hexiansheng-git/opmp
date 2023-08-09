package com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.safe.qqchSocietySafeRisk.domain.QqchSocietySafeRisk;

/**
 * @author ldd
 * @date 2023-08-09 09:30:14
 * @remark 
 */
public interface QqchSocietySafeRiskMapper {
                                                                                                                                                                                                                                                                                                                                        
    QqchSocietySafeRisk getQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk);

    List<QqchSocietySafeRisk> getQqchSocietySafeRiskList(QqchSocietySafeRisk qqchSocietySafeRisk);

    int insertQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk);

    int insertQqchSocietySafeRiskList(@Param("qqchSocietySafeRiskList") List<QqchSocietySafeRisk> qqchSocietySafeRiskList);

    int updateQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk);

            int updateQqchSocietySafeRiskList(@Param("qqchSocietySafeRiskList") List<QqchSocietySafeRisk> qqchSocietySafeRiskList);
    
    int deleteQqchSocietySafeRisk(QqchSocietySafeRisk qqchSocietySafeRisk);

            int deleteQqchSocietySafeRiskByPks(@Param("qqchSocietySafeRiskPkList") List<Long> qqchSocietySafeRiskPkList);
    }
