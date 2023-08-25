package com.hhwy.system.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.domain.base.system.SelfUserInfo;
import com.hhwy.domain.base.system.UserPostInfo;
import com.hhwy.system.api.domain.SysDept;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.api.model.LoginUser;
import com.hhwy.system.mapper.UserMapper;
import com.hhwy.system.service.IUserService;
import com.hhwy.system.utils.redis.SysRedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysRedisUtils redisUtils;

    private static Long max_size = 20l;

    @Override
    public List<SelfUserInfo> getUserInfoBy(SelfUserInfo selfUserInfo) {
        List<SelfUserInfo> list = userMapper.getUserInfoBy(selfUserInfo);
        if(CollectionUtils.isEmpty(list)){
            return new ArrayList<>();
        }
        //处理人员岗位信息
        List<Long> userIds = new ArrayList<>();
        list.stream().forEachOrdered(t -> userIds.add(t.getUserId()));
        UserPostInfo queryUserPostInfo = new UserPostInfo();
        queryUserPostInfo.setUserIds(userIds);
        List<UserPostInfo> userPostInfos = userMapper.getUserPostsBy(queryUserPostInfo);
        if(!CollectionUtils.isEmpty(userPostInfos)){
            Map<Long, List<UserPostInfo>> userPostInfoMap = userPostInfos.stream().collect(Collectors.groupingBy(UserPostInfo :: getUserId));
            list.parallelStream().forEach(u -> {
                List<UserPostInfo> userPostInfoList = userPostInfoMap.get(u.getUserId());
                if(userPostInfoList != null){
                    u.setPostNames(userPostInfoList.get(0).getPostNames());
                    u.setPostIds(userPostInfoList.get(0).getPostIds());
                }
            });
        }
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
}
