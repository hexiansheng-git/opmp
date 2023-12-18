package com.hhwy.sd.disclosureRecord.domain.vo;

import com.hhwy.sd.disclosureRecord.domain.KcsjDisclosureRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-12-18 11:15:48
 * @remark kcsj_disclosure_record
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisclosureRecordVo {

    private List<Long> delIdList;

    private List<KcsjDisclosureRecord> recordList;
}
