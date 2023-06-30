package com.hhwy.pm.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <br>描 述： HelloWorld
 * <br>创 建 人：jzq
 * <br>创建时间：2021/11/1 13:53
 * <br>修改备注：无
 * <br>版本：1.0.0
 */

@RestController
@RequestMapping("/hello-world")
public class HelloWorldController extends BaseController {

    /**
     * 测试
     */
    @GetMapping("/test")
    public AjaxResult test() {
        return AjaxResult.success("操作成功","祝您使用愉快！");
    }
}
