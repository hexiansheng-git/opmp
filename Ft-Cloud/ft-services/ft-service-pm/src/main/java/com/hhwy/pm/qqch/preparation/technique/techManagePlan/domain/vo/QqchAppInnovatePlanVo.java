
package com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAppInnovatePlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:47
 * @remark 四新应用及创新计划
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchAppInnovatePlanVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：四新应用及创新计划集合
     */
    private List<QqchAppInnovatePlan> list;
}
