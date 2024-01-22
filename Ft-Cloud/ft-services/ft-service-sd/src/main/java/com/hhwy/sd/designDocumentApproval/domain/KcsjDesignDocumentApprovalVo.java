package com.hhwy.sd.designDocumentApproval.domain;

import lombok.Data;

import java.util.List;

/**
 * @author wll
 * 2024/1/22
 */
@Data
public class KcsjDesignDocumentApprovalVo {

    //页面数据集合
    private List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList;

    //删除id
    private List<String> delIdList;
}
