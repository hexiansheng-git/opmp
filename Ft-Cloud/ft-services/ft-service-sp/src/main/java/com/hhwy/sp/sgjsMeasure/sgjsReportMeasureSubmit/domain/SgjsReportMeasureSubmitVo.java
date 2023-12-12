package com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain;

import java.util.List;
import lombok.Data;

/**
 * 返回结果
 */
@Data
public class SgjsReportMeasureSubmitVo {
    //新增数据
    private List<SgjsReportMeasureSubmit> treeList;
    //删除id
    private List<String> delIdList;

}
