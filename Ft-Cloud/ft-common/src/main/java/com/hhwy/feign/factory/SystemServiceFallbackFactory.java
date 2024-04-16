package com.hhwy.feign.factory;

import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.country.CountryInfo;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Demo服务降级处理
 *
 * @author hhwy
 */
@Component
public class SystemServiceFallbackFactory implements FallbackFactory<SystemServiceApi> {
    private static final Logger log = LoggerFactory.getLogger(SystemServiceFallbackFactory.class);

    @Override
    public SystemServiceApi create(Throwable throwable) {
        log.error("系统服务调用失败:{}", throwable.getMessage());
        return new SystemServiceApi() {
            @Override
            public AjaxResult resolveDict(String dictType, String dictValue) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult reverseDict(String dictType, String dictLabel) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult dictType(String dictType) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult getQqchMenu(String name) {
                return null;
            }

            @Override
            public List<SysTenant> tenantList() {
                return new ArrayList<>();
            }

            @Override
            public AjaxResult selectCurrencyList(CurrencyInfo where) {
                // 这里的降级没啥用, 异常会被全局异常捕捉器捕捉并处理
                return AjaxResult.error("失败");
            }

            @Override
            public AjaxResult selectCountryInfoByNames(String name) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public List<CountryInfo> selectCountryInfoByCodes(String countryCodes) {
                return new ArrayList<>();
            }

            @Override
            public AjaxResult selectSysUserInfo(SysUser sysUser) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public List<SysUser> selectSysUserInfoList(SysUser sysUser) {
                return new ArrayList<>();
            }

            @Override
            public R<List<SysUser>> selectUserListByUsernames(String usernames) {
                return R.fail("请求失败:" + throwable.getMessage());
            }

            @Override
            public R<List<SysUser>> selectUserListByUsernames(String tenantKey, String usernames) {
                return R.fail("请求失败:" + throwable.getMessage());
            }

            @Override
            public AjaxResult selectPeriodByYear(@RequestBody PeriodInfo periodInfo){
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult selectAllPeriodByYear(String year) {
                return null;
            }

            /**
             * 根据日期和币种查询汇率
             *
             * @param map
             * @return
             */
            @Override
            public AjaxResult selectListRatePeriodByCodeAndCurrent(Map<String, String> map) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult addWarn(TWarn tWarn) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult addWarnNonGm(TWarn tWarn) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult insertTWarnList(List<TWarn> tWarnListParam) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public List<SysUser> selectUserInfoByUserNameAndTenant(Map map) {
                return new ArrayList<>();
            }

            @Override
            public List<SysMenu> getMenuId(String component, String tenantKey) {
                return new ArrayList<>();
            }

            @Override
            public List<CountryInfo> getCountryInfoList() {
                return null;
            }

            @Override
            public R publish(String clientId, String topic, String message) {
                return R.fail("请求失败：", throwable.getMessage());
            }

            @Override
            public R batchPublish(String clientIds, String topic, String message) {
                return R.fail("请求失败：", throwable.getMessage());
            }

            @Override
            public R broadcast(String topic, String message) {
                return R.fail("请求失败：", throwable.getMessage());
            }

            @Override
            public AjaxResult selectByRoleAndTenant(String[] roleKeyList, String tenantKey) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }
        };
    }
}
