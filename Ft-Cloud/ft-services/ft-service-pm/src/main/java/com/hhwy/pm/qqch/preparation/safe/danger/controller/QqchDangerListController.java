package com.hhwy.pm.qqch.preparation.safe.danger.controller;

import com.alibaba.nacos.common.http.param.MediaType;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerListVo;
import com.hhwy.pm.qqch.preparation.safe.danger.service.IQqchDangerListService;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProj;
import com.hhwy.pm.qyzs.safe.qyzsSafeRiskBigProj.domain.QyzsSafeRiskBigProjItem;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-07 14:22:57
 * @remark 8.3.1 危大工程清单
 */
@Validated
@RestController
@RequestMapping("/qqchDangerList")
public class QqchDangerListController extends BaseController {

    @Autowired
    private IQqchDangerListService qqchDangerListService;


    //获取总部知识库危大工程清单
    @GetMapping("/getGmRiskBigProjList")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-特种设备管控策划-获取总部特种设备类型", name = "8.3.1 特种设备及大型设备风险识别与措施策划-获取总部知识库危大工程清单" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getRiskBigProjList(QyzsSafeRiskBigProjItem qyzsSafeRiskBigProj) {
        return qqchDangerListService.getGmRiskBigProjList(qyzsSafeRiskBigProj);
    }

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDangerList:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchDangerListVo qqchDangerListVo = qqchDangerListService.getQqchDangerListList(version);
        return AjaxResult.success(qqchDangerListVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchDangerListVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDangerList:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-安全策划-8.3 危大工程管控策划", name = "\n" +
            "8.3.1 危大工程清单" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchDangerListVo qqchDangerListVo) {
        qqchDangerListService.batchSave(qqchDangerListVo);
        return AjaxResult.success();
    }
}
