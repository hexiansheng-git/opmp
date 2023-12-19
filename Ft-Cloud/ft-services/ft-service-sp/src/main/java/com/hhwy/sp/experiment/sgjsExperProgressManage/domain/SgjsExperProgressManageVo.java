package com.hhwy.sp.experiment.sgjsExperProgressManage.domain;

import lombok.Data;

import java.util.List;

/**
 * @author wll
 * 2023/12/11
 */
@Data
public class SgjsExperProgressManageVo {

    //新增数据
    private List<SgjsExperProgressManage> treeList;
    //删除id
    private List<String> delIdList;
}
