package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.domain.QqchEmergencyPlanControl;

/**
 * @author ldd
 * @date 2023-08-10 18:39:24
 * @remark 
 */
public interface QqchEmergencyPlanControlMapper {
                                                                                                                                                                                                                                                                                                                                                                                        
    QqchEmergencyPlanControl getQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl);

    List<QqchEmergencyPlanControl> getQqchEmergencyPlanControlList(QqchEmergencyPlanControl qqchEmergencyPlanControl);

    int insertQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl);

    int insertQqchEmergencyPlanControlList(@Param("qqchEmergencyPlanControlList") List<QqchEmergencyPlanControl> qqchEmergencyPlanControlList);

    int updateQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl);

            int updateQqchEmergencyPlanControlList(@Param("qqchEmergencyPlanControlList") List<QqchEmergencyPlanControl> qqchEmergencyPlanControlList);
    
    int deleteQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl);

            int deleteQqchEmergencyPlanControlByPks(@Param("qqchEmergencyPlanControlPkList") List<Long> qqchEmergencyPlanControlPkList);
    }
