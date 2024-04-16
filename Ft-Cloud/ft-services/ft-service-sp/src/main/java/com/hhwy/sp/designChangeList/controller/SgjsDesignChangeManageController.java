package com.hhwy.sp.designChangeList.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
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
import com.hhwy.utils.validation.ValidationGroups;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.hhwy.sp.designChangeList.domain.SgjsDesignChangeManage;
import com.hhwy.sp.designChangeList.service.ISgjsDesignChangeManageService;

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
     * 导出施工技术管理-设计变更管理列表
     */
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(SgjsDesignChangeManage sgjsDesignChangeManage, Locale locale) {
//        List<SgjsDesignChangeManage> list = sgjsDesignChangeManageService.selectSgjsDesignChangeManageList(sgjsDesignChangeManage);
//        ExcelUtil<SgjsDesignChangeManage> util = new ExcelUtil<SgjsDesignChangeManage>(SgjsDesignChangeManage.class, locale);
//        return util.exportExcel(list, "DesignChangeList");
//    }

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
