package com.hhwy.pm.qqch.preparation.qqchOrganizationList.controller;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationList;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.domain.QqchOrganizationListVo;
import com.hhwy.pm.qqch.preparation.qqchOrganizationList.service.IQqchOrganizationListService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hwj
 * @date 2023-07-24 17:02:12
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchOrganizationList")
public class QqchOrganizationListController extends BaseController {

    @Autowired
    private IQqchOrganizationListService qqchOrganizationListService;

    /**
     * 项目组织设置台账
     *
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchOrganizationList:list")
    @GetMapping("/list")
    public AjaxResult getQqchChangeProcedurePlanVo(BigDecimal version) {
        QqchOrganizationListVo organizationListVo = qqchOrganizationListService.getQqchOrganizationListVo(version);
        return AjaxResult.success(organizationListVo);
    }

    /**
     * 编辑、提交、确认接口
     *
     * @param qqchOrganizationListVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchOrganizationList:add")
    @PostMapping("/save")
    public AjaxResult insertQqchWorkPlanningPrjImg(@Validated(ValidationGroups.Update.class) @RequestBody QqchOrganizationListVo qqchOrganizationListVo) {
        try {
            return AjaxResult.success(qqchOrganizationListService.insertQqchOrganizationListVo(qqchOrganizationListVo));
        } catch (CustomBusinessException e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }


    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file, @RequestParam("list") String list) {
        FtExcelUtil<QqchOrganizationList> excelUtil = new FtExcelUtil<>(QqchOrganizationList.class);
        List<QqchOrganizationList> qqchImportants = null;

        try {
            qqchImportants = excelUtil.importTreeExcel(file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        if (!CollectionUtils.isEmpty(qqchImportants)) {
            QqchOrganizationList qqchOrganizationList = qqchImportants.get(0);
            String organization = qqchOrganizationList.getOrganization();
            if (StringUtils.isEmpty(organization)) {
                throw new RuntimeException("没有检测到数据,请按照正确模板导入数据");
            }
        } else {
            throw new RuntimeException("没有检测到数据,请按照正确模板导入数据");
        }


        List<QqchOrganizationList> qqchOrganizationLists = JSONObject.parseArray(list, QqchOrganizationList.class);
        List<QqchOrganizationList> res = qqchOrganizationListService.mergeData(qqchImportants, qqchOrganizationLists);
        return AjaxResult.success(res);
    }
}
