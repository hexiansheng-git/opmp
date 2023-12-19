package com.hhwy.sd.organManage.domain;

import lombok.Data;

import java.util.List;

@Data
public class KcsjOrganManage4Update {

    List<Long> delIdList;

    List<KcsjOrganManage> treeList;

}
