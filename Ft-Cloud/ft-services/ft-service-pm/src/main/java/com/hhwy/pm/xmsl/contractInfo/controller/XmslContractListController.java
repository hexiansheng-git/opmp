package com.hhwy.pm.xmsl.contractInfo.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.UUIDUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.excel.Util;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ContractListQueryVo;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ImportXmslContractListVo;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractListDto;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractListVo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.utils.Constant;
import com.hhwy.utils.excelUtil.ExcelUtilByTemplate;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
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
 * @date 2023-07-10 14:17:42
 * @remark  合同信息--主合同清单
 */
@Validated
@RestController
@RequestMapping("/xmslContractList")
public class XmslContractListController extends BaseController {

    @Autowired
    private IXmslContractListService xmslContractListService;


    @PreAuthorize(hasPermi = "xmslContractList:list")
    @GetMapping
    public AjaxResult getXmslContractList(@Validated(ValidationGroups.Get.class)  XmslContractList xmslContractListParam) {
        List<XmslContractList> treeList  = xmslContractListService.getXmslContractList(xmslContractListParam);
        return AjaxResult.success(treeList);
    }

    /**
     *   懒加载列表
     * @param xmslContractListParam
     * @return
     */
    @PreAuthorize(hasPermi = "xmslContractList:list")
    @GetMapping("/lazylist")
    public AjaxResult getXmslContractList2(@Validated(ValidationGroups.Get.class)  XmslContractList xmslContractListParam) {
        List<XmslContractList> list  = xmslContractListService.getXmslContractList2(xmslContractListParam);
        return AjaxResult.success(list);
    }

    /**
     *   获取生效的清单列表（懒加载）
     *
     * @param xmslContractListParam
     * @return
     */
    @GetMapping("/getEffectList")
    public AjaxResult getEffectList(@Validated(ValidationGroups.Get.class)  XmslContractList xmslContractListParam) {
        //默认第一层级
        if(xmslContractListParam.getPid() == null || xmslContractListParam.getPid() <= 0L){
            xmslContractListParam.setPtVar1("1");
            xmslContractListParam.setPid(null);
        }
        List<XmslContractList> list  = xmslContractListService.getEffectList(xmslContractListParam);
        return AjaxResult.success(list);
    }




    @PreAuthorize(hasPermi = "xmslContractList:list")
    @GetMapping("/list")
    public AjaxResult getXmslContractListList(@Validated(ValidationGroups.Select.class)  XmslContractList xmslContractListParam) {
        startPage();
        List<XmslContractList> xmslContractListList = xmslContractListService.getXmslContractListList(xmslContractListParam);
        return getDataTableAjaxResult(xmslContractListList);
    }

    @PreAuthorize(hasPermi = "xmslContractList:add")
    @PostMapping("/add")
    public AjaxResult insertXmslContractList(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractList xmslContractListParam) {
        xmslContractListService.insertXmslContractList(xmslContractListParam);
        return AjaxResult.success(xmslContractListParam);
    }

    @PreAuthorize(hasPermi = "xmslContractList:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertXmslContractListList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslContractListVo> xmslContractListListParam) {
        int i = xmslContractListService.insertXmslContractListList(xmslContractListListParam);
        if (i == 500) return AjaxResult.error("保存异常，主合同清单编号重复");
        return AjaxResult.success(xmslContractListListParam);
    }

    @PreAuthorize(hasPermi = "xmslContractList:update")
    @PostMapping("/update")
    public AjaxResult updateXmslContractList(@Validated(ValidationGroups.Update.class) @RequestBody XmslContractListDto dto) {
        xmslContractListService.updateXmslContractList(dto);
        return AjaxResult.success();
    }



    @PreAuthorize(hasPermi = "xmslContractList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslContractList(@Validated(ValidationGroups.Delete.class) @RequestBody XmslContractList xmslContractListParam) {
        return toAjax(xmslContractListService.deleteXmslContractList(xmslContractListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractList:remove")
    @PostMapping("/remove")
    public AjaxResult deleteXmslContractListByPks(@RequestBody XmslContractList xmslContractListParam) {
        List<Long> xmslContractListPkList = Arrays.asList(xmslContractListParam.getIds());
        Long masterId = xmslContractListParam.getMasterId();
        return toAjax(xmslContractListService.deleteXmslContractListByPks(xmslContractListPkList, masterId));
    }

    /**
     *  导出接口
     *
     * @param response
     * @param xmslContractListParam
     * @throws IOException
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody XmslContractList xmslContractListParam) throws IOException {
        try{
             InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("template/exportXmslContractList.xlsx");
             Map<String, Object> map = new HashMap<>();
            List<XmslContractList> list = xmslContractListService.getXmslContractListList(xmslContractListParam);
            Util util = new Util();
            for (XmslContractList xmslContractList : list) {
                String s = util.resolveDict("list_type", xmslContractList.getListType());
                xmslContractList.setListType(s);
            }

            ExcelUtilByTemplate.exportExcel(response, list, map, "xmslContractList", resourceAsStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  导入接口
     *
     * @throws IOException
     */
    @PostMapping("/import")
    public AjaxResult importDate(@RequestPart("file") MultipartFile file) {
        ExcelUtils<ImportXmslContractListVo> util = new ExcelUtils<>(ImportXmslContractListVo.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<ImportXmslContractListVo> importXmslContractListVos = util.importExcel(inputStream);
            List<ImportXmslContractListVo> dateList1 = toTreeList1(importXmslContractListVos);
            List<ImportXmslContractListVo> dateList = ListTreeUtil.formatTree(dateList1, o -> o.getPid()==null, (r, n) -> r.getId().equals(n.getPid()), ImportXmslContractListVo::getChildren, ImportXmslContractListVo::setChildren);
            return AjaxResult.success(dateList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    public static void main(String[] args) {
        ArrayList<ImportXmslContractListVo> importXmslContractListVos = new ArrayList<>();
        ImportXmslContractListVo importXmslContractListVo1 = new ImportXmslContractListVo();
        ImportXmslContractListVo importXmslContractListVo2 = new ImportXmslContractListVo();
        ImportXmslContractListVo importXmslContractListVo3 = new ImportXmslContractListVo();
        ImportXmslContractListVo importXmslContractListVo4 = new ImportXmslContractListVo();
        ImportXmslContractListVo importXmslContractListVo5 = new ImportXmslContractListVo();
        ImportXmslContractListVo importXmslContractListVo6 = new ImportXmslContractListVo();
        ImportXmslContractListVo importXmslContractListVo7 = new ImportXmslContractListVo();
        importXmslContractListVo1.setInnerCode("1");
        importXmslContractListVo2.setInnerCode("1-1");
        importXmslContractListVo3.setInnerCode("1-2");
        importXmslContractListVo4.setInnerCode("1-2-1");
        importXmslContractListVo5.setInnerCode("2");
        importXmslContractListVo6.setInnerCode("2-1");
        importXmslContractListVo7.setInnerCode("2-2");
        importXmslContractListVos.add(importXmslContractListVo1);
        importXmslContractListVos.add(importXmslContractListVo2);
        importXmslContractListVos.add(importXmslContractListVo3);
        importXmslContractListVos.add(importXmslContractListVo4);
        importXmslContractListVos.add(importXmslContractListVo5);
        importXmslContractListVos.add(importXmslContractListVo6);
        importXmslContractListVos.add(importXmslContractListVo7);
        List<ImportXmslContractListVo> importXmslContractListVos1 = toTreeList1(importXmslContractListVos);
        List<ImportXmslContractListVo> dateList = ListTreeUtil.formatTree(importXmslContractListVos1, o -> o.getPid()==null, (r, n) -> r.getId().equals(n.getPid()), ImportXmslContractListVo::getChildren, ImportXmslContractListVo::setChildren);

        dateList.forEach(System.out::println);
    }


    private static List<ImportXmslContractListVo> toTreeList1(List<ImportXmslContractListVo> importXmslContractListVos){
        Map<String, ImportXmslContractListVo> collect = importXmslContractListVos.stream()
                .filter(p -> StringUtils.isNotEmpty(p.getInnerCode()))
                .collect(Collectors.toMap(key -> key.getInnerCode(), value -> value, (v1, v2) -> v1));
        for (int i = 0;  i< importXmslContractListVos.size(); i++) {
            ImportXmslContractListVo importXmslContractListVo = importXmslContractListVos.get(i);
            importXmslContractListVo.setId(IdUtil.getSnowflakeNextId());
            String innerCode = importXmslContractListVo.getInnerCode();
            if (!innerCode.contains("-")) {
                //第一层级
                continue;
            }
            String parentCode = innerCode.substring(0, innerCode.lastIndexOf("-"));
            String curentCode = innerCode.substring(innerCode.lastIndexOf("-") +1);
            //获取当前数据的父层级
            ImportXmslContractListVo parent = collect.get(parentCode);
            Assert.notNull(parent, "第{}数据未找到父层级，请确认编号按层级顺序排列", i);
            //获取父层级的children，将当前记录add进去
            List<ImportXmslContractListVo> children = parent.getChildren();
            if (CollectionUtil.isEmpty(children)) {
                children = new ArrayList<>();
            }
            importXmslContractListVo.setPid(parent.getId());
            children.add(importXmslContractListVo);
        }
        return new ArrayList<>(collect.values());
    }

    private static List<ImportXmslContractListVo> toTreeList(List<ImportXmslContractListVo> importXmslContractListVos){
        Map<String, ImportXmslContractListVo> result = new HashMap<>();
        Map<String, ImportXmslContractListVo> collect = importXmslContractListVos.stream()
                .filter(p -> StringUtils.isNotEmpty(p.getInnerCode()))
                .collect(Collectors.toMap(key -> key.getInnerCode(), value -> value, (v1, v2) -> v1));
        for (int i = 0;  i< importXmslContractListVos.size(); i++) {
            ImportXmslContractListVo importXmslContractListVo = importXmslContractListVos.get(i);
            importXmslContractListVo.setId(IdUtil.getSnowflakeNextId());
            String innerCode = importXmslContractListVo.getInnerCode();
            if (!innerCode.contains("-")) {
                //第一层级
                result.put(innerCode, importXmslContractListVo);
                continue;
            }
            String parentCode = innerCode.substring(0, innerCode.lastIndexOf("-"));
            String curentCode = innerCode.substring(innerCode.lastIndexOf("-") +1);
            //获取当前数据的父层级
            ImportXmslContractListVo parent = collect.get(parentCode);
            Assert.notNull(parent, "第{}数据未找到父层级，请确认编号按层级顺序排列", i);
            //获取父层级的children，将当前记录add进去
            String level1 = parent.getInnerCode().split("-")[0];
            if (ObjectUtil.isEmpty(result.get(level1))) {
                result.put(parent.getInnerCode(), parent);
            }
            List<ImportXmslContractListVo> children = result.get(parent.getInnerCode()).getChildren();
             if (CollectionUtil.isEmpty(children)) {
                children = new ArrayList<>();
                result.get(parent.getInnerCode()).setChildren(children);
            }
            importXmslContractListVo.setPid(parent.getId());
            children.add(importXmslContractListVo);
        }
        return new ArrayList<>(result.values());
    }

    /**
     * 处理祖级名称、id
     * @return
     */
    @PostMapping("/handlerAncestor")
    public AjaxResult handlerAncestor() {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        xmslContractListService.handlerAncestors();
        return AjaxResult.success();
    }

    /**
     * 4.1.4合同清单弹窗
     * @param queryVo
     * @return
     */
    @GetMapping("popUpWindows")
    public AjaxResult popUpWindows(ContractListQueryVo queryVo){
        List<XmslContractList> list = xmslContractListService.popUpWindows(queryVo);
        return AjaxResult.success(list);
    }

}
