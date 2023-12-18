package com.hhwy.sd.disclosureRecord.mapper;

import com.hhwy.sd.disclosureRecord.domain.KcsjDisclosureRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author han
 * @date 2023-12-18 11:15:48
 * @remark
 */
public interface KcsjDisclosureRecordMapper {

    KcsjDisclosureRecord getKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    List<KcsjDisclosureRecord> getKcsjDisclosureRecordList(KcsjDisclosureRecord kcsjDisclosureRecord);

    int insertKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    int insertKcsjDisclosureRecordList(@Param("kcsjDisclosureRecordList") List<KcsjDisclosureRecord> kcsjDisclosureRecordList);

    int updateKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    int updateKcsjDisclosureRecordList(@Param("list") List<KcsjDisclosureRecord> kcsjDisclosureRecordList);

    int deleteKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    int deleteKcsjDisclosureRecordByPks(@Param("kcsjDisclosureRecordPkList") List<Long> kcsjDisclosureRecordPkList);
}
