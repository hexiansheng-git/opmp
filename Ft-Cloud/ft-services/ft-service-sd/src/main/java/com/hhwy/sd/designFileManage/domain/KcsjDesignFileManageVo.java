package com.hhwy.sd.designFileManage.domain;

import java.util.List;
import lombok.Data;

@Data
public class KcsjDesignFileManageVo {
    //新增数据
    private List<KcsjDesignFileManage> treeList;
    //删除Ids
    private List<String> delIdList;

}
