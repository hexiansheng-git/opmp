package com.hhwy.log.mapper;

import com.hhwy.log.domain.Demo;

/**
 * 参数配置 数据层
 *
 * @author hhwy
 */
public interface DemoMapper {
    int cs (String sql);

    int add(Demo demo);
}