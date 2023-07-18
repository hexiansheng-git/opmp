package com.hhwy.domain.base.system;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName : UserPostInfo
 * @Description : TODO
 * @Author : zxb
 * @Date :  14:29
 * @Version : V1.0
 **/
public class UserPostInfo implements Serializable {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long userId;
    private String postIds;
    private String postCodes;
    private String postNames;

    @Transient
    private List<Long> userIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPostIds() {
        return postIds;
    }

    public void setPostIds(String postIds) {
        this.postIds = postIds;
    }

    public String getPostCodes() {
        return postCodes;
    }

    public void setPostCodes(String postCodes) {
        this.postCodes = postCodes;
    }

    public String getPostNames() {
        return postNames;
    }

    public void setPostNames(String postNames) {
        this.postNames = postNames;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
