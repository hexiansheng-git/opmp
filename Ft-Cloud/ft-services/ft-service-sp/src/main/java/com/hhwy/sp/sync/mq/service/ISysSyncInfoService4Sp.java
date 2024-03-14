package com.hhwy.sp.sync.mq.service;

import com.hhwy.sp.experiment.sgjsExperProgressManage.domain.SgjsExperProgressManageVo;
import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;
import com.hhwy.sp.techTrain.domain.SgjsTechnicalTraining;

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

    /**
     * 推送试验进度计划管理
     * @param sgjsExperProgressManageVo
     */
    public void pushSgjsExperProgressManage(SgjsExperProgressManageVo sgjsExperProgressManageVo);


    /**
     * 推送技术培训管理
     * @param sgjsTechnicalTraining
     */
    public void pushSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining);


}
