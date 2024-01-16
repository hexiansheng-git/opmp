package com.hhwy.pm.qqch.preparation.quality.emp.vo;

import com.hhwy.pm.qqch.preparation.quality.emp.domain.QqchEmpItem;
import com.hhwy.pm.qyzs.quality.qyzsQualitySpecialInspection.domain.QyzsQualitySpecialInspection;
import lombok.Data;

import java.util.List;

@Data
public class EmpItemMergeDto {

    //wbs信息
    private QqchEmpItem item;
    //知识库
    List<QyzsQualitySpecialInspection> inspectionList;
    //清单集合
    List<QqchEmpItem> list;
}
