package com.hhwy.pm.qqch.preparation.finance.policy.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalAccountingPolicy;
import com.hhwy.pm.qqch.preparation.finance.policy.domain.vo.QqchLocalAccountingPolicyVo;
import com.hhwy.pm.qqch.preparation.finance.policy.service.IQqchLocalAccountingPolicyService;
import com.hhwy.utils.excel.ExportUtil;
import com.hhwy.utils.excel.FtExcelUtil;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:44
 * @remark 10.2.3当地会计政策描述
 */
@Validated
@RestController
@RequestMapping("/qqchLocalAccountingPolicy")
public class QqchLocalAccountingPolicyController extends BaseController {

    @Autowired
    private IQqchLocalAccountingPolicyService qqchLocalAccountingPolicyService;

    /**
     * 列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchLocalAccountingPolicy:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchLocalAccountingPolicyVo qqchLocalAccountingPolicyVo = qqchLocalAccountingPolicyService
            .getQqchLocalAccountingPolicyList(version);
        return AjaxResult.success(qqchLocalAccountingPolicyVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchLocalAccountingPolicyVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchLocalAccountingPolicy:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(@RequestBody QqchLocalAccountingPolicyVo qqchLocalAccountingPolicyVo) {
        qqchLocalAccountingPolicyService.batchSave(qqchLocalAccountingPolicyVo);
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
    public void export(HttpServletResponse response, BigDecimal version)
        throws IOException {
        QqchLocalAccountingPolicyVo qqchLocalAccountingPolicyVo = qqchLocalAccountingPolicyService
            .getQqchLocalAccountingPolicyList(version);
        FtExcelUtil<QqchLocalAccountingPolicy> util = new FtExcelUtil<>(QqchLocalAccountingPolicy.class);
        // 导出维护序号
        List<QqchLocalAccountingPolicy> newList = ExportUtil
            .preserveSerialNumber(qqchLocalAccountingPolicyVo.getList(), QqchLocalAccountingPolicy::setSerialNum);
        util.exportExcel(response, newList, DateUtils.getDate());
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        FtExcelUtil<QqchLocalAccountingPolicy> util = new FtExcelUtil<>(QqchLocalAccountingPolicy.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<QqchLocalAccountingPolicy> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
