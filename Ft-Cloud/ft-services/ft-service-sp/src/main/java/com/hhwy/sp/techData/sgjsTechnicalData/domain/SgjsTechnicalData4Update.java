package com.hhwy.sp.techData.sgjsTechnicalData.domain;

import lombok.Data;

import java.util.List;

@Data
public class SgjsTechnicalData4Update {

    private Long dataCatalogId;

    private List<SgjsTechnicalData> treeList;

    private List<Long> delIdList;

    private String projectCode;

}
