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
    XX("XX", "yyyyMMdd", 3),


    /*总需计划*/
    EQU_TOTAL_PLAN("SBCHZXJH", "yyyy", 3),
    /*设备申购*/
    EQU_PERCHASE("SBSG", "yyyy", 3),
    /*设备进口调查*/
    EQU_IMPORT_INQUIRY("SBCHJKDC","yyyy",3),
    /*设备进口方案*/
    EQU_IMPORT_PLAN("SBCHJKFA","yyyy",3),
    /*设备属地化采购*/
    EQU_LOCALPERCHASE("SDHCG", "yyyy", 3),
    /*清关档案*/
    EQU_CUSTOMS_CLEAR("SBCHQGDA","yyyy",3),
    /*大型设备运输方案*/
    EQU_MATERIAL_TRAN("SBCHYSFA","yyyy",3),
    /*设备爱现场管理策划*/
    EQU_MATERIAL_MANAGER("SBCHXCGL","yyyy",3),
    /*同国别设备调拨*/
    EQU_SAMEALLO("TGBTB", "yyyy", 3),
    /*异国别设备调拨*/
    EQU_CROSSALLO("YGBTB", "yyyy", 3),
    /*单机核算策划*/
    EQU_SINGLE_CHECK("SBCHDJHS","yyyy",3),
    /*设备租赁供应商*/
    EQU_SUPPLIER("ZLGYS", "yyyy", 3),
    /*租赁设备*/
    EQU_LEASE("ZLSB", "yyyy", 3),
    /*设备策划--设备人员配置*/
    EQU_STAFFING("SBCGRYPZ","yyyy",3),
    /*设备策划--设备人员配置-特殊人员配置*/
    EQU_STAFF_SPECIAL("SBCHTSRYPZ","yyyy",3),
    /*协作单位设备*/
    EQU_XZDWSB("XZDWSB", "yyyy", 3),
    /*特种设备管理*/
    EQU_SPECIAL("TZSB", "yyyy", 3),
    /*特种设备风险识别和措施策划对象管理*/
    EQU_SPECIALPLAN("TZSBJH", "yyyy", 3),
    /*特种设备管控策划管理*/
    EQU_SPECIALCONTROLPLAN("TZSBGKJH", "yyyy", 3),


    /**物资总需*/
    EQU_TOTAL_DEMAND("WZZX","yyyyMMdd",3),
    /**来源策划*/
    EQU_SOURCE("LUCH","yyyyMMdd",3),
    /**进出口调查*/
    EQU_SURVEY("JCKDC","yyyyMMdd",3),
    /**进出口策划*/
    EQU_IMPORT_EXPORT_PLAN("JCKCH","yyyyMMdd",3),
    /**当地运输方案策划*/
    EQU_LOCAL_TRANSPORT_PLAN("DDYSFACH","yyyyMMdd",3),
    /**专项物资发运策划*/
    EQU_SPECICAL_MATERIAL_PLAN("ZXWZFYCH","yyyyMMdd",3),

    /**
     * 优先进场物资设备采购策划编码规则 YXJCWSBZCG20221123001
     */
    WPP("YXJCWSBZCG", "yyyyMMdd", 3),
    /*采购供应策划表*/
    WPS("CGGYCH", "yyyyMMdd", 3),
    /*周转材租赁策划*/
    WRR("ZZCZL", "yyyyMMdd", 3),
    /*内部调剂材料策划*/
    WIA("NBTJ", "yyyyMMdd", 3),
    /*属地采购供应策划表*/
    WLPS("SDCGGYCH", "yyyyMMdd", 3),
    /*资金策划*/
    WF("ZJCH", "yyyyMMdd", 3),
    /*专项物资策划*/
    WSP("ZXWZCH", "yyyyMMdd", 3);
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
