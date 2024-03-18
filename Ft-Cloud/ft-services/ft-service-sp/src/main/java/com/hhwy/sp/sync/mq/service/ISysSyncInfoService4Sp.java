package com.hhwy.sp.sync.mq.service;

import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.vo.CriticalExpReportVo;
import com.hhwy.sp.experiment.sgjsExperProgressManage.domain.SgjsExperProgressManageVo;
import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManageVo;
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

    /**
     * 推送测量计划进度管理
     * @param vo
     */
    public void pushSgjsPlanMeasureManage(SgjsPlanMeasureManageVo vo);

    /**
     * 推送关键试验报告
     * @param vo
     */
    public void pushSgjsCriticalExpReport(CriticalExpReportVo vo);
}
