package com.hhwy.pm.xmsl.contractInfo.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.common.core.annotation.Excel;
import lombok.Data;

@Data
public class XmslContractSignVo {

    /**
     * 字段描述：签订方
     */
    @JsonProperty
    @Excel(name = "签订方")
    private String signedBy;
    /**
     * 字段描述：签订单位
     */
    @JsonProperty
    @Excel(name = "签订单位")
    private String signedUnit;
    /**
     * 字段描述：经办人
     */
    @JsonProperty
    @Excel(name = "经办人")
    private String handledBy;
    /**
     * 字段描述：联系电话
     */
    @JsonProperty
    @Excel(name = "联系电话")
    private String phoneNum;
    /**
     * 字段描述：备注/描述
     */
    @JsonProperty
    @Excel(name = "备注")
    private String remark;
}
