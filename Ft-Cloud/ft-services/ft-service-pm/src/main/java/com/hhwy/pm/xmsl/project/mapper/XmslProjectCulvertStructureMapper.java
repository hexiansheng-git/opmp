package com.hhwy.pm.xmsl.project.mapper;

import java.util.List;
import com.hhwy.pm.xmsl.project.domain.XmslProjectCulvertStructure;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-03 09:48:31
 * @remark 主要涵洞结构形式
 */
@Repository
public interface XmslProjectCulvertStructureMapper {
                                                                                                                                                                                                                                                                                                                                        
    XmslProjectCulvertStructure getProjectCulvertStructure(XmslProjectCulvertStructure xmslProjectCulvertStructure);

    List<XmslProjectCulvertStructure> getProjectCulvertStructureList(XmslProjectCulvertStructure xmslProjectCulvertStructure);

    int insertProjectCulvertStructure(XmslProjectCulvertStructure xmslProjectCulvertStructure);

    int insertProjectCulvertStructureList(@Param("xmslProjectCulvertStructureList") List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList);

    int updateProjectCulvertStructure(XmslProjectCulvertStructure xmslProjectCulvertStructure);

    int updateProjectCulvertStructureList(@Param("list") List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList);
    
    int deleteProjectCulvertStructure(XmslProjectCulvertStructure xmslProjectCulvertStructure);

    int deleteProjectCulvertStructureByPks(@Param("projectCulvertStructurePkList") List<Long> projectCulvertStructurePkList);
}
