package com.hhwy.utils.excel;

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
    QQCH_IMPORTANCE("importImportance.xlsx", "开工前重要策划.xlsx", "com.hhwy.pm.qqch.sgch.important.domain.QqchImportant"),

    QQCH_TAX_GLOBAL("importTaxGlobal.xlsx", "整体资金策划.xlsx", "com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal"),
    QQCH_TAX_GOAL("importTaxGoal.xlsx", "财务目标.xlsx", "com.hhwy.pm.qqch.tax.qqchTaxGoal.domain.QqchTaxGoal"),
    QQCH_TEC_MAE("importTecMae.xlsx", "技术材料清单.xlsx", "com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMae"),
    QQCH_EXP_BETON("importExpBeton.xls", "混凝土配合比.xls", "com.hhwy.pm.qqch.preparation.measureexp.beton.domain.vo.QqchExpBetonImportVo"),

    QQCH_CONTRACT_LIST("importXmslContractList.xlsx", "主合同清单.xlsx", "com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList"),
    QQCH_CONTRACT_SIGN("importContractSign.xlsx", "主合同信息-签订信息.xlsx", "com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSign"),
    QQCH_CONTRACT_INSURE("importContractInsure.xlsx", "主合同信息-投保险种.xlsx", "com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInsure"),
    QQCH_CONTRACT_PAYINFO("importContractPayinfo.xlsx", "主合同信息-项目支付信息.xlsx", "com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo"),
    WZCH_TOTAL_DEMAND_DETIAL("importEquPlanDemand.xlsx", "总需详情.xlsx", "com.hhwy.pm.qqch.preparation.sbch.plan.domain.SbchTotalDemandPlanDetail");


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
