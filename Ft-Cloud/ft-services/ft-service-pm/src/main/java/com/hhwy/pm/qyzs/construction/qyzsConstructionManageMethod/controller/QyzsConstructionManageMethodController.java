package com.hhwy.pm.qyzs.construction.qyzsConstructionManageMethod.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.construction.qyzsConstructionManageMethod.domain.ConstructionManageMethodQueryVo;
import com.hhwy.pm.qyzs.construction.qyzsConstructionManageMethod.service.IQyzsConstructionManageMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author cjh
 * @date 2023-11-14 16:41:14
 * @remark 施工技术知识库-制度及管理方法库
 */
@Validated
@RestController
@RequestMapping("/qyzsConstructionManageMethod")
public class QyzsConstructionManageMethodController extends BaseController {

    @Autowired
    private IQyzsConstructionManageMethodService qyzsConstructionManageMethodService;


    /**
     * 制度及管理方法库台账
     * @param queryVo
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQyzsConstructionManageMethodList(ConstructionManageMethodQueryVo queryVo) {
        return qyzsConstructionManageMethodService.getQyzsConstructionManageMethodList(queryVo);
    }
}
