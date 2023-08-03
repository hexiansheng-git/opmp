package com.hhwy.pm.qqch.preparation.quality.duty.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.quality.duty.domain.QqchQualityPostDuty;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-03 14:28:57
 * @remark 质量岗位职责
 */
@Data
public class QqchQualityPostDutyVo extends PreparationEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：质量岗位职责集合
     */
    private List<QqchQualityPostDuty> list;
}
