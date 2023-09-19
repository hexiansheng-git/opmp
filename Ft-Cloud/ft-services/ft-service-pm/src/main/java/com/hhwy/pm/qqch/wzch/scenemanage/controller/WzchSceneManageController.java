package com.hhwy.pm.qqch.wzch.scenemanage.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.wzch.scenemanage.domain.WzchSceneManage;
import com.hhwy.pm.qqch.wzch.scenemanage.domain.WzchSceneManageDetail;
import com.hhwy.pm.qqch.wzch.scenemanage.dto.WzchSceneManageDTO;
import com.hhwy.pm.qqch.wzch.scenemanage.service.WzchSceneManageService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 现场物资管控策划(WzchSceneManage)表控制层
 *
 * @author makejava
 * @since 2022-12-18 11:24:30
 */
@RestController
@RequestMapping("wzch/sceneManage")
public class WzchSceneManageController extends BaseController {

    @Resource
    private WzchSceneManageService wzchSceneManageService;

    /**
     * 新增 编辑 详情数据回显
     */
    @GetMapping("baseInfo")
//    @CustomLogger(title = "新增 编辑 详情数据回显", businessType = CustomBusinessType.SELECT)
    public AjaxResult baseInfo(@RequestParam(required = false) WzchSceneManageDTO dto) {
        return AjaxResult.success(wzchSceneManageService.baseInfo(dto==null?new WzchSceneManageDTO():dto));
    }


    /**
     * 查询资金策划列表
     */
    @PreAuthorize(hasPermi = "wzch:sceneManage:list")
    @PostMapping("/list")
//    @CustomLogger(title = "查询资金策划列表", businessType = CustomBusinessType.SELECT)
    public AjaxResult list(@Validated(ValidationGroups.Select.class) @RequestBody WzchSceneManage wzchSceneManage) {
        startPage();
        List<WzchSceneManage> list = wzchSceneManageService.selectWzchSceneManageList(wzchSceneManage);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出资金策划列表
     */
    @PostMapping("/export")
//    @CustomLogger(title = "导出资金策划列表", businessType = CustomBusinessType.EXPORT)
    public void export(WzchSceneManage wzchSceneManage, HttpServletResponse response) {
        try {
            List<WzchSceneManage> list = wzchSceneManageService.selectWzchSceneManageList(wzchSceneManage);
            ExcelUtils<WzchSceneManage> util = new ExcelUtils<>(WzchSceneManage.class);
            util.exportExcel(response, list, "sceneManage");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出失败");
        }
    }

    /**
     * 新增保存资金策划
     */
    @PreAuthorize(hasPermi = "wzch:sceneManage:add")
    @PostMapping("/add")
//    @CustomLogger(title = "新增保存资金策划", businessType = CustomBusinessType.SAVE)
    public AjaxResult addSave(@Validated(ValidationGroups.Save.class) @RequestBody WzchSceneManageDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchSceneManageService.insert(dto)));
    }


    /**
     * 修改保存资金策划
     */
    @PreAuthorize(hasPermi = "wzch:sceneManage:edit")
    @PostMapping("/edit")
//    @CustomLogger(title = "修改保存资金策划", businessType = CustomBusinessType.UPDATE)
    public AjaxResult editSave(@Validated(ValidationGroups.Update.class) @RequestBody WzchSceneManageDTO dto) {
        return AjaxResult.success("操作成功", String.valueOf(wzchSceneManageService.edit(dto)));
    }


    /**
     * 删除资金策划
     */
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody Map<String,String> map) {
        String ids = map.get("ids");
        return toAjax(wzchSceneManageService.deleteByIds(ids));
    }


    /**
     * 导出资金策划详情
     */
    @PostMapping("detail/export")
//    @CustomLogger(title = "导出资金策划详情", businessType = CustomBusinessType.EXPORT)
    public void exportDetail(@Validated(ValidationGroups.Other.class) @RequestBody Map<String, List<WzchSceneManageDetail>> params, HttpServletResponse response) {
        try {
            List<WzchSceneManageDetail> details = params.get("detailList");
            ExcelUtils<WzchSceneManageDetail> util = new ExcelUtils<>(WzchSceneManageDetail.class);
            util.exportExcel(response, details, "资金策划详情");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "导出异常");
        }
    }


    @PostMapping("detail/importData")
//    @CustomLogger(title = "导入周转材料详情列表", businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file, @RequestParam Map map) {
        try {
            ExcelUtils<WzchSceneManageDetail> util = new ExcelUtils<>(WzchSceneManageDetail.class);
            List<WzchSceneManageDetail> dtoList = util.importExcel(file.getInputStream());
            return AjaxResult.success(dtoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "获取导入数据异常");

        }
    }


}

