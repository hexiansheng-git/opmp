package com.hhwy.pm.qqch.sgch.sche.dto;

import com.hhwy.pm.qqch.sgch.sche.domain.*;
import com.hhwy.utils.JsonUtils;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
public class QqchScheDTO implements Serializable {

    private BigDecimal version;
    /**
     * 提交状态
     */
    private String submitFlag;
    /**
     * 说明
     */
    private QqchScheDiffDesc diffDesc;
    /**
     * 差异化列表
     */
    private List<QqchScheDiff> diffList;
    /**
     * 进度分析要素
     */
    private List<QqchScheAnalyse> analyseList;
    /**
     * 影响因素
     */
    private List<QqchScheFactors> factorsList;
    /**
     * 纠偏措施
     */
    private List<QqchScheCorr> corrList;


    public static void main(String[] args) {
        JsonUtils.soutJsonStr(QqchScheDiffDesc.class);
        JsonUtils.soutJsonStr(QqchScheDiff.class);
        JsonUtils.soutJsonStr(QqchScheAnalyse.class);
        JsonUtils.soutJsonStr(QqchScheFactors.class);
        JsonUtils.soutJsonStr(QqchScheCorr.class);
    }

}
