package com.hhwy.constant;

public enum WarnScopeType {
    ALL("1"),
    DEPT("2"),
    USER("3"),
    ROLE("4");

    public String getWarnScopeType() {
        return warnScopeType;
    }

    private final String warnScopeType;

    WarnScopeType(String warnScopeType) {
        this.warnScopeType = warnScopeType;
    }
}
