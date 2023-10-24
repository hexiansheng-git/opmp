package com.hhwy.pm.qqch.preparation.sbch.equAllot.domain;

import lombok.Data;

import java.util.List;

/**
 * 功能：现场设备接口返回实体
 * 作者: fushudong
 * 时间: 2023/10/24
 */
@Data
public class ActiveEquResult<T> {
    private int code;
    private String msg;
    private int total;
    private List<T> rows;
}