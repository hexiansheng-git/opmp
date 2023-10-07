package com.hhwy.pm.xmsl.project.constant;

/**
 * 项目状态字典值   字典：project_status
 */
public class ProjectStatus {

    /*未中标*/
    public static final String LOSE_BID = "1";

    /*中标未开工*/
    public static final String WIN_BID_NOT_SERVE = "2";

    /*正常施工*/
    public static final String NORMAL_CONSTRUCTION = "3";

    /*停工*/
    public static final String LOCKOUT = "4";

    /*主体完工*/
    public static final String MAIN_COMPLETION = "5";

    /*已完工*/
    public static final String ALREADY_COMPLETION = "6";

    /*已初验*/
    public static final String ALREADY_INITIAL_INSPECTION = "7";

    /*已终验*/
    public static final String ALREADY_FINAL_INSPECTION = "8";
}
