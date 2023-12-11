package com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain;

import java.util.List;
import lombok.Data;

/**
 * 返回结果
 *
 * @author zmh
 * @date 2023-12-08
 */
@Data
public class SgjsPlanMeasureManageVo {
    //新增数据
    private List<SgjsPlanMeasureManage> treeList;
    //删除id
    private List<String> delIdList;

}
