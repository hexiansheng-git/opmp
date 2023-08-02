package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchDangerConstructionList;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-17 14:26:41
 * @remark qqch_danger_construction_list
 */
@Data
public class QqchDangerConstructionListVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：危大工程方案清单集合
     */
    private List<QqchDangerConstructionList> list;
}
