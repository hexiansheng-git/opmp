package com.hhwy.sp.experiment.mixRatioManage.service;

import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManageStaffRecord;
import com.hhwy.sp.experiment.mixRatioManage.domain.vo.SgjsMixRatioManageSaveVo;

import java.util.List;

/**
 * 施工方案管理-施工方案评审-人员意见记录Service接口
 * 
 * @author wk
 * @date 2024-04-29
 */
public interface ISgjsMixRatioManageStaffRecordService {
    /**
     * 查询施工方案管理-施工方案评审-人员意见记录
     * 
     * @param id 施工方案管理-施工方案评审-人员意见记录ID
     * @return 施工方案管理-施工方案评审-人员意见记录
     */
    public SgjsMixRatioManageStaffRecord selectSgjsMixRatioManageStaffRecordById(Long id);

    /**
     * 查询施工方案管理-施工方案评审-人员意见记录列表
     * 
     * @param sgjsMixRatioManageStaffRecord 施工方案管理-施工方案评审-人员意见记录
     * @return 施工方案管理-施工方案评审-人员意见记录集合
     */
    public List<SgjsMixRatioManageStaffRecord> selectSgjsMixRatioManageStaffRecordList(SgjsMixRatioManageStaffRecord sgjsMixRatioManageStaffRecord);

    void saveRecord(SgjsMixRatioManageSaveVo mixRatioManage);

    int batchInsert(List<SgjsMixRatioManageStaffRecord> list);

    /**
     * 新增施工方案管理-施工方案评审-人员意见记录
     * 
     * @param sgjsMixRatioManageStaffRecord 施工方案管理-施工方案评审-人员意见记录
     * @return 结果
     */
    public int insertSgjsMixRatioManageStaffRecord(SgjsMixRatioManageStaffRecord sgjsMixRatioManageStaffRecord);

    /**
     * 修改施工方案管理-施工方案评审-人员意见记录
     * 
     * @param sgjsMixRatioManageStaffRecord 施工方案管理-施工方案评审-人员意见记录
     * @return 结果
     */
    public int updateSgjsMixRatioManageStaffRecord(SgjsMixRatioManageStaffRecord sgjsMixRatioManageStaffRecord);

    /**
     * 批量删除施工方案管理-施工方案评审-人员意见记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSgjsMixRatioManageStaffRecordByIds(String ids);

    /**
     * 删除施工方案管理-施工方案评审-人员意见记录信息
     * 
     * @param id 施工方案管理-施工方案评审-人员意见记录ID
     * @return 结果
     */
    public int deleteSgjsMixRatioManageStaffRecordById(Long id);
}
