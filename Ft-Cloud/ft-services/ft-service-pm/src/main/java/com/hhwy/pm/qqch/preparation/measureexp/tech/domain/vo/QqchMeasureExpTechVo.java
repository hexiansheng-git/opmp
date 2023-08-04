package com.hhwy.pm.qqch.preparation.measureexp.tech.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.measureexp.tech.domain.QqchMeasureExpTech;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-04 16:09:57
 * @remark 3.6.3测量技术方案计划、3.7.3试验方案计划
 */
@Data
public class QqchMeasureExpTechVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：方案类别 1-测量技术方案计划，2-试验方案计划
     */
    private String type;

    /**
     * 字段描述：测量技术方案计划集合
     */
    private List<QqchMeasureExpTech> measureList;

    /**
     * 字段描述：试验方案计划集合
     */
    private List<QqchMeasureExpTech> experimentList;
}
