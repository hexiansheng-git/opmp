package com.hhwy.pm.xmsl.contractInfo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.excel.Util;
import com.hhwy.pm.common.util.TreeNodeUtil;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSpecial;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ImportTreeNodeVo;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ImportXmslContractListVo;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ImportXmslContractSpecial;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractSpecialService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excelUtil.ExcelUtilByTemplate;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-07-10 14:17:51
 * @remark  合同信息登记--专用条件
 */
@Validated
@RestController
@RequestMapping("/xmslContractSpecial")
public class XmslContractSpecialController extends BaseController {

    @Autowired
    private IXmslContractSpecialService xmslContractSpecialService;


    @PreAuthorize(hasPermi = "xmslContractSpecial:list")
    @GetMapping
    @CustomLogger(title = "项目设立-合同信息-专用条件", name = "专用条件", businessType = CustomBusinessType.SELECT)
    public AjaxResult getXmslContractSpecial(@Validated(ValidationGroups.Get.class)  XmslContractSpecial xmslContractSpecialParam) {
        List<XmslContractSpecial> treeVOS  = xmslContractSpecialService.getXmslContractSpecial(xmslContractSpecialParam);
        return AjaxResult.success(treeVOS);
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:list")
    @GetMapping("/list")
    @CustomLogger(title = "项目设立-合同信息-专用条件", name = "专用条件", businessType = CustomBusinessType.SELECT)
    public AjaxResult getXmslContractSpecialList(@Validated(ValidationGroups.Select.class)  XmslContractSpecial xmslContractSpecialParam) {
        startPage();
        List<XmslContractSpecial> xmslContractSpecialList = xmslContractSpecialService.getXmslContractSpecialList(xmslContractSpecialParam);
        return getDataTableAjaxResult(xmslContractSpecialList);
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:add")
    @PostMapping("/add")
    @CustomLogger(title = "项目设立-合同信息-专用条件", name = "专用条件", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertXmslContractSpecial(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractSpecial xmslContractSpecialParam) {
        xmslContractSpecialService.insertXmslContractSpecial(xmslContractSpecialParam);
        return AjaxResult.success(xmslContractSpecialParam);
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "项目设立-合同信息-专用条件", name = "专用条件", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertXmslContractSpecialList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslContractSpecial> xmslContractSpecialListParam) {
        xmslContractSpecialService.insertXmslContractSpecialList(xmslContractSpecialListParam);
        return AjaxResult.success(xmslContractSpecialListParam);
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:update")
    @PostMapping("/update")
    public AjaxResult updateXmslContractSpecial(@Validated(ValidationGroups.Update.class) @RequestBody XmslContractSpecial xmslContractSpecialParam) {
        return toAjax(xmslContractSpecialService.updateXmslContractSpecial(xmslContractSpecialParam));
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslContractSpecialList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslContractSpecial> xmslContractSpecialListParam) {
        return toAjax(xmslContractSpecialService.updateXmslContractSpecialList(xmslContractSpecialListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:remove")
    @PostMapping("/delete")
    @CustomLogger(title = "项目设立-合同信息-专用条件", name = "专用条件", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteXmslContractSpecial(@Validated(ValidationGroups.Delete.class) @RequestBody XmslContractSpecial xmslContractSpecialParam) {
        return toAjax(xmslContractSpecialService.deleteXmslContractSpecial(xmslContractSpecialParam));
    }

    @PreAuthorize(hasPermi = "xmslContractSpecial:remove")
    @PostMapping("/remove")
    @CustomLogger(title = "项目设立-合同信息-专用条件", name = "专用条件", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteXmslContractSpecialByPks(@RequestBody XmslContractSpecial xmslContractSpecialParam) {
        List<Long> xmslContractSpecialPkList = Arrays.asList(xmslContractSpecialParam.getIds());
        Long masterId = xmslContractSpecialParam.getMasterId();
        return toAjax(xmslContractSpecialService.deleteXmslContractSpecialByPks(xmslContractSpecialPkList, masterId));
    }

    /**
     *  导出
     *
     * @param response
     * @param xmslContractSpecialParam
     * @throws IOException
     */
    @GetMapping("/export")
    @CustomLogger(title = "项目设立-合同信息-专用条件", name = "专用条件", businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response,@RequestBody XmslContractSpecial xmslContractSpecialParam) throws IOException {
        try{
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("template/exportXmslContractSpecial.xlsx");
            Map<String, Object> map = new HashMap<>();
            List<XmslContractSpecial> list = xmslContractSpecialService.getXmslContractSpecialList(xmslContractSpecialParam);
            if (CollUtil.isEmpty(list)) return;
            List<XmslContractSpecial> collect = list.stream().filter(p -> StrUtil.isNotBlank(p.getCode())).collect(Collectors.toList());
            collect.sort((k1, k2) -> k1.getCode().compareToIgnoreCase(k2.getCode()));
            ExcelUtilByTemplate.exportExcel(response, collect, map, "xmslContractSpecial", resourceAsStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  导入
     * @param file
     * @return
     */
    @PostMapping("/import")
    @CustomLogger(title = "项目设立-合同信息-专用条件", name = "专用条件", businessType = CustomBusinessType.IMPORT)
    public AjaxResult importDate(@RequestPart("file") MultipartFile file) throws Exception {
        ExcelUtils<ImportXmslContractSpecial> util = new ExcelUtils<>(ImportXmslContractSpecial.class);
        InputStream inputStream = file.getInputStream();
        List<ImportXmslContractSpecial> xmslContractLists = util.importExcel(inputStream);
        if (CollUtil.isEmpty(xmslContractLists)) {
            return AjaxResult.error("无数据可处理");
        }
        List<ImportXmslContractSpecial> importXmslContractSpecials = this.parseLevelStruct(xmslContractLists);
        List<ImportXmslContractSpecial> dateList = ListTreeUtil.formatTree(importXmslContractSpecials, o -> o.getPid()==null
                , (r, n) -> r.getId().equals(n.getPid())
                , ImportXmslContractSpecial::getChildren
                , ImportXmslContractSpecial::setChildren);
        return AjaxResult.success(dateList);
    }

    /**
     *  给编制模块（合同策划）提供接口
     * @param xmslContractSpecialParam
     * @return
     */
    @PostMapping("/provideList")
    @CustomLogger(title = "项目设立-合同信息-专用条件", name = "专用条件", businessType = CustomBusinessType.SELECT)
    public AjaxResult provideList(@Validated(ValidationGroups.Get.class) XmslContractSpecial xmslContractSpecialParam) {
        List<XmslContractSpecial> treeVOS = xmslContractSpecialService.provideList(xmslContractSpecialParam);
        return AjaxResult.success(treeVOS);
    }


    /**
     * 功能描述: 导入功能，将excel中的数据组成树形结构返回前端
     * @param list excel数据
     * @return java.util.List<T> 树形结构
     * 作者: fushudong
     * 时间: 2024/1/8
     */
    public static List<ImportXmslContractSpecial> parseLevelStruct(List<ImportXmslContractSpecial> list) {
        Map<String, ImportXmslContractSpecial> collect = list.stream()
                .filter(p -> com.hhwy.common.core.utils.StringUtils.isNotEmpty(p.getInnerCode()))
                .collect(Collectors.toMap(key -> key.getInnerCode(), value -> value, (v1, v2) -> v1));
        for (int i = 0;  i< list.size(); i++) {
            ImportXmslContractSpecial t = list.get(i);
            t.setId(IdUtil.getSnowflakeNextId());
            String innerCode = t.getInnerCode();
            if (!innerCode.contains("-")) {
                //第一层级
                continue;
            }
            String parentCode = innerCode.substring(0, innerCode.lastIndexOf("-"));
            String curentCode = innerCode.substring(innerCode.lastIndexOf("-") +1);
            //获取当前数据的父层级
            ImportXmslContractSpecial parent = collect.get(parentCode);
            Assert.notNull(parent, "层级码：{} 未找到父层级：{}，请确认是否存在", innerCode, parentCode);
            //获取父层级的children，将当前记录add进去
            List<ImportXmslContractSpecial> children = parent.getChildren();
            if (CollectionUtil.isEmpty(children)) {
                children = new ArrayList<>();
            }
            t.setPid(parent.getId());
            children.add(t);
        }
        return new ArrayList<>(collect.values());
    }
}
