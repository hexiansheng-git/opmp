package com.hhwy.sp.techFile.sgjsCheckData.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.security.annotation.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.techFile.sgjsCheckData.service.ISgjsCheckDataService;
import com.hhwy.sp.techFile.sgjsCheckData.domain.SgjsCheckData;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;

/**
 * @author xuzl
 * @date 2024-10-18 15:52:00
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsCheckData")
public class SgjsCheckDataController extends BaseController {

    @Autowired
    private ISgjsCheckDataService sgjsCheckDataService;

    @PreAuthorize(hasPermi = "sgjsCheckData:list")
    @GetMapping
    public AjaxResult getSgjsCheckData(@Validated(ValidationGroups.Get.class) SgjsCheckData sgjsCheckDataParam) {
        SgjsCheckData sgjsCheckData = sgjsCheckDataService.getSgjsCheckData(sgjsCheckDataParam);
        return AjaxResult.success(sgjsCheckData);
    }
    @PreAuthorize(hasPermi = "sgjsCheckData:list")
    @GetMapping("/list")
    public AjaxResult getSgjsCheckDataList(@Validated(ValidationGroups.Select.class) SgjsCheckData sgjsCheckDataParam) {
        startPage();
        List<SgjsCheckData> sgjsCheckDataList = sgjsCheckDataService.getSgjsCheckDataList(sgjsCheckDataParam);
        return getDataTableAjaxResult(sgjsCheckDataList);
    }

    @PreAuthorize(hasPermi = "sgjsCheckData:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsCheckData(@Validated(ValidationGroups.Save.class) @RequestBody SgjsCheckData sgjsCheckDataParam) {
        sgjsCheckDataService.insertSgjsCheckData(sgjsCheckDataParam);
        return AjaxResult.success(sgjsCheckDataParam);
    }

    @PreAuthorize(hasPermi = "sgjsCheckData:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsCheckDataList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsCheckData> sgjsCheckDataListParam) {
        sgjsCheckDataService.insertSgjsCheckDataList(sgjsCheckDataListParam);
        return AjaxResult.success(sgjsCheckDataListParam);
    }

    @PreAuthorize(hasPermi = "sgjsCheckData:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsCheckData(@Validated(ValidationGroups.Update.class) @RequestBody SgjsCheckData sgjsCheckDataParam) {
        return toAjax(sgjsCheckDataService.updateSgjsCheckData(sgjsCheckDataParam));
    }

    @PreAuthorize(hasPermi = "sgjsCheckData:batchUpdate")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsCheckDataList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsCheckData> sgjsCheckDataListParam) {
        return toAjax(sgjsCheckDataService.updateSgjsCheckDataList(sgjsCheckDataListParam));
    }

    @PostMapping("/delete")
    public AjaxResult deleteSgjsCheckData(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsCheckData sgjsCheckDataParam) {
        return toAjax(sgjsCheckDataService.deleteSgjsCheckData(sgjsCheckDataParam));
    }

    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsCheckDataByPks(@PathVariable Long[] ids) {
        List<Long> sgjsCheckDataPkList = Arrays.asList(ids);
        return toAjax(sgjsCheckDataService.deleteSgjsCheckDataByPks(sgjsCheckDataPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsCheckData sgjsCheckDataParam) throws IOException {
        List<SgjsCheckData> sgjsCheckDataList = sgjsCheckDataService.getSgjsCheckDataList(sgjsCheckDataParam);
        ExcelUtils<SgjsCheckData> util = new ExcelUtils<>(SgjsCheckData.class);
        sgjsCheckDataList.forEach(p ->{
            String effective = p.getEffective();
            if(StrUtil.isNotBlank(effective)){
                p.setEffective(effective.equals("0")?"有效":"无效");
            }
        });
        util.exportExcel(response, sgjsCheckDataList, DateUtils.getDate());
    }

    //数据推送总部版
//    public void doSendGm(List<Long> delIdList){
//        SgjsTechnicalData4Update sendData = new SgjsTechnicalData4Update();
//        sendData.setProjectCode(getProjectDto().getProjectCode());
//        //查询待推送数据
//        List<SgjsTechnicalData> sgjsTechnicalData = sgjsTechnicalDataService.getList(new SgjsTechnicalData());
//        if (CollUtil.isEmpty(sgjsTechnicalData)) {
//            //集合为空，推送一个项目编号
//            rocketMQTemplate.convertAndSend("sgjs_technical_data:tenantSuccess", sendData);
//            return;
//        }
//        sendData.setTreeList(sgjsTechnicalData);
//        sendData.setDelIdList(delIdList);
//        rocketMQTemplate.convertAndSend("sgjs_technical_data:tenantSuccess", sendData);
//    }
}
