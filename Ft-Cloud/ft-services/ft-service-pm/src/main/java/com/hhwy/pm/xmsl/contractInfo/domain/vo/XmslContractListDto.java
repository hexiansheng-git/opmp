package com.hhwy.pm.xmsl.contractInfo.domain.vo;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class XmslContractListDto {

    @Valid
    private List<XmslContractListVo> list;
    private String delIds;
    //提交标志
    private Integer submitFlag;
}
