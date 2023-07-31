package com.hhwy.pm.qqch.preparation.measureexp.range.dto;

import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpPerson;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureOrg;
import com.hhwy.utils.JsonUtils;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author m
 */
@Data
@ToString
public class QqchMeasureExpDTO {
    private BigDecimal version;
    private String submitFlag;
    private QqchMeasureOrg org;
    private List<QqchMeasureExpRange> expRangeList;
    private List<QqchMeasureExpPerson> personList;


    public static void main(String[] args) {
        JsonUtils.soutJsonStr(QqchMeasureOrg.class);
        JsonUtils.soutJsonStr(QqchMeasureExpRange.class);
        JsonUtils.soutJsonStr(QqchMeasureExpPerson.class);
    }
    
    
    
    
    
}
