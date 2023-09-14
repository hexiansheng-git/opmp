package com.hhwy.pm.qqch.utils;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.common.mapper.CommonMapper;

import java.math.BigDecimal;

public class VersionUtil {

    private static CommonMapper commonMapper;

    static {
        commonMapper = SpringUtils.getBean(CommonMapper.class);
    }

    /**
     * 获取版本
     *
     * @param tableName 数据库表名
     * @param version   版本号
     * @return
     */
    public static BigDecimal getVersion(String tableName, BigDecimal version) {
        if (version == null) {
            /*查询当前最大有效版本*/
            version = commonMapper.selectMaxVersion(tableName);
        } else {
            /*查询当前最接近（小于等于）指定版本的版本号*/
            version = commonMapper.selectLessOrEqualAssignVersion(tableName, version);
            if(version == null){
                version = BigDecimal.ONE;
            }
        }
        return version;
    }

    /**
     * 获取版本，适用于多个功能公用一个表，通过类型区分
     *
     * @param tableName 数据库表名
     * @param version   版本号
     * @return
     */
    public static BigDecimal getVersionByType(String tableName, BigDecimal version, String type) {
        if (StringUtils.isBlank(type)) {
            return getVersion(tableName, version);
        }
        if (version == null) {
            /*查询当前最大有效版本*/
            version = commonMapper.selectMaxVersionByType(tableName, type);
        } else {
            /*查询当前最接近（小于等于）指定版本的版本号*/
            version = commonMapper.selectLessOrEqualAssignVersionByType(tableName, version, type);
        }
        return version;
    }
}
