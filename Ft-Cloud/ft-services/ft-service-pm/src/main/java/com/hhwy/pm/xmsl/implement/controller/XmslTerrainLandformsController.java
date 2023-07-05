package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslTerrainLandforms;
import com.hhwy.pm.xmsl.implement.service.IXmslTerrainLandformsService;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
     * 导入
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        ExcelUtils<XmslTerrainLandforms> util = new ExcelUtils<>(XmslTerrainLandforms.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslTerrainLandforms> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
