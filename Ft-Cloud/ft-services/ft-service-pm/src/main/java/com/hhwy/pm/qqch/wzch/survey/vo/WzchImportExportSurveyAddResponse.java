package com.hhwy.pm.qqch.wzch.survey.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class WzchImportExportSurveyAddResponse {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    private String createUserName;
}
