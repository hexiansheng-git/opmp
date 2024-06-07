package com.hhwy.sp.designChangeList.controller;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeList;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManageRecord;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeListService;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeManageRecordService;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeWbsService;
import com.hhwy.sp.designChangeList.vo.ChangeManagSaveVo;
import com.hhwy.sp.designChangeList.vo.SgjsDesignChangeManageVo;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.redisson.transaction.operation.map.MapAddAndGetOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManage;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeManageService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 施工技术管理-设计变更管理Controller
 * 
 * @author wk
 * @date 2024-04-16
 */
@RestController
@RequestMapping("/designChangeList")
public class SgjsDesignChangeManageController extends BaseController {

    @Autowired
    private ISgjsDesignChangeManageService sgjsDesignChangeManageService;
    @Autowired
    private ISgjsDesignChangeWbsService designChangeWbsService;
    @Autowired
    private ISgjsDesignChangeListService designChangeListService;
    @Autowired
    private ISgjsDesignChangeManageRecordService recordService;
    @Autowired
    private PmServiceApi pmServiceApi;

    /**
     * 查询施工技术管理-设计变更管理列表
     */
    @GetMapping("/list")
    @PreAuthorize(hasPermi = "designChangeList:list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) SgjsDesignChangeManageVo sgjsDesignChangeManage) {
        startPage();
        sgjsDesignChangeManage.setParams(ObjectUtils.toMap(
                "startDate", sgjsDesignChangeManage.getStartDate(),
                "endDate", sgjsDesignChangeManage.getEndDate()
        ));
        List<SgjsDesignChangeManage> list = sgjsDesignChangeManageService.selectSgjsDesignChangeManageList(sgjsDesignChangeManage);
        FlowInfoSearchUtil.getFlowInfo(list, FlowEnum.SGJS_DESIGN_CHANGE_MANAGE);
        return getDataTableAjaxResult(list);
    }

    @GetMapping("/detail")
    public AjaxResult detail(SgjsDesignChangeManage manage) {
        if(manage.getId() == null){
            manage = new SgjsDesignChangeManage();
            manage.setChangeApplyDate(new Date());
        }else{
            manage = this.sgjsDesignChangeManageService.selectSgjsDesignChangeManageById(manage.getId());
            //wbs
            List<SgjsDesignChangeWbs> wbsList = designChangeWbsService.wbsTreeList(manage.getId(),"1");
            
            manage.setWbsList(wbsList);
        }
        putPrjInfo(manage);
        FlowInfoSearchUtil.getFlowInfo(manage, FlowEnum.SGJS_DESIGN_CHANGE_MANAGE);
        manage.setDirectFlag( sgjsDesignChangeManageService.isDirectProject() );
        
        return AjaxResult.success(manage);
    }

    //过程及结果登记明细
    @GetMapping("/detailProcess")
    public AjaxResult detailProcess(SgjsDesignChangeManage manage) {
        if(manage.getId() == null)
            return AjaxResult.success(new SgjsDesignChangeManage());
        manage = this.sgjsDesignChangeManageService.selectSgjsDesignChangeManageById(manage.getId());
        if(manage == null)
            return AjaxResult.error("未获取到数据，请确认ID是否正确");
        //wbs
        List<SgjsDesignChangeWbs> wbsList = designChangeWbsService.wbsTreeList(manage.getId(),"2");
        manage.setWbsList(wbsList);
        putPrjInfo(manage);
        //过程记录
        SgjsDesignChangeManageRecord query = new SgjsDesignChangeManageRecord();
        query.setMainId(manage.getId());
        List<SgjsDesignChangeManageRecord> list = recordService.selectSgjsDesignChangeManageRecordList(query);
        List<SgjsDesignChangeManageRecord> recordList = new ArrayList<>();
        List<SgjsDesignChangeManageRecord> recordContactList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SgjsDesignChangeManageRecord temp = list.get(i);
            if(StringUtils.equals(temp.getType(),"1"))
                recordList.add(temp);
            if(StringUtils.equals(temp.getType(),"2"))
                recordContactList.add(temp);
        }
        manage.setRecordList(recordList);
        manage.setRecordContactList(recordContactList);
        return AjaxResult.success(manage);
    }

    /**
     * 获取wbs全量树形，
     * @param wbs {id,type类型，默认2}
     * @return
     */
    @GetMapping("/wbsTreeList")
    public AjaxResult wbsTreeList(SgjsDesignChangeWbs wbs) {
        String type = ObjectUtils.nvlString(wbs.getPtVar2(),"2");
        List<SgjsDesignChangeWbs> wbsList = designChangeWbsService.wbsTreeList(wbs.getId(),type);
        return AjaxResult.success(wbsList);
    }
    
    //根据wbs编号获取清单(全量树形)
    @GetMapping("/listByWbsCode")
    public AjaxResult listByWbsCode(SgjsDesignChangeList list) {
        List<SgjsDesignChangeList> resultList = designChangeListService.treeList(list.getMainId(), list.getType(), list.getWbsCode());
        return AjaxResult.success(resultList);
    }

    /**                            
     * 获取wbs挂接的清单
     * @param wbs {code WBS编号}
     * @return {list清单集合，wbsList:wbs集合}
     */
    @GetMapping("/relateList")
    public AjaxResult relateList(SgjsDesignChangeWbs wbs) {
        Map<String,Object> map = sgjsDesignChangeManageService.relateList(wbs);
        return AjaxResult.success(map);
    }

    @PostMapping("/importData")
    public AjaxResult importData(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
        try {
            List<SgjsDesignChangeList> list = sgjsDesignChangeManageService.importData(file);
            return AjaxResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("导入失败:"+e.getMessage());
        }
    }

    //台账页导出
    @PostMapping("/export")
    public void export(@RequestBody Map map, HttpServletResponse response) throws IOException {
        FtExcelUtil<SgjsDesignChangeManage> util = new FtExcelUtil<>(SgjsDesignChangeManage.class);
        List<SgjsDesignChangeManage> list = exportForGm(map);
        util.exportExcel(response, list, "数据","设计变更管理.xlsx");
    }

    //查询台账页导出数据
    @PostMapping("/exportForGm")
    public List<SgjsDesignChangeManage> exportForGm(@RequestBody Map map) throws IOException {
        List<SgjsDesignChangeManage> list = new ArrayList<>();
        if(map == null || ObjectUtils.isBlank(map.get("ids")) ){
            list = this.sgjsDesignChangeManageService.selectSgjsDesignChangeManageList(new SgjsDesignChangeManage());
        }else{
            Long[] ids = Convert.toLongArray(map.get("ids").toString());
            list = this.sgjsDesignChangeManageService.selectSgjsDesignChangeManageByIds(ids);
        }
        return list;
    }
     
    
    @PostMapping("/exportData")
    public void exportData(@RequestBody SgjsDesignChangeList designChangeList, HttpServletResponse response) throws IOException {
        FtExcelUtil<SgjsDesignChangeList> util = new FtExcelUtil<>(SgjsDesignChangeList.class);
        if(designChangeList.getId() != null){
            //重写wbs的Id,从100开始
            Map<Long,Long> wbsOIdMap = new HashMap<>();
            Function<Long,Long> buildNewIdFunc = (oid)->{
                Long newId = wbsOIdMap.get(oid);
                if(newId != null) return newId;
                Long resu = wbsOIdMap.size()+100L;
                wbsOIdMap.put(oid, resu);
                return resu;
            };
            List<SgjsDesignChangeList> wbsList = designChangeListService.selectWbsAsDesignList(designChangeList.getId());
            for (int i = 0; i < wbsList.size(); i++) {
                SgjsDesignChangeList temp = wbsList.get(i);
                temp.setId(buildNewIdFunc.apply(temp.getId()));
                if(temp.getPid() == null || temp.getPid() < 1)
                    continue;
                temp.setPid(buildNewIdFunc.apply(temp.getPid()));
            }
            SgjsDesignChangeList query = new SgjsDesignChangeList();
            query.setMainId(designChangeList.getId());
            List<SgjsDesignChangeList> list = designChangeListService.selectSgjsDesignChangeListList(query);
            //将清单第一级的父级ID改为wbsID
            for (int i = 0; i < list.size(); i++) {
                SgjsDesignChangeList temp = list.get(i);
                temp.setWbsCode("");
                temp.setWbsName("");
                if(temp.getPid()==null || temp.getPid() == -1L){
                    temp.setPid(wbsOIdMap.get(temp.getWbsId()));
                }
            }
            wbsList.addAll(list);
            List<SgjsDesignChangeList> resuList = TreeUtil.exportListFormat((List)wbsList);
            util.exportExcel(response, resuList, "数据","设计变更清单.xlsx");
        }else{
            util.exportExcel(response, new ArrayList<>(2), "数据","设计变更清单.xlsx", Arrays.asList(
                    "清单编号","清单中文名称","清单外文名称","清单类型","单位","本次变更数量","本次变更单价（不含税）","本次变更金额（不含税）"
            ));
        }
    }

    /**
     * 新增保存施工技术管理-设计变更管理
     */
    @PostMapping("/save")
    @CustomLogger(title = "施工技术-设计变更管理",name = "设计变更管理",businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@RequestBody ChangeManagSaveVo saveVo) {
        try{
            saveVo.setPtVar2(ObjectUtils.nvlString(saveVo.getPtVar2(),"1"));
            Long id = sgjsDesignChangeManageService.save(saveVo);
            return AjaxResult.success(id);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 同步
     * @param manage
     * @return
     */
    @PostMapping("/sync")
    public AjaxResult sync(@RequestBody SgjsDesignChangeManage manage) {
        try{
            sgjsDesignChangeManageService.sync(manage.getId());
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    

    /**
     * 删除施工技术管理-设计变更管理
     */
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody SgjsDesignChangeManage manage){
        try{
            Assert.notNull(manage.getId(), "id不能为空");
            sgjsDesignChangeManageService.deleteSgjsDesignChangeManageById(manage.getId());
        }catch(Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }
    
    private void putPrjInfo(SgjsDesignChangeManage manage){
        Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
        if (CollectionUtils.isEmpty(prjInfo)) {
            logger.error("获取项目信息异常");
        }else{
            manage.setProjectCode(ObjectUtils.nvlString(prjInfo.get("projectCode")));
            manage.setProjectName(ObjectUtils.nvlString(prjInfo.get("projectName")));
        }
        AjaxResult result = pmServiceApi.getContractInfo();
        if(!AjaxResult.isSuccess(result)){
            logger.error("获取合同信息异常");
        }else{
            JSONObject conObj = JSONObject.parseObject(JSONObject.toJSONString(result.getData()));
            manage.setMasterContractCode(conObj.getString("code"));
            manage.setCurrency(conObj.getString("listCurrencyName"));
            manage.setCurrencyCode(conObj.getString("listCurrencyCode"));
        } 
    }

    @PostMapping("/listener")
    public AjaxResult listener(@RequestParam("id") Long businessId){
        sgjsDesignChangeManageService.finishFlow(businessId);
        return AjaxResult.success();
    }

    @PostMapping("/pushMsg")
    public AjaxResult pushMsg(@RequestParam("id") Long id){
        sgjsDesignChangeManageService.pushMsg(id);
        return AjaxResult.success();
    }
}
