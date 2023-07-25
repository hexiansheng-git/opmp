package com.hhwy.pm.qqch.preparation.technique.techManage.domain.vo;

import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchProjectLinkupManage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 11:24:02
 * @remark 技术管理项目沟通管理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchProjectLinkupManageVo {
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
     * 字段描述：技术管理项目沟通管理集合
     */
    private List<QqchProjectLinkupManage> qqchProjectLinkupManageList;
}
