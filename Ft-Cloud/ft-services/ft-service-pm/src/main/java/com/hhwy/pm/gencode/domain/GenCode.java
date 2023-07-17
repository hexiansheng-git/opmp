package com.hhwy.pm.gencode.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName gen_code
 */
@Data
@ToString
public class GenCode implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 中间字符串
     */
    private String middle;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 创建时间，默认为当前时间
     */
    private Date createTime;
    /**
     * 创建日期
     */

    private Date createDate;
    /**
     * 创建人id
     */
    private String createBy;

    /**
     * 数据修改时间，默认为当前时间
     */
    private Date updateTime;

    /**
     * 修改人id
     */
    private String updateBy;

    /**
     * 删除人id
     */
    private String deleteBy;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 删除标记，0 未删除 1 已删除 不可为空
     */
    private String deleteFlag;

    private static final long serialVersionUID = 1L;

}