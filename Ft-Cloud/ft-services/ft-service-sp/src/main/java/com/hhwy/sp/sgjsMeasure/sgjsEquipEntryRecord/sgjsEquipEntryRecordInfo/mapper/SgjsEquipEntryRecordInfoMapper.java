package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:49:36
 * @remark
 */
public interface SgjsEquipEntryRecordInfoMapper {

    SgjsEquipEntryRecordInfo getSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo);

    List<SgjsEquipEntryRecordInfo> getSgjsEquipEntryRecordInfoList(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo);

    int insertSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo);

    int insertSgjsEquipEntryRecordInfoList(@Param("sgjsEquipEntryRecordInfoList") List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoList);

    int updateSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo);

    int updateSgjsEquipEntryRecordInfoList(@Param("sgjsEquipEntryRecordInfoList") List<SgjsEquipEntryRecordInfo> sgjsEquipEntryRecordInfoList);

    int deleteSgjsEquipEntryRecordInfo(SgjsEquipEntryRecordInfo sgjsEquipEntryRecordInfo);

    int deleteSgjsEquipEntryRecordInfoByPks(@Param("sgjsEquipEntryRecordInfoPkList") List<Long> sgjsEquipEntryRecordInfoPkList);
}
