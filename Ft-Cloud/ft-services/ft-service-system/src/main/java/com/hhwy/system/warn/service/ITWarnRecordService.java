package com.hhwy.system.warn.service;

import com.hhwy.domain.base.system.warn.TWarnRecord;

import java.util.List;


/**
 * @author han
 * @date 2023-09-26 17:52:28
 * @remark
 */
public interface ITWarnRecordService {

    TWarnRecord getTWarnRecord(TWarnRecord tWarnRecord);

    List<TWarnRecord> getTWarnRecordList(TWarnRecord tWarnRecord);

    int insertTWarnRecord(TWarnRecord tWarnRecord);

    int insertTWarnRecordList(List<TWarnRecord> tWarnRecordList);

    int updateTWarnRecord(TWarnRecord tWarnRecord);

    int updateTWarnRecordList(List<TWarnRecord> tWarnRecordList);

    int deleteTWarnRecord(TWarnRecord tWarnRecord);

    int deleteTWarnRecordByPks(List<Long> tWarnRecordPkList);

    List<TWarnRecord> getWranRecordReadInfo(String[] businessId);
}
