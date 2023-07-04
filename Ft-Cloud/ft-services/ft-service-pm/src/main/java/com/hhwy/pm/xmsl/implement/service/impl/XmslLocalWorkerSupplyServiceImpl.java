package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.pm.xmsl.implement.mapper.XmslLocalWorkerSupplyMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslLocalWorkerSupplyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:57
 * @remark 属地工人供应情况
 */
@Service
public class XmslLocalWorkerSupplyServiceImpl implements IXmslLocalWorkerSupplyService {

    @Autowired
    private XmslLocalWorkerSupplyMapper xmslLocalWorkerSupplyMapper;

    @Transactional
    public int deleteXmslLocalWorkerSupplyByPks(List<Long> xmslLocalWorkerSupplyPkList) {
        return xmslLocalWorkerSupplyMapper.deleteXmslLocalWorkerSupplyByPks(xmslLocalWorkerSupplyPkList);
    }
}
