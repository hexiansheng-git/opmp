package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.service;

import java.util.List;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:49:36
 * @remark
 */
public interface ISgjsEquipEntryRecordInfoService {

    SgjsEquipEntryRecordInfo getSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo);

    List<SgjsEquipEntryRecordInfo> getSgjsEquipEntryRecordInfoList(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo);

    int insertSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo);

    int insertSgjsEquipEntryRecordInfoList(List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoList);

    int updateSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo);

    int updateSgjsEquipEntryRecordInfoList(List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoList);

    int deleteSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo);

    int deleteSgjsEquipEntryRecordInfoByPks(List<Long> sgjsEquipEntryRecordInfoPkList);
}
