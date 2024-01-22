package com.hhwy.sd.designDocumentApproval.mapper;

import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApproval;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wll
 * @date 2024-01-19 17:39:21
 * @remark
 */
public interface KcsjDesignDocumentApprovalMapper {

    KcsjDesignDocumentApproval getKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    List<KcsjDesignDocumentApproval> getKcsjDesignDocumentApprovalList(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int insertKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int insertKcsjDesignDocumentApprovalList(@Param("kcsjDesignDocumentApprovalList") List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList);

    int updateKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int updateKcsjDesignDocumentApprovalList(@Param("list") List<KcsjDesignDocumentApproval> list);

    int deleteKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int deleteKcsjDesignDocumentApprovalByIdPks(@Param("kcsjDesignDocumentApprovalPkList") List<Long> kcsjDesignDocumentApprovalPkList,@Param("delUser") String delUser);

    int deleteKcsjDesignDocumentApprovalByPks(@Param("kcsjDesignDocumentApprovalPkList") List<Long> kcsjDesignDocumentApprovalPkList);
}
