package com.hhwy.pm.xmsl.wbs.push.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 *  作业推送p6bean
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2024/5/11 10:28   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2024/5/11 10:28    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
@Data
public class P6WorkBean {

    private String objectId; //p6编码

    private String id;      //作业编码(当前层级)
    private String name;    //作业名称
    private String reason;  //推送失败原因

    public P6WorkBean() {
    }

    public P6WorkBean(String id, String name,String objectId) {
        this.objectId = objectId;
        this.id = id;
        this.name = name;
    }
}
