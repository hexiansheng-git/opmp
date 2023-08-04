package com.hhwy.pm.qqch.preparation.quality.qc.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcTopicList;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-04 10:30:25
 * @remark QC课题清单
 */
@Data
public class QqchQcTopicListVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：QC课题清单集合
     */
    private List<QqchQcTopicList> list;
}
