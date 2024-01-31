package com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author han
 * @date 2024-01-25 11:01:25
 * @remark sgjs_patent_declare
 */
@Data
public class PatentDeclareQueryVo {

    /**
     * 字段描述：专利名称
     */
    private String patentName;

    /**
     * 字段描述：专利号
     */
    private String patentNumber;

    /**
     * 字段描述：当前状态（未发起，审批中，申请通过，申请不通过）
     */
    private String currentState;

    private List<Long> ids;
}
