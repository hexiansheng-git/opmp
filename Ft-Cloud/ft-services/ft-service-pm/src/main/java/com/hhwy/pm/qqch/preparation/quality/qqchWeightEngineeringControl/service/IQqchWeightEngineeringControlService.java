package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.service;

import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.QqchWeightEngineeringControl;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringControl.domain.vo.QqchWeightEngineeringControlVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 15:10:09
 * @remark 
 */
public interface IQqchWeightEngineeringControlService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchWeightEngineeringControl getQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl);

    QqchWeightEngineeringControlVo getQqchWeightEngineeringControlList(QqchWeightEngineeringControl qqchWeightEngineeringControl);

    int insertQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl);


    int updateQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl);

   int updateQqchWeightEngineeringControlList(List<QqchWeightEngineeringControl> qqchWeightEngineeringControlList);
    
    int deleteQqchWeightEngineeringControl(QqchWeightEngineeringControl qqchWeightEngineeringControl);

   int deleteQqchWeightEngineeringControlByPks(List<Long> qqchWeightEngineeringControlPkList);

    void save(QqchWeightEngineeringControlVo vo);

    public void insertList(List<QqchWeightEngineeringControl> qqchWeightEngineeringControlList, BigDecimal version);
}
