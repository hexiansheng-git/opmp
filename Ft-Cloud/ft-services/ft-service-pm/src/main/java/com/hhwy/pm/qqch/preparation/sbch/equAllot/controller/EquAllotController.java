package com.hhwy.pm.qqch.preparation.sbch.equAllot.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.ActiveEquVo;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.EquAllotVo;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.service.EquAllotService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author zqq
 * @create 2023-08-25 16:09
 *
 * 7.2.2
 */
@RestController
@RequestMapping("/equAllot")
public class EquAllotController extends BaseController {
    @Autowired
    private EquAllotService equAllotService;

    @GetMapping("/getList")
    public AjaxResult list(BigDecimal version){
        EquAllotVo equAllotVo = equAllotService.getList(version);
        return AjaxResult.success(equAllotVo);
    }

    @PostMapping("/batchAdd")
    public AjaxResult batchAdd(@Validated(ValidationGroups.Save.class) @RequestBody EquAllotVo equAllotVo){
        try {
            equAllotService.batchAdd(equAllotVo);
            return AjaxResult.success();
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /***
     * 功能描述: 选择调拨设备  调用物设接口
     * 作者: fushudong
     * 时间: 2023/10/23
     */
    @PostMapping("/xzxcsb")
    public AjaxResult xzxcsb(@RequestBody ActiveEquVo activeEquVo){
        EquAllotVo equAllotVo = equAllotService.xzxcsb(activeEquVo);
        return AjaxResult.success(equAllotVo);
    }

}
