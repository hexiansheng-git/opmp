package com.hhwy.pm.xmsl.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.XmslProjectCulvertStructure;
import com.hhwy.pm.xmsl.project.mapper.XmslProjectCulvertStructureMapper;
import com.hhwy.pm.xmsl.project.service.IXmslProjectCulvertStructureService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author han
 * @date 2023-07-03 09:48:31
 * @remark 主要涵洞结构形式
 */
@Service
public class XmslProjectCulvertStructureServiceImpl implements IXmslProjectCulvertStructureService {

    @Autowired
    private XmslProjectCulvertStructureMapper xmslProjectCulvertStructureMapper;

                                                                                                                                                                                                                                                                                                                                        
    public XmslProjectCulvertStructure getProjectCulvertStructure(XmslProjectCulvertStructure xmslProjectCulvertStructure) {
        return xmslProjectCulvertStructureMapper.getProjectCulvertStructure(xmslProjectCulvertStructure);
    }

    public List<XmslProjectCulvertStructure> getProjectCulvertStructureList(XmslProjectCulvertStructure xmslProjectCulvertStructure) {
        return xmslProjectCulvertStructureMapper.getProjectCulvertStructureList(xmslProjectCulvertStructure);
    }

    @Transactional
    public int insertProjectCulvertStructure(XmslProjectCulvertStructure xmslProjectCulvertStructure) {
        xmslProjectCulvertStructure.setId(IdWorker.createId());
        xmslProjectCulvertStructure.setCreateUser(SecurityUtils.getUserName());
        xmslProjectCulvertStructure.setCreateTime(DateUtils.getNowDate());
        return xmslProjectCulvertStructureMapper.insertProjectCulvertStructure(xmslProjectCulvertStructure);
    }

    /**
     * 编辑数据
     * @param xmslProjectCulvertStructureList
     * @param xmslProjectBasicInfo
     */
    @Transactional
    public void editProjectCulvertStructureList(List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList, XmslProjectBasicInfo xmslProjectBasicInfo){
        List<XmslProjectCulvertStructure> insertList = new ArrayList<>();
        List<XmslProjectCulvertStructure> updateList = new ArrayList<>();
        for (XmslProjectCulvertStructure xmslProjectCulvertStructure : xmslProjectCulvertStructureList) {
            Long projectCulvertStructureId = xmslProjectCulvertStructure.getId();
            if(projectCulvertStructureId == null){
//                projectCulvertStructure.setId(IdWorker.createId());
//                projectCulvertStructure.setProjectId(projectBasicInfo.getProjectId());
//                projectCulvertStructure.setProjectBasicInfoId(projectBasicInfo.getId());
//                projectCulvertStructure.setProjectName(projectBasicInfo.getProjectName());
//                projectCulvertStructure.setCreateUser(SecurityUtils.getUserName());
//                projectCulvertStructure.setCreateTime(DateUtils.getNowDate());
                insertList.add(xmslProjectCulvertStructure);
            }else{
//                projectCulvertStructure.setUpdateUser(SecurityUtils.getUserName());
//                projectCulvertStructure.setUpdateTime(DateUtils.getNowDate());
                updateList.add(xmslProjectCulvertStructure);
            }
        }
        if(insertList.size() > 0){
            this.insertProjectCulvertStructureList(insertList, xmslProjectBasicInfo);
        }
        if(updateList.size() > 0){
            this.updateProjectCulvertStructureList(updateList);
        }
    }

    @Transactional
    public int insertProjectCulvertStructureList(List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList, XmslProjectBasicInfo xmslProjectBasicInfo) {
        for (XmslProjectCulvertStructure xmslProjectCulvertStructure : xmslProjectCulvertStructureList) {
            xmslProjectCulvertStructure.setId(IdWorker.createId());
            xmslProjectCulvertStructure.setProjectId(xmslProjectBasicInfo.getProjectId());
            xmslProjectCulvertStructure.setProjectBasicInfoId(xmslProjectBasicInfo.getId());
            xmslProjectCulvertStructure.setProjectName(xmslProjectBasicInfo.getProjectName());
            xmslProjectCulvertStructure.setCreateUser(SecurityUtils.getUserName());
            xmslProjectCulvertStructure.setCreateTime(DateUtils.getNowDate());
        }
        return xmslProjectCulvertStructureMapper.insertProjectCulvertStructureList(xmslProjectCulvertStructureList);
    }

    @Transactional
    public int updateProjectCulvertStructure(XmslProjectCulvertStructure xmslProjectCulvertStructure) {
        xmslProjectCulvertStructure.setUpdateUser(SecurityUtils.getUserName());
        xmslProjectCulvertStructure.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectCulvertStructureMapper.updateProjectCulvertStructure(xmslProjectCulvertStructure);
    }

    @Transactional
    public int updateProjectCulvertStructureList(List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList) {
        for (XmslProjectCulvertStructure xmslProjectCulvertStructure : xmslProjectCulvertStructureList) {
            xmslProjectCulvertStructure.setUpdateUser(SecurityUtils.getUserName());
            xmslProjectCulvertStructure.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslProjectCulvertStructureMapper.updateProjectCulvertStructureList(xmslProjectCulvertStructureList);
    }
    
    @Transactional
    public int deleteProjectCulvertStructure(XmslProjectCulvertStructure xmslProjectCulvertStructure) {
        xmslProjectCulvertStructure.setUpdateUser(SecurityUtils.getUserName());
        xmslProjectCulvertStructure.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectCulvertStructureMapper.deleteProjectCulvertStructure(xmslProjectCulvertStructure);
    }

    @Transactional
    public int deleteProjectCulvertStructureByPks(List<Long> projectCulvertStructurePkList) {
        return xmslProjectCulvertStructureMapper.deleteProjectCulvertStructureByPks(projectCulvertStructurePkList);
    }
}
