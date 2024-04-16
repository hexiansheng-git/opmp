package com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.gm.wbs.service.ITWbsService;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.domain.QyzsSafeSafeRisk;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.domain.SafeSafeRiskQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.service.IQyzsSafeSafeRiskService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * @author cjh
 * @date 2023-11-17 16:26:05
 * @remark
 */
@Service
public class QyzsSafeSafeRiskServiceImpl implements IQyzsSafeSafeRiskService {

    @Autowired
    private ITWbsService tWbsService;

    @Value("${gm.back-url}")
    private String gmUrl;

    public AjaxResult getQyzsSafeSafeRiskList(SafeSafeRiskQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsSafeSafeRisk/list?projectType={projectType}&wbsCode={wbsCode}&workUnit={workUnit}&riskLevel={riskLevel}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        String projectType = tWbsService.getDefaultEngineeringType();
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, projectType,queryVo.getWbsCode(),queryVo.getWorkUnit(),queryVo.getRiskLevel());
    }

    @Override
    public List<QyzsSafeSafeRisk> getCommonListBy(QyzsSafeSafeRisk qyzsSafeSafeRisk) {
        String url = gmUrl + "/gm/qyzsSafeSafeRisk/getCommonListBy";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        String projectType = tWbsService.getDefaultEngineeringType();
        qyzsSafeSafeRisk.setProjectType(projectType);
        HttpEntity<QyzsSafeSafeRisk> httpEntity = new HttpEntity<>(qyzsSafeSafeRisk,headers);
        AjaxResult result = RestTemplateUtils.post(url, httpEntity, AjaxResult.class);
        Object data = result.get("data");
        List<QyzsSafeSafeRisk> safeRiskList = JSONObject.parseArray(JSON.toJSONString(data), QyzsSafeSafeRisk.class);
        return safeRiskList;
    }
}
