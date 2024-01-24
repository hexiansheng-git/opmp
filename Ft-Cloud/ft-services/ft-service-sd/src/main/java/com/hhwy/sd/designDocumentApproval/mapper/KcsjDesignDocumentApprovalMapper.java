package com.hhwy.sd.designDocumentApproval.mapper;

import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApproval;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wll
 * @date 2024-01-19 17:39:21
 * @remark 勘察设计-设计文件报批
 */
public interface KcsjDesignDocumentApprovalMapper {

    KcsjDesignDocumentApproval getKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    List<KcsjDesignDocumentApproval> getKcsjDesignDocumentApprovalList(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int insertKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int insertKcsjDesignDocumentApprovalList(@Param("kcsjDesignDocumentApprovalList") List<KcsjDesignDocumentApproval> kcsjDesignDocumentApprovalList);

    int updateKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int updateKcsjDesignDocumentApprovalList(@Param("list") List<KcsjDesignDocumentApproval> list);

    int deleteKcsjDesignDocumentApproval(KcsjDesignDocumentApproval kcsjDesignDocumentApproval);

    int deleteKcsjDesignDocumentApprovalByIdPks(@Param("kcsjDesignDocumentApprovalPkList") List<Long> kcsjDesignDocumentApprovalPkList, @Param("delUser") String delUser);

    int deleteKcsjDesignDocumentApprovalByPks(@Param("kcsjDesignDocumentApprovalPkList") List<Long> kcsjDesignDocumentApprovalPkList);

    /**
     * 查询下次跟进日期不为空
     * 且反馈日期和反馈内容不为空的数据
     *
     * @author lcf
     * @date 2024-01-22
     * @return
     */
    List<KcsjDesignDocumentApproval> selectByFollowUpDate();



}
