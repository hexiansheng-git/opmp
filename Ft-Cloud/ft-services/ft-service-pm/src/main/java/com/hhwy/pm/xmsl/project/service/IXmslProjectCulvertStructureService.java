package com.hhwy.pm.xmsl.project.service;

import com.hhwy.pm.xmsl.project.domain.XmslProjectCulvertStructure;

import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:31
 * @remark 主要涵洞结构形式
 */
public interface IXmslProjectCulvertStructureService {
                                                                                                                                                                                                                                                                                                                                        
//    ProjectCulvertStructure getProjectCulvertStructure(ProjectCulvertStructure projectCulvertStructure);

//    List<ProjectCulvertStructure> getProjectCulvertStructureList(ProjectCulvertStructure projectCulvertStructure);

//    int insertProjectCulvertStructure(ProjectCulvertStructure projectCulvertStructure);

//    int insertProjectCulvertStructureList(List<ProjectCulvertStructure> projectCulvertStructureList);

//    int updateProjectCulvertStructure(ProjectCulvertStructure projectCulvertStructure);

//    int updateProjectCulvertStructureList(List<ProjectCulvertStructure> projectCulvertStructureList);
    
    int deleteProjectCulvertStructure(XmslProjectCulvertStructure xmslProjectCulvertStructure);

    int deleteProjectCulvertStructureByPks(List<Long> projectCulvertStructurePkList);
}
