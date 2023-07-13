package com.hhwy.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.factory.SystemServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文件服务
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-system", fallbackFactory = SystemServiceFallbackFactory.class)
public interface SystemServiceApi {

    //查询字典项，导出使用
    @GetMapping(value = "/syspm/resolveDict")
    AjaxResult resolveDict(@RequestParam("dictType") String dictType,  @RequestParam("dictValue")  String dictValue);
    //查询字典项，导入使用,
    @GetMapping(value = "/syspm/reverseDict")
    AjaxResult reverseDict(@RequestParam("dictType") String dictType,  @RequestParam("dictLabel")  String dictLabel);

    /**
     * 字典项数据获取
     * @param dictType
     * @return com.hhwy.common.core.web.domain.AjaxResult
     */
    @GetMapping("/syspm/typeData")
    AjaxResult dictType(@RequestParam("dictType") String dictType);

    /**
     * 前期策划获取菜单信息
     */
    @GetMapping("/syspm/menu/qqch")
    AjaxResult getQqchMenu();
}
