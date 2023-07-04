package com.hhwy.pm.xmsl.implement.service;

import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:53
 * @remark 当地资源供应-属地物资供应情况
 */
public interface IXmslLocalMaterialsSupplyService {

    int deleteXmslLocalMaterialsSupplyByPks(List<Long> xmslLocalMaterialsSupplyPkList);
}
