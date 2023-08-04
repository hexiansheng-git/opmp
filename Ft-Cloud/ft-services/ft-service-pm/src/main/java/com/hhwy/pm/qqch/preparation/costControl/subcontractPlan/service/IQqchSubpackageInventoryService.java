package com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.service;

import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.domain.QqchSubpackageInventory;
import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.domain.vo.SubpackageInventoryCollectVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:35:47
 * @remark
 */
public interface IQqchSubpackageInventoryService {

    QqchSubpackageInventory getQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory);

    List<QqchSubpackageInventory> getQqchSubpackageInventoryList(QqchSubpackageInventory qqchSubpackageInventory);

    int insertQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory);

    int insertQqchSubpackageInventoryList(List<QqchSubpackageInventory> qqchSubpackageInventoryList);

    int updateQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory);

    int updateQqchSubpackageInventoryList(List<QqchSubpackageInventory> qqchSubpackageInventoryList);

    int deleteQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory);

    int deleteQqchSubpackageInventoryByPks(List<Long> qqchSubpackageInventoryPkList);

    /**
     * 分包清单汇总
     * @param qqchSubpackageInventory
     * @return
     */
    List<SubpackageInventoryCollectVo> getSubpackageInventoryCollectVoList(QqchSubpackageInventory qqchSubpackageInventory);
}
