package com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.QqchSurveyDesignTeams;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.domain.vo.QqchSurveyDesignTeamsVo;
import com.hhwy.pm.qqch.preparation.survey.qqchSurveyDesignTeams.service.IQqchSurveyDesignTeamsService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ldd
 * @date 2023-07-25 11:08:53
 * @remark  2.1.3 勘察设计队伍配置
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyDesignTeams")
public class QqchSurveyDesignTeamsController extends BaseController {

    @Autowired
    private IQqchSurveyDesignTeamsService qqchSurveyDesignTeamsService;


    /**
     * 列表
     * @param qqchSurveyDesignTeamsParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyDesignTeams:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyDesignTeamsList(@Validated(ValidationGroups.Select.class) QqchSurveyDesignTeams qqchSurveyDesignTeamsParam) {
        QqchSurveyDesignTeamsVo vo = qqchSurveyDesignTeamsService.getQqchSurveyDesignTeamsList(qqchSurveyDesignTeamsParam);
        return AjaxResult.success(vo);
    }


    /**
     *  新增
     *
     * @param qqchSurveyDesignTeamsVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyDesignTeams:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSurveyDesignTeamsList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyDesignTeamsVo qqchSurveyDesignTeamsVo) {
        qqchSurveyDesignTeamsService.save(qqchSurveyDesignTeamsVo);
        return AjaxResult.success(qqchSurveyDesignTeamsVo);
    }


    /**
     * 确认
     * @param qqchSurveyDesignTeamsVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchSurveyDesignTeams:confirm")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyDesignTeamsVo qqchSurveyDesignTeamsVo) {
        qqchSurveyDesignTeamsService.confirm(qqchSurveyDesignTeamsVo);
        return AjaxResult.success(qqchSurveyDesignTeamsVo);
    }


}
