package com.hhwy.feign.service;

import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.feign.factory.SystemServiceFallbackFactory;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /**
     * 不要这样返回 一定要返回AjaxResult. 因为发生异常的时候,会被全局异常处理器拦截并返回AjaxResult.error();
     * @param where
     * @return
     */
//    @PostMapping("/currency/info/selectList")
//    List<CurrencyInfo> selectCurrencyList(CurrencyInfo where);

    @PostMapping("/currency/info/getList")
    AjaxResult selectCurrencyList(CurrencyInfo where);

    @GetMapping("/country/info/selectCountryInfoByNames")
    AjaxResult selectCountryInfoByNames(@RequestParam("name") String name);

    @GetMapping("/country/info/selectCountryInfoByCodes")
    List<CountryInfo> selectCountryInfoByCodes(@RequestParam("countryCodes") String countryCodes);



    @GetMapping("/selfSysUser/selectSysUserInfo")
    AjaxResult selectSysUserInfo(@RequestBody SysUser sysUser);



    @GetMapping("/selfSysUser/selectSysUserInfoList")
    List<SysUser> selectSysUserInfoList(@RequestBody SysUser sysUser);


    /**
     * 根据用户名获取信息
     * @param usernames
     * @return
     */
    @GetMapping({"/user/selectUserListByUsernames/{usernames}"})
    R<List<SysUser>> selectUserListByUsernames(@PathVariable("usernames") String usernames);
    
    @PostMapping("/period/info/selectPeriodByYear")
    AjaxResult selectPeriodByYear(@RequestBody PeriodInfo periodInfo);

    @GetMapping( "/period/info/selectAllPeriodByYear")
    AjaxResult selectAllPeriodByYear(@RequestParam("year") String year);
    /**
     * 根据日期和币种查询汇率
     *
     * @param map
     * @return
     */
    @PostMapping("/periodCurrency/periodCurrency/selectListRatePeriodByCodeAndCurrent")
    AjaxResult selectListRatePeriodByCodeAndCurrent(@RequestBody Map<String,String> map);

    /**
     * 发送预警
     * @param tWarn
     * @return
     */
    @PostMapping("/tWarn/addWarn")
    AjaxResult addWarn(@RequestBody TWarn tWarn);

    /**
     * 批量新增预警
     *
     * @param tWarnListParam
     * @return
     */
    @PostMapping("/tWarn/batchAdd")
    AjaxResult insertTWarnList(@RequestBody List<TWarn> tWarnListParam);

    /**
     * 根据用户名，租户标识获取用户信息
     * @param usernames 登录账号，多个逗号分割
     * @param tenanKey 租户标识
     * @return
     */
    @GetMapping({"/selfSysUser/selectUserInfoByUserNameAndTenant"})
    List<SysUser> selectUserInfoByUserNameAndTenant(Map map);

    @GetMapping({"/syspm/getMenuId"})
    List<SysMenu> getMenuId(@RequestParam("component") String component, @RequestParam("tenantKey") String tenantKey);

    @GetMapping("/country/info/getCountryInfoList")
    List<CountryInfo> getCountryInfoList();

    /***
     * 功能描述: 发送消息给某人
     * @param clientId
     * @param topic 监听主体，默认"system"
     * @param message 消息体
     */
    @GetMapping("/notify/publish/{clientId}")
    R publish(@PathVariable("clientId")String clientId, @RequestParam("topic")String topic, @RequestParam("message")String message);

    /***
     * 功能描述: 发送消息给某些人
     * @param clientIds 被通知的用户集，逗号分割
     * @param topic 监听主体，默认"system"
     * @param message 消息体
     */
    @GetMapping("/notify/batchPublish")
    R batchPublish(@RequestParam("clientIds")String clientIds, @RequestParam("topic")String topic, @RequestParam("message")String message);

    /***
     * 功能描述: 发送消息给所有人
     * @param topic 监听主体，默认"system"
     * @param message 消息体
     */
    @GetMapping("/notify/boradcast")
    R broadcast(@RequestParam("topic")String topic, @RequestParam("message")String message);

    /**
     * 功能描述: 获取给定角色和租户下的所有用户
     * @param roleKeyList 角色集  role key
     * @param tenantKey 租户
     * 作者: fsd
     * 时间: 2024/2/4
     */
    @PostMapping("/tWarn/selectByRoleAndTenant")
    AjaxResult selectByRoleAndTenant(@RequestParam(value = "roleKeyList") String[] roleKeyList, @RequestParam(value = "tenantKey", required = false) String tenantKey);
}
