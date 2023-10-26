package com.hhwy.system.warn.mapper;

import com.hhwy.domain.base.system.warn.TWarnRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-09-26 17:52:28
 * @remark
 */
@Repository
public interface TWarnRecordMapper {

    TWarnRecord getTWarnRecord(TWarnRecord tWarnRecord);

    List<TWarnRecord> getTWarnRecordList(TWarnRecord tWarnRecord);

    int insertTWarnRecord(TWarnRecord tWarnRecord);

    int insertTWarnRecordList(@Param("tWarnRecordList") List<TWarnRecord> tWarnRecordList);

    int updateTWarnRecord(TWarnRecord tWarnRecord);

    int updateTWarnRecordList(@Param("tWarnRecordList") List<TWarnRecord> tWarnRecordList);

    int deleteTWarnRecord(TWarnRecord tWarnRecord);

    int deleteTWarnRecordByPks(@Param("tWarnRecordPkList") List<Long> tWarnRecordPkList);

    TWarnRecord getWarnRecordByWarnIdAndWarnUser(@Param("warnId") Long warnId, @Param("userName") String userName);

    int changeStatus(TWarnRecord record);
}
