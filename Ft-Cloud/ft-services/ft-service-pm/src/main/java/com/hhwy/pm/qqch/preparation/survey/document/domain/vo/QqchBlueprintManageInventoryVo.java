package com.hhwy.pm.qqch.preparation.survey.document.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchBlueprintManageInventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-13 11:40:34
 * @remark 勘察设计图纸管理清单Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchBlueprintManageInventoryVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：勘察设计图纸管理清单集合
     */
    private List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryList;
}
