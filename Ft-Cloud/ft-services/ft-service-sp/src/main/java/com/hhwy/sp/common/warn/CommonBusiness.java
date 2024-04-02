package com.hhwy.sp.common.warn;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsWarnConfig;
import com.hhwy.sp.utils.http.HttpHeadersUtils;
import com.hhwy.sp.utils.http.RestTemplateUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class CommonBusiness {

    /*
     * 功能描述: 从总部获取预警配置信息
     * @param: url 请求url
     * @param: warnSubject 预警项(中文)
     * @return: 预警配置信息
     * 时间: 2024/4/2
     */
    public static SgjsWarnConfig getSgjsWarnConfig(String url, String warnSubject) {
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(headers);
        AjaxResult ajaxResul = RestTemplateUtils.get(url, httpEntity, AjaxResult.class, warnSubject);
        Integer code = (Integer) ajaxResul.get("code");
        Assert.isTrue(code.equals(200), "从总部获取预警配置信息失败");
        String warnInfo = JSON.toJSONString(ajaxResul.get("data"));
        Assert.isTrue(StrUtil.isNotBlank(warnInfo), "获取" + warnSubject + "预警配置无数据");
        List<com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsWarnConfig> sgjsWarnConfigs = JSON.parseArray(warnInfo, SgjsWarnConfig.class);
        if (CollUtil.isEmpty(sgjsWarnConfigs)) return null;
        return sgjsWarnConfigs.get(0);
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
        return warnContent.replace("【项目名称】", projectName)
                .replace("【预警项名称】", warnSubject).replace("【预警规则】", warnRule);
    }
}
