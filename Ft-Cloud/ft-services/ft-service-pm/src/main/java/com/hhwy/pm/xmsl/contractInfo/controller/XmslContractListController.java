package com.hhwy.pm.xmsl.contractInfo.controller;

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
import com.hhwy.utils.excelUtil.ExcelUtilByTemplate;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<ImportXmslContractListVo> dateList = ListTreeUtil.formatTree(importXmslContractListVos, o -> o.getParentInnerCode()==null, (r, n) -> r.getInnerCode().equals(n.getParentInnerCode()), ImportXmslContractListVo::getChildren, ImportXmslContractListVo::setChildren);
            return AjaxResult.success(dateList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
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
