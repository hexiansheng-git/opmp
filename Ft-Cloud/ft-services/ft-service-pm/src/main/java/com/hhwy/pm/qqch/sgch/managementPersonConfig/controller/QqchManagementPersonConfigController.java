package com.hhwy.pm.qqch.sgch.managementPersonConfig.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.QqchManagementPersonConfig;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.domain.vo.QqchManagementPersonConfigVo;
import com.hhwy.pm.qqch.sgch.managementPersonConfig.service.IQqchManagementPersonConfigService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ldd
 * @date 2023-07-31 15:15:56
 * @remark 1.5.1
 */
@Validated
@RestController
@RequestMapping("/qqchManagementPersonConfig")
public class QqchManagementPersonConfigController extends BaseController {

    @Autowired
    private IQqchManagementPersonConfigService qqchManagementPersonConfigService;

    /**
     * 列表右上角统计信息
     * 管理人员总数： 154中方管理： 35  外方管理 67  外方比例： 10%
     */
    @GetMapping("/personTypeStatistics")
    public AjaxResult personTypeStatistics(@Validated(ValidationGroups.Select.class) QqchManagementPersonConfig qqchManagementPersonConfigParam) {
        Map<String, Integer> result = qqchManagementPersonConfigService.personNumCalc(qqchManagementPersonConfigParam);
        return AjaxResult.success(result);
    }

    /**
     * 同步项目组织数据
     */
    @PostMapping("/synchData")
    public AjaxResult getInitData(@RequestBody QqchManagementPersonConfigVo vo) {
        QqchManagementPersonConfigVo qqchManagementPersonConfigVo = qqchManagementPersonConfigService.synchData(vo);
        return AjaxResult.success(qqchManagementPersonConfigVo);
    }


    /**
     * 列表接口
     *
     * @param qqchManagementPersonConfigParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:list")
    @GetMapping("/list")
    public AjaxResult getQqchManagementPersonConfigList(@Validated(ValidationGroups.Select.class) QqchManagementPersonConfig qqchManagementPersonConfigParam) {
        QqchManagementPersonConfigVo qqchManagementPersonConfigVo = qqchManagementPersonConfigService.getQqchManagementPersonConfigList(qqchManagementPersonConfigParam);
        return AjaxResult.success(qqchManagementPersonConfigVo);
    }

    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:add")
    @PostMapping("/add")
    public AjaxResult insertQqchManagementPersonConfig(@Validated(ValidationGroups.Save.class) @RequestBody QqchManagementPersonConfig qqchManagementPersonConfigParam) {
        qqchManagementPersonConfigService.insertQqchManagementPersonConfig(qqchManagementPersonConfigParam);
        return AjaxResult.success(qqchManagementPersonConfigParam);
    }

    /**
     * 保存/确认/提交
     *
     * @param qqchManagementPersonConfigVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:add")
    @PostMapping("/save")
    public AjaxResult insertQqchManagementPersonConfigList(@Validated(ValidationGroups.Save.class) @RequestBody QqchManagementPersonConfigVo qqchManagementPersonConfigVo) {
        qqchManagementPersonConfigService.save(qqchManagementPersonConfigVo);
        return AjaxResult.success(qqchManagementPersonConfigVo);
    }

    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:update")
    @PostMapping("/update")
    public AjaxResult updateQqchManagementPersonConfig(@Validated(ValidationGroups.Update.class) @RequestBody QqchManagementPersonConfig qqchManagementPersonConfigParam) {
        return toAjax(qqchManagementPersonConfigService.updateQqchManagementPersonConfig(qqchManagementPersonConfigParam));
    }

    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchManagementPersonConfigList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchManagementPersonConfig> qqchManagementPersonConfigListParam) {
        return toAjax(qqchManagementPersonConfigService.updateQqchManagementPersonConfigList(qqchManagementPersonConfigListParam));
    }

    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchManagementPersonConfig(@Validated(ValidationGroups.Delete.class) @RequestBody QqchManagementPersonConfig qqchManagementPersonConfigParam) {
        return toAjax(qqchManagementPersonConfigService.deleteQqchManagementPersonConfig(qqchManagementPersonConfigParam));
    }

    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchManagementPersonConfigByPks(@PathVariable Long[] ids) {
        List<Long> qqchManagementPersonConfigPkList = Arrays.asList(ids);
        return toAjax(qqchManagementPersonConfigService.deleteQqchManagementPersonConfigByPks(qqchManagementPersonConfigPkList));
    }

    /***
     * 功能描述:
     * @param userName 员工账号
     * 作者: fushudong
     * 时间: 2023/10/25
     */
    @GetMapping("/queryPersonType")
    public AjaxResult queryPersonType(@RequestParam(value = "userName", required = true) String userName){
        String personType = null;
        try {
            personType = qqchManagementPersonConfigService.getPersonType(userName);
        } catch (Exception e) {
            return AjaxResult.error("获取人员类型异常");
        }
        return AjaxResult.success("success",personType);
    }


}
