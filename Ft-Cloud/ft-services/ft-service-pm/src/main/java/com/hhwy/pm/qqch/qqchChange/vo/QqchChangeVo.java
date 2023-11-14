package com.hhwy.pm.qqch.qqchChange.vo;

import com.hhwy.pm.qqch.qqchChange.domain.QqchChange;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class QqchChangeVo extends QqchChange {

    List<QqchChangeDetail> detailList;

    //提交标志 1：提交，否则：保存
    private String submitFlag;


    //项目分类
    private String projectCategory;
    //合同金额（万美元）
    private BigDecimal amount;

}
