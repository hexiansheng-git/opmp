package com.hhwy.sd.sync.mq;

import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApproval;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBill;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsList;
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


    /**
     * 推送工程量清单
     */
    public void pushKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill);

    /**
     * 推送主材清单
     * @param kcsjMaterialsList
     */
    public void pushKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList);
}
