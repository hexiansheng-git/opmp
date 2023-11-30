package com.hhwy.pm.qyzs.safe.qyzsSafeSpecialJobCatalog.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeSpecialJobCatalog.domain.SafeSpecialJobCatalogQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeSpecialJobCatalog.service.IQyzsSafeSpecialJobCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-20 15:21:34
 * @remark 安全知识库-特种作业目录
 */
@Validated
@RestController
@RequestMapping("/qyzsSafeSpecialJobCatalog")
public class QyzsSafeSpecialJobCatalogController extends BaseController {

    @Autowired
    private IQyzsSafeSpecialJobCatalogService qyzsSafeSpecialJobCatalogService;


    @GetMapping("/list")
    public AjaxResult getQyzsSafeSpecialJobCatalogList(SafeSpecialJobCatalogQueryVo queryVo) {
        return qyzsSafeSpecialJobCatalogService.getQyzsSafeSpecialJobCatalogList(queryVo);
    }
}
