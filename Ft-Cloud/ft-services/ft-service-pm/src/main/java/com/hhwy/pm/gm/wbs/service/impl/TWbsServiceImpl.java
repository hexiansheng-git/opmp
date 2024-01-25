package com.hhwy.pm.gm.wbs.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
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
import java.util.function.BiFunction;
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
    public TWbs getTWbsByFullCode(String code) {
        if(StringUtils.isBlank(code))
            return null;
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            return tWbsMapper.getLatestTWbsByFullCode(code);
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }
    }

    @Override
    public TWbs getTWbsById(Long id) {
        if(id == null)
            return null;
        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            TWbs query = new TWbs();
            query.setId(id+"");
            return this.tWbsMapper.getTWbs(query);
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
            boolean hasCondition = StringUtils.isNotBlank(wbs.getNodeType()) || StringUtils.isNotBlank(wbs.getName()) || wbs.getMainId() != null;
//        if(hasCondition && (StringUtils.trim(wbs.getNodeType())+StringUtils.trim(wbs.getName())).length() < 3)
//            throw new RuntimeException("搜索参数过小");
            if(!hasCondition){
                List<TWbs> list = tWbsMapper.getTWbsList(wbs);
                return list;
            }
            //如果是懒加载,找出满足条件的id，扔redis
            String key = "twbs::lazySearch_"+SecurityUtils.getTenantKey()+
                    StringUtils.join(new String[]{wbs.getName(),wbs.getNodeType(),ObjectUtils.nvlLong(wbs.getMainId())+""},",");
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
        //当前项目的工程类型
        String currentProjEngType = this.getDefaultEngineeringType();
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
                engineeringType = currentProjEngType;
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
            Set<Long> idSet = list.stream().map(r->Long.valueOf(r.getId())).collect(Collectors.toSet());
            //查询出祖级对象
            Set<Long> pidSet = new HashSet<>();
            for (int i = 0; i < list.size(); i++) {
                TWbs temp = list.get(i);
                List<Long> pidList = StringUtils.isBlank(temp.getAncestors())?new ArrayList<>(2):Arrays.asList(Convert.toLongArray(temp.getAncestors()));
                pidList = pidList.stream().filter(r->!idSet.contains(r)).collect(Collectors.toList());
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
        public Map<String, List<TWbs>> copyChildList(String parentCode,Integer level,Integer rootNum,Integer num,Long[] ids) {
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        try {
            Map<String,String> idRelateMap = new HashMap<>();
            Map<String,TWbs> wbsMap = new HashMap<>();
            //id:当前子级生成流水号
            Map<String,Integer> sortNumMap = new HashMap<>();
            BiFunction<String,Integer,Integer> getSelfCodeSortNum = (id,start)->{
                Integer temp = sortNumMap.get(id);
                if(temp == null){
                    temp = start==null?0:start;
                    sortNumMap.put(id,temp);
                }
                sortNumMap.put( id,++temp);
                return temp;
            };
            BiFunction<Integer,Boolean,String> buildSelfCode = (sortNum,isRoot)->{ //序号，是否为根级
                return isRoot?sortNum+"00":String.format("%03d",sortNum);
            };
            Map<String,List<TWbs>> resuMap = new LinkedHashMap<>();
            List<Long> idList = new ArrayList<>(Arrays.asList(ids));
            idList.sort(CompareUtil::compare);
            Set<String> existIdSet = new HashSet<>();
            //遍历5级查找
            for (int i = 0; i < idList.size(); i++) {
                if(existIdSet.contains(idList.get(i)+""))
                    continue;
                String id = idList.get(i)+"";
                List<TWbs> wbsList = tWbsMapper.getAllChildTWbs(idList.get(i));
                List<TWbs> resuList = new ArrayList<>();
                wbsList = CollectionUtils.isEmpty(wbsList)?new ArrayList<>(2):wbsList;
                for (int j = 0; j < wbsList.size(); j++) {
                    TWbs temp = wbsList.get(j);
                    //若直属于idList，则处理父级编码
                    String selfCode = "";
                    boolean put2Resu = true;
                    if(temp.getId().equals(idList.get(i)+"")){
                        boolean isRoot = StringUtils.isBlank(parentCode);
                        temp.setParentCode(parentCode);
                        Integer startNum = isRoot?rootNum:num;
                        selfCode = buildSelfCode.apply(getSelfCodeSortNum.apply(temp.getParentCode(),startNum),isRoot);
                        temp.setLevel(level);
                        put2Resu = false;
                    }else if(temp.getLevel() != 1){ //非第一级
                        String newPid = idRelateMap.get(temp.getParentId());
                        TWbs parent = wbsMap.get(newPid);
                        temp.setParentCode(parent.getCode());
                        temp.setParentId(parent.getId());
                        temp.setLevel(parent.getLevel()+1);
                        selfCode = buildSelfCode.apply(getSelfCodeSortNum.apply(temp.getParentCode(),null),false);
                    }
                    temp.setSelfCode(selfCode);
                    String pcode = temp.getParentCode();
                    temp.setCode((StringUtils.isBlank(pcode)?"":pcode+"-")+ObjectUtils.nvlString(temp.getSelfCode()));
                    temp.setPtVar3(temp.getName());
                    temp.setName(ObjectUtils.nvlString(temp.getCode())+"-"+ObjectUtils.nvlString(temp.getName()));
                    //String newId = UUIDUtils.getShortUuid();
                    String newId = temp.getId();//前端又要求不付写
                    idRelateMap.put(temp.getId(),newId);
                    temp.setId(newId);
                    existIdSet.add(temp.getId());
                    wbsMap.put(temp.getId(),temp);
//                    if(temp.getLevel() != 2)
//                        temp.setParentCode(StringUtils.removeEnd(temp.getAncestorsName().replace(temp.getCode(),""),"-") );
//                    else
//                        temp.setParentCode("");
//                    idList.add(Long.valueOf(temp.getId()));
                    if(put2Resu)  //被选中的数据不用传给前端
                        resuList.add(temp);
                }
                resuMap.put(idList.get(i)+"",resuList);
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
            list.forEach(w->{
                w.setParentCode(StringUtils.removeEnd(w.getAncestorsName().replace(w.getCode(),""),"-") );
            });
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
        String enType = tWbsMapper.getEffectEngineeringTypeByProType(type);
        return ObjectUtils.nvlString(enType);
    }

    @Override
    public String getEngineeringTypeByMainId(Long mainId) {
        if(mainId == null)
            return null;
        return this.tWbsMapper.getEngineeringTypeByMainId(mainId);
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
