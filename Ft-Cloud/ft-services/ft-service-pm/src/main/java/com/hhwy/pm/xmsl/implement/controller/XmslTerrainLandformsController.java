package com.hhwy.pm.xmsl.implement.controller;

import com.alibaba.excel.EasyExcel;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.listener.TerrainLandformsImportListener;
import com.hhwy.pm.xmsl.implement.domain.XmslTerrainLandforms;
import com.hhwy.pm.xmsl.implement.domain.vo.ImplementVo;
import com.hhwy.pm.xmsl.implement.service.IXmslTerrainLandformsService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:13
 * @remark 地形地貌
 */
@RestController
@RequestMapping("/xmslTerrainLandforms")
public class XmslTerrainLandformsController extends BaseController {

    @Autowired
    private IXmslTerrainLandformsService xmslTerrainLandformsService;

    /**
     * 实施条件列表-所有列表一起返回
     *
     * @return
     */
    @PreAuthorize(hasPermi = "xmslTerrainLandforms:list")
    @GetMapping("/getAllList")
    public AjaxResult getAllList() {
        ImplementVo implementVo = xmslTerrainLandformsService.getAllList();
        return AjaxResult.success(implementVo);
    }

    /**
     * 实施条件所有表格数据批量保存
     *
     * @param implementVo
     * @return
     */
    @PreAuthorize(hasPermi = "xmslTerrainLandforms:add")
    @PostMapping("/batchSave")
    @CustomLogger(title = "项目设立", name = "实施条件" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult batchSave(@RequestBody ImplementVo implementVo) {
        xmslTerrainLandformsService.batchSave(implementVo);
        return AjaxResult.success("保存成功！");
    }

    @GetMapping("/getList")
    public AjaxResult getList(XmslTerrainLandforms xmslTerrainLandformsParam) {
        List<XmslTerrainLandforms> xmslTerrainLandformsList = xmslTerrainLandformsService
            .getXmslTerrainLandformsList(xmslTerrainLandformsParam);
        return AjaxResult.success(xmslTerrainLandformsList);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody List<XmslTerrainLandforms> xmslTerrainLandformsListParam) {
        xmslTerrainLandformsService.save(xmslTerrainLandformsListParam);
        return AjaxResult.success("保存成功！");
    }

    @PostMapping("/remove")
    public AjaxResult deleteXmslTerrainLandformsByPks(Long[] pks) {
        List<Long> xmslTerrainLandformsPkList = Arrays.asList(pks);
        return toAjax(xmslTerrainLandformsService.deleteXmslTerrainLandformsByPks(xmslTerrainLandformsPkList));
    }

    /**
     * 地形地貌导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        try {
            TerrainLandformsImportListener readListener = new TerrainLandformsImportListener();
            try {
                // 两行表头
                EasyExcel.read(file.getInputStream(), XmslTerrainLandforms.class, readListener).headRowNumber(2)
                    .sheet(0).doRead();
                List<XmslTerrainLandforms> list = readListener.getList();
                return AjaxResult.success(list);
            } catch (IOException e) {
                return AjaxResult.error();
            }
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslTerrainLandforms xmslTerrainLandformsParam)
        throws IOException {
        List<XmslTerrainLandforms> xmslTerrainLandformsList =
            xmslTerrainLandformsService.getXmslTerrainLandformsList(xmslTerrainLandformsParam);
        ExcelUtils<XmslTerrainLandforms> util = new ExcelUtils<>(XmslTerrainLandforms.class);
        util.exportExcel(response, xmslTerrainLandformsList, DateUtils.getDate());
    }
}
