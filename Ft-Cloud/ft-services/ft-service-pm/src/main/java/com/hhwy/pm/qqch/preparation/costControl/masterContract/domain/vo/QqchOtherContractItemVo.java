package com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchOtherContractItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:36
 * @remark 其他合同事项分析Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchOtherContractItemVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    private List<QqchOtherContractItem> list;
}
