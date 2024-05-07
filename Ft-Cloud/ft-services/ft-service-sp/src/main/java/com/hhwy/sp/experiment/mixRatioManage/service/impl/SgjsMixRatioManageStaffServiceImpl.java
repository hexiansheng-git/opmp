package com.hhwy.sp.experiment.mixRatioManage.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hhwy.sp.experiment.mixRatioManage.mapper.SgjsMixRatioManageStaffMapper;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageStaff;
import com.hhwy.sp.experiment.mixRatioManage.service.ISgjsMixRatioManageStaffService;
import com.hhwy.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 施工方案管理-施工方案评审-人员Service业务层处理
 * 
 * @author wk
 * @date 2024-04-29
 */
@Service
public class SgjsMixRatioManageStaffServiceImpl implements ISgjsMixRatioManageStaffService {
    @Autowired
    private SgjsMixRatioManageStaffMapper sgjsMixRatioManageStaffMapper;

    /**
     * 查询施工方案管理-施工方案评审-人员
     * 
     * @param id 施工方案管理-施工方案评审-人员ID
     * @return 施工方案管理-施工方案评审-人员
     */
    @Override
    public SgjsMixRatioManageStaff selectSgjsMixRatioManageStaffById(Long id) {
        return sgjsMixRatioManageStaffMapper.selectSgjsMixRatioManageStaffById(id);
    }

    /**
     * 查询施工方案管理-施工方案评审-人员列表
     * 
     * @param sgjsMixRatioManageStaff 施工方案管理-施工方案评审-人员
     * @return 施工方案管理-施工方案评审-人员
     */
    @Override
    public List<SgjsMixRatioManageStaff> selectSgjsMixRatioManageStaffList(SgjsMixRatioManageStaff sgjsMixRatioManageStaff) {
        return sgjsMixRatioManageStaffMapper.selectSgjsMixRatioManageStaffList(sgjsMixRatioManageStaff);
    }

    /**
     * 新增施工方案管理-施工方案评审-人员
     * 
     * @param sgjsMixRatioManageStaff 施工方案管理-施工方案评审-人员
     * @return 结果
     */
    @Override
    public int insertSgjsMixRatioManageStaff(SgjsMixRatioManageStaff sgjsMixRatioManageStaff) {
        sgjsMixRatioManageStaff.setCreateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageStaffMapper.insertSgjsMixRatioManageStaff(sgjsMixRatioManageStaff);
    }

    @Override
    @Transactional
    public int batchInsert(List<SgjsMixRatioManageStaff> list) {
        if(CollectionUtils.isEmpty(list))
            return 0;
        return sgjsMixRatioManageStaffMapper.batchInsert(list);
    }

    /**
     * 修改施工方案管理-施工方案评审-人员
     * 
     * @param sgjsMixRatioManageStaff 施工方案管理-施工方案评审-人员
     * @return 结果
     */
    @Override
    public int updateSgjsMixRatioManageStaff(SgjsMixRatioManageStaff sgjsMixRatioManageStaff) {
        sgjsMixRatioManageStaff.setUpdateTime(DateUtils.getNowDate());
        return sgjsMixRatioManageStaffMapper.updateSgjsMixRatioManageStaff(sgjsMixRatioManageStaff);
    }

    /**
     * 删除施工方案管理-施工方案评审-人员对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSgjsMixRatioManageStaffByIds(String ids) {
        return sgjsMixRatioManageStaffMapper.deleteSgjsMixRatioManageStaffByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除施工方案管理-施工方案评审-人员信息
     * 
     * @param id 施工方案管理-施工方案评审-人员ID
     * @return 结果
     */
    public int deleteSgjsMixRatioManageStaffById(Long id) {
        return sgjsMixRatioManageStaffMapper.deleteSgjsMixRatioManageStaffById(id);
    }
}
