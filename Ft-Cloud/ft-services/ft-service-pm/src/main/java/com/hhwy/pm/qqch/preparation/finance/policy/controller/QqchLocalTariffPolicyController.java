package com.hhwy.pm.qqch.preparation.finance.policy.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalTariffPolicy;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchLocalTariffPolicyVo;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchLocalTariffPolicyService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:49
 * @remark 10.2.4当地关税政策描述
 */
@Validated
@RestController
@RequestMapping("/qqchLocalTariffPolicy")
public class QqchLocalTariffPolicyController extends BaseController {

    @Autowired
    private IQqchLocalTariffPolicyService qqchLocalTariffPolicyService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchLocalTariffPolicy:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchLocalTariffPolicyVo qqchLocalTariffPolicyVo = qqchLocalTariffPolicyService
            .getQqchLocalTariffPolicyList(version);
        return AjaxResult.success(qqchLocalTariffPolicyVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchLocalTariffPolicyVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchLocalTariffPolicy:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.2税务、会计、金融政策", name = "\n" +
            "10.2.4 当地关税政策描述" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchLocalTariffPolicyVo qqchLocalTariffPolicyVo) {
        qqchLocalTariffPolicyService.batchSave(qqchLocalTariffPolicyVo);
        return AjaxResult.success();
    }

    /**
     * 导出
     *
     * @param response
     * @param version
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, @RequestParam(value = "version") BigDecimal version)
        throws IOException {
        QqchLocalTariffPolicyVo qqchLocalTariffPolicyVo = qqchLocalTariffPolicyService
            .getQqchLocalTariffPolicyList(version);
        FtExcelUtil<QqchLocalTariffPolicy> util = new FtExcelUtil<>(QqchLocalTariffPolicy.class);
        util.exportExcel(response, qqchLocalTariffPolicyVo.getList(), DateUtils.getDate());
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.2税务、会计、金融政策", name = "\n" +
            "10.2.4 当地关税政策描述" ,businessType = CustomBusinessType.IMPORT)
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        FtExcelUtil<QqchLocalTariffPolicy> util = new FtExcelUtil<>(QqchLocalTariffPolicy.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<QqchLocalTariffPolicy> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
