package com.hhwy.pm.qqch.wzch.source.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * @author HCT
 */
@Data
public class ReminderOfChangeResponse {
    private Long projectId;
    private String projectName;
    private String demandNewVersion;
    @JsonFormat(pattern = "YYYY年MM月dd日" , timezone = "GMT+8")
    private Date demandValidDate;

}
