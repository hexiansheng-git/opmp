package com.hhwy.pm.xmsl.project.service;

import com.hhwy.pm.xmsl.project.domain.XmslProjectEngineeringAmount;
import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:34
 * @remark 主要工程数量
 */
public interface IXmslProjectEngineeringAmountService {
                                                                                                                                                                                                                                                                                                                
//    ProjectEngineeringAmount getProjectEngineeringAmount(ProjectEngineeringAmount projectEngineeringAmount);

//    List<ProjectEngineeringAmount> getProjectEngineeringAmountList(ProjectEngineeringAmount projectEngineeringAmount);

//    int insertProjectEngineeringAmount(ProjectEngineeringAmount projectEngineeringAmount);

//    int insertProjectEngineeringAmountList(List<ProjectEngineeringAmount> projectEngineeringAmountList);

//    int updateProjectEngineeringAmount(ProjectEngineeringAmount projectEngineeringAmount);

//    int updateProjectEngineeringAmountList(List<ProjectEngineeringAmount> projectEngineeringAmountList);
    
    int deleteProjectEngineeringAmount(XmslProjectEngineeringAmount xmslProjectEngineeringAmount);

    int deleteProjectEngineeringAmountByPks(List<Long> projectEngineeringAmountPkList);
}
