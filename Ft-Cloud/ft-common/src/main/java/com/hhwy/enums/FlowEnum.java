package com.hhwy.enums;

/**
 * 流程enum
 *
 * @author mls
 */
public enum FlowEnum {


        /**********************前期策划开始************************/
        QQCH_REVIEW("qqch_receive", "qqch_receive_process"),
        /*前期策划变更*/
        QQCH_CHANGE("qqch_change", "qqch_change_process"),

        /*前期策划工作小组*/
        QQCH_WORK_GROUP("qqch_work_group", "process_qqch_work_group"),

        /*前期策划工作计划*/
        QQCH_WORK_PLAN("qqch_work_plan", "process_qqch_work_plan"),

        /*前期策划评审*/
        QQCH_REVIEW1("qqch_review","process_qqch_review_1"),
        QQCH_REVIEW2("qqch_review","process_qqch_review_3"),

        /*前期策划执行检查*/
        QQCH_PERFORM_INSPECTION("qqch_perform_inspection","process_qqch_perform_inspection"),

        // 前期策划总结评价
        QQCH_SUMMARY_EVALUATION("qqch_summary_evaluation", "process_qqch_summary_evaluation"),

        /*物资总需求*/
        WZZX("wzch_total_demand", "total_material_demand_process"),
        /**
         * 采购供应策划表
         */
        WPS("wzch_purchase_supply", "wzch_purchase_supply_process"),
        /**
         * 周转材租赁策划
         */
        WRR("wzch_revolve_rent", "wzch_revolve_rent_process"),
        /**
         * 内部调剂材料策划
         */
        WIA("wzch_internal_adjust", "wzch_internal_adjust_process"),
        /**
         * 属地采购供应策划
         */
        WLPS("wzch_local_purchase_supply", "wzch_local_purchase_supply_process"),
        /**********************前期策划结束************************/

        /**********************项目设立开始************************/
        //项目设立，合同
        XMSL_CONTRACT("xmsl_contract_info", "process_test_contract"),
        //项目设立，wbs
        XMSL_WBS("xmsl_wbs_main", "xmsl_wbs_main_process"),
        //图纸复核
        XMSL_DRAW_REVIEW("xmsl_draw_review", "xmsl_draw_review_process"),
        /**********************项目设立结束************************/



        /*********************进度管理****************************/
        // 进度管理-纠偏措施制定
        JDGL_CORRECTION_MEASURES_MAKE("jdgl_correction_measures_make", "process_jdgl_correction_measures_make"),

        // 进度管理年进度计划
        JDGL_YEARPLAN("jdgl_year_plan", "process_jdgl_year_plan"),
        // 进度管理季进度计划
        JDGL_QUARTERPLAN("jdgl_quarter_plan", "process_jdgl_quarter_plan"),
        // 进度管理月进度计划
        JDGL_MONTHPLAN("jdgl_month_plan", "process_jdgl_month_plan"),
        // 进度管理周进度计划
        JDGL_WEEKPLAN("jdgl_week_plan", "process_jdgl_week_plan"),
        // 进度管理进度填报
        JDGL_DAYSCHEDULE("jdgl_day_schedule","process_jdgl_day_schedule"),

        //前期策划执行检查
        QQCH_ZXJC("qqch_perform_inspection","process_qqch_perform_inspection"),



        /*********************施工技术管理****************************/
        /*********************科技管理****************************/
        //专利申报管理
        SGJS_PATENT_DECLARE("sgjs_patent_declare","process_sgjs_patent_declare"),
        //论文发表管理
        SGJS_PAPER_PUBLISH("sgjs_paper_publish","process_sgjs_paper_publish"),

        // 四新成果管理
        SGJS_FOUR_NEWS_ACHIEVEMENT("sgjs_four_news_achievement", "process_sgjs_four_news_achievement"),
        // 工艺工法管理
        SGJS_TECH_METHOD("sgjs_tech_method", "process_sgjs_tech_method"),

        //科技管理 - 科研课题管理 - 课题申请
        SGJS_TECH_SCIENCE_TOPIC("sgsj_technical_science_topic", "process_sgsj_technical_science_topic"),

        //科技管理 - 科研课题管理 - 课题立项
        SGJS_TECH_SCIENCE_TOPIC_LX("sgsj_technical_science_topic", "process_sgsj_technical_science_topic_lx"),



        /*********************勘察设计管理****************************/
        //勘察设计大纲评审
        KCSJ_PATENT_DECLARE("kcsj_outline_review","process_kcsj_outline_review");

        public String getTableName() {
            return this.tableName;
        }

        public String getProcessKey() {
            return this.processKey;
        }

        private final String tableName;

        private final String processKey;


        FlowEnum(String tableName, String processKey) {
            this.tableName = tableName;
            this.processKey = processKey;
        }
}
