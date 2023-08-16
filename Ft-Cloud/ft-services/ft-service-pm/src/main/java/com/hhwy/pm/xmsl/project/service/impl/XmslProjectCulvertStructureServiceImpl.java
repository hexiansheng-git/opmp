package com.hhwy.pm.xmsl.project.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.XmslProjectCulvertStructure;
import com.hhwy.pm.xmsl.project.mapper.XmslProjectCulvertStructureMapper;
import com.hhwy.pm.xmsl.project.service.IXmslProjectCulvertStructureService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:31
 * @remark 主要涵洞结构形式
 */
@Service
public class XmslProjectCulvertStructureServiceImpl implements IXmslProjectCulvertStructureService {

    @Autowired
    private XmslProjectCulvertStructureMapper xmslProjectCulvertStructureMapper;


    public List<XmslProjectCulvertStructure> getProjectCulvertStructureList(XmslProjectCulvertStructure xmslProjectCulvertStructure) {
        return xmslProjectCulvertStructureMapper.getProjectCulvertStructureList(xmslProjectCulvertStructure);
    }

    /**
     * 编辑数据
     * @param xmslProjectCulvertStructureList
     * @param xmslProjectBasicInfo
     */
    @Transactional
    public void editProjectCulvertStructureList(List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList, XmslProjectBasicInfo xmslProjectBasicInfo){
        //删除旧数据
        XmslProjectCulvertStructure xmslProjectCulvertStructure = new XmslProjectCulvertStructure();
        xmslProjectCulvertStructure.setProjectBasicInfoId(xmslProjectBasicInfo.getId());
        xmslProjectCulvertStructureMapper.deleteProjectCulvertStructure(xmslProjectCulvertStructure);

        //插入新数据
        if(!CollectionUtils.isEmpty(xmslProjectCulvertStructureList)){
            this.insertProjectCulvertStructureList(xmslProjectCulvertStructureList, xmslProjectBasicInfo);
        }
    }

    /**
     * 批量插入
     * @param xmslProjectCulvertStructureList
     * @param xmslProjectBasicInfo
     * @return
     */
    @Transactional
    public int insertProjectCulvertStructureList(List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList, XmslProjectBasicInfo xmslProjectBasicInfo) {
        for (XmslProjectCulvertStructure xmslProjectCulvertStructure : xmslProjectCulvertStructureList) {
            xmslProjectCulvertStructure.setId(IdWorker.createId());
            xmslProjectCulvertStructure.setProjectId(xmslProjectBasicInfo.getProjectId());
            xmslProjectCulvertStructure.setProjectBasicInfoId(xmslProjectBasicInfo.getId());
            xmslProjectCulvertStructure.setProjectName(xmslProjectBasicInfo.getProjectName());
            xmslProjectCulvertStructure.setRegionId(xmslProjectBasicInfo.getRegionId());
            xmslProjectCulvertStructure.setRegionName(xmslProjectBasicInfo.getRegionName());
            xmslProjectCulvertStructure.setDeptId(xmslProjectBasicInfo.getDeptId());
            xmslProjectCulvertStructure.setCreateUser(xmslProjectBasicInfo.getCreateUser());
            xmslProjectCulvertStructure.setCreateUserName(xmslProjectBasicInfo.getCreateUserName());
            xmslProjectCulvertStructure.setCreateTime(DateUtils.getNowDate());
        }
        return xmslProjectCulvertStructureMapper.insertProjectCulvertStructureList(xmslProjectCulvertStructureList);
    }
    
    @Transactional
    public int deleteProjectCulvertStructure(XmslProjectCulvertStructure xmslProjectCulvertStructure) {
        xmslProjectCulvertStructure.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        xmslProjectCulvertStructure.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectCulvertStructureMapper.deleteProjectCulvertStructure(xmslProjectCulvertStructure);
    }

    @Transactional
    public int deleteProjectCulvertStructureByPks(List<Long> projectCulvertStructurePkList) {
        return xmslProjectCulvertStructureMapper.deleteProjectCulvertStructureByPks(projectCulvertStructurePkList);
    }
}
