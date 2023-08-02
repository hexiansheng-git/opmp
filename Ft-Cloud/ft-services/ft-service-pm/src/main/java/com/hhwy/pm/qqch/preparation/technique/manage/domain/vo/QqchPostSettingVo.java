package com.hhwy.pm.qqch.preparation.technique.manage.domain.vo;

import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-11 15:23:27
 * @remark 3.3.2岗位设置
 */
@Data
public class QqchPostSettingVo {

    private static final long serialVersionUID = 1L;

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
     * 岗位设置集合
     */
    private List<QqchPostSetting> treeList;
}
