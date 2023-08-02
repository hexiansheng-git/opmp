package com.hhwy.pm.qqch.preparation.survey.document.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchManageProcedure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author han
 * @date 2023-07-13 11:40:23
 * @remark 管理程序Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchManageProcedureVo extends PreparationEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：管理程序集合
     */
    private List<QqchManageProcedure> qqchManageProcedureList;
}
