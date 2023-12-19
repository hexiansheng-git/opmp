package com.hhwy.sd.disclosureRecord.mapper;

import com.hhwy.sd.disclosureRecord.domain.KcsjDisclosureRecord;
import com.hhwy.sd.disclosureRecord.domain.vo.DisclosureRecordQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-12-18 11:15:48
 * @remark
 */
@Repository
public interface KcsjDisclosureRecordMapper {

    KcsjDisclosureRecord getKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    List<KcsjDisclosureRecord> getKcsjDisclosureRecordList(DisclosureRecordQueryVo queryVo);

    int insertKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    int insertKcsjDisclosureRecordList(@Param("kcsjDisclosureRecordList") List<KcsjDisclosureRecord> kcsjDisclosureRecordList);

    int updateKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    int updateKcsjDisclosureRecordList(@Param("list") List<KcsjDisclosureRecord> kcsjDisclosureRecordList);

    int deleteKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord);

    int deleteKcsjDisclosureRecordByPks(@Param("kcsjDisclosureRecordPkList") List<Long> kcsjDisclosureRecordPkList);

    List<KcsjDisclosureRecord> getListByIds(@Param("ids") List<Long> ids);

    void deleteAll();
}
