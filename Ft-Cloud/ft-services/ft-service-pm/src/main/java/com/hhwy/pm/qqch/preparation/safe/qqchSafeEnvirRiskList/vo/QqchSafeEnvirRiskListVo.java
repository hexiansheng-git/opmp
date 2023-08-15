package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskList;
import lombok.Data;

import java.util.List;

/**
 * @author zqq
 * @create 2023-08-14 14:15
 */
@Data
public class QqchSafeEnvirRiskListVo extends PreparationEntity {
    private List<QqchSafeEnvirRiskList> list;
}
