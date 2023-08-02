package com.hhwy.pm.qqch.preparation.finance.contract.domain.vo;

import com.hhwy.pm.qqch.preparation.finance.contract.domain.QqchFinancialMainContractTerms;
import com.hhwy.utils.validation.ValidationGroups;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:03
 * @remark 10.1财务相关主合同条款
 */
@Data
public class QqchFinancialMainContractTermsVo {

    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：阶段标识（1：第一阶段，2：第二阶段，3：第三阶段）
     */
    @NotBlank(message = "阶段标识不能为空", groups = {ValidationGroups.Update.class})
    private String stageIdentity;

    /**
     * 字段描述：版本
     */
    @NotBlank(message = "版本不能为空", groups = {ValidationGroups.Update.class})
    private BigDecimal version;

    /**
     * 字段描述：菜单id
     */
    @NotBlank(message = "菜单路由不能为空", groups = {ValidationGroups.Update.class})
    private String menuId;

    /**
     * 字段描述：按钮标识（0：保存，1：确认，2：提交）
     */
    @NotBlank(message = "按钮标识不能为空", groups = {ValidationGroups.Update.class})
    private String buttonMark;

    /**
     * 字段描述：主要税目税率集合
     */
    private List<QqchFinancialMainContractTerms> list;
}
