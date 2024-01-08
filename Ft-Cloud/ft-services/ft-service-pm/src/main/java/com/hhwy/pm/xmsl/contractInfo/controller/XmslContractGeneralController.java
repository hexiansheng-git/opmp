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
import com.hhwy.pm.common.util.TreeNodeUtil;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSpecial;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ImportTreeNodeVo;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ImportXmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ImportXmslContractListVo;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractGeneralVo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractGeneralService;
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
        try{
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("template/exportXmslContractGeneral.xlsx");
            Map<String, Object> map = new HashMap<>();
            List<XmslContractGeneral> list = xmslContractGeneralService.getXmslContractGeneralList(xmslContractGeneralParam);
            if (CollUtil.isEmpty(list)) return;
            List<XmslContractGeneral> collect = list.stream().filter(p -> StrUtil.isNotBlank(p.getCode())).collect(Collectors.toList());
            collect.sort((k1, k2) -> k1.getCode().compareToIgnoreCase(k2.getCode()));
            ExcelUtilByTemplate.exportExcel(response, collect, map, "xmslContractGeneral", resourceAsStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  导入
     *
     * @param file
     * @return
     */
    @PostMapping("/import")
    @CustomLogger(title = "项目设立-合同信息-通用条件", name = "通用条件", businessType = CustomBusinessType.IMPORT)
    public AjaxResult importDate(@RequestPart("file") MultipartFile file) throws Exception {
        ExcelUtils<ImportXmslContractGeneral> util = new ExcelUtils<>(ImportXmslContractGeneral.class);
        InputStream inputStream = file.getInputStream();
        List<ImportXmslContractGeneral> xmslContractLists = util.importExcel(inputStream);
        if (CollUtil.isEmpty(xmslContractLists)) {
            return AjaxResult.error("无数据可处理");
        }
        //找到层级关系
        List<ImportXmslContractGeneral> treeList = this.parseLevelStruct(xmslContractLists);
        List<ImportXmslContractGeneral> dateList = ListTreeUtil.formatTree(treeList, o -> o.getPid()==null
                , (r, n) -> r.getId().equals(n.getPid())
                , ImportXmslContractGeneral::getChildren
                , ImportXmslContractGeneral::setChildren);
        return AjaxResult.success(dateList);
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

    /**
     * 功能描述: 导入功能，将excel中的数据组成树形结构返回前端
     * @param list excel数据
     * @return java.util.List<T> 树形结构
     * 作者: fushudong
     * 时间: 2024/1/8
     */
    public static  List<ImportXmslContractGeneral> parseLevelStruct(List<ImportXmslContractGeneral> list) {
        Map<String, ImportXmslContractGeneral> collect = list.stream()
                .filter(p -> com.hhwy.common.core.utils.StringUtils.isNotEmpty(p.getInnerCode()))
                .collect(Collectors.toMap(key -> key.getInnerCode(), value -> value, (v1, v2) -> v1));
        for (int i = 0;  i< list.size(); i++) {
            ImportXmslContractGeneral t = list.get(i);
            t.setId(IdUtil.getSnowflakeNextId());
            String innerCode = t.getInnerCode();
            if (!innerCode.contains("-")) {
                //第一层级
                continue;
            }
            String parentCode = innerCode.substring(0, innerCode.lastIndexOf("-"));
            String curentCode = innerCode.substring(innerCode.lastIndexOf("-") +1);
            //获取当前数据的父层级
            ImportXmslContractGeneral parent = collect.get(parentCode);
            Assert.notNull(parent, "层级码：{} 未找到父层级：{}，请确认是否存在", innerCode, parentCode);
            //获取父层级的children，将当前记录add进去
            List<ImportXmslContractGeneral> children = parent.getChildren();
            if (CollectionUtil.isEmpty(children)) {
                children = new ArrayList<>();
            }
            t.setPid(parent.getId());
            children.add(t);
        }
        return new ArrayList<>(collect.values());
    }
}
