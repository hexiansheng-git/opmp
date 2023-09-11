package com.hhwy.pm.gm.wbs.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.convert.Convert;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.UUIDUtils;
import com.hhwy.common.datasource.utils.DataSourceUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.gm.wbs.mapper.TWbsMapper;
import com.hhwy.pm.gm.wbs.service.ITWbsService;
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


    public TWbs getTWbs(TWbs tWbs) {
        return tWbsMapper.getTWbs(tWbs);
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
    }

    @Override
    public Map<String, List<TWbs>> copyChildList(Long[] ids) {
        List<TWbs> historyList = tWbsMapper.getTWbsParentList(ids);
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
    }

    public void selectDbColumnList(String dataSource) {
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push(dataSource);
        try {
            
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }
    
    @Override
    public List<TWbs> wbsListByType(String engineeringType, String name, String nodeType, Long parentId) {
        if(StringUtils.isBlank(engineeringType))
            return new ArrayList<>(2);
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            Long mainId = tWbsMapper.getEffectMainIdByType(engineeringType);
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
