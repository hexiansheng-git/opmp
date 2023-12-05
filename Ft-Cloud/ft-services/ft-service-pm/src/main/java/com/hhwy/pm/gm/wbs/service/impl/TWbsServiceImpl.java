package com.hhwy.pm.gm.wbs.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.convert.Convert;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.UUIDUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.gm.wbs.mapper.TWbsMapper;
import com.hhwy.pm.gm.wbs.service.ITWbsService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * wbs
 * @author wk
 * @date 2023-08-01 11:26:43
 * @remark
 */
@Service
public class TWbsServiceImpl implements ITWbsService {
    @Autowired
    private TWbsMapper tWbsMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;


    public TWbs getTWbs(TWbs tWbs) {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            return tWbsMapper.getTWbs(tWbs);
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    @Override
    public List<TWbs> getTWbsListByMainId(Long id) {
        if(id == null)
            return new ArrayList<>();
        TWbs tWbs = new TWbs();
        tWbs.setMainId(id);
        return tWbsMapper.getTWbsList(tWbs);
    }

    public List<TWbs> getTWbsList(TWbs tWbs) {
        return tWbsMapper.getTWbsList(tWbs);
    }

    @Override
    public List<TWbs> lazySearchList(TWbs wbs) {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            boolean hasCondition = StringUtils.isNotBlank(wbs.getNodeType()) || StringUtils.isNotBlank(wbs.getName());
//        if(hasCondition && (StringUtils.trim(wbs.getNodeType())+StringUtils.trim(wbs.getName())).length() < 3)
//            throw new RuntimeException("搜索参数过小");
            if(!hasCondition){
                List<TWbs> list = tWbsMapper.getTWbsList(wbs);
                return list;
            }
            //如果是懒加载,找出满足条件的id，扔redis
            String key = "twbs::lazySearch_"+SecurityUtils.getTenantKey()+
                    StringUtils.join(new String[]{wbs.getName(),wbs.getNodeType()},",");
            //获取ids
            Set<String> idSet = null;
            if(!redisUtils.hasKey(key) ){
                String parentId = wbs.getParentId();
                wbs.setParentId(null);
                List<TWbs> list = tWbsMapper.getTWbsId(wbs);
                final Set<String> resuIdSet = new ConcurrentHashSet<>();
                list.parallelStream().forEach(r->{
                    if(r == null || StringUtils.isBlank(r.getAncestors()))
                        return ;
                    resuIdSet.addAll(Arrays.asList(Convert.toStrArray(r.getAncestors())));
                });
                wbs.setParentId(parentId);
                if(resuIdSet.size() < 1)
                    resuIdSet.add("-1");
                idSet = resuIdSet.stream().map(r->r+"").collect(Collectors.toSet());
                redisUtils.sAdd(key,idSet.toArray(new String[]{}));
                redisUtils.expire(key,10, TimeUnit.MINUTES);
            }else{
                idSet = redisUtils.sMembers(key);
            }
            TWbs queryWbs = new TWbs();
            queryWbs.setParams(ObjectUtils.toMap("ids", idSet));
            queryWbs.setMainId(wbs.getMainId());
            queryWbs.setParentId(wbs.getParentId());
            List<TWbs> list = tWbsMapper.getTWbsList(queryWbs);
            return list;
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
        
        
    }

    @Override
    public List<TWbs> wbsTreeList(Map map) {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey("master");
        DynamicDataSourceContextHolder.push(dataSource);
        List<TWbs> resuList = new ArrayList<>();
        try {
            String engineeringType = null;
            Object typeObj = map.get("engineeringType");
            if(typeObj != null){
                engineeringType = (String) typeObj;
            }
            if(StringUtils.isBlank(engineeringType)){
                engineeringType = this.getDefaultEngineeringType();
            }
            if(StringUtils.isBlank(engineeringType)){
                return new ArrayList<>(2);
            }

            Long mainId = tWbsMapper.getEffectMainIdByType(engineeringType);
            if(mainId == null)
                return new ArrayList<>(2);
            TWbs query = new TWbs();
            query.setMainId(mainId);
            query.setId(map.get("standardId") != null?map.get("standardId").toString():null);
            query.setCode(map.get("code")!=null?map.get("code").toString():null);
            query.setName(map.get("name")!=null?map.get("name").toString():null);
            List<TWbs> list = this.getTWbsList(query);
            //查询出祖级对象
            Set<Long> pidSet = new HashSet<>();
            for (int i = 0; i < list.size(); i++) {
                TWbs temp = list.get(i);
                List<Long> pidList = StringUtils.isBlank(temp.getAncestors())?new ArrayList<>(2):Arrays.asList(com.hhwy.common.core.text.Convert.toLongArray(temp.getAncestors()));
                pidList.remove(temp.getId());
                pidSet.addAll(pidList);
            }
            if(CollectionUtils.isNotEmpty(pidSet)){
                query = new TWbs();
                query.setParams(ObjectUtils.toMap("ids",pidSet));
                List<TWbs> tempList = this.getTWbsList(query);
                list.addAll(0,tempList);
            }
            //转树形
            Map<String,TWbs> wbsMap = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                TWbs temp = list.get(i);
                wbsMap.put(temp.getId(),temp);
            }
            for (int i = 0; i < list.size(); i++) {
                TWbs temp = list.get(i);
                if(temp.getLevel()==1){
                    resuList.add(temp);
                    continue;
                }
                TWbs parent = wbsMap.get(temp.getParentId());
                if(parent==null)
                    continue;
                if(parent.getChildren() == null){
                    parent.setChildren(new ArrayList<>(10));
                }
                parent.getChildren().add(temp);
            }
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
        return resuList;
    }

    @Override
    public Map<String, List<TWbs>> copyChildList(Long[] ids) {
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            //子级id : 最上级id
            Map<String,String> realIdMap = new HashMap<>();
            Map<String,List<TWbs>> resuMap = new HashMap<>();
            List<Long> idList = new ArrayList<>();
            idList.addAll(Arrays.asList(ids));
            //旧Id : 新的UUID
            Map<String,String> newIdMap = new HashMap<>();
            //遍历5级查找
            for (int i = 0; i < 5; i++) {
                List<TWbs> tempList = tWbsMapper.getTWbsParentList(idList.toArray(new Long[]{}));
                if(CollectionUtils.isEmpty(tempList))
                    break;
                idList.clear();
                for (int j = 0; j < tempList.size(); j++) {
                    TWbs temp = tempList.get(j);
                    String topId = i==0?temp.getParentId():realIdMap.get(temp.getParentId());
                    idList.add(Long.valueOf(temp.getId()));
                    realIdMap.put(temp.getId(), topId);
                    //替换掉Id和父级Id，否则前端id会重
                    ObjectUtils.add2MapList(resuMap,topId,temp);
                    String newId = UUIDUtils.getShortUuid();
                    newIdMap.put(temp.getId(), newId);
                    temp.setId(newId);
                    temp.setParentId(i==0?temp.getParentId(): ObjectUtils.nvlString(newIdMap.get(temp.getParentId())));
                }
            }
            return resuMap;
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    @Override
    public List<TWbs> wbsListByType(String type,String name, String nodeType, Long parentId) {
        //获取项目的产品类型
//        ProjectBasicInfo projectBasicInfo =  projectBasicInfoService.projectInfo();
        if(StringUtils.isBlank(type) )
            return new ArrayList<>(2);
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            Long mainId = tWbsMapper.getEffectMainIdByType(type);
            if(mainId == null)
                return new ArrayList<>(2);
            TWbs query = new TWbs();
            query.setMainId(mainId);
            query.setParentId((parentId==null||parentId<0)?"-1":parentId+"");
            query.setName(name);
            query.setNodeType(nodeType);
            List<TWbs> list = this.lazySearchList(query);
            return list;
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    @Override
    public String getDefaultEngineeringType() {
        ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
        String type = projectBasicInfo.getBusinessAreasAndProducts();
        if(StringUtils.isBlank(type))
            return "";
        String[] types = Convert.toStrArray(type);
        String enType = tWbsMapper.getEffectEngineeringTypeByProType(types);
        return ObjectUtils.nvlString(enType);
    }

    @Override
    public Map<String, TWbs> getTWbsByPrjWbsCode(Set<String> set) {
        List<XmslWbs> wbsList = WbsRedisUtils.getWbsByCodes(set);
        if(CollectionUtils.isEmpty(wbsList))
            return new HashMap<>(2);
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            Set<Long> wbsIdList = wbsList.stream().map(XmslWbs::getStandardId).filter(r->r!=null).collect(Collectors.toSet());
            List<TWbs> tWbsList = getTWbsByIds(wbsIdList);
            if(CollectionUtils.isEmpty(tWbsList))
                return new HashMap<>(2);
            Map<String,TWbs> tWbsMap = tWbsList.stream().collect(Collectors.toMap(TWbs::getId,r->r));
            Map<String, TWbs> resuMap = new HashMap<>();
            wbsList.stream().forEach(r->{
                resuMap.put(r.getCode(),r.getStandardId()!=null?tWbsMap.get(r.getStandardId()+""):null);
            });
            return resuMap;
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }


    }

    public List<TWbs> getTWbsByIds(Collection<Long> ids){
        if(CollectionUtils.isEmpty(ids))
            return new ArrayList<>(2);
        TWbs query = new TWbs();
        query.setParams(ObjectUtils.toMap("ids",ids));
        return this.tWbsMapper.getTWbsList(query);
    }

    @Transactional
    public int insertTWbs(TWbs tWbs) {
        tWbs.setId(IdWorker.createId()+"");
        tWbs.setCreateUser(SecurityUtils.getUserName());
        tWbs.setCreateTime(DateUtils.getNowDate());
        return tWbsMapper.insertTWbs(tWbs);
    }

    @Transactional
    public int insertTWbsList(List<TWbs> tWbsList) {
        return tWbsMapper.insertTWbsList(tWbsList);
    }

    @Transactional
    public int updateTWbs(TWbs tWbs) {
        tWbs.setUpdateUser(SecurityUtils.getUserName());
        tWbs.setUpdateTime(DateUtils.getNowDate());
        return tWbsMapper.updateTWbs(tWbs);
    }

    @Transactional
    public int updateTWbsList(List<TWbs> tWbsList) {
//        for (TWbs tWbs : tWbsList) {
//            tWbs.setUpdateUser(SecurityUtils.getUserName());
//            tWbs.setUpdateTime(DateUtils.getNowDate());
//        }
        return tWbsMapper.updateTWbsList(tWbsList);
    }

    @Transactional
    public int deleteTWbs(TWbs tWbs) {
        tWbs.setUpdateUser(SecurityUtils.getUserName());
        tWbs.setUpdateTime(DateUtils.getNowDate());
        return tWbsMapper.deleteTWbs(tWbs);
    }

    @Override
    public int insertTWbsMain(Map map) {
        return tWbsMapper.insertTWbsMain(map);
    }

    @Override
    public int deleteTWbsMain(Long id) {
        return tWbsMapper.deleteTWbsMain(id);
    }

    @Transactional
    public int deleteTWbsByPks(List<Long> tWbsPkList) {
        return tWbsMapper.deleteTWbsByPks(tWbsPkList);
    }
    
}
