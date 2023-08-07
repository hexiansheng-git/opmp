package com.hhwy.pm.qqch.preparation.safe.danger.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerList;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-07 14:22:57
 * @remark 危大工程清单
 */
@Data
public class QqchDangerListVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：危大工程清单集合
     */
    private List<QqchDangerList> list;
}
