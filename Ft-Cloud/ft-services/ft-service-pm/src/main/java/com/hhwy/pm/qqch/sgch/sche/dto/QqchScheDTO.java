package com.hhwy.pm.qqch.sgch.sche.dto;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.sche.domain.*;
import com.hhwy.pm.qqch.sgch.sche.vo.ScheFactorsVO;
import com.hhwy.utils.JsonUtils;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@ToString
public class QqchScheDTO extends CompileEntity<QqchScheDTO> implements Serializable {

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
     * 影响因素入参
     */
    private List<List<QqchScheFactors>> factorsVOList;
    /**
     * 影响因素出参
     */
    private ScheFactorsVO scheFactorsVO;
    /**
     * 纠偏措施
     */
    private List<QqchScheCorr> corrList;


    public static void main(String[] args) {
        JsonUtils.soutJsonStr(QqchScheDiffDesc.class);
        JsonUtils.soutJsonStr(QqchScheDiff.class);
        JsonUtils.soutJsonStr(QqchScheAnalyse.class);
        JsonUtils.soutJsonStr(QqchScheFactors.class);
        JsonUtils.soutFtJsonStr(QqchScheCorr.class);
    }

}
