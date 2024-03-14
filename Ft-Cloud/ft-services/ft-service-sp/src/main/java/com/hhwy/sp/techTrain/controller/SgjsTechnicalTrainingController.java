package com.hhwy.sp.techTrain.controller;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.techTrain.domain.SgjsTechnicalTraining;
import com.hhwy.sp.techTrain.service.ISgjsTechnicalTrainingService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author wll-技术培训管理
 * @date 2023-12-07 18:05:53
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsTechnicalTraining")
public class SgjsTechnicalTrainingController extends BaseController {

    @Autowired
    private ISgjsTechnicalTrainingService sgjsTechnicalTrainingService;


    /**
     * 详情
     * @param sgjsTechnicalTrainingParam
     * @return
     */
//    @PreAuthorize(hasPermi = "sgjsTechnicalTraining:select")
    @GetMapping
    public AjaxResult getSgjsTechnicalTraining(@Validated(ValidationGroups.Get.class) SgjsTechnicalTraining sgjsTechnicalTrainingParam) {
        SgjsTechnicalTraining sgjsTechnicalTraining = sgjsTechnicalTrainingService.getSgjsTechnicalTraining(sgjsTechnicalTrainingParam);
        return AjaxResult.success(sgjsTechnicalTraining);
    }


    /**
     * 列表查询
     * @param sgjsTechnicalTrainingParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalTraining:list")
    @GetMapping("/list")
    public AjaxResult getSgjsTechnicalTrainingList(@Validated(ValidationGroups.Select.class) SgjsTechnicalTraining sgjsTechnicalTrainingParam) {
        startPage();
        List<SgjsTechnicalTraining> sgjsTechnicalTrainingList = sgjsTechnicalTrainingService.getSgjsTechnicalTrainingList(sgjsTechnicalTrainingParam);
        return getDataTableAjaxResult(sgjsTechnicalTrainingList);
    }

    /**
     * 新增
     * @param sgjsTechnicalTrainingParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalTraining:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsTechnicalTraining(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechnicalTraining sgjsTechnicalTrainingParam) {
        sgjsTechnicalTrainingService.insertSgjsTechnicalTraining(sgjsTechnicalTrainingParam);
        return AjaxResult.success(sgjsTechnicalTrainingParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalTraining:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsTechnicalTrainingList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsTechnicalTraining> sgjsTechnicalTrainingListParam) {
        sgjsTechnicalTrainingService.insertSgjsTechnicalTrainingList(sgjsTechnicalTrainingListParam);
        return AjaxResult.success(sgjsTechnicalTrainingListParam);
    }

    /**
     * 修改
     * @param sgjsTechnicalTrainingParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalTraining:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsTechnicalTraining(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechnicalTraining sgjsTechnicalTrainingParam) {
        return toAjax(sgjsTechnicalTrainingService.updateSgjsTechnicalTraining(sgjsTechnicalTrainingParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalTraining:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsTechnicalTrainingList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsTechnicalTraining> sgjsTechnicalTrainingListParam) {
        return toAjax(sgjsTechnicalTrainingService.updateSgjsTechnicalTrainingList(sgjsTechnicalTrainingListParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalTraining:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsTechnicalTraining(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsTechnicalTraining sgjsTechnicalTrainingParam) {
        return toAjax(sgjsTechnicalTrainingService.deleteSgjsTechnicalTraining(sgjsTechnicalTrainingParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalTraining:remove")
    @PostMapping("/delByIds")
    public AjaxResult deleteSgjsTechnicalTrainingByPks(@RequestBody SgjsTechnicalTraining sgjsTechnicalTraining) {

        if(sgjsTechnicalTraining.getIds().size()>0){
            return toAjax(sgjsTechnicalTrainingService.deleteSgjsTechnicalTrainingByPks(sgjsTechnicalTraining.getIds()));
        }else {
            return AjaxResult.error("未选中数据");
        }

    }

    /**
     * 导出
     * @param response
     * @param sgjsTechnicalTrainingParam
     * @throws IOException
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalTraining:report")
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody SgjsTechnicalTraining sgjsTechnicalTrainingParam) throws IOException {

        List<Long> ids = sgjsTechnicalTrainingParam.getIds();
        List<SgjsTechnicalTraining> list=null;
        if (CollectionUtils.isEmpty(ids)){
            List<SgjsTechnicalTraining> sgjsTechnicalTrainingList = sgjsTechnicalTrainingService.getSgjsTechnicalTrainingList(sgjsTechnicalTrainingParam);
            if(CollectionUtils.isNotEmpty(sgjsTechnicalTrainingList)){
                list=sgjsTechnicalTrainingList;
            }
        }else {
            List<SgjsTechnicalTraining> sgjsTechnicalTrainingList=sgjsTechnicalTrainingService.getIds(ids);
            if(CollectionUtils.isNotEmpty(sgjsTechnicalTrainingList)){
                list=sgjsTechnicalTrainingList;
            }
        }
        ExcelUtils<SgjsTechnicalTraining> utils = new ExcelUtils<>(SgjsTechnicalTraining.class);
        utils.exportExcel(response,list,DateUtils.getDate());

    }
}
