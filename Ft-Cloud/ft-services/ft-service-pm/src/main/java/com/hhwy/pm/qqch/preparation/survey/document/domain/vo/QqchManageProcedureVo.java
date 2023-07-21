package com.hhwy.pm.qqch.preparation.survey.document.domain.vo;

import com.hhwy.pm.qqch.constant.ConfirmStatus;
import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchManageProcedure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-13 11:40:23
 * @remark 管理程序Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchManageProcedureVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    private String stageIdentity;
    /**
     * 字段描述：确认状态（0：未确认，1：已确认）
     */
    private String confirmStatus = ConfirmStatus.UNCONFIRMED;
    /**
     * 字段描述：版本
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
     * 字段描述：管理程序集合
     */
    private List<QqchManageProcedure> qqchManageProcedureList;
}
