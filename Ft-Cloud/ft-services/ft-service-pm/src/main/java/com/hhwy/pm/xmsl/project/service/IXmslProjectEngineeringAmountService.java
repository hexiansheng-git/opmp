package com.hhwy.pm.xmsl.project.service;

import com.hhwy.pm.xmsl.project.domain.XmslProjectEngineeringAmount;
import com.hhwy.pm.xmsl.project.domain.vo.XmslProjectEngineeringAmountExportVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:34
 * @remark 主要工程数量
 */
public interface IXmslProjectEngineeringAmountService {

    List<XmslProjectEngineeringAmount> getProjectEngineeringAmountList(XmslProjectEngineeringAmount projectEngineeringAmount);

    int deleteProjectEngineeringAmount(XmslProjectEngineeringAmount xmslProjectEngineeringAmount);

    int deleteProjectEngineeringAmountByPks(List<Long> projectEngineeringAmountPkList);

    List<XmslProjectEngineeringAmountExportVo> getProjectEngineeringAmountExportVoList(XmslProjectEngineeringAmount projectEngineeringAmountParam);
}
