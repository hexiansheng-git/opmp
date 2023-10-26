package com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.domain.QqchTotalDemand;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.domain.vo.QqchTotalDemandVo;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.service.IQqchTotalDemandService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-02 10:55:15
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchTotalDemand")
public class QqchTotalDemandController extends BaseController{

    @Autowired
    private IQqchTotalDemandService qqchTotalDemandService;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                            

//    @PreAuthorize(hasPermi = "qqchTotalDemand:list")
    @GetMapping
    public AjaxResult getQqchTotalDemand(@Validated(ValidationGroups.Get.class)  QqchTotalDemand qqchTotalDemandParam){
        QqchTotalDemand qqchTotalDemand =  qqchTotalDemandService.getQqchTotalDemand(qqchTotalDemandParam);
        return AjaxResult.success(qqchTotalDemand);
    }

    /**
     *  列表接口
     * @param qqchTotalDemandParam
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchTotalDemand:list")
    @GetMapping("/list")
    public AjaxResult getQqchTotalDemandList(@Validated(ValidationGroups.Select.class) QqchTotalDemand qqchTotalDemandParam){
        QqchTotalDemandVo vo = qqchTotalDemandService.getQqchTotalDemandList(qqchTotalDemandParam);
        return AjaxResult.success(vo);
    }


    /**
     *  新增
     * @param vo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchTotalDemand:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchTotalDemandVo vo){
        qqchTotalDemandService.save(vo);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchTotalDemand:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTotalDemand(@Validated(ValidationGroups.Save.class) @RequestBody QqchTotalDemand qqchTotalDemandParam){
        qqchTotalDemandService.insertQqchTotalDemand(qqchTotalDemandParam);
        return AjaxResult.success(qqchTotalDemandParam);
    }


    @PreAuthorize(hasPermi = "qqchTotalDemand:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTotalDemand(@Validated(ValidationGroups.Update.class) @RequestBody QqchTotalDemand qqchTotalDemandParam){
        return toAjax(qqchTotalDemandService.updateQqchTotalDemand(qqchTotalDemandParam));
    }

            @PreAuthorize(hasPermi = "qqchTotalDemand:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchTotalDemandList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTotalDemand> qqchTotalDemandListParam){
            return toAjax(qqchTotalDemandService.updateQqchTotalDemandList(qqchTotalDemandListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchTotalDemand:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTotalDemand(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTotalDemand qqchTotalDemandParam){
        return toAjax(qqchTotalDemandService.deleteQqchTotalDemand(qqchTotalDemandParam));
    }

            @PreAuthorize(hasPermi = "qqchTotalDemand:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchTotalDemandByPks(@PathVariable Long[] ids){
            List<Long> qqchTotalDemandPkList = Arrays.asList(ids);
            return toAjax(qqchTotalDemandService.deleteQqchTotalDemandByPks(qqchTotalDemandPkList));
        }
    

}
