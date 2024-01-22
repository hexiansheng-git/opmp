package com.hhwy.sd.designDocumentApproval.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApproval;
import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApprovalVo;

import java.util.List;

/**
 * @author wll
 * @date 2024-01-19 17:39:21
 * @remark
 */
public interface IKcsjDesignDocumentApprovalService {

    KcsjDesignDocumentApproval getKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    List<KcsjDesignDocumentApproval> getKcsjDesignDocumentApprovalList(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int insertKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    AjaxResult saveKcsjDesignDocumentApprovalList(KcsjDesignDocumentApprovalVo kcsjDesignDocumentApprovalListVo);

    int insertKcsjDesignDocumentApprovalList(List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList);

    int updateKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int updateKcsjDesignDocumentApprovalList(List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList);

    int deleteKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int deleteKcsjDesignDocumentApprovalByPks(List<Long> kcsjDesignDocumentApprovalPkList);
}
