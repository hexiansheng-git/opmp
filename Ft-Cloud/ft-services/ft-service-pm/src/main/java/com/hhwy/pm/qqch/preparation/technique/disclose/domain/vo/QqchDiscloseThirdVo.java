package com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo;

import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThird;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThirdDetail;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-21 14:26:42
 * @remark qqch_disclose_third
 */
@Data
public class QqchDiscloseThirdVo {

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
     * 字段描述：三级交底集合
     */
    private List<QqchDiscloseThird> treeList;

    /**
     * 字段描述：全部三级交底详情集合
     */
    private List<QqchDiscloseThirdDetail> allDetailTreeList;
}
