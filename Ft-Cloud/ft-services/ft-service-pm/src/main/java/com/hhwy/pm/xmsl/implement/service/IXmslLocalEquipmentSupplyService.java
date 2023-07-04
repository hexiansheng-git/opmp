package com.hhwy.pm.xmsl.implement.service;

import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:47
 * @remark 当地资源供应-属地设备供应情况
 */
public interface IXmslLocalEquipmentSupplyService {
    int deleteXmslLocalEquipmentSupplyByPks(List<Long> xmslLocalEquipmentSupplyPkList);
}
