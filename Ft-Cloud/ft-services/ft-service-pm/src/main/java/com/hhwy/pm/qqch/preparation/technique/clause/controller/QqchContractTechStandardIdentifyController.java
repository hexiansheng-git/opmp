package com.hhwy.pm.qqch.preparation.technique.clause.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.vo.QqchContractTechStandardIdentifyVo;
import com.hhwy.pm.qqch.preparation.technique.clause.service.IQqchContractTechStandardIdentifyService;
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
 * @date 2023-07-07 15:51:11
 * @remark 3.1.1合同执行技术标准识别
 */
@Validated
@RestController
@RequestMapping("/qqchContractTechStandardIdentify")
public class QqchContractTechStandardIdentifyController extends BaseController {

    @Autowired
    private IQqchContractTechStandardIdentifyService qqchContractTechStandardIdentifyService;

    /**
     * 查询列表
     *
     * @param
     * @return
     */
    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(BigDecimal version) {
        QqchContractTechStandardIdentifyVo qqchContractTechStandardIdentifyVo = qqchContractTechStandardIdentifyService
            .getTreeList(version);
        return AjaxResult.success(qqchContractTechStandardIdentifyVo);
    }

    /**
     * 批量保存
     *
     * @param qqchContractTechStandardIdentifyVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchContractTechStandardIdentifyVo qqchContractTechStandardIdentifyVo) {
        qqchContractTechStandardIdentifyService.batchSave(qqchContractTechStandardIdentifyVo);
        return AjaxResult.success();
    }

}
