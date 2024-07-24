package com.hhwy.system.nacosconfig;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.period.vo.PeriodCurrencyInfoVo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.system.period.service.IPeriodInfoService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.core.DateUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;


/**
 * infoController
 * 
 * @author hwj
 * @date 2022-11-24
 */
@RestController
@RequestMapping("/nacos")
public class NacosInfoController extends BaseController {

    @Autowired
    private IPeriodInfoService periodInfoService;


    /**
     * 查询info列表
     */
    @PostMapping("/nacosConfig")
    public AjaxResult nacosConfig(@RequestBody NacosInfo nacosInfo) {
        Boolean aBoolean = NacosConfig.updateConfig(nacosInfo.getDataId(), nacosInfo.getNamespace(), nacosInfo.getMapSon());
        return AjaxResult.success(aBoolean);
    }
}
