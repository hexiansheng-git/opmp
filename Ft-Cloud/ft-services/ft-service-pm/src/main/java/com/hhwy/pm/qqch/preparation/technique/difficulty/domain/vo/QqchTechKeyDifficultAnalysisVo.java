package com.hhwy.pm.qqch.preparation.technique.difficulty.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.QqchTechKeyDifficultAnalysis;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-10 14:20:39
 * @remark 施工重点难点分析
 */
@Data
public class QqchTechKeyDifficultAnalysisVo extends PreparationEntity {

    /**
     * 施工重点
     */
    private List<QqchTechKeyDifficultAnalysis> keyAnalysisList;

    /**
     * 施工难点
     */
    private List<QqchTechKeyDifficultAnalysis> difficultAnalysisList;
}
