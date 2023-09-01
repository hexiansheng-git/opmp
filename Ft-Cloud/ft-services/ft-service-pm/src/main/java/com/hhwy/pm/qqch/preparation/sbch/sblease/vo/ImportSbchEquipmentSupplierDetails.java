package com.hhwy.pm.qqch.preparation.sbch.sblease.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.Data;

/**
 * 设备租赁供应商详情对象 sbch_equipment_supplier_details
 * 
 * @author hwj
 * @date 2022-11-27
 */
@Data
public class ImportSbchEquipmentSupplierDetails extends CommonBaseEntity {
    private static final long serialVersionUID = 1L;
    @Excel(name = "序号")
    private String xh;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplier;

    /** 主要设备 */
    @Excel(name = "主要设备")
    private String mainEquipment;

    /** 国家id */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long countryId;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 公司注册地 */
    @Excel(name = "公司注册地点")
    private String companyAddr;

    /** 联系人 */
    @Excel(name = "联系人")
    private String linkman;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String mobile;
    /** 联系电话 */
    @Excel(name = "备注")
    private String remark;
}
