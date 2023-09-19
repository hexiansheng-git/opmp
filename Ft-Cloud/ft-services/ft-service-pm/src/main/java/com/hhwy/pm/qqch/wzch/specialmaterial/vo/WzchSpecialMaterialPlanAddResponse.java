package com.hhwy.pm.qqch.wzch.specialmaterial.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class WzchSpecialMaterialPlanAddResponse extends CommonBaseEntity {
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 版本号 */
    private String versionCode;
    private String versionCodeStr;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm"
    )
    private Date createTime;

    private String createUserName;
}
