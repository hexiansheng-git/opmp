package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.mapper;

import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.QqchWeightEngineeringControl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 15:10:09
 * @remark 
 */
public interface QqchWeightEngineeringControlMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchWeightEngineeringControl getQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl);

    List<QqchWeightEngineeringControl> getQqchWeightEngineeringControlList(QqchWeightEngineeringControl qqchWeightEngineeringControl);

    int insertQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl);

    int insertQqchWeightEngineeringControlList(@Param("qqchWeightEngineeringControlList") List<QqchWeightEngineeringControl> qqchWeightEngineeringControlList);

    int updateQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl);

            int updateQqchWeightEngineeringControlList(@Param("qqchWeightEngineeringControlList") List<QqchWeightEngineeringControl> qqchWeightEngineeringControlList);
    
    int deleteQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl);

            int deleteQqchWeightEngineeringControlByPks(@Param("qqchWeightEngineeringControlPkList") List<Long> qqchWeightEngineeringControlPkList);
    }
