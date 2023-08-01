package com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.service;

import com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.domain.QqchConstructionEquipment;
import com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.domain.vo.QqchConstructionEquipmentVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-01 16:22:12
 * @remark 
 */
public interface IQqchConstructionEquipmentService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchConstructionEquipment getQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment);

    QqchConstructionEquipmentVo getQqchConstructionEquipmentList(QqchConstructionEquipment qqchConstructionEquipment);

    int insertQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment);

    void insertQqchConstructionEquipmentList(List<QqchConstructionEquipment> qqchConstructionEquipmentList, BigDecimal version);

    int updateQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment);

            int updateQqchConstructionEquipmentList(List<QqchConstructionEquipment> qqchConstructionEquipmentList);
    
    int deleteQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment);

            int deleteQqchConstructionEquipmentByPks(List<Long> qqchConstructionEquipmentPkList);

    void save(QqchConstructionEquipmentVo qqchConstructionEquipmentVo);
}
