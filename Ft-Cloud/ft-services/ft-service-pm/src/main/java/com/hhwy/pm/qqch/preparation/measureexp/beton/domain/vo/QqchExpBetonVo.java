package com.hhwy.pm.qqch.preparation.measureexp.beton.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.QqchExpBeton;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-04 16:12:49
 * @remark 混凝土配合比
 */
@Data
public class QqchExpBetonVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：混凝土配合比树集合
     */
    private List<QqchExpBeton> treeList;
}
