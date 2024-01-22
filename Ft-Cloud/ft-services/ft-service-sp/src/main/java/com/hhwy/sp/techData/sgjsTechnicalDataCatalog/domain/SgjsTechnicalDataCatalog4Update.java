package com.hhwy.sp.techData.sgjsTechnicalDataCatalog.domain;

import lombok.Data;

import java.util.List;

@Data
public class SgjsTechnicalDataCatalog4Update {

    private List<SgjsTechnicalDataCatalog> treeList;

    private List<Long> delIdList;

}
