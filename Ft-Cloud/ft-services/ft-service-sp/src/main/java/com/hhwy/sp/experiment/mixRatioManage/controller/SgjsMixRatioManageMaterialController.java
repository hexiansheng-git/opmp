package com.hhwy.sp.experiment.mixRatioManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageMaterial;
import com.hhwy.sp.experiment.mixRatioManage.domain.vo.MixRatioManageMaterialQueryVo;
import com.hhwy.sp.experiment.mixRatioManage.service.ISgjsMixRatioManageMaterialService;
import com.hhwy.utils.excel.FtExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author han
 * @date 2024-04-07 13:27:44
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsMixRatioManageMaterial")
public class SgjsMixRatioManageMaterialController extends BaseController {

    @Autowired
    private ISgjsMixRatioManageMaterialService sgjsMixRatioManageMaterialService;


    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file){
        FtExcelUtil<SgjsMixRatioManageMaterial> util = new FtExcelUtil<>(SgjsMixRatioManageMaterial.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<SgjsMixRatioManageMaterial> materialList = util.importExcel(inputStream);
            return AjaxResult.success(materialList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
    /**
     * 导出
     * @param response
     * @param queryVo
     * @throws IOException
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody MixRatioManageMaterialQueryVo queryVo) throws IOException {
        List<Long> ids = queryVo.getIds();
        List<SgjsMixRatioManageMaterial> materialList;
        if(CollectionUtils.isEmpty(ids)){
            materialList = sgjsMixRatioManageMaterialService.getListByForeignId(queryVo.getForeignId());
        }else {
            materialList = sgjsMixRatioManageMaterialService.getListByIds(ids);
        }
        FtExcelUtil<SgjsMixRatioManageMaterial> util = new FtExcelUtil<>(SgjsMixRatioManageMaterial.class);
        util.exportExcel(response, materialList, DateUtils.getDate());
    }
}
