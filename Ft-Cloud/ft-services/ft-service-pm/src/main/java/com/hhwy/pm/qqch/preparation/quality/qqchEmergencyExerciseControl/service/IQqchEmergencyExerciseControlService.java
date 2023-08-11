package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.service;

import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.domain.QqchEmergencyExerciseControl;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyExerciseControl.domain.vo.QqchEmergencyExerciseControlVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-10 18:39:07
 * @remark
 */
public interface IQqchEmergencyExerciseControlService {

    QqchEmergencyExerciseControl getQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl);

    QqchEmergencyExerciseControlVo getQqchEmergencyExerciseControlList(QqchEmergencyExerciseControl qqchEmergencyExerciseControl);

    int insertQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl);

    int updateQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl);

    int updateQqchEmergencyExerciseControlList(List<QqchEmergencyExerciseControl> qqchEmergencyExerciseControlList);

    int deleteQqchEmergencyExerciseControl(QqchEmergencyExerciseControl qqchEmergencyExerciseControl);

    int deleteQqchEmergencyExerciseControlByPks(List<Long> qqchEmergencyExerciseControlPkList);

    void save(QqchEmergencyExerciseControlVo vo);
}
