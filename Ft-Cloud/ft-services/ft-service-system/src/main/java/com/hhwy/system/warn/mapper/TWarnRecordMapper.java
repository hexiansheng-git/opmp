package com.hhwy.system.warn.mapper;

import com.hhwy.system.warn.domain.TWarnRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-09-26 10:33:21
 * @remark
 */
@Repository
public interface TWarnRecordMapper {

    TWarnRecord getTWarnRecord(TWarnRecord tWarnRecord);

    List<TWarnRecord> getTWarnRecordList(TWarnRecord tWarnRecord);

    int insertTWarnRecord(TWarnRecord tWarnRecord);

    int insertTWarnRecordList(@Param("tWarnRecordList") List<TWarnRecord> tWarnRecordList);

    int updateTWarnRecord(TWarnRecord tWarnRecord);

    int updateTWarnRecordList(@Param("recordIdList") List<TWarnRecord> tWarnRecordList);

    int deleteTWarnRecord(TWarnRecord tWarnRecord);

    int deleteTWarnRecordByPks(@Param("tWarnRecordPkList") List<Long> tWarnRecordPkList);
}
