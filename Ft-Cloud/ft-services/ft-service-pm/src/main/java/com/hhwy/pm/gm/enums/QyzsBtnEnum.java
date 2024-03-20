package com.hhwy.pm.gm.enums;

/**
 *  知识库按钮enum 
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2024/3/19 17:02   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2024/3/19 17:02    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
public enum QyzsBtnEnum {
    /**
     * 勘察设计-制度及管理方法库-0#工程量清单、材料清单管理方法 
     */
    SYSTEMMAN_LISTMETHOD("list_mater_manager"),
    
    /**
     * 勘察设计-文件模板库-周报模板 
     */
    FILEMODE_WEEK("week_template"),
    /**
     * 勘察设计-文件模板库-月报模板 
     */
    FILEMODE_MONTH("month_template"),
    
    
    /**
     * 施工技术知识库-制度及管理方法库-施工方案评分表 
     */
    CON_SCORE("construction_score"),
    /**
     * 施工技术知识库-制度及管理方法库-海外施工方案管理实施细则 
     */
    CON_METHOD("out_construction_method"),
    /**
     * 施工技术知识库-制度及管理方法库-交底制度 
     */
    CON_DISCLOSE("disclose"),
    /**
     * 施工技术知识库-制度及管理方法库-档案管理制度 
     */
    CON_DOC("doc_manager"),

    /**
     * 施工技术知识库-文件模板库-施工方案编制模板 
     */
    CONFILEMODE_TEMP("construction_template"),
    /**
     * 施工技术知识库-文件模板库-施工技术交底样表 
     */
    CONFILEMODE_DISCLOSE_TABLE("construction_table"),
    /**
     * 施工技术知识库-文件模板库-施工技术交底模板 
     */
    CONFILEMODE_DISCLOSE("construction_disclose"),
    /**
     * 施工技术知识库-文件模板库-测量报告模板 
     */
    CONFILEMODE_TEST("test_report"),
    
    ;
    
    
    private final String key;

    QyzsBtnEnum(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
