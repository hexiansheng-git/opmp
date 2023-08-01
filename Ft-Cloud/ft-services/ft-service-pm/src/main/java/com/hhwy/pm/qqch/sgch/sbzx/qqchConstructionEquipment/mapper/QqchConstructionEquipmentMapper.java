package com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.mapper;

import com.hhwy.pm.qqch.sgch.sbzx.qqchConstructionEquipment.domain.QqchConstructionEquipment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-01 16:22:12
 * @remark 
 */
public interface QqchConstructionEquipmentMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchConstructionEquipment getQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment);

    List<QqchConstructionEquipment> getQqchConstructionEquipmentList(QqchConstructionEquipment qqchConstructionEquipment);

    int insertQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment);

    int insertQqchConstructionEquipmentList(@Param("qqchConstructionEquipmentList") List<QqchConstructionEquipment> qqchConstructionEquipmentList);

    int updateQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment);

            int updateQqchConstructionEquipmentList(@Param("qqchConstructionEquipmentList") List<QqchConstructionEquipment> qqchConstructionEquipmentList);
    
    int deleteQqchConstructionEquipment(QqchConstructionEquipment qqchConstructionEquipment);

            int deleteQqchConstructionEquipmentByPks(@Param("qqchConstructionEquipmentPkList") List<Long> qqchConstructionEquipmentPkList);
    }
