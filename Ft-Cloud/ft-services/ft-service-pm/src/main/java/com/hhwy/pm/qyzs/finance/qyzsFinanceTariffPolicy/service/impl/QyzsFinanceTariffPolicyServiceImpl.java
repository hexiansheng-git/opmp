package com.hhwy.pm.qyzs.finance.qyzsFinanceTariffPolicy.service.impl;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTariffPolicy.domain.FinanceTariffPolicyQueryVo;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTariffPolicy.service.IQyzsFinanceTariffPolicyService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-27 16:41:26
 * @remark
 */
@Service
public class QyzsFinanceTariffPolicyServiceImpl implements IQyzsFinanceTariffPolicyService {

    @Value("${gm.back-url}")
    private String gmUrl;


    public AjaxResult getQyzsFinanceTariffPolicyList(FinanceTariffPolicyQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsFinanceTariffPolicy/list?countryCode={countryCode}&name={name}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getCountryCode(), queryVo.getName());
    }
}
