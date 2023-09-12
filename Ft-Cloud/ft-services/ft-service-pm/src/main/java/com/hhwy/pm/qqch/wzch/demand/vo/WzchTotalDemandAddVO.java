package com.hhwy.pm.qqch.wzch.demand.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author HCT
 */
@Data
public class WzchTotalDemandAddVO extends CommonBaseEntity {

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
