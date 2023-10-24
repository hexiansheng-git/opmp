package com.hhwy.utils;

/**
 * pms系统常量类
 *
 * @author lcf
 * @date 2022/10/25
 */
public class Constant {
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

    /** 是否含有历史记录按钮 **/
    public static final String HISTORY_NOTE_FIELD_NAME= "historyNote";
    /** 部门表-项目标识 **/
    public static final String DEPT_PRJ_FLAG = "prjInfo";
    
    //0-未发起; 1审核中; 4-流程已结束,业务未结束; 5-流程和业务都已结束'
//    public static final String task_status_INIt



}
