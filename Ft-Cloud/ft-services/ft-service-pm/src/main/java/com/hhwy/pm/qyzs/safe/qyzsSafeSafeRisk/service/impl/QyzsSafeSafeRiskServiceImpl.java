package com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.service.impl;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.domain.SafeSafeRiskQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.service.IQyzsSafeSafeRiskService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-17 16:26:05
 * @remark
 */
@Service
public class QyzsSafeSafeRiskServiceImpl implements IQyzsSafeSafeRiskService {


    @Value("${gm.back-url}")
    private String gmUrl;

    public AjaxResult getQyzsSafeSafeRiskList(SafeSafeRiskQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsSafeSafeRisk/list?projectType={projectType}&wbsCode={wbsCode}&workUnit={workUnit}&riskLevel={riskLevel}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getProjectType(),queryVo.getWbsCode(),queryVo.getWorkUnit(),queryVo.getRiskLevel());
    }
}
