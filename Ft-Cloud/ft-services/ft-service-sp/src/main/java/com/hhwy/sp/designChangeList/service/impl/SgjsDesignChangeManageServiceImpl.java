package com.hhwy.sp.designChangeList.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.UUIDUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.xmslEngineeringReport.domain.XmslEngineeringReport;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeList;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeListService;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeWbsService;
import com.hhwy.sp.designChangeList.vo.ChangeManagSaveVo;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.utils.validation.ValidationUtil;
import jdk.nashorn.internal.ir.ContinueNode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
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
    private PmServiceApi pmServiceApi;

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
    public void save(ChangeManagSaveVo saveVo) {
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
            sgjsDesignChangeManageMapper.deleteWbsByMainId(saveVo.getId());
        List<SgjsDesignChangeWbs> addWbsList = new ArrayList<>();
        List<SgjsDesignChangeList> addList = new ArrayList<>();
        List<String> wbsCodeList = new ArrayList<>();
        handlerWbsList(saveVo,null,saveVo.getWbsList(),addWbsList,wbsCodeList,addList);
        changeListService.deleteByWbsCodes(saveVo.getId(),"1",wbsCodeList);
        changeWbsService.batchInsert(addWbsList);
        changeListService.batchInsert(addList);
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
            addWbsList.add(wbs);
            if(wbs.getPtVar1().equals("1"))
                deleteWbsCodeList.add(wbs.getCode());
            //处理清单
            handlerList(saveVo,wbs,null,wbs.getList(),addList);
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
            addList.add(temp);
            handlerList(saveVo,wbs,temp,temp.getChildren(),addList);
        }
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
    public int deleteSgjsDesignChangeManageById(Long id) {
        return sgjsDesignChangeManageMapper.deleteSgjsDesignChangeManageById(id);
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
            //2、加载其下清单
            Long id = sgjsDesignChangeManageMapper.lastEffectId();
            if(id == null){ //第一次添加
                XmslEngineeringReport report = new XmslEngineeringReport();
                report.setWbsCode(StringUtils.join(wbsMap.keySet(), ","));
                AjaxResult result = pmServiceApi.relateList(report);
                Map<String,Object> resultMap = (Map)result.getData();
                for(String wbsCode : resultMap.keySet()){
                    List<SgjsDesignChangeList> designList = new ArrayList<>();
                    List<XmslContractList> list = JSONObject.parseArray(JSONObject.toJSONString(resultMap.get(wbsCode)),XmslContractList.class);
                    //转换成SgjsDesignChangeList
                    for (int i = 0; i < list.size(); i++) {
                        XmslContractList temp = list.get(i);
                        SgjsDesignChangeList designChangeList = trans2DesignList(wbs.getCode(),temp);
                        designList.add(designChangeList);
                    }
                    designList = TreeUtil.build(designList,null);
                    wbsMap.get(wbsCode).setParams(ObjectUtils.toMap("list",designList));
                }
            }else{ //查询设计变更最新版本的挂接清单
                SgjsDesignChangeList query = new SgjsDesignChangeList();
                query.setMainId(id);
                query.setType(1);
                query.setParams(ObjectUtils.toMap("wbsCodes", wbsMap.keySet()));
                List<SgjsDesignChangeList> designList = changeListService.selectSgjsDesignChangeListList(query);
                Map<String,List<SgjsDesignChangeList>> groupDeisngList = designList.stream().collect(Collectors.groupingBy(r->r.getWbsCode()));
                for(String wbsCode : groupDeisngList.keySet()){
                    List<SgjsDesignChangeList> tempList = groupDeisngList.get(wbsCode);
                    designList = TreeUtil.build(designList,-1L);
                    wbsMap.get(wbsCode).setParams(ObjectUtils.toMap("list",designList));
                }
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
        design.setConNum(list.getWinNum());
        design.setConExcludePrice(list.getWinAmount());
        design.setConSumPrice(list.getWinAmount());
        design.setZeroNum(ObjectUtils.toDecimal(list.getPtVar1()));
        design.setZeroExcludePrice(list.getWinAmount());
        design.setZeroSumPrice(design.getZeroNum().multiply(design.getZeroExcludePrice()));
        design.setBeforeNum(design.getZeroNum());
        design.setBeforeExcludePrice(design.getZeroExcludePrice());
        design.setBeforeSumPrice(design.getZeroSumPrice());
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
}
