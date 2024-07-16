package com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecord.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.sp.sgjsMeasure.sgjsEquipEntryRecord.sgjsEquipEntryRecordInfo.domain.SgjsEquipEntryRecordInfo;

import java.util.Date;
import java.util.List;

/**
 * @author lcf   测量管理--测试设备进场记录
 * @date 2023-12-08 10:47:00
 * @remark   sgjs_equip_entry_record
 */
public class SgjsEquipEntryRecordVo extends BaseEntity {
    private List<Long> delIdList;

    private List<SgjsEquipEntryRecord> list;

    public List<Long> getDelIdList() {
        return delIdList;
    }

    public void setDelIdList(List<Long> delIdList) {
        this.delIdList = delIdList;
    }

    public List<SgjsEquipEntryRecord> getList() {
        return list;
    }

    public void setList(List<SgjsEquipEntryRecord> list) {
        this.list = list;
    }
}
