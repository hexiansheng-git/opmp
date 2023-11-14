package com.hhwy.pm.qqch.utils;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.qqchChange.service.IQqchChangeService;

import java.math.BigDecimal;

public class VersionUtil {

    private static CommonMapper commonMapper;
    private static IQqchChangeService qqchChangeService;

    static {
        commonMapper = SpringUtils.getBean(CommonMapper.class);
        qqchChangeService = SpringUtils.getBean(IQqchChangeService.class);
    }

    /**
     * 获取最大版本号
     * @param tableName
     * @return
     */
    public static BigDecimal getMaxVersion(String tableName) {
        return commonMapper.selectMaxVersion(tableName);
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
            version  = qqchChangeService.effectVersion();
        }
        /*查询当前最接近（小于等于）指定版本的版本号*/
        version = commonMapper.selectLessOrEqualAssignVersion(tableName, version);
        if(version == null){
            version = BigDecimal.ONE;
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
