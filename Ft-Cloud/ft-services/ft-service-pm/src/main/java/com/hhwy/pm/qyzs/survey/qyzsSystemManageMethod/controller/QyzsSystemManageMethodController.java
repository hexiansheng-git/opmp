package com.hhwy.pm.qyzs.survey.qyzsSystemManageMethod.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.survey.qyzsSystemManageMethod.domain.SystemManageMethodQueryVo;
import com.hhwy.pm.qyzs.survey.qyzsSystemManageMethod.service.IQyzsSystemManageMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-14 16:41:14
 * @remark 勘察设计知识库-制度及管理方法库
 */
@Validated
@RestController
@RequestMapping("/qyzsSystemManageMethod")
public class QyzsSystemManageMethodController extends BaseController {

    @Autowired
    private IQyzsSystemManageMethodService qyzsSystemManageMethodService;

    /**
     * 制度及管理方法库台账
     * @param queryVo
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQyzsSystemManageMethodList(SystemManageMethodQueryVo queryVo) {
        return qyzsSystemManageMethodService.getQyzsSystemManageMethodList(queryVo);
    }


}
