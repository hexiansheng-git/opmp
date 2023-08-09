package com.hhwy.pm.xmsl.wbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * wbs挂接清单
 * @author wk
 * @date 2023-08-03 18:39:00
 * @remark xmsl_wbs_list_relation
 */
@Data
public class XmslWbsListRelation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public XmslWbsListRelation() {
    }

    public XmslWbsListRelation(Long mainId, Long wbsId, String listCode, Long listId) {
        this.mainId = mainId;
        this.wbsId = wbsId;
        this.listCode = listCode;
        this.listId = listId;
    }

    /**
     * 字段描述：mainId,xmsl_wbs_main
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "mainId,xmsl_wbs_main")
    private Long mainId;
    /**
     * 字段描述：wbsId,xmsl_wbs
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "wbsId,xmsl_wbs")
    private Long wbsId;

    private Long wbsCode;
    /**
     * 字段描述：工程量清单编码,xmsl_contract_list.code
     */
    @JsonProperty
    @Excel(name = "工程量清单编码,xmsl_contract_list.code")
    private String listCode;
    /**
     * 字段描述：工程量清单ID,xmsl_contract_list
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "工程量清单ID,xmsl_contract_list")
    private Long listId;

}
