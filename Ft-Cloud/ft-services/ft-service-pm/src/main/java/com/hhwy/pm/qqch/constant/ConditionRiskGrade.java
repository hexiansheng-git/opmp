package com.hhwy.pm.qqch.constant;

/**
 * 有利性分析-风险等级
 */
public enum ConditionRiskGrade {

    HIGH("1", "高"),
    MIDDLE("2", "中"),
    LOW("3", "低");

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    private final String label;

    private final String value;

    ConditionRiskGrade(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
