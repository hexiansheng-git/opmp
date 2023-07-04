package com.hhwy.pm.xmsl.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.pm.xmsl.project.domain.*;
import com.hhwy.pm.xmsl.project.mapper.*;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author han
 * @date 2023-07-03 09:48:24
 * @remark 项目基本信息
 */
@Service
public class XmslProjectBasicInfoServiceImpl implements IXmslProjectBasicInfoService {

    @Autowired
    private XmslProjectBasicInfoMapper xmslProjectBasicInfoMapper;

    @Autowired
    private XmslProjectBridgeStructureServiceImpl projectBridgeStructureService;

    @Autowired
    private XmslProjectCulvertStructureServiceImpl projectCulvertStructureService;

    @Autowired
    private XmslProjectEngineeringAmountServiceImpl projectEngineeringAmountService;

    @Autowired
    private XmslProjectMaterialsAmountServiceImpl projectMaterialsAmountService;

    @Autowired
    private XmslProjectBridgeStructureMapper xmslProjectBridgeStructureMapper;

    @Autowired
    private XmslProjectCulvertStructureMapper xmslProjectCulvertStructureMapper;

    @Autowired
    private XmslProjectEngineeringAmountMapper xmslProjectEngineeringAmountMapper;

    @Autowired
    private XmslProjectMaterialsAmountMapper xmslProjectMaterialsAmountMapper;

    /**
     * 根据id获取项目基本信息
     * @param id
     * @return
     */
    @Override
    public XmslProjectBasicInfo getProjectBasicInfoById(Long id) {
        //项目基本信息
        XmslProjectBasicInfo xmslProjectBasicInfo = new XmslProjectBasicInfo();
        xmslProjectBasicInfo.setId(id);
        xmslProjectBasicInfo = xmslProjectBasicInfoMapper.getProjectBasicInfo(xmslProjectBasicInfo);

        //主要桥梁结构形式
        XmslProjectBridgeStructure xmslProjectBridgeStructure = new XmslProjectBridgeStructure();
        xmslProjectBridgeStructure.setProjectBasicInfoId(id);
        List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList = xmslProjectBridgeStructureMapper.getProjectBridgeStructureList(xmslProjectBridgeStructure);
        xmslProjectBasicInfo.setXmslProjectBridgeStructureList(xmslProjectBridgeStructureList);

        //主要涵洞结构形式
        XmslProjectCulvertStructure xmslProjectCulvertStructure = new XmslProjectCulvertStructure();
        xmslProjectCulvertStructure.setProjectBasicInfoId(id);
        List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList = xmslProjectCulvertStructureMapper.getProjectCulvertStructureList(xmslProjectCulvertStructure);
        xmslProjectBasicInfo.setXmslProjectCulvertStructureList(xmslProjectCulvertStructureList);

        //主要工程数量
        XmslProjectEngineeringAmount xmslProjectEngineeringAmount = new XmslProjectEngineeringAmount();
        xmslProjectEngineeringAmount.setProjectBasicInfoId(id);
        List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList = xmslProjectEngineeringAmountMapper.getProjectEngineeringAmountList(xmslProjectEngineeringAmount);
        List<XmslProjectEngineeringAmount> treeList = this.getXmslProjectEngineeringAmountTreeList(xmslProjectEngineeringAmountList);
        xmslProjectBasicInfo.setXmslProjectEngineeringAmountList(treeList);

        //主要材料数量
        XmslProjectMaterialsAmount xmslProjectMaterialsAmount = new XmslProjectMaterialsAmount();
        xmslProjectMaterialsAmount.setProjectBasicInfoId(id);
        List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList = xmslProjectMaterialsAmountMapper.getProjectMaterialsAmountList(xmslProjectMaterialsAmount);
        xmslProjectBasicInfo.setXmslProjectMaterialsAmountList(xmslProjectMaterialsAmountList);

        return xmslProjectBasicInfo;
    }

    /**
     * 工程结构数量集合转换树列表
     * @param xmslProjectEngineeringAmountList
     * @return
     */
    public List<XmslProjectEngineeringAmount> getXmslProjectEngineeringAmountTreeList(List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList){
        List<XmslProjectEngineeringAmount> treeList = new ArrayList<>();
        for (XmslProjectEngineeringAmount xmslProjectEngineeringAmount : xmslProjectEngineeringAmountList) {
            Long pid = xmslProjectEngineeringAmount.getPid();
            if(pid == null || pid == 0){
                this.getChildren(xmslProjectEngineeringAmount,xmslProjectEngineeringAmountList);
                treeList.add(xmslProjectEngineeringAmount);
            }
        }
        return treeList;
    }

    /**
     * 获取子集
     * @param root
     * @param xmslProjectEngineeringAmountList
     */
    private void getChildren(XmslProjectEngineeringAmount root, List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList) {
        List<XmslProjectEngineeringAmount> children = new ArrayList<>();
        for (XmslProjectEngineeringAmount xmslProjectEngineeringAmount : xmslProjectEngineeringAmountList) {
            if (xmslProjectEngineeringAmount.getPid() != null && xmslProjectEngineeringAmount.getPid().equals(root.getId())) {
                getChildren(xmslProjectEngineeringAmount, xmslProjectEngineeringAmountList);
                children.add(xmslProjectEngineeringAmount);
            }
        }
        root.setChildren(children);
    }

    public XmslProjectBasicInfo getProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo) {
        return xmslProjectBasicInfoMapper.getProjectBasicInfo(xmslProjectBasicInfo);
    }

    public List<XmslProjectBasicInfo> getProjectBasicInfoList(XmslProjectBasicInfo xmslProjectBasicInfo) {
        return xmslProjectBasicInfoMapper.getProjectBasicInfoList(xmslProjectBasicInfo);
    }

    /**
     * 新增项目基本信息
     * @param xmslProjectBasicInfo
     * @return
     */
    @Transactional
    public int insertProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo) {
        Long id = IdWorker.createId();
        xmslProjectBasicInfo.setId(id);

        //主要桥梁结构形式
        List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList = xmslProjectBasicInfo.getXmslProjectBridgeStructureList();
        if(!CollectionUtils.isEmpty(xmslProjectBridgeStructureList)){
            projectBridgeStructureService.insertProjectBridgeStructureList(xmslProjectBasicInfo.getXmslProjectBridgeStructureList(), xmslProjectBasicInfo);
        }

        //主要涵洞结构形式
        List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList = xmslProjectBasicInfo.getXmslProjectCulvertStructureList();
        if(!CollectionUtils.isEmpty(xmslProjectCulvertStructureList)){
            projectCulvertStructureService.insertProjectCulvertStructureList(xmslProjectBasicInfo.getXmslProjectCulvertStructureList(), xmslProjectBasicInfo);
        }

        //主要工程数量
        List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList = xmslProjectBasicInfo.getXmslProjectEngineeringAmountList();
        if(!CollectionUtils.isEmpty(xmslProjectEngineeringAmountList)){
            projectEngineeringAmountService.insertProjectEngineeringAmountList(xmslProjectBasicInfo.getXmslProjectEngineeringAmountList(), xmslProjectBasicInfo);
        }

        //主要材料数量
        List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList = xmslProjectBasicInfo.getXmslProjectMaterialsAmountList();
        if(!CollectionUtils.isEmpty(xmslProjectMaterialsAmountList)){
            projectMaterialsAmountService.insertProjectMaterialsAmountList(xmslProjectBasicInfo.getXmslProjectMaterialsAmountList(), xmslProjectBasicInfo);
        }

        xmslProjectBasicInfo.setCreateUser(SecurityUtils.getUserName());
        xmslProjectBasicInfo.setCreateTime(DateUtils.getNowDate());
        return xmslProjectBasicInfoMapper.insertProjectBasicInfo(xmslProjectBasicInfo);
    }

    @Transactional
    public int insertProjectBasicInfoList(List<XmslProjectBasicInfo> xmslProjectBasicInfoList) {
        for (XmslProjectBasicInfo xmslProjectBasicInfo : xmslProjectBasicInfoList) {
            xmslProjectBasicInfo.setCreateUser(SecurityUtils.getUserName());
            xmslProjectBasicInfo.setCreateTime(DateUtils.getNowDate());
        }
        return xmslProjectBasicInfoMapper.insertProjectBasicInfoList(xmslProjectBasicInfoList);
    }

    /**
     * 修改项目基本信息
     * @param xmslProjectBasicInfo
     * @return
     */
    @Transactional
    public int updateProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo) {
        //主要桥梁结构形式
        List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList = xmslProjectBasicInfo.getXmslProjectBridgeStructureList();
        if(!CollectionUtils.isEmpty(xmslProjectBridgeStructureList)){
            projectBridgeStructureService.editProjectBridgeStructureList(xmslProjectBridgeStructureList, xmslProjectBasicInfo);
        }

        //主要涵洞结构形式
        List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList = xmslProjectBasicInfo.getXmslProjectCulvertStructureList();
        if(!CollectionUtils.isEmpty(xmslProjectCulvertStructureList)){
            projectCulvertStructureService.editProjectCulvertStructureList(xmslProjectCulvertStructureList, xmslProjectBasicInfo);
        }

        //主要工程数量
        List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList = xmslProjectBasicInfo.getXmslProjectEngineeringAmountList();
        if(!CollectionUtils.isEmpty(xmslProjectEngineeringAmountList)){
            projectEngineeringAmountService.editProjectEngineeringAmountList(xmslProjectEngineeringAmountList, xmslProjectBasicInfo);
        }

        //主要材料数量
        List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList = xmslProjectBasicInfo.getXmslProjectMaterialsAmountList();
        if(!CollectionUtils.isEmpty(xmslProjectMaterialsAmountList)){
            projectMaterialsAmountService.editProjectMaterialsAmountList(xmslProjectMaterialsAmountList, xmslProjectBasicInfo);
        }

        xmslProjectBasicInfo.setUpdateUser(SecurityUtils.getUserName());
        xmslProjectBasicInfo.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectBasicInfoMapper.updateProjectBasicInfo(xmslProjectBasicInfo);
    }

    @Transactional
    public int updateProjectBasicInfoList(List<XmslProjectBasicInfo> xmslProjectBasicInfoList) {
        for (XmslProjectBasicInfo xmslProjectBasicInfo : xmslProjectBasicInfoList) {
            xmslProjectBasicInfo.setUpdateUser(SecurityUtils.getUserName());
            xmslProjectBasicInfo.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslProjectBasicInfoMapper.updateProjectBasicInfoList(xmslProjectBasicInfoList);
    }
    
    @Transactional
    public int deleteProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfo) {
        xmslProjectBasicInfo.setUpdateUser(SecurityUtils.getUserName());
        xmslProjectBasicInfo.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectBasicInfoMapper.deleteProjectBasicInfo(xmslProjectBasicInfo);
    }

    @Transactional
    public int deleteProjectBasicInfoByPks(List<Long> projectBasicInfoPkList) {
        return xmslProjectBasicInfoMapper.deleteProjectBasicInfoByPks(projectBasicInfoPkList);
    }
}
