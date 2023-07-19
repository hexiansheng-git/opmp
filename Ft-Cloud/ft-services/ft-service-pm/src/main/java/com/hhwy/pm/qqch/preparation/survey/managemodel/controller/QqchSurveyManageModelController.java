package com.hhwy.pm.qqch.preparation.survey.managemodel.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.managemodel.service.IQqchSurveyManageModelService;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.MasterEntity;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ldd
 * @date 2023-07-18 14:57:01
 * @remark  2.1.1 总体勘察设计经营模式确定
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyManageModel")
public class QqchSurveyManageModelController extends BaseController{

    @Autowired
    private IQqchSurveyManageModelService qqchSurveyManageModelService;

                                                                                                                                                                                                                                                                                

    @PreAuthorize(hasPermi = "qqchSurveyManageModel:list")
    @GetMapping
    public AjaxResult getQqchSurveyManageModel(@Validated(ValidationGroups.Get.class)  QqchSurveyManageModel qqchSurveyManageModelParam){
        QqchSurveyManageModel qqchSurveyManageModel =  qqchSurveyManageModelService.getQqchSurveyManageModel(qqchSurveyManageModelParam);
        return AjaxResult.success(qqchSurveyManageModel);
    }

    /**
     *  总体勘察设计经营模式确定 列表查询
     * @param qqchSurveyManageModelParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyManageModel:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyManageModelList(@Validated(ValidationGroups.Select.class) QqchSurveyManageModel qqchSurveyManageModelParam){
        //startPage();
        MasterEntity masterEntity = qqchSurveyManageModelService.getQqchSurveyManageModelList(qqchSurveyManageModelParam);
        return AjaxResult.success(masterEntity);
    }

    /**
     *  新增
     * @param masterEntity
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyManageModel:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSurveyManageModel(@Validated(ValidationGroups.Save.class) @RequestBody MasterEntity masterEntity){
        qqchSurveyManageModelService.insertQqchSurveyManageModel(masterEntity);
        return AjaxResult.success(masterEntity);
    }

    /**
     *  确认
     * @param masterEntity
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyManageModel:add")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody MasterEntity masterEntity){
        qqchSurveyManageModelService.confirm(masterEntity);
        return AjaxResult.success(masterEntity);
    }



    @PreAuthorize(hasPermi = "qqchSurveyManageModel:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSurveyManageModelList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSurveyManageModel> qqchSurveyManageModelListParam){
        qqchSurveyManageModelService.insertQqchSurveyManageModelList(qqchSurveyManageModelListParam);
        return AjaxResult.success(qqchSurveyManageModelListParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyManageModel:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSurveyManageModel(@Validated(ValidationGroups.Update.class) @RequestBody QqchSurveyManageModel qqchSurveyManageModelParam){
        return toAjax(qqchSurveyManageModelService.updateQqchSurveyManageModel(qqchSurveyManageModelParam));
    }

    
    @PreAuthorize(hasPermi = "qqchSurveyManageModel:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSurveyManageModel(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSurveyManageModel qqchSurveyManageModelParam){
        return toAjax(qqchSurveyManageModelService.deleteQqchSurveyManageModel(qqchSurveyManageModelParam));
    }

}
