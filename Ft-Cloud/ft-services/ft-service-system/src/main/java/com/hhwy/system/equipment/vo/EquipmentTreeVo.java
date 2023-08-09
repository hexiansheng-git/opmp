package com.hhwy.system.equipment.vo;

import lombok.Data;

import java.util.List;

/**
 *设备分类树形
 *
 * @author lcf
 */
@Data
public class EquipmentTreeVo {
    private String id;
    private String pId;
    private String label;
    private String name;
    private String interLabel;//国际化标签名称
    private List<EquipmentTreeVo> children;
    private boolean isOpen = false;
    private String orderNum;
    private String type;
    private String code;
    private String pCode;
}
