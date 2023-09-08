package com.hhwy.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.feign.factory.SystemServiceFallbackFactory;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
     * 根据菜单名称 获取向下的树形结构，目前只支持第一层级
     */
    @GetMapping("/syspm/menu/qqch")
    AjaxResult getQqchMenu(@RequestParam("name") String name);

    /**
     * 获取所有租户
     * @return
     */
    @PostMapping("/syspm/tenantList")
    List<SysTenant> tenantList();

    @PostMapping("/currency/info/selectList")
    List<CurrencyInfo> selectCurrencyList(CurrencyInfo where);

    @GetMapping("/country/info/selectCountryInfoByNames")
    AjaxResult selectCountryInfoByNames(@RequestParam("name") String name);

    @GetMapping("/selfSysUser/selectSysUserInfo")
    AjaxResult selectSysUserInfo(@RequestBody SysUser sysUser);

    @PostMapping("/period/info/selectPeriodByYear")
    AjaxResult selectPeriodByYear(@RequestBody PeriodInfo periodInfo);
}
