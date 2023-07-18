package com.hhwy.system.mapper;

import com.hhwy.domain.base.system.UserInfo;
import com.hhwy.domain.base.system.UserPostInfo;
import com.hhwy.system.api.domain.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    //获取所有的用户信息
    @Select("select user_name userName, nick_name nickName,user_id userId from sys_user where tenant_key='master'")
    List<Map<String, String>> getAllUserInfos();

    @Select("select user_name userName, nick_name nickName, pt_var_5 ptVar5 from sys_user where pt_var_5=#{tokenId4A} limit 1")
    Map<String, String> selectUserBy4A(@Param("tokenId4A") String tokenId4A);

    @Select({"<script>" +
            "select " +
                "u.user_id userId " +
                ",u.dept_id deptId " +
                ",u.user_name userName " +
                ",u.nick_name nickName " +
                ",u.sex sex " +
                ",u.phone_number phoneNumber " +
                ",d.dept_name deptName " +
            "from sys_user u " +
            "left join sys_dept d on d.dept_id=u.dept_id " +
            "where u.`status`=0 and u.del_flag=0 " +
            "<if test=\"userInfo.deptId != null\"> AND (u.dept_id = #{userInfo.deptId} OR u.dept_id IN ( SELECT t.dept_id FROM sys_dept t WHERE find_in_set(#{userInfo.deptId}, ancestors) )) </if>" +
            "<if test=\"userInfo.userIds != null\"> " +
            "    and u.user_id in " +
            "    <foreach item=\"item\" collection=\"userInfo.userIds\" open=\"(\" separator=\",\" close=\")\"> " +
            "         #{item} " +
            "    </foreach> " +
            "</if> " +
            "</script>"})
    List<UserInfo> getUserInfoBy(@Param("userInfo") UserInfo userInfo);

    @Select({"<script>" +
            "select  " +
                "u.user_id userId, " +
                "GROUP_CONCAT(p.post_id) postIds, " +
                "GROUP_CONCAT(p.post_code) postCodes, " +
                "GROUP_CONCAT(p.post_name) postNames " +
            "from sys_user_post sup " +
            "INNER join sys_user u on u.user_id=sup.user_id " +
            "INNER JOIN sys_post p on p.post_id=sup.post_id " +
            "<where> " +
            "     <if test=\"userPostInfo.userIds != null\"> " +
            "         and u.user_id in " +
            "         <foreach item=\"item\" collection=\"userPostInfo.userIds\" open=\"(\" separator=\",\" close=\")\"> " +
            "              #{item} " +
            "         </foreach> " +
            "     </if> " +
            "</where>" +
            "group by sup.user_id" +
            "</script>"})
    List<UserPostInfo> getUserPostsBy(@Param("userPostInfo") UserPostInfo userPostInfo);

    /**
     * 根据用户ids查询用户信息
     *
     * @param userIdList
     * @return
     */
    List<SysUser> selectByUserIds(@Param(value = "userIdList") List<String> userIdList);

    /**
     * 根据用户ids查询用户信息
     *
     * @param userIdList
     * @return
     */
    List<SysUser> selectByIdCards(@Param(value = "idCardList") List<String> userIdList);

    /**
     * 查询用户信息
     *
     * @param sysUser
     * @return
     */
    List<SysUser> selectSysUserInfo(SysUser sysUser);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int insertBathUser(@Param(value = "list") List<SysUser> list);

    /**
     * 批量修改
     *
     * @param list
     * @return
     */
    int bathUpdate(@Param(value = "dataList") List<SysUser> list);

    /**
     * 根据用户名称查询4A编码
     *
     * @param map
     * @return
     */
    List<SysUser> select4AByUserNames(Map<String, String> map);

    /**
     * 根据用户4A编码查询用户信息
     *
     * @param fourAList
     * @return
     */
    List<SysUser> selectBy4AList(@Param(value = "fourAList") List<String> fourAList);
}
