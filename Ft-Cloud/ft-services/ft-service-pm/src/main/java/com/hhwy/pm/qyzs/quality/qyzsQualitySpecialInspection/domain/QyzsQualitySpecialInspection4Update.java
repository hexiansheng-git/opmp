package com.hhwy.pm.qyzs.quality.qyzsQualitySpecialInspection.domain;

import lombok.Data;

import java.util.List;

@Data
public class QyzsQualitySpecialInspection4Update {

    List<QyzsQualitySpecialInspection> qyzsQualitySpecialInspectionListParam;

    String projectType;

    String wbsCode;

}
