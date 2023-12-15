package com.hhwy.pm.qqch.preparation.technique.scheme.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionListVo;
import com.hhwy.pm.qqch.preparation.technique.scheme.service.IQqchConstructionListService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.ExportUtil;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * 施工方案清单
 * @author zhenglili
 * @date 2023-07-13 14:27:14
 * @remark 3.4.2施工方案清单
 */
@Validated
@RestController
@RequestMapping("/qqchConstructionList")
public class QqchConstructionListController extends BaseController {

    @Autowired
    private IQqchConstructionListService qqchConstructionListService;

//    @PreAuthorize(hasPermi = "qqchConstructionList:list")
    @GetMapping("/getList")
    @CustomLogger(title = "前期策划-前期策划编制-施工技术策划-3.4 施工方案计划管理", name = "3.4.2 施工方案清单" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getList(
        @Validated(ValidationGroups.Select.class) QqchConstructionListVo qqchConstructionListParamVo) {
//        if(StringUtils.isBlank(qqchConstructionListParamVo.getWbsCode()))
//            return AjaxResult.success(new ArrayList<>(2));
        QqchConstructionListVo qqchConstructionListVo = qqchConstructionListService.getQqchConstructionListList(qqchConstructionListParamVo);
        return AjaxResult.success(qqchConstructionListVo);
    }

//    @PreAuthorize(hasPermi = "qqchConstructionList:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "前期策划-前期策划编制-施工技术策划-3.4 施工方案计划管理", name = "\n" +
            "3.4.2 施工方案清单" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody QqchConstructionListVo qqchConstructionListVo) {
        qqchConstructionListService.batchSave(qqchConstructionListVo);
        return AjaxResult.success();
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, @RequestBody(required = false) QqchConstructionList qqchConstructionList)
            throws IOException {
//        QqchConstructionListVo qqchConstructionListVo = qqchConstructionListService.getQqchConstructionListList(qqchConstructionListParamVo);
        List<QqchConstructionList> qqchConstructionListList = qqchConstructionListService.list(qqchConstructionList);
        FtExcelUtil<QqchConstructionList> util = new FtExcelUtil<>(QqchConstructionList.class);

        // 导出维护序号
        List<QqchConstructionList> newList = ExportUtil
            .preserveSerialNumber(qqchConstructionListList, QqchConstructionList::setSerialNum);
        util.exportExcel(response, newList, DateUtils.getDate());
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    @CustomLogger(title = "前期策划-前期策划编制-施工技术策划-3.4 施工方案计划管理", name = "\n" +
            "3.4.2 施工方案清单" ,businessType = CustomBusinessType.IMPORT)
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file, BigDecimal version) {
        FtExcelUtil<QqchConstructionList> util = new FtExcelUtil<>(QqchConstructionList.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<QqchConstructionList> list = util.importExcel(inputStream);
            qqchConstructionListService.importData(list, version);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导入失败！"+e.getMessage());
        }
    }
}
