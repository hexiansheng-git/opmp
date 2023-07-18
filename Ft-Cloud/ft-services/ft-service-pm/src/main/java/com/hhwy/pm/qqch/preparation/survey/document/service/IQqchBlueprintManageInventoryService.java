package com.hhwy.pm.qqch.preparation.survey.document.service;

import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchBlueprintManageInventory;
import com.hhwy.pm.qqch.preparation.survey.document.domain.vo.QqchBlueprintManageInventoryVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-13 11:40:34
 * @remark 勘察设计图纸管理清单
 */
public interface IQqchBlueprintManageInventoryService {

    /**
     * 勘察设计图纸管理清单台账
     * @return
     */
    QqchBlueprintManageInventoryVo getQqchBlueprintManageInventoryVo();

    /**
     * 保存
     * @param qqchBlueprintManageInventoryVo
     * @return
     */
    void save(QqchBlueprintManageInventoryVo qqchBlueprintManageInventoryVo);

    /**
     * 确认
     * @param qqchBlueprintManageInventoryVo
     * @return
     */
    void confirm(QqchBlueprintManageInventoryVo qqchBlueprintManageInventoryVo);
}
