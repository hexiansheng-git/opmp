package com.hhwy.pm.ehr.domain;

import lombok.Data;

import java.util.List;

@Data
public class PersonCertifyCompetency {

    private String categoryName;
    private String certifiedCompetencyName;
    private String przcny;
    private String levelNumber;
    private String zymc;
    private String categoryNumber;
    private String appointUnit;
    private String certifiedCompetencyNumber;
    private String levelName;
    private List<Attachment> attachmentList;
    private String certificateNumber;
    private Boolean isHighest;
    private String obtainDate;

//    category_name -> 财会类
//
//    certifiedCompetency_name -> 会计从业资格证书
//
//    przcny ->
//
//    level_number -> 001
//
//    zymc -> 会计
//
//    category_number -> 005
//
//    appointUnit ->
//
//    certifiedCompetency_number -> 008
//
//    level_name -> L01
//
//    attachment -> {JSONArray@22690}  size = 1
//
//
//
//    certificateNumber ->
//
//    isHighest -> {Boolean@22694} true
//
//    obtainDate -> 2015-03-11
}
