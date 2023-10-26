package com.hhwy.pm.qqch.preparation.safe.risk.domain.vo;

import com.hhwy.pm.qqch.common.domain.PreparationEntity;
import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskList;
import com.hhwy.utils.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**安全风险清单/重大安全风险清单
 * @author zqq
 * @create 2023-08-11 13:50
 */
@Data
public class QqchSafeRiskListVo  extends PreparationEntity {
    @NotBlank(message = "标识不能为空！",groups = {ValidationGroups.Save.class,ValidationGroups.Select.class})
    private String type;//0
    /*是否编辑过*/
    private String isEdit;
    private QqchSafeRiskList safeRiskList;
}
