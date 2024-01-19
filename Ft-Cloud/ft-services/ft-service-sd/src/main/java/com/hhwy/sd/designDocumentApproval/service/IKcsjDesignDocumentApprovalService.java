package com.hhwy.sd.designDocumentApproval.service;

import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApproval;

import java.util.List;

/**
 * @author wll
 * @date 2024-01-19 17:10:08
 * @remark
 */
public interface IKcsjDesignDocumentApprovalService {

    KcsjDesignDocumentApproval getKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    List<KcsjDesignDocumentApproval> getKcsjDesignDocumentApprovalList(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int insertKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int insertKcsjDesignDocumentApprovalList(List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList);

    int updateKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int updateKcsjDesignDocumentApprovalList(List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList);

    int deleteKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int deleteKcsjDesignDocumentApprovalByPks(List<Long> kcsjDesignDocumentApprovalPkList);
}
