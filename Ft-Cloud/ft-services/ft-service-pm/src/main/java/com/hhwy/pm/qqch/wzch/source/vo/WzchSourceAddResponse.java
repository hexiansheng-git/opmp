package com.hhwy.pm.qqch.wzch.source.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增按钮新增按钮响应对象
 * @author HCT
 */
@Data
public class WzchSourceAddResponse implements Serializable {

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    private String createUserName;
}
