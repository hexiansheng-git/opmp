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

/**
 * @author ldd
 * @date 2023-07-31 15:15:56
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchManagementPersonConfig")
public class QqchManagementPersonConfigController extends BaseController{

    @Autowired
    private IQqchManagementPersonConfigService qqchManagementPersonConfigService;

    /**
     *  同步项目组织数据
     */
    @PostMapping("/synchData")
    public AjaxResult getInitData(@RequestBody QqchManagementPersonConfigVo vo){
        QqchManagementPersonConfigVo qqchManagementPersonConfigVo=qqchManagementPersonConfigService.synchData(vo);
        return AjaxResult.success(qqchManagementPersonConfigVo);
    }


    /**
     *  列表接口
     * @param qqchManagementPersonConfigParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:list")
    @GetMapping("/list")
    public AjaxResult getQqchManagementPersonConfigList(@Validated(ValidationGroups.Select.class) QqchManagementPersonConfig qqchManagementPersonConfigParam){
        QqchManagementPersonConfigVo qqchManagementPersonConfigVo = qqchManagementPersonConfigService.getQqchManagementPersonConfigList(qqchManagementPersonConfigParam);
        return AjaxResult.success(qqchManagementPersonConfigVo);
    }

    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:add")
    @PostMapping("/add")
    public AjaxResult insertQqchManagementPersonConfig(@Validated(ValidationGroups.Save.class) @RequestBody QqchManagementPersonConfig qqchManagementPersonConfigParam){
        qqchManagementPersonConfigService.insertQqchManagementPersonConfig(qqchManagementPersonConfigParam);
        return AjaxResult.success(qqchManagementPersonConfigParam);
    }

    /**
     *  保存/确认/提交
     * @param qqchManagementPersonConfigVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:add")
    @PostMapping("/save")
    public AjaxResult insertQqchManagementPersonConfigList(@Validated(ValidationGroups.Save.class) @RequestBody QqchManagementPersonConfigVo qqchManagementPersonConfigVo){
        qqchManagementPersonConfigService.save(qqchManagementPersonConfigVo);
        return AjaxResult.success(qqchManagementPersonConfigVo);
    }

    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:update")
    @PostMapping("/update")
    public AjaxResult updateQqchManagementPersonConfig(@Validated(ValidationGroups.Update.class) @RequestBody QqchManagementPersonConfig qqchManagementPersonConfigParam){
        return toAjax(qqchManagementPersonConfigService.updateQqchManagementPersonConfig(qqchManagementPersonConfigParam));
    }

            @PreAuthorize(hasPermi = "qqchManagementPersonConfig:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchManagementPersonConfigList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchManagementPersonConfig> qqchManagementPersonConfigListParam){
            return toAjax(qqchManagementPersonConfigService.updateQqchManagementPersonConfigList(qqchManagementPersonConfigListParam));
        }

    @PreAuthorize(hasPermi = "qqchManagementPersonConfig:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchManagementPersonConfig(@Validated(ValidationGroups.Delete.class) @RequestBody QqchManagementPersonConfig qqchManagementPersonConfigParam){
        return toAjax(qqchManagementPersonConfigService.deleteQqchManagementPersonConfig(qqchManagementPersonConfigParam));
    }

            @PreAuthorize(hasPermi = "qqchManagementPersonConfig:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchManagementPersonConfigByPks(@PathVariable Long[] ids){
            List<Long> qqchManagementPersonConfigPkList = Arrays.asList(ids);
            return toAjax(qqchManagementPersonConfigService.deleteQqchManagementPersonConfigByPks(qqchManagementPersonConfigPkList));
        }


}
