package com.hhwy.pm.qqch.preparation.qqchOrganizationList.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationListVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchChangeProcedurePlanVo;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningPrjImg;
import com.hhwy.pm.qqch.sgch.important.domain.QqchImportant;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.service.IQqchOrganizationListService;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationList;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hwj
 * @date 2023-07-24 17:02:12
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchOrganizationList")
public class QqchOrganizationListController extends BaseController{

    @Autowired
    private IQqchOrganizationListService qqchOrganizationListService;

    /**
     * 项目组织设置台账
     *
     * @return
     */
    @PreAuthorize(hasPermi = "qqchOrganizationList:list")
    @GetMapping("/list")
    public AjaxResult getQqchChangeProcedurePlanVo(BigDecimal version) {
        QqchOrganizationListVo organizationListVo = qqchOrganizationListService.getQqchOrganizationListVo(version);
        return AjaxResult.success(organizationListVo);
    }

    /**编辑、提交、确认接口
     * @param qqchOrganizationListVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchOrganizationList:add")
    @PostMapping("/save")
    public AjaxResult insertQqchWorkPlanningPrjImg(@Validated(ValidationGroups.Update.class) @RequestBody QqchOrganizationListVo qqchOrganizationListVo){
        try{
            return AjaxResult.success(qqchOrganizationListService.insertQqchOrganizationListVo(qqchOrganizationListVo));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }


    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) {
        FtExcelUtil<QqchOrganizationList> excelUtil = new FtExcelUtil<>(QqchOrganizationList.class);
        try {
            List<QqchOrganizationList> qqchImportants = excelUtil.importTreeExcel(file.getInputStream());
            return AjaxResult.success(qqchImportants);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
