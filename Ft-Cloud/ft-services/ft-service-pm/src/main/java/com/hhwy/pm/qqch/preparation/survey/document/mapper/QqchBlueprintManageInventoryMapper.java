package com.hhwy.pm.qqch.preparation.survey.document.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchBlueprintManageInventory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-13 11:40:34
 * @remark 勘察设计图纸管理清单
 */
@Repository
public interface QqchBlueprintManageInventoryMapper {

    QqchBlueprintManageInventory getQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory);

    List<QqchBlueprintManageInventory> getQqchBlueprintManageInventoryList(QqchBlueprintManageInventory qqchBlueprintManageInventory);

    int insertQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory);

    int insertQqchBlueprintManageInventoryList(@Param("qqchBlueprintManageInventoryList") List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryList);

    int updateQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory);

    int updateQqchBlueprintManageInventoryList(@Param("list") List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryList);

    int deleteQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory);

    int deleteQqchBlueprintManageInventoryByPks(@Param("qqchBlueprintManageInventoryPkList") List<Long> qqchBlueprintManageInventoryPkList);
}
