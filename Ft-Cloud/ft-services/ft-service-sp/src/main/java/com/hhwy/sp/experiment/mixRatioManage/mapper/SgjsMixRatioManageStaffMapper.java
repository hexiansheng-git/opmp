package com.hhwy.sp.experiment.mixRatioManage.mapper;

import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageStaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 施工方案管理-施工方案评审-人员Mapper接口
 * 
 * @author wk
 * @date 2024-04-29
 */
public interface SgjsMixRatioManageStaffMapper {
    /**
     * 查询施工方案管理-施工方案评审-人员
     * 
     * @param id 施工方案管理-施工方案评审-人员ID
     * @return 施工方案管理-施工方案评审-人员
     */
    public SgjsMixRatioManageStaff selectSgjsMixRatioManageStaffById(Long id);

    /**
     * 查询施工方案管理-施工方案评审-人员列表
     * 
     * @param sgjsMixRatioManageStaff 施工方案管理-施工方案评审-人员
     * @return 施工方案管理-施工方案评审-人员集合
     */
    public List<SgjsMixRatioManageStaff> selectSgjsMixRatioManageStaffList(SgjsMixRatioManageStaff sgjsMixRatioManageStaff);

    int batchInsert(@Param("dataList") List<SgjsMixRatioManageStaff> list);

    /**
     * 新增施工方案管理-施工方案评审-人员
     * 
     * @param sgjsMixRatioManageStaff 施工方案管理-施工方案评审-人员
     * @return 结果
     */
    public int insertSgjsMixRatioManageStaff(SgjsMixRatioManageStaff sgjsMixRatioManageStaff);

    /**
     * 修改施工方案管理-施工方案评审-人员
     * 
     * @param sgjsMixRatioManageStaff 施工方案管理-施工方案评审-人员
     * @return 结果
     */
    public int updateSgjsMixRatioManageStaff(SgjsMixRatioManageStaff sgjsMixRatioManageStaff);

    /**
     * 删除施工方案管理-施工方案评审-人员
     * 
     * @param id 施工方案管理-施工方案评审-人员ID
     * @return 结果
     */
    public int deleteSgjsMixRatioManageStaffById(Long id);

    /**
     * 批量删除施工方案管理-施工方案评审-人员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSgjsMixRatioManageStaffByIds(String[] ids);
}
