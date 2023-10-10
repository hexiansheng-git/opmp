package com.hhwy.system.config;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.domain.base.system.material.MaterialCategory;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.system.core.service.ISysDictTypeService;
import com.hhwy.system.service.IMaterialCategoryService;
import com.hhwy.system.service.IMaterialInfoService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Configuration
public class SpringbootReadyEvent {
    private static final Logger log= LoggerFactory.getLogger(SpringbootReadyEvent.class);
    @Autowired
    private IMaterialInfoService materialInfoService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IMaterialCategoryService materialCategoryService;
    @Autowired
    private ISysDictTypeService sysDictTypeService;

    @EventListener({ApplicationReadyEvent.class})
    void initMethod(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();//单一线程池
        executorService.execute(() -> {
            //材料编码redis
            dealWithMaterial();
            //材料编码分类redis
            dealWithCategory();
        });

    }
    // 材料编码存入redis
    private void dealWithMaterial(){
        if(!redisUtils.hasKey(PmsConstant.MATERIALREDISKEY)){
                int i = materialInfoService.selectCount();
                double pageNum = Math.ceil((double)i / 10000);
                int pageSize=10000;
                for (int j = 1; j <=pageNum ; j++) {
                    MaterialInfo info = new MaterialInfo();
                    info.setPageNum((j - 1) * pageSize);
                    info.setPageSize(pageSize);
                    info.setIsFalg("1");
                    List<MaterialInfo> list = materialInfoService.selectMaterialInfoList(info);
                    Iterator<MaterialInfo> its = list.iterator();
                    while (its.hasNext()) {
                        MaterialInfo temp = its.next();
                        redisUtils.lRightPush("materialInfoLrange", JSONObject.toJSONString(temp));
                        redisUtils.hPut(PmsConstant.MATERIALREDISKEY, temp.getMaterialCode(), JSONObject.toJSONString(temp));
                    }
                }
        }
    }
    // 材料编码分类存入redis
    private void dealWithCategory(){
        boolean hasCategory = redisUtils.hasKey(PmsConstant.CATEGORYREDISKEY);   //物资分类
        boolean hasCategory16 = redisUtils.hasKey(PmsConstant.CATEGORYREDISKEY_16); //物资16大类
        if(hasCategory && hasCategory16)
            return ;
        //16类redis map  分类编码 : 汇总码（多个以逗号隔开） 
        Map<String,String> category16Map = getCategory16Map();
        Map<String,String> category16DataMap = new HashMap<>(17);
        int i = materialCategoryService.selectCount();
        double pageNum = Math.ceil((double)i / 1000);
        int pageSize=1000;
        for (int j = 1; j <=pageNum ; j++) {
            MaterialCategory info = new MaterialCategory();
            info.setPageNum((j - 1) * pageSize);
            info.setPageSize(pageSize);
            List<MaterialCategory> list = materialCategoryService.newSelectMaterialCategoryList(info);
            Iterator<MaterialCategory> its = list.iterator();
            while (its.hasNext()) {
                MaterialCategory temp = its.next();
                if(!hasCategory) //存放物资分类缓存
                    redisUtils.hPut(PmsConstant.CATEGORYREDISKEY, temp.getCategoryCode(), JSONObject.toJSONString(temp));
                if(temp.getType() < 1 && temp.getType() > 3)   //16大类只统计 0-3 
                    continue;
                //物资编码归属的 16类 缓存
                if(!hasCategory16){
                    Iterator<String> cateIter = category16Map.keySet().iterator();
                    while(cateIter.hasNext()){
                        String matchCategory = cateIter.next();
                        if(!temp.getCategoryCode().startsWith(matchCategory))
                            continue;
                        String[] summaryCodes = category16Map.get(matchCategory).split(","); // 同一个分类编码可能归属于多个16类
                        for (int k = 0; k < summaryCodes.length; k++) {
                            ObjectUtils.add2StrMap(category16DataMap,summaryCodes[k],temp.getCategoryCode());
                        }
                    }
                }
            }
        }
        if(!hasCategory16 && category16DataMap.size() > 0)
            redisUtils.hPutAll(PmsConstant.CATEGORYREDISKEY_16,category16DataMap);
    }

    private void initWbs(){

    }

    /**
     * 获取物资16大类map
     * @return {物资分类编码 : 物资分类编码}
     */
    private Map<String,String> getCategory16Map(){
//        List<SysDictData> dictList = sysDictTypeService.selectDictDataByType("xcwz_buy_summary");
//        if(CollectionUtils.isEmpty(dictList)){
//            log.debug("购进消费库存统计表,字典项xcwz_buy_summary未配置，无法初始化数据");
//            return null;
//        }
//        Map<String,String> dictMap = new HashMap<>();
//        //格式化 > 分类编码 : 汇总码（多个以逗号隔开）
//        for (int i = 0; i < dictList.size(); i++) {
//            SysDictData temp = dictList.get(i);
////            if(temp.getDictValue().equals("SP")) //特殊编码则默认为配件分类编码库
////                temp.setDictValue("1301");
//            Set<String> set = new HashSet(Arrays.asList(temp.getRemark().split(",")));
//            for(String k : set){
//                ObjectUtils.add2StrMap(dictMap,k,temp.getDictValue());
//            }
//        }
//        return dictMap;
        return new HashMap<>();
    }
    
}
