package com.hhwy.demo.service.impl;

import com.hhwy.demo.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 参数配置 服务层实现
 *
 * @author hhwy
 */
@Service
public class DemoServiceImpl implements IDemoService {

    @Autowired
    private com.hhwy.demo.mapper.DemoMapper demoMapper;


}
