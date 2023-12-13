package com.hhwy.log.constant;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @ClassName : InterFaceConstant
 * @Description : 接口调用常量
 * @Author : zxb
 * @Date :  10:07
 * @Version : V1.0
 **/
public class InterfaceConstant {

    //接口调用标识+描述 update: zxb 2021-09-04
//    public static Map<String, String> INTERFACES = null;
    public static Map<String, String> INTERFACES = ImmutableMap.<String, String>builder()
            .put("schemaService","竹云--获取机构")
            .put("orgCreateService","竹云--创建组织")
            .put("orgUpdateService","竹云--修改组织")
            .put("userCreateService","竹云--用户创建")
            .put("userUpdateService","竹云--用户修改")
            .build();
}
