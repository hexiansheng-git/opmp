package com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.domain.QqchSurveyResultAsk;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.domain.QqchSurveyResultAskVo;
import com.hhwy.pm.qqch.preparation.survey.surveyResultAsk.service.IQqchSurveyResultAskService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ldd
 * @date 2023-07-21 16:45:52
 * @remark 2.3.2 勘察成果验收内容形式审查要求
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyResultAsk")
public class QqchSurveyResultAskController extends BaseController {

    @Autowired
    private IQqchSurveyResultAskService qqchSurveyResultAskService;


    /**
     * 列表查询
     *
     * @param qqchSurveyResultAskParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyResultAsk:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计成果策划", name = "2.3.2勘测成果验收计划-勘测成果验收内容审查要求" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchSurveyResultAskList(@Validated(ValidationGroups.Select.class) QqchSurveyResultAsk qqchSurveyResultAskParam) {
        QqchSurveyResultAskVo vo = qqchSurveyResultAskService.getQqchSurveyResultAskList(qqchSurveyResultAskParam);
        return AjaxResult.success(vo);
    }


    /**
     *  批量新增
     *
     * @param qqchSurveyResultAskVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyResultAsk:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计成果策划", name = "2.3.2勘测成果验收计划-勘测成果验收内容审查要求" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchSurveyResultAskList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyResultAskVo qqchSurveyResultAskVo) {
        qqchSurveyResultAskService.save(qqchSurveyResultAskVo);
        return AjaxResult.success(qqchSurveyResultAskVo);
    }

    /**
     *  确认
     *
     * @param qqchSurveyResultAskVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyResultAsk:confirm")
    @PostMapping("/confirm")
    @CustomLogger(title = "前期策划-前期策划编制-勘察设计策划-勘察设计成果策划", name = "2.3.2勘测成果验收计划-勘测成果验收内容审查要求" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyResultAskVo qqchSurveyResultAskVo) {
        qqchSurveyResultAskService.confirm(qqchSurveyResultAskVo);
        return AjaxResult.success(qqchSurveyResultAskVo);
    }


}
