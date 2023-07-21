package com.hhwy.pm.qqch.utils;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.pm.common.mapper.CommonMapper;

import java.math.BigDecimal;

public class VersionUtil {
    private static CommonMapper commonMapper;

    static {
        commonMapper = SpringUtils.getBean(CommonMapper.class);
    }

    /**
     * 获取版本
     * @param tableName
     * @param version
     * @return
     */
    public static BigDecimal getVersion(String tableName,BigDecimal version){
        if(version == null){
            /*查询当前最大有效版本*/
            version = commonMapper.selectMaxVersion(tableName);
        }else {
            /*查询当前最接近（小于等于）指定版本的版本号*/
            version = commonMapper.selectLessOrEqualAssignVersion(tableName,version);
        }
        return version;
    }
}
