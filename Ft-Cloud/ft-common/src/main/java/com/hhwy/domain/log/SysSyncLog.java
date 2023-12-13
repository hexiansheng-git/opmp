package com.hhwy.domain.log;

import com.hhwy.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class SysSyncLog extends BaseEntity {
    //id
    private Long id;
    //删除时间
    private Date delTime;
    //删除人
    private String delUser;
    //接口名称
    private String interfaceName;
    //接口地址
    private String interfaceUrl;
    //功能名称
    private String funName;
    //请求参数
    private String req;
    //响应描述
    private String res;
    //响应码
    private String status;
    //备注
    private String remark;
    //业务唯一标识
    private String businessUnique;

}
