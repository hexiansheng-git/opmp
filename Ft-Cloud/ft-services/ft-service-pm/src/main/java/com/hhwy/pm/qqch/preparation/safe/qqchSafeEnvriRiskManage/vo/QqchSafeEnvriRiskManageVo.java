package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvriRiskManage.domain.QqchSafeEnvriRiskManage;
import lombok.Data;

import java.util.List;

/**
 * @author zqq
 * @create 2023-08-14 15:56
 */
@Data
public class QqchSafeEnvriRiskManageVo extends PreparationEntity {
    private List<QqchSafeEnvriRiskManage> list;
}
