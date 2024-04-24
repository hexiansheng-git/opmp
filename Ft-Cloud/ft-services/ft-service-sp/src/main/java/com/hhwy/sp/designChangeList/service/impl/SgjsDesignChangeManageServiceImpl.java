package com.hhwy.sp.designChangeList.service.impl;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.UUIDUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.xmslEngineeringReport.domain.XmslEngineeringReport;
import com.hhwy.sp.common.warn.CommonBusiness;
import com.hhwy.sp.core.system.SystemApiService;
import com.hhwy.sp.designChangeList.domain.ProjectBasicInfo;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeList;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeListService;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeManageRecordService;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeWbsService;
import com.hhwy.sp.designChangeList.vo.ChangeManagSaveVo;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.utils.validation.ValidationUtil;
import jdk.nashorn.internal.ir.ContinueNode;
import lombok.var;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.ehcache.shadow.org.terracotta.offheapstore.storage.listener.ListenableStorageEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hhwy.sp.designChangeList.mapper.SgjsDesignChangeManageMapper;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManage;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeManageService;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.print.event.PrintJobAttributeEvent;

/**
 * 施工技术管理-设计变更管理Service业务层处理
 * 
 * @author wk
 * @date 2024-04-16
 */
@Service
public class SgjsDesignChangeManageServiceImpl implements ISgjsDesignChangeManageService {
    Logger logger = LoggerFactory.getLogger(SgjsDesignChangeManageServiceImpl.class);
    @Autowired
    private SgjsDesignChangeManageMapper sgjsDesignChangeManageMapper;
    @Autowired
    private ISgjsDesignChangeWbsService changeWbsService;
    @Autowired
    private ISgjsDesignChangeListService changeListService;
    @Autowired
    private ISgjsDesignChangeManageRecordService changeManageRecordService;
    @Autowired
    private PmServiceApi pmServiceApi;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private SystemServiceApi systemServiceApi;

    /**
     * 查询施工技术管理-设计变更管理
     * 
     * @param id 施工技术管理-设计变更管理ID
     * @return 施工技术管理-设计变更管理
     */
    @Override
    public SgjsDesignChangeManage selectSgjsDesignChangeManageById(Long id) {
        return sgjsDesignChangeManageMapper.selectSgjsDesignChangeManageById(id);
    }

    @Override
    public List<SgjsDesignChangeManage> selectSgjsDesignChangeManageByIds(Long[] ids) {
        return sgjsDesignChangeManageMapper.selectSgjsDesignChangeManageByIds(ids);
    }

    /**
     * 查询施工技术管理-设计变更管理列表
     * 
     * @param sgjsDesignChangeManage 施工技术管理-设计变更管理
     * @return 施工技术管理-设计变更管理
     */
    @Override
    public List<SgjsDesignChangeManage> selectSgjsDesignChangeManageList(SgjsDesignChangeManage sgjsDesignChangeManage) {
        return sgjsDesignChangeManageMapper.selectSgjsDesignChangeManageList(sgjsDesignChangeManage);
    }
    
    

    /**
     * 新增施工技术管理-设计变更管理
     * 
     * @param sgjsDesignChangeManage 施工技术管理-设计变更管理
     * @return 结果
     */
    @Override
    public int insertSgjsDesignChangeManage(SgjsDesignChangeManage sgjsDesignChangeManage) {
        sgjsDesignChangeManage.setCreateTime(DateUtils.getNowDate());
        return sgjsDesignChangeManageMapper.insertSgjsDesignChangeManage(sgjsDesignChangeManage);
    }

    @Override
    @Transactional
    public Long save(ChangeManagSaveVo saveVo) {
        boolean isNew = saveVo.getId() == null;
        saveVo.setPtVar1("0"); //是否生效
        if(isNew){
            new AddBaseInfoUtil<>(saveVo);
            saveVo.setId(IdWorker.createId());
            sgjsDesignChangeManageMapper.insertSgjsDesignChangeManage(saveVo);
        }else{
            new AddBaseInfoUtil<>().updateBaseEntity(saveVo);
            sgjsDesignChangeManageMapper.updateSgjsDesignChangeManage(saveVo);
        }
        if(!isNew)
            sgjsDesignChangeManageMapper.deleteWbsByType(saveVo.getId(),saveVo.getPtVar2());
        List<SgjsDesignChangeWbs> addWbsList = new ArrayList<>();
        List<SgjsDesignChangeList> addList = new ArrayList<>();
        List<String> wbsCodeList = new ArrayList<>();
        handlerWbsList(saveVo,null,saveVo.getWbsList(),addWbsList,wbsCodeList,addList);
        changeListService.deleteByWbsCodes(saveVo.getId(),saveVo.getPtVar2(),wbsCodeList);
        changeWbsService.batchInsert(addWbsList);
        changeListService.batchInsert(addList);
        //事项记录
        changeManageRecordService.deleteByMainId(saveVo.getId());
        saveVo.setRecordList(CollectionUtils.isEmpty(saveVo.getRecordList())?new ArrayList<>():saveVo.getRecordList());
        saveVo.setRecordContactList(CollectionUtils.isEmpty(saveVo.getRecordContactList())?new ArrayList<>():saveVo.getRecordContactList());
        saveVo.getRecordList().stream().forEach(r->{
            r.setId(IdWorker.createId());
            r.setMainId(saveVo.getId());
            r.setType("1");
            new AddBaseInfoUtil<>().addBaseEntity(r);
        });
        saveVo.getRecordContactList().stream().forEach(r->{
            r.setId(IdWorker.createId());
            r.setMainId(saveVo.getId());
            r.setType("2");
            new AddBaseInfoUtil<>().addBaseEntity(r);
        });
        saveVo.getRecordList().addAll(saveVo.getRecordContactList());
        changeManageRecordService.batchInsert(saveVo.getRecordList());
        //推送到总部版
        if(StringUtils.equals(saveVo.getSubmitFlag(),"1")){
            push2Gm(saveVo);
        }
        return saveVo.getId();
    }
    private void handlerWbsList(ChangeManagSaveVo saveVo, SgjsDesignChangeWbs parent,List<SgjsDesignChangeWbs> wbsList 
            , List<SgjsDesignChangeWbs> addWbsList,List<String> deleteWbsCodeList, List<SgjsDesignChangeList> addList){
        Integer level = parent==null?1:parent.getLevel()+1;
        if(CollectionUtils.isEmpty(wbsList)) return;
        for (int i = 0; i < wbsList.size(); i++) {
            SgjsDesignChangeWbs wbs = wbsList.get(i);
            if(StringUtils.equals(saveVo.getSubmitFlag(),"1")){
                JyDetailsUtil.jy(wbs, new Class[]{ValidationGroups.Other.class} );
            }
            wbs.setId(IdWorker.createId());
            wbs.setParentId(parent==null?-1L:parent.getId());
            new AddBaseInfoUtil<>().addBaseEntity(wbs);
            wbs.setMainId(saveVo.getId());
            wbs.setLevel(level);
            wbs.setPtVar2(saveVo.getPtVar2());
            addWbsList.add(wbs);
            if(wbs.getPtVar1().equals("1"))
                deleteWbsCodeList.add(wbs.getCode());
            //处理清单
            List<SgjsDesignChangeList> list = new ArrayList<>();
            try{
                list = CollectionUtils.isEmpty(wbs.getList())?
                        JSONObject.parseArray(JSONObject.toJSONString(wbs.getParams().get("list")),SgjsDesignChangeList.class):
                        wbs.getList();
            }catch(Exception e){
                e.printStackTrace();
            }
            handlerList(saveVo,wbs,null,list,addList);
            handlerWbsList(saveVo,wbs,wbs.getChildren(),addWbsList,deleteWbsCodeList,addList);
            wbs.setPtVar1("");
        }
    }

    private void handlerList(ChangeManagSaveVo saveVo, SgjsDesignChangeWbs wbs,SgjsDesignChangeList parent,List<SgjsDesignChangeList> list,
                             List<SgjsDesignChangeList> addList){
        if(!StringUtils.equals(wbs.getPtVar1(),"1") || CollectionUtils.isEmpty(list))
            return;
        for (int j = 0; j < list.size(); j++) {
            SgjsDesignChangeList temp = list.get(j);
            if(StringUtils.equals(saveVo.getSubmitFlag(),"1")){
                JyDetailsUtil.jy(temp, new Class[]{ValidationGroups.Other.class} );
            }
            new AddBaseInfoUtil<>().addBaseEntity(temp);
            temp.setId(IdWorker.createId());
            temp.setMainId(saveVo.getId());
            temp.setWbsCode(wbs.getCode());
            temp.setWbsId(wbs.getId());
            temp.setPid(parent==null?-1L:parent.getId());
            Integer type = StringUtils.equals(saveVo.getPtVar2(),"1")?1:2;
            temp.setType(type);
            addList.add(temp);
            handlerList(saveVo,wbs,temp,temp.getChildren(),addList);
        }
    }
    
    private void push2Gm(ChangeManagSaveVo saveVo){
        String regionName = "",prjName = "";
        AjaxResult result = pmServiceApi.projectInfo();
        if(!AjaxResult.isSuccess(result)){
            logger.error("获取项目信息失败,{}",result.get(AjaxResult.MSG_TAG));
        }else{
            ProjectBasicInfo projectBasicInfo = JSONObject.parseObject(JSONObject.toJSONString(result.getData()),ProjectBasicInfo.class);
            regionName = projectBasicInfo.getRegionName();
            prjName = projectBasicInfo.getProjectName();
        }
        saveVo.setProjectCode(SecurityUtils.getTenantKey());
        saveVo.setProjectName(prjName);
        saveVo.setRegionName(regionName);
        saveVo.setWbsList(null);
        saveVo.setRecordList(null);
        saveVo.setRecordContactList(null);
        rocketMQTemplate.convertAndSend("sgjs_design_change:tenantSuccess", JSONObject.toJSONString(saveVo));
    }
    /**
     * 修改施工技术管理-设计变更管理
     * 
     * @param sgjsDesignChangeManage 施工技术管理-设计变更管理
     * @return 结果
     */
    @Override
    public int updateSgjsDesignChangeManage(SgjsDesignChangeManage sgjsDesignChangeManage) {
        sgjsDesignChangeManage.setUpdateTime(DateUtils.getNowDate());
        return sgjsDesignChangeManageMapper.updateSgjsDesignChangeManage(sgjsDesignChangeManage);
    }

    /**
     * 删除施工技术管理-设计变更管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSgjsDesignChangeManageByIds(String ids) {
        return sgjsDesignChangeManageMapper.deleteSgjsDesignChangeManageByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除施工技术管理-设计变更管理信息
     * 
     * @param id 施工技术管理-设计变更管理ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSgjsDesignChangeManageById(Long id) {
        int result = sgjsDesignChangeManageMapper.deleteSgjsDesignChangeManageById(id);
        sgjsDesignChangeManageMapper.deleteWbsByMainIdVitual(id);
        sgjsDesignChangeManageMapper.deleteListByMainIdVitual(id);
        sgjsDesignChangeManageMapper.deleteRecordByMainIdVitual(id);
        return result;
    }

    @Override
    public Map<String,Object> relateList(SgjsDesignChangeWbs wbs){
        long beginMills = System.currentTimeMillis();
        try{
            //1、加载选择的wbs的父级以及全部子级  
            AjaxResult wbsResult = pmServiceApi.fullByWbsCode(ObjectUtils.toMap("code",wbs.getCode()));
            if(!AjaxResult.isSuccess(wbsResult)){
                logger.error(SecurityUtils.getTenantKey()+":获取wbs失败,msg:{}",wbsResult.get(AjaxResult.MSG_TAG));
                throw new RuntimeException("调用PM服务获取wbs接口失败");
            }
            List<XmslWbs> wbsList = JSONObject.parseArray(JSONObject.toJSONString(wbsResult.getData()), XmslWbs.class);
            Map<String,XmslWbs> wbsMap = wbsList.stream().collect(Collectors.toMap(r->r.getCode(), r->r));
            //2、加载最新挂接关系
            List<SgjsDesignChangeList> finalList = new ArrayList<>();
            List<SgjsDesignChangeList> designList = changeListService.selectLastByWbsCode(wbsMap.keySet().toArray(new String[]{}));
            Map<String,Long> lastVersionMap = new HashMap<>();
            for (int i = 0; i < designList.size(); i++) {
                SgjsDesignChangeList temp = designList.get(i);
                Long lastMainId = lastVersionMap.get(temp.getWbsCode());
                if(lastMainId != null && !lastMainId.equals(temp.getMainId()))
                    continue;
                lastVersionMap.put(temp.getWbsCode(), temp.getMainId());
                finalList.add(temp);
            }
            //若没有存在于design，从工程量报表里找
            Set<String> queryReportCodeSet = new HashSet<>(); 
            for(String wbsCode : wbsMap.keySet()){
                if(!lastVersionMap.containsKey(wbsCode))
                    queryReportCodeSet.add(wbsCode);
            }
            if(CollectionUtils.isNotEmpty(queryReportCodeSet)){
                XmslEngineeringReport report = new XmslEngineeringReport();
                report.setWbsCode(StringUtils.join(queryReportCodeSet, ","));
                AjaxResult result = pmServiceApi.relateList(report);
                Map<String,Object> resultMap = (Map)result.getData();
                for(String wbsCode : resultMap.keySet()){
                    List<XmslContractList> list = JSONObject.parseArray(JSONObject.toJSONString(resultMap.get(wbsCode)),XmslContractList.class);
                    //转换成SgjsDesignChangeList
                    for (int i = 0; i < list.size(); i++) {
                        XmslContractList temp = list.get(i);
                        if(temp.getPid()==null || temp.getPid() < 1)
                            temp.setPid(-1L);
                        SgjsDesignChangeList designChangeList = trans2DesignList(wbs.getCode(),temp);
                        finalList.add(designChangeList);
                    }
                }
            }
            //分组处理获取到的清单 ，塞入wbs
            Map<String,List<SgjsDesignChangeList>> listMap = finalList.stream().collect(Collectors.groupingBy(r->r.getWbsCode()));
            for(String wbsCode : listMap.keySet()){
                List<SgjsDesignChangeList> list = listMap.get(wbsCode);
                List<SgjsDesignChangeList> tlist = TreeUtil.build(list,-1L);
                wbsMap.get(wbsCode).setParams(ObjectUtils.toMap("list",tlist));
            }
            //3、wbs转成树形
            Map<String,XmslWbs> wbsIdMap = wbsList.stream().collect(Collectors.toMap(r->r.getId(), r->r));
            List<XmslWbs> treeList = new ArrayList<>();
            for (int i = 0; i < wbsList.size(); i++) {
                XmslWbs temp = wbsList.get(i);
                temp.setPtVar1("1"); //标记为已加载清单数据
                if(StringUtils.isBlank(temp.getParentId()) || temp.getParentId().equals("-1")){
                    treeList.add(temp);
                    continue;
                }
                XmslWbs parent = wbsIdMap.get(temp.getParentId());
                parent.setChildren(CollectionUtils.isEmpty(parent.getChildren())?new ArrayList<>():parent.getChildren());
                parent.getChildren().add(temp);
            }
            return ObjectUtils.toMap("wbsList",treeList);
        }finally {
            logger.debug("【设计变更】获取wbs的挂接数据耗时:{},wbsCode:{}",System.currentTimeMillis()-beginMills,wbs.getCode());
        }
    }
    
    public SgjsDesignChangeList trans2DesignList(String wbsCode,XmslContractList list){
        SgjsDesignChangeList design = new SgjsDesignChangeList();
        design.setId(list.getId());
        design.setType(1);
        design.setListCode(list.getCode());
        design.setPid(list.getPid());
        design.setListId(list.getListId());
        design.setWbsCode(wbsCode);
        design.setAncestors(list.getAncestors());
        design.setChineseName(list.getChineseName());
        design.setForeignName(list.getForeignName());
        design.setListType(list.getListType());
        design.setUnitCode(list.getUnitCode());
        design.setUnit(list.getUnit());
        design.setConNum(ObjectUtils.nvlBigDecimal(list.getWinNum()));
        design.setConExcludePrice(ObjectUtils.nvlBigDecimal(list.getWinUnitPrice()));
        design.setConSumPrice(ObjectUtils.nvlBigDecimal(list.getWinAmount()));
        design.setZeroNum(ObjectUtils.toDecimal(list.getPtVar1()));
        design.setZeroExcludePrice(list.getWinAmount());
        design.setZeroSumPrice(BigDecimalUtils.multiply(design.getZeroNum(),design.getZeroExcludePrice()));
        design.setBeforeNum(ObjectUtils.nvlBigDecimal(design.getZeroNum()));
        design.setBeforeExcludePrice(ObjectUtils.nvlBigDecimal(design.getZeroExcludePrice()));
        design.setBeforeSumPrice(ObjectUtils.nvlBigDecimal(design.getZeroSumPrice()));
        design.setCreateUser(list.getCreateUser());
        design.setCreateUserName(list.getCreateUserName());
        design.setUpdateUser(list.getUpdateUser());
        design.setDelUser(list.getDelUser());
        design.setDelTime(list.getDelTime());
        design.setDelFlag(list.getDelFlag());
        design.setPtVar1(list.getPtVar1());
        design.setPtVar2(list.getPtVar2());
        design.setPtVar3(list.getPtVar3());
        design.setPtVar4(list.getPtVar4());
        design.setPtVar5(list.getPtVar5());
        return design;
    }

    @Override
    public List<SgjsDesignChangeList> importData( MultipartFile file) throws Exception {
        ZipSecureFile.setMinInflateRatio(-1.0d);  //
        FtExcelUtil<SgjsDesignChangeList> excelUtil = new FtExcelUtil<>(SgjsDesignChangeList.class);
        List<SgjsDesignChangeList> list = excelUtil.importExcel(file.getInputStream());
        return list;
    }

    @Override
    public Integer isDirectProject() {
        if(SecurityUtils.getSysUser().getDept() == null) return 0;
        String ancestorStr = SecurityUtils.getSysUser().getDept().getAncestors();
        Set<String> regionIdSet = new HashSet(Arrays.asList("101148617,101148910,101149129,101149344,101187590,101321233,101322264,101322275,101322288,101322301".split(",")));
        if(StringUtils.isBlank(ancestorStr))
            return 0;
        String[] ancestors = ancestorStr.split(",");
        for(String pid : ancestors){
            if(regionIdSet.contains(pid))
                return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public void sync(Long mainId) {
        sgjsDesignChangeManageMapper.deleteWbsByType(mainId, "2");
        sgjsDesignChangeManageMapper.deleteListByType(mainId, 2);
        //查询wbs和清单
        Map<Long,Long> newWbsIdMap = new HashMap<>();
        Map<Long,Long> newListIdMap = new HashMap<>();
        BiFunction<Long,Map,Long> replaceIdFunc = (id,map)->{
            if(id == null || id < 1L)
                return id;
            Object o = map.get(id);
            if(o != null)
                return (Long)o;
            Long nid = IdWorker.createId();
            map.put(id, nid);
            return nid;
        };
        Date now = new Date();
        SgjsDesignChangeWbs query = new SgjsDesignChangeWbs();
        query.setMainId(mainId);
        query.setPtVar2("1");
        List<SgjsDesignChangeWbs> wbsList = this.changeWbsService.selectSgjsDesignChangeWbsList(query);
        for (int i = 0; i < wbsList.size(); i++) {
            SgjsDesignChangeWbs temp = wbsList.get(i);
            temp.setId(replaceIdFunc.apply(temp.getId(), newWbsIdMap));
            temp.setParentId(replaceIdFunc.apply(temp.getParentId(), newWbsIdMap));
            temp.setPtVar2("2");
            temp.setCreateTime(now);
        }
        SgjsDesignChangeList listQuery = new SgjsDesignChangeList();
        listQuery.setMainId(mainId);
        listQuery.setType(1);
        List<SgjsDesignChangeList> list = this.changeListService.selectSgjsDesignChangeListList(listQuery);
        for (int i = 0; i < list.size(); i++) {
            SgjsDesignChangeList temp = list.get(i);
            temp.setId(replaceIdFunc.apply(temp.getId(), newListIdMap));
            temp.setPid(replaceIdFunc.apply(temp.getPid(), newListIdMap));
            temp.setWbsId(newWbsIdMap.get(temp.getWbsId()));
            temp.setType(2);
            temp.setCreateTime(now);
        }
        this.changeWbsService.batchInsert(wbsList);
        this.changeListService.batchInsert(list);
    }

    @Override
    @Transactional
    public void finishFlow(Long id) {
        if(id == null)
            return ;
        this.sgjsDesignChangeManageMapper.effect(id);
    }

    @Override
    @Transactional
    public void pushMsg(Long id) {
        if(id == null)
            return ;
        SgjsDesignChangeManage manage = this.sgjsDesignChangeManageMapper.selectSgjsDesignChangeManageById(id);
        if(manage == null)
            return ;
        TWarn warn = new TWarn();
        warn.setBusinessId(id);
        warn.setCreateTime(DateUtils.getNowDate());
        warn.setTenantKey(SecurityUtils.getTenantKey());
        warn.setWarnItem(WarnItem.SGJS_DESIGN_CHANGE.getWarnItem());
        warn.setWarnItemId(WarnItem.SGJS_DESIGN_CHANGE.getWarnItemId());
        warn.setWarnScopeType("4");
        warn.setWarnScope("area_technology_leader");
        String warnContent = "您好，设计变更管理新增了数据【"+ObjectUtils.nvlString(manage.getChangeAmount())+"】已提交至海外事业部审批";
        warn.setWarnContent(warnContent);
        warn.setWarnUrl("/constructionTechnique/DesignChangeManagement/detail");
        systemServiceApi.addWarnNonGm(warn);
    }
}

