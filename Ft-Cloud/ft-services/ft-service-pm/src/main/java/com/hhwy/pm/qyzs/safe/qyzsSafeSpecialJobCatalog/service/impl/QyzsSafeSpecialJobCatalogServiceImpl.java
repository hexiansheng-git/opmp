package com.hhwy.pm.qyzs.safe.qyzsSafeSpecialJobCatalog.service.impl;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeSpecialJobCatalog.domain.SafeSpecialJobCatalogQueryVo;
import com.hhwy.pm.qyzs.safe.qyzsSafeSpecialJobCatalog.service.IQyzsSafeSpecialJobCatalogService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * @author cjh
 * @date 2023-11-20 15:21:34
 * @remark
 */
@Service
public class QyzsSafeSpecialJobCatalogServiceImpl implements IQyzsSafeSpecialJobCatalogService {

    @Value("${gm.back-url}")
    private String gmUrl;

    public AjaxResult getQyzsSafeSpecialJobCatalogList(SafeSpecialJobCatalogQueryVo queryVo) {
        String url = gmUrl + "/gm/qyzsSafeSpecialJobCatalog/list?specialJobName={specialJobName}&specialJobContent={specialJobContent}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class, queryVo.getSpecialJobName(),queryVo.getSpecialJobContent());
    }
}
