package com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo;

import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeChangeOrganization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:50
 * @remark 优化变更组织策划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchOptimizeChangeOrganizationVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：模块确认情况
     */
    private QqchModuleConfirmCase qqchModuleConfirmCase;
    /**
     * 字段描述：优化变更组织策划树列表
     */
    private List<QqchOptimizeChangeOrganization> treeList;
}
