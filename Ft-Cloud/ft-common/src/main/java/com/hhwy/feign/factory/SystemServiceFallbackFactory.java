package com.hhwy.feign.factory;

import com.hhwy.common.core.domain.R;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.period.PeriodInfo;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.feign.service.SystemServiceApi;
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
            public List<CurrencyInfo> selectCurrencyList(CurrencyInfo where) {
                return new ArrayList<>();
            }

            @Override
            public AjaxResult selectCountryInfoByNames(String name) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public AjaxResult selectSysUserInfo(SysUser sysUser) {
                return AjaxResult.error("请求失败:",throwable.getMessage());
            }

            @Override
            public R<List<SysUser>> selectUserListByUsernames(String usernames) {
                return R.fail("请求失败:" + throwable.getMessage());
            }
            @Override
            public AjaxResult selectPeriodByYear(@RequestBody PeriodInfo periodInfo){
                return AjaxResult.error("请求失败:",throwable.getMessage());
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
                return null;
            }
        };
    }
}
