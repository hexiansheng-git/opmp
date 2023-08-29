package com.hhwy.pm.xmsl.wbs;


import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

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
    //子级ID hashMap  WBS::child_id::租户标志   wbsId  子级Id(多个以逗号拼接)
    public static final String CHILD_KEY = "WBS::child_id::";
    //直属子级ID hashMap  WBS::dire_child_id::租户标志   wbsId  直属子级Id(多个以逗号拼接)
    public static final String DIRE_CHILD_KEY = "WBS::dire_child_id::";
    //清单编号对应wbs编号   WBS::list_wbs::租户标志   L+清单编号 :: wbsId
    public static final String LIST_WBS_KEY = "WBS::list_wbs::";
    //wbs编号对应清单编号   WBS::list_wbs::租户标志   L+清单编号 :: wbsId
    public static final String WBS_LIST_KEY = "WBS::wbs_list::";
    
    //wbs编号对应清单编号   WBS::list_wbs::租户标志   L+清单编号 :: wbsId
//    public static final String WBS_LIST_KEY = "WBS_HIS::wbs_list::";


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

    public static List<XmslWbs> getWbs(Collection wbsIds){
        String tenantKey = SecurityUtils.getTenantKey();
        List<Object> wbsObjList = redisUtils.hMultiGet(WbsRedisUtils.getKey(tenantKey),wbsIds);
        List<XmslWbs> list = new ArrayList<>(wbsObjList.size());
        for (int i = 0; i < wbsObjList.size(); i++) {
            Object temp = wbsObjList.get(i);
            if(temp == null)
                continue;
            list.add(JSONObject.parseObject(temp.toString(),XmslWbs.class));
        }
        return list;
    }

    public static List<XmslWbs> getWbs(Long[] wbsIds){
        if(ArrayUtils.isEmpty(wbsIds))
            return new ArrayList<>();
        Collection<String> set = Arrays.stream(wbsIds).map(r->r.toString()).collect(Collectors.toSet());
        return getWbs(set);
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
     * 获取直属子级wbsId
     * @param wbsId
     * @return
     */
    public static Long[] getDireChildWbsId(String wbsId){
        String tenantKey = SecurityUtils.getTenantKey();
        String key = getDireChildKey(tenantKey);
        Object childIdObj = redisUtils.hGet(key,wbsId);
        if(ObjectUtils.isEmpty(childIdObj))
            return new Long[]{};
        return Convert.toLongArray(childIdObj);
    }

    public static List<XmslWbs> getDireChildWbs(String wbsId){
        Long[] wbsIds = WbsRedisUtils.getDireChildWbsId(wbsId);
        if(ArrayUtils.isEmpty(wbsIds))
            return new ArrayList<>(2);
        List<XmslWbs> list = WbsRedisUtils.getWbs(Arrays.asList(wbsIds));
        return list;
    }

    /**
     * 根据清单编号获取对应的wbs编号
     * @param listCode
     * @return
     */
    public static String[] getWbsCodeByListCode(String listCode){
        String tenantKey = SecurityUtils.getTenantKey();
        String key = getListWbsKey(tenantKey);
        Object childIdObj = redisUtils.hGet(key,listCode);
        if(ObjectUtils.isEmpty(childIdObj))
            return new String[]{};
        return Convert.toStrArray(childIdObj);
    }
    /**
     * 根据wbs编号获取对应的清单编号
     * @param wbsCode
     * @return
     */
    public static String[] getListCodeByWbsCode(String wbsCode){
        String tenantKey = SecurityUtils.getTenantKey();
        String key = getWbsListKey(tenantKey);
        Object childIdObj = redisUtils.hGet(key,wbsCode);
        if(ObjectUtils.isEmpty(childIdObj))
            return new String[]{};
        return Convert.toStrArray(childIdObj);
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

    /**
     * 直属子级wbskey
     * @param tenantKey
     * @return
     */
    public static String getDireChildKey(String tenantKey){
        return WbsRedisUtils.DIRE_CHILD_KEY + tenantKey;
    }
    public static String getListWbsKey(String tenantKey){
        return WbsRedisUtils.LIST_WBS_KEY+ tenantKey;
    }
    public static String getWbsListKey(String tenantKey){
        return WbsRedisUtils.WBS_LIST_KEY+ tenantKey;
    }

}
