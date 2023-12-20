package com.hhwy.pm.xmsl.contractInfo.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ImportXmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractGeneralVo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractGeneralService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:30
 * @remark   合同信息登记--通用条件
 */
@Validated
@RestController
@RequestMapping("/xmslContractGeneral")
public class XmslContractGeneralController extends BaseController {

    @Autowired
    private IXmslContractGeneralService xmslContractGeneralService;

    @PreAuthorize(hasPermi = "xmslContractGeneral:list")
    @GetMapping
    @CustomLogger(title = "项目设立-合同信息-通用条件", name = "通用条件", businessType = CustomBusinessType.SELECT)
    public AjaxResult getXmslContractGeneral(@Validated(ValidationGroups.Get.class)  XmslContractGeneral xmslContractGeneralParam) {
        List<XmslContractGeneral> treeVOS = xmslContractGeneralService.getXmslContractGeneral(xmslContractGeneralParam);
        return AjaxResult.success(treeVOS);
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:list")
    @GetMapping("/list")
    @CustomLogger(title = "项目设立-合同信息-通用条件", name = "通用条件", businessType = CustomBusinessType.SELECT)
    public AjaxResult getXmslContractGeneralList(@Validated(ValidationGroups.Select.class)  XmslContractGeneral xmslContractGeneralParam) {
        startPage();
        List<XmslContractGeneral> xmslContractGeneralList = xmslContractGeneralService.getXmslContractGeneralList(xmslContractGeneralParam);
        return getDataTableAjaxResult(xmslContractGeneralList);
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:add")
    @PostMapping("/add")
    @CustomLogger(title = "项目设立-合同信息-通用条件", name = "通用条件", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertXmslContractGeneral(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractGeneral xmslContractGeneralParam) {
        xmslContractGeneralService.insertXmslContractGeneral(xmslContractGeneralParam);
        return AjaxResult.success(xmslContractGeneralParam);
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "项目设立-合同信息-通用条件", name = "通用条件", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertXmslContractGeneralList(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractGeneralVo param) {
        if (null == param.getMasterId()) {
            return AjaxResult.error("masterId不能为空");
        }
        xmslContractGeneralService.insertXmslContractGeneralList(param);
        return AjaxResult.success(param);
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:update")
    @PostMapping("/update")
    public AjaxResult updateXmslContractGeneral(@Validated(ValidationGroups.Update.class) @RequestBody XmslContractGeneral xmslContractGeneralParam) {
        return toAjax(xmslContractGeneralService.updateXmslContractGeneral(xmslContractGeneralParam));
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslContractGeneralList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslContractGeneral> xmslContractGeneralListParam) {
        return toAjax(xmslContractGeneralService.updateXmslContractGeneralList(xmslContractGeneralListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslContractGeneral(@Validated(ValidationGroups.Delete.class) @RequestBody XmslContractGeneral xmslContractGeneralParam) {
        return toAjax(xmslContractGeneralService.deleteXmslContractGeneral(xmslContractGeneralParam));
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:remove")
    @PostMapping("/remove")
    @CustomLogger(title = "项目设立-合同信息-通用条件", name = "通用条件", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteXmslContractGeneralByPks(@RequestBody XmslContractGeneral xmslContractGeneralParam) {
        Long [] ids = xmslContractGeneralParam.getIds();
        Long masterId = xmslContractGeneralParam.getMasterId();
        List<Long> xmslContractGeneralPkList = Arrays.asList(ids);
        return toAjax(xmslContractGeneralService.deleteXmslContractGeneralByPks(xmslContractGeneralPkList, masterId));
    }

    /**
     *  导出
     *
     * @param response
     * @param xmslContractGeneralParam
     * @throws IOException
     */
    @GetMapping("/export")
    @CustomLogger(title = "项目设立-合同信息-通用条件", name = "通用条件", businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response,@RequestBody XmslContractGeneral xmslContractGeneralParam) throws IOException {
        List<XmslContractGeneral> xmslContractGeneralList = xmslContractGeneralService.getXmslContractGeneralList(xmslContractGeneralParam);
        ExcelUtils<XmslContractGeneral> util = new ExcelUtils<>(XmslContractGeneral.class);
        util.exportExcel(response, xmslContractGeneralList, DateUtils.getDate());
    }

    /**
     *  导入
     *
     * @param file
     * @return
     */
    @PostMapping("/import")
    @CustomLogger(title = "项目设立-合同信息-通用条件", name = "通用条件", businessType = CustomBusinessType.IMPORT)
    public AjaxResult importDate(@RequestPart("file") MultipartFile file){
        ExcelUtils<ImportXmslContractGeneral> util = new ExcelUtils<>(ImportXmslContractGeneral.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<ImportXmslContractGeneral> xmslContractLists = util.importExcel(inputStream);
            List<ImportXmslContractGeneral> dateList = ListTreeUtil.formatTree(xmslContractLists, o -> o.getParentInnerCode()==0, (r, n) -> r.getInnerCode().equals(n.getParentInnerCode()), ImportXmslContractGeneral::getChildren, ImportXmslContractGeneral::setChildren);
            return AjaxResult.success(dateList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }


    /**
     *  给编制模块（合同策划）提供接口
     * @param xmslContractGeneralParam
     * @return
     */
    @PostMapping("/provideList")
    @CustomLogger(title = "项目设立-合同信息-通用条件", name = "通用条件 - 给编制模块（合同策划）提供接口", businessType = CustomBusinessType.SELECT)
    public AjaxResult provideList(@Validated(ValidationGroups.Get.class) XmslContractGeneral xmslContractGeneralParam) {
        List<XmslContractGeneral> treeVOS = xmslContractGeneralService.provideList(xmslContractGeneralParam);
        return AjaxResult.success(treeVOS);
    }


    /**
     * 功能描述: 弹窗功能，整合弹框选中和列表中的数据
     * 作者: fushudong
     * 时间: 2023/12/11
     */
    @PostMapping("/dataHandler")
    public AjaxResult dataHandler(@RequestBody XmslContractGeneralVo xmslContractGeneralVo){
        List<XmslContractGeneral> result = xmslContractGeneralService.dataHandler(xmslContractGeneralVo);
        return AjaxResult.success(result);
    }
}
