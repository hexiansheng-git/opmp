package com.hhwy.pm.xmsl.project.service;

import com.hhwy.pm.xmsl.project.domain.XmslProjectMaterialsAmount;
import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:36
 * @remark 主要材料数量
 */
public interface IXmslProjectMaterialsAmountService {

    List<XmslProjectMaterialsAmount> getProjectMaterialsAmountList(XmslProjectMaterialsAmount projectMaterialsAmount);

    int deleteProjectMaterialsAmount(XmslProjectMaterialsAmount xmslProjectMaterialsAmount);

    int deleteProjectMaterialsAmountByPks(List<Long> projectMaterialsAmountPkList);
}
