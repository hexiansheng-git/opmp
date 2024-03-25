package com.hhwy.sd.sync.wushe.vo;

import lombok.Data;

/**
 * 设备信息vo
 *
 * @data 2023-12-15
 * @author lcf
 */
@Data
public class SyncMaterialInfoVo {
    private String code;//materialCode
    private String bottomNo;//底盘系列号
    private String type;//0：自有  1协作单位   2租赁
    private String spec;//规格型号
    private String mainNo;//主机系列号
    private String weight;//重量
    private String originalValue;//原值
    private String Managementcode;//设备管理编号
    private String productPower;//生产能力
    private String name;//名称
    private String mainPower;//生产能力
    private String mainModel;//主机型号
    private String netValue;//净值
    private String typtCode;//分类编码
    private String deviceFrom;//生产厂家
    private String checkDate;//验收日期
    private String source;//来源
    private String sizeMsg;//尺寸
    private String unit;//单位
    private String exitDate;//退场时间
    private String status;//设备状态
}
