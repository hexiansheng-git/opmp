package com.hhwy.pm.qqch.preparation.technique.expert.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetAdvisoryOrgan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:56:43
 * @remark 外部目标咨询机构选择
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchTargetAdvisoryOrganVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：外部目标咨询机构选择集合
     */
    private List<QqchTargetAdvisoryOrgan> list;
}
