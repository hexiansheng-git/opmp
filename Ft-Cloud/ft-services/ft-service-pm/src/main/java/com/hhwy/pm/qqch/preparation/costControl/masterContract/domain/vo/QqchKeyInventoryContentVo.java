package com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchKeyInventoryContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:34:18
 * @remark qqch_key_inventory_content
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchKeyInventoryContentVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    private List<QqchKeyInventoryContent> qqchKeyInventoryContentList;
}
