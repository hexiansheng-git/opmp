package com.hhwy.sp.core.system;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.system.api.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *  简化调用SystemApi接口 
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2023/9/8 15:58   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2023/9/8 15:58    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
@Component
@Slf4j
public class SystemApiService {
    @Autowired
    private SystemServiceApi systemServiceApi;

    /**
     * 
     * @param userName
     * @return
     */
    public List<SysUser> selectUserListByUsernames(String userName){
        R<List<SysUser>> r = systemServiceApi.selectUserListByUsernames(userName);
        if(r.getCode() != R.SUCCESS){
            log.info("从system获取用户信息失败:"+r.getMsg());
            return new ArrayList<>(2);
        }
        return r.getData();
    }

    /**
     *
     * @param userName
     * @return
     */
    public List<SysUser> selectUserListByUsernames(String userName, String tenantKey){
        R<List<SysUser>> r = systemServiceApi.selectUserListByUsernames(userName, tenantKey);
        if(r.getCode() != R.SUCCESS){
            log.info("从system获取用户信息失败:"+r.getMsg());
            return new ArrayList<>(2);
        }
        return r.getData();
    }

    /**
     * 查询字典项
     * @param dictType 字典key
     * @return
     */
    public List<SysDictData> selectDictDataByType(String dictType){
        AjaxResult result = systemServiceApi.dictType(dictType);
        List<SysDictData> list = JSONObject.parseArray(JSONObject.toJSONString(result.get("data")),SysDictData.class);
        return list;
    }

    
    
    
}
