package com.hhwy.pm.xmsl.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBridgeStructure;
import com.hhwy.pm.xmsl.project.mapper.XmslProjectBridgeStructureMapper;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBridgeStructureService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author han
 * @date 2023-07-03 09:48:28
 * @remark 主要桥梁结构形式
 */
@Service
public class XmslProjectBridgeStructureServiceImpl implements IXmslProjectBridgeStructureService {

    @Autowired
    private XmslProjectBridgeStructureMapper xmslProjectBridgeStructureMapper;

                                                                                                                                                                                                                                                                                                                                        
    public XmslProjectBridgeStructure getProjectBridgeStructure(XmslProjectBridgeStructure xmslProjectBridgeStructure) {
        return xmslProjectBridgeStructureMapper.getProjectBridgeStructure(xmslProjectBridgeStructure);
    }

    public List<XmslProjectBridgeStructure> getProjectBridgeStructureList(XmslProjectBridgeStructure xmslProjectBridgeStructure) {
        return xmslProjectBridgeStructureMapper.getProjectBridgeStructureList(xmslProjectBridgeStructure);
    }

    @Transactional
    public int insertProjectBridgeStructure(XmslProjectBridgeStructure xmslProjectBridgeStructure) {
        xmslProjectBridgeStructure.setId(IdWorker.createId());
        xmslProjectBridgeStructure.setCreateUser(SecurityUtils.getUserName());
        xmslProjectBridgeStructure.setCreateTime(DateUtils.getNowDate());
        return xmslProjectBridgeStructureMapper.insertProjectBridgeStructure(xmslProjectBridgeStructure);
    }

    /**
     * 编辑数据
     * @param xmslProjectBridgeStructureList
     * @param xmslProjectBasicInfo
     */
    @Transactional
    public void editProjectBridgeStructureList(List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList, XmslProjectBasicInfo xmslProjectBasicInfo){
        List<XmslProjectBridgeStructure> insertList = new ArrayList<>();
        List<XmslProjectBridgeStructure> updateList = new ArrayList<>();
        for (XmslProjectBridgeStructure xmslProjectBridgeStructure : xmslProjectBridgeStructureList) {
            Long projectBridgeStructureId = xmslProjectBridgeStructure.getId();
            if(projectBridgeStructureId == null){
//                projectBridgeStructure.setProjectId(projectBasicInfo.getId());
//                projectBridgeStructure.setProjectBasicInfoId(projectBasicInfo.getId());
//                projectBridgeStructure.setProjectName(projectBasicInfo.getProjectName());
//                projectBridgeStructure.setId(IdWorker.createId());
//                projectBridgeStructure.setCreateUser(SecurityUtils.getUserName());
//                projectBridgeStructure.setCreateTime(DateUtils.getNowDate());
                insertList.add(xmslProjectBridgeStructure);
            }else{
//                projectBridgeStructure.setUpdateUser(SecurityUtils.getUserName());
//                projectBridgeStructure.setUpdateTime(DateUtils.getNowDate());
                updateList.add(xmslProjectBridgeStructure);
            }
        }
        if(insertList.size() > 0){
            this.insertProjectBridgeStructureList(insertList, xmslProjectBasicInfo);
        }
        if(updateList.size() > 0){
            this.updateProjectBridgeStructureList(updateList);
        }
    }

    /**
     * 批量插入
     * @param xmslProjectBridgeStructureList
     * @param xmslProjectBasicInfo
     * @return
     */
    @Transactional
    public int insertProjectBridgeStructureList(List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList, XmslProjectBasicInfo xmslProjectBasicInfo) {
        for (XmslProjectBridgeStructure xmslProjectBridgeStructure : xmslProjectBridgeStructureList) {
            xmslProjectBridgeStructure.setId(IdWorker.createId());
            xmslProjectBridgeStructure.setProjectBasicInfoId(xmslProjectBasicInfo.getId());
            xmslProjectBridgeStructure.setProjectId(xmslProjectBasicInfo.getProjectId());
            xmslProjectBridgeStructure.setProjectName(xmslProjectBasicInfo.getProjectName());
            xmslProjectBridgeStructure.setCreateUser(SecurityUtils.getUserName());
            xmslProjectBridgeStructure.setCreateTime(DateUtils.getNowDate());
        }
        return xmslProjectBridgeStructureMapper.insertProjectBridgeStructureList(xmslProjectBridgeStructureList);
    }

    @Transactional
    public int updateProjectBridgeStructure(XmslProjectBridgeStructure xmslProjectBridgeStructure) {
        xmslProjectBridgeStructure.setUpdateUser(SecurityUtils.getUserName());
        xmslProjectBridgeStructure.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectBridgeStructureMapper.updateProjectBridgeStructure(xmslProjectBridgeStructure);
    }

    @Transactional
    public int updateProjectBridgeStructureList(List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList) {
        for (XmslProjectBridgeStructure xmslProjectBridgeStructure : xmslProjectBridgeStructureList) {
            xmslProjectBridgeStructure.setUpdateUser(SecurityUtils.getUserName());
            xmslProjectBridgeStructure.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslProjectBridgeStructureMapper.updateProjectBridgeStructureList(xmslProjectBridgeStructureList);
    }
    
    @Transactional
    public int deleteProjectBridgeStructure(XmslProjectBridgeStructure xmslProjectBridgeStructure) {
        xmslProjectBridgeStructure.setUpdateUser(SecurityUtils.getUserName());
        xmslProjectBridgeStructure.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectBridgeStructureMapper.deleteProjectBridgeStructure(xmslProjectBridgeStructure);
    }

    @Transactional
    public int deleteProjectBridgeStructureByPks(List<Long> projectBridgeStructurePkList) {
        return xmslProjectBridgeStructureMapper.deleteProjectBridgeStructureByPks(projectBridgeStructurePkList);
    }
}
