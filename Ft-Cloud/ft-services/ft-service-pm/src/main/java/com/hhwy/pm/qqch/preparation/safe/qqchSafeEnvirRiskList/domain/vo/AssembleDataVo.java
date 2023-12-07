package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.vo;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskListDetail;
import com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.domain.QyzsSafeEnvRiskProc;
import lombok.Data;

import java.util.List;

@Data
public class AssembleDataVo {

    //标准wbsCode
    private String wbsCode;

    //页面数据
    private List<QqchSafeEnvirRiskListDetail> detailList;

    //选择的数据
    private List<QyzsSafeEnvRiskProc> envRiskProcList;
}
