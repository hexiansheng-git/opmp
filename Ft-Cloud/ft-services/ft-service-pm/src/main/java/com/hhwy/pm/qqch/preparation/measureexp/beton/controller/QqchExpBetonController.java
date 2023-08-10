package com.hhwy.pm.qqch.preparation.measureexp.beton.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.QqchExpBeton;
import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.vo.QqchExpBetonImportVo;
import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.vo.QqchExpBetonVo;
import com.hhwy.pm.qqch.preparation.measureexp.beton.service.IQqchExpBetonService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
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
 * @date 2023-08-04 16:12:49
 * @remark 3.7.5混凝土配合比
 */
@Validated
@RestController
@RequestMapping("/qqchExpBeton")
public class QqchExpBetonController extends BaseController {

    @Autowired
    private IQqchExpBetonService qqchExpBetonService;

    /**
     * 树列表
     *
     * @param version
     * @return
     */
    @PreAuthorize(hasPermi = "qqchExpBeton:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(BigDecimal version) {
        QqchExpBetonVo qqchExpBetonVo = qqchExpBetonService.getTreeList(version);
        return AjaxResult.success(qqchExpBetonVo);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchExpBetonVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchExpBeton:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(@Validated(ValidationGroups.Save.class) @RequestBody QqchExpBetonVo qqchExpBetonVo) {
        qqchExpBetonService.batchSave(qqchExpBetonVo);
        return AjaxResult.success();
    }

    /**
     * 树列表导出
     *
     * @param response
     * @throws IOException
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, BigDecimal version)
        throws IOException {
        List<QqchExpBeton> list = qqchExpBetonService.getList(version);
        FtExcelUtil<QqchExpBeton> util = new FtExcelUtil<>(QqchExpBeton.class);
        util.exportExcel(response, list, DateUtils.getDate());
    }

    /**
     * 树列表导出导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        FtExcelUtil<QqchExpBetonImportVo> util = new FtExcelUtil<>(QqchExpBetonImportVo.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<QqchExpBetonImportVo> list = util.importTreeExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
