package com.hhwy.pm.xmsl.drawReview.controller;

import java.util.HashMap;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewService;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * 图纸复核
 * @author wk
 * @date 2023-08-07 11:23:32
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/xmslDrawReview")
public class XmslDrawReviewController extends BaseController{
    @Autowired
    private IXmslDrawReviewService xmslDrawReviewService;


    @PreAuthorize(hasPermi = "xmslDrawReview:list")
    @GetMapping("/list")
    public AjaxResult getXmslDrawReviewList(@Validated(ValidationGroups.Select.class) XmslDrawReview xmslDrawReviewParam){
        startPage();
        List<XmslDrawReview> xmslDrawReviewList = xmslDrawReviewService.getXmslDrawReviewList(xmslDrawReviewParam);
        return getDataTableAjaxResult(xmslDrawReviewList);
    }

    @PreAuthorize(hasAnyPermi = {"xmslDrawReview:list"})
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody XmslDrawReview drawReview) {
        if(drawReview.getId() == null){
            drawReview =xmslDrawReviewService.getLast();
        }else{
            drawReview = xmslDrawReviewService.getById(drawReview.getId());
        }
        //是否有调整记录
//        Long count = xmslWbsMainService.getXmslWbsMainCount(new XmslWbsMain());
//        if(drawReview != null)
//            drawReview.setParams(ObjectUtils.toMap(Constant.HISTORY_NOTE_FIELD_NAME,count>1?1:0));
        return AjaxResult.success(drawReview==null?new HashMap<>(2):drawReview);
    }

//    @GetMapping("/export")
//    public void export(HttpServletResponse response, XmslDrawReview xmslDrawReviewParam) throws IOException {
//        List<XmslDrawReview> xmslDrawReviewList = xmslDrawReviewService.getXmslDrawReviewList(xmslDrawReviewParam);
//        ExcelUtils<XmslDrawReview> util = new ExcelUtils<>(XmslDrawReview.class);
//        util.exportExcel(response, xmslDrawReviewList, DateUtils.getDate());
//    }
}
