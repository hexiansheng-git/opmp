package com.hhwy.pm.xmsl.contractInfo.domain.vo;

import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.tree.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportTreeNodeVo extends TreeNode {

    private static final long serialVersionUID = 1L;

    @Excel(name = "层级码")
    private String innerCode;
    @Excel(name = "父层级码")
    private String parentInnerCode;

    /**
     *
     */
    private String dataFrom;


}
