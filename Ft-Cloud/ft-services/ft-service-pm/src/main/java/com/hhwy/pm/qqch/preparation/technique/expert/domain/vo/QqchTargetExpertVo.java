package com.hhwy.pm.qqch.preparation.technique.expert.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetExpert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:56:35
 * @remark 内外部目标专家选择
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchTargetExpertVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：内外部目标专家选择集合
     */
    private List<QqchTargetExpert> list;
}
