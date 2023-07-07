package com.hhwy.pm.xmsl.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
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

    @Override
    public List<XmslProjectBridgeStructure> getProjectBridgeStructureList(XmslProjectBridgeStructure xmslProjectBridgeStructure) {
        return xmslProjectBridgeStructureMapper.getProjectBridgeStructureList(xmslProjectBridgeStructure);
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
                insertList.add(xmslProjectBridgeStructure);
            }else{
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
            xmslProjectBridgeStructure.setRegionId(xmslProjectBasicInfo.getRegionId());
            xmslProjectBridgeStructure.setRegionName(xmslProjectBasicInfo.getRegionName());
            xmslProjectBridgeStructure.setDeptId(xmslProjectBasicInfo.getDeptId());
            xmslProjectBridgeStructure.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslProjectBridgeStructure.setCreateUserName(SecurityUtils.getUserName());
            xmslProjectBridgeStructure.setCreateTime(DateUtils.getNowDate());
        }
        return xmslProjectBridgeStructureMapper.insertProjectBridgeStructureList(xmslProjectBridgeStructureList);
    }

    @Transactional
    public int updateProjectBridgeStructureList(List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList) {
        for (XmslProjectBridgeStructure xmslProjectBridgeStructure : xmslProjectBridgeStructureList) {
            xmslProjectBridgeStructure.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslProjectBridgeStructure.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslProjectBridgeStructureMapper.updateProjectBridgeStructureList(xmslProjectBridgeStructureList);
    }

    @Override
    @Transactional
    public int deleteProjectBridgeStructure(XmslProjectBridgeStructure xmslProjectBridgeStructure) {
        xmslProjectBridgeStructure.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        xmslProjectBridgeStructure.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectBridgeStructureMapper.deleteProjectBridgeStructure(xmslProjectBridgeStructure);
    }

    @Override
    @Transactional
    public int deleteProjectBridgeStructureByPks(List<Long> projectBridgeStructurePkList) {
        return xmslProjectBridgeStructureMapper.deleteProjectBridgeStructureByPks(projectBridgeStructurePkList);
    }
}
