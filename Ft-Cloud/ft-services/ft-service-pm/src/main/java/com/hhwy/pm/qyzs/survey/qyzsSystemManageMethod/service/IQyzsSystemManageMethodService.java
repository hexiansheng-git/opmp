package com.hhwy.pm.qyzs.survey.qyzsSystemManageMethod.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.survey.qyzsSystemManageMethod.domain.SystemManageMethodQueryVo;

/**
 * @author cjh
 * @date 2023-11-14 16:41:14
 * @remark
 */
public interface IQyzsSystemManageMethodService {

    AjaxResult getQyzsSystemManageMethodList(SystemManageMethodQueryVo queryVo);
}
