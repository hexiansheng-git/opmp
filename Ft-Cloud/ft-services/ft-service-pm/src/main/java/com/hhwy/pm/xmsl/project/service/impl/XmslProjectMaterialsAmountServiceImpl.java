package com.hhwy.pm.xmsl.project.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.XmslProjectMaterialsAmount;
import com.hhwy.pm.xmsl.project.mapper.XmslProjectMaterialsAmountMapper;
import com.hhwy.pm.xmsl.project.service.IXmslProjectMaterialsAmountService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:36
 * @remark 主要材料数量
 */
@Service
public class XmslProjectMaterialsAmountServiceImpl implements IXmslProjectMaterialsAmountService {

    @Autowired
    private XmslProjectMaterialsAmountMapper xmslProjectMaterialsAmountMapper;

    @Override
    public List<XmslProjectMaterialsAmount> getListByProjectInfoId(Long projectInfoId) {
        XmslProjectMaterialsAmount xmslProjectMaterialsAmount = new XmslProjectMaterialsAmount();
        xmslProjectMaterialsAmount.setProjectBasicInfoId(projectInfoId);
        return this.getProjectMaterialsAmountList(xmslProjectMaterialsAmount);
    }

    public List<XmslProjectMaterialsAmount> getProjectMaterialsAmountList(XmslProjectMaterialsAmount xmslProjectMaterialsAmount) {
        return xmslProjectMaterialsAmountMapper.getProjectMaterialsAmountList(xmslProjectMaterialsAmount);
    }

    /**
     * 编辑数据
     * @param xmslProjectMaterialsAmountList
     * @param xmslProjectBasicInfo
     */
    @Transactional
    public void editProjectMaterialsAmountList(List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList, XmslProjectBasicInfo xmslProjectBasicInfo){
        //删除旧数据
        XmslProjectMaterialsAmount xmslProjectMaterialsAmount = new XmslProjectMaterialsAmount();
        xmslProjectMaterialsAmount.setProjectBasicInfoId(xmslProjectBasicInfo.getId());
        xmslProjectMaterialsAmountMapper.deleteProjectMaterialsAmount(xmslProjectMaterialsAmount);

        //插入新数据
        if(!CollectionUtils.isEmpty(xmslProjectMaterialsAmountList)){
            this.insertProjectMaterialsAmountList(xmslProjectMaterialsAmountList, xmslProjectBasicInfo);
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
            xmslProjectMaterialsAmount.setRegionId(xmslProjectBasicInfo.getRegionId());
            xmslProjectMaterialsAmount.setRegionName(xmslProjectBasicInfo.getRegionName());
            xmslProjectMaterialsAmount.setDeptId(xmslProjectBasicInfo.getDeptId());
            xmslProjectMaterialsAmount.setCreateUser(xmslProjectBasicInfo.getCreateUser());
            xmslProjectMaterialsAmount.setCreateUserName(xmslProjectBasicInfo.getCreateUserName());
            xmslProjectMaterialsAmount.setCreateTime(DateUtils.getNowDate());
        }
        return xmslProjectMaterialsAmountMapper.insertProjectMaterialsAmountList(xmslProjectMaterialsAmountList);
    }

    @Transactional
    public int deleteProjectMaterialsAmount(XmslProjectMaterialsAmount xmslProjectMaterialsAmount) {
        xmslProjectMaterialsAmount.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        xmslProjectMaterialsAmount.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectMaterialsAmountMapper.deleteProjectMaterialsAmount(xmslProjectMaterialsAmount);
    }

    @Transactional
    public int deleteProjectMaterialsAmountByPks(List<Long> projectMaterialsAmountPkList) {
        return xmslProjectMaterialsAmountMapper.deleteProjectMaterialsAmountByPks(projectMaterialsAmountPkList);
    }
}
