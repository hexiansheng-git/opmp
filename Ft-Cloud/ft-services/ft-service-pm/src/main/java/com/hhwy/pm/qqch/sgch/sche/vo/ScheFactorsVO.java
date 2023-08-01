package com.hhwy.pm.qqch.sgch.sche.vo;

import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheFactors;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@Data
@ToString
public class ScheFactorsVO implements Serializable {
    private List<ScheFactorsHeader> headerList;
    private List<QqchScheFactors> factorsList;


    @Data
    @ToString
    public static class ScheFactorsHeader {
        private String headerName;
        private String headerValue;
    }
}
