package com.hhwy.pm.qqch.preparation.measureexp.range.service;

import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;
import com.hhwy.pm.qqch.preparation.measureexp.range.dto.QqchMeasureExpDTO;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-25 18:01:34
 * @remark
 */
public interface IQqchMeasureService {
    void saveAll(QqchMeasureExpDTO expVO);
}
