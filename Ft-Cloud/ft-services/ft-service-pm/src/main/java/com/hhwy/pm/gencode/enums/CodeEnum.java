package com.hhwy.pm.gencode.enums;

/**
 * 生成编码 Enum prefix:编码前缀 timeFormat:编码时间格式 digit: 流水号位数
 *
 * @author mls
 */

public enum CodeEnum {

    /***************************************************************************前期策划**********************************************************、
     /**
     *  前期策划工作计划
     */
    BASE_LIMIT("QQCH", "yyyyMMdd", 3),

    /**
     * 前期策划编制-3.4.2施工方案清单
     */
    QQCH_CONSTRUCTION_LIST("SGFAQD", null, 3),

    /**
     * 示例 XX20221123001
     */
    XX("XX", "yyyyMMdd", 3);


    /**
     * 前缀
     */
    private final String prefix;
    /**
     * 时间格式
     */
    private final String timeFormat;
    /**
     * 流水号位数
     */
    private final int digit;

    CodeEnum(String prefix, String timeFormat, int digit) {
        this.prefix = prefix;
        this.timeFormat = timeFormat;
        this.digit = digit;
    }

    /**
     * 获取前缀
     *
     * @return
     */

    public String prefix() {
        return this.prefix;
    }

    /**
     * 获取时间格式化
     *
     * @return
     */
    public String timeFormat() {
        return this.timeFormat;
    }


    /**
     * 获取流水号位数
     *
     * @return
     */
    public int digit() {
        return this.digit;
    }

}
