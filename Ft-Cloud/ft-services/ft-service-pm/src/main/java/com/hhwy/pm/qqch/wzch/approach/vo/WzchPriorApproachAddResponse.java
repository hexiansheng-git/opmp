package com.hhwy.pm.qqch.wzch.approach.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HCT
 */
@Data
public class WzchPriorApproachAddResponse implements Serializable {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    private String createUserName;


}
