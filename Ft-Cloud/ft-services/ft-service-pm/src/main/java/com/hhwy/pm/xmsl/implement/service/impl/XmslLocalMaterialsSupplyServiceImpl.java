package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.pm.xmsl.implement.mapper.XmslLocalMaterialsSupplyMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslLocalMaterialsSupplyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:53
 * @remark 当地资源供应-属地物资供应情况
 */
@Service
public class XmslLocalMaterialsSupplyServiceImpl implements IXmslLocalMaterialsSupplyService {

    @Autowired
    private XmslLocalMaterialsSupplyMapper xmslLocalMaterialsSupplyMapper;

    @Transactional
    public int deleteXmslLocalMaterialsSupplyByPks(List<Long> xmslLocalMaterialsSupplyPkList) {
        return xmslLocalMaterialsSupplyMapper
            .deleteXmslLocalMaterialsSupplyByPks(xmslLocalMaterialsSupplyPkList);
    }
}
