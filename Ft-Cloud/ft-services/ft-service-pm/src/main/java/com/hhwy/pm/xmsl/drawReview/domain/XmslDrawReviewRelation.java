package com.hhwy.pm.xmsl.drawReview.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Set;

/**
 * 图纸复核-清单挂接wbs
 * @author wk
 * @date 2023-08-07 11:35:16
 * @remark xmsl_draw_review_relation
 */
@Data
public class XmslDrawReviewRelation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：mainId,xmsl_draw_review.id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "mainId,xmsl_draw_review.id")
    private Long mainId;
    /**
     * 字段描述：wbsId,xmsl_draw_review_wbs.wbs_id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "wbsId,xmsl_draw_review_wbs.wbs_id")
    private Long wbsId;
    /**
     * 字段描述：wbsId,xmsl_draw_review_wbs.wbs_Code
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty
    @Excel(name = "wbsCode,xmsl_draw_review_wbs.wbs_id")
    private String wbsCode;
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

    private Integer version;

    private Set<String> codeSet;
    private Set<String> wbsCodeSet;

    @JsonIgnore
    public Long getMainId() {
        return mainId;
    }

    @JsonIgnore
    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    @JsonIgnore
    public Long getWbsId() {
        return wbsId;
    }

    @JsonIgnore
    public void setWbsId(Long wbsId) {
        this.wbsId = wbsId;
    }

    @JsonIgnore
    public String getListCode() {
        return listCode;
    }

    @JsonIgnore
    public void setListCode(String listCode) {
        this.listCode = listCode;
    }

    @JsonIgnore
    public Long getListId() {
        return listId;
    }

    @JsonIgnore
    public void setListId(Long listId) {
        this.listId = listId;
    }
}
