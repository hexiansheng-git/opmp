package com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-13 14:40:32
 * @remark 3.4.2施工方案清单
 */
@Data
public class QqchConstructionListVo {

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
     * 字段描述：项目编码（保存时生成方案编号用）
     */
    private String projectCode;

    /**
     * 字段描述：方案名称（筛选条件）
     */
    private String schemeName;

    /**
     * 字段描述：方案类型（字典类型scheme_type）（筛选条件）
     */
    private String schemeType;

    /**
     * 字段描述：施工方案清单集合
     */
    private List<QqchConstructionList> list;
}
