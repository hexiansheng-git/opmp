package com.hhwy.pm.qqch.preparation.technique.clause.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.vo.QqchContractTechAchievementIdentifyVo;
import com.hhwy.pm.qqch.preparation.technique.clause.service.IQqchContractTechAchievementIdentifyService;
import com.hhwy.utils.validation.ValidationGroups;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-10 14:36:27
 * @remark 3.1.2合同要求提交的技术文件成果识别
 */
@Validated
@RestController
@RequestMapping("/qqchContractTechAchievementIdentify")
public class QqchContractTechAchievementIdentifyController extends BaseController {

    @Autowired
    private IQqchContractTechAchievementIdentifyService qqchContractTechAchievementIdentifyService;

    /**
     * 查询列表
     *
     * @param
     * @return
     */
    @PreAuthorize(hasPermi = "qqchContractTechAchievementIdentify:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(BigDecimal version) {
        QqchContractTechAchievementIdentifyVo qqchContractTechAchievementIdentifyVo = qqchContractTechAchievementIdentifyService
            .getTreeList(version);
        return AjaxResult.success(qqchContractTechAchievementIdentifyVo);
    }

    /**
     * 批量保存
     *
     * @param qqchContractTechAchievementIdentifyVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchContractTechAchievementIdentify:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchContractTechAchievementIdentifyVo qqchContractTechAchievementIdentifyVo) {
        qqchContractTechAchievementIdentifyService.batchSave(qqchContractTechAchievementIdentifyVo);
        return AjaxResult.success();
    }
}
