package com.hhwy.pm.qyzs.safe.qyzsSafeOrganPost.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeOrganPost.domain.SafeOrganPostQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeOrganPost.service.IQyzsSafeOrganPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cjh
 * @date 2023-11-17 16:26:00
 * @remark 安全知识库-安全组织岗位职责库
 */
@Validated
@RestController
@RequestMapping("/qyzsSafeOrganPost")
public class QyzsSafeOrganPostController extends BaseController {

    @Autowired
    private IQyzsSafeOrganPostService qyzsSafeOrganPostService;


    @GetMapping("/list")
    public AjaxResult getQyzsSafeOrganPostList(SafeOrganPostQueryVo queryVo) {
        return qyzsSafeOrganPostService.getQyzsSafeOrganPostList(queryVo);
    }
}
