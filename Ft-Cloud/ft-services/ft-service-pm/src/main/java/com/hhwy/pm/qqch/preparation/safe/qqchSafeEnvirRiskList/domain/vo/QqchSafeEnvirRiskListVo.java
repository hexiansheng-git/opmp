package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskList;
import lombok.Data;

/**
 * @author zqq
 * @create 2023-08-14 14:15
 */
@Data
public class QqchSafeEnvirRiskListVo extends PreparationEntity {

    /*是否编辑过*/
    private String isEdit;

    private String wbsId;

    private String wbsCode;

    private QqchSafeEnvirRiskList qqchSafeEnvirRiskList;
}
