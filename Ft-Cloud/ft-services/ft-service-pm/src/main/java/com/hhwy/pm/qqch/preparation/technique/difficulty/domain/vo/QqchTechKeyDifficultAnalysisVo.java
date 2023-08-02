package com.hhwy.pm.qqch.preparation.technique.difficulty.domain.vo;

import com.hhwy.pm.qqch.preparation.technique.difficulty.domain.QqchTechKeyDifficultAnalysis;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-10 14:20:39
 * @remark 施工重点难点分析
 */
@Data
public class QqchTechKeyDifficultAnalysisVo {

    /**
     * 阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    private String stageIdentity;

    /**
     * 版本状态
     */
    private BigDecimal version;

    /**
     * 字段描述：菜单id
     */
    private String menuId;

    /**
     * 字段描述：按钮标识（0：保存，1：确认，2：提交）
     */
    private String buttonMark;

    /**
     * 施工重点
     */
    private List<QqchTechKeyDifficultAnalysis> keyAnalysisList;

    /**
     * 施工难点
     */
    private List<QqchTechKeyDifficultAnalysis> difficultAnalysisList;
}
