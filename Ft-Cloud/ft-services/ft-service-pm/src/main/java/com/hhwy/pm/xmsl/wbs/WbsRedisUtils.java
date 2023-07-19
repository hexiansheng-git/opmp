package com.hhwy.pm.xmsl.wbs;


import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
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
    //hashMap  wbs::项目id  wbsId  wbsjson
    public static final String KEY = "WBS:";

    public static List<XmslWbs> allWbs(String prjId){
        String key = WbsRedisUtils.getKey(prjId);
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

    public static String getKey(String prjId){
        return WbsRedisUtils.KEY + prjId;
    }
}
