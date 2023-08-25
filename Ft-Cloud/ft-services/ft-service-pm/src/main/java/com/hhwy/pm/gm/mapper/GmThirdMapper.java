package com.hhwy.pm.gm.mapper;

import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface GmThirdMapper {

    /**
     * 前期策划执行检查
     * @return
     */
    public List<Map> inspectionSummaryList();
}
