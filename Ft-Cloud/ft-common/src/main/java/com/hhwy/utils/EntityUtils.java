package com.hhwy.utils;

import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.utils.common.CommonBaseEntity;
import lombok.extern.slf4j.Slf4j;
import com.hhwy.common.security.util.SecurityUtils;

import java.util.Date;
import java.util.List;

/**
 * 设置实体类的 String createUser; String createUserName; Date createTime;
 * String updateUser; String updateUserName; Date updateTime;
 * String delFlag;
 * <p>
 * 使用时请注意类型
 *
 * @author mls
 */
@Slf4j
public class EntityUtils {


    /**
     * 设置新增编辑基本信息
     *
     * @param t
     * @param <T>
     */
    public static <T extends CommonBaseEntity> void setCreateInfo(T t) {

        try {

            SysUser sysUser = SecurityUtils.getSysUser();
            Long deptId = sysUser == null ? null : sysUser.getDeptId();
            String userId = String.valueOf(sysUser == null ? null : sysUser.getUserId());
            String userName = sysUser == null ? null : sysUser.getNickName();
            Date date = new Date();

            // 创建信息
            t.setCreateUser(userId);
            t.setCreateUserName(userName);
            t.setCreateTime(date);
            t.setDeptId(deptId);
            // 删除标识
            t.setDelFlag("0");
        } catch (Exception e) {
            log.error("设置创建信息异常", e);
        }


    }


    /**
     * 设置更新信息
     *
     * @param t
     * @param <T>
     */
    public static <T extends CommonBaseEntity> void setUpdateInfo(T t) {

        try {
            SysUser sysUser = SecurityUtils.getSysUser();
            String userId = String.valueOf(sysUser == null ? null : sysUser.getUserId());
            String userName = sysUser == null ? null : sysUser.getNickName();
            Date date = new Date();

            // 更新信息
            t.setUpdateUser(userId);
            t.setUpdateUserName(userName);
            t.setUpdateTime(date);
        } catch (Exception e) {
            log.error("设置更新信息异常", e);
        }


    }


    /**
     * 设置新增信息
     *
     * @param t
     * @param <T>
     */
    public static <T extends CommonBaseEntity> void setCreateUpdateInfo(T t) {

        try {
            SysUser sysUser = SecurityUtils.getSysUser();
            Long deptId = sysUser == null ? null : sysUser.getDeptId();
            String userId = String.valueOf(sysUser == null ? null : sysUser.getUserId());
            String userName = sysUser == null ? null : sysUser.getNickName();
            Date date = new Date();

            // 创建信息
            t.setCreateUser(userId);
            t.setCreateUserName(userName);
            t.setCreateTime(date);
            t.setDeptId(deptId);
            // 删除标识
            t.setDelFlag("0");


            // 更新信息
            t.setUpdateUser(userId);
            t.setUpdateUserName(userName);
            t.setUpdateTime(date);
        } catch (Exception e) {
            log.error("设置创建信息更新信息异常", e);
        }

    }


    /**
     * 批量设置新增信息
     *
     * @param list
     * @param <T>
     */
    public static <T extends CommonBaseEntity> void setCreateUpdateInfo(List<T> list) {

        try {
            SysUser sysUser = SecurityUtils.getSysUser();

            Long deptId = sysUser == null ? null : sysUser.getDeptId();
            String userId = String.valueOf(sysUser == null ? null : sysUser.getUserId());
            String userName = sysUser == null ? null : sysUser.getNickName();
            Date date = new Date();
            for (T t : list) {
                // 创建信息
                t.setCreateUser(userId);
                t.setCreateUserName(userName);
                t.setCreateTime(date);
                t.setDeptId(deptId);
                // 删除标识
                t.setDelFlag("0");


                // 更新信息
                t.setUpdateUser(userId);
                t.setUpdateUserName(userName);
                t.setUpdateTime(date);
            }
        } catch (Exception e) {
            log.error("设置创建信息更新信息异常", e);
        }

    }


}
