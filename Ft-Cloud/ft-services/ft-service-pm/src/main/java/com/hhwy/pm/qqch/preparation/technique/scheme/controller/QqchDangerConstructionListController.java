package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchDangerConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchDangerConstructionListVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchDangerConstructionListService;
import com.hhwy.utils.excel.ExportUtil;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-17 14:26:41
 * @remark 3.4.3危大工程方案清单
 */
@Validated
@RestController
@RequestMapping("/qqchDangerConstructionList")
public class QqchDangerConstructionListController extends BaseController {

    @Autowired
    private IQqchDangerConstructionListService qqchDangerConstructionListService;

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:list")
    @GetMapping("/getList")
    public AjaxResult getList(BigDecimal version) {
        QqchDangerConstructionListVo qqchDangerConstructionListVo = qqchDangerConstructionListService
            .getQqchDangerConstructionListList(version);
        return AjaxResult.success(qqchDangerConstructionListVo);
    }

    @PreAuthorize(hasPermi = "qqchDangerConstructionList:update")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchDangerConstructionListVo qqchDangerConstructionListVo) {
        qqchDangerConstructionListService.batchSave(qqchDangerConstructionListVo);
        return AjaxResult.success();
    }

    /**
     * 从施工方案清单同步数据
     *
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDangerConstructionList:add")
    @PostMapping("/syncData")
    public AjaxResult syncData(
        @Validated(ValidationGroups.Update.class) @RequestBody QqchDangerConstructionListVo qqchDangerConstructionListVo) {
        qqchDangerConstructionListService.syncData(qqchDangerConstructionListVo);
        return AjaxResult.success();
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, BigDecimal version)
        throws IOException {
        QqchDangerConstructionListVo qqchDangerConstructionListVo = qqchDangerConstructionListService
            .getQqchDangerConstructionListList(version);
        FtExcelUtil<QqchDangerConstructionList> util = new FtExcelUtil<>(QqchDangerConstructionList.class);

        // 导出维护序号
        List<QqchDangerConstructionList> newList = ExportUtil
            .preserveSerialNumber(qqchDangerConstructionListVo.getList(), QqchDangerConstructionList::setSerialNum);
        util.exportExcel(response, newList, DateUtils.getDate());
    }
}
