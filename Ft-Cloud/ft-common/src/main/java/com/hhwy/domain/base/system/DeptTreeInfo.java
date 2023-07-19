package com.hhwy.domain.base.system;

import lombok.Data;

import java.util.List;

/**
 * dept
 *
 * @author lcf
 */
@Data
public class DeptTreeInfo {
    private String id;
    private String pId;
    private String label;
    private String name;
    private String interLabel;//国际化标签名称
    private List<DeptTreeInfo> children;
    private boolean isOpen = false;
    private String deptType;
    private String deptTypeName;
    private String deptCode;
    private String orderNum;
    private String type;
    private String ancestors;

    private String ptVar1;
    private String ptVar2;

}
