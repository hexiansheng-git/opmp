package com.hhwy.system.service;

import com.hhwy.domain.base.system.SelfUserInfo;
import com.hhwy.system.api.domain.SysUser;

import java.util.List;
import java.util.Map;

public interface IUserService {

    List<SelfUserInfo> getUserInfoBy(SelfUserInfo selfUserInfo);

    int updateRecentSelectUser(List<String> userIds);

    List<SelfUserInfo> getRecentSelectUser();

    /**
     *根据userIds查询用户信息
     *
     * @param userIdList
     * @return
     */
    List<SysUser> selectByUserIds(List<String> userIdList);

    /**
     * 查询用户信息
     *
     * @param sysUser
     * @return
     */
    List<SysUser> selectSysUserInfo(SysUser sysUser);

    /**
     * 批量查询4A编码根据用户名称
     *
     * @param map
     * @return
     */
    List<SysUser> select4AByUserNames(Map<String, String> map);
}
