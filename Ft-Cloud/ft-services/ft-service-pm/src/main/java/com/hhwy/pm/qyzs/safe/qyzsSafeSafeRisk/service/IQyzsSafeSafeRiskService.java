package com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.service;


import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.domain.QyzsSafeSafeRisk;
import com.hhwy.pm.qyzs.safe.qyzsSafeSafeRisk.domain.SafeSafeRiskQueryVo;

import java.util.List;

/**
 * @author cjh
 * @date 2023-11-17 16:26:05
 * @remark
 */
public interface IQyzsSafeSafeRiskService {

    AjaxResult getQyzsSafeSafeRiskList(SafeSafeRiskQueryVo queryVo);

    List<QyzsSafeSafeRisk> getCommonListBy(QyzsSafeSafeRisk qyzsSafeSafeRisk);
}
