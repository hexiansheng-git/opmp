package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskList;
import lombok.Data;

/**
 * @author zqq
 * @create 2023-08-14 15:25
 */
@Data
public class QqchSafeMostEnvirRiskListVo extends PreparationEntity {

    /*是否编辑过*/
    private String isEdit;

    private Long wbsId;

    private String wbsCode;

    private QqchSafeMostEnvirRiskList qqchSafeMostEnvirRiskList;
}
