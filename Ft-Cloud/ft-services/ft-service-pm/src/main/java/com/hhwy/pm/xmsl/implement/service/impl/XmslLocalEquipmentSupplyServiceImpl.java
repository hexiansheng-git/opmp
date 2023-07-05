package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.pm.xmsl.implement.domain.XmslLocalEquipmentSupply;
import com.hhwy.pm.xmsl.implement.mapper.XmslLocalEquipmentSupplyMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslLocalEquipmentSupplyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:47
 * @remark 当地资源供应-属地设备供应情况
 */
@Service
public class XmslLocalEquipmentSupplyServiceImpl implements IXmslLocalEquipmentSupplyService {

    @Autowired
    private XmslLocalEquipmentSupplyMapper xmslLocalEquipmentSupplyMapper;

    @Override
    public List<XmslLocalEquipmentSupply> getXmslLocalEquipmentSupplyList(
        XmslLocalEquipmentSupply xmslLocalEquipmentSupply) {
        return xmslLocalEquipmentSupplyMapper.getXmslLocalEquipmentSupplyList(xmslLocalEquipmentSupply);
    }

    @Transactional
    public int deleteXmslLocalEquipmentSupplyByPks(List<Long> xmslLocalEquipmentSupplyPkList) {
        return xmslLocalEquipmentSupplyMapper.deleteXmslLocalEquipmentSupplyByPks(xmslLocalEquipmentSupplyPkList);
    }
}
