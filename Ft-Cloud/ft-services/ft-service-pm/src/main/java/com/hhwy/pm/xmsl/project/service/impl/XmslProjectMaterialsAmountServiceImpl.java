package com.hhwy.pm.xmsl.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.XmslProjectMaterialsAmount;
import com.hhwy.pm.xmsl.project.mapper.XmslProjectMaterialsAmountMapper;
import com.hhwy.pm.xmsl.project.service.IXmslProjectMaterialsAmountService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author han
 * @date 2023-07-03 09:48:36
 * @remark 主要材料数量
 */
@Service
public class XmslProjectMaterialsAmountServiceImpl implements IXmslProjectMaterialsAmountService {

    @Autowired
    private XmslProjectMaterialsAmountMapper xmslProjectMaterialsAmountMapper;

                                                                                                                                                                                                                                                                                                    
    public XmslProjectMaterialsAmount getProjectMaterialsAmount(XmslProjectMaterialsAmount xmslProjectMaterialsAmount) {
        return xmslProjectMaterialsAmountMapper.getProjectMaterialsAmount(xmslProjectMaterialsAmount);
    }

    public List<XmslProjectMaterialsAmount> getProjectMaterialsAmountList(XmslProjectMaterialsAmount xmslProjectMaterialsAmount) {
        return xmslProjectMaterialsAmountMapper.getProjectMaterialsAmountList(xmslProjectMaterialsAmount);
    }

    @Transactional
    public int insertProjectMaterialsAmount(XmslProjectMaterialsAmount xmslProjectMaterialsAmount) {
        xmslProjectMaterialsAmount.setId(IdWorker.createId());
        xmslProjectMaterialsAmount.setCreateUser(SecurityUtils.getUserName());
        xmslProjectMaterialsAmount.setCreateTime(DateUtils.getNowDate());
        return xmslProjectMaterialsAmountMapper.insertProjectMaterialsAmount(xmslProjectMaterialsAmount);
    }

    /**
     * 编辑数据
     * @param xmslProjectMaterialsAmountList
     * @param xmslProjectBasicInfo
     */
    @Transactional
    public void editProjectMaterialsAmountList(List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList, XmslProjectBasicInfo xmslProjectBasicInfo){
        List<XmslProjectMaterialsAmount> insertList = new ArrayList<>();
        List<XmslProjectMaterialsAmount> updateList = new ArrayList<>();
        for (XmslProjectMaterialsAmount xmslProjectMaterialsAmount : xmslProjectMaterialsAmountList) {
            Long projectMaterialsAmountId = xmslProjectMaterialsAmount.getId();
            if(projectMaterialsAmountId == null){
//                projectMaterialsAmount.setId(IdWorker.createId());
//                projectMaterialsAmount.setProjectId(projectBasicInfo.getProjectId());
//                projectMaterialsAmount.setProjectBasicInfoId(projectBasicInfo.getId());
//                projectMaterialsAmount.setProjectName(projectBasicInfo.getProjectName());
//                projectMaterialsAmount.setCreateUser(SecurityUtils.getUserName());
//                projectMaterialsAmount.setCreateTime(DateUtils.getNowDate());
                insertList.add(xmslProjectMaterialsAmount);
            }else{
//                projectMaterialsAmount.setUpdateUser(SecurityUtils.getUserName());
//                projectMaterialsAmount.setUpdateTime(DateUtils.getNowDate());
                updateList.add(xmslProjectMaterialsAmount);
            }
        }
        if(insertList.size() > 0){
            this.insertProjectMaterialsAmountList(insertList, xmslProjectBasicInfo);
        }
        if(updateList.size() > 0){
            this.updateProjectMaterialsAmountList(updateList);
        }
    }

    /**
     * 批量插入
     * @param xmslProjectMaterialsAmountList
     * @return
     */
    @Transactional
    public int insertProjectMaterialsAmountList(List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList, XmslProjectBasicInfo xmslProjectBasicInfo) {
        for (XmslProjectMaterialsAmount xmslProjectMaterialsAmount : xmslProjectMaterialsAmountList) {
            xmslProjectMaterialsAmount.setId(IdWorker.createId());
            xmslProjectMaterialsAmount.setProjectId(xmslProjectBasicInfo.getProjectId());
            xmslProjectMaterialsAmount.setProjectBasicInfoId(xmslProjectBasicInfo.getId());
            xmslProjectMaterialsAmount.setProjectName(xmslProjectBasicInfo.getProjectName());
            xmslProjectMaterialsAmount.setCreateUser(SecurityUtils.getUserName());
            xmslProjectMaterialsAmount.setCreateTime(DateUtils.getNowDate());
        }
        return xmslProjectMaterialsAmountMapper.insertProjectMaterialsAmountList(xmslProjectMaterialsAmountList);
    }

    @Transactional
    public int updateProjectMaterialsAmount(XmslProjectMaterialsAmount xmslProjectMaterialsAmount) {
        xmslProjectMaterialsAmount.setUpdateUser(SecurityUtils.getUserName());
        xmslProjectMaterialsAmount.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectMaterialsAmountMapper.updateProjectMaterialsAmount(xmslProjectMaterialsAmount);
    }

    /**
     * 批量修改
     * @param xmslProjectMaterialsAmountList
     * @return
     */
    @Transactional
    public int updateProjectMaterialsAmountList(List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList) {
        for (XmslProjectMaterialsAmount xmslProjectMaterialsAmount : xmslProjectMaterialsAmountList) {
            xmslProjectMaterialsAmount.setUpdateUser(SecurityUtils.getUserName());
            xmslProjectMaterialsAmount.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslProjectMaterialsAmountMapper.updateProjectMaterialsAmountList(xmslProjectMaterialsAmountList);
    }
    
    @Transactional
    public int deleteProjectMaterialsAmount(XmslProjectMaterialsAmount xmslProjectMaterialsAmount) {
        xmslProjectMaterialsAmount.setUpdateUser(SecurityUtils.getUserName());
        xmslProjectMaterialsAmount.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectMaterialsAmountMapper.deleteProjectMaterialsAmount(xmslProjectMaterialsAmount);
    }

    @Transactional
    public int deleteProjectMaterialsAmountByPks(List<Long> projectMaterialsAmountPkList) {
        return xmslProjectMaterialsAmountMapper.deleteProjectMaterialsAmountByPks(projectMaterialsAmountPkList);
    }
}
