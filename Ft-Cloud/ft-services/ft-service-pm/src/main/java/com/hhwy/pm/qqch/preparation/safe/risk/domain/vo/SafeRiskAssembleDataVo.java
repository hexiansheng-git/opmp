package com.hhwy.pm.qqch.preparation.safe.risk.domain.vo;

import com.hhwy.pm.qqch.preparation.safe.risk.domain.QqchSafeRiskListDetail;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.domain.QyzsSafeSafeRisk;
import lombok.Data;

import java.util.List;

@Data
public class SafeRiskAssembleDataVo {

    //标准wbsCode
    private String wbsCode;

    //页面数据
    private List<QqchSafeRiskListDetail> detailList;

    //选择的数据
    private List<QyzsSafeSafeRisk> safeSafeRiskList;
}
