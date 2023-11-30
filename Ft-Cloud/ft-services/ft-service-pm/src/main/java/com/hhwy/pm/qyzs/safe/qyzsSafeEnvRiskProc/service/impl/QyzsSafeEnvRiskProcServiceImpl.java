package com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.service.impl;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.domain.SafeEnvRiskProcQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeEnvRiskProc.service.IQyzsSafeEnvRiskProcService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-17 16:25:55
 * @remark
 */
@Service
public class QyzsSafeEnvRiskProcServiceImpl implements IQyzsSafeEnvRiskProcService {

    @Value("${gm.back-url}")
    private String gmUrl;


    public AjaxResult getQyzsSafeEnvRiskProcList(SafeEnvRiskProcQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsSafeEnvRiskProc/getList";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        MultiValueMap<String,String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("projectType",queryVo.getProjectType());
        multiValueMap.add("wbsCode",queryVo.getWbsCode());
        multiValueMap.add("procName",queryVo.getProcName());
        multiValueMap.add("workName",queryVo.getWorkName());
        multiValueMap.add("frequency",queryVo.getFrequency());
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(multiValueMap,headers);
        return RestTemplateUtils.post(url, httpEntity, AjaxResult.class);
    }
}
