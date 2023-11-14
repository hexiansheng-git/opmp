package com.hhwy.pm.jdgl.diff.analysis.service;


import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglWarnRecord;

import java.util.List;

/**
 * @author han
 * @date 2023-09-01 13:29:05
 * @remark
 */
public interface IJdglWarnRecordService {

    JdglWarnRecord getJdglWarnRecord(JdglWarnRecord jdglWarnRecord);

    List<JdglWarnRecord> getJdglWarnRecordList(JdglWarnRecord jdglWarnRecord);

    int insertJdglWarnRecord(JdglWarnRecord jdglWarnRecord);

    int insertJdglWarnRecordList(List<JdglWarnRecord> jdglWarnRecordList);

    int updateJdglWarnRecord(JdglWarnRecord jdglWarnRecord);

    int updateJdglWarnRecordList(List<JdglWarnRecord> jdglWarnRecordList);

    int deleteJdglWarnRecord(JdglWarnRecord jdglWarnRecord);

    int deleteJdglWarnRecordByPks(List<Long> jdglWarnRecordPkList);
}
