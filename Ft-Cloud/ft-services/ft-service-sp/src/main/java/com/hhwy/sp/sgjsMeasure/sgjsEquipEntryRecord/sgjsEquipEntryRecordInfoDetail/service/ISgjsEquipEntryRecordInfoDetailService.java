package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.service;

import java.util.List;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfoDetail.domain.SgjsEquipEntryRecordInfoDetail;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:49:49
 * @remark 
 */
public interface ISgjsEquipEntryRecordInfoDetailService {
                                                                                                                                                                                                                                                                                        
    SgjsEquipEntryRecordInfoDetail getSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail);

    List<SgjsEquipEntryRecordInfoDetail> getSgjsEquipEntryRecordInfoDetailList(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail);

    int insertSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail);

    int insertSgjsEquipEntryRecordInfoDetailList(List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailList);

    int updateSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail);

            int updateSgjsEquipEntryRecordInfoDetailList(List<SgjsEquipEntryRecordInfoDetail> sgjsEquipEntryRecordInfoDetailList);
    
    int deleteSgjsEquipEntryRecordInfoDetail(SgjsEquipEntryRecordInfoDetail sgjsEquipEntryRecordInfoDetail);

            int deleteSgjsEquipEntryRecordInfoDetailByPks(List<Long> sgjsEquipEntryRecordInfoDetailPkList);
    }
