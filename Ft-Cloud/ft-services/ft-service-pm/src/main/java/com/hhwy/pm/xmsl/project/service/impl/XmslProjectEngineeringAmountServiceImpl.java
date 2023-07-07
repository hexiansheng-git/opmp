package com.hhwy.pm.xmsl.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.XmslProjectEngineeringAmount;
import com.hhwy.pm.xmsl.project.domain.vo.XmslProjectEngineeringAmountExportVo;
import com.hhwy.pm.xmsl.project.mapper.XmslProjectEngineeringAmountMapper;
import com.hhwy.pm.xmsl.project.service.IXmslProjectEngineeringAmountService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author han
 * @date 2023-07-03 09:48:34
 * @remark 主要工程数量
 */
@Service
public class XmslProjectEngineeringAmountServiceImpl implements IXmslProjectEngineeringAmountService {

    @Autowired
    private XmslProjectEngineeringAmountMapper xmslProjectEngineeringAmountMapper;


    public List<XmslProjectEngineeringAmount> getProjectEngineeringAmountList(XmslProjectEngineeringAmount xmslProjectEngineeringAmount) {
        return xmslProjectEngineeringAmountMapper.getProjectEngineeringAmountList(xmslProjectEngineeringAmount);
    }

    /**
     * 编辑数据
     * @param xmslProjectEngineeringAmountList
     * @param xmslProjectBasicInfo
     */
    @Transactional
    public void editProjectEngineeringAmountList(List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList, XmslProjectBasicInfo xmslProjectBasicInfo){
        List<XmslProjectEngineeringAmount> insertList = new ArrayList<>();
        List<XmslProjectEngineeringAmount> updateList = new ArrayList<>();
        for (XmslProjectEngineeringAmount xmslProjectEngineeringAmount : xmslProjectEngineeringAmountList) {
            Long projectEngineeringAmountId = xmslProjectEngineeringAmount.getId();
            if(projectEngineeringAmountId == null){
                insertList.add(xmslProjectEngineeringAmount);
            }else{
                updateList.add(xmslProjectEngineeringAmount);
            }
        }
        if(insertList.size() > 0){
            this.insertProjectEngineeringAmountList(insertList, xmslProjectBasicInfo);
        }
        if(updateList.size() > 0){
            this.updateProjectEngineeringAmountList(updateList);
        }
    }

    @Transactional
    public int insertProjectEngineeringAmountList(List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList, XmslProjectBasicInfo xmslProjectBasicInfo) {
        for (XmslProjectEngineeringAmount xmslProjectEngineeringAmount : xmslProjectEngineeringAmountList) {
            xmslProjectEngineeringAmount.setId(IdWorker.createId());
            xmslProjectEngineeringAmount.setPid(IdWorker.createId());
            xmslProjectEngineeringAmount.setProjectId(xmslProjectBasicInfo.getProjectId());
            xmslProjectEngineeringAmount.setProjectBasicInfoId(xmslProjectBasicInfo.getId());
            xmslProjectEngineeringAmount.setProjectName(xmslProjectBasicInfo.getProjectName());
            xmslProjectEngineeringAmount.setRegionId(xmslProjectBasicInfo.getRegionId());
            xmslProjectEngineeringAmount.setRegionName(xmslProjectBasicInfo.getRegionName());
            xmslProjectEngineeringAmount.setDeptId(xmslProjectBasicInfo.getDeptId());
            xmslProjectEngineeringAmount.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslProjectEngineeringAmount.setCreateUserName(SecurityUtils.getUserName());
            xmslProjectEngineeringAmount.setCreateTime(DateUtils.getNowDate());
        }
        return xmslProjectEngineeringAmountMapper.insertProjectEngineeringAmountList(xmslProjectEngineeringAmountList);
    }

    @Transactional
    public int updateProjectEngineeringAmountList(List<XmslProjectEngineeringAmount> xmslProjectEngineeringAmountList) {
        for (XmslProjectEngineeringAmount xmslProjectEngineeringAmount : xmslProjectEngineeringAmountList) {
            xmslProjectEngineeringAmount.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            xmslProjectEngineeringAmount.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslProjectEngineeringAmountMapper.updateProjectEngineeringAmountList(xmslProjectEngineeringAmountList);
    }
    
    @Transactional
    public int deleteProjectEngineeringAmount(XmslProjectEngineeringAmount xmslProjectEngineeringAmount) {
        xmslProjectEngineeringAmount.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        xmslProjectEngineeringAmount.setUpdateTime(DateUtils.getNowDate());
        return xmslProjectEngineeringAmountMapper.deleteProjectEngineeringAmount(xmslProjectEngineeringAmount);
    }

    @Transactional
    public int deleteProjectEngineeringAmountByPks(List<Long> projectEngineeringAmountPkList) {
        return xmslProjectEngineeringAmountMapper.deleteProjectEngineeringAmountByPks(projectEngineeringAmountPkList);
    }

    @Override
    public List<XmslProjectEngineeringAmountExportVo> getProjectEngineeringAmountExportVoList(XmslProjectEngineeringAmount projectEngineeringAmountParam) {
        return xmslProjectEngineeringAmountMapper.getProjectEngineeringAmountExportVoList(projectEngineeringAmountParam);
    }
}
