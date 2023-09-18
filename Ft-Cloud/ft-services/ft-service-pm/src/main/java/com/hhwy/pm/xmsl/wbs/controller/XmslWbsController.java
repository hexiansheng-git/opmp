package com.hhwy.pm.xmsl.wbs.controller;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.gm.wbs.domain.TWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsHistory;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.dto.XmslWbsDto;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsMainService;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.tree.TreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.utils.validation.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * wbs
 * @author wk
 * @date 2023-07-13 18:13:13
 * @remark
 */
@Validated
@RestController
@RequestMapping("/xmslWbs")
public class XmslWbsController extends BaseController {
    @Autowired
    private IXmslWbsService xmslWbsService;
    @Autowired
    private IXmslWbsMainService xmslWbsMainService;

    @PreAuthorize(hasPermi = "xmslWbs:list")
    @PostMapping
    public AjaxResult getXmslWbs(@Validated(ValidationGroups.Get.class) @RequestBody XmslWbs xmslWbsParam) {
        XmslWbs xmslWbs = xmslWbsService.getXmslWbs(xmslWbsParam);
        return AjaxResult.success(xmslWbs);
    }

    @PreAuthorize(hasPermi = "xmslWbs:list")
    @PostMapping("/list")
    public AjaxResult getXmslWbsList(@Validated(ValidationGroups.Select.class) @RequestBody XmslWbs wbs) {
        Map map = xmslWbsService.listData(wbs);
        return AjaxResult.success(map);
    }

    @PostMapping("/getChildrenByIds")
    public AjaxResult getChildrenByIds(@RequestBody Map map) {
        String ids = ObjectUtils.nvlString(map.get("ids"));
        if(StringUtils.isBlank(ids))
            return AjaxResult.error("参数缺失");
        List<XmslWbs> wbsList = xmslWbsService.childListByIds(Convert.toLongArray(ids));
        return AjaxResult.success(wbsList);
    }

    /**
     * 获取指定wbs的子级，也会将传入的wbs返回
     * @param map {ids}
     * @return
     */
    @PostMapping("/getChildrenByIdsWithSelf")
    public AjaxResult getChildrenByIdsWithSelf(@RequestBody Map map) {
        String ids = ObjectUtils.nvlString(map.get("ids"));
        if(StringUtils.isBlank(ids))
            return AjaxResult.error("参数缺失");
        List<XmslWbs> wbsList = xmslWbsService.childListByIds(Convert.toLongArray(ids),true);
        return AjaxResult.success(wbsList);
    }

    /**
     * 获取指定wbs的子级，并覆盖掉其id以及父级id。为前端赋值黏贴使用
     * @param map {ids}
     * @return
     */
    @PostMapping("/copyData")
    public AjaxResult copyData(@RequestBody Map map) {
        String ids = ObjectUtils.nvlString(map.get("ids"));
        if(StringUtils.isBlank(ids))
            return AjaxResult.error("参数缺失");
        Map<String,List<XmslWbsHistory>> resuMap = xmslWbsService.copyChildList(Convert.toLongArray(ids)
                ,ObjectUtils.nvlLong(map.get("mainId")));
        return AjaxResult.success(resuMap);
    }


    @PostMapping("/latestList")
    public AjaxResult latestList(@RequestBody XmslWbs wbs) {
        List<XmslWbs> list = xmslWbsService.latestData(wbs);
        return AjaxResult.success(list);
    }

    @PreAuthorize(hasPermi = "xmslWbs:add")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody XmslWbsDto dto) {
        if(dto.getSubmitFlag() != null && dto.getSubmitFlag() == 1)
            ValidationUtil.getValidator().validate(dto,ValidationGroups.Save.class);
        xmslWbsService.save(dto);
        return AjaxResult.success(dto.getMainId());
    }

    /**
     * 处理祖级名称、id
     * @return
     */
    @PostMapping("/handlerAncestor")
    public AjaxResult handlerAncestor() {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        xmslWbsService.handlerAncestors();
        return AjaxResult.success();
    }

    /**
     * 处理祖级名称、id
     * @return
     */
    @PostMapping("/initWbs2Redis")
    public AjaxResult initWbs2Redis() {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        xmslWbsService.initWbs2Redis();
        return AjaxResult.success();
    }


    @PostMapping("/exportTemplate")
    public void exportTemplate(HttpServletRequest request, HttpServletResponse response) {
        FtExcelUtil<XmslWbs> excelUtil = new FtExcelUtil<>(XmslWbs.class);
        try {
            excelUtil.exportExcel(response,"项目WBS模板.xlsx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/importData")
    public AjaxResult exportTemplate(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
        try {
            return AjaxResult.success(xmslWbsService.importData(file));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("导入失败:"+e.getMessage());
        }
    }

    @PostMapping("/exportData")
    public void export(@RequestBody XmslWbsMain wbsMain,HttpServletResponse response) throws IOException {
        if(wbsMain.getId()==null){
            wbsMain = this.xmslWbsMainService.getEffect();
            return ;
        }
        List list = xmslWbsService.getByMainId(wbsMain.getId());
        for (int i = 0; i < list.size(); i++) {
           XmslWbs temp = (XmslWbs)list.get(i);
           temp.setPid(temp.getParentId());
        }
        list = TreeUtil.exportListFormat(list, (Class)String.class);
        FtExcelUtil<XmslWbs> util = new FtExcelUtil<>(XmslWbs.class);
        util.exportExcel(response, list, DateUtils.getDate(),"项目WBS.xlsx");
    }

}
