package com.hhwy.pm.qqch.preparation.sbch.equAllot.domain;/**
 * @description TODO
 * @date 2023-06-13 17:33
 * @author zq

 */
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zq
 * @date 2023年06月13日 17:33
 */
@Data
public class XcsbMonthSelfEquInsure extends CommonBaseEntity {
    private Long id;
    private Long infoId;
    private String insureFormNo;
    private BigDecimal insurePrice;
    private String insureDate;
    private String isTax;
    private String ptVar1;
    private String ptVar2;
    private String ptVar3;
    private String ptVar4;
    private Long projectId;
    private String prjCode;
    private String projectName;
    private Long delUser;
    private Date delTime;

}
