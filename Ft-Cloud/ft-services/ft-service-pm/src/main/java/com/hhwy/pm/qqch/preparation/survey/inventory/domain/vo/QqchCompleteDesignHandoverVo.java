package com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchCompleteDesignHandover;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-11 17:11:14
 * @remark 完整设计交接情况
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchCompleteDesignHandoverVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：完整设计交接情况集合
     */
    private List<QqchCompleteDesignHandover> qqchCompleteDesignHandoverList;
}
