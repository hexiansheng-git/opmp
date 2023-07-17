package com.hhwy.utils;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.domain.BaseEntity;
import com.hhwy.common.security.util.SecurityUtils;

/**
 * @ClassName : AddBaseInfoUtil
 * @Description : 对基本实体添加当前登录人、更新时间等
 * @Author : zxb
 * @Date :  16:09
 * @Version : V1.0
 **/
public class AddBaseInfoUtil<T extends BaseEntity> {
    public T t;

    public AddBaseInfoUtil(){}
    public AddBaseInfoUtil(T t){
        this.t = add(t);
    }
    public T add(T t){
        String currentUserId = String.valueOf(SecurityUtils.getUserId());
        t.setCreateUser(currentUserId);
        t.setUpdateUser(currentUserId);
        t.setUpdateTime(DateUtils.getNowDate());
        t.setCreateTime(DateUtils.getNowDate());
        t.setDelFlag("0");
        return t;
    }

    public T update(T t){
        String currentUserId = String.valueOf(SecurityUtils.getUserId());
        t.setUpdateUser(currentUserId);
        t.setUpdateTime(DateUtils.getNowDate());
        return t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
