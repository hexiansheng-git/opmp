package com.hhwy.pm.xmsl.contractInfo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ldd
 * @date 2023-07-10 14:17:42
 * @remark   合同信息--主合同清单实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractListQueryVo {
    private static final long serialVersionUID = 1L;

    /**
     * 字段描述：清单编号
     */
    private String code;
    /**
     * 字段描述：清单中文名称
     */
    private String chineseName;
}
