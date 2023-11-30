package com.hhwy.pm.qyzs.safe.qyzsSafeSpecialJobCatalog.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeSpecialJobCatalog.domain.SafeSpecialJobCatalogQueryVo;

/**
 * @author cjh
 * @date 2023-11-20 15:21:34
 * @remark
 */
public interface IQyzsSafeSpecialJobCatalogService {


    AjaxResult getQyzsSafeSpecialJobCatalogList(SafeSpecialJobCatalogQueryVo queryVo);

}
