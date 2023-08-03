package com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.mapper;

import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.domain.QqchSubpackageInventory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:35:47
 * @remark
 */
@Repository
public interface QqchSubpackageInventoryMapper {

    QqchSubpackageInventory getQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory);

    List<QqchSubpackageInventory> getQqchSubpackageInventoryList(QqchSubpackageInventory qqchSubpackageInventory);

    int insertQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory);

    int insertQqchSubpackageInventoryList(@Param("qqchSubpackageInventoryList") List<QqchSubpackageInventory> qqchSubpackageInventoryList);

    int updateQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory);

    int updateQqchSubpackageInventoryList(@Param("list") List<QqchSubpackageInventory> qqchSubpackageInventoryList);

    int deleteQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory);

    int deleteQqchSubpackageInventoryByPks(@Param("qqchSubpackageInventoryPkList") List<Long> qqchSubpackageInventoryPkList);
}
