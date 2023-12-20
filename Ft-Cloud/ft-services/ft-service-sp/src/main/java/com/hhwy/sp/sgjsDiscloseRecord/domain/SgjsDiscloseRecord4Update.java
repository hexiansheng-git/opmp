package com.hhwy.sp.sgjsDiscloseRecord.domain;

import lombok.Data;

import java.util.List;

@Data
public class SgjsDiscloseRecord4Update {
    List<Long> delIdList;
    List<SgjsDiscloseRecord> treeList;
}
