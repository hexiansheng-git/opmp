package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.vo;

import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchPigeonholeDutyDivision;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:48:27
 * @remark 技术档案归档责任分工
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchPigeonholeDutyDivisionVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    private String stageIdentity;
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
     * 字段描述：优技术档案归档责任分工集合
     */
    private List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList;
}
