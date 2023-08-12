package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.domain.vo;


import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.domain.QqchSpecialBigProcessControl;
import lombok.Data;

import java.util.List;

@Data
public class QqchSpecialBigProcessControlVo extends PreparationEntity {

    private List<QqchSpecialBigProcessControl> list;
}
