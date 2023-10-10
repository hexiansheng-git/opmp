package com.hhwy.pm.qqch.preparation.technique.techManagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchTopicResearchPlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchTopicResearchPlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchTopicResearchPlanImportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchTopicResearchPlanVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchTopicResearchPlanService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:25
 * @remark 课题研究计划
 */
@Validated
@RestController
@RequestMapping("/qqchTopicResearchPlan")
public class QqchTopicResearchPlanController extends BaseController {

    @Autowired
    private IQqchTopicResearchPlanService qqchTopicResearchPlanService;
    @Autowired
    private IXmslProjectBasicInfoService xmslProjectBasicInfoService;


//    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:list")
    @GetMapping
    public AjaxResult getQqchTopicResearchPlan(@Validated(ValidationGroups.Get.class) QqchTopicResearchPlan qqchTopicResearchPlanParam) {
        QqchTopicResearchPlan qqchTopicResearchPlan = qqchTopicResearchPlanService.getQqchTopicResearchPlan(qqchTopicResearchPlanParam);
        return AjaxResult.success(qqchTopicResearchPlan);
    }

//    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchTopicResearchPlanList(@Validated(ValidationGroups.Select.class) QqchTopicResearchPlan qqchTopicResearchPlanParam) {
        startPage();
        List<QqchTopicResearchPlan> qqchTopicResearchPlanList = qqchTopicResearchPlanService.getQqchTopicResearchPlanList(qqchTopicResearchPlanParam);
        return getDataTableAjaxResult(qqchTopicResearchPlanList);
    }

//    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTopicResearchPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchTopicResearchPlan qqchTopicResearchPlanParam) {
        qqchTopicResearchPlanService.insertQqchTopicResearchPlan(qqchTopicResearchPlanParam);
        return AjaxResult.success(qqchTopicResearchPlanParam);
    }

//    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTopicResearchPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchTopicResearchPlan qqchTopicResearchPlanParam) {
        return toAjax(qqchTopicResearchPlanService.updateQqchTopicResearchPlan(qqchTopicResearchPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTopicResearchPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTopicResearchPlan> qqchTopicResearchPlanListParam) {
        return toAjax(qqchTopicResearchPlanService.updateQqchTopicResearchPlanList(qqchTopicResearchPlanListParam));
    }

//    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTopicResearchPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTopicResearchPlan qqchTopicResearchPlanParam) {
        return toAjax(qqchTopicResearchPlanService.deleteQqchTopicResearchPlan(qqchTopicResearchPlanParam));
    }

//    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTopicResearchPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchTopicResearchPlanPkList = Arrays.asList(ids);
        return toAjax(qqchTopicResearchPlanService.deleteQqchTopicResearchPlanByPks(qqchTopicResearchPlanPkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("/import")
    public AjaxResult importData(@RequestPart("file") MultipartFile file){
        FtExcelUtil<QqchTopicResearchPlanImportVo> util = new FtExcelUtil<>(QqchTopicResearchPlanImportVo.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<QqchTopicResearchPlanImportVo> qqchTopicResearchPlanImportVoList = util.importExcel(inputStream);
            //获取项目信息
            ProjectBasicInfo projectInfo = xmslProjectBasicInfoService.projectInfo();
            if(projectInfo != null){
                Long regionId = projectInfo.getRegionId();
                String regionName = projectInfo.getRegionName();
                String projectName = projectInfo.getProjectName();
                for (QqchTopicResearchPlanImportVo qqchTopicResearchPlanImportVo : qqchTopicResearchPlanImportVoList) {
                    qqchTopicResearchPlanImportVo.setRegionId(regionId);
                    qqchTopicResearchPlanImportVo.setRegionName(regionName);
                    qqchTopicResearchPlanImportVo.setProjectName(projectName);
                }
            }
            return AjaxResult.success(qqchTopicResearchPlanImportVoList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    /**
     * 导出
     * @param response
     * @param qqchTopicResearchPlan
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTopicResearchPlan qqchTopicResearchPlan) throws IOException {
        List<QqchTopicResearchPlanExportVo> qqchTopicResearchPlanExportVoList = qqchTopicResearchPlanService.getQqchTopicResearchPlanExportVoList(qqchTopicResearchPlan);
        FtExcelUtil<QqchTopicResearchPlanExportVo> util = new FtExcelUtil<>(QqchTopicResearchPlanExportVo.class);
        util.exportExcel(response, qqchTopicResearchPlanExportVoList, DateUtils.getDate());
    }

    /**
     * 获取课题研究计划Vo
     * @param qqchTopicResearchPlan
     * @return
     */
    @GetMapping("/getQqchTopicResearchPlanVo")
    public AjaxResult getQqchTopicResearchPlanVo(@Validated(ValidationGroups.Select.class) QqchTopicResearchPlan qqchTopicResearchPlan){
        QqchTopicResearchPlanVo qqchTopicResearchPlanVo = qqchTopicResearchPlanService.getQqchTopicResearchPlanVo(qqchTopicResearchPlan);
        return AjaxResult.success(qqchTopicResearchPlanVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchTopicResearchPlanVo
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchTopicResearchPlan:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchTopicResearchPlanVo qqchTopicResearchPlanVo) {
        qqchTopicResearchPlanService.save(qqchTopicResearchPlanVo);
        return AjaxResult.success();
    }
}
