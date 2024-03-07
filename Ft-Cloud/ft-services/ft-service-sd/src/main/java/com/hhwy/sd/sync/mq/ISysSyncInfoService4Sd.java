package com.hhwy.sd.sync.mq;

import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApproval;
import com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.domain.KcsjPlanCommunicationRecords;

import java.util.List;

/**
 * @author wll
 * 2024/3/6
 */
public interface ISysSyncInfoService4Sd  {


    /**
     * 推送往来沟通记录
     */
    public void pushKcsjPlanCommunicationRecords(List<KcsjPlanCommunicationRecords> list);

    /**
     * 推送设计文件报批
     */
    public void pushKcsjDesignDocumentApproval(List<KcsjDesignDocumentApproval> list);

}
