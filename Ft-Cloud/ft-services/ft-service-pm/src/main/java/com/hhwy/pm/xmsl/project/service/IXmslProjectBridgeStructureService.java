package com.hhwy.pm.xmsl.project.service;

import com.hhwy.pm.xmsl.project.domain.XmslProjectBridgeStructure;

import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:28
 * @remark 主要桥梁结构形式
 */
public interface IXmslProjectBridgeStructureService {
                                                                                                                                                                                                                                                                                                                                        
//    ProjectBridgeStructure getProjectBridgeStructure(ProjectBridgeStructure projectBridgeStructure);

    List<XmslProjectBridgeStructure> getProjectBridgeStructureList(XmslProjectBridgeStructure xmslProjectBridgeStructure);

//    int insertProjectBridgeStructure(ProjectBridgeStructure projectBridgeStructure);

//    int insertProjectBridgeStructureList(List<ProjectBridgeStructure> projectBridgeStructureList);

//    int updateProjectBridgeStructure(ProjectBridgeStructure projectBridgeStructure);

//    int updateProjectBridgeStructureList(List<ProjectBridgeStructure> projectBridgeStructureList);
    
    int deleteProjectBridgeStructure(XmslProjectBridgeStructure xmslProjectBridgeStructure);

    int deleteProjectBridgeStructureByPks(List<Long> projectBridgeStructurePkList);
}
