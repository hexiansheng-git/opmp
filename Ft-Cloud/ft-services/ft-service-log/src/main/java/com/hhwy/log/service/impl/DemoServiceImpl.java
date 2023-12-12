package com.hhwy.log.service.impl;

import com.hhwy.log.domain.Demo;
import com.hhwy.log.mapper.DemoMapper;
import com.hhwy.log.service.IDemoService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 参数配置 服务层实现
 *
 * @author hhwy
 */
@Service
public class DemoServiceImpl implements IDemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    @Transactional
    public Boolean test(){
        return true;
    }

    @Override
    public int add(Demo demo) {
        return demoMapper.add(demo);
    }

    //分布式事务测试
    @Override
    @GlobalTransactional
    public void seataTest() {

    }


}
