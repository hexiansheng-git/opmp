package com.hhwy.domain.base.system.material;

import java.io.Serializable;
import java.util.List;

public class CascaderMaterialCategoryVo implements Serializable {


    private String id;
    private String pid;
    private String value;
    private String label;

    private List<CascaderMaterialCategoryVo> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<CascaderMaterialCategoryVo> getChildren() {
        return children;
    }

    public void setChildren(List<CascaderMaterialCategoryVo> children) {
        this.children = children;
    }
}
