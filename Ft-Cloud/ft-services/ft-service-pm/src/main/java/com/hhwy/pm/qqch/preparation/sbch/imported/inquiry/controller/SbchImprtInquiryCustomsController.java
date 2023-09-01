package com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.controller;/**
 * @description TODO
 * @date 2022-12-05 17:04
 * @author zq
 */

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;

import com.hhwy.pm.qqch.preparation.sbch.imported.inquiry.domain.SbchImportInquiryCustoms;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zq
 * @date 2022年12月05日 17:04
 */
@Controller
@RequestMapping("/inquiry/customs")
public class SbchImprtInquiryCustomsController extends BaseController {
    /**
     * 导入港口详情
     * @author zq
     * @date 2022/11/25 14:06
     * @param file
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    //@CustomLogger(title = "港口详情-导入", businessType = CustomBusinessType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file){
        try{
            ExcelUtils<SbchImportInquiryCustoms> util = new ExcelUtils(SbchImportInquiryCustoms.class);
            List<SbchImportInquiryCustoms> list = util.importExcel(file.getInputStream());
            return AjaxResult.success(list);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
