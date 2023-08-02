package com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchGeneralCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:46
 * @remark 通用条件梳理Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchGeneralConditionVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：
     */
    private List<QqchGeneralCondition> qqchGeneralConditionList;
}
