package com.hhwy.pm.qqch.preparation.survey.managemodel.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.MasterEntity;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.MasterEntityVo;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;
import com.hhwy.pm.qqch.preparation.survey.managemodel.service.IQqchSurveyManageModelService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

                                                                                                                                                                                                                                                                                


    /**
     *  总体勘察设计经营模式确定 列表查询
     * @param qqchSurveyManageModelParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyManageModel:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyManageModelList(@Validated(ValidationGroups.Select.class) QqchSurveyManageModel qqchSurveyManageModelParam){
        MasterEntity masterEntity = qqchSurveyManageModelService.getQqchSurveyManageModelList(qqchSurveyManageModelParam);
        return AjaxResult.success(masterEntity);
    }

    /**
     *  新增
     * @param masterEntityVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyManageModel:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSurveyManageModel(@Validated(ValidationGroups.Save.class) @RequestBody MasterEntityVo masterEntityVo){
        qqchSurveyManageModelService.save(masterEntityVo);
        return AjaxResult.success(masterEntityVo);
    }

    /**
     *  确认
     * @param masterEntityVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyManageModel:confirm")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody MasterEntityVo masterEntityVo){
        qqchSurveyManageModelService.confirm(masterEntityVo);
        return AjaxResult.success(masterEntityVo);
    }


}
