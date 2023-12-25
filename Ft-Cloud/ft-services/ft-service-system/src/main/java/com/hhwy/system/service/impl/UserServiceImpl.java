package com.hhwy.system.service.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.system.SelfUserInfo;
import com.hhwy.domain.base.system.UserPostInfo;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.api.domain.SysRole;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.api.model.LoginUser;
import com.hhwy.system.core.domain.SysUserRole;
import com.hhwy.system.core.mapper.SysUserMapper;
import com.hhwy.system.core.mapper.SysUserRoleMapper;
import com.hhwy.system.core.service.ISysUserService;
import com.hhwy.system.mapper.UserMapper;
import com.hhwy.system.service.IDeptService;
import com.hhwy.system.service.IRoleService;
import com.hhwy.system.service.IUserService;
import com.hhwy.system.utils.redis.SysRedisUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName : UserServiceImpl
 * @Description : TODO
 * @Author : zxb
 * @Date :  14:23
 * @Version : V1.0
 **/
@Service
@RefreshScope
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysRedisUtils redisUtils;

    @Autowired
    private ISysUserService userService;

    //向总部推送角色接口地址
    @Value("${pushGmRole.url}")
    private String pushGmRoleUrl;


    @Autowired
    private SysUserMapper sysUserMapper;



    private static Long max_size = 20l;

    @Override
    public List<SelfUserInfo> getUserInfoBy(SelfUserInfo selfUserInfo) {
//        String tenantKey = SecurityUtils.getTenantKey();
        //强制修改为master
        String tenantKey = "master";

        List<SelfUserInfo> list = userMapper.getUserInfoBy(selfUserInfo,tenantKey);
        if(CollectionUtils.isEmpty(list)){
            return new ArrayList<>();
        }
//        //处理人员岗位信息
//        List<Long> userIds = new ArrayList<>();
//        list.stream().forEachOrdered(t -> userIds.add(t.getUserId()));
//        UserPostInfo queryUserPostInfo = new UserPostInfo();
//        queryUserPostInfo.setUserIds(userIds);
//        List<UserPostInfo> userPostInfos = userMapper.getUserPostsBy(queryUserPostInfo);
//        if(!CollectionUtils.isEmpty(userPostInfos)){
//            Map<Long, List<UserPostInfo>> userPostInfoMap = userPostInfos.stream().collect(Collectors.groupingBy(UserPostInfo :: getUserId));
//            list.parallelStream().forEach(u -> {
//                List<UserPostInfo> userPostInfoList = userPostInfoMap.get(u.getUserId());
//                if(userPostInfoList != null){
//                    u.setPostNames(userPostInfoList.get(0).getPostNames());
//                    u.setPostIds(userPostInfoList.get(0).getPostIds());
//                }
//            });
//        }
        return list;
    }

    @Override
    public List<SelfUserInfo> getUserInfoBySameDept(SelfUserInfo selfUserInfo) {
        String tenantKey = SecurityUtils.getTenantKey();
        List<SelfUserInfo> list = userMapper.getUserInfoBy(selfUserInfo,tenantKey);
        if(CollectionUtils.isEmpty(list)){
            return new ArrayList<>();
        }
//        //处理人员岗位信息
//        List<Long> userIds = new ArrayList<>();
//        list.stream().forEachOrdered(t -> userIds.add(t.getUserId()));
//        UserPostInfo queryUserPostInfo = new UserPostInfo();
//        queryUserPostInfo.setUserIds(userIds);
//        List<UserPostInfo> userPostInfos = userMapper.getUserPostsBy(queryUserPostInfo);
//        if(!CollectionUtils.isEmpty(userPostInfos)){
//            Map<Long, List<UserPostInfo>> userPostInfoMap = userPostInfos.stream().collect(Collectors.groupingBy(UserPostInfo :: getUserId));
//            list.parallelStream().forEach(u -> {
//                List<UserPostInfo> userPostInfoList = userPostInfoMap.get(u.getUserId());
//                if(userPostInfoList != null){
//                    u.setPostNames(userPostInfoList.get(0).getPostNames());
//                    u.setPostIds(userPostInfoList.get(0).getPostIds());
//                }
//            });
//        }
        return list;
    }


    @Override
    public int updateRecentSelectUser(List<String> userIds) {
        //获取当前用户信息
        LoginUser loginUser = this.tokenService.getLoginUser();
        String key = "recentSelectUser:" + loginUser.getUserid();
        for(String userId : userIds){
            Long size = redisUtils.zZCard(key);
            if(size > max_size){
                //只保留20个，超出的，先删除一个，再加一个
                redisUtils.zRemoveRange(key, 0, 0);
                redisUtils.zAdd(key, userId, DateUtils.getNowDate().getTime());
                redisUtils.expire(key, 30, TimeUnit.DAYS);//直保留30天
            }else{
                redisUtils.zAdd(key, userId, DateUtils.getNowDate().getTime());
            }
        }
        return userIds.size();
    }
    //查询最近选择的用户信息
    @Override
    public List<SelfUserInfo> getRecentSelectUser() {
        List<SelfUserInfo> list = new ArrayList<>();

        //获取当前用户信息
        LoginUser loginUser = this.tokenService.getLoginUser();
        String key = "recentSelectUser:" + loginUser.getUserid();
        //获取前20个最近选择用户id
        Set<String> userIds = redisUtils.zRange(key, 0, max_size);
        if(CollectionUtils.isEmpty(userIds)){
            return  list;
        }
        //拿着用户id查询用户信息
        SelfUserInfo querySelfUserInfo = new SelfUserInfo();
        querySelfUserInfo.setUserIds(userIds);
        return this.getUserInfoBy(querySelfUserInfo);
    }

    @Override
    public List<SysUser> selectByUserIds(List<String> userIdList) {
        List<SysUser> list = userMapper.selectByUserIds(userIdList);
        return list;
    }

    @Override
    public List<SysUser> selectSysUserInfo(SysUser sysUser) {
        List<SysUser> userList = userMapper.selectSysUserInfo(sysUser);
        return userList;
    }

    /**
     * 根据用户名称查询4A编码
     *
     * @param map
     * @return
     */
    @Override
    public List<SysUser> select4AByUserNames(Map<String, String> map) {
        String userNames = map.get("userNames");//多个,分隔
        List<SysUser> list=userMapper.select4AByUserNames(map);
        return list;
    }

    @Override
    public List<SysUser> selectAllUser(List<SysDept> deptList) {
        return  userMapper.selectAllUser(deptList);
    }

    /**
     * 插入用户集合
     * @param sysUserList
     * @return
     */
    @Override
    public int insertSysUserList(List<SysUser> sysUserList) {
        return userMapper.insertSysUserList(sysUserList);
    }
//    @Autowired
//    private IUserService userService;
//
//    @Autowired
//    private IDeptService deptService;
//
//    @Autowired
//    private IRoleService roleService;
//
//    @Autowired
//    private SysUserRoleMapper sysUserRoleMapper;
//
//    public List<SysUser> testUser(String s) {
//        Map projectBasicInfo = JSON.parseObject(s, Map.class);
//        // 需要同步的领导账号
//
//        SysUser sysUser = JSONObject.parseObject(projectBasicInfo.get("sysUser").toString(),SysUser.class);
//        // 需要同步领导账号的租户的集合
//        List<String> tenantKeys = JSONObject.parseObject(projectBasicInfo.get("tenantKeys").toString(), ArrayList.class);
//        if(sysUser != null && !org.apache.commons.collections4.CollectionUtils.isEmpty(tenantKeys)) {
//
//            // 需要新增的用户
//            List<SysUser> sysUserList4Add = new ArrayList<>();
//            // 需要新增的用户与角色关系
//            List<SysUserRole> sysUserRoleList4Add = new ArrayList<>();
//            // 查询已存在该用户的租户用户数据
//            SysUser sysUser1 = new SysUser();
//            sysUser1.setUserName(sysUser.getUserName());
//            List<SysUser> sysUsers = userService.selectSysUserInfo(sysUser1);
//
//            // 查询每个租户默认的领导角色
//            SysRole sysRole = new SysRole();
//            sysRole.setRoleKey("common");
//            List<SysRole> sysRoles = roleService.list(sysRole);
//
//            // 获取每个租户的项目机构数据
//            List<SysDept> projectOrgInfo = deptService.getProjectOrgInfo();
//
//            for(String tenantKey : tenantKeys) {
//                // 过滤已存在的租户用户
//                if(!org.apache.commons.collections4.CollectionUtils.isEmpty(sysUsers)) {
//                    SysUser sysUserExist = sysUsers.stream().filter(vo -> tenantKey.equals(vo.getTenantKey())).findFirst().orElse(null);
//                    if(sysUserExist != null) {
//                        continue;
//                    }
//                }
//                // 复制领导用户数据
//                SysUser sysUser4Add = JSONObject.parseObject(JSONObject.toJSONString(sysUser), SysUser.class);
//                if(sysUser4Add != null) {
//
//                    // ID
//                    Long id = IdWorker.createId();
//                    sysUser4Add.setUserId(id);
//
//                    if(!org.apache.commons.collections4.CollectionUtils.isEmpty(sysRoles)) {
//                        //逻辑有问题暂时修改2023年9月13日17:27:37 todo
//                        //SysRole sysRole1 = sysRoles.stream().filter(vo -> tenantKey.equals(vo.getTenantKey())).findFirst().orElse(null);
//                        SysRole sysRole1 = sysRoles.get(0);
//                        // 默认角色
//                        if(sysRole1 != null) {
//
//                            // 权限id 维护
//                            Long[] roleIds = new Long[1];
//                            roleIds[0] = sysRole1.getRoleId();
//                            sysUser4Add.setRoleIds(roleIds);
//                            sysUser4Add.setTenantKey(tenantKey);
//                            sysUser4Add.setStatus("0");
//
//                            // 角色用户关系维护
//                            SysUserRole sysUserRole = new SysUserRole();
//                            sysUserRole.setUserId(id);
//                            sysUserRole.setRoleId(sysRole1.getRoleId());
//                            sysUserRole.setTenantKey(tenantKey);
//                            sysUserRoleList4Add.add(sysUserRole);
//
//                        }
//                    }
//                    if(!org.apache.commons.collections4.CollectionUtils.isEmpty(projectOrgInfo)) {
//                        SysDept sysDept = projectOrgInfo.stream().filter(vo -> tenantKey.equals(vo.getTenantKey())).findFirst().orElse(null);
//                        // 默认部门维护
//                        if(sysDept != null) sysUser4Add.setDeptId(null);
//                    }
//
//                    sysUserList4Add.add(sysUser4Add);
//                }
//            }
//            // 插入用户数据
//            if(!org.apache.commons.collections4.CollectionUtils.isEmpty(sysUserList4Add)) userService.insertSysUserList(sysUserList4Add);
//            // 插入权限关系数据
//            if(!org.apache.commons.collections4.CollectionUtils.isEmpty(sysUserRoleList4Add)) sysUserRoleMapper.batchUserRole(sysUserRoleList4Add);
//        }
//
//        return  null;
//    }

    @Override
    public List<SysUser> selectUserIdByTenant(String tenantKey) {
        return userMapper.selectUserIdByTenant(tenantKey);
    }

    @Override
    @Transactional
    public String batchInsert(List<SysUser> userList) {
        StringBuffer sb = new StringBuffer();
        sb.append("添加返回结果:");
        //需要在总部进行授权的用户
        ArrayList<String> roleUserList = new ArrayList<>();
        for(SysUser user:userList){
            if ("1".equals(this.userService.checkUserNameUnique(user.getUserName()))) {
                sb.append(user.getUserName()+"已存在,不进行添加").append(System.lineSeparator());
            } else {
                user.setCreateUser(SecurityUtils.getUserName());
                //暂时置空
                user.setUserId(null);
                user.setDeptId(null);
                Long [] roleIds= {2L};
                user.setRoleIds(roleIds);
                int i = this.userService.insertUser(user);
                sb.append(user.getUserName()+"添加成功").append(System.lineSeparator());
                roleUserList.add(user.getUserName());
            }
        }
        String tenantKey = SecurityUtils.getTenantKey();
        Map<String, Object> map = new HashMap<>();
        map.put("tenantKey",tenantKey);
        map.put("roleUserList",roleUserList);
        String res= HttpRequest.post(pushGmRoleUrl)
                .header("Content-Type","application/json")
                .body(JSON.toJSONString(map)).execute().body();
        sb.append(res);
        return sb.toString();
    }

    @Override
    public List<SysUser> selectUserList(SysUser user) {
        List<String> tenantKeyList = new ArrayList();
        tenantKeyList.add("master");
        return this.sysUserMapper.selectUserList(user, tenantKeyList);
    }


}
