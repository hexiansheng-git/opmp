package com.hhwy.pm.xmsl.wbs;


import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * wbs缓存
 */
public class WbsRedisUtils {
    private static RedisUtils redisUtils;
    static{
        redisUtils = SpringUtils.getBean(RedisUtils.class);
    }
    //hashMap  wbs::租户标志  wbsId  wbsjson
    public static final String KEY = "WBS::";
    //hashMap  WBS::child_id::租户标志   wbsId  子级Id(多个以逗号拼接)
    public static final String CHILD_KEY = "WBS::child_id::";

    /**
     * 获取某项目下所有的wbs
     * @param tenantKey
     * @return
     */
    public static List<XmslWbs> allWbs(String tenantKey){
        String key = WbsRedisUtils.getKey(tenantKey);
        Map<Object, Object> map = redisUtils.hGetAll(key);
        if(MapUtils.isEmpty(map))
            return new ArrayList<>(2);
        Collection<Object> valColls = map.values();
        List<XmslWbs> wbsList = new CopyOnWriteArrayList<>();
        valColls.parallelStream().forEach(r->{
            wbsList.add(JSONObject.parseObject(r.toString(),XmslWbs.class));
        });
        return wbsList;
    }
    public static List<XmslWbs> allWbs(){
        String tenantKey = SecurityUtils.getTenantKey();
        return WbsRedisUtils.allWbs(tenantKey);
    }

    /**
     * 获取指定wbs的所有子级
     * @param tenantKey
     * @param wbsId
     * @return
     */
    public static Long[] getChildWbsId(String tenantKey,String wbsId){
        String key = getChildKey(tenantKey);
        Object childIdObj = redisUtils.hGet(key,wbsId);
        if(ObjectUtils.isEmpty(childIdObj))
            return null;
        return Convert.toLongArray(childIdObj);
    }
    public static Long[] getChildWbsId(String wbsId){
        String tenantKey = SecurityUtils.getTenantKey();
        return WbsRedisUtils.getChildWbsId(tenantKey,wbsId);
    }

    /**
     * 获取wbs redisKey
     * @param tenantKey
     * @return
     */
    public static String getKey(String tenantKey){
        return WbsRedisUtils.KEY + tenantKey;
    }

    /**
     * 子级wbskey
     * @param tenantKey
     * @return
     */
    public static String getChildKey(String tenantKey){
        return WbsRedisUtils.CHILD_KEY + tenantKey;
    }

}
