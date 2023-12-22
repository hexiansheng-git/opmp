package com.hhwy.pm.qqch.preparation.doc.techmae.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMae;
import com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMaeV0;
import com.hhwy.pm.qqch.preparation.doc.techmae.service.IQqchDocTechMaeService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mls
 * @date 2023-07-25 18:25:47
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchDocTechMae")
public class QqchDocTechMaeController extends BaseController {

    @Autowired
    private IQqchDocTechMaeService qqchDocTechMaeService;

//    @PreAuthorize(hasPermi = "qqchDocTechMae:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-3.8施工技术文件资料", name = "技术材料清单" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchDocTechMaeList(BigDecimal version) {
        QqchDocTechMaeV0 qqchDocTechVo = qqchDocTechMaeService.geteQqchDocTechMaeVo(version);
        return AjaxResult.success(qqchDocTechVo);
    }

    /**
     * 编辑、提交、确认接口
     *
     * @param
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchDocTech:add")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-3.8施工技术文件资料", name = "技术材料清单" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchDocTech(@Validated(ValidationGroups.Update.class) @RequestBody QqchDocTechMaeV0 qqchDocTechParam) {
        try {
            return AjaxResult.success(qqchDocTechMaeService.inserteQqchDocTechMaeVo(qqchDocTechParam));
        } catch (CustomBusinessException e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }


    /**
     * 编辑、提交、确认接口
     *
     * @param
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchDocTech:add")
    @PostMapping("/importData")
    @CustomLogger(title = "前期策划-前期策划编制-3.8施工技术文件资料", name = "技术材料清单" ,businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file) {

        FtExcelUtil<QqchDocTechMae> excelUtil = new FtExcelUtil<>(QqchDocTechMae.class);
        try {
            List<QqchDocTechMae> qqchDocTechMaes = excelUtil.importTreeExcel(file.getInputStream());
            return AjaxResult.success(qqchDocTechMaes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 3.11.2弹窗
     *
     * @param dataClassify
     * @return
     */
    @GetMapping("/popUpWindows")
    public AjaxResult popUpWindows(String dataClassify) {
        return AjaxResult.success(qqchDocTechMaeService.popUpWindows(dataClassify));
    }

}
