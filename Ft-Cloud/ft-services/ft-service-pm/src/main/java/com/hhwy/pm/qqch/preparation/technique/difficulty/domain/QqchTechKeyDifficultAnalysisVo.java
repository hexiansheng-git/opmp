package com.hhwy.pm.qqch.preparation.technique.difficulty.domain;

import com.hhwy.common.core.web.domain.BaseEntity;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-10 14:20:39
 * @remark
 */
@Data
public class QqchTechKeyDifficultAnalysisVo extends BaseEntity {

    private List<QqchTechKeyDifficultAnalysis> keyAnalysisList;

    private List<QqchTechKeyDifficultAnalysis> difficultAnalysisList;
}
