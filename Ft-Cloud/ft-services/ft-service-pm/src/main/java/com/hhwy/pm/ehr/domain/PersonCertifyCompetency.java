package com.hhwy.pm.ehr.domain;

import lombok.Data;

import java.util.List;

@Data
public class PersonCertifyCompetency {

    /*职(执)业资格类别名称*/
    private String categoryName;
    /*职(执)业资格类别编码*/
    private String categoryNumber;
    /*职(执)业资格名称*/
    private String certificateName;
    /*职(执)业资格编码*/
    private String certificateNo;
    /*职(执)业资格级别名称*/
    private String levelName;
    /*职(执)业资格级别编码*/
    private String levelNumber;
    /*专业名称*/
    private String zymc;
    /*聘任注册时间 格式为:yyyy-MM-dd*/
    private String przcny;
    /*聘任或注册单位*/
    private String appointUnit;
    /*注册编号*/
    private String certificateNumber;
    /*主要职业资格*/
    private Boolean isHighest;
    /*获取时间 格式:yyyy-MM-dd*/
    private String issueDate;
    /*附件列表*/
    private List<Attachment> attachmentList;
}
