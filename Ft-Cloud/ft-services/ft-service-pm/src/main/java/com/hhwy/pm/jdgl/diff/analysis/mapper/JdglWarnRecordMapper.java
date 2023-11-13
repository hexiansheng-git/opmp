package com.hhwy.pm.jdgl.diff.analysis.mapper;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglWarnRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-09-01 13:29:05
 * @remark
 */
@Repository
public interface JdglWarnRecordMapper {

    JdglWarnRecord getJdglWarnRecord(JdglWarnRecord jdglWarnRecord);

    List<JdglWarnRecord> getJdglWarnRecordList(JdglWarnRecord jdglWarnRecord);

    int insertJdglWarnRecord(JdglWarnRecord jdglWarnRecord);

    int insertJdglWarnRecordList(@Param("jdglWarnRecordList") List<JdglWarnRecord> jdglWarnRecordList);

    int updateJdglWarnRecord(JdglWarnRecord jdglWarnRecord);

    int updateJdglWarnRecordList(@Param("list") List<JdglWarnRecord> jdglWarnRecordList);

    int deleteJdglWarnRecord(JdglWarnRecord jdglWarnRecord);

    int deleteJdglWarnRecordByPks(@Param("jdglWarnRecordPkList") List<Long> jdglWarnRecordPkList);
}
