package com.hhwy.system.service;

import com.hhwy.domain.base.system.UserInfo;
import com.hhwy.system.api.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IUserService {

    List<UserInfo> getUserInfoBy(UserInfo userInfo);

    int updateRecentSelectUser(List<String> userIds);

    List<UserInfo> getRecentSelectUser();

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
