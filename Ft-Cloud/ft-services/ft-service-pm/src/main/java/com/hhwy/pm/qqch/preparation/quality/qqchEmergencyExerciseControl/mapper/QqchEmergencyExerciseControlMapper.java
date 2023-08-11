package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.domain.QqchEmergencyExerciseControl;

/**
 * @author ldd
 * @date 2023-08-10 18:39:07
 * @remark
 */
public interface QqchEmergencyExerciseControlMapper {

    QqchEmergencyExerciseControl getQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl);

    List<QqchEmergencyExerciseControl> getQqchEmergencyExerciseControlList(QqchEmergencyExerciseControl qqchEmergencyExerciseControl);

    int insertQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl);

    int insertQqchEmergencyExerciseControlList(@Param("qqchEmergencyExerciseControlList") List<QqchEmergencyExerciseControl> qqchEmergencyExerciseControlList);

    int updateQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl);

    int updateQqchEmergencyExerciseControlList(@Param("qqchEmergencyExerciseControlList") List<QqchEmergencyExerciseControl> qqchEmergencyExerciseControlList);

    int deleteQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl);

    int deleteQqchEmergencyExerciseControlByPks(@Param("qqchEmergencyExerciseControlPkList") List<Long> qqchEmergencyExerciseControlPkList);
}
