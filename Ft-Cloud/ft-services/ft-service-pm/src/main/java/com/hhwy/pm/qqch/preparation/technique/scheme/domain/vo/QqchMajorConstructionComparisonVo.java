package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchMajorConstructionComparison;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-13 14:27:03
 * @remark 3.4.1重大施工方案比选
 */
@Data
public class QqchMajorConstructionComparisonVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：重大施工方案比选集合
     */
    private List<QqchMajorConstructionComparison> treeList;
}
