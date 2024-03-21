package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.service;

import java.util.List;
import java.util.Map;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain.SgjsEquipEntryRecord;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:47:00
 * @remark
 */
public interface ISgjsEquipEntryRecordService {

    SgjsEquipEntryRecord getSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    List<SgjsEquipEntryRecord> getSgjsEquipEntryRecordList(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int insertSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int insertSgjsEquipEntryRecordList(List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList);

    int updateSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int updateSgjsEquipEntryRecordList(List<SgjsEquipEntryRecord> sgjsEquipEntryRecordList);

    int deleteSgjsEquipEntryRecord(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    int deleteSgjsEquipEntryRecordByPks(List<Long> sgjsEquipEntryRecordPkList);

    /**
     * 同步3.6.4
     *
     * @return
     */
    AjaxResult sync();

    /**
     * 查询
     *
     * @param sgjsEquipEntryRecord
     * @return
     */
    List<SgjsEquipEntryRecord> selectList(SgjsEquipEntryRecord sgjsEquipEntryRecord);

    /**
     * 手动同步物设进场设备记录
     *
     * @param map
     * @return
     */
    List<SgjsEquipEntryRecordInfo> getDatatByOther(List<Map> map);
}
