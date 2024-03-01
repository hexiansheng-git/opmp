package com.hhwy.sp.core.ziyuanku.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author lcf
 * @date 2024-02-26
 */
@RestController
@RequestMapping("/ziyuanku")
public class ZiyuanKuController {
    /**
     * 获取专家库数据
     *
     * @return
     */
    @PostMapping("/getData")
    public AjaxResult getData(){
        return AjaxResult.success();
    }

}
