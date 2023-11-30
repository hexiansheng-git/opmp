package com.hhwy.pm.qyzs.finance.qyzsFinanceTaxItemRate.service.impl;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxItemRate.domain.FinanceTaxItemRateQueryVo;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxItemRate.service.IQyzsFinanceTaxItemRateService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-27 16:01:25
 * @remark
 */
@Service
public class QyzsFinanceTaxItemRateServiceImpl implements IQyzsFinanceTaxItemRateService {

    @Value("${gm.back-url}")
    private String gmUrl;

    public AjaxResult getQyzsFinanceTaxItemRateList(FinanceTaxItemRateQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsFinanceTaxItemRate/list?countryCode={countryCode}&taxesCategories={taxesCategories}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getCountryCode(), queryVo.getTaxesCategories());
    }
}
