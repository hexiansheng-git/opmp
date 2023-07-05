package com.hhwy.pm.xmsl.project.service;

import com.hhwy.pm.xmsl.project.domain.XmslProjectBridgeStructure;

import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:28
 * @remark 主要桥梁结构形式
 */
public interface IXmslProjectBridgeStructureService {

    List<XmslProjectBridgeStructure> getProjectBridgeStructureList(XmslProjectBridgeStructure xmslProjectBridgeStructure);

    int deleteProjectBridgeStructure(XmslProjectBridgeStructure xmslProjectBridgeStructure);

    int deleteProjectBridgeStructureByPks(List<Long> projectBridgeStructurePkList);
}
