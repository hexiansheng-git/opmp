package com.hhwy.pm.xmsl.wbs.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.UUIDUtils;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsHistory;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.dto.XmslWbsDto;
import com.hhwy.pm.xmsl.wbs.mapper.XmslWbsHistoryMapper;
import com.hhwy.pm.xmsl.wbs.mapper.XmslWbsMapper;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsHistoryService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.ThreadPoolUtil;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import com.hhwy.utils.redissonLock.RedissonLockUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import sun.security.pkcs11.wrapper.Functions;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
@Service
public class XmslWbsServiceImpl implements IXmslWbsService {
    private Logger logger= LoggerFactory.getLogger(XmslWbsServiceImpl.class);
    @Autowired
    private XmslWbsMapper xmslWbsMapper;
    @Resource
    private IXmslWbsMainService wbsMainService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private TokenService tokenService;
    @Resource
    private IXmslWbsHistoryService wbsHistoryService;
    @Resource
    private IXmslContractInfoService xmslContractInfoService;

    @Override
    public XmslWbs getByCode(String code) {
        if(StringUtils.isBlank(code))
            return null;
        XmslWbs xmslWbs = new XmslWbs();
        xmslWbs.setCode(code);
        return xmslWbsMapper.getXmslWbs(xmslWbs);
    }

    public XmslWbs getXmslWbs(XmslWbs xmslWbs) {
        return xmslWbsMapper.getXmslWbs(xmslWbs);
    }

    @Override
    public List<XmslWbs> latestWbsList(XmslWbs xmslWbs) {
        return xmslWbsMapper.latestWbsList(xmslWbs);
    }

    @Override
    public List<XmslWbs> latestWbsListSortLevel() {
        XmslWbs query = new XmslWbs();
        query.setParams(ObjectUtils.toMap("sortLevel","1"));
        return xmslWbsMapper.latestWbsList(query);
    }

    @Override
    public List<XmslWbs> getByMainId(Long mainId) {
        XmslWbs query = new XmslWbs();
        query.setMainId(mainId);
        XmslWbsMain main = wbsMainService.getById(mainId);
        if(main == null)
            return new ArrayList<>();
        query.setParams(ObjectUtils.toMap("tableName",main.getValid()==Constant.YES_INT?"xmsl_wbs":"xmsl_wbs_history"));
        List<XmslWbs> list = xmslWbsMapper.getXmslWbsList(query);
        return list;
    }

    @Override
    public Map listData(XmslWbs xmslWbs) {
        if(StringUtils.isBlank(xmslWbs.getParentId()) ){
            xmslWbs.setParentId("-1");
        }else{
            try{
                Long.valueOf(xmslWbs.getParentId());
            }catch(Exception e){
                return ObjectUtils.toMap("list",new ArrayList<>(2),"mainId",xmslWbs.getMainId());
            }
        }
        if(xmslWbs.getMainId() == null){
            XmslWbsMain wbsMain = wbsMainService.getEffect();
            if(wbsMain == null)
                return ObjectUtils.toMap("list",new ArrayList<>(2),"mainId","");
            xmslWbs.setMainId(wbsMain.getId());
        }
        //判断查询历史还是查询当前
        XmslWbsMain main = wbsMainService.getById(xmslWbs.getMainId());
        xmslWbs.setParams(xmslWbs.getParams()==null?new HashMap<>(1):xmslWbs.getParams());
        xmslWbs.getParams().put("tableName",main.getValid()==Constant.NO_INT?"xmsl_wbs_history":"xmsl_wbs");
        List<XmslWbs> list = xmslWbsMapper.getXmslWbsList(xmslWbs);
        //清单信息获取
        return ObjectUtils.toMap("list",list,"mainId",main.getId());
    }

    @Override
    public List<XmslWbs> latestData(XmslWbs wbs) {
        if(StringUtils.isBlank(wbs.getParentId()) )
            wbs.setParentId("-1");
        boolean hasCondition = StringUtils.isNotBlank(wbs.getCode()) || StringUtils.isNotBlank(wbs.getName());
        if(hasCondition && (StringUtils.trim(wbs.getCode())+StringUtils.trim(wbs.getCode())).length() < 3)
            throw new RuntimeException("搜索参数过小");
        if(!hasCondition){
            List<XmslWbs> list = xmslWbsMapper.latestWbsList(wbs);
            return list;
        }
        //如果是懒加载,找出满足条件的id，扔redis
        String key = "wbs::lazySearch_"+SecurityUtils.getTenantKey();
        //获取ids
        Set<String> idSet = null;
        if(!redisUtils.hasKey(key) ){
            List<XmslWbs> list = xmslWbsMapper.latestWbsId(wbs);
            final Set<String> resuIdSet = new ConcurrentHashSet<>();
            list.parallelStream().forEach(r->{
                resuIdSet.addAll(Arrays.asList(Convert.toStrArray(r.getAncestors())));
            });
            if(resuIdSet.size() < 1)
                resuIdSet.add("-1");
            redisUtils.sAdd(key,resuIdSet.toArray(new String[]{}));
            redisUtils.expire(key,10, TimeUnit.MINUTES);
            idSet = resuIdSet;
        }else{
            idSet = redisUtils.sMembers(key);
        }
        wbs.setParams(wbs.getParams()==null?new HashMap<>():wbs.getParams());
        wbs.getParams().put("ids",idSet);
        List<XmslWbs> list = xmslWbsMapper.latestWbsList(wbs);
        return list;
    }

    @Override
    public List<XmslWbs> getXmslWbsListByTname(XmslWbs xmslWbs) {
        return xmslWbsMapper.getXmslWbsList(xmslWbs);
    }

    
    @Override
    public List<XmslWbs> getXmslWbsList(XmslWbs xmslWbs) {
        xmslWbs.setParams(ObjectUtils.toMap("tableName","xmsl_wbs"));
        return getXmslWbsListByTname(xmslWbs);
    }

    @Override
    public List<XmslWbs> childListByIds(Long[] ids) {
        return childListByIds(ids,false);
    }

    @Override
    public List<XmslWbs> childListById(Long id) {
        return this.childListByIds(new Long[]{id});
    }

    @Override
    public List<XmslWbs> childListByIds(Long[] ids,boolean containSelf) {
        if(ArrayUtils.isEmpty(ids))
            return new ArrayList<>(2);
        List<XmslWbs> list = xmslWbsMapper.getByIds(ids);
        if(CollectionUtils.isEmpty(list))
            return new ArrayList<>(2);
        Set<Long> childIdSet = new HashSet<>();
        for (int i = 0; i < ids.length; i++) {
            Long[] tempIds = WbsRedisUtils.getChildWbsId(ids[i]+"");
            childIdSet.addAll(Arrays.asList(tempIds));
        }
        if(containSelf)
            childIdSet.addAll(Arrays.asList(ids));
        List<XmslWbs> wbsList = xmslWbsMapper.getByIds(childIdSet.toArray(new Long[]{}));
        return wbsList;
    }

//    @Override
//    public Map<String,List<XmslWbs>> copyChildList(String[] ids) {
//        if(ArrayUtils.isEmpty(ids))
//            return new HashMap<>(2);
//        List<XmslWbs> list = WbsRedisUtils.getWbs(SetUtils.hashSet(ids));
//        Map resuMap = new HashMap<>();
//        if(CollectionUtils.isEmpty(list)){
//            for (int i = 0; i < ids.length; i++) 
//                resuMap.put(ids[i],new ArrayList<>(2));    
//            return resuMap;
//        }
//        for (int i = 0; i < ids.length; i++) {
//            if(ids[i].length() > 20)
//                continue;    
//            resuMap.put(ids[i],copyData(ids[i]));
//        }
//        return resuMap;
//    }
//    private List<XmslWbs> copyData(String id){
//        Set<String> childIdSet = new HashSet<>();
//        Long[] tempIds = WbsRedisUtils.getChildWbsId(id+"");
//        childIdSet.addAll(Arrays.asList(ArrayUtils.toStringArray(tempIds)));
//        //获取所有子级数据
//        List<XmslWbs> wbsList = WbsRedisUtils.getWbs(childIdSet);
//        wbsList.sort((r,r1)->CompareUtil.compare(r.getLevel(),r1.getLevel()));
//        //替换id 为 uuid
//        Map<String,String> idWbsMap = new HashMap<>(wbsList.size());
//        for (int i = 0; i < wbsList.size(); i++) {
//            XmslWbs tempWbs = wbsList.get(i);
//            String newId = UUIDUtils.getShortUuid();
//            idWbsMap.put(tempWbs.getId(), newId);
//            tempWbs.setId(newId);
//        }
//        //处理父级Id
//        for (int i = 0; i < wbsList.size(); i++) {
//            XmslWbs tempWbs = wbsList.get(i);
//            if(StringUtils.isBlank(tempWbs.getParentId()))
//                continue;
//            tempWbs.setParentId(ObjectUtils.nvlString(idWbsMap.get(tempWbs.getParentId()+""),tempWbs.getParentId()));
//        }
//        return wbsList;
//    }
    @Override
    public Map<String,List<XmslWbsHistory>> copyChildList(Long[] ids,Long mainId){
        List<XmslWbsHistory> historyList = wbsHistoryService.getListByParentIds(Arrays.asList(ids),mainId);
        //子级id : 最上级id
        Map<String,String> realIdMap = new HashMap<>();
        Map<String,List<XmslWbsHistory>> resuMap = new HashMap<>();
        List<Long> idList = new ArrayList<>();
        idList.addAll(Arrays.asList(ids));
        //旧Id : 新的UUID
        Map<String,String> newIdMap = new HashMap<>();
        //遍历5级查找
        for (int i = 0; i < 5; i++) {
            List<XmslWbsHistory> tempList = wbsHistoryService.getListByParentIds(idList,mainId);
            if(CollectionUtils.isEmpty(tempList))
                break;
            idList.clear();
            for (int j = 0; j < tempList.size(); j++) {
                XmslWbsHistory temp = tempList.get(j);
                String topId = i==0?temp.getParentId():realIdMap.get(temp.getParentId());
                idList.add(Long.valueOf(temp.getId()));
                realIdMap.put(temp.getId(), topId);
                //替换掉Id和父级Id，否则前端id会重
                ObjectUtils.add2MapList(resuMap,topId,temp);
                String newId = UUIDUtils.getShortUuid();
                newIdMap.put(temp.getId(), newId);
                temp.setId(newId);
                temp.setParentId(i==0?temp.getParentId():ObjectUtils.nvlString(newIdMap.get(temp.getParentId())));
            }
        }
        return resuMap;
    }
    
    @Override
    public Long countByWbs(XmslWbs wbs) {
        return this.xmslWbsMapper.countByWbs(wbs);
    }

    @Override
    public List<XmslWbs> latestWbsSimpleAllList() {
        return xmslWbsMapper.latestWbsSimpleAllList();
    }

    @Override
    public List<XmslWbs> importData(MultipartFile file) throws Exception {
        //读取excel中的数据，替换id
        FtExcelUtil<XmslWbs> excelUtil = new FtExcelUtil<>(XmslWbs.class);
        List<XmslWbs> list = excelUtil.importExcel(file.getInputStream());
        Map<String,XmslWbs> codeMap = new HashMap<>(list.size());
        List<XmslWbs> resuList = new ArrayList<>();
        //序号map
        Map<String,Integer> sortMap = new HashMap<>(list.size());
        Function<String,Integer> getSortFunc = (code)->{
            Integer sort = sortMap.get(code);
            sort = sort==null?1:sort+1;
            sortMap.put(code,sort);
            return sort;    
        };
        for (int i = 0; i < list.size(); i++) {
            XmslWbs temp = list.get(i);
            if(StringUtils.isBlank(temp.getCode()))
                break;
            resuList.add(temp);
            String code = temp.getCode().trim();
            String parentCode = "";  //父级编码，用于记录子级的序号
            if(code.indexOf("-") < 0){
                temp.setLevel(1);
                temp.setParentId("-1");
                parentCode = "-1";
            }else{
                parentCode = StringUtils.substringBeforeLast(code,"-");
                //查找父级
                XmslWbs parent = codeMap.get(parentCode);
                Assert.notNull(parent, "未找到父级,请确保父级编码写在子级的前面，行号:"+(i+2));
                temp.setParentId(parent.getId());
                temp.setLevel(parent.getLevel()+1);
                parent.setHaveChildren(Constant.YES_INT);
            }
            //获取序号
            Integer sort = sortMap.get(parentCode);
            sort = sort==null?1:sort+1;
            sortMap.put(parentCode,sort);
            temp.setSort(sort);
            temp.setId(UUIDUtils.getShortUuid());
            codeMap.put(temp.getCode(), temp);
        }
        return resuList;
    }

    @Override
    public List<XmslWbs> exportData(XmslWbsMain main) {
        
        return null;
    }

    @Override
    public void handlerAncestors() {
        handlerAncestors(null);
    }

    @Override
    public void handlerAncestors(Function<XmslWbs,XmslWbs> func) {
        long begin = System.currentTimeMillis();
        try{
            List<XmslWbs> list = this.xmslWbsMapper.latestWbsSimpleAllList();
            //祖级id、名称map
            Map<String,List<String>> parentIdMap = new HashMap<>(list.size());
            Map<String,List<String>> parentNameMap = new HashMap<>(list.size());
            Map<String,String> idNameMap = new HashMap<>(list.size());
            //是否为父级
            Function<String,Boolean> isParentFunc = (s)->{return StringUtils.isBlank(s) || StringUtils.equalsAny(s,"-1","0");};
            //遍历，获取祖级id、名称
            for (int i = 0; i < list.size(); i++) {
                XmslWbs temp = list.get(i);
                if(func != null){
                    func.apply(temp);
                }
                idNameMap.put(temp.getId(),temp.getName().trim());
                //若有父级，则放入parentIdMap、parentNameMap
                if(isParentFunc.apply(temp.getParentId())){
                    parentIdMap.put(temp.getId(),ListUtil.toList(temp.getId()));
                    parentNameMap.put(temp.getId(),ListUtil.toList(temp.getName()));
                    continue;
                }
                String pid = temp.getParentId();
                String pname = idNameMap.get(pid);
                if(StringUtils.isBlank(pname))
                    logger.warn("WBS同步祖级名称ID时，未找到父级名称,子级ID:{},父级ID:{}",temp.getId(),pid);
                List<String> pidList = ListUtils.defaultIfNull(parentIdMap.get(pid),new ArrayList<>());
                List<String> pnameList = ListUtils.defaultIfNull(parentNameMap.get(pid),new ArrayList<>());
                parentIdMap.put(temp.getId(),copyAndAdd(pidList,temp.getId()));
                parentNameMap.put(temp.getId(),copyAndAdd(pnameList,temp.getName()));
            }
            //填充祖级id、名称
            for (int i = 0; i < list.size(); i++) {
                XmslWbs temp = list.get(i);
                if(isParentFunc.apply(temp.getParentId())){
                    temp.setAncestors(temp.getId());
                    temp.setAncestorsName(temp.getName());
                    continue;
                }
                temp.setAncestors(StringUtils.join(parentIdMap.get(temp.getId()),","));
                temp.setAncestorsName(StringUtils.join(parentNameMap.get(temp.getId()),","));
            }
            xmslWbsMapper.updateXmslWbsAncestorList(list);
        }finally{
            long usemills = System.currentTimeMillis()-begin;
            logger.debug("WBS同步祖级名称ID，耗时:{}毫秒",usemills);
        }
    }

    @Override
    public void initWbs2Redis(){
        String tenantKey = tokenService.getTenantKey();
        ThreadPoolUtil.execute(()->{
            String key = WbsRedisUtils.getKey(tenantKey);
            String childKey = WbsRedisUtils.getChildKey(tenantKey);
            String direChildKey = WbsRedisUtils.getDireChildKey(tenantKey);
            String wbsListKey = WbsRedisUtils.getWbsListKey(tenantKey);
            String listWbsKey = WbsRedisUtils.getListWbsKey(tenantKey);
            try{
                if(RedissonLockUtil.lock(key)){
                    Long count = xmslWbsMapper.countByWbs(new XmslWbs());
                    int limitSize = 3;
                    Long pages = count/limitSize+(count%limitSize>0?1:0);
                    Map<String,String> redisMap = new ConcurrentHashMap<>(limitSize);
                    Map<String,String> childRedisMap = new ConcurrentHashMap<>(limitSize); //wbs对应的全部子级（孙级）
                    Map<String,String> direChildRedisMap = new ConcurrentHashMap<>(limitSize);//wbs对应的直属子级
                    Map<String,String> wbsListMap = new ConcurrentHashMap<>(); //wbs编号对应清单编号
                    Map<String,String> listWbsMap = new ConcurrentHashMap<>(); //清单编号对应wbs编号
                    redisUtils.delete(key);
                    for (int i = 0; i < pages.intValue(); i++) {
                        PageHelper.startPage(i+1,limitSize,false);
                        List<XmslWbs> allList = this.latestWbsListSortLevel();
                        //遍历塞入map
                        allList.parallelStream().forEach(r->{
                            String[] listCodes = Convert.toStrArray(r.getListCode());
                            ObjectUtils.addStr2MapList(wbsListMap,r.getCode(),r.getListCode());
                            if(ArrayUtils.isNotEmpty(listCodes)){
                                for (int j = 0; j < listCodes.length; j++) {
                                    ObjectUtils.addStr2MapList(listWbsMap,listCodes[j],r.getCode());
                                }    
                            }
                            redisMap.put(r.getId(), JSONObject.toJSONString(r));
                            //直属子级
                            if(StringUtils.isNotBlank(r.getParentId()))
                                ObjectUtils.addStr2MapList(direChildRedisMap,r.getParentId(),r.getId());
                            String ancestor = r.getAncestors();
                            if(com.hhwy.common.core.utils.StringUtils.isBlank(ancestor))
                                return;
                            String[] pids = ancestor.split(",");
                            for (int j = 0; j < pids.length; j++) {
                                if(com.hhwy.common.core.utils.StringUtils.equals(pids[j],r.getId()))
                                    continue;
                                ObjectUtils.addStr2MapList(childRedisMap,pids[j],r.getId());
                            }
                        });
                        redisUtils.hPutAll(key,redisMap);
                        redisMap.clear();
                    }
                    redisUtils.delete(childKey);
                    redisUtils.hPutAll(childKey,childRedisMap);
                    redisUtils.delete(direChildKey);
                    redisUtils.hPutAll(direChildKey,direChildRedisMap);
                    redisUtils.delete(wbsListKey);
                    redisUtils.hPutAll(wbsListKey,wbsListMap);
                    redisUtils.delete(listWbsKey);
                    redisUtils.hPutAll(listWbsKey,listWbsMap);
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.info("塞wbs到缓存失败,msg:{}",e.getMessage());
            }finally {
                RedissonLockUtil.unlock(key);
            }
        });
    }

    private List<String> copyAndAdd(List<String> list,String str){
        List<String> result = new ArrayList<>(list);
        result.add(str);
        return result;
    }

    @Override
    public Map hasEffectWbs() {
        XmslWbsMain main = wbsMainService.getEffect();
        Long count = wbsMainService.getXmslWbsMainCount(new XmslWbsMain());
        return ObjectUtils.toMap("hasEffect",main!=null?1:0,"hasChange",count>1?1:0);
    }

    @Override
    @Transactional
    public void save(XmslWbsDto dto) {
        //保存校验
        saveCheck(dto);
        //1、主表数据
        saveMain(dto);
        //2、明细数据
        List<XmslWbsHistory> list = dto.getList();
        if(CollectionUtils.isEmpty(list) && StringUtils.isBlank(dto.getDelIds()))
            throw new CustomBusinessException("要保存的数据为空");
        List<XmslWbsHistory> addList = new ArrayList<>();
        List<XmslWbsHistory> updateList = new ArrayList<>();
        //前端新增数据的ID都为uid,需要替换为后端生成的id
        Map<String,String> idRepalceMap = new ConcurrentHashMap<>(list.size()/2);
        list.sort((r, r1) -> {return r.getLevel() > r1.getLevel() ? 1 : -1;});
        for (int i = 0; i < list.size(); i++) {
            XmslWbsHistory temp = list.get(i);
            temp.setMainId(dto.getMainId());
            //若wbs有子级，清除清单编号。20230804 玉涛需求
            if(temp.getHaveChildren() == Constant.YES_INT){
                temp.setListCode(null);
                temp.setListIds(null);
            }
            if(temp.getId().length() < 21){
                new AddBaseInfoUtil<>().updateBaseEntity(temp);
                updateList.add(temp);
                continue;
            }
            String id = getSnowId(temp.getId(),idRepalceMap);
            idRepalceMap.put(temp.getId(),id);
            temp.setId(id);
            String tempPid = idRepalceMap.get(temp.getParentId());
            if (StringUtils.isNotBlank(tempPid))
                temp.setParentId(tempPid);
            new AddBaseInfoUtil<>().addBaseEntity(temp);
            temp.setPtVar1("0");
            addList.add(temp);
        }
        if(CollectionUtils.isNotEmpty(addList))
            wbsHistoryService.insertXmslWbsHistoryList(addList);
        if(CollectionUtils.isNotEmpty(updateList))
            wbsHistoryService.updateXmslWbsHistoryList(updateList);
        //删除
        if(StringUtils.isNotBlank(dto.getDelIds())){
            wbsHistoryService.deleteXmslWbsHistoryByPks(Arrays.asList(Convert.toLongArray(dto.getDelIds())));
        }
    }
    
    private void saveMain(XmslWbsDto dto){
        //mainID不为空直接更新
        if(dto.getMainId() != null){
            XmslWbsMain main = wbsMainService.getById(dto.getMainId());
            new AddBaseInfoUtil<>().update(main);
            this.wbsMainService.updateXmslWbsMain(main);
            return;
        }
        XmslWbsMain lastMain = wbsMainService.getLast();
        XmslWbsMain main = null;
        if(lastMain == null){
            main = new XmslWbsMain();
            main.setValid(Constant.NO_INT);
            main.setVersion(1);
            new AddBaseInfoUtil<>(main);
            wbsMainService.insertXmslWbsMain(main);
        }
//        else{
//            main = lastMain;
//            main.setVersion(lastMain.getVersion()+1);
//            main.setValid(Constant.NO_INT);
//            new AddBaseInfoUtil<>(main);
//            this.wbsMainService.insertXmslWbsMain(main);
//        }
    }

    private void saveCheck(XmslWbsDto dto){
        if(dto.getMainId() == null){
            //为空则判断是否已经有未完成的数据
            XmslWbsMain query = new XmslWbsMain();
            query.setValid(Constant.NO_INT);
            Long count = wbsMainService.getXmslWbsMainCount(query);
            Assert.isTrue(count!= null && count > 0,"已存在未生效的历史，无法再新增新数据");
            return;
        }
        XmslWbsMain wbsMain = this.wbsMainService.getById(dto.getMainId());
        Assert.notNull(wbsMain,"mainId有误，获取主数据失败");
        Assert.isTrue(wbsMain.getValid()==Constant.NO_INT,"已生效的数据无法编辑");
    }

    public String getSnowId(String id,Map<String,String> idRepalceMap){
        if(id.length() < 21)
            return id;
        String temp = idRepalceMap.get(id);
        return temp == null?IdWorker.createId()+"":temp;
    }


    @Transactional
    public int insertXmslWbs(XmslWbs xmslWbs) {
        xmslWbs.setId(IdWorker.createId()+"");
        xmslWbs.setCreateUser(SecurityUtils.getUserName());
        xmslWbs.setCreateTime(DateUtils.getNowDate());
        return xmslWbsMapper.insertXmslWbs(xmslWbs);
    }

    @Transactional
    public int insertXmslWbsList(List<XmslWbs> xmslWbsList) {
        for (XmslWbs xmslWbs : xmslWbsList) {
            xmslWbs.setId(IdWorker.createId()+"");
            xmslWbs.setCreateUser(SecurityUtils.getUserName());
            xmslWbs.setCreateTime(DateUtils.getNowDate());
        }
        return xmslWbsMapper.insertXmslWbsList(xmslWbsList);
    }

    @Transactional
    public int updateXmslWbs(XmslWbs xmslWbs) {
        xmslWbs.setUpdateUser(SecurityUtils.getUserName());
        xmslWbs.setUpdateTime(DateUtils.getNowDate());
        return xmslWbsMapper.updateXmslWbs(xmslWbs);
    }

    @Transactional
    public int updateXmslWbsList(List<XmslWbs> xmslWbsList) {
        for (XmslWbs xmslWbs : xmslWbsList) {
            xmslWbs.setUpdateUser(SecurityUtils.getUserName());
            xmslWbs.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslWbsMapper.updateXmslWbsList(xmslWbsList);
    }

    @Transactional
    public int deleteXmslWbs(XmslWbs xmslWbs) {
        xmslWbs.setUpdateUser(SecurityUtils.getUserName());
        xmslWbs.setUpdateTime(DateUtils.getNowDate());
        return xmslWbsMapper.deleteXmslWbs(xmslWbs);
    }

    @Transactional
    public int deleteXmslWbsByPks(List<Long> xmslWbsPkList) {
        return xmslWbsMapper.deleteXmslWbsByPks(xmslWbsPkList);
    }
}
