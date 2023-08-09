package com.hhwy.utils.common;

/**
 * pms系统常量类
 *
 * @author lcf
 * @date 2022/10/25
 */
public class PmsConstant {
    /** 301响应码 **/
    public static final Integer WARN_CODE=301;
    /** 301响应码 **/
    public static final String SUCCESS_CODE="200";
    /** del_flag 0有效 **/
    public static final String VALID_DELFLAG="0";
    /** del_flag 1无效 **/
    public static final String NO_VALID_DELFLAG="1";
    /** status 0正常 **/
    public static final String VALID_STATUS="0";
    /** status 1停用 **/
    public static final String NO_VALID_STATUS="1";
    /** status 0正常 **/
    public static final Integer VALID_STATUS_INT=0;
    /** status 1停用 **/
    public static final Integer NO_VALID_STATUS_INT=1;
    /** 是否-否 **/
    public static final Integer NO_INT=0;
    /** 是否-是 **/
    public static final Integer YES_INT=1;
    /** 采购类别---机械设备 **/
    public static final String PURCHAR_TYPE_EQU="6";

    /**材料编码:材料**/
    public static final Integer BASE_MATERIAL_INFO=0;
    /**材料编码:设备**/
    public static final Integer BASE_EQUIMENT_INFO=1;
    /**材料编码:配件**/
    public static final Integer BASE_PART_INFO=2;
    /**材料编码库redis key**/
    public static final String MATERIALREDISKEY="materialInfoRedis";
    /**最近选择key**/
    public static final String RECENTMATERIALKEY="recentMaterialKey";
    /**材料编码分类redis key**/
    public static final String CATEGORYREDISKEY="categoryInfoRedis";
    /**材料编码16大类redis key**/
    public static final String CATEGORYREDISKEY_16="categoryInfo16Redis";
    /**材料编码分类redis key**/
    public static final String WPP_PURCHASE_VIEW = "WPP_PURCHASE_VIEW_";
    /**材料编码分类redis key**/
    public static final String WPP_LOCAL_PURCHASE_VIEW = "WPP_LOCAL_PURCHASE_VIEW_";
    /**币种信息redis key**/
    public static final String CURRENCYINFOKEY = "baishanyunCurrencyInfo";

    /**合同装箱通知-最新内容字典项  **/
    public static final String CONTRACT_NOTIFY_CONTENT_DICT_KEY = "cght_contract_notify_last";
    
    /**合同变更类型-未发生变更  **/
    public static final Integer CONTRACT_CHANGE_TYPE_UN = 1;
    /**合同变更类型-已发生变更  **/
    public static final Integer CONTRACT_CHANGE_TYPE_CHANGE = 2;
    
    /**合同汇总类型-合同登记  **/
    public static final Integer CONTRACT_SUMMARY_TYPE_CON = 1;
    /**合同汇总类型-其他费用登记  **/
    public static final Integer CONTRACT_SUMMARY_TYPE_OTHER = 2;
    /**合同汇总状态-初始化  **/
    public static final Integer CONTRACT_SUMMARY_STATUS_INIT = 1;
    /**合同汇总状态-已结算  **/
    public static final Integer CONTRACT_SUMMARY_STATUS_SETTLE = 2;
    /**合同汇总状态-已付款  **/
    public static final Integer CONTRACT_SUMMARY_STATUS_PAY = 3;
    /**合同汇总状态-已发运  **/
    public static final Integer CONTRACT_SUMMARY_STATUS_SEND = 4;

    /**合同付款汇总类型-合同登记  **/
    public static final Integer CONTRACT_PAY_SUMMARY_TYPE_CON = 1;
    /**合同付款汇总类型-国内总部付款  **/
    public static final Integer CONTRACT_PAY_SUMMARY_TYPE_COUNTRY = 2;
    /**合同付款汇总类型-协作队伍  **/
    public static final Integer CONTRACT_PAY_SUMMARY_TYPE_COOPERATION = 3;
    /**合同付款汇总类型-委托总包  **/
    public static final Integer CONTRACT_PAY_SUMMARY_TYPE_ENTRUST = 4;

    /**合同结算汇总类型-合同付款  **/
    public static final Integer CONTRACT_SETTLE_SUMMARY_TYPE_CONTRACT = 1;
    /**合同结算汇总类型-其他费用结算  **/
    public static final Integer CONTRACT_SETTLE_SUMMARY_TYPE_OTHER = 2;

    /**币种-rmb  **/
    public static final String CURRENCY_RMB = "CNY";

    /**供应商付款信息类型-人民币  **/
    public static final Integer SUPPLIER_TYPE_RMB = 1;
    /**供应商付款信息类型-外币  **/
    public static final Integer SUPPLIER_TYPE_OTHER = 2;

    /**供应商优质资源评分状态-未评分  **/
    public static final Integer SUPPLIER_HIGH_STATUS_UN = 0;
    /**供应商优质资源评分状态-评分中  **/
    public static final Integer SUPPLIER_HIGH_STATUS_SCOREING = 1;
    /**供应商优质资源评分状态-已完成  **/
    public static final Integer SUPPLIER_HIGH_STATUS_FINISH = 2;

    /**供应商评分模板类型-优势资源评分  **/
    public static final Integer SUPPLIER_SCORETYPE_ADVANTAGE = 1;
    /**供应商评分模板类型-合同评分  **/
    public static final Integer SUPPLIER_SCORETYPE_CONTRACT = 2;
    /**评分模板状态-编辑中  **/
    public static final Integer SCORETEMPLATE_STATUS_EDITING = 0;
    /**评分模板状态-已发布  **/
    public static final Integer SCORETEMPLATE_STATUS_PUBLISH = 1;
    /**评分模板属性-打分 **/
    public static final Integer SCORETEMPLATE_PROPERTY_SCORE = 1;
    /**评分模板属性-单选 **/
    public static final Integer SCORETEMPLATE_PROPERTY_SINGLE = 2;
    /**评分模板明细类型-用户定义 **/
    public static final Integer SCORETEMPLATE_DETAIL_TYPE_CUST = 1;
    /**评分模板明细类型-系统内置 **/
    public static final Integer SCORETEMPLATE_DETAIL_TYPE_SYS = 2;
    
    /**评分主表-状态-初始化 **/
    public static final Integer SCOREMAIN_STATUS_INIT = 0;
    /**评分主表-状态-评分中 **/
    public static final Integer SCOREMAIN_STATUS_SCOREING = 1;
    /**评分主表-状态-已结束 **/
    public static final Integer SCOREMAIN_STATUS_END = 2;
    /**评分人表-状态-初始化 **/
    public static final Integer SCOREPERSON_STATUS_INIT = 1;
    /**评分人表-状态-评分中 **/
    public static final Integer SCOREPERSON_STATUS_SCOREING = 2;
    /**评分人表-状态-已评分 **/
    public static final Integer SCOREPERSON_STATUS_SCORED = 3;

    /**移动端状态-成功 **/
    public static final Integer MOBILE_CODE_SUCCESS = 200;
    /**移动端状态-失败 **/         
    public static final Integer MOBILE_CODE_ERROR = 500;
    /**********************************流程常量***************************************/

    //1:撤回
    public static final String ISFLAG = "1";
    //流程节点信息isArea
    public static final String ISAREA=" isArea";
    //流程节点信息isArea
    public static final String ISABROAD="  isAbroad";
    //流程状态:4
    public static final String PROCESS_TASKSTATUS="4";
    //流程业务状态：5
    public  static final String PROCESS_FINISH="5";


    /** 箱子对应的二维码内容*/
//    public  static final String BOX_QR_PATH= "/wlgl/boxcollect/selectMtlList?markOrEquId=%s&templateType=%s";;
    public  static final String BOX_QR_PATH= "唛头编号：%s\n发运号：%s\n箱单号：%s\n合同号：%s";
//    public  static final String BOX_QR_PATH= "/shippingManagement/packingListSummary/xdrecordlistdetail?boxCode=%s&markOrEquId=%s&packingCode=%s";
    public  static final String BOX_QR_CONTENT= "唛头编号：%s\n发运号：%s\n箱单号：%s\n合同号：%s\n供应商：%s\n货物名称：%s";

    /*设备 二维码内容*/
    public  static final String MAT_QR_PATH= "合同号：%s\n物资编码：%s\n物资名称：%s\n供应商：%s";
    public  static final String MAT_QR_CONTENT= "合同号：%s\n物资编码：%s\n物资名称：%s\n供应商：%s";

    /** 箱子对应的二维码内容 BAK*/
//    public  static final String BOX_QR_PATH= "/shippingManagement/packingListSummary/xdrecordlistdetail?boxCode=%s&markOrEquId=%s&packingCode=%s";
//    public  static final String BOX_QR_CONTENT= "唛头编号：%s\n发运编号：%s\n供应商：%s\n货物名称：%s\n合同编号：%s\n箱单号：%s";

    /*设备 二维码内容 BAK*/
//    public  static final String MAT_QR_PATH= "供应商名称：%s\n合同号：%s\n物资编码：%s\n物资名称：%s\n单位：%s\n箱单号：%s\n规格型号：%s";
//    public  static final String MAT_QR_CONTENT= "供应商名称：%s\n合同号：%s\n物资编码：%s\n物资名称：%s\n单位：%s\n箱单号：%s";

    //箱单汇总-装箱发货通知，点击邮件跳转路径
    public  static final String BOX_COLLECT_EMAIL_GO= "/shippingManagement/packingListSummary/xdrecordlist";
    /***************增值税专用发票开票信息***************/
    /*中国交通建设股份有限公司-----购货单位信息*/
    public  static final String ZJJS_INVOICE_UNIT_INFO= "";
    /*中国交通建设股份有限公司-----购货单位名称*/
    public  static final String ZJJS_INVOICE_UNIT_NAME= "91110000710934369E";
    /*中国交通建设股份有限公司-----纳税人识别号*/
    public  static final String ZJJS_INVOICE_UNIT_NUMBER= "中国交通建设股份有限公司";
    /*中国交通建设股份有限公司-----地址、电话*/
    public  static final String ZJJS_INVOICE_UNIT_ADDR= "北京市西城区德胜门外大街85号 (010-82016697)";
    /*中国交通建设股份有限公司-----开户行及账号*/
    public  static final String ZJJS_INVOICE_UNIT_ACCOUNT= "北京市西城区德胜门外大街85号 (010-82016697)";


    /*中交一公局集团有限公司-----购货单位信息*/
    public  static final String ZJYGJ_INVOICE_UNIT_INFO= "";
    /*中交一公局集团有限公司-----购货单位名称*/
    public  static final String ZJYGJ_INVOICE_UNIT_NAME= "中交一公局集团有限公司";
    /*中交一公局集团有限公司-----纳税人识别号*/
    public  static final String ZJYGJ_INVOICE_UNIT_NUMBER= "911100001017004524";
    /*中交一公局集团有限公司-----地址、电话*/
    public  static final String ZJYGJ_INVOICE_UNIT_ADDR= "北京市朝阳区管庄周家井 (010-65168161)";
    /*中交一公局集团有限公司-----开户行及账号*/
    public  static final String ZJYGJ_INVOICE_UNIT_ACCOUNT= "招商银行北京青年路支行 010900050010413";

    /***************增值税专用发票开票信息end***************/

    //申请分部名称
    public  static final String APPLY_SUBDEPT = "海外分公司总部";
    // 税种字典项
    public  static final String WLGL_TAX_TYPE = "wlgl_tax_type";

    // 前端保存的列
    public  static final String ROUTER = "router";
    public  static final String CNY = "CNY";

    //数据来源
    public static final String PTVAR_ZHUSHUJU="zhushuju";
    public static final String PTVAR_MYSELF="myself";

}
