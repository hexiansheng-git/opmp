package com.hhwy.sp.sgjsDiscloseRecord.service;

import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;

import java.util.List;

/**
 * @author cjh
 * @date 2023-11-23 14:47:22
 * @remark
 */
public interface ISgjsDiscloseRecordService {

    SgjsDiscloseRecord getSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    List<SgjsDiscloseRecord> getSgjsDiscloseRecordList(SgjsDiscloseRecord sgjsDiscloseRecord);

    int insertSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    int insertSgjsDiscloseRecordList(List<SgjsDiscloseRecord> sgjsDiscloseRecordList);

    int updateSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    int updateSgjsDiscloseRecordList(List<SgjsDiscloseRecord> sgjsDiscloseRecordList);

    int deleteSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    int deleteSgjsDiscloseRecordByPks(List<Long> sgjsDiscloseRecordPkList);

    int importData(List<SgjsDiscloseRecord> sgjsDiscloseRecordList, String dataType);
}
