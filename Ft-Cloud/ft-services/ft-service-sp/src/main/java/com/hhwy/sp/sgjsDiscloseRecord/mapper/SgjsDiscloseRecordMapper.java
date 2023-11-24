package com.hhwy.sp.sgjsDiscloseRecord.mapper;

import java.util.List;

import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;
import org.apache.ibatis.annotations.Param;

/**
 * @author cjh
 * @date 2023-11-23 14:47:22
 * @remark
 */
public interface SgjsDiscloseRecordMapper {

    SgjsDiscloseRecord getSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    List<SgjsDiscloseRecord> getSgjsDiscloseRecordList(SgjsDiscloseRecord sgjsDiscloseRecord);

    int insertSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    int insertSgjsDiscloseRecordList(@Param("sgjsDiscloseRecordList") List<SgjsDiscloseRecord> sgjsDiscloseRecordList);

    int updateSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    int updateSgjsDiscloseRecordList(@Param("list") List<SgjsDiscloseRecord> sgjsDiscloseRecordList);

    int deleteSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord);

    int deleteSgjsDiscloseRecordByPks(@Param("sgjsDiscloseRecordPkList") List<Long> sgjsDiscloseRecordPkList);

    List<SgjsDiscloseRecord> getSgjsDiscloseRecordListByNames(@Param("list") List<String> discloseNames);

    List<SgjsDiscloseRecord> getSgjsDiscloseRecordListByIds(@Param("list") List<Long> sgjsDiscloseRecordPkList);
}
