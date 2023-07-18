package com.hhwy.domain.base.system;

import lombok.Data;

import java.util.List;

@Data
public class TreeUtil {
    private String id;
    private String pId;
    private String label;
    private String name;
    private String interLabel;//国际化标签名称
    private List<TreeUtil> children;
    private boolean isOpen = false;

    private String deptType;
    private String deptCode;
    private String orderNum;
    private String type;
}
