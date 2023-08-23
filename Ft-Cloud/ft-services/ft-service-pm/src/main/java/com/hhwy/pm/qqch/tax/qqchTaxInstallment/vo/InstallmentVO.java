package com.hhwy.pm.qqch.tax.qqchTaxInstallment.vo;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.utils.tree.TreeNode;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
public class InstallmentVO extends CompileEntity<InstallmentVO> {
    private Long inRecordId;
    private Long costRecordId;
    private List<ListVO> list;






    @Data
    @ToString
    public static class ListVO extends TreeNode<ListVO> {
        private String digest;
        // 内账成本
        private BigDecimal innerAmt;
        // 符合属地账要求成本
        private BigDecimal reqAmt;
        // 差异
        private BigDecimal diffAmt;
        // 属地账策划成本
        private BigDecimal localAmt;
        // 属地账成本
        private BigDecimal locAmt;
        // 收入
        private BigDecimal amt;
        private BigDecimal profit;


//        private BigDecimal usdInnerAmt;
//        private BigDecimal usdReqAmt;
//        private BigDecimal usdDiffAmt;
//        private BigDecimal usdLocalAmt;
//        private BigDecimal usdLocAmt;
//
        private BigDecimal usdAmt;
        
        
        
    }
    
    
}
