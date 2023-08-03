package com.hhwy.pm.xmsl.wbs.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author wk
 * @date 2023-07-13 18:02:49
 * @remark
 */
@Validated
@RestController
@RequestMapping("/xmslWbsMain")
public class XmslWbsMainController extends BaseController {

    @Autowired
    private IXmslWbsMainService xmslWbsMainService;

    @PreAuthorize(hasPermi = "xmslWbsMain:list")
    @GetMapping
    public AjaxResult getXmslWbsMain(@Validated(ValidationGroups.Get.class) @RequestBody XmslWbsMain xmslWbsMainParam) {
        XmslWbsMain xmslWbsMain = xmslWbsMainService.getXmslWbsMain(xmslWbsMainParam);
        return AjaxResult.success(xmslWbsMain);
    }

    @PreAuthorize(hasPermi = "xmslWbsMain:list")
    @GetMapping("/list")
    public AjaxResult getXmslWbsMainList(@Validated(ValidationGroups.Select.class) XmslWbsMain xmslWbsMainParam) {
        startPage();
        List<XmslWbsMain> xmslWbsMainList = xmslWbsMainService.getXmslWbsMainList(xmslWbsMainParam);
        return getDataTableAjaxResult(xmslWbsMainList);
    }

    @PreAuthorize(hasAnyPermi = {"xmslWbsMain:detail","xmslWbsMain:edit"})
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody XmslWbsMain xmslWbsMainParam) {
        XmslWbsMain wbsMain = null;
        if(xmslWbsMainParam.getId() == null){
            wbsMain =xmslWbsMainService.getLast();
        }else{
            wbsMain = xmslWbsMainService.getById(xmslWbsMainParam.getId());
        }
        //是否有调整记录
        Long count = xmslWbsMainService.getXmslWbsMainCount(new XmslWbsMain());
        if(wbsMain != null)
            wbsMain.setParams(ObjectUtils.toMap(Constant.HISTORY_NOTE_FIELD_NAME,count>1?1:0));
        return AjaxResult.success(wbsMain);
    }

    /**
     * 获取当前调整数据
     * @return
     */
    @PreAuthorize(hasAnyPermi = {"xmslWbsMain:adjust"})
    @GetMapping("/adjustInfo")
    public AjaxResult adjustInfo() {
        XmslWbsMain wbsMain = this.xmslWbsMainService.getAdjustInfo();
        if(wbsMain == null){
            Long id = xmslWbsMainService.initAdjust();
            if(id != null)
                wbsMain = this.xmslWbsMainService.getById(id);
            return AjaxResult.success("",wbsMain);
        }
        //是否有调整记录
        Long count = xmslWbsMainService.getXmslWbsMainCount(new XmslWbsMain());
        if(wbsMain != null)
            wbsMain.setParams(ObjectUtils.toMap(Constant.HISTORY_NOTE_FIELD_NAME,count>1?1:0));
        return AjaxResult.success(wbsMain);
    }

    @PreAuthorize(hasPermi = "xmslWbsMain:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslWbsMain(@Validated(ValidationGroups.Delete.class) @RequestBody XmslWbsMain xmslWbsMainParam) {
        return toAjax(xmslWbsMainService.deleteXmslWbsMain(xmslWbsMainParam));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslWbsMain xmslWbsMainParam) throws IOException {
        List<XmslWbsMain> xmslWbsMainList = xmslWbsMainService.getXmslWbsMainList(xmslWbsMainParam);
        ExcelUtils<XmslWbsMain> util = new ExcelUtils<>(XmslWbsMain.class);
        util.exportExcel(response, xmslWbsMainList, DateUtils.getDate());
    }

    /**
     * 审批监听器
     * @param map
     * @return
     */
    @PostMapping("/finishFlow")
    public AjaxResult finishFlow(@RequestBody Map map){
        Long businessId = ObjectUtils.nvlLong(((Map)((Map)map.get("execution")).get("variables")).get("businessId")) ;
//        DelegateTask delegateTask = JSONObject.parseObject(JSONObject.toJSONString(map.get("execution")),DelegateTask.class);;
//        Map varMap = delegateTask.getVariables();
//        xmslWbsMainService.finishFlow(ObjectUtils.nvlLong(varMap.get("businessId")));
        xmslWbsMainService.finishFlow(businessId);
        return AjaxResult.success();
    }
}
