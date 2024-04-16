package com.hhwy.sp.sgjsDiscloseRecord.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;
import lombok.Data;

import java.util.List;

/**
 * @author cjh
 * @date 2023-11-23 14:47:22
 * @remark sgjs_disclose_record
 */
@Data
public class DiscloseRecordPushVo {

    /**
     * 数据分类:oneOrTwo、three
     */
    @JsonProperty
    private String dataType;

    @JsonProperty
    private String projectCode;

    @JsonProperty
    private List<SgjsDiscloseRecord> recordList;
}
