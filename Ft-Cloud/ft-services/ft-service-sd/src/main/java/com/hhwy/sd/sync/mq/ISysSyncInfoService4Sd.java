package com.hhwy.sd.sync.mq;

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



}
