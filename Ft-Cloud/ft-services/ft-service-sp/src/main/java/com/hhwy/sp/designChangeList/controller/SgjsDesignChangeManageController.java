package com.hhwy.sp.designChangeList.controller;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.log.enums.BusinessType;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.enums.FlowEnum;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeList;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeWbs;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeListService;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeWbsService;
import com.hhwy.sp.designChangeList.vo.ChangeManagSaveVo;
import com.hhwy.sp.designChangeList.vo.SgjsDesignChangeManageVo;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
        }else{
            manage = this.sgjsDesignChangeManageService.selectSgjsDesignChangeManageById(manage.getId());
            //wbs
            List<SgjsDesignChangeWbs> wbsList = designChangeWbsService.wbsTreeList(manage.getId());
            manage.setWbsList(wbsList);
        }
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
        }    
        return AjaxResult.success(manage);
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

    @PostMapping("/exportData")
    public void export(@RequestBody SgjsDesignChangeList designChangeList, HttpServletResponse response) throws IOException {
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
    @ResponseBody
    public AjaxResult save(@RequestBody ChangeManagSaveVo saveVo) {
        try{
            sgjsDesignChangeManageService.save(saveVo);
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
    public AjaxResult remove(String ids)
    {
        return toAjax(sgjsDesignChangeManageService.deleteSgjsDesignChangeManageByIds(ids));
    }
}
