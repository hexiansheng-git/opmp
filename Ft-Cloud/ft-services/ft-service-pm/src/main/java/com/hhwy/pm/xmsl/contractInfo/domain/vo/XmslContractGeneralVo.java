package com.hhwy.pm.xmsl.contractInfo.domain.vo;

import com.hhwy.pm.qyzs.manage.qyzsManageContCondition.domain.QyzsManageContCondition;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import lombok.Data;

import java.util.List;

/**
 * 功能：弹框List和列表list数据
 * 作者: fushudong
 * 时间: 2023/12/11
 */
@Data
public class XmslContractGeneralVo {

    /**
     * 字段描述：列表集合
     */
    List<XmslContractGeneral> alreadyList;

    /**
     * 字段描述：弹框选中的集合
     */
    List<QyzsManageContCondition> knowledgeList;
}