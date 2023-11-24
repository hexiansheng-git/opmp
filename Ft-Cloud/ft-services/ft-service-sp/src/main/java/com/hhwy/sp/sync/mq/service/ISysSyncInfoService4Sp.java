package com.hhwy.sp.sync.mq.service;

import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;

import java.util.List;

/**
 * 数据同步节点记录Service接口
 * 
 * @author XXX
 * @date 2023-09-04
 */
public interface ISysSyncInfoService4Sp {
    /**
     * 推送交底记录管理
     */
    public void pushSgjsDiscloseRecord(List<SgjsDiscloseRecord> List);
}
