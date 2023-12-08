package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.domain.SgjsEquipEntryRecordInfoDetail;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:49:49
 * @remark
 */
public interface SgjsEquipEntryRecordInfoDetailMapper {

    SgjsEquipEntryRecordInfoDetail getSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail);

    List<SgjsEquipEntryRecordInfoDetail> getSgjsEquipEntryRecordInfoDetailList(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail);

    int insertSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail);

    int insertSgjsEquipEntryRecordInfoDetailList(@Param("sgjsEquipEntryRecordInfoDetailList") List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailList);

    int updateSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail);

    int updateSgjsEquipEntryRecordInfoDetailList(@Param("sgjsEquipEntryRecordInfoDetailList") List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailList);

    int deleteSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail);

    int deleteSgjsEquipEntryRecordInfoDetailByPks(@Param("sgjsEquipEntryRecordInfoDetailPkList") List<Long> sgjsEquipEntryRecordInfoDetailPkList);
}
