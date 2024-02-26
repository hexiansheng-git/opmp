package com.hhwy.pm.xmsl.wbs.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.github.pagehelper.PageHelper;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.UUIDUtils;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.gm.wbs.service.ITWbsService;
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
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.*;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import com.hhwy.utils.redissonLock.RedissonLockUtil;
import nonapi.io.github.classgraph.json.Id;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Autowired
    private XmslWbsHistoryMapper xmslWbsHistoryMapper;
    @Resource
    private IXmslWbsMainService wbsMainService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private TokenService tokenService;
    @Resource
    private IXmslWbsHistoryService wbsHistoryService;
    @Resource
    private ITWbsService twbsService;
    @Autowired
    private SystemApiService systemApiService;

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
        XmslWbsMain effect = wbsMainService.getEffect();
        String tableName = "xmsl_wbs_history";
        if(xmslWbs.getMainId() == null){
            if(effect == null)
                return ObjectUtils.toMap("list",new ArrayList<>(2),"mainId","");
            xmslWbs.setMainId(effect.getId());
        }
        tableName = Long.valueOf(xmslWbs.getMainId()).equals(effect==null?-1L:effect.getId())?"xmsl_wbs":"xmsl_wbs_history";
        //判断查询历史还是查询当前
        XmslWbsMain main = wbsMainService.getById(xmslWbs.getMainId());
        xmslWbs.setParams(xmslWbs.getParams()==null?new HashMap<>(1):xmslWbs.getParams());
        xmslWbs.getParams().put("tableName",tableName);
        List<XmslWbs> list = xmslWbsMapper.getXmslWbsList(xmslWbs);
        //清单信息获取
        return ObjectUtils.toMap("list",list,"mainId",main.getId(),"version",main.getVersion());
    }

    @Override
    public List<XmslWbs> latestData(XmslWbs wbs) {
        if(StringUtils.isBlank(wbs.getParentId()) )
            wbs.setParentId("-1");
        boolean hasCondition = StringUtils.isNotBlank(wbs.getCode()) || StringUtils.isNotBlank(wbs.getName())
                || StringUtils.isNotBlank(wbs.getPtVar1());
//        if(hasCondition && (StringUtils.trim(wbs.getCode())+StringUtils.trim(wbs.getName())).length() < 3)
//            throw new RuntimeException("搜索参数过小");
        if(!hasCondition){
            List<XmslWbs> list = xmslWbsMapper.latestWbsList(wbs);
            return list;
        }
        //如果是懒加载,找出满足条件的id，扔redis
        String key = "wbs::lazySearch_"+ MySecurityUtils.getTenantKey()+"::"+StringUtils.join(",",wbs.getCode(),wbs.getName(),wbs.getPtVar1());
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
        //清空搜索条件，用id当条件即可
        wbs.setCode(null);
        wbs.setName(null);
        wbs.setPtVar1(null);
        List<XmslWbs> list = xmslWbsMapper.latestWbsList(wbs);
        return list;
    }

    @Override
    public List<XmslWbs> getXmslWbsListByTname(XmslWbs xmslWbs) {
        return xmslWbsMapper.getXmslWbsList(xmslWbs);
    }

    @Override
    public List<XmslWbs> getXmslWbsHistoryList(Long mainId) {
        XmslWbs query = new XmslWbs();
        query.setMainId(mainId);
        query.setParams(ObjectUtils.toMap("tableName","xmsl_wbs_history"));
        return getXmslWbsListByTname(query);
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
            if(tempIds != null)
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
    public Map<String,List<XmslWbsHistory>> copyChildList(String parentCode,Integer level,Integer rootNum,Integer num,Long[] ids,Long mainId){
        //子级id : 最上级id
        Map<String,String> realIdMap = new HashMap<>();
        Map<String,List<XmslWbsHistory>> resuMap = new HashMap<>();
        Map<String,XmslWbsHistory> wbsMap = new HashMap<>();
        List<Long> idList = new ArrayList<>();
        idList.addAll(Arrays.asList(ids));
        Set<Long> idSet = new HashSet<>(idList);
        //id:当前子级生成流水号
        Map<String,Integer> sortNumMap = new HashMap<>();
        BiFunction<String,Integer,Integer> getSelfCodeSortNum = (id, start)->{
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
        //旧Id : 新的UUID
        Map<String,String> idRelateMap = new HashMap<>();
        List<XmslWbsHistory> tempList = wbsHistoryService.getListByIds(idList,mainId);
        //遍历5级查找
        for (int i = 0; i < 5; i++) {
            if(i != 0)
                tempList.addAll(wbsHistoryService.getListByParentIds(idList,mainId));
            if(CollectionUtils.isEmpty(tempList))
                break;
            idList.clear();
            for (int j = 0; j < tempList.size(); j++) {
                XmslWbsHistory temp = tempList.get(j);
                String topId = idSet.contains(Long.valueOf(temp.getId()))?temp.getId():realIdMap.get(temp.getParentId());
                //若直属于idList，则处理父级编码
                String selfCode = "";
                if(idSet.contains(Long.valueOf(temp.getId()))){
                    boolean isRoot = StringUtils.isBlank(parentCode);
                    temp.setParentCode(parentCode);
                    Integer startNum = isRoot?rootNum:num;
                    selfCode = buildSelfCode.apply(getSelfCodeSortNum.apply(temp.getParentCode(),startNum),isRoot);
                    temp.setLevel(level);
                }else if(temp.getLevel() != 1){ //非第一级
                    String newPid = idRelateMap.get(temp.getParentId());
                    XmslWbsHistory parent = wbsMap.get(newPid);
                    temp.setParentCode(parent.getCode());
                    temp.setParentId(parent.getId());
                    temp.setLevel(parent.getLevel()+1);
                    selfCode = buildSelfCode.apply(getSelfCodeSortNum.apply(temp.getParentCode(),null),false);
                }
                temp.setSelfCode(selfCode);
                String pcode = temp.getParentCode();
                idList.add(Long.valueOf(temp.getId()));
                temp.setCode((StringUtils.isBlank(pcode)?"":pcode+"-")+ObjectUtils.nvlString(temp.getSelfCode()));
                temp.setPtVar3(temp.getName());
                temp.setName(ObjectUtils.nvlString(temp.getCode())+"-"+ObjectUtils.nvlString(temp.getName()));
                if(!idSet.contains(Long.valueOf(temp.getId())))
                    ObjectUtils.add2MapList(resuMap,topId,temp);
                realIdMap.put(temp.getId(), topId);
                //替换掉Id和父级Id，否则前端id会重
//                String newId = UUIDUtils.getShortUuid();
                String newId = temp.getId();
                idRelateMap.put(temp.getId(),newId);
                temp.setId(newId);
//                temp.setParentId(i==0?temp.getParentId():ObjectUtils.nvlString(idRelateMap.get(temp.getParentId())));
                wbsMap.put(temp.getId(),temp);
            }
            tempList.clear();
        }
        //填充不存在的id，给前端返回空数组
        for (int i = 0; i < ids.length; i++) {
            if(!resuMap.containsKey(ids[i]+""))
                resuMap.put(ids[i]+"",new ArrayList<>(1));
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
    public Long importData(Long id,MultipartFile file) throws Exception {
        //读取excel中的数据，替换id
        ZipSecureFile.setMinInflateRatio(-1.0d);  //
        FtExcelUtil<XmslWbsHistory> excelUtil = new FtExcelUtil<>(XmslWbsHistory.class);
        List<XmslWbsHistory> list = excelUtil.importExcel(3,file.getInputStream());
        Map<String,XmslWbs> codeMap = new HashMap<>(list.size());
        List<XmslWbsHistory> resuList = new ArrayList<>();
        List<SysDictData> nodeTypeDictList = systemApiService.selectDictDataByType("xmsl_wbs_type");
        //序号map
        Map<String,Integer> sortMap = new HashMap<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            XmslWbsHistory temp = list.get(i);
            if(temp == null || StringUtils.isBlank(temp.getCode()))
                break;
            Assert.isTrue(StringUtils.isNotBlank(temp.getStandardCode()),"，关联标准WBS不能为空必须填入标准WBS编码");
            resuList.add(temp);
            String code = temp.getCode().trim();
            String parentCode = "";  //父级编码，用于记录子级的序号
            if(code.indexOf("-") < 0){
                temp.setLevel(1);
                temp.setParentId("-1");
                temp.setParentCode("");
                temp.setSelfCode(temp.getCode());
                parentCode = "-1";
            }else{
                parentCode = StringUtils.substringBeforeLast(code,"-");
                //查找父级
                XmslWbs parent = codeMap.get(parentCode);
                Assert.notNull(parent, "未找到父级,请确保父级编码写在子级的前面，行号:"+(i+1));
                temp.setParentId(parent.getId());
                temp.setLevel(parent.getLevel()+1);
                temp.setParentCode( parent.getCode());
                temp.setSelfCode(StringUtils.substringAfterLast(code,"-"));
                parent.setHaveChildren(Constant.YES_INT);
            }
            //获取序号
            Integer sort = sortMap.get(parentCode);
            sort = sort==null?1:sort+1;
            sortMap.put(parentCode,sort);
            temp.setSort(sort);
            temp.setId(UUIDUtils.getShortUuid());
            codeMap.put(temp.getCode(), temp);
            temp.setStatus(ObjectUtils.nvl(temp.getStatus(),1));
            SysDictData tempDict = nodeTypeDictList.get(NumberUtil.min(temp.getLevel(),nodeTypeDictList.size())-1);
            temp.setNodeType(tempDict==null?"":tempDict.getDictValue());
            //处理标准WBS
            TWbs twbs = twbsService.getTWbsByFullCode(temp.getStandardCode());
            if(twbs == null)
                twbs = twbsService.getTWbsByFullCode(temp.getStandardCode()+"-");
            Assert.notNull(twbs,"标准WBS录入有误，未找到编号："+temp.getStandardCode());
            temp.setStandardId(Long.valueOf(twbs.getId()));
            temp.setStandardCode(twbs.getCode());
            temp.setStandardName(twbs.getName());
            temp.setPtVar3(twbs.getName());
            temp.setName(ObjectUtils.nvlString(temp.getPartCode())+"-"+ObjectUtils.nvlString(temp.getName()));
            new AddBaseInfoUtil<>().addBaseEntity(temp);
        }
        //调用save接口进行保存
        XmslWbsDto wbsDto = new XmslWbsDto();
        wbsDto.setMainId(id);
        wbsDto.setList(list);
        wbsDto.setSubmitFlag(0);
        if(id != null){
            XmslWbsHistory temp = new XmslWbsHistory();
            temp.setMainId(id);
            xmslWbsHistoryMapper.deleteXmslWbsHistory(temp);
        }
        this.save(wbsDto);
        return null;
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
                }else{
                    temp.setAncestors(StringUtils.join(parentIdMap.get(temp.getId()),","));
                    temp.setAncestorsName(StringUtils.join(parentNameMap.get(temp.getId()),","));
                }
                if(func != null){  //调用自定义遍历方法
                    func.apply(temp);
                }
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
        initWbs2Redis(tenantKey);
    }

    @Override
    public void initWbs2Redis(String tenantKey){
        ThreadPoolUtil.execute(()->{
            //切换到master
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            try {
                String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
                DynamicDataSourceContextHolder.push(dataSource);

                String key = WbsRedisUtils.getKey(tenantKey);
                String codeKey = WbsRedisUtils.getCodeKey(tenantKey);
                String childKey = WbsRedisUtils.getChildKey(tenantKey);
                String direChildKey = WbsRedisUtils.getDireChildKey(tenantKey);
                String wbsListKey = WbsRedisUtils.getWbsListKey(tenantKey);
                String listWbsKey = WbsRedisUtils.getListWbsKey(tenantKey);
                try{
                    if(RedissonLockUtil.lock(key)){
                        Long count = xmslWbsMapper.countByWbs(new XmslWbs());
                        int limitSize = 1000;
                        Long pages = count/limitSize+(count%limitSize>0?1:0);
                        Map<String,String> codeIdMap = new ConcurrentHashMap<>(limitSize); //wbsCode : wbsId
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
                                codeIdMap.put(r.getCode(),r.getId()+"" );
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
                            redisUtils.hPutAll(codeKey,codeIdMap);
                            codeIdMap.clear();
                        }
                        redisUtils.delete(childKey);
                        redisUtils.hPutAll(childKey,childRedisMap);
                        redisUtils.delete(direChildKey);
                        redisUtils.hPutAll(direChildKey,direChildRedisMap);
                        redisUtils.delete(wbsListKey);
                        redisUtils.hPutAll(wbsListKey,wbsListMap);
                        redisUtils.delete(listWbsKey);
                        redisUtils.hPutAll(listWbsKey,listWbsMap);
//                    redisUtils.hput
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    logger.info("塞wbs到缓存失败,msg:{}",e.getMessage());
                }finally {
                    RedissonLockUtil.unlock(key);
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new CustomException(e.getMessage());
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
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
        if(CollectionUtils.isEmpty(list) && StringUtils.isBlank(dto.getDelIds())){
            submitCheck(dto);
            return ;
        }
        List<XmslWbsHistory> addList = new ArrayList<>();
        List<XmslWbsHistory> updateList = new ArrayList<>();
        List<XmslWbsHistory> updateParentCodeList = new ArrayList<>();
        //前端新增数据的ID都为uid,需要替换为后端生成的id
        Map<String,String> idRepalceMap = new ConcurrentHashMap<>(list.size()/2);
        list.sort((r, r1) -> {return r.getLevel()==r1.getLevel()?0:(r.getLevel() > r1.getLevel() ? 1 : -1);});
        Map<String,XmslWbsHistory> map = list.stream().collect(Collectors.toMap(r->r.getId(),r->r));
        for (int i = 0; i < list.size(); i++) {
            XmslWbsHistory temp = list.get(i);
            XmslWbsHistory parent = map.get(temp.getParentId());
            if(parent == null && temp.getParentId().length()<21){
                List<XmslWbsHistory> historyList = wbsHistoryService.getListByIds(Arrays.asList(Long.valueOf(temp.getParentId())),dto.getMainId());
                if(CollectionUtils.isEmpty(historyList)){
                    logger.error("项目wbs保存，前端传入的parentId[{}]未在库中找到",temp.getParentId());
                }else{
                    parent = historyList.get(0);
                }
            }
            String pcode = parent == null?"":ObjectUtils.nvlString(parent.getCode());
            temp.setParentCode(pcode);
            temp.setPtVar2(StringUtils.isBlank(temp.getPtVar2())?"-1":temp.getPtVar2()); //ptVar2 变更状态添加默认值
            String stardName = ObjectUtils.nvlString(temp.getPtVar3());
            if(ObjectUtils.isBlank(stardName) )
                stardName = temp.getStandardName();
            temp.setName(ObjectUtils.nvlString(temp.getPartCode())+ObjectUtils.nvlString(stardName));
            temp.setCode((StringUtils.isBlank(pcode)?"":pcode+"-")+ObjectUtils.nvlString(temp.getSelfCode()));
            temp.setMainId(dto.getMainId());
            //若wbs有子级，清除清单编号。20230804 玉涛需求
            if(Objects.equals(temp.getHaveChildren(), Constant.YES_INT)){
                temp.setListCode(null);
                temp.setListIds(null);
            }
            temp.setHaveChildren(ObjectUtils.nvl(temp.getHaveChildren(),0));
            if(temp.getId().length() < 21){
                new AddBaseInfoUtil<>().updateBaseEntity(temp);
                updateList.add(temp);
                //若编号已修改，需要更新子级
                if(!StringUtils.equals(temp.getSelfCode(),temp.getOldSelfCode()) && StringUtils.isNotBlank(temp.getSelfCode()) )
                    updateParentCodeList.add(temp);
                continue;
            }
            String id = getSnowId(temp.getId(),idRepalceMap);
            idRepalceMap.put(temp.getId(),id);
            temp.setId(id);
            String tempPid = idRepalceMap.get(ObjectUtils.nvlString(temp.getParentId()));
            if (StringUtils.isNotBlank(tempPid))
                temp.setParentId(tempPid);
            temp.setParentId(ObjectUtils.nvlString(temp.getParentId(),"-1"));
            new AddBaseInfoUtil<>().addBaseEntity(temp);
            temp.setPtVar1("0");
            temp.setName(ObjectUtils.nvlString(temp.getName()));
            temp.setPartCode(ObjectUtils.nvlString(temp.getPartCode()));
            addList.add(temp);
        }
        if(CollectionUtils.isNotEmpty(addList))
            wbsHistoryService.insertXmslWbsHistoryList(addList);
        if(CollectionUtils.isNotEmpty(updateList))
            wbsHistoryService.updateXmslWbsHistoryList(updateList);
        //删除
        if(StringUtils.isNotBlank(dto.getDelIds()))
            delById(dto.getMainId(),dto.getDelIds());
        //修改父级编码
        if(CollectionUtils.isNotEmpty(updateParentCodeList))
            xmslWbsHistoryMapper.updateParentCodes(updateParentCodeList);
        //提交校验
        submitCheck(dto);
    }
    
    private void saveMain(XmslWbsDto dto){
        //mainID不为空直接更新
        if(dto.getMainId() != null){
            XmslWbsMain main = wbsMainService.getById(dto.getMainId());
            new AddBaseInfoUtil<>().update(main);
            if(main.getPublishUserId() == null){
                main.setPublishUserId(SecurityUtils.getUserId());
                main.setPublishUserName(SecurityUtils.getSysUser().getNickName());
            }
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
            dto.setMainId(main.getId());
            if(main.getPublishUserId() == null){
                main.setPublishUserId(SecurityUtils.getUserId());
                main.setPublishUserName(SecurityUtils.getSysUser().getNickName());
            }
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
            Assert.isTrue(count!= null && count < 1,"已存在未生效的历史，无法再新增新数据");
            return;
        }
        XmslWbsMain wbsMain = this.wbsMainService.getById(dto.getMainId());
        Assert.notNull(wbsMain,"mainId有误，获取主数据失败");
        Assert.isTrue(wbsMain.getValid()==Constant.NO_INT,"已生效的数据无法编辑");

    }

    private void submitCheck(XmslWbsDto dto){
        //如果为提交，校验所有wbs必填项
        if(Constant.YES_INT.equals(dto.getSubmitFlag())){
            String wrongCodes = this.xmslWbsMapper.countWbsOnlyOne(dto.getMainId());
            Assert.isTrue(StringUtils.isBlank(wrongCodes),"wbs编号为:["+wrongCodes+"]的数据未填写项目部位（桩号）或标准WBS名称");
            //校验重复编码
            List<String> repeatCodeList = xmslWbsMapper.repeatWbsCode(dto.getMainId());
            Assert.isTrue(CollectionUtils.isEmpty(repeatCodeList),"["+StringUtils.join(repeatCodeList,",")+"]WBS编号重复");
            //同一父级下不允许有重复的wbs名称(来自p6的校验)
            List<String> repeatNameList = xmslWbsMapper.repeatWbsName(dto.getMainId());
            Assert.isTrue(CollectionUtils.isEmpty(repeatNameList),"["+StringUtils.join(repeatNameList,",")+"]WBS名称重复,同一父级下不能有相同的WBS名称");
        }
    }

    public String getSnowId(String id,Map<String,String> idRepalceMap){
        if(id.length() < 21)
            return id;
        String temp = idRepalceMap.get(id);
        return temp == null?IdWorker.createId()+"":temp;
    }

    //删除所有子级，
    private void delById(Long mainId,String delIdStr){
        Long[] delIds = Convert.toLongArray(delIdStr);
        Set<Long> delAlIdList = new HashSet<>(Arrays.asList(delIds));
        Set<Long> childIdList = new HashSet<>(Arrays.asList(delIds));
        do{
            delAlIdList.addAll(childIdList);
            childIdList= xmslWbsHistoryMapper.selectIdByParentIds(mainId,childIdList);
        }while(CollectionUtils.isNotEmpty(childIdList));
        wbsHistoryService.deleteXmslWbsHistoryByPks(new ArrayList<>(delAlIdList));
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

    @Override
    public int updatePtVar2List(List<XmslWbs> list) {
        if(CollectionUtils.isEmpty(list))
            return 0;
        return xmslWbsMapper.updatePtVar2List(list);
    }

    @Override
    public int clearPtVar4(Set<Long> set) {
        if(CollectionUtils.isEmpty(set))
            return 0;
        return xmslWbsMapper.clearPtVar4(set);
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
