package com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.domain.KcsjPlanCommunicationRecords;

import java.util.List;

/**
 * @author wll
 * @date 2023-12-15 10:37:09
 * @remark
 */
public interface IKcsjPlanCommunicationRecordsService {

    KcsjPlanCommunicationRecords getKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords);

    List<KcsjPlanCommunicationRecords> getKcsjPlanCommunicationRecordsList(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords);

    int insertKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords);

    AjaxResult insertKcsjPlanCommunicationRecordsList(List<KcsjPlanCommunicationRecords> kcsjPlanCommunicationRecordsList);

    int updateKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords);

    int updateKcsjPlanCommunicationRecordsList(List<KcsjPlanCommunicationRecords> kcsjPlanCommunicationRecordsList);

    int deleteKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords);

    int deleteKcsjPlanCommunicationRecordsByPks(List<Long> kcsjPlanCommunicationRecordsPkList);
}
