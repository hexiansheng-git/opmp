package com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.domain.QqchMeasuringInstrument;
import com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.domain.vo.QqchMeasuringInstrumentVo;
import com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.service.IQqchMeasuringInstrumentService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-02 10:50:39
 * @remark   1.7.3 实验测量仪器
 */
@Validated
@RestController
@RequestMapping("/qqchMeasuringInstrument")
public class QqchMeasuringInstrumentController extends BaseController{

    @Autowired
    private IQqchMeasuringInstrumentService qqchMeasuringInstrumentService;


    @PreAuthorize(hasPermi = "qqchMeasuringInstrument:list")
    @GetMapping
    public AjaxResult getQqchMeasuringInstrument(@Validated(ValidationGroups.Get.class)  QqchMeasuringInstrument qqchMeasuringInstrumentParam){
        QqchMeasuringInstrument qqchMeasuringInstrument =  qqchMeasuringInstrumentService.getQqchMeasuringInstrument(qqchMeasuringInstrumentParam);
        return AjaxResult.success(qqchMeasuringInstrument);
    }


    /**
     * 拉取 施工部署数据(设备策划)
     *
     */
    @PostMapping("/syncData")
    public AjaxResult syncData(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasuringInstrumentVo param){
        QqchMeasuringInstrumentVo vo = qqchMeasuringInstrumentService.syncData(param);
        return AjaxResult.success(vo);
    }


    /**
     * 列表接口
     *
     * @param qqchMeasuringInstrumentParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasuringInstrument:list")
    @GetMapping("/list")
    public AjaxResult getQqchMeasuringInstrumentList(@Validated(ValidationGroups.Select.class) QqchMeasuringInstrument qqchMeasuringInstrumentParam){
        QqchMeasuringInstrumentVo vo = qqchMeasuringInstrumentService.getQqchMeasuringInstrumentList(qqchMeasuringInstrumentParam);
        return AjaxResult.success(vo);
    }

    /**
     *   保存/确认
     * @param vo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchMeasuringInstrument:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasuringInstrumentVo vo){
        qqchMeasuringInstrumentService.save(vo);
        return AjaxResult.success();
    }


    @PreAuthorize(hasPermi = "qqchMeasuringInstrument:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMeasuringInstrument(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasuringInstrument qqchMeasuringInstrumentParam){
        qqchMeasuringInstrumentService.insertQqchMeasuringInstrument(qqchMeasuringInstrumentParam);
        return AjaxResult.success(qqchMeasuringInstrumentParam);
    }


    @PreAuthorize(hasPermi = "qqchMeasuringInstrument:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMeasuringInstrument(@Validated(ValidationGroups.Update.class) @RequestBody QqchMeasuringInstrument qqchMeasuringInstrumentParam){
        return toAjax(qqchMeasuringInstrumentService.updateQqchMeasuringInstrument(qqchMeasuringInstrumentParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasuringInstrument:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchMeasuringInstrumentList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMeasuringInstrument> qqchMeasuringInstrumentListParam){
            return toAjax(qqchMeasuringInstrumentService.updateQqchMeasuringInstrumentList(qqchMeasuringInstrumentListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchMeasuringInstrument:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMeasuringInstrument(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMeasuringInstrument qqchMeasuringInstrumentParam){
        return toAjax(qqchMeasuringInstrumentService.deleteQqchMeasuringInstrument(qqchMeasuringInstrumentParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasuringInstrument:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchMeasuringInstrumentByPks(@PathVariable Long[] ids){
            List<Long> qqchMeasuringInstrumentPkList = Arrays.asList(ids);
            return toAjax(qqchMeasuringInstrumentService.deleteQqchMeasuringInstrumentByPks(qqchMeasuringInstrumentPkList));
        }

}
