package com.hhwy.pm.qqch.preparation.sbch.equAllot.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.ActiveEquVo;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.EquAllotVo;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.XcsbMonthSelfEquInfo;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.service.EquAllotService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备配置与选型", name = "7.2.2设备调拨策划", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(BigDecimal version){
        EquAllotVo equAllotVo = equAllotService.getList(version);
        return AjaxResult.success(equAllotVo);
    }

    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备配置与选型", name = "7.2.2设备调拨策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult batchAdd(@RequestBody EquAllotVo equAllotVo){
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
     * 功能描述: 选择调拨设备  调用物设接口（现场设备）
     * 作者: fushudong
     * 时间: 2023/10/23
     */
    @PostMapping("/xzxcsb")
    @CustomLogger(title = "前期策划-前期策划编制-设备策划-设备配置与选型", name = "7.2.2设备调拨策划", businessType = CustomBusinessType.SELECT)
    public AjaxResult xzxcsb(@RequestBody ActiveEquVo activeEquVo){
        return equAllotService.xzxcsb(activeEquVo);
    }

}
