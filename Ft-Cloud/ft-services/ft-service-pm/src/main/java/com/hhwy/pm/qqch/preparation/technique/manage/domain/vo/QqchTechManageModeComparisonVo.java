package com.hhwy.pm.qqch.preparation.technique.manage.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchTechManageModeComparison;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-11 15:17:31
 * @remark 3.3.1技术管理模式比选
 */
@Data
public class QqchTechManageModeComparisonVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 技术管理模式比选集合
     */
    private List<QqchTechManageModeComparison> list;
}
