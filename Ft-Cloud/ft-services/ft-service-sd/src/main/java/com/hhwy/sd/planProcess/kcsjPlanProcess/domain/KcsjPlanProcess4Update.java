package com.hhwy.sd.planProcess.kcsjPlanProcess.domain;

import lombok.Data;

import java.util.List;

@Data
public class KcsjPlanProcess4Update {

    List<Long> delIdList;

    List<KcsjPlanProcess> treeList;

}
