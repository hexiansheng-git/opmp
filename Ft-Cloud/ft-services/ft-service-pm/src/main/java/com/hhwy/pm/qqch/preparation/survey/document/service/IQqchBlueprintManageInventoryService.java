package com.hhwy.pm.qqch.preparation.survey.document.service;

import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchBlueprintManageInventory;

import java.util.List;

/**
 * @author han
 * @date 2023-07-13 11:40:34
 * @remark 勘察设计图纸管理清单
 */
public interface IQqchBlueprintManageInventoryService {

    QqchBlueprintManageInventory getQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory);

    List<QqchBlueprintManageInventory> getQqchBlueprintManageInventoryList(QqchBlueprintManageInventory qqchBlueprintManageInventory);

    int insertQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory);

    int insertQqchBlueprintManageInventoryList(List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryList);

    int updateQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory);

    int updateQqchBlueprintManageInventoryList(List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryList);

    int deleteQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory);

    int deleteQqchBlueprintManageInventoryByPks(List<Long> qqchBlueprintManageInventoryPkList);
}
