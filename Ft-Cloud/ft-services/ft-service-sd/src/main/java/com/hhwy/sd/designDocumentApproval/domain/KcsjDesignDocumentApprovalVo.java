package com.hhwy.sd.designDocumentApproval.domain;

import lombok.Data;

import java.util.List;

/**
 * @author wll
 * 2024/1/22
 */
@Data
public class KcsjDesignDocumentApprovalVo {

    //新增数据
    private List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList;

    //删除id
    private List<String> delIdList;
}
