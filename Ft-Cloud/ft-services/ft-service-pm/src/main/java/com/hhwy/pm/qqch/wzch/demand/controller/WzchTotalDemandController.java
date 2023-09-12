package com.hhwy.pm.qqch.wzch.demand.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.log.annotation.Log;
import com.hhwy.common.log.enums.BusinessType;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemand;
import com.hhwy.pm.qqch.wzch.demand.domain.WzchTotalDemandDetail;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandDetailService;
import com.hhwy.pm.qqch.wzch.demand.service.IWzchTotalDemandService;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandAddVO;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandDetailExportVO;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandDetailVO;
import com.hhwy.pm.qqch.wzch.demand.vo.WzchTotalDemandExportRequest;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 物资总需Controller
 * 
 * @author mls
 * @date 2022-11-15
 */
@RestController
@RequestMapping("/wzch/demand")
public class WzchTotalDemandController extends BaseController {

    @Autowired
    private IWzchTotalDemandService wzchTotalDemandService;
    @Autowired
    private IWzchTotalDemandDetailService wzchTotalDemandDetailService;

    /**
     * 新增保存物资总需
     */
    @PreAuthorize(hasPermi = "wzch:demand:add")
    @GetMapping("/add")
    public AjaxResult addSave() {
        WzchTotalDemandAddVO wzchTotalDemandAddVO = wzchTotalDemandService.add();
        return new AjaxResult(200,"成功",wzchTotalDemandAddVO);
    }

    /**
     * 修改物资总需
     */
//    @PreAuthorize(hasPermi ="wzch:demand:edit")
//    @GetMapping("/edit")
////    @CustomLogger(title = "物资总需-编辑",businessType = CustomBusinessType.UPDATE)
//    public AjaxResult edit(Long id) {
//       return AjaxResult.success(wzchTotalDemandService.detail(new WzchTotalDemand(id)));
//    }

    /**
     * 调整物资总需
     */
    @PreAuthorize(hasPermi ="wzch:demand:adjust")
    @GetMapping("/modify")
//    @CustomLogger(title = "物资总需-调整",businessType = CustomBusinessType.UPDATE)
    public AjaxResult modify(Long id) {
        return wzchTotalDemandService.modify(new WzchTotalDemand(id));
    }

    /**
     * 详情
     */
    @GetMapping("/detail")
//    @CustomLogger(title = "物资总需-详情",businessType = CustomBusinessType.SELECT)
    public AjaxResult detail(WzchTotalDemandDetailVO vo) {
        return AjaxResult.success(wzchTotalDemandService.detail(vo));
    }
}
