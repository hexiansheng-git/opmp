package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchKeyDifficultConstructionBrief;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-17 15:29:49
 * @remark qqch_key_difficult_construction_brief
 */
@Data
public class QqchKeyDifficultConstructionBriefVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：重难点分项施工方案简述集合
     */
    private List<QqchKeyDifficultConstructionBrief> list;
}
