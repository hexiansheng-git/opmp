package com.hhwy.sd.disclosureRecord.service;

import com.hhwy.sd.disclosureRecord.domain.KcsjDisclosureRecord;

import java.util.List;


/**
 * @author han
 * @date 2023-12-18 11:15:48
 * @remark
 */
public interface IKcsjDisclosureRecordService {

    KcsjDisclosureRecord getKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    List<KcsjDisclosureRecord> getKcsjDisclosureRecordList(KcsjDisclosureRecord kcsjDisclosureRecord);

    int insertKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    int insertKcsjDisclosureRecordList(List<KcsjDisclosureRecord> kcsjDisclosureRecordList);

    int updateKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    int updateKcsjDisclosureRecordList(List<KcsjDisclosureRecord> kcsjDisclosureRecordList);

    int deleteKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    int deleteKcsjDisclosureRecordByPks(List<Long> kcsjDisclosureRecordPkList);
}
