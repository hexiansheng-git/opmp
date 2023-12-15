package com.hhwy.sd.equipEntryRecord.domain;

import java.util.List;
import lombok.Data;

@Data
public class KcsjEquipEntryRecordVo {
    //新增数据
    private List<KcsjEquipEntryRecord> treeList;
    //删除Ids
    private List<String> delIdList;

}
