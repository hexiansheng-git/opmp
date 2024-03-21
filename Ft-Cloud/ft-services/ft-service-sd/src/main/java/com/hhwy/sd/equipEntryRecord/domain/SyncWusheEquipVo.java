package com.hhwy.sd.equipEntryRecord.domain;

import lombok.Data;

/**
 * 勘察设计设备进场记录
 *
 * @author lcf
 * @data 2024-03-21
 */
@Data
public class SyncWusheEquipVo {
    //主表id
    private String recordId;
    //项目编码
    private String projectCode;
    //设备编码
    private String materialCode;
    //来源
    private String source;
    //班组名称
    private String teamName;

}
