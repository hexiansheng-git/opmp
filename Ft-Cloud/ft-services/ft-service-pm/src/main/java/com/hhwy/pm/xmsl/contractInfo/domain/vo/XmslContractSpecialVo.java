package com.hhwy.pm.xmsl.contractInfo.domain.vo;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSpecial;
import lombok.Data;

import java.util.List;

/**
 * 功能：弹框List和列表list数据
 * 作者: fushudong
 * 时间: 2023/12/11
 */
@Data
public class XmslContractSpecialVo {


    private Long masterId;

    /**
     * 字段描述：列表集合
     */
    List<XmslContractSpecial> alreadyList;

}