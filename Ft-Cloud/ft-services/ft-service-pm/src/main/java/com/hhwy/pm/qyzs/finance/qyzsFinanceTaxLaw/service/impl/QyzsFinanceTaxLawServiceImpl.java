package com.hhwy.pm.qyzs.finance.qyzsFinanceTaxLaw.service.impl;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxLaw.domain.FinanceTaxLawQueryVo;
import com.hhwy.pm.qyzs.finance.qyzsFinanceTaxLaw.service.IQyzsFinanceTaxLawService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-27 16:01:30
 * @remark
 */
@Service
public class QyzsFinanceTaxLawServiceImpl implements IQyzsFinanceTaxLawService {

    @Value("${gm.back-url}")
    private String gmUrl;


    public AjaxResult getQyzsFinanceTaxLawList(FinanceTaxLawQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsFinanceTaxLaw/list?countryCode={countryCode}&taxLawChnName={taxLawChnName}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getCountryCode(), queryVo.getTaxLawChnName());
    }
}
