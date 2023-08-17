package com.hhwy.utils.excelUtil;

import com.hhwy.utils.excel.ExcelFunction;

/**
 * 下载模板枚举类
 *
 * @author zq
 * @date 2022年11月24日 20:00
 */
public enum FtExcelEnum {

    /**
     * 前期策划 纠偏措施 导入模板 默认初始化字典下来 可以实现函数进行自定义下拉
     */
    QQCH_SCHE_CORR("importCorr.xlsx", "纠偏措施模板.xlsx", "com.hhwy.pm.qqch.sgch.sche.domain.QqchScheCorr"),
    QQCH_ORG("importOrg.xlsx", "项目组织机构模板.xlsx", "com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationList"),
    QQCH_IMPORTANCE("importImportance.xlsx", "开工前重要策划.xlsx", "com.hhwy.pm.qqch.sgch.important.domain.QqchImportant");


    private final String templateName;
    private final String exportName;
    private final String clazzName;
    private ExcelFunction function;
    
    FtExcelEnum(String templateName, String exportName, String clazzName) {
        this.templateName = templateName;
        this.exportName = exportName;
        this.clazzName = clazzName;
    }

    FtExcelEnum(String templateName, String exportName, String clazzName, ExcelFunction function) {
        this.templateName = templateName;
        this.exportName = exportName;
        this.clazzName = clazzName;
        this.function = function;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public String getExportName() {
        return exportName;
    }

    public ExcelFunction getFunction() {
        return function;
    }
}
