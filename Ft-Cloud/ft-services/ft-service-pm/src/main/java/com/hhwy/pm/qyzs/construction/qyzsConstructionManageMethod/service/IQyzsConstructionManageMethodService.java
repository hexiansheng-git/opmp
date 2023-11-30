package com.hhwy.pm.qyzs.construction.qyzsConstructionManageMethod.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.construction.qyzsConstructionManageMethod.domain.ConstructionManageMethodQueryVo;

/**
 * @author cjh
 * @date 2023-11-14 16:41:14
 * @remark
 */
public interface IQyzsConstructionManageMethodService {

    AjaxResult getQyzsConstructionManageMethodList(ConstructionManageMethodQueryVo queryVo);
}
