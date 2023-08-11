package com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.service;

import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.domain.QqchEmergencyPlanControl;
import com.hhwy.pm.qqch.preparation.quality.qqchEmergencyPlanControl.domain.vo.QqchEmergencyPlanControlVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-10 18:39:24
 * @remark
 */
public interface IQqchEmergencyPlanControlService {

    QqchEmergencyPlanControl getQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl);

    QqchEmergencyPlanControlVo getQqchEmergencyPlanControlList(QqchEmergencyPlanControl qqchEmergencyPlanControl);

    int insertQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl);


    int updateQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl);

    int updateQqchEmergencyPlanControlList(List<QqchEmergencyPlanControl> qqchEmergencyPlanControlList);

    int deleteQqchEmergencyPlanControl(QqchEmergencyPlanControl qqchEmergencyPlanControl);

    int deleteQqchEmergencyPlanControlByPks(List<Long> qqchEmergencyPlanControlPkList);

    void save(QqchEmergencyPlanControlVo vo);
}
