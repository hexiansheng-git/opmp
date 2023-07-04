package com.hhwy.pm.xmsl.implement.service;

import com.hhwy.pm.xmsl.implement.domain.LocalResourceSupply;

/**
 * @author zhenglili
 * @date 2023-07-04 13:15:00
 * @remark 当地资源供应
 */
public interface ILocalResourceSupplyService {

    LocalResourceSupply getList(LocalResourceSupply localResourceSupply);

    void save(LocalResourceSupply localResourceSupply);
}
