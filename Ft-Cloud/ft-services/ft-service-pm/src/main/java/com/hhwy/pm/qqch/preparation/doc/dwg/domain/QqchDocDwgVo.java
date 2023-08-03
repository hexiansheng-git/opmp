package com.hhwy.pm.qqch.preparation.doc.dwg.domain;

import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTech;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author hwj
 * @date 2023-07-07 18:35:34
 * @remark
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QqchDocDwgVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    @NotBlank(message = "阶段标识不能为空",groups = {ValidationGroups.Update.class})
    private String stageIdentity;
    /**
     * 字段描述：确认状态（0：未确认，1：已确认）
     */
//    private String confirmStatus = ConfirmStatus.UNCONFIRMED;
    /**
     * 字段描述：版本
     */
    @NotNull(message = "按钮标识不能为空",groups = {ValidationGroups.Update.class})
    private BigDecimal version;
    /**
     * 字段描述：菜单id
     */
    @NotBlank(message = "菜单路由不能为空",groups = {ValidationGroups.Update.class})
    private String menuId;
    /**
     * 字段描述：按钮标识（0：保存，1：确认，2：提交）
     */
    @NotBlank(message = "按钮标识不能为空",groups = {ValidationGroups.Update.class})
    private String buttonMark;
    /**
     * 字段描述：附件组id
     */
    private String fileGroupId;
    private String createUser;
    private String createUserName;
    /**
     * 字段描述：变更程序策划集合
     */
    private List<QqchDocDwg> dataList;
}
