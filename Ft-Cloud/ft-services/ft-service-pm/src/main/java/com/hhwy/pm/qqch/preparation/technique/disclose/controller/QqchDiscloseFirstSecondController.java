package com.hhwy.pm.qqch.preparation.technique.disclose.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.QqchDiscloseFirstSecondVo;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.RelateProjectVo;
import com.hhwy.pm.qqch.preparation.technique.disclose.service.IQqchDiscloseFirstSecondService;
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
 * @date 2023-07-20 15:07:49
 * @remark 3.5.1一、二级交底
 */
@Validated
@RestController
@RequestMapping("/qqchDiscloseFirstSecond")
public class QqchDiscloseFirstSecondController extends BaseController {

    @Autowired
    private IQqchDiscloseFirstSecondService qqchDiscloseFirstSecondService;

//    @PreAuthorize(hasPermi = "qqchDiscloseFirstSecond:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(BigDecimal version) {
        QqchDiscloseFirstSecondVo qqchDiscloseFirstSecondVo = qqchDiscloseFirstSecondService
            .getQqchDiscloseFirstSecondList(version);
        return AjaxResult.success(qqchDiscloseFirstSecondVo);
    }

//    @PreAuthorize(hasPermi = "qqchDiscloseFirstSecond:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchDiscloseFirstSecondVo qqchDiscloseFirstSecondVo) {
        qqchDiscloseFirstSecondService.batchSave(qqchDiscloseFirstSecondVo);
        return AjaxResult.success();
    }

    /**
     * 根据关联wbs，查询wbs本级以及所有下级关联项目危大工程方案、重难点施工方案简述关联的wbs、以及关联的施工方案。
     *
     * @param id
     * @return
     */
    @GetMapping("/getRelateProjectByWbs")
    public AjaxResult getRelateProjectByWbs(Long id) {
        RelateProjectVo relateProjectVo = qqchDiscloseFirstSecondService.getRelateProjectByWbs(id);
        return AjaxResult.success(relateProjectVo);
    }
}
