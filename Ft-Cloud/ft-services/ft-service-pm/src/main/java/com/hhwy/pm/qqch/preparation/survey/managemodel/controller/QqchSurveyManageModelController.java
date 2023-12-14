package com.hhwy.pm.qqch.preparation.survey.managemodel.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModel;
import com.hhwy.pm.qqch.preparation.survey.managemodel.domain.QqchSurveyManageModelVo;
import com.hhwy.pm.qqch.preparation.survey.managemodel.service.IQqchSurveyManageModelService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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

                                                                                                                                                                                                                                                                                


    /**
     *  总体勘察设计经营模式确定 列表查询
     * @param qqchSurveyManageModelParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyManageModel:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计经营模式策划", name = "2.1.1勘察设计经营模式确定" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchSurveyManageModelList(@Validated(ValidationGroups.Select.class) QqchSurveyManageModel qqchSurveyManageModelParam){
        QqchSurveyManageModelVo qqchSurveyManageModelVo = qqchSurveyManageModelService.getQqchSurveyManageModelList(qqchSurveyManageModelParam);
        return AjaxResult.success(qqchSurveyManageModelVo);
    }

    /**
     *  新增
     * @param qqchSurveyManageModelVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyManageModel:add")
    @PostMapping("/add")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计经营模式策划", name = "2.1.1勘察设计经营模式确定" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSurveyManageModel(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyManageModelVo qqchSurveyManageModelVo){
        qqchSurveyManageModelService.save(qqchSurveyManageModelVo);
        return AjaxResult.success(qqchSurveyManageModelVo);
    }

    /**
     *  确认
     * @param qqchSurveyManageModelVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyManageModel:confirm")
    @PostMapping("/confirm")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计经营模式策划", name = "2.1.1勘察设计经营模式确定" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyManageModelVo qqchSurveyManageModelVo){
     QqchSurveyManageModelVo   qqchSurveyManageModelVo1= qqchSurveyManageModelService.confirm(qqchSurveyManageModelVo);
        return AjaxResult.success(qqchSurveyManageModelVo1);
    }

    /**
     *  查询同类项目
     */
    @PostMapping("/querySameProject")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计经营模式策划", name = "2.1.1勘察设计经营模式确定" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult querySameTypeProject(@Validated(ValidationGroups.Select.class)@RequestBody QqchSurveyManageModel qqchSurveyManageModelParam){
        List<QqchSurveyManageModel> result = qqchSurveyManageModelService.getSameTypeProject(qqchSurveyManageModelParam);
        return AjaxResult.success(result);
    }

}
