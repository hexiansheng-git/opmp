package com.hhwy.pm.gm.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.enums.FlowEnum;
import com.hhwy.enums.QyzsBtnEnum;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.gm.service.IGmThirdService;
import com.hhwy.pm.gm.service.IQyzsBtnService;
import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.utils.ObjectUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 为总部版提供的接口
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2023/8/24 15:59   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2023/8/24 15:59    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
@RestController
@RequestMapping("/gmThird")
public class GmThirdController {
    @Autowired
    private IGmThirdService gmThirdService;
    @Autowired
    private IQyzsBtnService qyzsBtnService;
    
    /**
     * 前期策划工作计划功能
     * @param map  {planApprovalUnit,valid,pageNum,pageSize}
     * @return
     */
    @PostMapping("/workPlanList")
    public AjaxResult workPlanList(@RequestBody Map map) {
        TableDataInfo tableDataInfo = gmThirdService.workPlanList(map);
        return AjaxResult.success(tableDataInfo);
    }
    
    /**
     * 前期策划评审功能
     * @param map {planStage,tenantKeys}
     * @return
     */
    @PostMapping("/reviewList")
    public AjaxResult reviewList(@RequestBody Map map) {
        Map<String,List<Review>> resuMap = gmThirdService.reviewList(map);
        return AjaxResult.success(resuMap);
    }

    /**
     * 所有租户下的前期策划执行检查统计信息
     * @return
     */
    @PostMapping("/inspectionSummaryList")
    public AjaxResult tenantSummaryList() {
        List<Map> list = gmThirdService.inspectionSummaryList();
        return AjaxResult.success(list);
    }

    /**
     * 前期策划执行检查信息
     * @param map
     * @return
     */
    @PostMapping("/inspectionList")
    public AjaxResult inspectionList(@RequestBody Map map) {
        List<QqchPerformInspection> list = gmThirdService.inspectionList(map);
        return AjaxResult.success(list);
    }
    

    /**
     * 前期策划执行检查
     * @param map {tenantKeys}
     * @return
     */
    @PostMapping("/evaluationList")
    public AjaxResult evaluationList(@RequestBody Map map) {
        List<QqchSummaryEvaluation> list = gmThirdService.evauluationList(map);
        return AjaxResult.success(list);
    }

    /**
     * 流程信息
     * 总部调用该接口
     * @param map {businessIds:{},flowKey(FlowEnum枚举key) }
     * @return
     */
    @RequestMapping("/getFlowInfo")
    public AjaxResult isNowfirstNode(@RequestBody Map map){
        if(MapUtils.isEmpty(map) || map.get("businessIds")==null || ObjectUtils.isBlank(map.get("flowKey")) )
            return AjaxResult.success();
        FlowEnum flowEnum = FlowEnum.valueOf(map.get("flowKey").toString());
        if(flowEnum == null)
            return AjaxResult.error("获取枚举类失败");
        Map map1 = ((Map)map.get("businessIds"));
        List list = FlowInfoSearchUtil.getFlowInfo(map1,flowEnum);
        return AjaxResult.success(list);
    }

    /**
     * 获取总部版知识库按钮信息 
     * @param map {name : 枚举名称}
     * @return
     */
    @PostMapping("/getQyzsBtnInfo")
    public AjaxResult getQyzsBtnInfo(@RequestBody Map map) {
        String name = ObjectUtils.nvlString(map.get("name"));
        QyzsBtnEnum qyzsBtnEnum = QyzsBtnEnum.valueOf(name);
        Map result = new HashMap(2);
        switch (qyzsBtnEnum.businessName()){
            case "systemMan": //勘察设计-制度及管理方法库
                result = qyzsBtnService.qyzsSystemManageMethod(qyzsBtnEnum);
                break;
            case "systemMan_file": //勘察设计-文件模板库
                result = qyzsBtnService.qyzsFileMode(qyzsBtnEnum);
                break;
            case "con":   //施工技术知识库-制度及管理方法库
                result = qyzsBtnService.qyzsConstructionManageMethod(qyzsBtnEnum);
                break;
            case "con_file":
                result = qyzsBtnService.qyzsConstructionFileMode(qyzsBtnEnum);
                break;
        }
        return AjaxResult.success(result);
    }
}
