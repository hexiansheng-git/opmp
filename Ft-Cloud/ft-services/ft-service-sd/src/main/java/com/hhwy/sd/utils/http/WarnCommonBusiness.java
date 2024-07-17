package com.hhwy.sd.utils.http;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjWarnConfig;
import com.hhwy.system.api.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class WarnCommonBusiness {

    static SystemServiceApi systemServiceApi = SpringUtils.getBean(SystemServiceApi.class);

    private static Logger logger= LoggerFactory.getLogger(WarnCommonBusiness.class);

    /*
     * 功能描述: 从总部获取预警配置信息
     * @param: url 请求url
     * @param: warnSubject 预警项(中文)
     * @return: 预警配置信息
     * 时间: 2024/4/2
     */
    public static List<SysUser> getSysUsers(String[] roleKeys, String tenantKey) {
        AjaxResult ajaxResult = new AjaxResult();
        if (StrUtil.isBlank(tenantKey)) {
            ajaxResult = systemServiceApi.selectByRole(roleKeys, null);
        }else {
            ajaxResult = systemServiceApi.selectByRoleAndTenant(roleKeys, tenantKey);
        }
        Integer code1 = (Integer) ajaxResult.get("code");
        Assert.isTrue(code1.equals(200), "获取用户列表失败");
        String userInfoStr = JSON.toJSONString(ajaxResult.get("data"));
        Assert.isTrue(StrUtil.isNotBlank(userInfoStr), "角色未绑定用户");
        return JSON.parseArray(userInfoStr, SysUser.class);
    }

    /*
     * 功能描述: 从总部获取预警配置信息
     * @param: url 请求url
     * @param: warnSubject 预警项(中文)
     * @return: 预警配置信息
     * 时间: 2024/4/2
     */
    public static KcsjWarnConfig getSgjsWarnConfig(String url, String warnSubject) {
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(headers);
        AjaxResult ajaxResul = RestTemplateUtils.get(url, httpEntity, AjaxResult.class, warnSubject);
        Integer code = (Integer) ajaxResul.get("code");
        Assert.isTrue(code.equals(200), "从总部获取预警配置信息失败");
        String warnInfo = JSON.toJSONString(ajaxResul.get("data"));
        Assert.isTrue(StrUtil.isNotBlank(warnInfo), "获取" + warnSubject + "预警配置无数据");
        KcsjWarnConfig config = JSON.parseObject(warnInfo, KcsjWarnConfig.class);
        if(null==config){
            return null;
        }
        return config;
    }

    /*
     * 功能描述: 处理预警消息
     * @param: warnContent 消息内容
     * @param: projectName 项目名
     * @param: warnSubject 预警项
     * @param: warnRule 预警规则
     * @return:
     * 时间: 2024/4/2
     */
    public static String warnMessageHandle(String warnContent, String projectName, String warnSubject, String warnRule) {
        logger.info("项目名称【{}】,预警项名称【{}】,预警规则【{}】",projectName,warnSubject,warnRule);
        String replace = warnContent.replace("【项目名称】", projectName)
                .replace("【预警项名称】", warnSubject).replace("【预警规则】", warnRule);
        logger.info("warnContent---【{}】,,,,replace---->【{}】",warnContent,replace);
        return replace;
    }
}
