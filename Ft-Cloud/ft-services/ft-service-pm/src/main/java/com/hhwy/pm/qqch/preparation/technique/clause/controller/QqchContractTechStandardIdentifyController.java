package com.hhwy.pm.qqch.preparation.technique.clause.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.clause.domain.QqchContractTechStandardIdentify;
import com.hhwy.pm.qqch.preparation.technique.clause.service.IQqchContractTechStandardIdentifyService;
import com.hhwy.utils.tree.TreeVO;
import com.hhwy.utils.validation.ValidationGroups;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-07 15:51:11
 * @remark 3.1.1合同执行技术标准识别
 */
@Validated
@RestController
@RequestMapping("/qqchContractTechStandardIdentify")
public class QqchContractTechStandardIdentifyController extends BaseController {

    @Autowired
    private IQqchContractTechStandardIdentifyService qqchContractTechStandardIdentifyService;

    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:remove")
    @PostMapping("/remove")
    public AjaxResult deleteQqchContractTechStandardIdentifyByPks(Long[] ids) {
        List<Long> qqchContractTechStandardIdentifyPkList = Arrays.asList(ids);
        return toAjax(qqchContractTechStandardIdentifyService
            .deleteQqchContractTechStandardIdentifyByPks(qqchContractTechStandardIdentifyPkList));
    }

    /**
     * 查询列表
     *
     * @param qqchContractTechStandardIdentifyParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:list")
    @GetMapping("/getTreeList")
    public AjaxResult getTreeList(
        @Validated(ValidationGroups.Select.class) @RequestBody QqchContractTechStandardIdentify qqchContractTechStandardIdentifyParam) {
        List<? extends TreeVO> treeList = qqchContractTechStandardIdentifyService
            .getTreeList(qqchContractTechStandardIdentifyParam);
        return AjaxResult.success(treeList);
    }

    /**
     * 批量保存
     *
     * @param qqchContractTechStandardIdentifyListParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchContractTechStandardIdentify:add")
    @PostMapping("/batchSave")
    public AjaxResult batchSave(
        @Validated(ValidationGroups.Save.class) @RequestBody List<QqchContractTechStandardIdentify> qqchContractTechStandardIdentifyListParam) {
        qqchContractTechStandardIdentifyService.batchSave(qqchContractTechStandardIdentifyListParam);
        return AjaxResult.success();
    }

}
