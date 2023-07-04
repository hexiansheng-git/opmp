package com.hhwy.pm.xmsl.project.mapper;

import java.util.List;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBridgeStructure;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-03 09:48:28
 * @remark 主要桥梁结构形式
 */
@Repository
public interface XmslProjectBridgeStructureMapper {
                                                                                                                                                                                                                                                                                                                                        
    XmslProjectBridgeStructure getProjectBridgeStructure(XmslProjectBridgeStructure xmslProjectBridgeStructure);

    List<XmslProjectBridgeStructure> getProjectBridgeStructureList(XmslProjectBridgeStructure xmslProjectBridgeStructure);

    int insertProjectBridgeStructure(XmslProjectBridgeStructure xmslProjectBridgeStructure);

    int insertProjectBridgeStructureList(@Param("xmslProjectBridgeStructureList") List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList);

    int updateProjectBridgeStructure(XmslProjectBridgeStructure xmslProjectBridgeStructure);

    int updateProjectBridgeStructureList(@Param("list") List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList);
    
    int deleteProjectBridgeStructure(XmslProjectBridgeStructure xmslProjectBridgeStructure);

    int deleteProjectBridgeStructureByPks(@Param("projectBridgeStructurePkList") List<Long> projectBridgeStructurePkList);
}
