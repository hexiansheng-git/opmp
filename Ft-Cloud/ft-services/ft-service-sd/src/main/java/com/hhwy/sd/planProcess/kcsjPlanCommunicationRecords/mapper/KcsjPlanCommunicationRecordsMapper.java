package com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.mapper;

import com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.domain.KcsjPlanCommunicationRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wll
 * @date 2023-12-15 10:37:09
 * @remark
 */
public interface KcsjPlanCommunicationRecordsMapper {

    KcsjPlanCommunicationRecords getKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords);

    List<KcsjPlanCommunicationRecords> getKcsjPlanCommunicationRecordsList(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords);

    int insertKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords);

    int insertKcsjPlanCommunicationRecordsList(@Param("kcsjPlanCommunicationRecordsList") List<KcsjPlanCommunicationRecords> kcsjPlanCommunicationRecordsList);

    int updateKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords);

    int updateKcsjPlanCommunicationRecordsList(@Param("kcsjPlanCommunicationRecordsList") List<KcsjPlanCommunicationRecords> kcsjPlanCommunicationRecordsList);

    int deleteKcsjPlanCommunicationRecords(KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords);

    int deleteKcsjPlanCommunicationRecordsByPks(@Param("kcsjPlanCommunicationRecordsPkList") List<Long> kcsjPlanCommunicationRecordsPkList,@Param("delUser") String delUser);

    List<KcsjPlanCommunicationRecords> getKcsjPlanCommunicationRecordsListByIds(@Param("list") List<Long> kcsjPlanCommunicationRecordsPkList);

}
