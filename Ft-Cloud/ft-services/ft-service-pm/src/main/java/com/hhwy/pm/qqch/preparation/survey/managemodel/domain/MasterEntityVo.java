package com.hhwy.pm.qqch.preparation.survey.managemodel.domain;

import com.hhwy.pm.qqch.constant.ConfirmStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MasterEntityVo {

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
     * 字段描述：完整设计交接情况集合
     */
    private List<MasterEntity> masterEntityList;
}
