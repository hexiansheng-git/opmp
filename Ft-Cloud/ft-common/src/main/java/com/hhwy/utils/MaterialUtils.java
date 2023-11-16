package com.hhwy.utils;


import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.domain.base.system.material.MaterialCategory;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MaterialUtils {

    private static RedisUtils redisUtils;
    static{
        redisUtils = SpringUtils.getBean(RedisUtils.class);
    }

    public static MaterialInfo getMaterialInfoByCode(String code){
        if(StringUtils.isBlank(code))
            return new MaterialInfo();
        List<MaterialInfo> list = getMaterialInfoByCodes(code);
        if(CollectionUtils.isEmpty(list))
            return new MaterialInfo();
        return list.get(0);
    }

    public static boolean hasMaterialCode(String code){
        if(StringUtils.isBlank(code))
            return false;
        return redisUtils.hExists(PmsConstant.MATERIALREDISKEY,code);
    }
    
    public static List<MaterialInfo> getMaterialInfoByCodes(String codes){
        if(StringUtils.isBlank(codes))
            return new ArrayList<>(2); 
        String[] codeArray = codes.split(",");
        Set codeSet = new HashSet<>(Arrays.asList(codeArray));
        List<MaterialInfo> list = getMaterialInfoByCodes(codeSet);
        return list;
    }

    /**
     * 根据多个物资编码获取物资信息对象
     * @param codes 物资编码数组
     * @return 物资信息集合
     */
    public static List<MaterialInfo> getMaterialInfoByCodes(Collection codes){
        if(CollectionUtils.isEmpty(codes))
            return new ArrayList<>(2);
        List<Object> materObjList = redisUtils.hMultiGet(PmsConstant.MATERIALREDISKEY, codes);
        if(CollectionUtils.isEmpty(materObjList))
            return new ArrayList<>(2); 
        List<MaterialInfo> materialInfoList = materObjList.stream().filter(Objects::nonNull).map(r->{
            MaterialInfo materialInfo = JSONObject.parseObject(r.toString(), MaterialInfo.class);
            return materialInfo;
        }).collect(Collectors.toList());
        return materialInfoList;
    }

    public static Map<String,MaterialInfo> getMaterialInfoMapByCodes(Collection codes){
        if(CollectionUtils.isEmpty(codes))
            return new HashMap<>(2);
        List<Object> materObjList = redisUtils.hMultiGet(PmsConstant.MATERIALREDISKEY, codes);
        if(CollectionUtils.isEmpty(materObjList))
            return new HashMap<>(2);
        Map<String,MaterialInfo> materialInfoMap = new HashMap<>(materObjList.size());
        for (int i = 0; i < materObjList.size(); i++) {
            Object o = materObjList.get(i);
            if(ObjectUtils.isEmpty(o))
                continue;
            MaterialInfo materialInfo = JSONObject.parseObject(o.toString(), MaterialInfo.class);
            materialInfoMap.put(materialInfo.getMaterialCode(),materialInfo);
        }
        return materialInfoMap;
    }

    /**
     * 获取物资分类
     * @param codes
     * @return
     */
    public static Map<String, MaterialCategory> getCategoryByCodes(Collection codes){
        if(CollectionUtils.isEmpty(codes))
            return new HashMap<>(2);
        List<Object> materObjList = redisUtils.hMultiGet(PmsConstant.CATEGORYREDISKEY, codes);
        if(CollectionUtils.isEmpty(materObjList))
            return new HashMap<>(2);
        Map<String,MaterialCategory> materialInfoMap = new HashMap<>(materObjList.size());
        for (int i = 0; i < materObjList.size(); i++) {
            MaterialCategory category = JSONObject.parseObject(materObjList.get(i).toString(), MaterialCategory.class);
            materialInfoMap.put(category.getCategoryCode(),category);
        }
        return materialInfoMap;   
    }

    /**
     * 获取物资分类
     * @param code
     * @return
     */
    public static MaterialCategory getCategoryByCode(String code){
        Map<String,MaterialCategory> map = getCategoryByCodes(SetUtils.hashSet(code));
        return map==null?null:map.get(code);
    }

    /**
     * 获取物资分类名称
     * @param materCode 物资编码
     * @return
     */
    public static String getCategoryNameByMaterCode(String materCode){
        MaterialInfo materialInfo = MaterialUtils.getMaterialInfoByCode(materCode);
        if(materialInfo == null)
            return "";
        MaterialCategory category = MaterialUtils.getCategoryByCode(materialInfo.getCategoryCode());
        return category == null?"":category.getCategoryName();
    }

    
}
