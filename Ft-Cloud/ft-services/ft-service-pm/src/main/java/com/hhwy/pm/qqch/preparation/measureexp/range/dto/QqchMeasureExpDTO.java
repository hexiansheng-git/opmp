package com.hhwy.pm.qqch.preparation.measureexp.range.dto;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpPerson;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureOrg;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author m
 */
@Data
@ToString
public class QqchMeasureExpDTO extends CompileEntity<QqchMeasureExpDTO> {
    private QqchMeasureOrg org;
    private List<QqchMeasureExpRange> expRangeList;
    private List<QqchMeasureExpPerson> personList;
}
