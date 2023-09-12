package com.hhwy.pm.qqch.wzch.demand.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class WzchSourceTotalDemandDetailExportRequest {

    private String materialCode;
    private String materialName;
    private String materialSpec;
    private String materialTechParam;
    private String materialStandard;
    private String unit;
    private BigDecimal totalDemandAmount;
    private BigDecimal selfDemandAmount;
    private BigDecimal nonSelfAmount;
    private String categoryName;
    private String firstEnterFlag;
    private Map<String,String> yearView;
    private Map<String,Map<String,String>> montbView;
    private Map<String,Map<String,String>> quarterView;
}
