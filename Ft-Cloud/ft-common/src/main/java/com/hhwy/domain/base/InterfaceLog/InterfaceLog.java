package com.hhwy.domain.base.InterfaceLog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hhwy.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * sys_interface_log
 *
 * @author lcf
 * @date 2022/10/25
 */
public class InterfaceLog extends BaseEntity {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 数据创建者 */
    private String createUser;

    /** 数据创建系统时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 数据修改者 */
    private String updateUser;

    /** 数据修改系统时间 */
    private Date updateTime;

    /** 数据删除者 */
    private String delUser;

    /** 数据删除系统时间 */
    private Date delTime;
    /** ip */
    private String operateIp;
    /** 响应码 */
    private long interfaceStatus;
    /** 接口名 */
    private String interfaceName;
    /** 接口描述 */
    private String interfaceDesc;
    /** 偏移量 */
    private String offset;
    /** 请求类型 */
    private String requestType;
    /** 请求路径 */
    private String requestUrl;

    /** 预留字段1 */
    private String var1;

    /** 预留字段1 */
    private String var2;

    /** 预留字段1 */
    private String var3;

    /** 是否有效0有效1无效 */
    private String delFlag;

    /** 备注/描述 */
    private String remark;

    /** 用户id **/
    private Long userId;

    /** 用户名称 **/
    private String userName;

    private String osName;

    private String osVersion;

    private String browser;

    private String req;

    private String res;
    /** 接口耗时 **/
    private String timeConsume;

    private String method;

    private String type;
    private String name;
    private String tenantkey;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTenantkey() {
        return this.tenantkey;
    }

    public void setTenantkey(final String tenantkey) {
        this.tenantkey = tenantkey;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTimeConsume() {
        return timeConsume;
    }

    public void setTimeConsume(String timeConsume) {
        this.timeConsume = timeConsume;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInterfaceDesc() {
        return interfaceDesc;
    }

    public void setInterfaceDesc(String interfaceDesc) {
        this.interfaceDesc = interfaceDesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getdelUser() {
        return delUser;
    }

    public void setdelUser(String delUser) {
        this.delUser = delUser;
    }

    public Date getdelTime() {
        return delTime;
    }

    public void setdelTime(Date delTime) {
        this.delTime = delTime;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public long getInterfaceStatus() {
        return interfaceStatus;
    }

    public void setInterfaceStatus(long interfaceStatus) {
        this.interfaceStatus = interfaceStatus;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }


    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar2() {
        return var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }

    public String getVar3() {
        return var3;
    }

    public void setVar3(String var3) {
        this.var3 = var3;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "InterfaceLog{" +
                "id=" + id +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", delUser='" + delUser + '\'' +
                ", delTime=" + delTime +
                ", operateIp='" + operateIp + '\'' +
                ", interfaceStatus=" + interfaceStatus +
                ", interfaceName='" + interfaceName + '\'' +
                ", offset='" + offset + '\'' +
                ", requestType='" + requestType + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", var1='" + var1 + '\'' +
                ", var2='" + var2 + '\'' +
                ", var3='" + var3 + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
